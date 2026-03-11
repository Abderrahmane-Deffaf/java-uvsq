package mma.algtojson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Construit la section plan du JSON à partir de la section opérations du .alg.
 *
 * @author Abderrahmane Deffaf
 * @version 1.0
 */
public final class BuildPlan {

  /**
   * Nombre de parties attendues dans un prédicat.
   */
  private static final int PREDICATE_PARTS_COUNT = 3;

  /**
   * Nombre de tokens attendus dans une opération JOIN.
   */
  private static final int JOIN_TOKENS_COUNT = 4;

  /**
   * Index pour le premier argument (index 1).
   */
  private static final int ARG_INDEX_1 = 1;

  /**
   * Index pour le deuxième argument (index 2).
   */
  private static final int ARG_INDEX_2 = 2;

  /**
   * Index pour le troisième argument (index 3).
   */
  private static final int ARG_INDEX_3 = 3;

  /**
   * Index pour le quatrième argument (index 4).
   */
  private static final int ARG_INDEX_4 = 4;

  /**
   * Map des opérations par nom.
   */
  private final Map<String, Map<String, Object>> operations = new HashMap<>();

  /**
   * Parse la section opérations et génère le JSON du plan.
   *
   * @param operationsSection La section contenant les opérations
   * @return La représentation JSON de la section plan
   */
  public String parse(final String operationsSection) {
    final String[] lines = operationsSection.split("\n|\r\n");
    final List<String> nonEmptyLines = new ArrayList<>();

    // Filtrer les lignes vides
    for (String line : lines) {
      final String trimmed = line.trim();
      if (!trimmed.isEmpty()) {
        nonEmptyLines.add(trimmed);
      }
    }

    // Passe 1: Parser toutes les lignes de haut en bas pour collecter
    // les noms des opérations (sans résoudre les dépendances)
    for (int i = 0; i < nonEmptyLines.size(); i++) {
      parseLineWithoutResolution(nonEmptyLines.get(i));
    }

    // Passe 2: Résoudre les dépendances de haut en bas
    for (int i = 0; i < nonEmptyLines.size(); i++) {
      resolveLineDependencies(nonEmptyLines.get(i));
    }

    // La dernière ligne est le résultat final
    final String finalResult = extractResultName(
        nonEmptyLines.get(nonEmptyLines.size() - 1));
    final Map<String, Object> rootOperation = operations.get(finalResult);

    return buildJson(rootOperation, 1);
  }

  /**
   * Parse une ligne d'opération sans résoudre les dépendances.
   *
   * @param line La ligne à parser
   */
  private void parseLineWithoutResolution(final String line) {
    final String[] parts = line.split("=", 2);
    if (parts.length != 2) {
      throw new IllegalArgumentException("Format invalide: " + line);
    }

    final String resultName = parts[0].trim();
    final String operation = parts[1].trim();

    final String[] tokens = operation.split("\\s+");
    final String type = tokens[0];

    final Map<String, Object> op = new HashMap<>();
    op.put("type", type.equals("PROJ") ? "PROJECT"
        : type.equals("SELECT") ? "SELECT"
        : type.equals("JOIN") ? "JOIN" : "SCAN");

    // Stocker les tokens bruts pour la résolution plus tard
    op.put("_tokens", tokens);
    op.put("_fullOperation", operation);

    operations.put(resultName, op);
  }

  /**
   * Résout les dépendances d'une ligne d'opération.
   *
   * @param line La ligne à résoudre
   */
  private void resolveLineDependencies(final String line) {
    final String[] parts = line.split("=", 2);
    final String resultName = parts[0].trim();
    final Map<String, Object> op = operations.get(resultName);

    final String[] tokens = (String[]) op.get("_tokens");
    final String fullOperation = (String) op.get("_fullOperation");
    final String type = (String) op.get("type");

    // Nettoyer les données temporaires
    op.remove("_tokens");
    op.remove("_fullOperation");

    switch (type) {
      case "PROJECT":
        parseProject(op, tokens);
        break;
      case "SELECT":
        parseSelect(op, tokens, fullOperation);
        break;
      case "JOIN":
        parseJoin(op, tokens);
        break;
      case "SCAN":
        parseScan(op, tokens);
        break;
      default:
        break;
    }
  }

