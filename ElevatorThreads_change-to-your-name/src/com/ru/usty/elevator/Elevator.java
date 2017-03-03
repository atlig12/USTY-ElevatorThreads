package com.ru.usty.elevator;

import java.util.concurrent.Semaphore;

public class Elevator implements Runnable {
	
	private int elevatorIndex;
	private ElevatorScene scene;
	int elevatorSize;
	
	
	public Elevator(int elevatorIndex, ElevatorScene scene){
		this.elevatorIndex = elevatorIndex;
		this.scene = scene;
		ElevatorScene.enterElevator.release(6); //Create 6 empty spaces in elevator before scene is started.
	}
	
	@Override
	public void run() {
		int x = ElevatorScene.scene.enterElevator.availablePermits();
		System.out.println(x + "counter i move elevator");
		while(true){
			
				if(x == 0){
					try {
						ElevatorScene.elevatorWaitMutex.acquire();
						ElevatorScene.scene.elevatorMove();	
						ElevatorScene.elevatorWaitMutex.release();
						
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					};
					
					
					
				}
		}
		
		
		
	}

}
