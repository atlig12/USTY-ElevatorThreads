package com.ru.usty.elevator;

public class Person implements Runnable{
	int sourceFloor, destinationFloor;
	private ElevatorScene scene;
	
	public Person(int sourceFloor, int destinationFloor, ElevatorScene scene){
		this.sourceFloor = sourceFloor;
		this.destinationFloor = destinationFloor;
		this.scene = scene;
	}
	
	
	
	@Override
	public void run() {
		while(ElevatorScene.enterElevator.availablePermits() != 0) {
			try {
//				ElevatorScene.elevatorWaitMutex.release();
				
//				ElevatorScene.enterElevator.acquire(); //wait
				
				ElevatorScene.getInElevatorMutex.get(sourceFloor).acquire();
				this.scene.decrementNumberOfPeopleWaitingAtFloor(sourceFloor);
				this.scene.addPersonToElevator(sourceFloor);
				
				System.out.println("New person enters the elevator " + ElevatorScene.enterElevator.availablePermits() + " available spots left");
				
				
//				ElevatorScene.elevatorWaitMutex.acquire();
				ElevatorScene.getOutOfElevatorMutex.get(destinationFloor).acquire();
//				ElevatorScene.enterElevator.release();
				this.scene.decrementPeopleFromElevator(0);				
				this.scene.personExitsAtFloor(destinationFloor);
				 	
				
					
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
//		System.out.println("Kalla í ElevatorMove");
//		
//		int y = ElevatorScene.scene.getCurrentFloorForElevator(sourceFloor);
//		System.out.println("currentFloor" + y);
//		ElevatorScene.scene.elevatorMove();
//		System.out.println("currentFloor: " + y);
//		ElevatorScene.scene.personExitsAtFloor(destinationFloor);
//		
//		int x = ElevatorScene.scene.getExitedCountAtFloor(destinationFloor);
//		System.out.println("fjöldi fólks sem fer út á hæðinni: " + x);
		
		
	
		
		
		
		
		//ElevatorScene.scene.decrementNumberOfPeopleWaitingAtFloor(sourceFloor);
		//ElevatorScene.scene.decrementPeopleFromElevator();
		//ElevatorScene.scene.incrementNumberOfPersonsWaitingAtFloor(destinationFloor);
		//ElevatorScene.scene.elevatorMove();
		//System.out.println("Person Thread released");
	}
	

}
