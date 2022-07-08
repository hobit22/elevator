package com.vitruv.elevator;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ElevatorEvent {
	// 목적지
	private int destination;
	// 예상 도착시간
	private int expectedArrival;

	public ElevatorEvent(int d, int t){
		destination = d;
		expectedArrival = t;
	}

}
