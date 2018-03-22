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

public class DBConfig {

  private static final Stage stage = new Stage();
  private static final JDBCController jdbsController = new JDBCController();
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
    if (jdbsController.tryToConnect(dbEntity)) {
      resultConnection.setText("Соединение установлено.");
      resultConnection.setTextFill(Color.GREEN);
    } else {
      resultConnection.setText("Соединение не установлено.");
      resultConnection.setTextFill(Color.RED);
    }
  }

  @FXML
  public void okDBConfig() {
    jdbsController.setJDBCConfig((String) chooseDMS.getValue(), urlDB.getText(), userName.getText(),
        password.getText());
    Stage stage = (Stage) cancelConfig.getScene()
                                      .getWindow();
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
    try {
      initWindow();
      stage.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @FXML
  public void initialize() {
    if (dbEntity.getUserName() != null) {
      userName.setText(dbEntity.getUserName());
    }
    if (dbEntity.getPassword() != null) {
      password.setText(dbEntity.getPassword());
    }
    if (dbEntity.getUrlDB() != null) {
      urlDB.setText(dbEntity.getUrlDB());
    }
    if (dbEntity.getChooseDMS() != null) {
      chooseDMS.setValue(dbEntity.getChooseDMS());
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
