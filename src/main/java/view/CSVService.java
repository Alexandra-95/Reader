package view;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import model.CSVConfig;

public class CSVService {

  private static final CSVConfig csvConfig = new CSVConfig();

  public static List<String[]> getLines() {
    return lines;
  }

  private static List<String[]> lines = new ArrayList<>();
  public static long numberStrError;

  public void setCSVConfig(String path, String lineSeparator, String tableName) {
    csvConfig.setPath(path);
    csvConfig.setLineSeparator(lineSeparator);
    csvConfig.setTableName(tableName);
  }

  public void setCSVConfig(String path, String lineSeparator) {
    csvConfig.setPath(path);
    csvConfig.setLineSeparator(lineSeparator);
  }

  public void read() {
    numberStrError = 0;
    List<String[]> result = new ArrayList<>();
    lines.clear();
    int length = 0;
    int countWords = 8;
    int lastIndex = 1;
    try (BufferedReader bufferedReader = new BufferedReader(new FileReader(csvConfig.getPath()))) {
      String line;
      while ((line = bufferedReader.readLine()) != null) {
        String[] dataSet = line.split(csvConfig.getLineSeparator());
        if (length == 0) {
          countWords = line.length() - line.replace(csvConfig.getLineSeparator(), "")
                                           .length();
          length = dataSet.length;
        }
        if (dataSet.length != length) {
          if (line.charAt(line.length() - 1) == csvConfig.getLineSeparator()
                                                         .charAt(0)) {
            dataSet = (line + " ").split(csvConfig.getLineSeparator());
          } else if (line.charAt(0) == csvConfig.getLineSeparator()
                                                .charAt(0)) {
            dataSet = (" " + line).split(csvConfig.getLineSeparator());
          } else {
            numberStrError = lastIndex + 1;
            break;
          }
        }
        if (countWords == 0) {
          numberStrError = -1;
          break;
        }
        result.add(dataSet);
        lastIndex++;
      }
    } catch (IOException exception) {
      exception.printStackTrace();
    }
    lines.addAll(result);
  }
}
