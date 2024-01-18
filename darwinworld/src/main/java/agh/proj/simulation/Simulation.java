package agh.proj.simulation;

import agh.proj.model.Globe;
import agh.proj.model.util.MapVisualizer;

public class Simulation implements Runnable {
    private Globe worldMap;
    private MapVisualizer mapVisualizer;

    public Simulation(Globe worldMap) {
        this.worldMap = worldMap;
        mapVisualizer = new MapVisualizer(worldMap);
    }

    @Override
    public void run() {
        System.out.println(mapVisualizer.draw());
        while (worldMap.allDead() != 0) {
            worldMap.dayCleaner();
            // System.out.println(worldMap.allDead() + ":DeadAnimals");
            worldMap.dayMovesAnimal();
            worldMap.dayEating();
            //System.out.println(worldMap.avgEnergy() + ":Avg Energy");
            worldMap.dayBreading();
            //System.out.println(worldMap.avgChildren() + ":Avg Children");
            worldMap.dayGrassGenerator();
            //System.out.println(worldMap.numberOfEmptySpaces() + ":EmptySpaces");
            worldMap.addDay();
            //System.out.println(worldMap.getDay() + ":Day");
            //System.out.println(worldMap.mostPopularGenom() + ":Genom");
            System.out.println(mapVisualizer.draw());
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {

            }
        }
        System.out.println("Sim End");
    }
}
