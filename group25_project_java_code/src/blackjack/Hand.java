package blackjack;
import java.util.ArrayList;

/**
 * This class houses all Hand objects related to the hand of a user (dealer or player)
 */
public class Hand {
    private ArrayList<Card> cardsInHand;
    private int betSize;
    
	/**
	 * Constructor of the class Hand
	 */
    public Hand() {
        cardsInHand = new ArrayList<>();
        betSize = 0;
	}

	/**
	 * Method that returns the value of the hand
	 * @return int with the value of the hand (sum of the value of all cards in the hand)
	 */
    public int value() {
        int value = 0;  // value of the hand without cards
    	int numAces = 0;
    	for(Card card : cardsInHand) {
    		
    		if(card.isAce()) {
    			value += 1;
    			numAces++;
    		}
    		else {
    			value += card.cardValue();
    		}
    	}

    	if( numAces > 0 ) {
    		if( value + 10 <= 21  ) {
    			value = value + 10;
    		}
    	}
        return value;
    }


    
	/**
	 * Method that adds a card to a hand
	 * @param newCard is a Card object that represents the card we want to add to a hand
	 * @return Card the card added to the hand is returned by the method
	 */
	public Card addCard(Card newCard) {
    	cardsInHand.add(newCard);
    	return newCard;
    }   
	
	/**
	 * Method that returns the number of elements in an array
	 * @return int the number of elements in an array
	 */
    public int size() {
        return cardsInHand.size();
    }
    
	/**
	 * Method that returns a card of hand, given by the index i
	 * @param i index of the desired card
	 * @return Card represents the card that we want to get from the hand
	 */
    public Card getCard(int i) {
    	return cardsInHand.get(i);
    }

    /**
	 * Method that determines if a hand is soft
	 * @return bool which is true when the hand is soft
	 */
    public boolean isSoft() {
    	int handVal = 0;
    	int numAces = 0;
    	
    	// Count the number of aces and the hand value considering aces have the value of 1
    	for(Card card : cardsInHand) {
    		if(card.isAce()) {
    			handVal += 1;
    			numAces++;
    		}
    		else {
    			handVal += card.cardValue();
    		}
    	}

    	
    	if( numAces > 0 ) {
    		// Check if the hand wouldn't bust if on of the aces was considered to be valued as 11
    		// If so the hand is soft and the hand's value should consider one of the aces to be valued as 11
    		// and the others (if they exist) to be valued as 1
    		if( handVal + 10 <= 21  ) {
    			handVal = handVal + 10;
    			return true;
    		}
    		// Otherwise the hand is hard
    	}
    	
        return false;
    }
    
   
	/**
	 * Method that checks if the hand is a pair (2 equal cards)
	 * @return boolean which is true if the hand is a pair
	 */
    public boolean isPair(){
    	if(this.cardsInHand.size() > 2) {
    		return false;
    	}
    	else {
    		// Consider two 10 value cards to be a pair
    		if(this.cardsInHand.get(0).cardValue() == this.cardsInHand.get(1).cardValue()) {
    			return true;
    		}
    		else {
    			return false;
    		}
    	}
    }
    
	/**
	 * Method that checks if the hand is bust (hand value > 21)
	 * @return boolean which is true if the hand is bust
	 */
    public boolean isBust() {
    	if(this.value() > 21) {
    		return true;
    	}
    	else{
    		return false;
    	}
    }
    
	/**
	 * Method that clears all cards in a hand (empties a hand)
	 */
    public void clear() {
    	cardsInHand.clear();
    }
    
	/**
	 * Method that transforms all the cards in a given hand to a string
	 * @return String with all the cards of a hand
	 */
    @Override public String toString(){
        String s = "";
        for (int i = 0; i < cardsInHand.size(); i++) {
            s += ( cardsInHand.get(i) + " ");
        }
        return ( s );
   }  
    
	/**
	 * Method that is a getter for the size (amount) of the bet
	 * @return int returns the bet amount
	 */
    public int getBetSize() {
    	return this.betSize;
    }
	/**
	 * Method that is a setter for the size (amount) of the bet
	 */
    public void setBetSize(int newBetSize) {
    	this.betSize = newBetSize;
    }
    
}