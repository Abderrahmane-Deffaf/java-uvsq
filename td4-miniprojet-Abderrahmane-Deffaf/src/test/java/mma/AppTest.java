package mma;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import mma.algtojson.BuildJsonFromAlg;
import mma.algtojson.BuildPlan;
import mma.algtojson.BuildTables;
import mma.csv.CsvReader;
import mma.csv.CsvWriter;
import mma.io.AlgFileProcessor;
import mma.io.JsonFileProcessor;
import mma.jsonexecution.ExecuteQuery;
import mma.model.Table;
import mma.operators.JoinOperator;
import mma.operators.ProjectOperator;
import mma.operators.ScanOperator;
import mma.operators.SelectOperator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Unit test for simple App.
 */
public class AppTest {

    @TempDir
    Path tempDir;

    /**
     * Test BuildTables parsing.
     */
    @Test
    public void testBuildTablesParser() {
        final BuildTables builder = new BuildTables();
        final String input = "users: users.csv\norders: orders.csv";
        final String result = builder.parse(input);

        assertTrue(result.contains("\"users\": \"users.csv\""));
        assertTrue(result.contains("\"orders\": \"orders.csv\""));
    }

    /**
     * Test BuildTables with invalid format.
     */
    @Test
    public void testBuildTablesInvalidFormat() {
        final BuildTables builder = new BuildTables();
        final String input = "invalid line without colon";

        assertThrows(IllegalArgumentException.class, () -> {
            builder.parse(input);
        });
    }

    /**
     * Test Table creation and basic operations.
     */
    @Test
    public void testTableCreation() {
        final List<String> columns = List.of("id", "name", "age");
        final Table table = new Table(columns);

        assertEquals(3, table.getColumns().size());
        assertEquals(0, table.size());
        assertTrue(table.isEmpty());
    }

    /**
     * Test Table addRow.
     */
    @Test
    public void testTableAddRow() {
        final List<String> columns = List.of("id", "name");
        final Table table = new Table(columns);

        final Map<String, String> row = new LinkedHashMap<>();
        row.put("id", "1");
        row.put("name", "Alice");

        table.addRow(row);

        assertEquals(1, table.size());
        assertFalse(table.isEmpty());
    }

    /**
     * Test Table projection.
     */
    @Test
    public void testTableProjection() {
        final List<String> columns = List.of("id", "name", "age");
        final Table table = new Table(columns);

        final Map<String, String> row = new LinkedHashMap<>();
        row.put("id", "1");
        row.put("name", "Alice");
        row.put("age", "22");
        table.addRow(row);

        final Table projected = table.project(List.of("name"));

        assertEquals(1, projected.getColumns().size());
        assertEquals("name", projected.getColumns().get(0));
        assertEquals(1, projected.size());
    }

    /**
     * Test CSV Reader and Writer.
     */
    @Test
    public void testCsvReadWrite() throws IOException {
        final Path csvFile = tempDir.resolve("test.csv");

        // Write
        final List<String> columns = List.of("id", "name");
        final Table table = new Table(columns);

        final Map<String, String> row1 = new LinkedHashMap<>();
        row1.put("id", "1");
        row1.put("name", "Alice");
        table.addRow(row1);

        final Map<String, String> row2 = new LinkedHashMap<>();
        row2.put("id", "2");
        row2.put("name", "Bob");
        table.addRow(row2);

        CsvWriter.write(table, csvFile);
        assertTrue(Files.exists(csvFile));

        // Read
        final Table readTable = CsvReader.read(csvFile);
        assertEquals(2, readTable.getColumns().size());
        assertEquals(2, readTable.size());
    }

    /**
     * Test CSV Reader with empty file.
     */
    @Test
    public void testCsvReaderEmptyFile() throws IOException {
        final Path csvFile = tempDir.resolve("empty.csv");
        Files.createFile(csvFile);

        assertThrows(IOException.class, () -> {
            CsvReader.read(csvFile);
        });
    }

