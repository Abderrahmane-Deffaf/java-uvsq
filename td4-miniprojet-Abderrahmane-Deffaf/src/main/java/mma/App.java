package mma;

import java.util.Map;
import java.util.function.Consumer;
import mma.io.AlgFileProcessor;
import mma.io.JsonFileProcessor;

/**
 * Classe principale de l'application MMA.
 * Cette classe contient le point d'entrée du programme.
 *
 * @author Abderrahmane Deffaf
 * @version 1.0
 */
public final class App {
    /**
     * Constructeur privé pour empêcher l'instanciation.
     * Cette classe est une utility class et ne doit pas être instanciée.
     */
    private App() {
        throw new UnsupportedOperationException("Utility class");
    }
    /**
     * Map des stratégies de traitement par extension de fichier.
     */
    private static final Map<String, Consumer<String>> FILE_PROCESSORS = Map.of(
        ".alg",  new AlgFileProcessor()::process,
        ".json", new JsonFileProcessor()::process
    );

    /**
     * Extrait l'extension d'un fichier.
     *
     * @param filename Le nom du fichier
     * @return L'extension (avec le point) ou chaîne vide
     */
    private static String getExtension(final String filename) {
        final int lastDot = filename.lastIndexOf('.');
        return lastDot > 0 ? filename.substring(lastDot) : "";
    }

    /**
     * Point d'entrée de l'application.
     * Affiche "Hello World!" sur la sortie standard.
     *
     * @param args Arguments de ligne de commande (non utilisés)
     */
    public static void main(final String[] args) {
        if (args.length == 0) {
            System.err.println(
                "Usage: java -jar mma.jar <file.alg|file.json>");
            System.exit(1);
        }

        final String filename = args[0];
        final String extension = getExtension(filename);

        // Approche fonctionnelle avec Map
        FILE_PROCESSORS
                .getOrDefault(extension,
                        file -> System.err.println(
                            "Format non supporté: " + file))
                .accept(filename);
    }
}
