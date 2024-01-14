package agh.proj.model;

import agh.proj.model.variants.MapVariant;
import agh.proj.model.variants.MutationVariant;
import agh.proj.model.variants.BehaviorVariant;
import agh.proj.model.variants.FoliageVariant;

public class Parameters {
    public final int mapHeight;
    public final int mapWidth;
    public final MapVariant mapVariant;
    public final int initialPlantsQuantity;
    public final int consumedPlantEnergy;
    public final int plantsGrowingADa;
    public final FoliageVariant foliageVariant;
    public final int initialAnimalsNumber;
    public final int initialEnergy;
    public final int breedNeededEnergy;
    public final int breedLostEnergy;
    public final int minMutations;
    public final int maxMutations;
    public final MutationVariant mutationVariant;
    public final int genotypeLength;
    public final BehaviorVariant behaviorVariant;

    public Parameters(int mapHeight, int mapWidth, MapVariant mapVariant, int initialPlantsQuantity,
                      int consumedPlantEnergy, int plantsGrowingADa, FoliageVariant foliageVariant, int initialAnimalsNumber,
                      int initialEnergy, int breedNeededEnergy, int breedLostEnergy, int minMutations,
                      int maxMutations, MutationVariant mutationVariant, int genotypeLength, BehaviorVariant behaviorVariant) {
        this.mapHeight = mapHeight;
        this.mapWidth = mapWidth;
        this.mapVariant = mapVariant;
        this.initialPlantsQuantity = initialPlantsQuantity;
        this.consumedPlantEnergy = consumedPlantEnergy;
        this.plantsGrowingADa = plantsGrowingADa;
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
}
