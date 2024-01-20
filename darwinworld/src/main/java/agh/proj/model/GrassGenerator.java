package agh.proj.model;

import java.util.Random;

public class GrassGenerator {

    public Grass generateGrass(int perOfBeingBad, int Energy, Vector2d position) {
        Random random = new Random();
        if (random.nextInt(100) < perOfBeingBad)
            return new Grass(-1 * Energy, position);
        else
            return new Grass(Energy, position);
    }
}
