package blackjack;
import java.util.ArrayList;
import java.util.Collections;


/**
 * The class Shoe is used in order to house Shoe objects
 */
public class Shoe {

	private ArrayList<Card> shoeCards;
	private ArrayList<Card> trashCards;
	private int numOfDecks;
	
	/**
	 * This is a constructor of the Shoe class (assumes number of decks to be 4)
	 */
	public Shoe() {
		int numDecks = 4;
		shoeCards = new ArrayList<>();
		trashCards = new ArrayList<>();
		
		numOfDecks = numDecks;
        for (int i = 0; i < numDecks; i++) {
            addDeck(new Deck());
        }
        Collections.shuffle(shoeCards);
        System.out.println("shuffling the shoe...");
	}
	
	
	// one argument shoe constructor (number of decks defined by user)
	/**
	 * This is a constructor of the Shoe class
	 * @param numDecks is an integer that represents the number of decks in the shoe
	 */	public Shoe(int numDecks) {
		shoeCards = new ArrayList<>();
		trashCards = new ArrayList<>();
		
		numOfDecks = numDecks;
        for (int i = 0; i < numDecks; i++) {
            addDeck(new Deck());
        }
        Collections.shuffle(shoeCards);
        System.out.println("shuffling the shoe...");
	}
	
		/**
		 * This is a constructor of the shoe (used for the debug mode)
		 * @param numDecks is an integer that represents the number of decks in the shoe
		 * @param shoeFile is an array of strings with all the cards present in the shoe
		 */
	public Shoe(int numDecks, String[] shoeFile) {
		shoeCards = new ArrayList<>();
		trashCards = new ArrayList<>();
		String rank;
		String suit;
		Card tempCard = new Card(SuitType.SPADES,RankType.ACE);
        for (int i = 0; i < shoeFile.length; i++) {
        	
        	if((shoeFile[i].charAt(0) == '1') && (shoeFile[i].charAt(1) == '0')) {
        		
        		rank = "10";
        		suit = String.valueOf(shoeFile[i].charAt(2));
        	}
        	else {
        		rank = String.valueOf(shoeFile[i].charAt(0));
        		suit = String.valueOf(shoeFile[i].charAt(1));
        	}
        	
        	shoeCards.add(tempCard.stringToCard(rank, suit));
	    }
        //addDeck(new Deck(shoeFile));
	}
	
	/**
	 * This method adds a full 52 card standard deck to the shoe
	 * @param deck is a deck object
	 */
	private void addDeck(Deck deck) {
        for (int i = 0; i < deck.size(); i++) {
            shoeCards.add(deck.getCard(i));
        }
    }
    
	/**
	 * This method picks a card from the deck, returns it and sends it to the trashed cards array
	 * @return Card object returned by the method
	 */
	public Card pickCard() {
    	trashCards.add(shoeCards.get(0));
    	return shoeCards.remove(0);
    }

    /**
	 * This method resets an existing shoe
	 */
	public void resetShoe() {
        shoeCards.clear();
        trashCards.clear();
        for (int i = 0; i < numOfDecks; i++) {
            addDeck(new Deck());
        }
	}
	
	/**
	 * This method shuffles the shoe
	 */
    public void shuffle() {
    	this.resetShoe();
    	Collections.shuffle(shoeCards);
    	System.out.println("shuffling the shoe...");
	}
	
	/**
	 * This method converts a shoe into a String format
	 * @return String with all the cards of the shoe
	 */
    @Override public String toString(){
        String s = "";
        for ( int i = 0; i < shoeCards.size(); i++ ) {
            s += ( shoeCards.get(i) );
        }
       return ( s );
   }
    
	/**
	 * This method returns the initial size of the shoe (before the first deal command)
	 * @return int that represents the initial size of the shoe
	 */   
    public int getInitialSize() {
    	return (this.shoeCards.size() + this.trashCards.size() );
    }
    
	/**
	 * This method returns the current size of the shoe
	 * @return int that represents the current size of the shoe
	 */
    public int getCurrentSize() {
    	return this.shoeCards.size();
    }
    
	/**
	 * This method is a getter for the number of decks in the shoe
	 * @return int that represents the number of decks in the shoe
	 */
    public int getNumOfDecks() {
    	return this.numOfDecks;
    }

}