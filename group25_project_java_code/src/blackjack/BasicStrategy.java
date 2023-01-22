package blackjack;

/**
 * 
 * 
 * this class has the purpose of housing the basic strategy
 *
 */
public class BasicStrategy{
	
    boolean insurable;
    boolean surrenderable;
    boolean doubleable;
	
	private Choice[][] pairTable = {{Choice.H  , Choice.H  , Choice.P  , Choice.P  , Choice.P  , Choice.P  , Choice.H  , Choice.H  , Choice.H  , Choice.H , Choice.H },
			                        {Choice.H  , Choice.H  , Choice.P  , Choice.P  , Choice.P  , Choice.P  , Choice.H  , Choice.H  , Choice.H  , Choice.H , Choice.H },
			                        {Choice.H  , Choice.H  , Choice.H  , Choice.H  , Choice.H  , Choice.H  , Choice.H  , Choice.H  , Choice.H  , Choice.H , Choice.H },
			                        {Choice.DH , Choice.DH , Choice.DH , Choice.DH , Choice.DH , Choice.DH , Choice.DH , Choice.DH , Choice.DH , Choice.H , Choice.H },
			                        {Choice.H  , Choice.P  , Choice.P  , Choice.P  , Choice.P  , Choice.H  , Choice.H  , Choice.H  , Choice.H  , Choice.H , Choice.H },
			                        {Choice.P  , Choice.P  , Choice.P  , Choice.P  , Choice.P  , Choice.P  , Choice.P  , Choice.H  , Choice.H  , Choice.H , Choice.H },
			                        {Choice.P  , Choice.P  , Choice.P  , Choice.P  , Choice.P  , Choice.P  , Choice.P  , Choice.P  , Choice.P  , Choice.P , Choice.P },
			                        {Choice.P  , Choice.P  , Choice.P  , Choice.P  , Choice.P  , Choice.P  , Choice.S  , Choice.P  , Choice.P  , Choice.S , Choice.S },
			                        {Choice.S  , Choice.S  , Choice.S  , Choice.S  , Choice.S  , Choice.S  , Choice.S  , Choice.S  , Choice.S  , Choice.S , Choice.S },
			                        {Choice.P  , Choice.P  , Choice.P  , Choice.P  , Choice.P  , Choice.P  , Choice.P  , Choice.P  , Choice.P  , Choice.P , Choice.P }};
	
	
	private Choice[][] softTable = {{Choice.H , Choice.H  , Choice.H  , Choice.DH , Choice.DH , Choice.H , Choice.H , Choice.H , Choice.H , Choice.H },
			                        {Choice.H , Choice.H  , Choice.H  , Choice.DH , Choice.DH , Choice.H , Choice.H , Choice.H , Choice.H , Choice.H },
			                        {Choice.H , Choice.H  , Choice.DH , Choice.DH , Choice.DH , Choice.H , Choice.H , Choice.H , Choice.H , Choice.H },
			                        {Choice.H , Choice.H  , Choice.DH , Choice.DH , Choice.DH , Choice.H , Choice.H , Choice.H , Choice.H , Choice.H },
			                        {Choice.H , Choice.DH , Choice.DH , Choice.DH , Choice.DH , Choice.H , Choice.H , Choice.H , Choice.H , Choice.H },
			                        {Choice.S , Choice.DS , Choice.DS , Choice.DS , Choice.DS , Choice.S , Choice.S , Choice.H , Choice.H , Choice.H },
			                        {Choice.S , Choice.S  , Choice.S  , Choice.S  , Choice.S  , Choice.S , Choice.S , Choice.S , Choice.S , Choice.S },
			                        {Choice.S , Choice.S  , Choice.S  , Choice.S  , Choice.S  , Choice.S , Choice.S , Choice.S , Choice.S , Choice.S },
			                        {Choice.S , Choice.S  , Choice.S  , Choice.S  , Choice.S  , Choice.S , Choice.S , Choice.S , Choice.S , Choice.S }};
	
