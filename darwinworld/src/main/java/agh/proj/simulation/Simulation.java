package agh.proj.simulation;

import agh.proj.model.Globe;
import agh.proj.model.interfaces.MapChangeListener;
import agh.proj.model.util.MapVisualizer;

import java.util.ArrayList;

public class Simulation implements Runnable {
    private Globe worldMap;
    private MapVisualizer mapVisualizer;
    private final ArrayList<MapChangeListener> subscribers = new ArrayList<>();

    public Simulation(Globe worldMap) {
        this.worldMap = worldMap;
        mapVisualizer = new MapVisualizer(worldMap);
    }

    public void register(MapChangeListener subscriber) {
        subscribers.add(subscriber);
    }

    @Override
    public void run() {
        System.out.println(mapVisualizer.draw());
        callSubscribers();
        while (worldMap.allDead() != 0) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
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
            System.out.println(worldMap.mostPopularGenom() + ":Genom");
            System.out.println(mapVisualizer.draw());
            callSubscribers();
        }
        System.out.println("Sim End");
    }

    private void callSubscribers() {
        for (MapChangeListener subscriber : subscribers) {
            subscriber.mapChanged();
        }
    }
}
