package agh.proj.model;

import agh.proj.model.interfaces.WorldElement;
import agh.proj.model.interfaces.WorldMap;

import java.util.HashMap;
import java.util.Map;

public class Globe implements WorldMap {
    private final int width;
    private final int height;
    private final Map<Vector2d, Animal> animals = new HashMap<>();

    public Globe(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public Vector2d getBounds() {
        return new Vector2d(width - 1, height - 1);
    }

    @Override
    public Vector2d moveValidator(Animal animal, Vector2d newPosition) {
        if (isInBounds(newPosition)) return newPosition;
        int x = newPosition.getX();
        int y = newPosition.getY();

        if (x == -1) {
            x = width - 1;
        }
        else if (x == width) {
            x = 0;
        }

        if (y == -1) {
            y = 0;
            animal.setOrientation(MapDirection.NORTH);
        }
        else if (y == height) {
            y = height - 1;
            animal.setOrientation(MapDirection.SOUTH);
        }
        return new Vector2d(x, y);
    }

    public boolean isInBounds(Vector2d position) {
        int x = position.getX();
        int y = position.getY();
        return y >= 0 && y < height && x >= 0 && x < width;
    }

    @Override
    public WorldElement objectAt(Vector2d position) {
        return animals.get(position);
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return objectAt(position) != null;
    }

    @Override
    public void move(Animal animal) {
        animals.remove(animal.getPosition());
        animal.move(this);
        animals.put(animal.getPosition(), animal);
    }

    @Override
    public void place(Animal animal, Vector2d position) {
        if (isInBounds(position)) animals.put(position, animal);
        else System.out.println("CANNOT PLACE AT " + position);
    }
}
