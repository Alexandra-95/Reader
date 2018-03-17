import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Application extends javafx.application.Application {

  private Stage primaryStage;

  public static void main(String[] args) {
    //    CSVReader csvReader = new CSVReader("C:/hotels.csv");
    //    JDBSSave jdbsSave = new JDBSSave(csvReader.lines,
    //        "org.postgresql.Driver",
    //        "jdbc:postgresql://localhost:5432/test",
    //        "postgres",
    //        "Vfhrbpbr",
    //        "hotels");
    //    //jdbsSave.createTable();
    //    jdbsSave.save();
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    this.primaryStage = primaryStage;
    this.primaryStage.setTitle("AddressApp");

    initRootLayout();
  }

  private void initRootLayout() {
    try {
      // Загружаем корневой макет из fxml файла.
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(Application.class.getResource("application.fxml"));
      AnchorPane rootLayout = loader.load();
      // Отображаем сцену, содержащую корневой макет.
      Scene scene = new Scene(rootLayout);
      primaryStage.setScene(scene);
      primaryStage.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
