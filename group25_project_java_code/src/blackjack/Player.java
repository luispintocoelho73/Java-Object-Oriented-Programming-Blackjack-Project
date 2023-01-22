package blackjack;

import java.util.ArrayList;

/**
 * The class Player is a subclass of the class User that is used to implement Player objects
 */
public class Player extends User {

	double balance;
	private int betSize;
	boolean hasInsured;
	String lastResult;
	int handIndex;
	double endBalance;
	
	/**
	 * This is the constructor for the Player class
	 */
	public Player() {
		this.balance = 0;
		this.betSize = 0;
		this.losses = 0;
		this.wins = 0;
		this.hasInsured = false;
		this.lastResult = "none";
		this.handIndex = 0;
		this.endBalance = 0;
	}
	
	/**
	 * This is a getter for the balance attribute of the player
	 * @return double that represents the current balance of the player
	 */
	public double getBalance() {
		return this.balance;
	}

	/**
	 * This is a setter for the balance attribute of the player
	 * @param d is a double with the new balance for the player
	 */
	public void setBalance(double d) {
		this.balance = d;
	}
	
	
	/**
	 * This is a getter for the bet attribute of the player
	 * @return int that represents the current bet of the player
	 */
	public int getBetSize() {
		return this.betSize;
	}

	/**
	 * This is a setter for the bet attribute of the player
	 * @param d is an integer that represents the current bet of the player
	 */	
	public void setBetSize(int d) {
		this.betSize = d;
	}
	
	/**
	 * This is a getter for the getHasInsured attribute of the player
	 * @return boolean that is true when a player has insured
	 */		
	public boolean getHasInsured() {
		return this.hasInsured;
	}

	/**
	 * This is a setter for the getHasInsured attribute of the player
	 * @param b is a boolean that is true when a player has insured
	 */	
	public void setHasInsured(boolean b) {
		this.hasInsured = b;
	}
	
	/**
	 * This is a getter for the last result of a player (win, loss or push)
	 * @return String with the last result of a player
	 */	
	public String getLastResult() {
		return this.lastResult;
	}
	
	/**
	 * This is a setter for the last result attribute of a player (win, loss or push)
	 * @param newResult is String that represents the last result of a player
	 */	
	public void setLastResult(String newResult) {
		this.lastResult = newResult;
		return;
	}

	/**
	 * This method is used to substitute the player hands (used while splitting in order to update the player's hands)
	 * @param newHands is an arraylist of Hand objects that will substitute the hands the player when this method is called
	 */	
	public void setHands (ArrayList<Hand> newHands) {
		this.hands = newHands;
	}
	
	/**
	 * This is a getter for the hand index of a player
	 * @return int that represents this current hand's index
	 */	
	public int getHandIndex() {
		return this.handIndex;
	}
	
	/**
	 * This method resets the hand index
	 */	
	public void resetHandIndex() {
		this.handIndex = 0;
	}
	/**
	 * This method increments the hand index
	 */	
	public void incrementHandIndex() {
		this.handIndex = this.handIndex + 1;
		return;
	}
	/**
	 * This method converts the hand index into a string
	 */	
	public String handIndexToString() {
		switch(this.handIndex) {
		  case 0:
		      return "1st";
		  case 1:
	          return "2nd";
		  case 2:
	          return "3rd";
		  case 3: 
			  return "4th";
			  
		  default:
		      return "nth";
		} 
	}
	
	/**
	 * This is a settter for the end balance after finishing a round
	 * @param newEndBalance that represents the player's new current balance
	 */	
	public void setEndBalance(double newEndBalance) {
		this.endBalance = newEndBalance;
	}
	
	/**
	 * This is a getter for the end balance after finishing a round
		@return double that represents the player's new current balance
	 */	
	public double getEndBalance() {
		return this.endBalance;
	}
	
	
}