package com.ru.usty.elevator;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

/**
 * The base function definitions of this class must stay the same
 * for the test suite and graphics to use.
 * You can add functions and/or change the functionality
 * of the operations at will.
 *
 */

public class ElevatorScene {
	
	public static Semaphore enterElevator;
	
	public static Semaphore personCountMutex;
	
	public static Semaphore elevatorWaitMutex;
	
	public static ElevatorScene scene;
	

	//TO SPEED THINGS UP WHEN TESTING,
	//feel free to change this.  It will be changed during grading
	public static final int VISUALIZATION_WAIT_TIME = 500;  //milliseconds

	private int numberOfFloors;
	private int numberOfElevators;
	public int elevatorStartFloor; //Setting default floor of beginning of scene
	public int currentElevatorFloor; //Current floor of the elevator.
	
	
	ArrayList<Integer> personCount; //use if you want but
									//throw away and
									//implement differently
									//if it suits you
	ArrayList<Integer> exitedCount = null;
	public int peopleInElevatorCount;
	public static Semaphore exitedCountMutex;
	public static Semaphore peopleInElevator;
	public static Semaphore peopleInElevatorMutex;

	//Base function: definition must not change
	//Necessary to add your code in this one
	public void restartScene(int numberOfFloors, int numberOfElevators) {
		
		scene = this;
		enterElevator = new Semaphore(0); //kemst enginn inn fyrr en búið er að signala
		personCountMutex = new Semaphore(1); //fyrsti sem kemur inn má koma inn en setur hana niður í 0, ekki fyrr en hann fer út og signalar þá fer hún í 1 
		elevatorWaitMutex = new Semaphore(1);
	
		peopleInElevatorMutex = new Semaphore(1);
		peopleInElevatorCount = 0;
		elevatorStartFloor = 0;
		currentElevatorFloor = 0;
		
		Elevator elevator = new Elevator(0, this);
		Thread thread = new Thread(elevator);
		thread.start();
		
		System.out.println("Lyfta startar me� " + enterElevator.availablePermits() + " Laus pl�s");
		
		/*
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				Elevator.this.
				System.out.println("New Thread");
				
			}
		}).start();
*/
		/**
		 * Important to add code here to make new
		 * threads that run your elevator-runnables
		 * 
		 * Also add any other code that initializes
		 * your system for a new run
		 * 
		 * If you can, tell any currently running
		 * elevator threads to stop
		 * aðgengilegt hvaðan sem er frá.
		 */

		this.numberOfFloors = numberOfFloors;
		this.numberOfElevators = numberOfElevators;
		

		personCount = new ArrayList<Integer>();
		for(int i = 0; i < numberOfFloors; i++) {
			this.personCount.add(0);
		}

		if(exitedCount == null) {
			exitedCount = new ArrayList<Integer>();
		}
		else {
			exitedCount.clear();
		}
		for(int i = 0; i < getNumberOfFloors(); i++) {
			this.exitedCount.add(0);
		}
		exitedCountMutex = new Semaphore(1);
	}

	//Base function: definition must not change
	//Necessary to add your code in this one
	public Thread addPerson(int sourceFloor, int destinationFloor) {
		//System.out.println("Function addPerson");
		
		Thread thread = new Thread(new Person(sourceFloor, destinationFloor));
		thread.start();
		
		
		/**
		 * Important to add code here to make a
		 * new thread that runs your person-runnable
		 * 
		 * Also return the Thread object for your person
		 * so that it can be reaped in the testSuite
		 * (you don't have to join() yourself)
		 */

		//dumb code, replace it!
		personCount.set(sourceFloor, personCount.get(sourceFloor) + 1);
		return thread;  //this means that the testSuite will not wait for the threads to finish
	}
	
	public void addPersonToElevator() throws InterruptedException{
			try{
				
				this.peopleInElevatorMutex.acquire();
				Thread.sleep(ElevatorScene.VISUALIZATION_WAIT_TIME);
				this.peopleInElevatorCount ++;
				this.peopleInElevatorMutex.release();	
				
				
			}
			catch(InterruptedException e){
				e.printStackTrace();
			}
		
		System.out.println(this.peopleInElevatorCount + " Counterinn okkar");
		
	}
	
