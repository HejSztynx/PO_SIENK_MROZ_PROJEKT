package agh.proj.model;

import agh.proj.model.interfaces.MoveValidator;
import agh.proj.model.interfaces.WorldElement;

public class Animal implements WorldElement {
    private final Parameters worldParameters;
    private Vector2d position;
    private MapDirection orientation;
    private int currentOrientationIndex;
    private int energy;
    private int age = 0;
    private int noOfChildren = 0;
    private final Genotype genotype;

    public Animal(Vector2d position, Genotype genotype, Parameters worldParameters) {
        this.position = position;
        this.energy = worldParameters.initialEnergy;
        this.genotype = genotype;
        orientation = MapDirection.NORTH;
        this.worldParameters = worldParameters;
        currentOrientationIndex = -1;
    }

    public Animal(Vector2d position, Parameters worldParameters) {
        this(position, Genotype.randomGenotype(worldParameters.genotypeLength), worldParameters);
    }

    public Vector2d getPosition() {
        return position;
    }
    public void setPosition(Vector2d position) {
        this.position = position;
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
        return "A";
    }

    public void move(MoveValidator moveValidator) {
        currentOrientationIndex = age % genotype.getLength();
        int orientationChange = genotype.getRawGenotype()[currentOrientationIndex];
        int newOrientation = (orientation.toNumber() + orientationChange) % 8;
        setOrientation(MapDirection.getOrientations()[newOrientation]);

        setPosition(moveValidator.moveValidator(this, orientation.transform(position)));

        age++;
        energy--;
        System.out.println(this);
    }

    public void eat() {
        energy += worldParameters.consumedPlantEnergy;
    }

    public void getsChild() {
        noOfChildren++;
    }

    public boolean canBreed() {
        return energy >= worldParameters.breedNeededEnergy;
    }

    public static Animal breed(Animal animal1, Animal animal2) {
        animal1.getsChild();
        animal2.getsChild();
        return new Animal(animal1.getPosition(), Genotype.cross(animal1, animal2, animal1.worldParameters), animal1.worldParameters);
    }
}
