package com.vitruv.elevator;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class ElevatorApplication {
    private static ElevatorController elevatorController;
    private static Thread elevatorControllerThread;

    public static void main(String[] args) {

        elevatorController = ElevatorController.getInstance();
        elevatorControllerThread = new Thread(elevatorController);
        elevatorControllerThread.start();

        int choice;

        while (true) {

            Scanner input = new Scanner(System.in);
            System.out.println("Enter choice (number): \n 1. 엘베 상태 확인 \n 2. 엘베 요청");
            choice = input.nextInt();

            if (choice == 1) {
                input = new Scanner(System.in);
                System.out.println("엘리베이터를 고르새요 (0 ~ 3): ");
                choice = input.nextInt();

                Elevator elevator = ElevatorController.getInstance().getElevatorList().get(choice);
                System.out.println("Elevator - " + elevator.getId() + " | Current floor - " + elevator.getCurrentFloor()
                        + " | Status - " + elevator.getElevatorState());
            }

            if (choice == 2) {
                input = new Scanner(System.in);
                System.out.println("출발 층을 입력하세요 ");
                int reqestFloor = input.nextInt();

                input = new Scanner(System.in);
                System.out.println("목적지 층을 입력하세요  ");
                int targetFloor = input.nextInt();

                ElevatorRequest elevatorRequest = new ElevatorRequest(reqestFloor, targetFloor);
                Elevator elevator = elevatorRequest.submitRequest();

            }

        }

    }
}