	private Choice[][] hardTable =  {{Choice.H  , Choice.H  , Choice.H  , Choice.H  , Choice.H  , Choice.H  , Choice.H  , Choice.H  , Choice.H  , Choice.H  },
			                         {Choice.H  , Choice.H  , Choice.H  , Choice.H  , Choice.H  , Choice.H  , Choice.H  , Choice.H  , Choice.H  , Choice.H  },
			                         {Choice.H  , Choice.H  , Choice.H  , Choice.H  , Choice.H  , Choice.H  , Choice.H  , Choice.H  , Choice.H  , Choice.H  },
			                         {Choice.H  , Choice.H  , Choice.H  , Choice.H  , Choice.H  , Choice.H  , Choice.H  , Choice.H  , Choice.H  , Choice.H  },
			                         {Choice.H  , Choice.DH , Choice.DH , Choice.DH , Choice.DH , Choice.H  , Choice.H  , Choice.H  , Choice.H  , Choice.H  },
			                         {Choice.DH , Choice.DH , Choice.DH , Choice.DH , Choice.DH , Choice.DH , Choice.DH , Choice.DH , Choice.H  , Choice.H  },
			                         {Choice.DH , Choice.DH , Choice.DH , Choice.DH , Choice.DH , Choice.DH , Choice.DH , Choice.DH , Choice.DH , Choice.H  },
			                         {Choice.H  , Choice.H  , Choice.S  , Choice.S  , Choice.S  , Choice.H  , Choice.H  , Choice.H  , Choice.H  , Choice.H  },
			                         {Choice.S  , Choice.S  , Choice.S  , Choice.S  , Choice.S  , Choice.H  , Choice.H  , Choice.H  , Choice.H  , Choice.H  },
			                         {Choice.S  , Choice.S  , Choice.S  , Choice.S  , Choice.S  , Choice.H  , Choice.H  , Choice.H  , Choice.H  , Choice.H  },
			                         {Choice.S  , Choice.S  , Choice.S  , Choice.S  , Choice.S  , Choice.H  , Choice.H  , Choice.H  , Choice.RH , Choice.H  },
			                         {Choice.S  , Choice.S  , Choice.S  , Choice.S  , Choice.S  , Choice.H  , Choice.H  , Choice.RH , Choice.RH , Choice.RH },
			                         {Choice.S  , Choice.S  , Choice.S  , Choice.S  , Choice.S  , Choice.S  , Choice.S  , Choice.S  , Choice.S  , Choice.S  },
			                         {Choice.S  , Choice.S  , Choice.S  , Choice.S  , Choice.S  , Choice.S  , Choice.S  , Choice.S  , Choice.S  , Choice.S  },
			                         {Choice.S  , Choice.S  , Choice.S  , Choice.S  , Choice.S  , Choice.S  , Choice.S  , Choice.S  , Choice.S  , Choice.S  },
			                         {Choice.S  , Choice.S  , Choice.S  , Choice.S  , Choice.S  , Choice.S  , Choice.S  , Choice.S  , Choice.S  , Choice.S  },
			                         {Choice.S  , Choice.S  , Choice.S  , Choice.S  , Choice.S  , Choice.S  , Choice.S  , Choice.S  , Choice.S  , Choice.S  },};
			
	

	/**
	 * 
	 * 
	 * This is the constructor of the AceFiveStrategy class
	 * @param dealersCard is the revealed card of the dealer
	 * @param playerHand is the current player's hand
	 * @return Choice is the next choice the player is recommended to take
	 */
	public Choice choice(Card dealersCard, Hand playerHand) {
		// Check if the player's hand is a pair
		if(playerHand.isPair()) {
			// Use pairs table			
			// 2 (Player card value) ---> 0 line   index
			// 2 (Dealer card value) ---> 0 column index  
			return pairTable[ playerHand.getCard(0).cardValue() - 2 ][ dealersCard.cardValue() - 2 ];
		}
		
		// Check if the player's hand is soft
		else if(playerHand.isSoft()) {
			// Use soft table
			// 13 (Player hand value) ---> 0 line   index
			// 2  (Dealer card value) ---> 0 column index  	
			return softTable[ playerHand.value() - 13 ][ dealersCard.cardValue() - 2 ];
		}
		
		// The player's hand is hard
		else {
			// Use hard table
			// 5 (Player card value) ---> 0 line   index
			// 2  (Dealer card value) ---> 0 column index 
			return hardTable[ playerHand.value() - 5 ][ dealersCard.cardValue() - 2 ];
		}
	}
	/**
	 * 
	 * 
	 * This method is used as a setter in order to set the insurable status
	 * @param insurable is true if insurable
	 */	
	public void setInsurable(boolean insurable) {
		this.insurable = insurable;
		return;
	}
	/**
	 * 
	 * 
	 * This method is used as a setter in order to set the surrenderable status
	 * @param surrenderable is true if surrenderable
	 */	
	public void setSurrenderable(boolean surrenderable) {
		this.surrenderable= surrenderable;
		return;
	}
	/**
	 * 
	 * 
	 * This method is used as a setter in order to set the doubleable status
	 * @param doubleable is true if doubleable
	 */	
	public void setDoubleable(boolean doubleable) {
		this.doubleable = doubleable;
		return;
	}
	


}
