package agh.proj.model.interfaces;

import agh.proj.model.Vector2d;

public interface WorldElement {
    Vector2d getPosition();

    String getImage();

    String getEnergyImage(int neededBreedEnergy);

    boolean isTracked(int n);

    int getRotation();
}
