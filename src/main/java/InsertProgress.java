import controller.JDBCController;
import java.io.IOException;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.concurrent.WorkerStateEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;
import view.JDBCService;

public class InsertProgress {

  @FXML
  private Button done;

  @FXML
  private ProgressBar progressBar = new ProgressBar();

  @FXML
  private Label supportText = new Label();

  @FXML
  private Label changedProgress;
  private static final Task task = JDBCController.jdbcService;


  @FXML
  private void progressDone() {
    Stage stage = (Stage) done.getScene()
                              .getWindow();
    stage.close();
    InitProgram.mainWindow.getWindow();
  }

  void getWindow() {

    try {
      InitProgram.initWindowInsertProgress();
      InitProgram.stageInsertProgress.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void initProgressBar() {
    done.setDisable(true);
    progressBar.progressProperty()
               .unbind();
    progressBar.progressProperty()
               .bind(task.progressProperty());
    changedProgress.textProperty()
                   .unbind();
    changedProgress.textProperty()
                   .bind(task.messageProperty());

    JDBCController.jdbcService.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED,
        event -> {
          task.getValue();
          supportText.setText("Запись прошла успешно.");
          done.setDisable(false);
        });
    Thread thread = new Thread(task);
    thread.setDaemon(true);
    thread.start();
  }

  @FXML
  public void initialize() {
      //task.run();
    //done.disableProperty().bind(Bindings.not(task.runningProperty()));
    supportText.setText("Идет процесс записи файла в базу данных. Пожалуйста, подождите.....");
    initProgressBar();
  }
}
