package blackjack;

/**
 * Class HiLoStrategy is a subclass of the class BasicStrategy that allows for the implementation of the Hi-lo strategy
 */
public class HiLoStrategy extends BasicStrategy {
    int runningCount;
    float trueCount;
    boolean insurable;
    boolean surrenderable;
    boolean doubleable;
    
    
    // T means 10
    // 15vT --> player 15 vs dealer 10
    /**
     * Constructor for the HiLoStrategy class
     */
    public HiLoStrategy() {
    	this.runningCount = 0;
    	this.trueCount = 0;
    }  
    
    
    /**
     * Method that adds an inserted card to the count
     * @param newCard card to be added to the count
     */
    public void addToCount(Card newCard) {
    	int cardValue = newCard.cardValue();
    	// If card rank = 2, 3, 4, 5, or 6 -> Value = +1
    	if( ( cardValue >=2 ) && ( cardValue < 7) ) {
    		runningCount = runningCount + 1;
    		return;
    	} 
    	// If card rank = 7, 8 or 9 -> Value = 0
    	else if( ( cardValue >= 7 ) && ( cardValue < 10) ) {
    		return;
    	}
    	
    	else if ( ( cardValue == 10 ) || ( cardValue == 11)){
    		runningCount = runningCount - 1;
    	}
    	return;
    }
    
    /**
     * Method that updates the true count
     * @param decksRemaining is an integer that represents the number of remaining decks
     */
    public void updateTrueCount(float decksRemaining) {
    	this.trueCount = this.runningCount/decksRemaining;
    	return;
    }
    
    
    
    // Stand or hit if trueCount value higher than val
    // "Stand at X or higher, hit otherwise."
	/**
	 *  This method is used in order to decide to stand or to hit
	 *  @param pushNumber is an integer representing the number of pushes
	 *  @return Choice of hitting or standing
	 */
    private Choice standOrHit(int val) {
    	if(this.trueCount >= val) {
    		return Choice.S; // Stand
    	}
    	else {
    		return Choice.H; // Hit
    	}
    	
    }
    
    // Surrender or use basic strategy if trueCount value higher than val
    // "Surrender at X or higher, basic strategy otherwise."
	/**
	 *  This method is used in order to decide to surrender or to adopt the basic strategy
	 *  @param val is an integer representing the hi-lo value
	 *  @param dealersCard is Card object representing the card that is being shown by the dealer
	 *  @param pushHand is a Hand object representing the player's hand
	 *  @return Choice of hitting or standing
	 */
    private Choice surrenderOrBasic(int val, Card dealersCard, Hand playerHand) {
    	if(this.trueCount >= val && this.surrenderable) {
    		return Choice.U; // Surrender
    	}
    	else {
    		return super.choice(dealersCard, playerHand); // Basic Strategy
    	}
    	
    }
    
    // Double or hit if trueCount value higher than val
    // "Double at +1 or higher, hit otherwise."
	/**
	 *  This method is used in order to decide to double down or to hit
	 *  @param var is an integer representing the hi-lo value
	 *  @return Choice of double downing or hitting
	 */ 
    private Choice doubleOrHit(int val) {
    	if(this.trueCount >= val && doubleable) {
    		return Choice.D; // Double
    	}
    	else {
    		return Choice.S; // Hit
    	}
    	
    }
    
    
    
