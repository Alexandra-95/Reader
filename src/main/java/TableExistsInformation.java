import controller.CSVController;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public class TableExistsInformation {

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
      InitProgram.jdbsController.setLines(CSVController.getLines());
      InitProgram.jdbsController.setTableToRewrite(chooseWhatToDo.getValue()
                                                                 .equals(
                                                                     "Удалить существующие данные"));
      InitProgram.jdbsController.insertData();
      InitProgram.progress.getWindow(InitProgram.stageInsertProgress);
    }
  }

  @FXML
  private void cancelExtraInf() {

  }

  public void getWindow() {
    try {
      InitProgram.initWindowTableExistInf();
      InitProgram.stageInsertProgress.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
