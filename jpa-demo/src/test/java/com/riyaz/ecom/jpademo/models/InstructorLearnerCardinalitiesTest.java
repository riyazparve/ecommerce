package com.riyaz.ecom.jpademo.models;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class InstructorLearnerCardinalitiesTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void testIfTableWithNameBatchIsCreated() {
        String tableName = "BATCH";
        String sql = "SELECT COUNT(*) FROM information_schema.tables WHERE table_name = ?";

        List<Integer> result = jdbcTemplate.queryForList(sql, Integer.class, tableName);
        Integer count = result.isEmpty() ? 0 : result.get(0);

        assertTrue(count != null && count > 0, "Table with name BATCH does not exist !");
    }

    @Test
    public void testIfTableWithNameBatches_ClassesIsCreated() {
        String tableName = "BATCHES_CLASSES";
        String sql = "SELECT COUNT(*) FROM information_schema.tables WHERE table_name = ?";

        List<Integer> result = jdbcTemplate.queryForList(sql, Integer.class, tableName);
        Integer count = result.isEmpty() ? 0 : result.get(0);

        assertTrue(count != null && count > 0, "Table with name BATCHES_CLASSES does not exist !");
    }

    @Test
    public void testIfTableWithNameClassIsCreated() {
        String tableName = "CLASS";
        String sql = "SELECT COUNT(*) FROM information_schema.tables WHERE table_name = ?";

        List<Integer> result = jdbcTemplate.queryForList(sql, Integer.class, tableName);
        Integer count = result.isEmpty() ? 0 : result.get(0);

        assertTrue(count != null && count > 0, "Table with name CLASS does not exist !");
    }

    @Test
    public void testIfTableWithNameInstructorIsCreated() {
        String tableName = "INSTRUCTOR";
        String sql = "SELECT COUNT(*) FROM information_schema.tables WHERE table_name = ?";

        List<Integer> result = jdbcTemplate.queryForList(sql, Integer.class, tableName);
        Integer count = result.isEmpty() ? 0 : result.get(0);

        assertTrue(count != null && count > 0, "Table with name INSTRUCTOR does not exist !");
    }

    @Test
    public void testIfTableWithNameInstructors_BatchesIsCreated() {
        String tableName = "INSTRUCTORS_BATCHES";
        String sql = "SELECT COUNT(*) FROM information_schema.tables WHERE table_name = ?";

        List<Integer> result = jdbcTemplate.queryForList(sql, Integer.class, tableName);
        Integer count = result.isEmpty() ? 0 : result.get(0);

        assertTrue(count != null && count > 0, "Table with name INSTRUCTORS_BATCHES does not exist !");
    }

    @Test
    public void testIfTableWithNameLearnerIsCreated() {
        String tableName = "LEARNER";
        String sql = "SELECT COUNT(*) FROM information_schema.tables WHERE table_name = ?";

        List<Integer> result = jdbcTemplate.queryForList(sql, Integer.class, tableName);
        Integer count = result.isEmpty() ? 0 : result.get(0);

        assertTrue(count != null && count > 0, "Table with name LEARNER does not exist !");
    }

    @Test
    public void testIfTableWithNameLearners_Previous_BatchesIsCreated() {
        String tableName = "LEARNERS_PREVIOUS_BATCHES";
        String sql = "SELECT COUNT(*) FROM information_schema.tables WHERE table_name = ?";

        List<Integer> result = jdbcTemplate.queryForList(sql, Integer.class, tableName);
        Integer count = result.isEmpty() ? 0 : result.get(0);

        assertTrue(count != null && count > 0, "Table with name LEARNERS_PREVIOUS_BATCHES does not exist !");
    }



    @Test
    public void testColumnNamesOfBatchTable() throws SQLException {
        String tableName = "BATCH";
        Set<String> expectedColumns = Set.of("ID", "NAME");

        boolean columnsAreValid = validateColumns(tableName, expectedColumns);

        assertTrue(columnsAreValid, "The table BATCH does not contain all expected columns like ID, NAME");
    }

    @Test
    public void testColumnNamesOfBatches_ClassesTable() throws SQLException {
        String tableName = "BATCHES_CLASSES";
        Set<String> expectedColumns = Set.of("BATCH_ID", "CLASS_ID");

        boolean columnsAreValid = validateColumns(tableName, expectedColumns);

        assertTrue(columnsAreValid, "The table BATCHES_CLASSES does not contain all expected columns like BATCH_ID, CLASS_ID");
    }

    @Test
    public void testColumnNamesOfClassTable() throws SQLException {
        String tableName = "CLASS";
        Set<String> expectedColumns = Set.of("CURRENT_INSTRUCTOR_ID", "ID", "TOPIC");

        boolean columnsAreValid = validateColumns(tableName, expectedColumns);

        assertTrue(columnsAreValid, "The table CLASS does not contain all expected columns like CURRENT_INSTRUCTOR_ID, ID, TOPIC");
    }

    @Test
    public void testColumnNamesOfInstructorTable() throws SQLException {
        String tableName = "INSTRUCTOR";
        Set<String> expectedColumns = Set.of("ID", "NAME", "EMAIL");

        boolean columnsAreValid = validateColumns(tableName, expectedColumns);

        assertTrue(columnsAreValid, "The table INSTRUCTOR does not contain all expected columns like ID, NAME, EMAIL");
    }

    @Test
    public void testColumnNamesOfInstructors_BatchesTable() throws SQLException {
        String tableName = "INSTRUCTORS_BATCHES";
        Set<String> expectedColumns = Set.of("BATCH_ID", "INSTRUCTOR_ID");

        boolean columnsAreValid = validateColumns(tableName, expectedColumns);

        assertTrue(columnsAreValid, "The table INSTRUCTORS_BATCHES  does not contain all expected columns like BATCH_ID, INSTRUCTOR_ID");
    }

    @Test
    public void testColumnNamesOfLearnerTable() throws SQLException {
        String tableName = "LEARNER";
        Set<String> expectedColumns = Set.of("CURRENT_BATCH_ID", "ID", "EMAIL", "NAME");

        boolean columnsAreValid = validateColumns(tableName, expectedColumns);

        assertTrue(columnsAreValid, "The table LEARNER does not contain all expected columns like CURRENT_BATCH_ID, ID, EMAIL, NAME");
    }

    @Test
    public void testColumnNamesOfLearners_Previous_BatchesTable() throws SQLException {
        String tableName = "LEARNERS_PREVIOUS_BATCHES";
        Set<String> expectedColumns = Set.of("LEARNER_ID" , "PREVIOUS_BATCH_ID");

        boolean columnsAreValid = validateColumns(tableName, expectedColumns);

        assertTrue(columnsAreValid, "The table LEARNERS_PREVIOUS_BATCHES does not contain all expected columns like LEARNER_ID , PREVIOUS_BATCH_ID");
    }



    private Set<String> getColumnNames(String tableName) throws SQLException {
        Set<String> columns = new HashSet<>();
        DatabaseMetaData metaData = jdbcTemplate.getDataSource().getConnection().getMetaData();

        try (ResultSet rs = metaData.getColumns(null, null, tableName, null)) {
            while (rs.next()) {
                columns.add(rs.getString("COLUMN_NAME"));
            }
        }
        return columns;
    }

    private boolean validateColumns(String tableName, Set<String> expectedColumns) throws SQLException {
        Set<String> actualColumns = getColumnNames(tableName);
        return actualColumns.containsAll(expectedColumns);
    }

}
