package mma.operators;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import mma.model.Table;

/**
 * Opérateur JOIN pour joindre deux tables.
 *
 * @author Abderrahmane Deffaf
 * @version 1.0
 */
public final class JoinOperator implements Operator {
  /**
   * Opérateur gauche.
   */
  private final Operator left;

  /**
   * Opérateur droit.
   */
  private final Operator right;

  /**
   * Colonne de jointure à gauche.
   */
  private final String leftColumn;

  /**
   * Colonne de jointure à droite.
   */
  private final String rightColumn;

  /**
   * Constructeur.
   *
   * @param leftOp L'opérateur de gauche
   * @param rightOp L'opérateur de droite
   * @param leftCol La colonne de jointure à gauche
   * @param rightCol La colonne de jointure à droite
   */
  public JoinOperator(final Operator leftOp, final Operator rightOp,
                      final String leftCol, final String rightCol) {
    this.left = leftOp;
    this.right = rightOp;
    this.leftColumn = leftCol;
    this.rightColumn = rightCol;
  }

  @Override
  public Table execute() {
    final Table leftTable = left.execute();
    final Table rightTable = right.execute();

    // Combiner les colonnes (éviter les doublons)
    final List<String> joinedColumns = new ArrayList<>(
        leftTable.getColumns());
    for (String col : rightTable.getColumns()) {
      if (!joinedColumns.contains(col)) {
        joinedColumns.add(col);
      }
    }

    final List<Map<String, String>> joinedRows = new ArrayList<>();

    // Jointure nested loop
    for (Map<String, String> leftRow : leftTable.getRows()) {
      for (Map<String, String> rightRow : rightTable.getRows()) {
        final String leftValue = leftRow.get(leftColumn);
        final String rightValue = rightRow.get(rightColumn);

        if (leftValue != null && leftValue.equals(rightValue)) {
          final Map<String, String> joinedRow = new LinkedHashMap<>();
          joinedRow.putAll(leftRow);
          joinedRow.putAll(rightRow);
          joinedRows.add(joinedRow);
        }
      }
    }

    return new Table(joinedColumns, joinedRows);
  }
}
