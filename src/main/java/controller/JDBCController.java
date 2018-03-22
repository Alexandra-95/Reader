package controller;

import java.util.List;
import model.JDBCConfig;
import view.JDBCService;

public class JDBCController {

  private static final JDBCService jdbcService = new JDBCService();

  public void setJDBCConfig(JDBCConfig jdbcConfig) {
    jdbcService.setJDBCConfig(jdbcConfig
        .getDriver(), jdbcConfig.getUrl(), jdbcConfig.getUserName(), jdbcConfig.getPassword());
  }

  public void setLines(List<String[]> lines) {
    jdbcService.setLines(lines);
  }

  public void setTableName(String tableName) {
    jdbcService.setTableName(tableName);
  }

  public JDBCConfig getJdbcConfig(){
    return jdbcService.getJdbcConfig();
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

  public boolean tryToConnect(JDBCConfig jdbcConfig) {
    return false;
  }
}
