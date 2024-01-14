package agh.proj.model;

import agh.proj.model.interfaces.BoundsValidator;
import agh.proj.model.interfaces.WorldElement;
import agh.proj.model.interfaces.WorldMap;

import java.util.HashMap;
import java.util.Map;

public class Globe implements WorldMap, BoundsValidator {
    private final Vector2d lowerLeft = new Vector2d(0, 0);
    private final Vector2d upperRight;

    private final Map<Vector2d, Animal> animals = new HashMap<>();

    public Globe(int width, int height) {
        upperRight = new Vector2d(width, height);
    }

    @Override
    public boolean boundsValidator(Vector2d position) {
        int x = position.getX();
        int y = position.getY();
        return y >= 0 && y < upperRight.getY() && x >= 0 && x < upperRight.getX();
    }

    @Override
    public Vector2d moveValidator(WorldElement element, Vector2d newPosition) {
        return null;
    }

    @Override
    public Vector2d getBounds() {
        return upperRight;
    }

    @Override
    public Vector2d moveValidator(Animal animal, Vector2d newPosition) {
        if (boundsValidator(newPosition)) return newPosition;
        int x = newPosition.getX();
        int y = newPosition.getY();

        if (x == -1) {
            x = upperRight.getX();

        } else if (x == upperRight.getX() + 1) {
            x = 0;

        }

        if (y == -1) {
            y = 0;
            animal.setOrientation(MapDirection.getOrientations()[(animal.getOrientation().toNumber()+2)%8]);
        } else if (y == upperRight.getY() + 1) {
            y = upperRight.getY();
            animal.setOrientation(MapDirection.getOrientations()[(animal.getOrientation().toNumber()+2)%8]);
        }
        return new Vector2d(x, y);
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


    //To jest do poprawy musimy wtawiać elemtent bno wstawiamy też grassy a po co nam do tego osobna funkcja chyba że zrobimy place animal i place grass
    @Override
    public void place(WorldElement element, Vector2d position) {
        if (boundsValidator(position)) animals.put(position, (Animal) element);
        else System.out.println("CANNOT PLACE AT " + position);
    }
}
