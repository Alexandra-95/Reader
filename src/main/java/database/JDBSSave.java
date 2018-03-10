package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class JDBSSave {
    private String driver;
    private String url;
    private String userName;
    private String password;
    private String tableName;
    private List<String[]> lines;
    private Connection conn = null;

    public JDBSSave(List<String[]> lines, String driver, String url, String userName, String password, String tableName) {
        this.lines = lines;
        this.driver = driver;
        this.url = url;
        this.userName = userName;
        this.password = password;
        this.tableName = tableName;
    }

    public void createTable() {
        String[] nameColumns = lines.get(0);
        startConnection();
        Statement stmt;
        try {
            stmt = conn.createStatement();
            StringBuilder sql = new StringBuilder("CREATE TABLE " + tableName + " (");
            for (int i = 0; i < nameColumns.length; i++) {
                sql.append(i == nameColumns.length - 1 ?
                        nameColumns[i] + " text);" :
                        nameColumns[i] + " text, ");
            }
            stmt.executeUpdate(sql.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnection();
    }

    public void save() {
        startConnection();
        Statement stmt;
        try {
            stmt = conn.createStatement();
            int count = 1;
            lines.remove(0);
            for (String[] line : lines) {
                StringBuilder sql = new StringBuilder("INSERT INTO ");
                sql.append(tableName).append(" VALUES (");
                for (int i = 0; i < line.length; i++) {
                    sql.append(i == line.length - 1 ?
                            "'" + line[i] + "'" + ");" :
                            "'" + line[i] + "'" + ", ");
                }
                stmt.executeUpdate(sql.toString());
                System.out.println(count++);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnection();
    }

    private void startConnection() {
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, userName, password);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void closeConnection() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
