package blackjack;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Class that houses all Deck objects (that compose the Shoe)
 */
public class Deck {

	private ArrayList<Card> deck;	
	
	/**
	 * Constructor for a Deck object
	 */
	// Normal 52 standard deck constructor
	public Deck() {
		deck = new ArrayList<>();
        for (SuitType s : SuitType.values()) {
	          for (RankType r : RankType.values()) {
	              deck.add(new Card(s,r));
	          }
	    }
        Collections.shuffle(deck);
	}
	
	
	/**
	 * Method used for representing a deck through a String
	 * @return String with the cards of a given deck 
	 */
    @Override public String toString(){
        String s = "";
        for ( int i = 0; i < deck.size(); i++ ) {
            s += ( deck.get(i) );
        }
       return ( s );
   }

    // picks a card and removes it from the deck and then returns it
	/**
	 * Method that picks a card, removes it from the deck and then returns it
	 * @return Card object of the removed card
	 */
    public Card pickCard() {
    	return deck.remove(0);
    }

	/**
	 * Method that shuffles the deck of cards
	 */
	public void shuffle() {
		Collections.shuffle(deck);
	}
	
	/**
	 * Method that returns the size of the deck of cards
	 * @return int with the size of the deck of cards
	 */
	public int size() {
		return deck.size();
	}
	

	/**
	 * Method that is a getter for card inside the deck in specific index
	 * @param i integer index of the desired card
	 * @return Card object corresponding to the index i
	 */
	public Card getCard(int i) {
		return deck.get(i);
	}
    
}