  /**
   * Parse une opération PROJECT.
   *
   * @param op La map de l'opération
   * @param tokens Les tokens de la ligne
   */
  private void parseProject(final Map<String, Object> op,
                            final String[] tokens) {
    // Format: PROJ tableName/resultName col1 col2 ...
    op.put("type", "PROJECT");

    final String inputName = tokens[1];
    final List<String> columns = new ArrayList<>();

    for (int i = 2; i < tokens.length; i++) {
      columns.add(tokens[i]);
    }

    op.put("columns", columns);

    // Résoudre l'input (soit une table SCAN, soit une opération précédente)
    if (operations.containsKey(inputName)) {
      op.put("input", operations.get(inputName));
    } else {
      // C'est une table, créer un SCAN
      final Map<String, Object> scan = new HashMap<>();
      scan.put("type", "SCAN");
      scan.put("table", inputName);
      op.put("input", scan);
    }
  }

  /**
   * Parse une opération SELECT.
   *
   * @param op La map de l'opération
   * @param tokens Les tokens de la ligne
   * @param fullOperation La ligne complète pour le prédicat
   */
  private void parseSelect(final Map<String, Object> op,
                           final String[] tokens,
                           final String fullOperation) {
    // Format: SELECT tableName/resultName predicate
    op.put("type", "SELECT");

    final String inputName = tokens[1];

    // Extraire le prédicat (tout après le nom de la table)
    final int predicateStart = fullOperation.indexOf(inputName)
        + inputName.length();
    final String predicate = fullOperation.substring(predicateStart).trim();

    op.put("predicate", predicate);

    // Résoudre l'input
    if (operations.containsKey(inputName)) {
      op.put("input", operations.get(inputName));
    } else {
      final Map<String, Object> scan = new HashMap<>();
      scan.put("type", "SCAN");
      scan.put("table", inputName);
      op.put("input", scan);
    }
  }

  /**
   * Parse une opération JOIN.
   *
   * @param op La map de l'opération
   * @param tokens Les tokens de la ligne
   */
  private void parseJoin(final Map<String, Object> op,
                         final String[] tokens) {
    // Format: JOIN leftTable rightTable leftCol rightCol
    op.put("type", "JOIN");

    final String leftName = tokens[ARG_INDEX_1];
    final String rightName = tokens[ARG_INDEX_2];
    final String leftCol = tokens[ARG_INDEX_3];
    final String rightCol = tokens[ARG_INDEX_4];

    // Résoudre left input
    if (operations.containsKey(leftName)) {
      op.put("left", operations.get(leftName));
    } else {
      final Map<String, Object> scan = new HashMap<>();
      scan.put("type", "SCAN");
      scan.put("table", leftName);
      op.put("left", scan);
    }

    // Résoudre right input
    if (operations.containsKey(rightName)) {
      op.put("right", operations.get(rightName));
    } else {
      final Map<String, Object> scan = new HashMap<>();
      scan.put("type", "SCAN");
      scan.put("table", rightName);
      op.put("right", scan);
    }

    op.put("onLeft", leftCol);
    op.put("onRight", rightCol);
  }

  /**
   * Parse une opération SCAN.
   *
   * @param op La map de l'opération
   * @param tokens Les tokens de la ligne
   */
  private void parseScan(final Map<String, Object> op,
                         final String[] tokens) {
    op.put("type", "SCAN");
    op.put("table", tokens[1]);
  }

  /**
   * Extrait le nom du résultat d'une ligne.
   *
   * @param line La ligne
   * @return Le nom du résultat
   */
  private String extractResultName(final String line) {
    return line.split("=")[0].trim();
  }

