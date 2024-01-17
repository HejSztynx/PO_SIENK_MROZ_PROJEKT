package agh.proj.gui;

import agh.proj.model.Globe;
import agh.proj.model.Parameters;
import agh.proj.model.util.CSVParameterSaver;
import agh.proj.model.util.CSVReader;
import agh.proj.simulation.Simulation;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class SimulationPresenter {
    private static String FIRST_CSV_LINE = "preset,mapHeight,mapWidth,mapVariant,initialPlantsQuantity," +
            "consumedPlantEnergy,plantsGrowingADay,foliageVariant,initialAnimalsNumber,initialEnergy," +
            "breedNeededEnergy,breedLostEnergy,minMutations,maxMutations,mutationVariant,genotypeLength,behaviorVariant";
    private final String projectDirectory = System.getProperty("user.dir");
    private final String presetsFileLocation = projectDirectory + "/presets/presets.csv";

    @FXML
    private TextField mapHeight;
    @FXML
    private TextField mapWidth;
    @FXML
    private ComboBox<String> mapVariant;
    @FXML
    private TextField initialPlantsQuantity;
    @FXML
    private TextField consumedPlantEnergy;
    @FXML
    private TextField plantsGrowingADay;
    @FXML
    private ComboBox<String> foliageVariant;
    @FXML
    private TextField initialAnimalsNumber;
    @FXML
    private TextField initialEnergy;
    @FXML
    private TextField breedNeededEnergy;
    @FXML
    private TextField breedLostEnergy;
    @FXML
    private ComboBox<String> behaviorVariant;
    @FXML
    private TextField minMutations;
    @FXML
    private TextField maxMutations;
    @FXML
    private ComboBox<String> mutationVariant;
    @FXML
    private TextField genotypeLength;

    @FXML
    private ComboBox<String> presets;

    private final ArrayList<String> values = new ArrayList<>();

    public void initialize() {
        initializeCSVDirectory();

        CSVReader csvReader = new CSVReader();
        ArrayList<String> presetNames = new ArrayList<>();
        presetNames.add("Custom");
        presetNames.addAll(csvReader.readFirstColumn(presetsFileLocation));
        presets.setItems(FXCollections.observableArrayList(presetNames));
        presets.setValue("Custom");
    }

    private void initializeCSVDirectory() {
        String CSVDirectoryURL = projectDirectory + "/presets";

        try {
            Path directory = Paths.get(CSVDirectoryURL);
            Path file = Paths.get(presetsFileLocation);

            if (!Files.exists(file)) {
                Files.createDirectories(directory);
                Files.createFile(file);
                initializeCSVFile(presetsFileLocation);
                System.out.println("Katalog i plik zostały utworzone (jeśli nie istniały).");
            } else {
                System.out.println("Plik już istnieje.");
            }
        } catch (IOException e) {
            System.err.println("Błąd podczas tworzenia katalogu i pliku: " + e.getMessage());
        }
    }

    private void initializeCSVFile(String url) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(url))) {
//            writer.newLine();
            writer.write(FIRST_CSV_LINE);
//                System.out.println("ZAPISALEM");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initializeValues() {
        values.clear();
        values.addAll(List.of(mapHeight.getText(), mapWidth.getText(), mapVariant.getValue(), initialPlantsQuantity.getText(),
                consumedPlantEnergy.getText(), plantsGrowingADay.getText(),
                foliageVariant.getValue(), initialAnimalsNumber.getText(), initialEnergy.getText(), breedNeededEnergy.getText(),
                breedLostEnergy.getText(), minMutations.getText(), maxMutations.getText(),
                mutationVariant.getValue(), genotypeLength.getText(), behaviorVariant.getValue()));
    }

    public void onSimulationStartClicked() {
        initializeValues();

        Parameters parameters = checkParameters(values);
        if (parameters != null) {
            Globe map = new Globe(parameters.getMapWidth(), parameters.getMapHeight(), parameters); // tu sie zmieni na WorldMap kiedys
            Simulation simulation = new Simulation(map);
            simulation.run();
        } else System.out.println("NIE GIT");
    }

    public void onSavePresetsClicked() {
        initializeValues();
        Parameters parameters = checkParameters(values);
        if (parameters != null) {
            CSVParameterSaver saver = new CSVParameterSaver();
            saver.save(values, presetsFileLocation);
        } else System.out.println("NIE GIT");

    }

    private Parameters checkParameters(ArrayList<String> values) {
        try {
            int mapHeight = Integer.parseInt(values.get(0));
            int mapWidth = Integer.parseInt(values.get(1));
            int initialPlantsQuantity = Integer.parseInt(values.get(3));
            int consumedPlantEnergy = Integer.parseInt(values.get(4));
            int plantsGrowingADay = Integer.parseInt(values.get(5));
            int initialAnimalsNumber = Integer.parseInt(values.get(7));
            int initialEnergy = Integer.parseInt(values.get(8));
            int breedNeededEnergy = Integer.parseInt(values.get(9));
            int breedLostEnergy = Integer.parseInt(values.get(10));
            int minMutations = Integer.parseInt(values.get(11));
            int maxMutations = Integer.parseInt(values.get(12));
            int genotypeLength = Integer.parseInt(values.get(14));

            if ((mapHeight >= 2) &&
                    (mapWidth >= 2) &&
                    (initialPlantsQuantity >= 0) &&
                    (consumedPlantEnergy >= 0) &&
                    (plantsGrowingADay >= 0) &&
                    (initialAnimalsNumber >= 1) &&
                    (initialEnergy >= 1) &&
                    (breedNeededEnergy >= 1) &&
                    (breedLostEnergy >= 0) &&
                    (minMutations >= 0) &&
                    (maxMutations >= 0 && maxMutations >= minMutations) &&
                    (genotypeLength >= 1)
            ) {
                return new Parameters(mapHeight, mapWidth, VariantConverter.mapVariant(mapVariant.getValue()), initialPlantsQuantity,
                        consumedPlantEnergy, plantsGrowingADay, VariantConverter.foliageVariant(foliageVariant.getValue()), initialAnimalsNumber,
                        initialEnergy, breedNeededEnergy, breedLostEnergy, minMutations, maxMutations,
                        VariantConverter.mutationVariant(mutationVariant.getValue()), genotypeLength,
                        VariantConverter.behaviorVariant(behaviorVariant.getValue()));
            } else {
                return null;
            }

        } catch (NumberFormatException e) {
            return null;
        }
    }
}
