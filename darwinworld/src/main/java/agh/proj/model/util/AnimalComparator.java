package agh.proj.model.util;

import agh.proj.model.Animal;
import java.util.Comparator;
import java.util.Random;

public class AnimalComparator implements Comparator<Animal> {

    @Override
    public int compare(Animal a1, Animal a2) {
        int result = Integer.compare(a1.getEnergy(), a2.getEnergy());
        if (result != 0) {
            return result;
        }
        result = Integer.compare(a1.getAge(), a2.getAge());
        if (result != 0) {
            return result;
        }
        result = Integer.compare(a1.getChildrenNo(), a2.getChildrenNo());
        if (result != 0) {
            return result;
        }

        Random random = new Random();
        result = random.nextInt(2);
        if (result == 1) return 1;
        return -1;
    }
}
