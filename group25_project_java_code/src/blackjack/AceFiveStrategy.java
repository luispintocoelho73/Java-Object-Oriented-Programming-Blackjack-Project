package blackjack;

/**
 * 
 * 
 * this class is a subclass of the BettingStrategy class and has the purpose of housing the ace five strategy
 *
 */
public class AceFiveStrategy extends BettingStrategy {
	int acefiveCount = 0;
	int maxBet = 0;
	int minBet = 0;
	int lastBet = 0;
	
	/**
	 * 
	 * 
	 * This is the constructor of the AceFiveStrategy class
	 * @param minBet is the minimum bet that the player can do
	 * @param maxBet is the maximum bet that the player can do
	 */	
	public AceFiveStrategy(int minBet, int maxBet) {
		this.minBet = minBet;
		this.maxBet = maxBet;
	}
	
	/**
	 * 
	 * 
	 * This method has the purpose of telling the player what their next bet should be
	 * @return int with the value of the proposed bet
	 */
	public int nextBet() {
		//At the beginning of each shoe, start with the minimum bet
		int bet = minBet;
		// If the count is greater than or equal to two, then double your last bet, up to your maximum bet.
		if(acefiveCount >= 2) {
			if(lastBet * 2 <= maxBet) {
				bet = lastBet * 2 ; 
			}
			else {
				bet = maxBet;
			}
		}
		
		// If the count is less than or equal to one, then make the minimum bet.
		else if(acefiveCount <= 1){
			bet = minBet;
		}
		
		return bet;
	}
	
	/**
	 * 
	 * 
	 * This method updates the value of the aceFiveCount parameter based on the value of a given card
	 * @param newCard is a Card object
	 */
    public void addToCount(Card newCard) {
    	int cardValue = newCard.cardValue();
        if(cardValue == 5) {
        	acefiveCount = acefiveCount + 1;
        }
        else if( cardValue == 11) {
        	acefiveCount = acefiveCount - 1;
        }
    	return;
    }
    
	/**
	 * 
	 * 
	 * This method has the purpose of being a setter for the maxBet attribute
	 */
    public void setMaxBet(int newMaxBet) {
    	this.maxBet = newMaxBet;
    	return;
    }
    
	/**
	 * 
	 * 
	 * This method has the purpose of being a setter for the minBet attribute
	 */
    public void setMinBet(int newMinBet) {
    	this.minBet = newMinBet;
    	return;
    }
	/**
	 * 
	 * 
	 * This method has the purpose of being a setter for the lastBet attribute
	 */
    public void setLastBet(int newLastBet) {
    	this.lastBet = newLastBet;
    	return;
    }
	/**
	 * 
	 * 
	 * This method has the purpose resetting the ace count of a given AceFiveStrategy object
	 */
    public void resetCount() {
    	this.acefiveCount = 0;
    }

}
