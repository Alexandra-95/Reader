package view;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import model.CSVConfig;

public class CSVService {
  private static final CSVConfig csvConfig = new CSVConfig();
  public static List<String[]> lines = new ArrayList<>();
  public static long numberStrError;

  public void setCSVConfig(String path, String lineSeparator, String tableName) {
    csvConfig.setPath(path);
    csvConfig.setLineSeparator(lineSeparator);
    csvConfig.setTableName(tableName);
  }

  public void read() {
    List<String[]> result = new ArrayList<>();
    int length = 0;
    int lastIndex = 1;
    try (BufferedReader bufferedReader = new BufferedReader(new FileReader(csvConfig.getPath()))) {
      String line;
      while ((line = bufferedReader.readLine()) != null) {
        String[] dateSet = line.split(csvConfig.getLineSeparator());
        if (length == 0) {
          length = dateSet.length;
        }
        if (dateSet.length != length) {
          numberStrError = lastIndex + 1;
          result.clear();
          break;
        }
        result.add(dateSet);
        lastIndex++;
      }
    } catch (IOException exception) {
      exception.printStackTrace();
    }
    lines.addAll(result);
  }
}
