package agh.proj.gui;

import agh.proj.model.Parameters;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class SimulationApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("menu.fxml"));
        BorderPane viewRoot = loader.load();
        SimulationPresenter presenter = loader.getController();
        presenter.setMainApp(this);

        configureStage(primaryStage, viewRoot);
        primaryStage.setTitle("Parameters");

        primaryStage.show();
    }

    private void configureStage(Stage stage, BorderPane viewRoot) {
        var scene = new Scene(viewRoot);
        stage.setScene(scene);
        stage.minWidthProperty().bind(viewRoot.minWidthProperty());
        stage.minHeightProperty().bind(viewRoot.minHeightProperty());
    }

    public void openSecondView(agh.proj.model.Parameters parameters) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("simulation.fxml"));
            BorderPane root = loader.load();
            SimulationRunner simulationRunnerController = loader.getController();
            simulationRunnerController.setParameters(parameters);

            Stage secondStage = new Stage();
            configureStage(secondStage, root);
            secondStage.setTitle("Simulation");
            secondStage.setWidth(1000);
            secondStage.setHeight(800);

            secondStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
