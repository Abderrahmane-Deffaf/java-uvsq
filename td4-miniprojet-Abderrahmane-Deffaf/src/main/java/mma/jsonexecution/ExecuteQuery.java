package mma.jsonexecution;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import mma.model.Table;
import mma.operators.JoinOperator;
import mma.operators.Operator;
import mma.operators.ProjectOperator;
import mma.operators.ScanOperator;
import mma.operators.SelectOperator;

/**
 * Exécute une requête à partir d'un fichier JSON.
 *
 * @author Abderrahmane Deffaf
 * @version 1.0
 */
public final class ExecuteQuery {
  /**
   * Nombre de parties attendues dans un prédicat.
   */
  private static final int PREDICATE_PARTS = 3;

  /**
   * Le chemin du fichier JSON.
   */
  private final Path jsonFilePath;

  /**
   * Map des tables par nom.
   */
  private final Map<String, Path> tables;

  /**
   * Constructeur.
   *
   * @param jsonPath Le chemin du fichier JSON
   */
  public ExecuteQuery(final Path jsonPath) {
    this.jsonFilePath = jsonPath;
    this.tables = new HashMap<>();
  }

  /**
   * Exécute la requête et retourne le résultat.
   *
   * @return La table résultante
   * @throws IOException Si erreur d'I/O
   */
  public Table execute() throws IOException {
    // Lire le fichier JSON
    final String jsonContent = Files.readString(jsonFilePath);
    final Gson gson = new Gson();
    final JsonObject root = gson.fromJson(jsonContent, JsonObject.class);

    // Charger les tables
    loadTables(root.getAsJsonObject("tables"));

    // Construire et exécuter le plan
    final JsonObject plan = root.getAsJsonObject("plan");
    final Operator operator = buildOperator(plan);
    return operator.execute();
  }

  /**
   * Charge les tables depuis la section tables du JSON.
   *
   * @param tablesJson L'objet JSON des tables
   */
  private void loadTables(final JsonObject tablesJson) {
    final Path baseDir = jsonFilePath.getParent();
    tablesJson.entrySet().forEach(entry -> {
      final String tableName = entry.getKey();
      final String fileName = entry.getValue().getAsString();
      final Path tablePath = baseDir.resolve(fileName);
      tables.put(tableName, tablePath);
    });
  }

  /**
   * Construit un opérateur à partir d'un JSON.
   *
   * @param planJson L'objet JSON du plan
   * @return L'opérateur construit
   */
  private Operator buildOperator(final JsonObject planJson) {
    final String type = planJson.get("type").getAsString();

    switch (type) {
      case "SCAN":
        return buildScan(planJson);
      case "PROJECT":
        return buildProject(planJson);
      case "SELECT":
        return buildSelect(planJson);
      case "JOIN":
        return buildJoin(planJson);
      default:
        throw new IllegalArgumentException("Type inconnu: " + type);
    }
  }

  /**
   * Construit un opérateur SCAN.
   *
   * @param planJson L'objet JSON
   * @return L'opérateur SCAN
   */
  private Operator buildScan(final JsonObject planJson) {
    final String tableName = planJson.get("table").getAsString();
    final Path tablePath = tables.get(tableName);
    if (tablePath == null) {
      throw new IllegalArgumentException("Table inconnue: " + tableName);
    }
    return new ScanOperator(tablePath);
  }

  /**
   * Construit un opérateur PROJECT.
   *
   * @param planJson L'objet JSON
   * @return L'opérateur PROJECT
   */
  private Operator buildProject(final JsonObject planJson) {
    final JsonArray columnsArray = planJson.getAsJsonArray("columns");
    final List<String> columns = new ArrayList<>();
    for (JsonElement elem : columnsArray) {
      columns.add(elem.getAsString());
    }

    final JsonObject inputJson = planJson.getAsJsonObject("input");
    final Operator input = buildOperator(inputJson);

    return new ProjectOperator(input, columns);
  }

