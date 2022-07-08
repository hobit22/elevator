package com.vitruv.elevator;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PassengerArrival {
	//요청보낸 승객의 수
	private int numPassengers;
	//목적 층
	private int destinationFloor;
	//반복시간
	private int timePeriod;
	//expected arrival time of this request
	private int expectedTimeOfArrival;

	public PassengerArrival(int p, int f, int r,int eta) {
		this.numPassengers = p;
		this.destinationFloor = f;
		this.timePeriod = r;
		this.expectedTimeOfArrival = eta;
	}

}