	/**
	 *  This method is used in order to obtain the correct hi-lo choice
	 *  @param dealersCard is a Card object representing the card in the dealer's hand that is being shown
	 *  @param playerHand is a Hand object that represents a certain player's hand
	 *  @return Choice
	 */   
	public Choice choice(Card dealersCard, Hand playerHand) {
		Choice hiLoChoice;
		int playerVal = playerHand.value();
		int dealerVal = dealersCard.cardValue();
		
		// Check for the possibility of insuring
		if(insurable) {
			if(trueCount >= 3) {
				hiLoChoice = Choice.I;
				return hiLoChoice;
			}
		}
		
		//  Pair of cards each valued at 10 special cases (TT) 
		if( (playerHand.getCard(0).cardValue() == 10) && (playerHand.getCard(1).cardValue() == 10)  ) {
			//If  TT v 6  Split at +4 or higher, stand otherwise.
			if(dealerVal == 6) {
				if(trueCount >= 4) {
					hiLoChoice = Choice.P; // Split
					return hiLoChoice;
				}
				else {
					hiLoChoice = Choice.S; // Stand
					return hiLoChoice;
				}
			}
			//If  TT v 5  Split at +5 or higher, stand otherwise.
			else if(dealerVal == 5) {
				if(trueCount >= 5) {
					hiLoChoice = Choice.P; // Split
					return hiLoChoice;
				}
				else {
					hiLoChoice = Choice.S; // Stand
					return hiLoChoice;
				}
			}
			//Otherwise use basic strategy
			else {
				hiLoChoice = super.choice(dealersCard, playerHand);
			}
		}
		
		// 15 v T special case
		if( (playerVal == 15) && (dealerVal == 10) ) {
			// If the true count is between 0 and +3 the player should surrender.
			if( (trueCount >= 0) && (trueCount <= 3) && this.surrenderable) {
				hiLoChoice = Choice.U; // Surrender
				// Surrender isn't available after splitting
				
				return hiLoChoice;
			}
			//If the true count is +4 or higher, the player should stand. 
			else if( trueCount >= 4 ) {
				hiLoChoice = Choice.S; // Stand
				return hiLoChoice;
			}
			//Otherwise, the player should hit.
			else {
				hiLoChoice = Choice.H; // Hit
				return hiLoChoice;
			}
		}
		
		// "Stand at X or higher, hit otherwise" cases
		if( (playerVal == 16) && (dealerVal == 10))       // 16 v T --- Stand at 0 or higher, hit otherwise.
			return this.standOrHit(0);
		else if( (playerVal == 16) && (dealerVal == 9))   // 16 v 9 --- Stand at +5 or higher, hit otherwise.
			return this.standOrHit(5);
		else if( (playerVal == 13) && (dealerVal == 3))   // 13 v 3 --- Stand at -2 or higher, hit otherwise.
			return this.standOrHit(-2); 
		else if( (playerVal == 13) && (dealerVal == 2))   // 13 v 2 --- Stand at -1 or higher, hit otherwise.
			return this.standOrHit(-1);
		else if( (playerVal == 12) && (dealerVal == 6))   // 12 v 6 --- Stand at -1 or higher, hit otherwise.
			return this.standOrHit(-1);
		else if( (playerVal == 12) && (dealerVal == 5))   // 12 v 5 --- Stand at -2 or higher, hit otherwise.
			return this.standOrHit(-2);
		else if( (playerVal == 12) && (dealerVal == 4))   // 12 v 4 --- Stand at  0 or higher, hit otherwise.
			return this.standOrHit(0);
		else if( (playerVal == 12) && (dealerVal == 3))   // 12 v 3 --- Stand at +2 or higher, hit otherwise.
			return this.standOrHit(2);
		else if( (playerVal == 12) && (dealerVal == 2))   // 12 v 2 --- Stand at +3 or higher, hit otherwise.
			return this.standOrHit(3);
		
		
		// "Double at +1 or higher, hit otherwise." cases
		if( (playerVal == 11) && (dealerVal == 11))       // 11 v A --- Double at +1 or higher, hit otherwise.
			return this.doubleOrHit(1);
		else if( (playerVal == 10) && (dealerVal == 11))  // 10 v A --- Double at +4 or higher, hit otherwise.
			return this.doubleOrHit(4);
		else if( (playerVal == 10) && (dealerVal == 10))  // 10 v T --- Double at +4 or higher, hit otherwise.
			return this.doubleOrHit(4);
		else if( (playerVal == 9) && (dealerVal == 7))    //  9 v 7 --- Double at +3 or higher, hit otherwise.
			return this.doubleOrHit(3);
		else if( (playerVal == 9) && (dealerVal == 2))    //  9 v 2 --- Double at +1 or higher, hit otherwise.
			return this.doubleOrHit(1);
		
		// "Surrender at +1 or higher, basic strategy otherwise." cases
		if( (playerVal == 15) && (dealerVal == 11))            // 15 v A --- Surrender at +1 or higher, basic strategy otherwise.
			return this.surrenderOrBasic(1, dealersCard, playerHand);
		else if( (playerVal == 15) && (dealerVal == 11))       // 15 v 9 --- Surrender at +2 or higher, basic strategy otherwise.
			return this.surrenderOrBasic(2, dealersCard, playerHand);
		else if( (playerVal == 14) && (dealerVal == 10))       // 14 v T --- Surrender at +3 or higher, basic strategy otherwise.
			return this.surrenderOrBasic(3, dealersCard, playerHand);
		
		// Other cases not specified in the Illustrious 18 or Fab4
		hiLoChoice = super.choice(dealersCard, playerHand);		
		return hiLoChoice;
	}
	
	/**
	 *  This method is used as a setter for the insurable attribute
	 *  @param insurable is an argument of a boolean type that is true when possible
	 */ 
	public void setInsurable(boolean insurable) {
		this.insurable = insurable;
		return;
	}
	
	/**
	 *  This method is used in order to reset both the running count and the true count
	 */ 
	public void setSurrenderable(boolean surrenderable) {
		this.surrenderable= surrenderable;
		return;
	}
	
	public void setDoubleable(boolean doubleable) {
		this.doubleable = doubleable;
		return;
	}
	
	/**
	 *  This method is used in order to reset both the running count and the true count
	 */ 
	public void resetCount() {
		this.runningCount = 0;
		this.trueCount = 0;
	}
	
	/**
	 *  This method is used as getter for the attribute trueCount
	 *  @return float that represents the true count
	 */ 
	public float getTrueCount() {
		return this.trueCount;
	}
	
	public int getRunningCount() {
		return this.runningCount;
	}
}
