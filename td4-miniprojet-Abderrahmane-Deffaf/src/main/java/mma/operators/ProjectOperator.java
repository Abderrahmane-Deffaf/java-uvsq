package mma.operators;

import java.util.List;
import mma.model.Table;

/**
 * Opérateur PROJECT pour la projection de colonnes.
 *
 * @author Abderrahmane Deffaf
 * @version 1.0
 */
public final class ProjectOperator implements Operator {
  /**
   * Opérateur source.
   */
  private final Operator input;

  /**
   * Colonnes à projeter.
   */
  private final List<String> columns;

  /**
   * Constructeur.
   *
   * @param sourceOp Opérateur source
   * @param projectedColumns Colonnes à projeter
   */
  public ProjectOperator(final Operator sourceOp,
      final List<String> projectedColumns) {
    this.input = sourceOp;
    this.columns = projectedColumns;
  }

  @Override
  public Table execute() {
    final Table inputTable = input.execute();
    return inputTable.project(columns);
  }
}
