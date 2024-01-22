package agh.proj.gui;

import agh.proj.model.Parameters;
import agh.proj.model.util.CSVParameterSaver;
import agh.proj.model.util.CSVReader;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class SimulationPresenter {
    private static final String FIRST_CSV_LINE = "preset,mapHeight,mapWidth,mapVariant,initialPlantsQuantity," +
            "consumedPlantEnergy,plantsGrowingADay,foliageVariant,initialAnimalsNumber,initialEnergy," +
            "breedNeededEnergy,breedLostEnergy,minMutations,maxMutations,mutationVariant,genotypeLength,behaviorVariant";
    private final String projectDirectory = System.getProperty("user.dir");
    private final String presetsFileLocation = projectDirectory + "/presets/presets.csv";

    private SimulationApp mainApp;

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
    private TextField csvName;
    @FXML
    private ComboBox<String> presets;

    private final ArrayList<String> values = new ArrayList<>();

    public void setMainApp(SimulationApp app) {
        mainApp = app;
    }

    public void initialize() {
        initializeCSVDirectory();

        initializePresets();
        csvName.setText("Simulation");
        presets.setOnAction(event -> {
            String chosenPreset = presets.getValue();
            if (chosenPreset == null) return;
            if (chosenPreset.equals("Custom")) {
                clearMenu();
                return;
            }
            CSVReader csvReader = new CSVReader();
            ArrayList<String> presetParameters = csvReader.readLineData(presetsFileLocation, chosenPreset);
            fillMenu(presetParameters);
        });
    }

    private void initializePresets() {
        updatePresets();
        presets.setValue("Custom");
    }

    private void updatePresets() {
        CSVReader csvReader = new CSVReader();
        ArrayList<String> presetNames = new ArrayList<>();
        presetNames.add("Custom");
        presetNames.addAll(csvReader.readFirstColumn(presetsFileLocation));

        presets.setItems(FXCollections.observableArrayList(presetNames));
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
            writer.write(FIRST_CSV_LINE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void fillMenu(ArrayList<String> values) {
        mapHeight.setText(values.get(0));
        mapWidth.setText(values.get(1));
        mapVariant.setValue(values.get(2));
        initialPlantsQuantity.setText(values.get(3));
        consumedPlantEnergy.setText(values.get(4));
        plantsGrowingADay.setText(values.get(5));
        foliageVariant.setValue(values.get(6));
        initialAnimalsNumber.setText(values.get(7));
        initialEnergy.setText(values.get(8));
        breedNeededEnergy.setText(values.get(9));
        breedLostEnergy.setText(values.get(10));
        minMutations.setText(values.get(11));
        maxMutations.setText(values.get(12));
        mutationVariant.setValue(values.get(13));
        genotypeLength.setText(values.get(14));
        behaviorVariant.setValue(values.get(15));
        csvName.setText("Simulation");
    }

    private void clearMenu() {
        mapHeight.setText("");
        mapWidth.setText("");
        mapVariant.setValue("Globe");
        initialPlantsQuantity.setText("");
        consumedPlantEnergy.setText("");
        plantsGrowingADay.setText("");
        foliageVariant.setValue("Verdant Equator");
        initialAnimalsNumber.setText("");
        initialEnergy.setText("");
        breedNeededEnergy.setText("");
        breedLostEnergy.setText("");
        minMutations.setText("");
        maxMutations.setText("");
        mutationVariant.setValue("Fully Random");
        genotypeLength.setText("");
        behaviorVariant.setValue("Full Predestination");
        csvName.setText("Simulation");
    }

    private void downloadValues() {
        values.clear();
        values.addAll(List.of(mapHeight.getText(), mapWidth.getText(), mapVariant.getValue(), initialPlantsQuantity.getText(),
                consumedPlantEnergy.getText(), plantsGrowingADay.getText(),
                foliageVariant.getValue(), initialAnimalsNumber.getText(), initialEnergy.getText(), breedNeededEnergy.getText(),
                breedLostEnergy.getText(), minMutations.getText(), maxMutations.getText(),
                mutationVariant.getValue(), genotypeLength.getText(), behaviorVariant.getValue()));
    }

    public void onSimulationStartClicked() {
        downloadValues();

        Parameters parameters = checkParameters(values);
        if (parameters != null) {
            mainApp.openSecondView(parameters,csvName.getText());
        } else System.out.println("NIE GIT");
    }

    public void onSavePresetsClicked() {
        downloadValues();
        Parameters parameters = checkParameters(values);
        if (parameters != null) {
            CSVParameterSaver saver = new CSVParameterSaver();
            saver.save(values, presetsFileLocation);
            updatePresets();
        } else System.out.println("NIE GIT");

    }

    public void onDeletePresetsClicked() {
        String toDelete = presets.getValue();
        if (toDelete.equals("Custom")) return;
        CSVParameterSaver csvParameterSaver = new CSVParameterSaver();
        csvParameterSaver.delete(presetsFileLocation, toDelete);
        initializePresets();
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
