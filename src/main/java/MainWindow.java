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

  private void disableAll() {
    tableName.setDisable(true);
    lineSeparator.setDisable(true);
    choose_file.setDisable(true);
    clickDBConfig.setDisable(true);
    clickReadFile.setDisable(true);
  }

  private void enableAll() {
    tableName.setDisable(false);
    lineSeparator.setDisable(false);
    choose_file.setDisable(false);
    clickDBConfig.setDisable(false);
    clickReadFile.setDisable(false);
  }

  @Override
  public void start(Stage primaryStage) {
    getWindow();
  }

  public void getWindow() {
    InitProgram.primaryStage.show();
  }

  @FXML
  private void dbConfig() {
    InitProgram.dbConfig.getWindow();
  }

  @FXML
  private void readFile() {
    disableAll();
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
        InitProgram.csvController.setCSVConfig(chooseFileArea.getText(),
            (String) lineSeparator.getValue());
        //initProgressBar();
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
    enableAll();
  }

  //  private void initProgressBar() {
  //    progressFileBar.progressProperty()
  //                   .unbind();
  //    progressFileBar.progressProperty()
  //                   .bind(CSVController.csvService.progressProperty());
  //    readFileStatus.textProperty()
  //                  .unbind();
  //    readFileStatus.textProperty()
  //                  .bind(CSVController.csvService.messageProperty());
  //    CSVController.csvService.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED,
  //        event -> {
  //          List<String[]> lines = CSVController.csvService.getValue();
  //          CSVController.setLines(lines);
  //          readFileStatus.textProperty()
  //                        .unbind();
  //          if (!csvController.getError()
  //                            .equals("")) {
  //            readFileStatus.setText(csvController.getError());
  //            readFileStatus.setTextFill(Color.RED);
  //          } else {
  //            readFileStatus.setText("Файл успешно считан");
  //            readFileStatus.setTextFill(Color.GREEN);
  //          }
  //
  //        });
  //    Thread thread = new Thread(CSVController.csvService);
  //    thread.setDaemon(true);
  //    thread.start();
  //  }

  @FXML
  private void startConvertation() {
    disableAll();
    readFile();
    if (!InitProgram.jdbsController.tryToConnect(InitProgram.jdbsController.getJdbcConfig())) {
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
      InitProgram.jdbsController.setTableName(tableName.getText());
      InitProgram.jdbsController.setLines(CSVController.getLines());
      if (InitProgram.jdbsController.checkIfExistTable()) {
        InitProgram.tableExist.getWindow();
      } else {
        InitProgram.jdbsController.createNewTable();
        InitProgram.progress.getWindow(new Stage());
      }
    }
    enableAll();
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
}
