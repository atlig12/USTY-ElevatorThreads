package com.ru.usty.elevator;

public class Person implements Runnable{
	int sourceFloor, destinationFloor;
	
	public Person(int sourceFloor, int destinationFloor){
		this.sourceFloor = sourceFloor;
		this.destinationFloor = destinationFloor;
	}
	
	
	
	@Override
	public void run() {
		try {
			
			ElevatorScene.elevatorWaitMutex.acquire(); 
			ElevatorScene.enterElevator.acquire(); //wait
			ElevatorScene.scene.addPersonToElevator();
			ElevatorScene.elevatorWaitMutex.release();
			System.out.println("New person enters the elevator " + ElevatorScene.enterElevator.availablePermits() + " available spots left");
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		//ElevatorScene.scene.decrementNumberOfPeopleWaitingAtFloor(sourceFloor);
		//ElevatorScene.scene.decrementPeopleFromElevator();
		//ElevatorScene.scene.incrementNumberOfPersonsWaitingAtFloor(destinationFloor);
		//ElevatorScene.scene.elevatorMove();
		//System.out.println("Person Thread released");
	}
	

}
