import controller.CSVController;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class TableExistsInformation {

  private static final Stage stage = new Stage();

  @FXML
  private ChoiceBox chooseWhatToDo;
  @FXML
  private Label statusExtraInfo;

  @FXML
  private void okExtraInf() {
    if (chooseWhatToDo.getValue() == null) {
      statusExtraInfo.setText("Выберите нужное действие.");
      statusExtraInfo.setTextFill(Color.RED);
    } else {
      DBConfig.jdbsController.setLines(CSVController.getLines());
      DBConfig.jdbsController.setTableToRewrite(chooseWhatToDo.getValue()
                                                              .equals("Удалить существующие данные")
          ?
          true : false);
      DBConfig.jdbsController.insertData();
      MainWindow.progress.getWindow(stage);
    }
  }

  @FXML
  private void cancelExtraInf() {

  }

  public void getWindow() {
    try {
      initWindow();
      stage.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void initWindow() throws IOException {
    // Загружаем корневой макет из fxml файла.
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(MainWindow.class.getResource("table_exists_information.fxml"));
    AnchorPane rootLayout = loader.load();
    Scene dbConfig = new Scene(rootLayout);
    // Отображаем сцену, содержащую корневой макет.
    stage.setTitle("Нужно больше информации");
    stage.setScene(dbConfig);
  }

  static {
    stage.initModality(Modality.APPLICATION_MODAL);
  }
}
