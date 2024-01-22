package agh.proj.simulation;

import agh.proj.model.Globe;
import agh.proj.model.interfaces.MapChangeListener;
import agh.proj.model.util.CSVSimulationSaver;
import agh.proj.model.util.MapVisualizer;

import java.io.IOException;
import java.util.ArrayList;

public class Simulation implements Runnable {
    private Globe worldMap;
    private MapVisualizer mapVisualizer;
    private CSVSimulationSaver csvSaver;
    private final ArrayList<MapChangeListener> subscribers = new ArrayList<>();
    private boolean pause = false;
    public Simulation(Globe worldMap,String csvName) throws IOException {
        this.worldMap = worldMap;
        mapVisualizer = new MapVisualizer(worldMap);
        csvSaver=new CSVSimulationSaver(csvName);
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }

    public void register(MapChangeListener subscriber) {
        subscribers.add(subscriber);
    }

    @Override
    public void run() {
        System.out.println(mapVisualizer.draw());
        callSubscribers();
        while (worldMap.allDead() != 0) {

            while (pause) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            worldMap.dayCleaner();
            System.out.println(worldMap.allDead() + ":DeadAnimals");
            worldMap.dayMovesAnimal();
            worldMap.dayEating();
            System.out.println(worldMap.avgEnergy() + ":Avg Energy");
            worldMap.dayBreading();
            System.out.println(worldMap.avgChildren() + ":Avg Children");
            worldMap.dayGrassGenerator();
            System.out.println(worldMap.numberOfEmptySpaces() + ":EmptySpaces");
            worldMap.addDay();
            System.out.println(worldMap.getDay() + ":Day");
            System.out.println(worldMap.mostPopularGenome() + ":Genom");
            System.out.println(mapVisualizer.draw());
            callSubscribers();
            try {
                csvSaver.updateCSV(worldMap.getDay(), worldMap.getRecords());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
        System.out.println("Sim End");
    }

    private void callSubscribers() {
        for (MapChangeListener subscriber : subscribers) {
            subscriber.mapChanged();
        }
    }
}
