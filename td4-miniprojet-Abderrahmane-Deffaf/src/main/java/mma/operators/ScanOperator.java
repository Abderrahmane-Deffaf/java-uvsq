package mma.operators;

import java.io.IOException;
import java.nio.file.Path;
import mma.csv.CsvReader;
import mma.model.Table;

/**
 * Opérateur SCAN pour lire une table depuis un fichier CSV.
 *
 * @author Abderrahmane Deffaf
 * @version 1.0
 */
public final class ScanOperator implements Operator {
  /**
   * Le chemin du fichier CSV.
   */
  private final Path filePath;

  /**
   * Constructeur.
   *
   * @param csvFilePath Le chemin du fichier CSV
   */
  public ScanOperator(final Path csvFilePath) {
    this.filePath = csvFilePath;
  }

  @Override
  public Table execute() {
    try {
      return CsvReader.read(filePath);
    } catch (IOException e) {
      throw new RuntimeException("Erreur lors du scan de " + filePath, e);
    }
  }
}
