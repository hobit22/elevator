package com.vitruv.elevator;

import lombok.Getter;

// 엘리베이터 요청
@Getter
public class ElevatorRequest {
    private int requestFloor;
    private int targetFloor;

    public ElevatorRequest(int requestFloor, int targetFloor){
        this.requestFloor = requestFloor;
        this.targetFloor = targetFloor;
    }

    public Elevator submitRequest(){
        return ElevatorController.getInstance().selectElevator(this);
    }
}