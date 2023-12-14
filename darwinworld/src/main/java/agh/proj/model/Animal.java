package agh.proj.model;

public class Animal {
    private final Parameters worldParameters;
    private Vector2d position;
    private MapDirection orientation;
    private int currentOrientationIndex;
    private int energy;
    private final Genotype genotype;

    public Animal(Vector2d position, Genotype genotype, Parameters worldParameters) {
        this.position = position;
        this.energy = worldParameters.initialEnergy;
        this.genotype = genotype;
        orientation = MapDirection.NORTH;
        this.worldParameters = worldParameters;
        currentOrientationIndex = -1;
    }

    public Vector2d getPosition() {
        return position;
    }

    public int getEnergy() {
        return energy;
    }

    public Genotype getGenotype() {
        return genotype;
    }

    public void setPosition(Vector2d position) {
        this.position = position;
    }

    public void setOrientation(MapDirection orientation) {
        this.orientation = orientation;
    }

    @Override
    public String toString() {
        return genotype + " --- " + position + " --- " + orientation + " --- ENERGY = " + energy;
    }

    public void move() {
        currentOrientationIndex++;
        currentOrientationIndex %= genotype.getLength();
        setOrientation(MapDirection.getOrientations()[genotype.getRawGenotype()[currentOrientationIndex]]);
        setPosition(orientation.transform(position));
        energy--;
        System.out.println(this);
    }

    public void eat() {
        energy += worldParameters.consumedPlantEnergy;
    }

    public boolean canBreed() {
        return energy >= worldParameters.breedNeededEnergy;
    }

    public static Animal breed(Animal animal1, Animal animal2) {
        return new Animal(animal1.getPosition(), Genotype.cross(animal1, animal2, animal1.worldParameters), animal1.worldParameters);
    }
}
