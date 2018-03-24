import controller.JDBCController;
import java.io.IOException;
import javafx.concurrent.WorkerStateEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class InsertProgress {

  private static Stage stage;

  @FXML
  private Button done;

  @FXML
  private ProgressBar progressBar = new ProgressBar();

  @FXML
  private void progressDone() {
    Stage stage = (Stage) done.getScene()
                              .getWindow();
    stage.close();
    MainWindow.mainWindow.getWindow();
  }

  public void getWindow(Stage stage) {
    try {
      InsertProgress.stage = stage;
      initWindow();
      stage.show();
      initProgressBar();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void initProgressBar() {
    progressBar.setProgress(0);
    progressBar.progressProperty()
               .unbind();
    progressBar.progressProperty()
               .bind(JDBCController.jdbcService.progressProperty());
    JDBCController.jdbcService.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED,
        event -> {
          Object p = JDBCController.jdbcService.getValue();
          System.out.println(p.toString());
        });
    Thread thread = new Thread(JDBCController.jdbcService);
    thread.setDaemon(true);
    thread.start();
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
