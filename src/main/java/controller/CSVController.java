package controller;

import view.CSVService;

public class CSVController {

  private static final CSVService csvService = new CSVService();

  public void setCSVConfig(String path, String lineSeparator, String tableName) {
    csvService.setCSVConfig(path, lineSeparator, tableName);
  }

  public void read() {
    csvService.read();
  }

  public String getError() {
    return CSVService.numberStrError == 0 ? "" : "Ошибка в строке " + CSVService.numberStrError;
  }
}
