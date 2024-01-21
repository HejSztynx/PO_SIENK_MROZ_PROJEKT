package agh.proj.model.interfaces;

import agh.proj.model.Animal;
import agh.proj.model.Vector2d;

import java.util.ArrayList;

public interface WorldMap extends MoveValidator {
    Vector2d moveValidator(Animal animal, Vector2d newPosition);

    WorldElement objectAt(Vector2d position);

    void move(Animal animal);

    void place(Animal animal, Vector2d position);

    boolean isOccupied(Vector2d position);

    Vector2d getBounds();

    String biomeColor(int x, int y);

    Animal getAnimal(int n);

    int getAnimalCount();
}
