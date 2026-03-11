package mma.operators;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import mma.model.Table;

/**
 * Opérateur SELECT pour filtrer les lignes.
 *
 * @author Abderrahmane Deffaf
 * @version 1.0
 */
public final class SelectOperator implements Operator {
  /**
   * Opérateur source.
   */
  private final Operator input;

  /**
   * Prédicat de sélection.
   */
  private final Predicate<Map<String, String>> predicate;

  /**
   * Constructeur.
   *
   * @param sourceOp Opérateur source
   * @param filterPredicate Prédicat de sélection
   */
  public SelectOperator(final Operator sourceOp,
      final Predicate<Map<String, String>> filterPredicate) {
    this.input = sourceOp;
    this.predicate = filterPredicate;
  }

  @Override
  public Table execute() {
    final Table inputTable = input.execute();
    final List<Map<String, String>> filteredRows = inputTable.getRows()
        .stream()
        .filter(predicate)
        .collect(Collectors.toList());

    return new Table(inputTable.getColumns(), filteredRows);
  }
}
