package blackjack;
/**
 *  Choices that can be made
 */
public enum Choice {
	/**
	 *  Hit
	 */
	H("hit"),
	/**
	 *  Stand
	 */
	S("stand"), 
	/**
	 *  Split
	 */
	P("split"), 
	/**
	 *  Double
	 */
	D("double"), 
	/**
	 *  Double if possible, otherwise hit
	 */
	DH("Dh"), 
	/**
	 *  Double if possible, otherwise stand
	 */
	DS("Ds"),
	/**
	 *  Surrender if possible, otherwise hit
	 */
	RH("Rh"),
	/**
	 *  Surrender
	 */
	U("surrender"),
	/**
	 *  Insurance
	 */
	I("insure");

	protected final String abbr; //represents the abbreviation of the choice
	/**
	 *  Choice constructor 
	 *  @param abbr takes an abbreviation as an input, in a String format
	 */
	Choice( String abbr ){
		this.abbr = abbr;
	}
	
}
