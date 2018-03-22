package view;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import model.JDBCConfig;

public class JDBCService {

  private static final JDBCConfig jdbcConfig = new JDBCConfig();
  private List<String[]> lines;
  private String tableName;
  private Connection conn = null;

  public void setJDBCConfig(String driver, String url, String userName, String password) {
    jdbcConfig.setUserName(userName);
    jdbcConfig.setPassword(password);
    jdbcConfig.setDriver(driver);
    jdbcConfig.setUrl(url);
  }

  public void setLines(List<String[]> lines) {
    this.lines = lines;
  }

  public void setTableName(String tableName) {
    this.tableName = tableName;
  }

  public JDBCConfig getJdbcConfig(){
    return jdbcConfig;
  }

  private void queryRunner(String sql) {
    Statement stmt;
    try {
      stmt = conn.createStatement();
      stmt.executeUpdate(sql);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void dropTable() {
    startConnection();
    queryRunner("DROP TABLE " + tableName);
    closeConnection();
  }

  public void createTable() {
    startConnection();
    String[] nameColumns = lines.get(0);
    StringBuilder sql = new StringBuilder("CREATE TABLE " + tableName + " (");
    for (int i = 0; i < nameColumns.length; i++) {
      sql.append(i == nameColumns.length - 1 ?
          nameColumns[i] + " text);" :
          nameColumns[i] + " text, ");
    }
    queryRunner(sql.toString());
    closeConnection();
  }

  public void save() {
    startConnection();
    StringBuilder result = new StringBuilder();
    lines.remove(0);
    for (String[] line : lines) {
      StringBuilder sql = new StringBuilder("INSERT INTO ");
      sql.append(tableName)
         .append(" VALUES (");
      for (int i = 0; i < line.length; i++) {
        sql.append(i == line.length - 1 ?
            "'" + line[i] + "'" + "); /n" :
            "'" + line[i] + "'" + ", ");
      }
      queryRunner(sql.toString());
    }
    closeConnection();
  }

  private void startConnection() {
    try {
      Class.forName(jdbcConfig.getDriver());
      conn = DriverManager.getConnection(jdbcConfig.getUrl(), jdbcConfig.getUserName(),
          jdbcConfig.getPassword());
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
