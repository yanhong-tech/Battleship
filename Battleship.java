//game battership 

package project1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Battleship {
	public static Random generartor = new Random();
	public static Scanner scan = new Scanner(System.in);
	public static String[] describe = new String[51];
	public static int [] ship, ship1, ship2, ship3;
	
	// PreNumber Array list will track all the locations of ships so that no two ships overlap each other
	public static ArrayList<Integer> preNumber = new ArrayList<Integer>();
	public static ArrayList<Integer> hitList = new ArrayList<Integer>();

	
	public static void main(String[] args) {
		// to track when if user wants to play the game again
		String answerPlayAgain;
		
		do {			
			/* Setup Ships */ 
			ship = setupShips();
			
			/* Show info to the player */
			getReady();
			
			// just can miss 10 times , when"M"<10,continue
			while (numberOfMiss() < 10) {
				int numberOfGuesses = Integer.parseInt(scan.nextLine());
				// if enter the same numberOfGuess re-enter
				while (describe[numberOfGuesses] != "-") {
					System.out.println("you already fired there! Try again");
					numberOfGuesses = Integer.parseInt(scan.nextLine());
				}
				// after enter the position you guess call missOrHit find out "M" or "H"
				missOrHit(ship, numberOfGuesses);
				
				// if "H"=12
				if (numberofHit() == 12) {
					System.out.println(
							"You've sunk all my battleships. You have won!!! You are the nost worthy opponent. I am sunk! ");
					break;
				}
				
			}
			// if "M"=10, lose game
			if (numberOfMiss() == 10) {
				System.out.println("You lose! Better luck next time");
				aimOfRange();
				// show the exactly the ship position use "X"
				showRealShipPosition(ship);
			}
			System.out.println();
			System.out.print("play again? Enter y or n:  ");
			answerPlayAgain = scan.nextLine();
			//empty preNumber arrayList use next time 
			preNumber.clear();
			System.out.println();
		} while (answerPlayAgain.equalsIgnoreCase("y"));
	}

	
	/* *****************************************
	 * Setup All the Ships
	 * *****************************************/
		public static int[] setupShips(){
			// get each ship position
			/* Ship 1*/
				ship1 = new int[3];
				positionOfShip(ship1);
				//System.out.println("Ship 1 Location : " + Arrays.toString(ship1));
			/* Ship 2*/
				ship2 = new int[4];
				positionOfShip(ship2);
				//System.out.println("Ship 2 Location : " + Arrays.toString(ship2));
			/* Ship 3*/
				ship3 = new int[5];
				positionOfShip(ship3);
				//System.out.println("Ship 3 Location : " + Arrays.toString(ship3));
				
				System.out.println(
						"The sizes of three ships are: [" + ship1.length + "," + ship2.length + "," + ship3.length + "]");
			// combine ship1+ship2+ship3	
				int[] ship12 = combineInt(ship1, ship2);
				//System.out.print("Combined Ships = " + Arrays.toString(combineInt(ship12, ship3)) + "\n");
				
				return combineInt(ship12, ship3);
		}
	
	/* *****************************************
	 *  Randomize Ship's position (give the each ship different random position, not the same)
	 * *****************************************/
		public static int[] positionOfShip(int[] ship) {
			
			int gridNumber = generartor.nextInt(51);
			
			for (int i = 0; i < ship.length; i++) {
				ship[i] = gridNumber + i;
				//preNumber.add(ship[i]);
				while(preNumber.contains(ship[i])){
					gridNumber = generartor.nextInt(51);
				}
				preNumber.add(ship[i]);
			}		
			return ship;
		}
	
	/* *****************************************
	 * combine two ships
	 * *****************************************/
		public static int[] combineInt(int[] a, int[] b) {
			int length = a.length + b.length;
			int[] result = new int[length];
			System.arraycopy(a, 0, result, 0, a.length);
			System.arraycopy(b, 0, result, a.length, b.length);
			return result;
		}

	/* *****************************************
	 * Start the game - Precursor 
	 * *****************************************/
		public static void getReady(){
			System.out.println("**********************************************************");
			System.out.println("Let' start the game !!!!");
			System.out.println("Enter a number to fire at that ship. You only get 10 misses! \n");
			System.out.println();
			System.out.println("Here are your shots so far(H is hit, M is miss): ");
			aimOfRange();
			// initial the hit or miss position use "-"
			for (int i = 0; i < describe.length; i++) {
				describe[i] = "-";
			}
			printPositions();
			
			System.out.println("Fire away! Aim for a location bteween 0 to 50 ");
		}
	

	/* *****************************************
	 * how the aim range which is 0-50
	 * *****************************************/ 
		public static void aimOfRange() {
		int count = 0;
		while (count < 51) {
			System.out.print(count + "  ");
			count++;
		}
		System.out.println();
	}
	
	/* *****************************************
	 * pass guess number, check if hit ship or miss
	 * *****************************************/
		public static void missOrHit(int[] ship, int numberOfGuesses) {
			int position = -1;
			for (int i = 0; i < ship.length; i++) {
				if (ship[i] == numberOfGuesses) {
					System.out.println("^^^^^^^^^^^^^^^^^^^^ Boom! That is a hit!! ^^^^^^^^^^^^^^^^^^^^");
					System.out.println();
					System.out.println("Here are your shots so far(H is hit, M is miss): ");
					aimOfRange();
					//method change position"-" to "H"
					changePositionHorM("H", numberOfGuesses);
					//printPositions();
					whichShipSunk(numberOfGuesses);
					System.out.println("Fire away! Aim for a location bteween 0 to 50 ");
					position = ship[i];
				}
			}
			if (position != numberOfGuesses) {
				System.out.println("Miss!");
				System.out.println();
				System.out.println("Here are your shots so far(H is hit, M is miss): ");
				aimOfRange();
				//printPositions();
				//method change position"-" to "M"
				changePositionHorM("M", numberOfGuesses);
				numberOfMiss();
				System.out.println("Fire away! Aim for a location bteween 0 to 50 ");
			}
		}

	/* *****************************************
	 * describe position when come to "M" or "H", change to the letters 
	 * *****************************************/
		public static void changePositionHorM(String letter, int position) {
			describe[position] = letter;
			printPositions();	
		}

	/* *****************************************
	 * print all positions
	 * *****************************************/
		public static void  printPositions(){
			for (int i = 0; i < describe.length; i++) {
				System.out.print(describe[i] + "  ");
			}
			System.out.print("<<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>> \n");
			System.out.println();
		}
	/* *****************************************
	 * track number of "M"
	 * *****************************************/
		public static int numberOfMiss() {
			int countM = 0;
			for (int i = 0; i < describe.length; i++) {
				if (describe[i] == "M") {
					countM++;
				}
			}
			return countM;
		}

	/* *****************************************
	 * track number of "H"
	 * *****************************************/
		public static int numberofHit() {
		int countH = 0;
		for (int i = 0; i < describe.length; i++) {
			if (describe[i] == "H") {
				countH++;
			}
		}
		return countH;
	}
	
	/* *****************************************
	 *  for when miss 10 and show where is the ship use "X"
	 * *****************************************/
		public static void showRealShipPosition(int[] ship) {
			for (int i = 0; i < ship.length; i++) {
				if (describe[ship[i]] != "H") {
					describe[ship[i]] = "X";
				}
			}
			for (int i = 0; i < describe.length; i++) {
				System.out.print(describe[i] + "  ");
			}
		}
		
	/* *****************************************
	 * getShip Number
	 * *****************************************/
		public static int getShipNumber(int location){
			int shipNumber = 0;
			for (int j=0; j<ship1.length; j++){
				if(ship1[j]==location){
					shipNumber = 1;
				}
			}
			for (int j=0; j<ship2.length; j++){
				if(ship2[j]==location){
					shipNumber = 2;
				}
			}
			for (int j=0; j<ship3.length; j++){
				if(ship3[j]==location){
					shipNumber = 3;
				}
			}
			return shipNumber;
		}
		
	/* *****************************************
	 *  Which Ship Sank
	 * *****************************************/
		public static void whichShipSunk(int hitLocation)
		{
			int shipNumber=0;
			
			if(describe[hitLocation]=="H"){
				// get the shipNumber
				shipNumber = getShipNumber(hitLocation);
				switch (shipNumber){
				case 1: 
					whichShipSankHelperMethod(ship1);
					break;
				case 2: 
					whichShipSankHelperMethod(ship2);
					break;
				case 3: 
					whichShipSankHelperMethod(ship3);
					break;
				default: break;
				}
			}
		}
	
		/* *****************************************
		 * Which Ship Sank 
		 * *****************************************/
			public static void whichShipSankHelperMethod(int[] ship){
				int hitCount=0;
				for (int k=0; k<ship.length; k++){
					if(describe[ship[k]]=="H"){
						hitCount++;
						continue;
					}else{
						break;
					}
				}
				if(hitCount == ship.length){
					System.out.println("########################## SHIP of Size = "+ ship.length + " SANK! ##########################");
				}
			}
}
