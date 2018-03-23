import controller.CSVController;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class InsertProgress {

  private static Stage stage;

  @FXML
  private Button done;

  @FXML
  private void progressDone() {
    Stage stage = (Stage) done.getScene()
                              .getWindow();
    stage.close();
    MainWindow.mainWindow.getWindow();
  }

  public void getWindow(Stage stage) {
    DBConfig.jdbsController.save();
    try {
      InsertProgress.stage = stage;
      initWindow();
      stage.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void initWindow() throws IOException {
    // Загружаем корневой макет из fxml файла.
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(MainWindow.class.getResource("insert_progress.fxml"));
    AnchorPane rootLayout = loader.load();
    Scene dbConfig = new Scene(rootLayout);
    // Отображаем сцену, содержащую корневой макет.
    stage.setTitle("Прогресс записи.");
    stage.setScene(dbConfig);
  }
}