	public void decrementPeopleFromElevator(){
		
		try{
			
			this.peopleInElevatorMutex.acquire();
			Thread.sleep(ElevatorScene.VISUALIZATION_WAIT_TIME);
			this.peopleInElevatorCount --;
			this.peopleInElevatorMutex.release();		
			
		}
		catch(InterruptedException e){
			e.printStackTrace();
		}
	System.out.println(this.peopleInElevatorCount + " Counterinn okkar");
	}
		
	public void incrementNumberOfPersonsWaitingAtFloor(int floor){
			
			try {
				ElevatorScene.personCountMutex.acquire();
					ElevatorScene.scene.personCount.set(floor, (personCount.get(floor) + 1));
				ElevatorScene.personCountMutex.release();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

	//Base function: definition must not change, but add your code
	public int getCurrentFloorForElevator(int elevator) {
		//System.out.println("Function gerCurrentFloorForElevator");
		//dumb code, replace it!
		return this.currentElevatorFloor;
	}

	//Base function: definition must not change, but add your code
	public int getNumberOfPeopleInElevator(int elevator) {
		//System.out.println("Function getNumberOfPeopleInElevator");
		//dumb code, replace it!
		return this.peopleInElevatorCount;
	}

	//Base function: definition must not change, but add your code
	public int getNumberOfPeopleWaitingAtFloor(int floor) {
		//System.out.println("Function getNumberOfPeopleWaitingAtFloor");
		
		return personCount.get(floor);
	}
	
	public void decrementNumberOfPeopleWaitingAtFloor(int floor) {
		//System.out.println("Function decrementNumberOfPeopleWaitingAtFloor");
		try {
			personCountMutex.acquire(); //verður hérna 0
				personCount.set(floor, (personCount.get(floor) - 1));
			personCountMutex.release(); //verður hérna 1
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}

	//Base function: definition must not change, but add your code if needed
	public int getNumberOfFloors() {
		//System.out.println("Function getNumberOfFloors");
		return numberOfFloors;
	}

	//Base function: definition must not change, but add your code if needed
	public void setNumberOfFloors(int numberOfFloors) {
		//System.out.println("Function setNumbersOfFloors");
		this.numberOfFloors = numberOfFloors;
	}

	//Base function: definition must not change, but add your code if needed
	public int getNumberOfElevators() {
		//System.out.println("Function getNumberOfElevators");
		return numberOfElevators;
	}

	//Base function: definition must not change, but add your code if needed
	public void setNumberOfElevators(int numberOfElevators) {
		//System.out.println("Function setNumberOfElevators");
		this.numberOfElevators = numberOfElevators;
	}

	//Base function: no need to change unless you choose
	//				 not to "open the doors" sometimes
	//				 even though there are people there
	public boolean isElevatorOpen(int elevator) {
		//System.out.println("Function isElevatorOpen");
		return isButtonPushedAtFloor(getCurrentFloorForElevator(elevator));
	}
	//Base function: no need to change, just for visualization
	//Feel free to use it though, if it helps
	public boolean isButtonPushedAtFloor(int floor) {
		//System.out.println("Function isButtonPushedAtFloor");
		return (getNumberOfPeopleWaitingAtFloor(floor) > 0);
	}

	//Person threads must call this function to
	//let the system know that they have exited.
	//Person calls it after being let off elevator
	//but before it finishes its run.
	public void personExitsAtFloor(int floor) {
		//System.out.println("Function PersonExitsAtFloor");
		try {
			
			exitedCountMutex.acquire();
			exitedCount.set(floor, (exitedCount.get(floor) + 1));
			exitedCountMutex.release();

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//Base function: no need to change, just for visualization
	//Feel free to use it though, if it helps
	public int getExitedCountAtFloor(int floor) {
		//System.out.println("Function getExitedCountAtFloor");
		if(floor < getNumberOfFloors()) {
			return exitedCount.get(floor);
		}
		else {
			return 0;
		}
	}
	
	//Function to send elevator between floor 0 and 1
	public void elevatorMove(){
		//ToDo setja inn bool skilyr�i, passa a� lyfta fari ekki ni�ur fyrir 0 og upp fyrir efstuh�� + setja inn mutex
		
		System.out.println("Move this fucking elevator!");
		this.currentElevatorFloor = 1;
		
	}


}
