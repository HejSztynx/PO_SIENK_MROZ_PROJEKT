package agh.proj.gui;

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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class SimulationRunner implements MapChangeListener {
    private Parameters parameters = null;
    private WorldMap worldMap;
    @FXML
    private GridPane mapGrid;
    private int cellDim = 80;

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
        SimulationEngine se = new SimulationEngine(new ArrayList<>(List.of(simulation)));
        try {
            se.runAsyncInThreadPool();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void setParameters(Parameters parameters) {
        this.parameters = parameters;
        initialize();
    }

    @Override
    public void mapChanged() {
        Platform.runLater(this::drawMap);
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
//                    tile.setStyle("-fx-background-color: lightgreen");
                tile.setStyle("-fx-background-color: " + worldMap.biomeColor(i - 1, j - 1));


                Border border = new Border(new BorderStroke(Color.BLACK,
                        BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));
                tile.setBorder(border);


                if (worldElement != null) {
                    Text text = new Text(worldElement.toString());
                    tile.getChildren().addAll(text);
                }
                mapGrid.add(tile, i, yDim - j);
            }
        }
    }

    private void drawMap() {
        clearGrid();
        drawGrid();
    }

    private void clearGrid() {
        mapGrid.getChildren().retainAll(mapGrid.getChildren().get(0)); // hack to retain visible grid lines
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
    }
}
