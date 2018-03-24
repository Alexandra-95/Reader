import controller.JDBCController;
import java.io.IOException;
import javafx.concurrent.WorkerStateEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;

public class InsertProgress {


  @FXML
  private Button done;

  @FXML
  private ProgressBar progressBar = new ProgressBar();

  @FXML
  private void progressDone() {
    Stage stage = (Stage) done.getScene()
                              .getWindow();
    stage.close();
    InitProgram.mainWindow.getWindow();
  }

  public void getWindow(Stage stage) {
    try {
      InitProgram.initWindowInsertProgress(stage);
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

}
