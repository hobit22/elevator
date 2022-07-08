package com.vitruv.elevator;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileNotFoundException;

@SpringBootApplication
public class ElevatorApplication {
    public static void main(String[] args) throws InterruptedException, FileNotFoundException {
        ElevatorSimulation es = new ElevatorSimulation();
        es.start();
        es.printBuildingState();
    }
}
