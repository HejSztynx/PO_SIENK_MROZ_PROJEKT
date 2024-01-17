package agh.proj.model.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.net.URL;
import java.util.ArrayList;

public class CSVReader {
    public ArrayList<String> readFirstColumn(String url) {
        ArrayList<String> firstColumnValues = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(url))) {
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                String[] columns = line.split(",");
                if (columns.length > 0) {
                    firstColumnValues.add(columns[0]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return firstColumnValues;
    }
}
