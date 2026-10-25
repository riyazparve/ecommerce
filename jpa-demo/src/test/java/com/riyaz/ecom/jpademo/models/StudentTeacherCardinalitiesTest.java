package com.riyaz.ecom.jpademo.models;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class StudentTeacherCardinalitiesTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void testIfTableWithNameStudentIsCreated() {
        String tableName = "STUDENT";
        String sql = "SELECT COUNT(*) FROM information_schema.tables WHERE table_name = ?";

        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{tableName}, Integer.class);

        assertTrue(count != null && count > 0, "Table with name STUDENT does not exist !");
    }

    @Test
    public void testIfTableWithNameTeacherIsCreated() {
        String tableName = "TEACHER";
        String sql = "SELECT COUNT(*) FROM information_schema.tables WHERE table_name = ?";

        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{tableName}, Integer.class);

        assertTrue(count != null && count > 0, "Table with name TEACHER does not exist !");
    }

    @Test
    public void testIfTableWithNameTeacher_RatingIsCreated() {
        String tableName = "TEACHER_RATING";
        String sql = "SELECT COUNT(*) FROM information_schema.tables WHERE table_name = ?";

        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{tableName}, Integer.class);

        assertTrue(count != null && count > 0, "Table with name TEACHER_RATING does not exist !");
    }

    @Test
    public void testIfTableWithNameStudents_TeachersIsCreated() {
        String tableName = "STUDENTS_TEACHERS";
        String sql = "SELECT COUNT(*) FROM information_schema.tables WHERE table_name = ?";

        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{tableName}, Integer.class);

        assertTrue(count != null && count > 0, "Table with name STUDENTS_TEACHERS does not exist !");
    }

    @Test
    public void testColumnNamesOfTeacher_RatingTable() throws SQLException {
        String tableName = "TEACHER_RATING";
        Set<String> expectedColumns = Set.of("RATING", "STUDENT_ID", "TEACHER_ID");

        boolean columnsAreValid = validateColumns(tableName, expectedColumns);

        assertTrue(columnsAreValid, "The table TEACHER_RATING does not contain all expected columns like RATING, STUDENT_ID, TEACHER_ID");
    }

    @Test
    public void testColumnNamesOfStudentTable() throws SQLException {
        String tableName = "STUDENT";
        Set<String> expectedColumns = Set.of("ID" , "NAME");

        boolean columnsAreValid = validateColumns(tableName, expectedColumns);

        assertTrue(columnsAreValid, "The table STUDENT does not contain all expected columns like ID , NAME");
    }

    @Test
    public void testColumnNamesOfTeacherTable() throws SQLException {
        String tableName = "TEACHER";
        Set<String> expectedColumns = Set.of("ID" , "NAME");

        boolean columnsAreValid = validateColumns(tableName, expectedColumns);

        assertTrue(columnsAreValid, "The table TEACHER does not contain all expected columns like  ID , NAME");
    }

    @Test
    public void testColumnNamesOfStudents_TeachersTable() throws SQLException {
        String tableName = "STUDENTS_TEACHERS";
        Set<String> expectedColumns = Set.of("STUDENT_ID", "TEACHER_ID");

        boolean columnsAreValid = validateColumns(tableName, expectedColumns);

        assertTrue(columnsAreValid, "The table STUDENTS_TEACHERS does not contain all expected columns like STUDENT_ID, TEACHER_ID");
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