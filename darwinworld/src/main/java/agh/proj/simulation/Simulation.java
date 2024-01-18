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
            worldMap.dayMovesAnimal();
            worldMap.dayEating();
            worldMap.dayBreading();
            worldMap.dayGrassGenerator();
            worldMap.addDay();
            System.out.println(worldMap.allDead() + ":");
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
