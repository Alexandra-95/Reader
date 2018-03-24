import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.JDBCConfig;

public class DBConfig {

  private static JDBCConfig jdbcConfig;
  @FXML
  private TextField userName = new TextField();

  @FXML
  private PasswordField password = new PasswordField();

  @FXML
  private TextField urlDB = new TextField();

  @FXML
  ChoiceBox chooseDMS;

  @FXML
  private Button cancelConfig;

  @FXML
  private Label resultConnection;

  @FXML
  public void testConnection() {
    jdbcConfig.setUserName(userName.getText());
    jdbcConfig.setUrl(urlDB.getText());
    jdbcConfig.setPassword(password.getText());
    jdbcConfig.setDriver((String) chooseDMS.getValue());
    if (InitProgram.jdbsController.tryToConnect(jdbcConfig)) {
      resultConnection.setText("Соединение установлено.");
      resultConnection.setTextFill(Color.GREEN);
    } else {
      resultConnection.setText("Соединение не установлено.");
      resultConnection.setTextFill(Color.RED);
    }
  }

  @FXML
  public void okDBConfig() {
    jdbcConfig.setUserName(userName.getText());
    jdbcConfig.setUrl(urlDB.getText());
    jdbcConfig.setPassword(password.getText());
    jdbcConfig.setDriver((String) chooseDMS.getValue());
    Stage stage = (Stage) cancelConfig.getScene()
                                      .getWindow();
    InitProgram.jdbsController.setJDBCConfig(jdbcConfig);
    stage.close();
    InitProgram.mainWindow.getWindow();
  }

  @FXML
  public void cancelDBConfig() {
    Stage stage = (Stage) cancelConfig.getScene()
                                      .getWindow();
    stage.close();
    InitProgram.mainWindow.getWindow();
  }

  public void getWindow() {
    DBConfig.jdbcConfig = InitProgram.jdbsController.getJdbcConfig();
    try {
      InitProgram.initWindowDBConfig();
      InitProgram.stageDBConfig.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @FXML
  public void initialize() {
    if (jdbcConfig.getUserName() != null) {
      userName.setText(jdbcConfig.getUserName());
    }
    if (jdbcConfig.getPassword() != null) {
      password.setText(jdbcConfig.getPassword());
    }
    if (jdbcConfig.getUrl() != null) {
      urlDB.setText(jdbcConfig.getUrl());
    }
    if (jdbcConfig.getDriver() != null) {
      chooseDMS.setValue(jdbcConfig.getDriver());
    }
  }


}
