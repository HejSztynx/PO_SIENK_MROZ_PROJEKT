package agh.proj.gui;

import agh.proj.model.Globe;
import agh.proj.model.Parameters;
import agh.proj.model.variants.BehaviorVariant;
import agh.proj.model.variants.FoliageVariant;
import agh.proj.model.variants.MapVariant;
import agh.proj.model.variants.MutationVariant;
import agh.proj.simulation.Simulation;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;

public class SimulationPresenter {
    @FXML
    private TextField mapHeight;
    @FXML
    private TextField mapWidth;
    @FXML
    private ComboBox<String> mapVariant;
    @FXML
    private TextField grassNumber;
    @FXML
    private TextField grassEnergy;
    @FXML
    private TextField grassGrowth;
    @FXML
    private ComboBox<String> foliageVariant;
    @FXML
    private TextField animalNumber;
    @FXML
    private TextField animalEnergy;
    @FXML
    private TextField breedEnergyNeeded;
    @FXML
    private TextField breedEnergyLost;
    @FXML
    private ComboBox<String> behaviorVariant;
    @FXML
    private TextField minMutations;
    @FXML
    private TextField maxMutations;
    @FXML
    private ComboBox<String> mutationVariant;
    @FXML
    private TextField genomeLength;

    private ArrayList<String> textFields= new ArrayList<>();

    public void onSimulationStartClicked() {
        textFields.clear();
        textFields.addAll(List.of(mapHeight.getText(), mapWidth.getText(), grassNumber.getText(), grassEnergy.getText(), grassGrowth.getText(),
                animalNumber.getText(), animalEnergy.getText(), breedEnergyNeeded.getText(), breedEnergyLost.getText(), minMutations.getText(), maxMutations.getText(), genomeLength.getText()));

        ArrayList<Integer> result = checkParameters(textFields);
        if (!result.isEmpty()) {
            Parameters parameters = new Parameters(result.get(0), result.get(1), VariantConverter.mapVariant(mapVariant.getValue()),
                    result.get(2), result.get(3), result.get(4), VariantConverter.foliageVariant(foliageVariant.getValue()),
                    result.get(5), result.get(6), result.get(7), result.get(8),
                    result.get(9), result.get(10), VariantConverter.mutationVariant(mutationVariant.getValue()),
                    result.get(11), VariantConverter.behaviorVariant(behaviorVariant.getValue()));
//            Parameters parameters = new Parameters(result.get(0), result.get(1), MapVariant.GLOBE,
//                    result.get(2), result.get(3), result.get(4), FoliageVariant.VERDANT_EQUATOR,
//                    result.get(5), result.get(6), result.get(7), result.get(8),
//                    result.get(9), result.get(10), MutationVariant.FULLY_RANDOM,
//                    result.get(11), BehaviorVariant.FULL_PREDESTINATION);
            Globe map = new Globe(parameters.getMapWidth(), parameters.getMapHeight(), parameters); // tu sie zmieni na WorldMap kiedys
            Simulation simulation = new Simulation(map);
            simulation.run();
        } else System.out.println("NIE GIT");
    }

    private ArrayList<Integer> checkParameters(ArrayList<String> textFields) {
        try {
            int mapHeight = Integer.parseInt(textFields.get(0));
            int mapWidth = Integer.parseInt(textFields.get(1));
            int grassNumber = Integer.parseInt(textFields.get(2));
            int grassEnergy = Integer.parseInt(textFields.get(3));
            int grassGrowth = Integer.parseInt(textFields.get(4));
            int animalNumber = Integer.parseInt(textFields.get(5));
            int animalEnergy = Integer.parseInt(textFields.get(6));
            int breedEnergyNeeded = Integer.parseInt(textFields.get(7));
            int breedEnergyLost = Integer.parseInt(textFields.get(8));
            int minMutations = Integer.parseInt(textFields.get(9));
            int maxMutations = Integer.parseInt(textFields.get(10));
            int genomeLength = Integer.parseInt(textFields.get(11));

            if ((mapHeight >= 2) &&
                    (mapWidth >= 2) &&
                    (grassNumber >= 0) &&
                    (grassEnergy >= 0) &&
                    (grassGrowth >= 0) &&
                    (animalNumber >= 1) &&
                    (animalEnergy >= 1) &&
                    (breedEnergyNeeded >= 1) &&
                    (breedEnergyLost >= 0) &&
                    (minMutations >= 0) &&
                    (maxMutations >= 0 && maxMutations >= minMutations) &&
                    (genomeLength >= 1)
            ) {
                return new ArrayList<>(List.of(mapHeight, mapWidth, grassNumber,
                        grassEnergy, grassGrowth, animalNumber, animalEnergy, breedEnergyNeeded, breedEnergyLost,
                        minMutations, maxMutations, genomeLength));
            } else {
                return new ArrayList<Integer>();
            }

        } catch (NumberFormatException e) {
            return new ArrayList<Integer>();
        }
    }
}
