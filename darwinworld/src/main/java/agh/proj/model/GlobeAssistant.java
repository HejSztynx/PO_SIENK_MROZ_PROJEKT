package agh.proj.model;

import agh.proj.model.util.AnimalComparator;

import java.util.*;

public class GlobeAssistant {
    private final Globe globe;
    public GlobeAssistant(Globe globe){
        this.globe=globe;
    }
    public int avgEnergy() {//To do odchudzenia
        if(globe.allDead()==0)
            return 0;
        int n = 0;
        int wyn = 0;
        for (Map.Entry<Vector2d, List<Animal>> entry : globe.getAnimals().entrySet()) {
            Vector2d key = entry.getKey();
            List<Animal> values = entry.getValue();
            for (int i = 0; i < values.size(); i++) {
                n++;
                wyn += values.get(i).getEnergy();
            }

        }
        return wyn / n;
    }
    public int avgChildren() {//To do odchudzenia
        if(globe.allDead()==0)
            return 0;
        int n = 0;
        int wyn = 0;
        for (Map.Entry<Vector2d, List<Animal>> entry : globe.getAnimals().entrySet()) {
            Vector2d key = entry.getKey();
            List<Animal> values = entry.getValue();
            for (int i = 0; i < values.size(); i++) {
                n++;
                wyn += values.get(i).getChildrenNo();
            }
        }
        return wyn / n;
    }
    public void dayGrassGenerator() {//To do odchudzenia
        GrassGenerator generator = new GrassGenerator();
        Random random = new Random();
        int grassCount = 0;
        for (int i = 0; i < globe.getParameters().getPlantsGrowingADay(); ) {
            Vector2d position = null;
            if (random.nextInt(100) < 80) {
                if (!globe.getEmptySpacesJungle().isEmpty()) {
                    position = (Vector2d) globe.getEmptySpacesJungle().toArray()[random.nextInt(globe.getEmptySpacesJungle().size())];
                    globe.getEmptySpacesJungle().remove(position);
                }
            } else {
                if (!globe.getEmptySpacesPlains().isEmpty()) {
                    position = (Vector2d) globe.getEmptySpacesPlains().toArray()[random.nextInt(globe.getEmptySpacesPlains().size())];
                    globe.getEmptySpacesPlains().remove(position);
                }
            }
            if (position != null) {
                Grass grass = new Grass(0,new Vector2d(0,0));
                boolean swampChcker = false;
                if (globe.getBiomes().containsKey("Swamp"))
                    if (globe.getBiomes().get("Swamp").boundsValidator(position))
                        swampChcker = true;
                if (swampChcker) {
                    grass = new GrassGenerator().generateGrass(50, globe.getParameters().getConsumedPlantEnergy(), position);
                } else {
                    grass = new GrassGenerator().generateGrass(0, globe.getParameters().getConsumedPlantEnergy(), position);
                }
                globe.getGrasses().put(position, grass);
                i++;
            }
            if (globe.numberOfEmptySpaces() == 0)
                return;
        }
    }
        public void dayBreading() {
            for (Map.Entry<Vector2d, List<Animal>> entry : globe.getAnimals().entrySet()) {
                Vector2d key = entry.getKey();
                List<Animal> values = entry.getValue();
                if (values.size() > 1) {
                    Collections.sort(values, new AnimalComparator());
                    Animal animal1 = values.get(0);
                    Animal animal2 = values.get(1);
                    if (animal1.canBreed(globe.getParameters().getBreedNeededEnergy()) && animal2.canBreed(globe.getParameters().getBreedNeededEnergy())) {
                        globe.increseNumberOfAnimals();
                        Animal newAnimal = Animal.breed(animal1, animal2, globe.getParameters().getBreedLostEnergy(), globe.getParameters().getMutationVariant(), globe.getParameters().getMinMutations(), globe.getParameters().getMaxMutations(),globe.getNumberOfAllAnimals());
                        globe.increseNumberOfAnimals();
                        globe.place(newAnimal, newAnimal.getPosition());
                        if(globe.getMostPopular().get(newAnimal.getGenotype())==null)
                            globe.getMostPopular().put(newAnimal.getGenotype(),0);
                        globe.getMostPopular().put(newAnimal.getGenotype(),globe.getMostPopular().get(newAnimal.getGenotype())+1);
                        animal1.getsDescendant();
                        animal2.getsDescendant();
                        newAnimal.setParents(animal1,animal2);
                        globe.getRecords().add(newAnimal);
                    }
                }
            }
        }
    public void dayMovesAnimal() {//To do odchudzenia
        List<Animal> tmpListOfAll = new ArrayList<>();
        for (Map.Entry<Vector2d, List<Animal>> entry : globe.getAnimals().entrySet()) {
            Vector2d key = entry.getKey();
            List<Animal> values = entry.getValue();
            tmpListOfAll.addAll(values);

        }
        for (int i = 0; i < tmpListOfAll.size(); i++) {
            globe.move(tmpListOfAll.get(i));
        }
    }
    public void dayEating() {//To do odchudzenia
        for (Map.Entry<Vector2d, List<Animal>> entry : globe.getAnimals().entrySet()) {
            Vector2d key = entry.getKey();
            List<Animal> values = entry.getValue();
            if (globe.getGrasses().get(key) != null && values.size() > 0) {
                Collections.sort(values, new AnimalComparator());
                //System.out.println(values+"->"+grasses.get(key).getEnergy()+"->");
                values.get(0).eat(globe.getGrasses().get(key));
                if(globe.getBiomes().get("Jungle").boundsValidator(key))
                    globe.getEmptySpacesJungle().add(key);
                else
                    globe.getEmptySpacesPlains().add(key);
                globe.getGrasses().remove(key);
                //System.out.println(values);
            }
        }
    }
    public void dayCleaner() {//To do odchudzenia
        List<Animal> tmpList = new ArrayList<>();
        for (Map.Entry<Vector2d, List<Animal>> entry : globe.getAnimals().entrySet()) {
            Vector2d key = entry.getKey();
            List<Animal> values = entry.getValue();

            for (int i = 0; i < values.size(); i++) {
                if (values.get(i).getEnergy() <= 0) {
                    tmpList.add(values.get(i));
                }
            }
        }
        for (Animal animal : tmpList) {
            globe.getAnimals().get(animal.getPosition()).remove(animal);
            globe.increseSumOfAge(animal.getAge());
            globe.increseDead();
            animal.die();
        }
    }
}

