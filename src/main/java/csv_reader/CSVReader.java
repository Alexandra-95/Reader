package csv_reader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {
    String path;
    public List<String[]> lines = new ArrayList<>();
    public CSVReader(String path){
        this.path = path;
        read();
    }
    private void read(){
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line=bufferedReader.readLine()) != null){
                lines.add(line.replaceAll("\"", "").split(";"));
            }
        } catch (IOException exception){
            exception.printStackTrace();
        }
    }
}
