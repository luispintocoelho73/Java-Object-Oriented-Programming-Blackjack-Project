package blackjack;

/**
 *  Enumerates all the possible card ranks with the corresponding value and name's abbreviation
 */

public enum RankType{
	ACE(11,"A"), 
	TWO(2,"2"), 
	THREE(3,"3"), 
	FOUR(4,"4"), 
	FIVE(5,"5"), 
	SIX(6,"6"), 
	SEVEN(7,"7"),
	EIGHT(8,"8"), 
	NINE(9,"9"), 
	TEN(10,"10"), 
	JACK(10,"J"), 
	QUEEN(10,"Q"), 
	KING(10,"K");
	
	protected final int value; //represents the value of some card
	protected final String abbr; //represents the abbreviation of the Rank type
	/**
	 *  Constructor for the class RankType
	 *  @param value is an integer that represents the value of a certain rank
	 *  @param abbr is a String corresponding to the abbreviation of the rank 
	 */
	RankType(int value, String abbr){
		this.value = value;
		this.abbr = abbr;
	}
	
}