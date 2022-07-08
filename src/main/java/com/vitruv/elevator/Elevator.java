package com.vitruv.elevator;

import java.util.ArrayList;

public class Elevator implements Runnable {

	private int elevatorID;

	private int currentFloor;

	private int numPassengers;

	private int totalLoadedPassengers;

	private int totalUnloadedPassengers;

	private ArrayList<ElevatorEvent> moveQueue;

	private int[] passengerDestinations;

	private BuildingManager BM;

	private boolean down;
	

	public Elevator(int elevatorID, BuildingManager manager){

		this.elevatorID = elevatorID;
		BM = manager;
		currentFloor = 0;
		numPassengers = 0;
		totalLoadedPassengers = 0;
		totalUnloadedPassengers = 0;
		moveQueue = new ArrayList<>();
		passengerDestinations = new int[5];
		down = true;
	}

	@Override
	public void run(){
		while(!Thread.interrupted()){
			if (moveQueue.isEmpty() && numPassengers == 0) nextFloor();
			if (!moveQueue.isEmpty()) {
				ElevatorEvent cur = moveQueue.get(0);
				if (cur.getExpectedArrival() == SimClock.getTime()){
					down = true;
					currentFloor = cur.getDestination();
					int passengers = passengerDestinations[currentFloor];

					if (numPassengers > 0){
							System.out.println("Time:  "+ SimClock.getTime()+ "  Elevator "+ elevatorID + " : "+currentFloor+" 층 도착 "+ passengers +" 명의 손님 내림 ");
							unloadPassengers(currentFloor,passengers);
							BM.elevatorArrival(elevatorID, currentFloor, passengers);

						    moveQueue.remove(0);	
					
					} else {

						pickUpPassengersUp(10);
						if (down == true){
							pickUpPassengersDown(10);
						}
						System.out.println("Time:  "+ SimClock.getTime()+ "  Elevator "+ elevatorID + " : "+currentFloor+" 층 도착 "+ numPassengers+ " 명의 손님 탑승 ");
						moveQueue.remove(0);
						BM.setapproachingElevator(currentFloor, -1);	
						
					}
				}
			}
		}
	}


	public void pickUpPassengersUp(int period){
		for (int j = currentFloor+1; j < 5;j++){
			 int requestpassengers = BM.getCurrentRequest(currentFloor, j);
			 if (requestpassengers > 0){
				 period =  period + 5 * Math.abs(j-currentFloor);
				 createElevatorEvent(j, SimClock.getTime()+period);
				 down = false;
				 loadPassengers(j,requestpassengers);
				 BM.setCurrentRequest(currentFloor, j, 0); 
				
				 
			 }
			 
		}
	}

	public void pickUpPassengersDown(int period){
		for (int j = currentFloor-1; j >= 0; j--){
			 int requestpassengers = BM.getCurrentRequest(currentFloor, j);
			 if (requestpassengers > 0){
				 period =  period + 5 * Math.abs(j-currentFloor);
				 createElevatorEvent(j, SimClock.getTime()+period);
				 loadPassengers(j,requestpassengers);
				 BM.setCurrentRequest(currentFloor, j, 0);
				
			 }
		}
	}

	public void nextFloor(){
		int nextfloor = BM.locateRequest(elevatorID);
		if(nextfloor != -1){
			int period = 5*Math.abs(nextfloor-currentFloor);
			System.out.println("Time:  "+ SimClock.getTime()+ "  Elevator "+ elevatorID + " : 이동중 " + currentFloor + "층에서 "+nextfloor+" 층으로 ");
			createElevatorEvent(nextfloor, SimClock.getTime()+period);
			
		}	
	}

	public void printElevatorState()
	{
		System.out.println("Elevator ID: " + elevatorID);
		System.out.println("이 엘리베이터를 탑승한 승객 수 " + totalLoadedPassengers);
		for (int i = 0; i < 5; i++){
			System.out.println("이 엘리베이터를 이용하여 " + i + " 층에서 " + BM.getelevatorArrival(elevatorID, i) + " 명 내림 ");
		}
		System.out.println("총 내린 승객 수 : " + totalUnloadedPassengers);
		System.out.println("현재 승객 수 : " + numPassengers);
	}

	public void createElevatorEvent(int d, int t){
		ElevatorEvent EE = new ElevatorEvent(d,t);
		addMoveQueue(EE);
	}

	public void addMoveQueue(ElevatorEvent ee){
		moveQueue.add(ee);
	}

	public void loadPassengers(int floor,int num){
		numPassengers+=num;
		totalLoadedPassengers+=num;
		passengerDestinations[floor]+=num;
		
	}
	
	public void unloadPassengers(int floor,int num){
		numPassengers-=num;
		totalUnloadedPassengers+=num;
		passengerDestinations[floor]-=num;
	}
	

	
	
	

}
