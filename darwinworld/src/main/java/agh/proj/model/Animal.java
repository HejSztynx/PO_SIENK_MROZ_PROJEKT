package agh.proj.model;

import agh.proj.model.interfaces.MoveValidator;
import agh.proj.model.interfaces.WorldElement;
import agh.proj.model.variants.MutationVariant;

public class Animal implements WorldElement {
    private final int dateOfBirth;
    private final Genotype genotype;
    private Vector2d position;
    private MapDirection orientation;
    private int currentOrientationIndex;
    private int energy;
    private int age = 0;
    private int noOfChildren = 0;
    private int numberOfEatedGrass = 0;
    private Animal animalParent1=null;
    private Animal animalParent2=null;
    private int numberOfDescendats=0;
    private boolean alive=true;


    public Animal(Vector2d position, Genotype genotype, int energy, int dateOfBirth) {
        this.position = position;
        this.dateOfBirth = dateOfBirth;
        this.energy = energy;
        this.genotype = genotype;
        orientation = MapDirection.NORTH;
        currentOrientationIndex = -1;
    }

    public Animal(Vector2d position, int energy, int genoTypeLength) {
        this(position, Genotype.randomGenotype(genoTypeLength), energy, 0);
    }
    public void setParents(Animal animalParent1,Animal animalParent2)
    {
        this.animalParent1=animalParent1;
        this.animalParent2=animalParent2;
    }
    public int getNumberOfDescendats()
    {
        return  numberOfDescendats;
    }
    public void getsDescendant(){
        this.numberOfDescendats++;
        if(animalParent1!=null)
            animalParent1.getsDescendant();
        if(animalParent2!=null)
            animalParent2.getsDescendant();
    }
    public static Animal breed(Animal animal1, Animal animal2, int breedEnergy, MutationVariant mutationVariant, int minMutation, int maxMutation) {
        animal1.getsChild();
        animal1.energy -= breedEnergy;
        animal2.getsChild();
        animal2.energy -= breedEnergy;
        return new Animal(animal1.getPosition(), Genotype.cross(animal1, animal2, mutationVariant, minMutation, maxMutation), breedEnergy * 2, animal1.dateOfBirth + animal1.age);
    }

    public Vector2d getPosition() {
        return position;
    }
    public boolean isAlive(){return alive;}
    public void die(){ alive=false;}
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

    @Override
    public String toString() {
        return orientation.toStringGood() + ":" + energy + ":" + age;
    }

    public void move(MoveValidator moveValidator) {
        currentOrientationIndex = age % genotype.getLength();
        int orientationChange = genotype.getRawGenotype()[currentOrientationIndex];
        int newOrientation = (orientation.toNumber() + orientationChange) % 8;
        setOrientation(MapDirection.getOrientations()[newOrientation]);

        position = (moveValidator.moveValidator(this, orientation.transform(position)));

        age++;
        energy--;
        //System.out.println(this);
    }

    public int getNumberOfEatedGrass() {
        return numberOfEatedGrass;
    }

    public void eat(Grass grass) {
        numberOfEatedGrass++;
        energy += grass.getEnergy();
    }

    public int whenItDied() {
        return dateOfBirth + age;
    }

    public void getsChild() {
        noOfChildren++;
    }

    public boolean canBreed(int breedNeededEnergy) {
        return energy >= breedNeededEnergy;
    }
}
