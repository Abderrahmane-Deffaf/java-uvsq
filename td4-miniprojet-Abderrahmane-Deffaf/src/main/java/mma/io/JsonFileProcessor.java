package mma.io;

import java.nio.file.Path;
import mma.csv.CsvWriter;
import mma.jsonexecution.ExecuteQuery;
import mma.model.Table;

/**
 * Processeur pour les fichiers .json.
 * Exécute directement une requête à partir d'un fichier JSON.
 *
 * @author Abderrahmane Deffaf
 * @version 1.0
 */

public class JsonFileProcessor {
  /**
   * Constructeur public.
   */
  public JsonFileProcessor() {
    // Constructeur par défaut
  }

  /**
   * Traite un fichier .json.
   *
   * @param filename Le nom du fichier .json
   */

  public void process(final String filename) {
    System.out.println("Traitement fichier JSON: " + filename);

    try {
      final Path jsonPath = Path.of(filename).toAbsolutePath();
      final ExecuteQuery executor = new ExecuteQuery(jsonPath);
      final Table result = executor.execute();

      // Sauvegarder le résultat
      saveResult(result, jsonPath);
      System.out.println("Requête exécutée avec succès!");
    } catch (Exception e) {
      System.err.println("Erreur lors de l'exécution de la requête: "
          + e.getMessage());
      e.printStackTrace();
    }
  }

  /**
   * Sauvegarde le résultat dans results/output.csv.
   *
   * @param result La table résultante
   * @param jsonPath Le chemin du fichier JSON
   */
  private void saveResult(final Table result, final Path jsonPath) {
    try {
      final Path baseDir = jsonPath.getParent();
      final Path resultsDir = baseDir.resolve("results");
      final Path outputPath = resultsDir.resolve("output.csv");

      CsvWriter.write(result, outputPath);
      System.out.println("Résultat sauvegardé dans: " + outputPath);
    } catch (Exception e) {
      System.err.println("Erreur lors de la sauvegarde: " + e.getMessage());
    }
  }
}
