package mma.csv;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import mma.model.Table;

/**
 * Lecteur de fichiers CSV.
 *
 * @author Abderrahmane Deffaf
 * @version 1.0
 */
public final class CsvReader {

  /**
   * Constructeur privé.
   */
  private CsvReader() {
    // Classe utilitaire
  }

  /**
   * Lit un fichier CSV et retourne une Table.
   *
   * @param filePath Le chemin du fichier CSV
   * @return La table lue
   * @throws IOException Si erreur de lecture
   */
  public static Table read(final Path filePath) throws IOException {
    final List<String> lines = new ArrayList<>();

    try (BufferedReader reader = Files.newBufferedReader(filePath)) {
      String line;
      while ((line = reader.readLine()) != null) {
        lines.add(line);
      }
    }

    if (lines.isEmpty()) {
      throw new IOException("Fichier CSV vide: " + filePath);
    }

    // Première ligne: colonnes
    final String headerLine = lines.get(0);
    final List<String> columns = Arrays.asList(headerLine.split(","));

    // Lignes suivantes: données
    final List<Map<String, String>> rows = new ArrayList<>();
    for (int i = 1; i < lines.size(); i++) {
      final String dataLine = lines.get(i).trim();
      if (dataLine.isEmpty()) {
        continue;
      }

      final String[] values = dataLine.split(",", -1);
      final Map<String, String> row = new LinkedHashMap<>();

      for (int j = 0; j < columns.size(); j++) {
        final String value = j < values.length ? values[j].trim() : "";
        row.put(columns.get(j).trim(), value);
      }

      rows.add(row);
    }

    return new Table(columns, rows);
  }
}
