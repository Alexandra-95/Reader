import controller.CSVController;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class MainWindow extends javafx.application.Application implements Initializable {

  private static Stage primaryStage = new Stage();

  private static final DBConfig dbConfig = new DBConfig();

  public static final MainWindow mainWindow = new MainWindow();

  private static final TableExistsInformation tableExist = new TableExistsInformation();

  private static final CSVController csvController = new CSVController();

  public static final InsertProgress progress = new InsertProgress();

  @FXML
  private Label chooseFileArea;

  @FXML
  private ChoiceBox lineSeparator;

  @FXML
  private TextField tableName;

  @FXML
  private Label configurationStatus;

  @FXML
  private Label csvStatus;

  @FXML
  private Label separatorStatus;

  @FXML
  private Label tableNameStatus;

  @FXML
  private Label readFileStatus;

  @Override
  public void start(Stage primaryStage) throws Exception {
    getWindow();
  }

  public static void setScene(Stage stage){
    primaryStage = stage;
  }

  public void getWindow() {
    primaryStage.show();
  }

  static {
    // Загружаем корневой макет из fxml файла.
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(MainWindow.class.getResource("main_window.fxml"));
    AnchorPane rootLayout = null;
    try {
      rootLayout = loader.load();
    } catch (IOException e) {
      e.printStackTrace();
    }
    // Отображаем сцену, содержащую корневой макет.
    Scene mainWindow = new Scene(Objects.requireNonNull(rootLayout));
    primaryStage.setTitle("main page");
    primaryStage.setScene(mainWindow);
  }

  @FXML
  private void dbConfig() {
    dbConfig.getWindow();
  }

  @FXML
  private void readFile() {
    if (chooseFileArea.getText()
                      .equals("")) {
      csvStatus.setText("Не задан файл.");
      csvStatus.setTextFill(Color.RED);
    } else {
      csvStatus.setText("");
      if (lineSeparator.getValue() == null) {
        separatorStatus.setText("Разделители не заданы.");
        separatorStatus.setTextFill(Color.RED);
      } else {
        separatorStatus.setText("");
        readFileStatus.setText("In progress...");
        readFileStatus.setTextFill(Color.GREEN);
        csvController.setCSVConfig(chooseFileArea.getText(), (String) lineSeparator.getValue());
        csvController.read();
        if (!csvController.getError()
                          .equals("")) {
          readFileStatus.setText(csvController.getError());
          readFileStatus.setTextFill(Color.RED);
        } else {
          readFileStatus.setText("Файл успешно считан");
          readFileStatus.setTextFill(Color.GREEN);
        }
      }
    }

  }

  @FXML
  private void startConvertation() {
    readFile();
    if (!DBConfig.jdbsController.tryToConnect(DBConfig.jdbsController.getJdbcConfig())) {
      configurationStatus.setText("Соединение не установлено.");
      configurationStatus.setTextFill(Color.RED);
    } else {
      configurationStatus.setText("");
    }
    if (tableName.getText()
                 .equals("")) {
      tableNameStatus.setText("Название таблицы не задано.");
      tableNameStatus.setTextFill(Color.RED);
    } else {
      tableNameStatus.setText("");
    }
    if (chooseFileArea.getText()
                      .equals("")) {
      csvStatus.setText("Не задан файл.");
      csvStatus.setTextFill(Color.RED);
    } else {
      csvStatus.setText("");
    }
    if (lineSeparator.getValue() == null) {
      separatorStatus.setText("Разделители не заданы.");
      separatorStatus.setTextFill(Color.RED);
    } else {
      separatorStatus.setText("");
    }
    if (configurationStatus.getText()
                           .equals("") &&
        tableNameStatus.getText()
                       .equals("") &&
        csvStatus.getText()
                 .equals("") &&
        separatorStatus.getText()
                       .equals("") &&
        readFileStatus.getText()
                      .equals("Файл успешно считан")) {
      DBConfig.jdbsController.setTableName(tableName.getText());
      if (DBConfig.jdbsController.checkIfExistTable()){
        tableExist.getWindow();
      } else {
        DBConfig.jdbsController.setLines(CSVController.getLines());
        DBConfig.jdbsController.createNewTable();
        MainWindow.progress.getWindow(new Stage());
      }
    }
  }

  @FXML
  private void chooseFile() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.getExtensionFilters()
               .addAll(
                   new ExtensionFilter("CSV Files", "*.csv"));
    fileChooser.setTitle("Open Resource File");
    File selectedFile = fileChooser.showOpenDialog(primaryStage);
    if (selectedFile != null) {
      chooseFileArea.setText(selectedFile.getAbsolutePath());
    }
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {

  }
}
