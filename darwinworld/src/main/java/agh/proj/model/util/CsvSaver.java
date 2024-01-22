package agh.proj.model.util;

import agh.proj.model.Animal;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class CsvSaver {

    public static void saveToCsv(String name, int day, ArrayList<Animal> records) throws IOException {
        File file = new File(name + "_d" + day + ".csv");
        FileWriter fw = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write("Day");
        for(int i=0;i<records.size();i++){
            bw.write(",Animal"+records.get(i).getHisNumber());
        }
        bw.newLine();
        bw.write(day);
        for(int i=0;i<records.size();i++){
            bw.write(","+records.get(i).toSaveString());
        }
        bw.close();
        fw.close();

    }
    public CsvSaver() throws IOException {
    }
}
