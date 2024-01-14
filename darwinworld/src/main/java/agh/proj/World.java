package agh.proj;

import agh.proj.model.*;
import agh.proj.model.interfaces.WorldMap;
import agh.proj.model.util.MapVisualizer;
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
                0, 1, MutationVariant.FULLY_RANDOM, 5, BehaviorVariant.FULL_PREDESTINATION);

        Animal animal1 = new Animal(new Vector2d(1, 1), new Genotype(new int[]{0, 1, 0, 0, 0}), parameters.getInitialEnergy(), 1);
        System.out.println(animal1);

        WorldMap map = new Globe(5, 5,true);
        MapVisualizer mapVisualizer = new MapVisualizer(map);
        System.out.println(mapVisualizer.draw());

        map.place(animal1, animal1.getPosition());
        System.out.println(mapVisualizer.draw());

        for (int i = 0; i < 40; i++) {
            map.move(animal1);
            System.out.println(mapVisualizer.draw());
        }

//        for(int i = 0; i < 6; i++) {
//            if (i == 5) animal1.eat();
//            animal1.move(map);
//            System.out.println(animal1.getPosition());
//        }

//        Animal animal2 = new Animal(new Vector2d(0, 0), new Genotype(new int[]{4, 3, 2, 1, 0}), parameters);
//        if (animal2.canBreed() && animal1.canBreed()) {
//            Animal child = Animal.breed(animal1, animal2);
//            System.out.println("-------------\n--- CHILD ---\n" + child);
//        }
    }
}
