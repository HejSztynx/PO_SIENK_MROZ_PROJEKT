package agh.proj.model;

import agh.proj.model.interfaces.BoundsValidator;
import agh.proj.model.interfaces.WorldElement;
import agh.proj.model.interfaces.WorldMap;
import agh.proj.model.util.AnimalComparator;
import agh.proj.model.variants.MapVariant;

import java.util.*;

import static java.lang.Math.sqrt;

public class Globe implements WorldMap, BoundsValidator {
    private static int animalCount = 0;
    private final Vector2d lowerLeft = new Vector2d(0, 0);
    private final Vector2d upperRight;
    private final List<Animal> records = new ArrayList<>();
    private final List<Animal> deadAnimals = new ArrayList<>();
    private final Map<String, Biome> biomes = new HashMap<>();
    private final List<List<Integer>> descendantTree = new ArrayList<>();
    private final Map<Vector2d, Grass> grasses = new HashMap<>();
    private final Parameters parameters;
    private int day = 0;
    private Map<Vector2d, List<Animal>> animals = new HashMap<>();
    private Map<Genotype, Integer> mostPopular = new HashMap<>();

    public Globe(int width, int height, Parameters parameters) {
        upperRight = new Vector2d(width, height);
        this.parameters = parameters;
        generateJungle();
        if (parameters.getMapVariant() == MapVariant.SWAMP)
            generateSwamp();
        initialGrassGenerator();
        initialAnimalMap();
        initialAnimalsGenerator();
    }

    public int allDead() {
        return records.size() - deadAnimals.size();
    }

    public void addDay() {
        day++;
    }

    public int avgAgeForDead() {
        int n = 0;
        int wyn = 0;
        for (Animal animal : deadAnimals) {
            n++;
            wyn += animal.getAge();
        }
        return wyn / n;
    }

    public int numberOfDescendants() {
        return numberOfDescendatns(0);
    }

    private int numberOfDescendatns(int res) {
//        res+=
//        for(int i=0;i<descendantTree.size();i++){
//
//        }
        return 1;
    }

    public int avgEnergy() {
        if(allDead()==0)
            return 0;
        int n = 0;
        int wyn = 0;
        for (Map.Entry<Vector2d, List<Animal>> entry : animals.entrySet()) {
            Vector2d key = entry.getKey();
            List<Animal> values = entry.getValue();
            for (int i = 0; i < values.size(); i++) {
                n++;
                wyn += values.get(i).getEnergy();
            }

        }
        return wyn / n;
    }

    public int getDay(){
        return day;
    }
    public int avgChildren() {
        if(allDead()==0)
            return 0;
        int n = 0;
        int wyn = 0;
        for (Map.Entry<Vector2d, List<Animal>> entry : animals.entrySet()) {
            Vector2d key = entry.getKey();
            List<Animal> values = entry.getValue();
            for (int i = 0; i < values.size(); i++) {
                n++;
                wyn += values.get(i).getChildrenNo();
            }
        }
        return wyn / n;
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
//        int wyn = 0;
//        for (int i = 0; i < upperRight.getY() + 1; i++) {
//            for (int j = 0; j < upperRight.getX() + 1; j++) {
//                if (isOccupied(new Vector2d(j, i))==false)
//                    wyn++;
//            }
//        }
//        return wyn;
    }

    private void generateJungle() {
        int jungleHeight = (int) (upperRight.getY() / sqrt(5));
        int jungleWidth = (int) (upperRight.getX() / sqrt(5));
        Biome jungle = new Biome(new Vector2d((upperRight.getX() - jungleWidth) / 2, (upperRight.getY() - jungleHeight) / 2), new Vector2d((upperRight.getX() - jungleWidth) / 2 + jungleWidth, (upperRight.getY() - jungleHeight) / 2 + jungleHeight));
        biomes.put("Jungle", jungle);
    }

    private void generateSwamp() {
        Random random = new Random();
        int startWidth = random.nextInt(0, (int) ((upperRight.getX() * 4) / sqrt(5)));
        int startHeight = random.nextInt(0, (int) ((upperRight.getY() * 4) / sqrt(5)));
        int swampHeight = (int) (upperRight.getY() / sqrt(5));
        int swampWidth = (int) (upperRight.getX() / sqrt(5));
        Biome jungle = new Biome(new Vector2d(startWidth, startHeight), new Vector2d(startWidth + swampWidth, startHeight + swampHeight));
        biomes.put("Swamp", jungle);
    }

    private void initialGrassGenerator() {
        Random random = new Random();
        for (int i = 0; i < parameters.getInitialPlantsQuantity(); ) {
            Vector2d position = new Vector2d(random.nextInt(upperRight.getX()), random.nextInt(upperRight.getY()));
            Grass grass = new Grass(parameters.getConsumedPlantEnergy(), position);
            if (grasses.get(position) == null) {
                grasses.put(position, grass);
                i++;
            }
        }
    }

