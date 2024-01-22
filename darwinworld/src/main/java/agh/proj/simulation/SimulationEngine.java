package agh.proj.simulation;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SimulationEngine {
    private final ArrayList<Simulation> simulations;

    public SimulationEngine(ArrayList<Simulation> simulations) {
        this.simulations = simulations;
    }

    public void runAsyncInThreadPool() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        for (Simulation simulation : simulations) {
            executorService.submit(simulation);
        }
//        awaitSimulationsEnd(executorService);
    }

    private void awaitSimulationsEnd(ExecutorService executorService) throws InterruptedException {
        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.SECONDS);
    }

    synchronized public void pauseResumeThreads(boolean pause) {
        for (Simulation simulation : simulations) {
            simulation.setPause(pause);
        }
    }

    synchronized public void endThreads() {
        for (Simulation simulation : simulations) {
            simulation.setToEnd(true);
        }
    }
}
