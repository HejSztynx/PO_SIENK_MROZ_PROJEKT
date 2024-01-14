package agh.proj.model;

import agh.proj.model.Grass;
import agh.proj.model.Vector2d;
import agh.proj.model.interfaces.BoundsValidator;

public class Biome implements BoundsValidator {
    private Vector2d lowerLeft;
    private Vector2d upperRight;

    @Override
    public boolean boundsValidator(Vector2d position) {
        return ((lowerLeft.getY() <= position.getY() && position.getY() <= upperRight.getY()) && (lowerLeft.getX()<= position.getX() && position.getX()<= upperRight.getX()));
    }
}
