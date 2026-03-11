package mma.io;

import java.nio.file.Files;
import java.nio.file.Path;
import mma.algtojson.BuildJsonFromAlg;

/**
 * Processeur pour les fichiers .alg.
 * Convertit un fichier .alg en .json puis exécute la requête.
 *
 * @author Abderrahmane Deffaf
 * @version 1.0
 */

public class AlgFileProcessor {
  /**
   * Constructeur public.
   */
  public AlgFileProcessor() {
    // Constructeur par défaut
  }

  /**
   * Traite un fichier .alg.
   *
   * @param filename Le nom du fichier .alg
   */
  public void process(final String filename) {
    final Path inputPath = Path.of(filename).toAbsolutePath();
    System.out.println("Conversion de " + inputPath + " en JSON...");

    final BuildJsonFromAlg builder = new BuildJsonFromAlg(filename);
    try {
      final String jsonContent = builder.parse();

      // Générer le chemin du fichier JSON
      final String jsonFileName = inputPath.toString()
          .replaceFirst("\\.alg$", ".json");
      final Path jsonPath = Path.of(jsonFileName);

      // Sauvegarder le fichier JSON
      Files.writeString(jsonPath, jsonContent);

      System.out.println("Fichier JSON sauvegardé: " + jsonPath);

    } catch (Exception e) {
      System.err.println("Erreur lors du traitement du fichier .alg: "
          + e.getMessage());
    }
  }
}
