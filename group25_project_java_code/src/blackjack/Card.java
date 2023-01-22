package blackjack;
/**
 * This class houses the cards used to play blackjack
 *
 */
public class Card {

	private SuitType suit;
	private RankType rank;

	/**
	 * This method is the Constructor of a Card object
	 * @param suit represents the suit type of a given card
	 * @param rank represents the rank type of a given card
	 */
	public Card(SuitType suit, RankType rank) {
		this.suit = suit;
		this.rank = rank;

	}
	
	/**
	 * This method compares a given Card object with the object thats calls this method
	 * @param o Object that will be compared with the object that calls this method
	 * @return boolean that is true if both objects are the same card
	 */
	@Override public boolean equals(Object o){
		if(this == o)
			return true;
		if(!(o instanceof Card))
			return false;
		Card c = (Card) o;
		return rank.equals(c.rank) && suit.equals(c.suit);
	}
	
	/**
	 * uses the values of the Enums hashCode and adds them together with 31,
   	 * as seen in Effective Java
	 * @return int 
	 */
	@Override public int hashCode(){
		return 31 + suit.hashCode() + rank.hashCode();
	}	
	
	/**
	 * Safely publish the rank of the card. Since it's an Enum
	 * the value is final, as is the assigned rank
	 * @return {@code RankType}
	 */
	public RankType getRank(){
		return rank;
	}
	
	/**
	 * Safely publish the suit of the card. Since it's an Enum
	 * the value is final, as is the assigned suit
	 * @return {@code Suit}
	 */
	public SuitType getSuit(){
		return suit;
	}
	/**
	 * determines whether or not some card is an ace or not
	 * @return boolean that is true if the card is an ace
	 */
	public boolean isAce(){
		return rank.abbr == "A";
	}
	
	/**
	 * Method that determines what the value of a card is
	 * @return int with the value of a card
	 */
	public int cardValue(){
		return rank.value;
	}
	
	/**
	 * Method that transforms the information of a card (rank and suit) to a string format
	 * @return string representation of a card
	 */
	@Override public String toString(){
		return rank.abbr + suit.abbr;
	}
	/**
	 * Method that transforms a string with the rank of a card to an RankType object
	 * @param rank string with the rank of a given card
	 * @return RankType an object representation of the rank of a card 
	 */
	private RankType stringToRank(String rank) {
		for (RankType r : RankType.values()) {
			if(r.abbr.equals(rank)) {
				return r;
			}
		}
		System.out.println("Invalid RankType");
		return null;
	} 
	/**
	 * Method that transforms a string with the suit of a card to an SuitType object
	 * @param suit string with the suit of a given card
	 * @return SuitType an object representation of the suit of a card 
	 */
	private SuitType stringToSuit(String suit) {
		for (SuitType s : SuitType.values()) {
			if(s.abbr.equals(suit)) {
				return s;
			}
		}
		System.out.println("Invalid SuitType");
		return null;
	} 	
	/**
	 * Method that transforms a card's information (rank and suit) in a string format to a Card object
	 * @param rank string with the rank of a given card
	 * @param suit string with the suit of a given card 
	 * @return Card an object representation of a card (rank and suit) 
	 */
	public Card stringToCard(String rank, String suit) {
		Card card = new Card(stringToSuit(suit),stringToRank(rank));
		return card;
	}
	
}