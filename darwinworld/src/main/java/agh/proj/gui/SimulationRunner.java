package agh.proj.gui;

import agh.proj.model.Animal;
import agh.proj.model.Globe;
import agh.proj.model.Parameters;
import agh.proj.model.Vector2d;
import agh.proj.model.interfaces.MapChangeListener;
import agh.proj.model.interfaces.WorldElement;
import agh.proj.model.interfaces.WorldMap;
import agh.proj.simulation.Simulation;
import agh.proj.simulation.SimulationEngine;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.transform.Rotate;

import java.util.ArrayList;
import java.util.List;

public class SimulationRunner implements MapChangeListener {
    private Parameters parameters = null;
    private WorldMap worldMap;
    @FXML
    private GridPane mapGrid;
    private int cellDim = 80;
    @FXML
    private Button pauseResumeButton;
    private SimulationEngine se = null;

    @FXML
    private TextField animalNumber;
    @FXML
    private TextField grassNumber;
    @FXML
    private TextField freeSpaces;
    @FXML
    private TextField popularGenotype;
    @FXML
    private TextField averageEnergy;
    @FXML
    private TextField averageLifeSpan;
    @FXML
    private TextField averageChildren;

    @FXML
    private VBox list;
    private int toTrack = 0;
    @FXML
    private Label tracked;
    @FXML
    private TextField positionTrack;
    @FXML
    private TextField ageTrack;
    @FXML
    private TextFlow genomeTrack;
    @FXML
    private TextField energyTrack;
    @FXML
    private TextField eatenGrassTrack;
    @FXML
    private TextField childrenTrack;
    @FXML
    private TextField descendantsTrack;
    @FXML
    private TextField deathTrack;

    @FXML
    private HBox hBox;

