package agh.proj.model.interfaces;

import agh.proj.model.Animal;
import agh.proj.model.Vector2d;

public interface MoveValidator {
    Vector2d moveValidator(Animal animal, Vector2d position);
}
