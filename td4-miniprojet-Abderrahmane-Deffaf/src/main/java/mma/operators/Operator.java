package mma.operators;

import mma.model.Table;

/**
 * Interface pour les opérateurs algébriques.
 *
 * @author Abderrahmane Deffaf
 * @version 1.0
 */
@FunctionalInterface
public interface Operator {
  /**
   * Exécute l'opération et retourne le résultat.
   *
   * @return La table résultante
   */
  Table execute();
}
