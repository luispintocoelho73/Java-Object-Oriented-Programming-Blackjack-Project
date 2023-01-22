package blackjack;

/**
 *  Enumerates all the possible card suits with the corresponding name's abbreviation
 */

public enum SuitType {
	CLUBS("C"),
	DIAMONDS("D"),
	HEARTS("H"),
	SPADES("S");
	
	protected final String abbr; //represents the abbreviation of the Suit type
	
	/**
	 *  Constructor for the class SuitType
	 *  @param abbr is a String corresponding to the abbreviation of the suit 
	 */
	SuitType( String abbr ){
		this.abbr = abbr;
	}
}