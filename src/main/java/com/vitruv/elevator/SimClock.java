package com.vitruv.elevator;

// 시뮬레이션 시간 체크를 위한 클래스
public class SimClock {

    private static int simTime;

	// 0부터 시작
    public SimClock() {
        simTime = 0;
    }

	// 1씩 증가
    public static void tick() {
        simTime += 1;
    }


    public static int getTime() {
        return simTime;
    }

}
