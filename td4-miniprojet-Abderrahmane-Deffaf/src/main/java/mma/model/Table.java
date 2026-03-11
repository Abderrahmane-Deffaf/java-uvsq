package mma.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Représente une table de données avec colonnes et lignes.
 *
 * @author Abderrahmane Deffaf
 * @version 1.0
 */
public final class Table {
  /**
   * Les colonnes de la table.
   */
  private final List<String> columns;

  /**
   * Les lignes de la table.
   */
  private final List<Map<String, String>> rows;

  /**
   * Constructeur.
   *
   * @param tableColumns Les noms des colonnes
   * @param tableRows Les lignes de données
   */
  public Table(final List<String> tableColumns,
               final List<Map<String, String>> tableRows) {
    this.columns = new ArrayList<>(tableColumns);
    this.rows = new ArrayList<>(tableRows);
  }

  /**
   * Constructeur pour table vide.
   *
   * @param tableColumns Les noms des colonnes
   */
  public Table(final List<String> tableColumns) {
    this(tableColumns, new ArrayList<>());
  }

  /**
   * Obtient les colonnes.
   *
   * @return La liste des colonnes
   */
  public List<String> getColumns() {
    return new ArrayList<>(columns);
  }

  /**
   * Obtient les lignes.
   *
   * @return La liste des lignes
   */
  public List<Map<String, String>> getRows() {
    return new ArrayList<>(rows);
  }

  /**
   * Ajoute une ligne.
   *
   * @param row La ligne à ajouter
   */
  public void addRow(final Map<String, String> row) {
    rows.add(new LinkedHashMap<>(row));
  }

  /**
   * Obtient le nombre de lignes.
   *
   * @return Le nombre de lignes
   */
  public int size() {
    return rows.size();
  }

  /**
   * Vérifie si la table est vide.
   *
   * @return true si vide
   */
  public boolean isEmpty() {
    return rows.isEmpty();
  }

  /**
   * Projette certaines colonnes.
   *
   * @param selectedColumns Les colonnes à garder
   * @return Une nouvelle table avec les colonnes sélectionnées
   */
  public Table project(final List<String> selectedColumns) {
    final List<Map<String, String>> projectedRows = rows.stream()
        .map(row -> selectedColumns.stream()
            .collect(Collectors.toMap(
                col -> col,
                col -> row.getOrDefault(col, ""),
                (a, b) -> a,
                LinkedHashMap::new)))
        .collect(Collectors.toList());

    return new Table(selectedColumns, projectedRows);
  }

  @Override
  public String toString() {
    return "Table{columns=" + columns + ", rows=" + rows.size() + "}";
  }
}