    public void initialize() {
        if (parameters == null) {
            return;
        }

        int max = Math.max(parameters.getMapHeight(), parameters.getMapWidth());
        if (max > 6) cellDim = 50;
        if (max > 10) cellDim = 40;
        if (max > 14) cellDim = 30;
        Globe map = new Globe(parameters.getMapWidth(), parameters.getMapHeight(), parameters);
        worldMap = map;
        Simulation simulation = new Simulation(map);
        simulation.register(this);
        se = new SimulationEngine(new ArrayList<>(List.of(simulation)));
        try {
            se.runAsyncInThreadPool();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void initializeMapStats() {
        animalNumber.setText(String.valueOf(worldMap.getAnimalCount()));
        grassNumber.setText(String.valueOf(worldMap.getGrassCount()));
        freeSpaces.setText(String.valueOf(worldMap.numberOfEmptySpaces()));
        popularGenotype.setText(worldMap.mostPopularGenome().toString());
        averageEnergy.setText(String.valueOf(worldMap.avgEnergy()));
        if (worldMap.avgAgeForDead() != -1)
            averageLifeSpan.setText(String.valueOf(worldMap.avgAgeForDead()));
        averageChildren.setText(String.valueOf(worldMap.avgChildren()));
    }

    private void initializeAnimalList() {
        Platform.runLater(() -> {
            initializeMapStats();

            int animalCount = worldMap.getAnimalCount();

            list.getChildren().clear();
            for (int i = 1; i <= animalCount; i++) {
                Label animalLabel = new Label("Animal " + i);
                animalLabel.setMinWidth(80);
                if (worldMap.getAnimal(i - 1) == null) {
                    animalLabel.setTextFill(Color.RED);
                }
                if (i == toTrack) {
                    animalLabel.setStyle("-fx-font-weight: bold");
                }
                animalLabel.setPadding(new Insets(10));
                animalLabel.setBorder(new Border(new javafx.scene.layout.BorderStroke(Color.BLACK,
                        javafx.scene.layout.BorderStrokeStyle.SOLID, null, new javafx.scene.layout.BorderWidths(1))));

                list.getChildren().add(animalLabel);
                animalLabel.setOnMouseClicked(event -> handleAnimalLabelClick(animalLabel));
            }
        });
    }

    private void handleAnimalLabelClick(Label label) {
        String selectedItem = label.getText();
        if (selectedItem == null) return;

        tracked.setText(selectedItem);
        tracked.setStyle("-fx-font-weight: bold; -fx-font-size: 20");
        String number = selectedItem.split(" ")[1];
        toTrack = Integer.parseInt(number);
        initializeAnimalList();
        updateTracking();
        Platform.runLater(this::drawMap);
    }

    private void updateTracking() {
        if (toTrack == 0) return;
        Animal animal = worldMap.getAnimal(toTrack - 1);
        if (animal == null) {
            animal = worldMap.getDeadAnimal(toTrack - 1);
            if (animal == null) {
                return;
            }
        }
        positionTrack.setText(String.valueOf(animal.getPosition()));
        ageTrack.setText(String.valueOf(animal.getAge()));

        genomeTrack.getChildren().clear();
        String genome = String.valueOf(animal.getGenotype());
        int currentGen = animal.getCurrentOrientationIndex();
        int toColor = 3 * currentGen + 1;
        int i = 0;

        for (char c : genome.toCharArray()) {
            Text letter = new Text(String.valueOf(c));
            letter.setStyle("-fx-font-weight: bold");
            if (i == toColor) {
                letter.setFill(Color.GREEN);
            }
            TextFlow textFlow = new TextFlow(letter);

            genomeTrack.getChildren().add(textFlow);
            i++;
        }

        energyTrack.setText(String.valueOf(animal.getEnergy()));
        eatenGrassTrack.setText(String.valueOf(animal.getNumberOfEatedGrass()));
        childrenTrack.setText(String.valueOf(animal.getChildrenNo()));
        descendantsTrack.setText(String.valueOf(animal.getNumberOfDescendats()));
        if (animal.getEnergy() == 0)
            deathTrack.setText("DIED:"+animal.getDateOfDeath());
        else
            deathTrack.setText("ALIVE");
    }

    public void setParameters(Parameters parameters) {
        this.parameters = parameters;
        initialize();
    }

    synchronized public void simulationPauseResumeClicked() {
        String value = pauseResumeButton.getText();
        if (value.equals("PAUSE")) {
            se.pauseResumeThreads(true);
            pauseResumeButton.setText("RESUME");
        }
        else if (value.equals("RESUME")) {
            se.pauseResumeThreads(false);
            pauseResumeButton.setText("PAUSE");
        }
    }
    @Override
    public void mapChanged() {
        Platform.runLater(this::drawMap);
        initializeAnimalList();
        Platform.runLater(this::updateTracking);
    }

    private void drawGrid() {
        Vector2d upperRight = worldMap.getBounds();
        int xDim = upperRight.getX() + 2;
        int yDim = upperRight.getY() + 2;

        for (int i = 0; i  < xDim; i++) {
            mapGrid.getColumnConstraints().add(new ColumnConstraints(cellDim));
        }
        for (int i = 0; i  < yDim; i++) {
            mapGrid.getRowConstraints().add(new RowConstraints(cellDim));
        }

        Label label = new Label("y\\x");
        mapGrid.add(label, 0, 0);
        GridPane.setHalignment(label, HPos.CENTER);

        for (int i = 1; i < yDim; i++) {
            label = new Label(String.valueOf(yDim - i - 1));
            mapGrid.add(label, 0, i);
            GridPane.setHalignment(label, HPos.CENTER);
        }
        for (int i = 1; i < xDim; i++) {
            label = new Label(String.valueOf(i - 1));
            mapGrid.add(label, i, 0);
            GridPane.setHalignment(label, HPos.CENTER);
        }

        for (int i = 1; i < xDim; i++) {
            for (int j = 1; j < yDim; j++) {
                WorldElement worldElement = worldMap.objectAt(new Vector2d(i - 1, j - 1));

                StackPane tile = new StackPane();

                String url = worldMap.biomeTexture(i - 1, j - 1);
                ImageView backgroundImageView = new ImageView(new Image(url));
                backgroundImageView.setFitWidth(cellDim - 1);
                backgroundImageView.setFitHeight(cellDim - 1);

                tile.getChildren().add(backgroundImageView);

                if (worldElement != null) {
                    String elementUrl = worldElement.getImage();
                    int toRotate = worldElement.getRotation();
                    int numberOfAnimals = worldMap.numberOfAnimalsOnPosition(new Vector2d(i - 1, j - 1));
                    if (numberOfAnimals > 0) {
                        for (Animal animal : worldMap.getAnimalsAtPosition(new Vector2d(i - 1, j - 1))) {
                            if (animal.isTracked(toTrack - 1)) {
                                elementUrl = "trackedcow.png";
                            }
                        }

                    }

//                    boolean aLot = false;
                    if (numberOfAnimals > 1) {
                        toRotate = 0;
                        elementUrl = elementUrl.substring(0, elementUrl.length() - 4);
                        if (numberOfAnimals > 4) {
//                            aLot = true;
                            elementUrl = elementUrl + 5 + ".png";
                        }
                        else {
                            elementUrl = elementUrl + numberOfAnimals + ".png";
                        }
                    }

                    ImageView overlayImageView = new ImageView(new Image(elementUrl));
                    overlayImageView.setFitWidth(cellDim - 1);
                    overlayImageView.setFitHeight(cellDim - 1);
                    Rotate rotate = new Rotate(toRotate, (double) (cellDim - 1) / 2, (double) (cellDim - 1) / 2);
                    overlayImageView.getTransforms().add(rotate);

//                    if (aLot) {
//                        overlayImageView.setFitWidth(cellDim / 1.5);
//                        overlayImageView.setFitHeight(cellDim / 1.5);
//                        tile.getChildren().add(overlayImageView);
//                        tile.getChildren().add(new Text(String.valueOf(numberOfAnimals)));
//                    }
//                    else {
//                        tile.getChildren().add(overlayImageView);
//                    }

                    tile.getChildren().add(overlayImageView);

                }
                Border border = new Border(new BorderStroke(Color.BLACK,
                        BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));
                tile.setBorder(border);
                mapGrid.add(tile, i, yDim - j);
            }
        }
    }

    private void drawMap() {
        clearGrid();
        drawGrid();
    }

    private void clearGrid() {
        mapGrid.getChildren().retainAll(mapGrid.getChildren().get(0));
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
    }
}
