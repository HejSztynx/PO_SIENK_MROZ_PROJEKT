package agh.proj.model;

import agh.proj.model.interfaces.WorldElement;

public class Grass implements WorldElement {
    private int energy;
    private Vector2d position;

    public Grass(int energy, Vector2d position) {
        this.position = position;
        this.energy = energy;
    }

    public Vector2d getPosition() {
        return position;
    }

    public int getEnergy() {
        return energy;
    }

    public String toString() {
        if (energy > 0)
            return "*";
        else
            return "x";
    }

    public String getImage() {
        if (energy < 0) return "purplegrass.png";
        return "grass.png";
    }
}
