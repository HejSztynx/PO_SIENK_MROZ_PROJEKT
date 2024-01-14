package agh.proj.model.interfaces;

import agh.proj.model.Animal;
import agh.proj.model.MapDirection;
import agh.proj.model.Vector2d;

public interface WorldMap extends MoveValidator {
    Vector2d moveValidator(WorldElement element, Vector2d newPosition);

    WorldElement objectAt(Vector2d position);

    void move(Animal animal);

    void place(WorldElement element, Vector2d position);

    boolean isOccupied(Vector2d position);

    Vector2d getBounds();
}
