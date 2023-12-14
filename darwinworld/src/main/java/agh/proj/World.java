package agh.proj;

import agh.proj.model.Animal;
import agh.proj.model.Genotype;
import agh.proj.model.Parameters;
import agh.proj.model.Vector2d;
import agh.proj.model.variants.BehaviorVariant;
import agh.proj.model.variants.FoliageVariant;
import agh.proj.model.variants.MapVariant;
import agh.proj.model.variants.MutationVariant;

public class World {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        Parameters parameters = new Parameters(10, 10, MapVariant.GLOBE,
                20, 5, 5, FoliageVariant.VERDANT_EQUATOR,
                5, 50, 10, 10,
                0, 1, MutationVariant.SWAP, 5, BehaviorVariant.FULL_PREDESTINATION);

        Animal animal1 = new Animal(new Vector2d(0, 0), new Genotype(new int[]{0, 2, 3, 5, 6}), parameters);
        System.out.println(animal1);

        for(int i = 0; i < 40; i++) {
            if (i == 5) animal1.eat();
            animal1.move();
        }

        Animal animal2 = new Animal(new Vector2d(0, 0), new Genotype(new int[]{4, 3, 2, 1, 0}), parameters);
        if (animal2.canBreed() && animal1.canBreed()) {
            Animal child = Animal.breed(animal1, animal2);
            System.out.println("-------------\n--- CHILD ---\n" + child);
        }
    }
}