  /**
   * Construit un opérateur SELECT.
   *
   * @param planJson L'objet JSON
   * @return L'opérateur SELECT
   */
  private Operator buildSelect(final JsonObject planJson) {
    final String predicateStr = planJson.get("predicate").getAsString();
    final Predicate<Map<String, String>> predicate =
        buildPredicate(predicateStr);

    final JsonObject inputJson = planJson.getAsJsonObject("input");
    final Operator input = buildOperator(inputJson);

    return new SelectOperator(input, predicate);
  }

  /**
   * Construit un opérateur JOIN.
   *
   * @param planJson L'objet JSON
   * @return L'opérateur JOIN
   */
  private Operator buildJoin(final JsonObject planJson) {
    final JsonObject leftJson = planJson.getAsJsonObject("left");
    final JsonObject rightJson = planJson.getAsJsonObject("right");
    final String onLeft = planJson.get("onLeft").getAsString();
    final String onRight = planJson.get("onRight").getAsString();

    final Operator left = buildOperator(leftJson);
    final Operator right = buildOperator(rightJson);

    return new JoinOperator(left, right, onLeft, onRight);
  }

  /**
   * Construit un prédicat à partir d'une chaîne.
   *
   * @param predicateStr La chaîne du prédicat (ex: "age < 25")
   * @return Le prédicat fonctionnel
   */
  private Predicate<Map<String, String>> buildPredicate(
      final String predicateStr) {
    // Parser simple: "column operator value"
    final String[] parts = predicateStr.split("\\s+");
    if (parts.length != PREDICATE_PARTS) {
      throw new IllegalArgumentException("Prédicat invalide: "
          + predicateStr);
    }

    final String column = parts[0];
    final String operator = parts[1];
    // Remove single quotes from string values
    String value = parts[2];
    if (value.startsWith("'") && value.endsWith("'") && value.length() > 1) {
      value = value.substring(1, value.length() - 1);
    }
    final String finalValue = value;

    return row -> {
      final String rowValue = row.get(column);
      if (rowValue == null) {
        return false;
      }

      switch (operator) {
        case "<":
          return compareValues(rowValue, finalValue) < 0;
        case "<=":
          return compareValues(rowValue, finalValue) <= 0;
        case ">":
          return compareValues(rowValue, finalValue) > 0;
        case ">=":
          return compareValues(rowValue, finalValue) >= 0;
        case "=":
        case "==":
          return equalsIgnoreCaseIfString(rowValue, finalValue);
        case "!=":
          return !equalsIgnoreCaseIfString(rowValue, finalValue);
        default:
          throw new IllegalArgumentException("Opérateur inconnu: "
              + operator);
      }
    };
  }

  /**
   * Compare deux valeurs (numériques ou chaînes).
   *
   * @param val1 Première valeur
   * @param val2 Deuxième valeur
   * @return Résultat de la comparaison
   */
  private int compareValues(final String val1, final String val2) {
    try {
      final double num1 = Double.parseDouble(val1);
      final double num2 = Double.parseDouble(val2);
      return Double.compare(num1, num2);
    } catch (NumberFormatException e) {
      // Si ce n'est pas un nombre, comparer comme des chaînes
      // (case-insensitive)
      return val1.toLowerCase().compareTo(val2.toLowerCase());
    }
  }

  /**
   * Compare deux valeurs pour l'égalité (case-insensitive pour les
   * chaînes).
   *
   * @param val1 Première valeur
   * @param val2 Deuxième valeur
   * @return true si les valeurs sont égales
   */
  private boolean equalsIgnoreCaseIfString(final String val1,
                                            final String val2) {
    try {
      final double num1 = Double.parseDouble(val1);
      final double num2 = Double.parseDouble(val2);
      return num1 == num2;
    } catch (NumberFormatException e) {
      // Si ce n'est pas un nombre, comparer comme des chaînes
      // (case-insensitive)
      return val1.equalsIgnoreCase(val2);
    }
  }
}

