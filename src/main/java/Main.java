import csv_reader.CSVReader;
import database.JDBSSave;

public class Main {
    public static void main(String[] args) {
        CSVReader csvReader = new CSVReader("C:/hotels.csv");
        JDBSSave jdbsSave = new JDBSSave(csvReader.lines,
                "org.postgresql.Driver",
                "jdbc:postgresql://localhost:5432/test",
                "postgres",
                "Vfhrbpbr",
                "hotels");
        //jdbsSave.createTable();
        jdbsSave.save();
    }
}
