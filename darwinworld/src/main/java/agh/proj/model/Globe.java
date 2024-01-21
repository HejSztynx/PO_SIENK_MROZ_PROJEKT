package agh.proj.model;

import agh.proj.model.interfaces.BoundsValidator;
import agh.proj.model.interfaces.WorldElement;
import agh.proj.model.interfaces.WorldMap;
import agh.proj.model.variants.FoliageVariant;

import java.util.*;

import static java.lang.Math.sqrt;

public class Globe implements WorldMap, BoundsValidator {
    private final Vector2d upperRight;
    private static int numberOfAllAnimals=0;
    private static int deadAnimals = 0;
    private static int sumOfAge=0;
    private final Map<String, Biome> biomes = new HashMap<>();
    private final Map<Vector2d, Grass> grasses = new HashMap<>();
    private final Parameters parameters;
    private int day = 0;
    private Map<Vector2d, List<Animal>> animals = new HashMap<>();
    private ArrayList<Animal> records=new ArrayList<>();

    public Map<Genotype, Integer> getMostPopular() {
        return mostPopular;
    }
    public ArrayList<Animal> getRecords(){
        return records;
    }
    private Map<Genotype, Integer> mostPopular = new HashMap<>();
    private Set<Vector2d> emptySpacesJungle = new HashSet<>();
    private Set<Vector2d> emptySpacesPlains = new HashSet<>();
    public Globe(int width, int height, Parameters parameters) {
        upperRight = new Vector2d(width - 1, height - 1);
        this.parameters = parameters;
        generateJungle();
        if (parameters.getFoliageVariant() == FoliageVariant.POISONOUS_PLANTS)
            generateSwamp();
        emptySpacesInitialazie();
        initialGrassGenerator();
        initialAnimalMap();
        initialAnimalsGenerator();

    }
    public void increseNumberOfAnimals(){
        numberOfAllAnimals++;
    }
    private void emptySpacesInitialazie() {
        for (int i = 0; i < upperRight.getY() + 1; i++) {
            for (int j = 0; j < upperRight.getX() + 1; j++) {
                Vector2d position = new Vector2d(j, i);
                if (biomes.get("Jungle").boundsValidator(position))
                    emptySpacesJungle.add(position);
                else
                    emptySpacesPlains.add(position);
            }
        }
    }

    public int allDead() {
        return numberOfAllAnimals-deadAnimals;
    }

    public void addDay() {
        day++;
    }

    public int avgAgeForDead() {
        return sumOfAge/deadAnimals;
    }
    public  Map<Vector2d, List<Animal>> getAnimals(){
        return animals;
    }
    public int getDay(){
        return day;
    }
    public Genotype mostPopularGenom() {
        Genotype wyn = null;
        int animals_with_it = 0;
        for (Map.Entry<Genotype, Integer> entry : mostPopular.entrySet()) {
            if (entry.getValue() > animals_with_it)
                wyn = entry.getKey();
        }
        return wyn;
    }

    private void initialAnimalMap() {
        for (int i = 0; i < upperRight.getY() + 1; i++) {
            for (int j = 0; j < upperRight.getX() + 1; j++) {
                animals.put(new Vector2d(j, i), new ArrayList<Animal>());

            }
        }
    }

    public int numberOfEmptySpaces() {
        return ((upperRight.getX()+1) * (upperRight.getY()+1) - grasses.size());
    }

    private void generateJungle() {
        int jungleHeight = (int) (upperRight.getY() / 5);
        int jungleWidth = (int) (upperRight.getX());
        Biome jungle = new Biome(new Vector2d(0, 3 * (upperRight.getY() - jungleHeight) / 5), new Vector2d(upperRight.getX(), 3 * (upperRight.getY() - jungleHeight) / 5 + jungleHeight));
        biomes.put("Jungle", jungle);
    }

    private void generateSwamp() {
        Random random = new Random();
        int startWidth = random.nextInt(0, (int) ((upperRight.getX()) / sqrt(4)));
        int startHeight = random.nextInt(0, (int) ((upperRight.getY()) / sqrt(5)));
        int swampHeight = (int) (upperRight.getY() / sqrt(5));
        int swampWidth = (int) (upperRight.getX() / sqrt(4));
        Biome jungle = new Biome(new Vector2d(startWidth, startHeight), new Vector2d(startWidth + swampWidth, startHeight + swampHeight));
        biomes.put("Swamp", jungle);
    }
    public int getNumberOfAllAnimals(){
        return numberOfAllAnimals;
    }
    public String biomeColor(int x, int y) {
        String color;
        Vector2d position = new Vector2d(x, y);
        if (biomes.containsKey("Jungle") && biomes.get("Jungle").boundsValidator(position)) {
            color = "lightgreen";
            if (biomes.containsKey("Swamp") && biomes.get("Swamp").boundsValidator(position)) {
                color = "lightblue";
            }
        }
        else if (biomes.containsKey("Swamp") && biomes.get("Swamp").boundsValidator(position)) {
            color = "BLUEVIOLET";
        }
        else color = "lightyellow";
        return color;
    }

