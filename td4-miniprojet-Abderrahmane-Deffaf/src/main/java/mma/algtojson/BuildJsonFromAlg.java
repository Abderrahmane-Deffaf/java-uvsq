package mma.algtojson;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Construit un fichier JSON à partir d'un fichier .alg.
 *
 * @author Abderrahmane Deffaf
 * @version 1.0
 */
public final class BuildJsonFromAlg {
    /**
     * Le nom du fichier .alg.
     */
    private final String fileName;

    /**
     * Constructeur.
     *
     * @param algFileName Le nom du fichier .alg
     */
    public BuildJsonFromAlg(final String algFileName) {
        this.fileName = algFileName;
    }

    /**
     * Parse le fichier .alg et génère le JSON.
     *
     * @return Le contenu JSON généré
     * @throws IOException Si erreur de lecture
     */
    public String parse() throws IOException {
        // Lire tout le contenu du fichier
        final Path filePath = Path.of(fileName);
        final String content = Files.readString(filePath);

        // Séparer le contenu par ligne vide
        final String[] sections = content.split("\n\n|\r\n\r\n");

        if (sections.length < 2) {
            throw new IllegalArgumentException(
                "Format invalide: le fichier doit contenir "
                + "une section tables et une section opérations "
                + "séparées par une ligne vide");
        }

        // Première section: déclarations de tables
        final String tablesJson = new BuildTables().parse(sections[0]);

        // Deuxième section: opérations
        final String planJson = new BuildPlan().parse(sections[1]);

        // Fusionner les deux parties
        return "{\n" + tablesJson + ",\n  \"plan\": " + planJson + "\n}";
    }
}
