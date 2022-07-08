package com.vitruv.elevator;

public class  BuildingManager {
	private static BuildingFloor[] floors;

	public BuildingManager(){
		// 층수 5개로 초기화
		floors = new BuildingFloor[5];
		for (int i = 0; i < 5;i++){
			floors[i] = new BuildingFloor();
		}
	}
	

	public synchronized void elevatorArrival(int elevatorID,int curfloor, int passengers){
		floors[curfloor].setArrivedPassengers(elevatorID, passengers);		
	}

	public synchronized int getelevatorArrival(int elevatorID, int floor){
		return floors[floor].getArrivedPassengers(elevatorID);
	}

	public  synchronized void setFloorRequest(int currentfloor,int destfloor,int passengers ){
		floors[currentfloor].addDestinationRequest(destfloor,passengers);
	}

	public  synchronized int getFloorRequest(int currentfloor,int destfloor,int passengers ){
			return floors[currentfloor].getDestinationRequest(destfloor);
	}

	public synchronized void setapproachingElevator(int floor, int elevatorID)
	{
		floors[floor].setApproachingElevator(elevatorID);
	}
	

	public synchronized int getCurrentRequest(int currentfloor,int destfloor){
		return floors[currentfloor].getPassengerRequests(destfloor);
	}

	public synchronized void setCurrentRequest(int requestfloor,int destination,int passengers){
			floors[requestfloor].setPassengerRequests(destination,passengers);
	}

	public synchronized int getApproachingElevator(int floor){
		return floors[floor].getApproachingElevator();
	}

	public synchronized int locateRequest(int elevatorID){
		for (int i = 0; i < 5; i++){
			for (int j = 0; j < 5; j++){
				if(getCurrentRequest(i,j) > 0 && getApproachingElevator(i) == -1){
					setapproachingElevator(i, elevatorID);
					return i;
				}
			}
		}
		return -1;
		
	}

	public void printStates()
	{
		for(int i = 0; i < 5; ++i)
		{
			if(floors[i] != null)
			{
				System.out.println("Floor " + i + ":");
				floors[i].printFloorStates();
				System.out.print("각 엘리베이터에서 이 층으로 온 손님의 수 : ");
				for (int j = 0; j < 5; j++){
					System.out.print(floors[i].getArrivedPassengers(j)+" ");
					
				}
				System.out.println();
			}
		}
	}

	
	 

	
	

}
