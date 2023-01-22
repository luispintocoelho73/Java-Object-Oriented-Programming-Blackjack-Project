package blackjack;

/**
 * The StandardBetting class is a subclass of the BettingStrategy class and is used in order to implement the standard betting strategy
 */
public class StandardBetting extends BettingStrategy {
	int maxBet = 0;
	int minBet = 0;
	int lastBet = 0;
	String lastResult;
	
	/**
	 * This method is the constructor for the StandardBetting class
	 * @param newMinBet is an integer corresponding to the new minimum bet
	 * @param newMaxBet is an integer corresponding to the new maximum bet
	 */
	public StandardBetting(int newMinBet, int newMaxBet){
		this.minBet = newMinBet;
		this.maxBet = newMaxBet;
		this.lastResult = "none";
	}
	
	
	/**
	 * This method is used to decide the value of the next bet
	 * @return int which is the value or amount of the next bet the player should do
	 */
	public int nextBet() {
		//If the player wins, increase the bet by min-bet (up to max-bet). 
		if(lastResult.equals("win")) {
			if(lastBet + minBet <= maxBet)
			    return (lastBet + minBet);
			else
				return maxBet;
		}
		//If the player loses, decrease the bet by min-bet (down to min-bet).
		else if (lastResult.equals("loss")) {
			if(lastBet - minBet >= minBet)
			    return (lastBet - minBet);
			else
				return minBet;
		}
		//If the player pushes, keep the bet.
		else if (lastResult.equals("push")) {
			return lastBet;
		}
		else if(lastResult.equals("none")) {
			return minBet;
		}
		else {
			System.out.println("Invalid lastResult parameter.");
			return lastBet;
		}
		
		
	}
	
	/**
	 * This method is a setter for the lastResult attribute (used to update this attribute of an object)
	 * @param newResult is a String and the new lastResult of an object
	 */
	public void setLastResult(String newResult){
		this.lastResult = new String(newResult);
		return;
	}
	
	/**
	 * This method is a setter for the lastBet attribute (used to update this attribute of an object)
	 * @param newBetVal is an integer and the new lastBet of an object
	 */
	public void setLastBet(int newBetVal) {
		this.lastBet = newBetVal;
		return;
	}
	
	/**
	 * This method is a setter for the minBet attribute (used to update this attribute of an object)
	 * @param newBetVal is an integer and the new minBet of an object
	 */
	public void setMinBet(int newBetVal) {
		this.minBet = newBetVal;
		return;
	}
	
	/**
	 * This method is a setter for the maxBet attribute (used to update this attribute of an object)
	 * @param newBetVal is an integer and the new maxBet of an object
	 */
	public void setMaxBet(int newBetVal) {
		this.maxBet = newBetVal;
		return;
	}
}
