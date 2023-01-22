package blackjack;

/**
 *  Dealer is a subclass of the User calls thats aims to house the Dealer objects of the blackjack game
 */
public class Dealer extends User {
	
	/**
	 *  This method is a constructor of a Dealer object
	 */
    public Dealer() {
    	super();
    }
	
    /**
     *  Getter for the dealer's first card
     *  @return Card of the dealer that is revealed
     */
	public Card getFirstCard() {
		return this.hands.get(0).getCard(0);
	}

    /**
     *  Getter for the dealer's hole card
     *  @return Card that is the dealer's hole card
     */
	public Card getHoleCard() {
		return this.hands.get(0).getCard(1);
	}
	
}

