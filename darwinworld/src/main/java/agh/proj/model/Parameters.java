package agh.proj.model;

import agh.proj.model.variants.MapVariant;
import agh.proj.model.variants.MutationVariant;
import agh.proj.model.variants.BehaviorVariant;
import agh.proj.model.variants.FoliageVariant;

public class Parameters {
    private final int mapHeight;
    private final int mapWidth;
    private final MapVariant mapVariant;
    private final int initialPlantsQuantity;
    private final int consumedPlantEnergy;
    private final int plantsGrowingADay;
    private final FoliageVariant foliageVariant;
    private final int initialAnimalsNumber;
    private final int initialEnergy;
    private final int breedNeededEnergy;
    private final int breedLostEnergy;
    private final int minMutations;
    private final int maxMutations;
    private final MutationVariant mutationVariant;
    private final int genotypeLength;
    private final BehaviorVariant behaviorVariant;

    public Parameters(int mapHeight, int mapWidth, MapVariant mapVariant, int initialPlantsQuantity,
                      int consumedPlantEnergy, int plantsGrowingADay, FoliageVariant foliageVariant, int initialAnimalsNumber,
                      int initialEnergy, int breedNeededEnergy, int breedLostEnergy, int minMutations,
                      int maxMutations, MutationVariant mutationVariant, int genotypeLength, BehaviorVariant behaviorVariant) {
        this.mapHeight = mapHeight;
        this.mapWidth = mapWidth;
        this.mapVariant = mapVariant;
        this.initialPlantsQuantity = initialPlantsQuantity;
        this.consumedPlantEnergy = consumedPlantEnergy;
        this.plantsGrowingADay = plantsGrowingADay;
        this.foliageVariant = foliageVariant;
        this.initialAnimalsNumber = initialAnimalsNumber;
        this.initialEnergy = initialEnergy;
        this.breedNeededEnergy = breedNeededEnergy;
        this.breedLostEnergy = breedLostEnergy;
        this.minMutations = minMutations;
        this.maxMutations = maxMutations;
        this.mutationVariant = mutationVariant;
        this.genotypeLength = genotypeLength;
        this.behaviorVariant = behaviorVariant;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public BehaviorVariant getBehaviorVariant() {
        return behaviorVariant;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public int getInitialEnergy() {
        return initialEnergy;
    }

    public MapVariant getMapVariant() {
        return mapVariant;
    }

    public int getInitialPlantsQuantity() {
        return initialPlantsQuantity;
    }

    public int getConsumedPlantEnergy() {
        return consumedPlantEnergy;
    }

    public int getPlantsGrowingADay() {
        return plantsGrowingADay;
    }

    public FoliageVariant getFoliageVariant() {
        return foliageVariant;
    }

    public int getInitialAnimalsNumber() {
        return initialAnimalsNumber;
    }

    public int getBreedNeededEnergy() {
        return breedNeededEnergy;
    }

    public int getBreedLostEnergy() {
        return breedLostEnergy;
    }

    public int getMinMutations() {
        return minMutations;
    }

    public int getMaxMutations() {
        return maxMutations;
    }

    public MutationVariant getMutationVariant() {
        return mutationVariant;
    }

    public int getGenotypeLength() {
        return genotypeLength;
    }


}
