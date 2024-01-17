package agh.proj.gui;

import agh.proj.model.variants.BehaviorVariant;
import agh.proj.model.variants.FoliageVariant;
import agh.proj.model.variants.MapVariant;
import agh.proj.model.variants.MutationVariant;

public class VariantConverter {
    public static MapVariant mapVariant(String map) {
        if (map.equals("Globe")) {
            return MapVariant.GLOBE;
        }
        throw new IllegalStateException("Unexpected value: " + map);
    }
    public static FoliageVariant foliageVariant(String foliage) {
        if (foliage.equals("Verdant Equator")) {
            return FoliageVariant.VERDANT_EQUATOR;
        }
        if (foliage.equals("Poisonous Plants")) {
            return FoliageVariant.POISONOUS_PLANTS;
        }
        throw new IllegalStateException("Unexpected value: " + foliage);
    }
    public static MutationVariant mutationVariant(String mutation) {
        if (mutation.equals("Fully Random")) {
            return MutationVariant.SWAP;
        }
        if (mutation.equals("Swap")) {
            return MutationVariant.FULLY_RANDOM;
        }
        throw new IllegalStateException("Unexpected value: " + mutation);
    }
    public static BehaviorVariant behaviorVariant(String behavior) {
        if (behavior.equals("Full Predestination")) {
            return BehaviorVariant.FULL_PREDESTINATION;
        }
        throw new IllegalStateException("Unexpected value: " + behavior);
    }
}
