package controller;

import java.util.List;
import view.CSVService;

public class CSVController {

  private static final CSVService csvService = new CSVService();

  public void setCSVConfig(String path, String lineSeparator) {
    csvService.setCSVConfig(path, lineSeparator);
  }

  public void setCSVConfig(String path, String lineSeparator, String tableName) {
    csvService.setCSVConfig(path, lineSeparator, tableName);
  }

  public void read() {
    csvService.read();
  }

  public static List<String[]> getLines() {
    return CSVService.getLines();
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
