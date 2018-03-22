import controller.JDBCController;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.JDBCConfig;

public class DBConfig {

  private static final Stage stage = new Stage();
  public static final JDBCController jdbsController = new JDBCController();
  public static JDBCConfig jdbcConfig;
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
    if (jdbsController.tryToConnect(jdbcConfig)) {
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
    jdbsController.setJDBCConfig(jdbcConfig);
    stage.close();
    MainWindow.mainWindow.getWindow();
  }

  @FXML
  public void cancelDBConfig() {
    Stage stage = (Stage) cancelConfig.getScene()
                                      .getWindow();
    stage.close();
    MainWindow.mainWindow.getWindow();
  }

  public void getWindow() {
    DBConfig.jdbcConfig = jdbsController.getJdbcConfig();
    try {
      initWindow();
      stage.show();
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

  private void initWindow() throws IOException {
    // Загружаем корневой макет из fxml файла.
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(MainWindow.class.getResource("db_config.fxml"));
    AnchorPane rootLayout = loader.load();
    Scene dbConfig = new Scene(rootLayout);
    // Отображаем сцену, содержащую корневой макет.
    stage.setTitle("db config");
    stage.setScene(dbConfig);
  }

  static {
    stage.initModality(Modality.APPLICATION_MODAL);
  }

}
