package agh.proj.simulation;

import agh.proj.model.Globe;
import agh.proj.model.Parameters;
import agh.proj.model.interfaces.WorldMap;
import agh.proj.model.util.MapVisualizer;

public class Simulation implements Runnable {
    private Globe worldMap;
    private MapVisualizer mapVisualizer;
    public Simulation(Globe worldMap){
        this.worldMap=worldMap;
        mapVisualizer=new MapVisualizer(worldMap);
    }
    @Override
    public void run() {
        while(worldMap.allDead()!=0)
        {
            worldMap.dayClaener();
            worldMap.dayMovesAnimal();
            worldMap.dayEating();
            worldMap.dayBreading();
            worldMap.dayGrassGenerator();
            worldMap.addDay();
            System.out.println(worldMap.allDead()+":");
            System.out.println(mapVisualizer.draw());
        }
        System.out.println("Sim End");
    }
}
