package agh.proj.model;

import java.util.Random;

public class GrassGenerator {
    private final static int chancePlains = 20;
    private final static int chanceJungle = 80;

    public Grass generateGrass(boolean inJungle, int perOfBeingBad, int Energy, Vector2d position) {
        Random random = new Random();
        int checker = chancePlains;
        if (inJungle)
            checker = chanceJungle;
        if (random.nextInt(100) < checker) {
            if (random.nextInt(100) < perOfBeingBad)
                return new Grass(-1 * Energy, position);
            else
                return new Grass(Energy, position);
        } else
            return new Grass(0, new Vector2d(-1, -1));
    }
}