    public void dayGrassGenerator() {
        GrassGenerator generator = new GrassGenerator();
        int grassCount = 0;
        for (int i = 0; i < upperRight.getY()+1; i++) {
            for (int j = 0; j < upperRight.getX()+1; j++) {
                Vector2d position = new Vector2d(j, i);
                Grass grass = null;
                if (parameters.getMapVariant() == MapVariant.SWAMP)
                    grass = generator.generateGrass(biomes.get("Jungle").boundsValidator(position), 50, parameters.getConsumedPlantEnergy(), position);
                else
                    grass = generator.generateGrass(biomes.get("Jungle").boundsValidator(position), 0, parameters.getConsumedPlantEnergy(), position);
                if (grass.getEnergy() != 0) {
                    if(grasses.get(position)==null)
                    {
                        grasses.put(position,grass);
                        grassCount+=1;
                    }
                }
                if (numberOfEmptySpaces() <=0 || grassCount >= parameters.getPlantsGrowingADay())
                {

                    return;
                }
                else if (i >= upperRight.getY() && j >= upperRight.getX()) {
                    i = 0;
                    System.out.println(grassCount+"/"+parameters.getPlantsGrowingADay()+":"+numberOfEmptySpaces());
                }

            }
        }
    }

    private void initialAnimalsGenerator() {
        Random random = new Random();
        for (int i = 0; i < parameters.getInitialAnimalsNumber(); i++) {
            Vector2d position = new Vector2d(random.nextInt(upperRight.getX()), random.nextInt(upperRight.getY()));
            Animal animal = new Animal(position, parameters.getInitialEnergy(), parameters.getGenotypeLength(), animalCount++);
            records.add(animal);
            descendantTree.add(new ArrayList<>());
            animals.get(position).add(animal);
            if(mostPopular.get(animal.getGenotype())==null)
                mostPopular.put(animal.getGenotype(),0);
            mostPopular.put(animal.getGenotype(),mostPopular.get(animal.getGenotype())+1);
        }
    }

    public void dayMovesAnimal() {
        List<Animal> tmpListOfAll = new ArrayList<>();
        for (Map.Entry<Vector2d, List<Animal>> entry : animals.entrySet()) {
            Vector2d key = entry.getKey();
            List<Animal> values = entry.getValue();
            tmpListOfAll.addAll(values);

        }
        for (int i = 0; i < tmpListOfAll.size(); i++) {
            move(tmpListOfAll.get(i));
        }
    }

    public void dayEating() {
        for (Map.Entry<Vector2d, List<Animal>> entry : animals.entrySet()) {
            Vector2d key = entry.getKey();
            List<Animal> values = entry.getValue();
            if (grasses.get(key) != null && values.size() > 0) {
                Collections.sort(values, new AnimalComparator());
                //System.out.println(values+"->"+grasses.get(key).getEnergy()+"->");
                values.get(0).eat(grasses.get(key));
                grasses.remove(key);
                //System.out.println(values);
            }
        }
    }

    public void dayCleaner() {
        List<Animal> tmpList = new ArrayList<>();
        for (Map.Entry<Vector2d, List<Animal>> entry : animals.entrySet()) {
            Vector2d key = entry.getKey();
            List<Animal> values = entry.getValue();

            for (int i = 0; i < values.size(); i++) {
                if (values.get(i).getEnergy() <= 0) {
                    tmpList.add(values.get(i));
                }
            }
        }
        for (Animal animal : tmpList) {
            animals.get(animal.getPosition()).remove(animal);
            deadAnimals.add(animal);
        }
    }

    public void dayBreading() {
        for (Map.Entry<Vector2d, List<Animal>> entry : animals.entrySet()) {
            Vector2d key = entry.getKey();
            List<Animal> values = entry.getValue();
            if (values.size() > 1) {
                Collections.sort(values, new AnimalComparator());
                Animal animal1 = values.get(0);
                Animal animal2 = values.get(1);
                if (animal1.canBreed(parameters.getBreedNeededEnergy()) && animal2.canBreed(parameters.getBreedNeededEnergy())) {
                    Animal newAnimal = Animal.breed(animal1, animal2, parameters.getBreedLostEnergy(), parameters.getMutationVariant(), parameters.getMinMutations(), parameters.getMaxMutations(), animalCount++);
                    records.add(newAnimal);
                    place(newAnimal, newAnimal.getPosition());
                    descendantTree.get(animal1.getHisNumber()).add(animalCount - 1);
                    descendantTree.get(animal2.getHisNumber()).add(animalCount - 1);
                    descendantTree.add(new ArrayList<>());
                    if(mostPopular.get(newAnimal.getGenotype())==null)
                        mostPopular.put(newAnimal.getGenotype(),0);
                    mostPopular.put(newAnimal.getGenotype(),mostPopular.get(newAnimal.getGenotype())+1);
                    //System.out.println(values+"->"+key);
                }
            }
        }
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

    @Override
    public void move(Animal animal) {
        animals.get(animal.getPosition()).remove(animal);
        animal.move(this);
        animals.get(animal.getPosition()).add(animal);
    }


    //Ogólnie tej funcki nie potrzebujemy bo nic nie placujemy
    @Override
    public void place(Animal animal, Vector2d position) {
        if (boundsValidator(position)) {
            animals.get(position).add((Animal) animal);
        } else System.out.println("CANNOT PLACE AT " + position);
    }
}
