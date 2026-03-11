package mma.algtojson;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Construit la section tables du JSON à partir de la section tables du .alg.
 *
 * @author Abderrahmane Deffaf
 * @version 1.0
 */
public final class BuildTables {

  /**
   * Parse la section tables et génère le JSON.
   *
   * @param tablesSection La section contenant les déclarations de tables
   * @return La représentation JSON de la section tables
   */
  public String parse(final String tablesSection) {
    final Map<String, String> tables = new LinkedHashMap<>();

    // Séparer par ligne
    final String[] lines = tablesSection.split("\n|\r\n");

    for (String line : lines) {
      final String trimmedLine = line.trim();
      if (trimmedLine.isEmpty()) {
        continue;
      }

      // Format: tableName: fileName.csv
      final String[] parts = trimmedLine.split(":");
      if (parts.length != 2) {
        throw new IllegalArgumentException(
            "Format de table invalide: " + trimmedLine);
      }

      final String tableName = parts[0].trim();
      final String fileName = parts[1].trim();

      tables.put(tableName, fileName);
    }

    return buildJson(tables);
  }

  /**
   * Construit le JSON pour la section tables.
   *
   * @param tables La map des tables
   * @return Le JSON de la section tables
   */
  private String buildJson(final Map<String, String> tables) {
    final StringBuilder json = new StringBuilder();
    json.append("  \"tables\": {\n");

    final int size = tables.size();
    int count = 0;

    for (Map.Entry<String, String> entry : tables.entrySet()) {
      json.append("    \"")
          .append(entry.getKey())
          .append("\": \"")
          .append(entry.getValue())
          .append("\"");

      count++;
      if (count < size) {
        json.append(",");
      }
      json.append("\n");
    }

    json.append("  }");
    return json.toString();
  }
}
