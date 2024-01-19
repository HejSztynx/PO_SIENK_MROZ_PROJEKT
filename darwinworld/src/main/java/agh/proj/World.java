package agh.proj;

import agh.proj.gui.SimulationApp;
import javafx.application.Application;

public class World {
    public static void main(String[] args) {
        System.out.println("--- System started ---");
        Application.launch(SimulationApp.class);
        System.out.println("--- System terminated ---");
    }
}
