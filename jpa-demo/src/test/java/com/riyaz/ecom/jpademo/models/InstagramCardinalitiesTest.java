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
public class InstagramCardinalitiesTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void testIfTableWithNameInstagramCommentIsCreated() {
        String tableName = "INSTAGRAM_COMMENT";
        String sql = "SELECT COUNT(*) FROM information_schema.tables WHERE table_name = ?";

        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{tableName}, Integer.class);

        assertTrue(count != null && count > 0, "Table with name INSTAGRAM_COMMENT does not exist !");
    }

    @Test
    public void testIfTableWithNameInstagramPageIsCreated() {
        String tableName = "INSTAGRAM_PAGE";
        String sql = "SELECT COUNT(*) FROM information_schema.tables WHERE table_name = ?";

        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{tableName}, Integer.class);

        assertTrue(count != null && count > 0, "Table with name INSTAGRAM_PAGE does not exist !");
    }

    @Test
    public void testIfTableWithNameInstagramPostIsCreated() {
        String tableName = "INSTAGRAM_POST";
        String sql = "SELECT COUNT(*) FROM information_schema.tables WHERE table_name = ?";

        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{tableName}, Integer.class);

        assertTrue(count != null && count > 0, "Table with name INSTAGRAM_POST does not exist !");
    }

    @Test
    public void testIfTableWithNameInstagramUserIsCreated() {
        String tableName = "INSTAGRAM_USER";
        String sql = "SELECT COUNT(*) FROM information_schema.tables WHERE table_name = ?";

        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{tableName}, Integer.class);

        assertTrue(count != null && count > 0, "Table with name INSTAGRAM_USER does not exist !");
    }

    @Test
    public void testIfTableWithNameInstagramLikeIsCreated() {
        String tableName = "INSTAGRAM_LIKE";
        String sql = "SELECT COUNT(*) FROM information_schema.tables WHERE table_name = ?";

        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{tableName}, Integer.class);

        assertTrue(count != null && count > 0, "Table with name INSTAGRAM_LIKE does not exist !");
    }

    @Test
    public void testColumnNamesOfInstagramLikeTable() throws SQLException {
        String tableName = "INSTAGRAM_LIKE";
        Set<String> expectedColumns = Set.of("ID", "POST_ID", "USER_ID");

        boolean columnsAreValid = validateColumns(tableName, expectedColumns);

        assertTrue(columnsAreValid, "The table INSTAGRAM_LIKE does not contain all expected columns like ID, POST_ID, USER_ID");
    }

    @Test
    public void testColumnNamesOfInstagramCommentTable() throws SQLException {
        String tableName = "INSTAGRAM_COMMENT";
        Set<String> expectedColumns = Set.of("ID", "POST_ID", "USER_ID", "TEXT");

        boolean columnsAreValid = validateColumns(tableName, expectedColumns);

        assertTrue(columnsAreValid, "The table INSTAGRAM_COMMENT does not contain all expected columns like ID, POST_ID, USER_ID, TEXT");
    }

    @Test
    public void testColumnNamesOfInstagramPageTable() throws SQLException {
        String tableName = "INSTAGRAM_PAGE";
        Set<String> expectedColumns = Set.of("CREATOR_ID", "ID");

        boolean columnsAreValid = validateColumns(tableName, expectedColumns);

        assertTrue(columnsAreValid, "The table INSTAGRAM_PAGE does not contain all expected columns like CREATOR_ID, ID");
    }

    @Test
    public void testColumnNamesOfInstagramUserTable() throws SQLException {
        String tableName = "INSTAGRAM_USER";
        Set<String> expectedColumns = Set.of("ID", "NAME");

        boolean columnsAreValid = validateColumns(tableName, expectedColumns);

        assertTrue(columnsAreValid, "The table INSTAGRAM_USER does not contain all expected columns like ID, NAME");
    }

    @Test
    public void testColumnNamesOfInstagramPostTable() throws SQLException {
        String tableName = "INSTAGRAM_POST";
        Set<String> expectedColumns = Set.of("ID", "INSTAGRAM_PAGE_ID", "CONTENT");

        boolean columnsAreValid = validateColumns(tableName, expectedColumns);

        assertTrue(columnsAreValid, "The table INSTAGRAM_POST does not contain all expected columns like ID, INSTAGRAM_PAGE_ID, CONTENT");
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
