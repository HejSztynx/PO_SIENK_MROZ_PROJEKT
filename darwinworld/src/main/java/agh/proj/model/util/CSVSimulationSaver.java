package agh.proj.model.util;

import agh.proj.model.Animal;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class CSVSimulationSaver {
    private final File file;
    private final String fileName;
    public CSVSimulationSaver(String nameOfSimulation) throws IOException {
        fileName=nameOfSimulation+".csv";
        file=new File(nameOfSimulation+".csv");
        FileWriter fw=new FileWriter(file);
    }
    public void updateCSV(int day, ArrayList<Animal> records)throws IOException  {
        FileWriter fw=new FileWriter(fileName,true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write("Day "+String.valueOf(day));
        for(Animal animal:records){
            bw.write(";"+animal.getSevingString());
        }
        bw.newLine();
        bw.close();
        fw.close();
    }

}