    /**
     * Test ScanOperator.
     */
    @Test
    public void testScanOperator() throws IOException {
        final Path csvFile = tempDir.resolve("users.csv");
        Files.writeString(csvFile, "id,name\n1,Alice\n2,Bob");

        final ScanOperator scan = new ScanOperator(csvFile);
        final Table result = scan.execute();

        assertNotNull(result);
        assertEquals(2, result.getColumns().size());
        assertEquals(2, result.size());
    }

    /**
     * Test ProjectOperator.
     */
    @Test
    public void testProjectOperator() throws IOException {
        final Path csvFile = tempDir.resolve("users.csv");
        Files.writeString(csvFile, "id,name,age\n1,Alice,22\n2,Bob,30");

        final ScanOperator scan = new ScanOperator(csvFile);
        final ProjectOperator project = new ProjectOperator(
            scan, List.of("name"));
        final Table result = project.execute();

        assertEquals(1, result.getColumns().size());
        assertEquals("name", result.getColumns().get(0));
        assertEquals(2, result.size());
    }

    /**
     * Test SelectOperator.
     */
    @Test
    public void testSelectOperator() throws IOException {
        final Path csvFile = tempDir.resolve("users.csv");
        Files.writeString(csvFile, "id,name,age\n1,Alice,22\n2,Bob,30");

        final ScanOperator scan = new ScanOperator(csvFile);
        final SelectOperator select = new SelectOperator(
            scan, row -> Integer.parseInt(row.get("age")) < 25);
        final Table result = select.execute();

        assertEquals(1, result.size());
        assertEquals("Alice", result.getRows().get(0).get("name"));
    }

    /**
     * Test JoinOperator.
     */
    @Test
    public void testJoinOperator() throws IOException {
        final Path usersFile = tempDir.resolve("users.csv");
        Files.writeString(usersFile, "id,name\n1,Alice\n2,Bob");

        final Path ordersFile = tempDir.resolve("orders.csv");
        Files.writeString(ordersFile, "order_id,user_id,amount\n101,1,50");

        final ScanOperator scanUsers = new ScanOperator(usersFile);
        final ScanOperator scanOrders = new ScanOperator(ordersFile);
        final JoinOperator join = new JoinOperator(
            scanUsers, scanOrders, "id", "user_id");
        final Table result = join.execute();

        assertEquals(1, result.size());
        assertTrue(result.getColumns().contains("name"));
        assertTrue(result.getColumns().contains("amount"));
    }

    /**
     * Test empty table operations.
     */
    @Test
    public void testEmptyTableOperations() {
        final List<String> columns = List.of("id", "name");
        final Table emptyTable = new Table(columns);

        assertTrue(emptyTable.isEmpty());
        assertEquals(0, emptyTable.size());

        final Table projected = emptyTable.project(List.of("id"));
        assertTrue(projected.isEmpty());
    }

    /**
     * Test table with multiple rows.
     */
    @Test
    public void testTableMultipleRows() {
        final List<String> columns = List.of("id", "value");
        final Table table = new Table(columns);

        for (int i = 1; i <= 10; i++) {
            final Map<String, String> row = new LinkedHashMap<>();
            row.put("id", String.valueOf(i));
            row.put("value", "val" + i);
            table.addRow(row);
        }

        assertEquals(10, table.size());
        assertFalse(table.isEmpty());
    }

    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {
        assertTrue(true);
    }

    // ================== BuildJsonFromAlg Tests ==================

    /**
     * Test BuildJsonFromAlg basic parsing.
     */
    @Test
    public void testBuildJsonFromAlgBasic() throws IOException {
        final Path usersFile = tempDir.resolve("users.csv");
        Files.writeString(usersFile, "id,name\n1,Alice");
        
        final Path algFile = tempDir.resolve("test.alg");
        Files.writeString(algFile, "users: users.csv\n\nR = SCAN users");

        final BuildJsonFromAlg builder = new BuildJsonFromAlg(algFile.toString());
        final String json = builder.parse();

        assertTrue(json.contains("tables"));
        assertTrue(json.contains("plan"));
    }

