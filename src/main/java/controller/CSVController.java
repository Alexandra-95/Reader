package controller;

import java.io.File;
import java.util.List;
import javafx.concurrent.Task;
import javafx.scene.control.ProgressBar;
import view.CSVService;

public class CSVController {

  public static final CSVService csvService = new CSVService();

  public void setCSVConfig(String path, String lineSeparator) {
    csvService.setCSVConfig(path, lineSeparator);
  }

  public void setCSVConfig(String path, String lineSeparator, String tableName) {
    csvService.setCSVConfig(path, lineSeparator, tableName);
  }

  public static List<String[]> getLines() {
    return CSVService.getLines();
  }

  public static void setLines(List<String[]> lines) {
    CSVService.setLines(lines);
  }

  public List<String[]> read(){
      return csvService.call();
  }

  public String getError() {
    if (CSVService.numberStrError != -1) {
      if (CSVService.numberStrError == 0) {
        if (getLines().size() < 2) {
          return "Файл пуст";
        } else {
          return "";
        }
      } else {
        return "Ошибка в строке " + CSVService.numberStrError;
      }
    } else {
      return "Не верно заданы разделители";
    }
  }
}