    private void initialGrassGenerator() {
        Random random = new Random();
        for (int i = 0; i < parameters.getInitialPlantsQuantity();i++ ) {
            Vector2d position = new Vector2d(random.nextInt(upperRight.getX()), random.nextInt(upperRight.getY()));
            Grass grass = new Grass(parameters.getConsumedPlantEnergy(), position);
            if (grasses.get(position) == null) {
                grasses.put(position, grass);
                if (biomes.get("Jungle").boundsValidator(position))
                    emptySpacesJungle.remove(position);
                else
                    emptySpacesPlains.remove(position);
            }
        }
    }

    public Parameters getParameters(){
        return parameters;
    }
    public Set<Vector2d> getEmptySpacesJungle(){
        return emptySpacesJungle;
    }
    public Set<Vector2d> getEmptySpacesPlains(){
        return emptySpacesPlains;
    }
    public Map<Vector2d, Grass> getGrasses(){
        return grasses;
    }
    public Map<String,Biome> getBiomes(){
        return biomes;
    }
    private void initialAnimalsGenerator() {
        Random random = new Random();
        for (int i = 0; i < parameters.getInitialAnimalsNumber(); i++) {
            Vector2d position = new Vector2d(random.nextInt(upperRight.getX()), random.nextInt(upperRight.getY()));
            Animal animal = new Animal(position, parameters.getInitialEnergy(), parameters.getGenotypeLength(),numberOfAllAnimals++);
            increseNumberOfAnimals();
            animals.get(position).add(animal);
            if(mostPopular.get(animal.getGenotype())==null)
                mostPopular.put(animal.getGenotype(),0);
            mostPopular.put(animal.getGenotype(),mostPopular.get(animal.getGenotype())+1);
            records.add(animal);
        }
    }
    public void increseSumOfAge(int age){
        sumOfAge+=age;
    }
    public void increseDead(){
        deadAnimals++;
    }







    @Override
    public boolean boundsValidator(Vector2d position) {
        int x = position.getX();
        int y = position.getY();
        return y >= 0 && y <= upperRight.getY() && x >= 0 && x <= upperRight.getX();
    }

    @Override
    public Vector2d getBounds() {
        return upperRight;
    }

    @Override
    public Vector2d moveValidator(Animal animal, Vector2d newPosition) {
        if (boundsValidator(newPosition)) return newPosition;
        int x = newPosition.getX();
        int y = newPosition.getY();

        if (x == -1) {
            x = upperRight.getX();

        } else if (x == upperRight.getX() + 1) {
            x = 0;

        }

        if (y == -1) {
            y = 0;
            switch (animal.getOrientation()) {
                case SOUTH -> animal.setOrientation(MapDirection.NORTH);
                case SOUTH_EAST -> animal.setOrientation(MapDirection.NORTH_EAST);
                case SOUTH_WEST -> animal.setOrientation(MapDirection.NORTH_WEST);
            }
        } else if (y == upperRight.getY() + 1) {
            y = upperRight.getY();
            switch (animal.getOrientation()) {
                case NORTH -> animal.setOrientation(MapDirection.SOUTH);
                case NORTH_EAST -> animal.setOrientation(MapDirection.SOUTH_EAST);
                case NORTH_WEST -> animal.setOrientation(MapDirection.SOUTH_WEST);
            }
        }
        return new Vector2d(x, y);
    }


    @Override
    public WorldElement objectAt(Vector2d position) {
        if (animals.get(position) != null) {
            if (animals.get(position).size() != 0)
                return animals.get(position).get(0);
        }
        return grasses.get(position);
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return objectAt(position) != null;
    }
    private boolean focusCheck(Animal animal){
        Random random=new Random();
        if(grasses.get(animal.getPosition())!=null){
            if(random.nextInt(100)<=20) {
                if(grasses.get(animal.getPosition()).getEnergy()<0)
                    return true;
            }
        }
        return false;
    }
    @Override
    public void move(Animal animal) {
        animals.get(animal.getPosition()).remove(animal);
        animal.move(this);
        if(focusCheck(animal))
            animal.move(this);
        animals.get(animal.getPosition()).add(animal);
    }
    @Override
    public void place(Animal animal, Vector2d position) {
        if (boundsValidator(position)) {
            animals.get(position).add((Animal) animal);
        } else System.out.println("CANNOT PLACE AT " + position);
    }
}
