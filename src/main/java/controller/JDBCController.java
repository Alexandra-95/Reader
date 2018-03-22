package controller;

import java.util.List;
import view.JDBCService;

public class JDBCController {

  private static final JDBCService jdbcService = new JDBCService();

  public void setJDBCConfig(String driver, String url, String userName, String password) {
    jdbcService.setJDBCConfig(driver, url, userName, password);
  }

  public void setLines(List<String[]> lines) {
    jdbcService.setLines(lines);
  }

  public void setTableName(String tableName) {
    jdbcService.setTableName(tableName);
  }

  public void dropTable() {
    jdbcService.dropTable();
  }

  public void createTable() {
    jdbcService.createTable();
  }

  public void save() {
    jdbcService.save();
  }
}
