package agh.proj.model;

import agh.proj.model.variants.MapVariant;
import agh.proj.model.variants.MutationVariant;
import agh.proj.model.variants.BehaviorVariant;
import agh.proj.model.variants.FoliageVariant;

public class Parameters {
    final int mapHeight;
    final int mapWidth;
    final MapVariant mapVariant;
    final int initialPlantsQuantity;
    final int consumedPlantEnergy;
    final int plantsGrowingADa;
    final FoliageVariant foliageVariant;
    final int initialAnimalsNumber;
    final int initialEnergy;
    final int breedNeededEnergy;
    final int breedLostEnergy;
    final int minMutations;
    final int maxMutations;
    final MutationVariant mutationVariant;
    final int genotypeLength;
    final BehaviorVariant behaviorVariant;

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
