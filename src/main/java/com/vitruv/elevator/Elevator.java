package com.vitruv.elevator;

import lombok.Getter;
import lombok.Setter;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NavigableSet;

@Getter
@Setter
public class Elevator implements Runnable{
    // 작동여부
    private boolean operating;
    private int id;
    // 현재 상태
    private ElevatorState elevatorState;
    // 현재위치한 층
    private int currentFloor;
    // 엘리베이터가 이동하는 동한 지나가는 floor들
    private NavigableSet<Integer> floorStops;
    // 엘리베이터의 움직임 저장
    public Map<ElevatorState, NavigableSet<Integer>> floorStopsMap;

    public Elevator(int id){
        this.id = id;
        setOperating(true);
    }

    public void setOperating(boolean state){
        this.operating = state;

        if(!state){
            setElevatorState(ElevatorState.MAINTENANCE);
            this.floorStops.clear();
        } else {
            setElevatorState(ElevatorState.STATIONARY);
            this.floorStopsMap = new LinkedHashMap<>();

            // To let controller know that this elevator is ready to serve
            ElevatorController.updateElevatorLists(this);
        }

        setCurrentFloor(0);
    }

    public void move(){
        synchronized (ElevatorController.getInstance()){ // Synchronized over the ElevatorController singleton.
            Iterator<ElevatorState> iter = floorStopsMap.keySet().iterator();

            while(iter.hasNext()){
                elevatorState = iter.next();

                // Get the floors that elevator will pass in the requested direction
                floorStops = floorStopsMap.get(elevatorState);
                iter.remove();
                Integer currFlr = null;
                Integer nextFlr = null;

                // Start moving the elevator
                while (!floorStops.isEmpty()) {

                    if (elevatorState.equals(ElevatorState.UP)) {
                        currFlr = floorStops.pollFirst();
                        nextFlr = floorStops.higher(currFlr);

                    } else if (elevatorState.equals(ElevatorState.DOWN)) {
                        currFlr = floorStops.pollLast();
                        nextFlr = floorStops.lower(currFlr);
                    } else {
                        return;
                    }

                    setCurrentFloor(currFlr);

                    if (nextFlr != null) {
                        // This helps us in picking up any request that might come
                        // while we are on the way.
                        generateIntermediateFloors(currFlr, nextFlr);
                    } else {
                        setElevatorState(ElevatorState.STATIONARY);
                        ElevatorController.updateElevatorLists(this);
                    }

                    System.out.println("Elevator ID " + this.id + " | Current floor - " + getCurrentFloor() + " | next move - " + getElevatorState());

                    try {
                        Thread.sleep(1000); // Let people get off the elevator :P
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            try {
                // Wait till ElevatorController has scanned the state of all elevators.
                // This helps us to serve any intermediate requests that might come
                // while elevators are on their respective paths.
                ElevatorController.getInstance().wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    private void generateIntermediateFloors(int initial, int target){

        if(initial==target){
            return;
        }

        if(Math.abs(initial-target) == 1){
            return;
        }

        int n = 1;
        if(target-initial<0){
            // This means with are moving DOWN
            n = -1;
        }

        while(initial!=target){
            initial += n;
            if(!floorStops.contains(initial)) {
                floorStops.add(initial);
            }
        }
    }

    @Override
    public void run() {
        while(true){
            if(isOperating()){
                move();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                break;
            }
        }
    }
}
