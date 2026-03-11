package mma.csv;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import mma.model.Table;

/**
 * Écrivain de fichiers CSV.
 *
 * @author Abderrahmane Deffaf
 * @version 1.0
 */
public final class CsvWriter {

  /**
   * Constructeur privé.
   */
  private CsvWriter() {
    // Classe utilitaire
  }

  /**
   * Écrit une Table dans un fichier CSV.
   *
   * @param table La table à écrire
   * @param filePath Le chemin du fichier CSV
   * @throws IOException Si erreur d'écriture
   */
  public static void write(final Table table, final Path filePath)
      throws IOException {
    // Créer le dossier parent si nécessaire
    final Path parent = filePath.getParent();
    if (parent != null && !Files.exists(parent)) {
      Files.createDirectories(parent);
    }

    try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
      // Écrire l'en-tête
      final List<String> columns = table.getColumns();
      writer.write(String.join(",", columns));

      // Écrire les lignes
      for (Map<String, String> row : table.getRows()) {
        writer.newLine();
        final String line = columns.stream()
            .map(col -> row.getOrDefault(col, ""))
            .collect(Collectors.joining(","));
        writer.write(line);
      }
    }
  }
}
