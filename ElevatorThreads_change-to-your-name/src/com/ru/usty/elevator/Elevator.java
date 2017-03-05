package com.ru.usty.elevator;

import java.util.concurrent.Semaphore;

public class Elevator implements Runnable {
	
	private int elevatorIndex;
	private ElevatorScene scene;
	int elevatorSize;
	
	
	public Elevator(int elevatorIndex, ElevatorScene scene){
		this.elevatorIndex = elevatorIndex;
		this.scene = scene;
		ElevatorScene.enterElevator.release(6);
		 //Create 6 empty spaces in elevator before scene is started.
	}
	
	@Override
	public void run() {
		while (true){
			try {
				int getThreadsToRelease = this.scene.getNumberOfPeopleInElevator(elevatorIndex);
				System.out.println("HAAAAALLÓÓÓÓ: " + getThreadsToRelease);
				for (int i = 0; i < ElevatorScene.enterElevator.availablePermits() - getThreadsToRelease; i++){
					Thread.sleep(ElevatorScene.VISUALIZATION_WAIT_TIME);
					ElevatorScene.getInElevatorMutex.get(this.scene.currentElevatorFloor).release();					
				}
				Thread.sleep(ElevatorScene.VISUALIZATION_WAIT_TIME);
				getThreadsToRelease = this.scene.getNumberOfPeopleInElevator(elevatorIndex);
				
				for (int i = 0; i < ElevatorScene.enterElevator.availablePermits() - getThreadsToRelease; i++){
					
					ElevatorScene.getInElevatorMutex.get(this.scene.currentElevatorFloor).acquire();					
				}
				
				Thread.sleep(ElevatorScene.VISUALIZATION_WAIT_TIME);
				
				this.scene.elevatorMove(elevatorIndex);
				
				int destination = this.scene.getNumberOfPeopleInElevator(elevatorIndex);
				
				for (int i = 0; i < destination; i++) {
					Thread.sleep(ElevatorScene.VISUALIZATION_WAIT_TIME);
					ElevatorScene.getOutOfElevatorMutex.get(this.scene.currentElevatorFloor).release();	
				}
				
				destination = this.scene.getNumberOfPeopleInElevator(elevatorIndex);
				
				for (int i = 0; i < destination; i++) {
					
					ElevatorScene.getOutOfElevatorMutex.get(this.scene.currentElevatorFloor).acquire();					
				}
				Thread.sleep(ElevatorScene.VISUALIZATION_WAIT_TIME);
			}
				
			catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
					
		}
		
//		for(int i = 0; i < elevatorSize - getThreadsToRelease; i++){
//			ElevatorScene.elevatorGoInMutex.get(this.scene.elevatorFloor).release();
//		}
		//ElevatorScene.scene.elevatorMove();	
		
		/*int x = ElevatorScene.scene.enterElevator.availablePermits();
		System.out.println(x + "counter i move elevator");
		while(true){
			
				if(x == 6){
					try {
						ElevatorScene.elevatorWaitMutex.acquire();
						ElevatorScene.scene.elevatorMove();	
						ElevatorScene.elevatorWaitMutex.release();
						
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					};
					
					
					
				}
		}*/
		
		
		
	}

}
