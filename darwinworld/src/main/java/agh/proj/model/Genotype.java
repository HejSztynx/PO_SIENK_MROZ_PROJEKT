package agh.proj.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class Genotype {
    private final int[] genotype;
    private final int genotypeLength;

    public Genotype(int[] genotype) {
        this.genotype = genotype;
        genotypeLength = genotype.length;
    }

    public int getLength() {
        return genotypeLength;
    }

    public int[] getRawGenotype() {
        return genotype;
    }

    @Override
    public String toString() {
        return Arrays.toString(genotype);
    }

    public static Genotype cross(Animal animal1, Animal animal2, Parameters worldParameters) {
        Animal animalStrong;
        Animal animalWeak;
        if (animal1.getEnergy() >= animal2.getEnergy()) {
            animalStrong = animal1;
            animalWeak = animal2;
        }
        else {
            animalStrong = animal2;
            animalWeak = animal1;
        }

        Random rand = new Random();
        int strongSide = rand.nextInt(2);
        int weakSide = (strongSide + 1) % 2;
        float ratio = (float) animalStrong.getEnergy() / (animalStrong.getEnergy() + animalWeak.getEnergy());
        int genotypeLength = animalStrong.getGenotype().getLength();
        int[] newGenotype = new int[genotypeLength];
        int genotypeSplit = (int) (ratio * genotypeLength);

        if (strongSide == 1) genotypeSplit = genotypeLength - genotypeSplit;

        int[][] genotypes = new int[2][];
        genotypes[strongSide] = animalStrong.getGenotype().getRawGenotype();
        genotypes[weakSide] = animalWeak.getGenotype().getRawGenotype();

        int i = 0;
        while (i < genotypeSplit) {
            newGenotype[i] = genotypes[0][i];
            i++;
        }
        while (i < genotypeLength) {
            newGenotype[i] = genotypes[1][i];
            i++;
        }
        return new Genotype(mutate(newGenotype, worldParameters));
    }

    private static int[] mutate(int[] genotype, Parameters worldParameters) {
        return switch(worldParameters.mutationVariant) {
            case FULLY_RANDOM -> mutateRandom(genotype, worldParameters);
            case SWAP -> mutateSwap(genotype, worldParameters);
        };
    }

    private static int[] mutateRandom(int[] genotype, Parameters worldParameters) {
        int minMutations = worldParameters.minMutations;
        int maxMutations = worldParameters.maxMutations;
        Random random = new Random();
        int numberOfMutations = random.nextInt(minMutations, maxMutations + 1);
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < genotype.length; i++) list.add(i);
        Collections.shuffle(list);
        int[] pickedGens = list.stream().limit(numberOfMutations).mapToInt(Integer::intValue).toArray();
        for (int i : pickedGens) {
            genotype[i] = random.nextInt(8);
        }
        return genotype;
    }

    private static int[] mutateSwap(int[] genotype, Parameters worldParameters) {
        int minMutations = worldParameters.minMutations;
        int maxMutations = worldParameters.maxMutations;
        Random random = new Random();
        int numberOfMutations = random.nextInt(minMutations, maxMutations + 1);
        for (int i = 0; i < numberOfMutations; i++) swapRandomTwo(genotype);
        return genotype;
    }

    private static void swapRandomTwo(int[] genotype) {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < genotype.length; i++) list.add(i);
        Collections.shuffle(list);
        int[] pickedGens = list.stream().limit(2).mapToInt(Integer::intValue).toArray();
        int temp = genotype[pickedGens[1]];
        genotype[pickedGens[1]] = genotype[pickedGens[0]];
        genotype[pickedGens[0]] = temp;
    }

    public static Genotype randomGenotype(int genotypeLength) {
        Random random = new Random();
        int[] genotype = new int[genotypeLength];
        for (int i = 0; i < genotypeLength; i++) {
            genotype[i] = random.nextInt(8);
        }
        return new Genotype(genotype);
    }
}