    // ================== BuildPlan Tests ==================

    /**
     * Test BuildPlan basic functionality.
     */
    @Test
    public void testBuildPlanBasic() {
        final BuildPlan builder = new BuildPlan();
        final String input = "R = SCAN users";
        final String result = builder.parse(input);

        assertNotNull(result);
        assertTrue(result.length() > 0);
    }

    // ================== ExecuteQuery Tests ==================

    /**
     * Test ExecuteQuery with nested JSON format.
     */
    @Test
    public void testExecuteQueryNested() throws IOException {
        final Path usersFile = tempDir.resolve("users.csv");
        Files.writeString(usersFile, "id,name\n1,Alice\n2,Bob");

        final String jsonQuery = "{\n"
            + "  \"tables\": {\"users\": \"users.csv\"},\n"
            + "  \"plan\": {\n"
            + "    \"type\": \"SCAN\",\n"
            + "    \"table\": \"users\"\n"
            + "  }\n"
            + "}";

        final Path jsonFile = tempDir.resolve("query.json");
        Files.writeString(jsonFile, jsonQuery);

        final ExecuteQuery executor = new ExecuteQuery(jsonFile);
        final Table result = executor.execute();

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    // ================== File Processor Tests ==================

    /**
     * Test JsonFileProcessor with nested format.
     */
    @Test
    public void testJsonFileProcessorNested() throws IOException {
        final Path usersFile = tempDir.resolve("users.csv");
        Files.writeString(usersFile, "id,name\n1,Alice\n2,Bob");

        final String jsonQuery = "{\n"
            + "  \"tables\": {\"users\": \"users.csv\"},\n"
            + "  \"plan\": {\n"
            + "    \"type\": \"SCAN\",\n"
            + "    \"table\": \"users\"\n"
            + "  }\n"
            + "}";

        final Path jsonFile = tempDir.resolve("query.json");
        Files.writeString(jsonFile, jsonQuery);

        final JsonFileProcessor processor = new JsonFileProcessor();
        processor.process(jsonFile.toString());

        final Path outputFile = tempDir.resolve("results").resolve("output.csv");
        assertTrue(Files.exists(outputFile));
    }

    /**
     * Test AlgFileProcessor basic functionality.
     */
    @Test
    public void testAlgFileProcessorBasic() throws IOException {
        final Path usersFile = tempDir.resolve("users.csv");
        Files.writeString(usersFile, "id,name\n1,Alice\n2,Bob");

        final Path algFile = tempDir.resolve("test.alg");
        final String algContent = "users: users.csv\n\nR = SCAN users";
        Files.writeString(algFile, algContent);

        final AlgFileProcessor processor = new AlgFileProcessor();
        processor.process(algFile.toString());

        // Verify JSON file was created
        final Path jsonFile = tempDir.resolve("test.json");
        assertTrue(Files.exists(jsonFile));
    }

    /**
     * Test complete pipeline.
     */
    @Test
    public void testCompletePipeline() throws IOException {
        final Path usersFile = tempDir.resolve("users.csv");
        Files.writeString(usersFile, "id,name\n1,Alice\n2,Bob");

        final String jsonQuery = "{\n"
            + "  \"tables\": {\"users\": \"users.csv\"},\n"
            + "  \"plan\": {\n"
            + "    \"type\": \"SCAN\",\n"
            + "    \"table\": \"users\"\n"
            + "  }\n"
            + "}";

        final Path jsonFile = tempDir.resolve("query.json");
        Files.writeString(jsonFile, jsonQuery);

        final ExecuteQuery executor = new ExecuteQuery(jsonFile);
        final Table result = executor.execute();

        assertEquals(2, result.size());
    }
}

