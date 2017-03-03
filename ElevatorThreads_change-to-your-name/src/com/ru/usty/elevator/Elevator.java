package com.ru.usty.elevator;

import java.util.concurrent.Semaphore;

public class Elevator implements Runnable {
	
	private int elevatorIndex;
	private ElevatorScene scene;
	int elevatorSize;
	
	public Elevator(int elevatorIndex, ElevatorScene scene){
		this.elevatorIndex = elevatorIndex;
		this.scene = scene;
		this.elevatorSize = 6;
	}
	
	@Override
	public void run() {
		
		
		for(int num = 0; num < 6; num++){
			try {
				System.out.println("Adding a fucking person to elevator");
				this.scene.addPersonToElevator();
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//this.scene.decrementPeopleFromElevator(ElevatorScene.peopleInElevator, ElevatorScene.peopleInElevatorMutex);
	}

}
