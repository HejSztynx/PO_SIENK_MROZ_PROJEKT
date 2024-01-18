package agh.proj;

import agh.proj.gui.SimulationApp;
import agh.proj.model.*;
import agh.proj.model.variants.BehaviorVariant;
import agh.proj.model.variants.FoliageVariant;
import agh.proj.model.variants.MapVariant;
import agh.proj.model.variants.MutationVariant;
import agh.proj.simulation.Simulation;
import javafx.application.Application;

public class World {
    public static void main(String[] args) {
        System.out.println("--- System started ---");

        Application.launch(SimulationApp.class);

        System.out.println("--- System terminated ---");
    }
}
