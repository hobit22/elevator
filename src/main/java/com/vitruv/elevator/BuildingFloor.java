package com.vitruv.elevator;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BuildingFloor {
	
	private int[] totalDestinationRequests;
	private int[] arrivedPassengers;
	private int[] passengerRequests;
	private int approachingElevator;
	
	
	BuildingFloor(){
		totalDestinationRequests = new int[5];
		arrivedPassengers = new int[5];
		passengerRequests = new int[5];
		approachingElevator = -1;
	}

	public void addDestinationRequest(int destfloor,int num){
		totalDestinationRequests[destfloor] += num;
	}

	public int getDestinationRequest(int floor){
		return totalDestinationRequests[floor];
	}

	public void setArrivedPassengers(int elevatorID,int num){
		arrivedPassengers[elevatorID] += num;
	}

	public int getArrivedPassengers(int elevatorID){
		return arrivedPassengers[elevatorID];
	}

	public void setPassengerRequests(int floor, int num){
		passengerRequests[floor] = num;
	}

	public int getPassengerRequests(int floor){
		return passengerRequests[floor];
	}

	public void printFloorStates()
	{
		System.out.println("이 엘리베이터는 현재 " + approachingElevator + " 로 이동중");

		int totalrequest = 0;
		int currentrequest = 0;
		int arrivedpassengers = 0;
		for(int i = 0; i < 5; i++)
		{
			totalrequest += totalDestinationRequests[i];
			currentrequest += passengerRequests[i];
			arrivedpassengers += arrivedPassengers[i];
			
		}
		System.out.println("이 층에서 엘리베이터를 요청한 손님 수 "+ totalrequest);
		System.out.println("이 층에 도착한 승객 수 " + arrivedpassengers);
		System.out.println("이 층에서 현재 대기중인 승객 수"+ currentrequest);
		
	}
	

}
