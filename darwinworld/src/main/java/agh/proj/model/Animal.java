package agh.proj.model;

import agh.proj.model.interfaces.MoveValidator;
import agh.proj.model.interfaces.WorldElement;
import agh.proj.model.variants.MutationVariant;

public class Animal implements WorldElement {
    private Vector2d position;
    private final int dateOfBirth;
    private MapDirection orientation;
    private int currentOrientationIndex;
    private int energy;
    private int age = 0;
    private int noOfChildren = 0;
    private final Genotype genotype;

    public Animal(Vector2d position, Genotype genotype, int energy, int dateOfBirth) {
        this.position = position;
        this.dateOfBirth = dateOfBirth;
        this.energy = energy;
        this.genotype = genotype;
        orientation = MapDirection.NORTH;
        currentOrientationIndex = -1;
    }

    public Animal(Vector2d position,int energy,int genoTypeLength) {
        this(position, Genotype.randomGenotype(genoTypeLength), energy, 0);
    }

    public Vector2d getPosition() {
        return position;
    }

    public int getEnergy() {
        return energy;
    }

    public int getAge() {
        return age;
    }

    public int getChildrenNo() {
        return noOfChildren;
    }

    public Genotype getGenotype() {
        return genotype;
    }

    public MapDirection getOrientation() {
        return orientation;
    }

    public void setOrientation(MapDirection orientation) {
        this.orientation = orientation;
    }

    public String longToString() {
        return genotype + " --- " + position + " --- " + orientation + " --- ENERGY = " + energy + " --- AGE = " + age;
    }

    @Override
    public String toString() {
        return "A"+orientation.toString();
    }

    public void move(MoveValidator moveValidator) {
        currentOrientationIndex = age % genotype.getLength();
        int orientationChange = genotype.getRawGenotype()[currentOrientationIndex];
        int newOrientation = (orientation.toNumber() + orientationChange) % 8;
        setOrientation(MapDirection.getOrientations()[newOrientation]);

        position = (moveValidator.moveValidator(this, orientation.transform(position)));

        age++;
        energy--;
        System.out.println(this);
    }

    public void eat(Grass grass) {
        energy += grass.getEnergy();
    }

    public void getsChild() {
        noOfChildren++;
    }

    public boolean canBreed(int breedNeededEnergy) {
        return energy >= breedNeededEnergy;
    }

    public static Animal breed(Animal animal1, Animal animal2, int breedEnergy, MutationVariant mutationVariant,int minMutation,int maxMutation) {
        animal1.getsChild();
        animal1.energy-=breedEnergy;
        animal2.getsChild();
        animal2.energy-=breedEnergy;
        return new Animal(animal1.getPosition(), Genotype.cross(animal1, animal2, mutationVariant,minMutation,maxMutation), breedEnergy*2, animal1.dateOfBirth + animal1.age);
    }
}
