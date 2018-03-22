import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class TableExistsInformation {
    private static final Stage stage = new Stage();

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
