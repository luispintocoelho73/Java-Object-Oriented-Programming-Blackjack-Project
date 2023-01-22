package blackjack;

import java.util.ArrayList;

/**
 *  User is an abstract class used to implement each party in a blackjack game
 */
public abstract class User {

	protected int wins;
	protected int losses;
	protected int pushes;
	protected int numberOfHands;
	protected boolean isBusted;
	protected int numberOfBlackjacks;
	protected ArrayList<Hand> hands;
	
	/**
	 *  This method is the constructor for the class User
	 */
	public User(){
		this.hands = new ArrayList<>();
		this.isBusted = false;
		wins = 0;
		losses = 0;	
		pushes = 0;
		numberOfBlackjacks = 0;
	}
	
	/**
	 *  This method is a setter for the number of pushes a user has done
	 *  @param pushNumber is an integer representing the number of pushes
	 */
	public void setPushes(int pushNumber) {
		this.pushes = pushNumber;
	}
	
	/**
	 *  This method is a getter for the number of pushes a user has done
	 *  @return int representing the number of pushes
	 */
	public int getPushes() {
		return this.pushes;
	}
	
	/**
	 *  This method is a setter for the number of wins a of user
	 *  @param winNumber is an integer representing the number of wins
	 */
	public void setWins(int winNumber) {
		this.wins = winNumber;
	}
	
	/**
	 *  This method is a getter for the number of wins a user has done
	 *  @return int representing the number of wins
	 */
	public int getWins() {
		return this.wins;
	}
	
	/**
	 *  This method is a setter for the number of losses of a user has
	 *  @param lossNumber is an integer representing the number of losses
	 */
	public void setLosses(int lossNumber) {
		this.losses = lossNumber;
	}
	
	/**
	 *  This method is a getter for the number of losses of a user
	 *  @return int representing the number of losses
	 */
	public int getLosses() {
		return this.losses;
	}
	
	/**
	 *  This method is a setter for the busted state of a user
	 */	
	public void setBusted() {
		this.isBusted = true;
	}
	
	/**
	 *  This method is a getter used in order to conclude the busted state of a user
	 *  @return boolean that is set to true when the player is busted
	 */
	public boolean isBusted() {
		return this.isBusted;
	}
	
	/**
	 *  This method is a getter used to get the number of blackjacks of a user
	 *  @return int that represents the number of blackjacks of a user
	 */
	public int getNumberOfBlackjacks() {
		return numberOfBlackjacks;
	}

	/**
	 *  This method is a setter used to set the number of blackjacks of a user
	 *  @param numberOfBlackjacks is an integer that represents the number of blackjacks of a user
	 */
	public void setNumberOfBlackjacks(int numberOfBlackjacks) {
		this.numberOfBlackjacks = numberOfBlackjacks;
	}
	
	/**
	 *  This method is a getter used to get the number of hands of a user
	 *  @return int that represents the number of hands of a user
	 */
	public int getNumberOfHands() {
		return this.numberOfHands;
	}

	/**
	 *  This method is a setter used to set the number of hands of a user
	 *  @param numberOfHands is an integer that represents the number of hands of a user
	 */
	public void setNumberOfHands(int numberOfHands) {
		this.numberOfHands = numberOfHands;
	}	
	
	
}