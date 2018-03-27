import controller.CSVController;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class MainWindow extends javafx.application.Application implements Initializable {

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

  @FXML
  private Button choose_file;

  @FXML
  private Button clickDBConfig;

  @FXML
  private Button clickReadFile;

  //  @FXML
  //  private void disableAll() {
  //    tableName.setDisable(true);
  //    lineSeparator.setDisable(true);
  //    choose_file.setDisable(true);
  //    clickDBConfig.setDisable(true);
  //    clickReadFile.setDisable(true);
  //  }
  //
  //  @FXML
  //  private void enableAll() {
  //    tableName.setDisable(false);
  //    lineSeparator.setDisable(false);
  //    choose_file.setDisable(false);
  //    clickDBConfig.setDisable(false);
  //    clickReadFile.setDisable(false);
  //  }
  @Override
  public void start(Stage primaryStage) {
    getWindow();
  }

  public void getWindow() {
    InitProgram.primaryStage.show();
  }

  @FXML
  private void dbConfig() {
    configurationStatus.setText("");
    InitProgram.dbConfig.getWindow();
  }

  @FXML
  private void readFile() {
    if (getCountToCheckPossibilityReadFile()) {
      InitProgram.csvController.setCSVConfig(chooseFileArea.getText(),
          (String) lineSeparator.getValue());
      CSVController.setLines(InitProgram.csvController.read());
      if (!InitProgram.csvController.getError()
                                    .equals("")) {
        readFileStatus.setText(InitProgram.csvController.getError());
        readFileStatus.setTextFill(Color.RED);
      } else {
        readFileStatus.setText("Файл успешно считан");
        readFileStatus.setTextFill(Color.GREEN);
      }
    }
  }

  @FXML
  private void startConvertation() {
    readFile();
    if (aCheckThatFieldsReadyToConvertation() &&
        readFileStatus.getText()
                      .equals("Файл успешно считан")) {
      InitProgram.jdbsController.setTableName(tableName.getText());
      InitProgram.jdbsController.setLines(CSVController.getLines());
      if (InitProgram.jdbsController.checkIfExistTable()) {
        InitProgram.tableExist.getWindow();
      } else {
        InitProgram.jdbsController.createNewTable();
        InitProgram.progress.getWindow();
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
    File selectedFile = fileChooser.showOpenDialog(InitProgram.primaryStage);
    if (selectedFile != null) {
      chooseFileArea.setText(selectedFile.getAbsolutePath());
    }
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {

  }

  private boolean getCountToCheckPossibilityReadFile() {
    int count = 0;
    if (lineSeparator.getValue() == null) {
      separatorStatus.setText("Разделители не заданы.");
      separatorStatus.setTextFill(Color.RED);
      count++;
    } else {
      separatorStatus.setText("");
    }
    if (chooseFileArea.getText()
                      .equals("")) {
      csvStatus.setText("Не задан файл.");
      csvStatus.setTextFill(Color.RED);
      count++;
    } else {
      csvStatus.setText("");
    }
    return count == 0;
  }

  private boolean aCheckThatFieldsReadyToConvertation() {
    int count = 0;
    if (!InitProgram.jdbsController.tryToConnect(InitProgram.jdbsController.getJdbcConfig())) {
      configurationStatus.setText("Соединение не установлено.");
      configurationStatus.setTextFill(Color.RED);
      count++;
    } else {
      configurationStatus.setText("");
    }
    if (tableName.getText()
                 .equals("")) {
      tableNameStatus.setText("Название таблицы не задано.");
      tableNameStatus.setTextFill(Color.RED);
      count++;
    } else {
      tableNameStatus.setText("");
    }
    return count == 0 && getCountToCheckPossibilityReadFile();
  }

}