  /**
   * Construit le JSON pour une opération.
   *
   * @param op L'opération
   * @param indent Le niveau d'indentation
   * @return Le JSON de l'opération
   */
  private String buildJson(final Map<String, Object> op, final int indent) {
    final StringBuilder json = new StringBuilder();
    final String indentStr = "  ".repeat(indent);

    json.append("{\n");

    final String type = (String) op.get("type");
    json.append(indentStr).append("  \"type\": \"").append(type).append("\"");

    switch (type) {
      case "PROJECT":
        buildProjectJson(json, op, indent);
        break;
      case "SELECT":
        buildSelectJson(json, op, indent);
        break;
      case "JOIN":
        buildJoinJson(json, op, indent);
        break;
      case "SCAN":
        buildScanJson(json, op, indent);
        break;
      default:
        break;
    }

    json.append("\n").append(indentStr).append("}");
    return json.toString();
  }

  /**
   * Construit le JSON pour PROJECT.
   *
   * @param json Le StringBuilder
   * @param op L'opération
   * @param indent Le niveau d'indentation
   */
  @SuppressWarnings("unchecked")
  private void buildProjectJson(final StringBuilder json,
                                 final Map<String, Object> op,
                                 final int indent) {
    final String indentStr = "  ".repeat(indent);
    final List<String> columns = (List<String>) op.get("columns");

    json.append(",\n").append(indentStr).append("  \"columns\": [");
    for (int i = 0; i < columns.size(); i++) {
      json.append("\"").append(columns.get(i)).append("\"");
      if (i < columns.size() - 1) {
        json.append(", ");
      }
    }
    json.append("]");

    final Map<String, Object> input =
        (Map<String, Object>) op.get("input");
    json.append(",\n").append(indentStr).append("  \"input\": ");
    json.append(buildJson(input, indent + 1));
  }

  /**
   * Construit le JSON pour SELECT.
   *
   * @param json Le StringBuilder
   * @param op L'opération
   * @param indent Le niveau d'indentation
   */
  @SuppressWarnings("unchecked")
  private void buildSelectJson(final StringBuilder json,
                                final Map<String, Object> op,
                                final int indent) {
    final String indentStr = "  ".repeat(indent);
    final String predicate = (String) op.get("predicate");

    json.append(",\n").append(indentStr).append("  \"predicate\": \"")
        .append(predicate).append("\"");

    final Map<String, Object> input =
        (Map<String, Object>) op.get("input");
    json.append(",\n").append(indentStr).append("  \"input\": ");
    json.append(buildJson(input, indent + 1));
  }

  /**
   * Construit le JSON pour JOIN.
   *
   * @param json Le StringBuilder
   * @param op L'opération
   * @param indent Le niveau d'indentation
   */
  @SuppressWarnings("unchecked")
  private void buildJoinJson(final StringBuilder json,
                              final Map<String, Object> op,
                              final int indent) {
    final String indentStr = "  ".repeat(indent);

    json.append(",\n").append(indentStr).append("  \"left\": ");
    json.append(buildJson((Map<String, Object>) op.get("left"), indent + 1));

    json.append(",\n").append(indentStr).append("  \"right\": ");
    json.append(buildJson((Map<String, Object>) op.get("right"), indent + 1));

    json.append(",\n").append(indentStr).append("  \"onLeft\": \"")
        .append(op.get("onLeft")).append("\"");
    json.append(",\n").append(indentStr).append("  \"onRight\": \"")
        .append(op.get("onRight")).append("\"");
  }

  /**
   * Construit le JSON pour SCAN.
   *
   * @param json Le StringBuilder
   * @param op L'opération
   * @param indent Le niveau d'indentation
   */
  private void buildScanJson(final StringBuilder json,
                              final Map<String, Object> op,
                              final int indent) {
    final String indentStr = "  ".repeat(indent);
    json.append(",\n").append(indentStr).append("  \"table\": \"")
        .append(op.get("table")).append("\"");
  }
}
