package blackjack;

/**
 * Class SimulationMode uses elements of the interface class GameMode that allows for the implementation of the simulation mode
 */
public class SimulationMode implements GameMode {
	
	int   MIN_BET  = 0; // minimum bet (MIN_BET >= 1)
	int   MAX_BET  = 0; // maximum bet (10 x MIN_BET<= MAX_BET <= 20 x MIN_BET)
    float BALANCE  = 0; // initial balance (balance >= 50 x MIN_BET)
	int   SHOE     = 0; // number of 52-card decks in the shoe (4<= SHOE <=8)
	int   SHUFFLE  = 0; // shuffle percentage of shoe played before shuffling (10 <= SHUFFLE <= 100)
	int   S_NUMBER = 0; // number of shuffles to perform until ending the simulation
	String STRATEGY; // counting cards’ strategy to use (combinations of BS, HL and AF)
	
	
	/**
	 * This method is used in order to verify the validity of the parameters inserted by the user
	 * @return boolean as true when all parameters are valid
	 */
	@Override
	public boolean parameterVerification() {
		// Check if minimum bet is >= 1
		if (MIN_BET < 1) {
			System.out.println("Please insert a Minimum Bet greater or equal to $1!");
			return false;
		}
		if (!((10*MIN_BET) <= MAX_BET && MAX_BET <= (20*MIN_BET))) {
			System.out.println("Please insert a Maximum Bet between 10*Minimum Bet and 20*Minimum Bet!");
			return false;
		}
		
		if (BALANCE  < 50*MIN_BET) {
			System.out.println("Please insert a Minimum Bet greater or equal to 50*Minimum Bet!");
			return false;
		}
		
		if (!(SHOE >= 4 && SHOE <= 8)) {
			System.out.println("Please insert a SHOE number between 4 and 8!");
			return false;
		}
		
		if (!(SHUFFLE >= 10 && SHUFFLE <= 100)) {
			System.out.println("Please insert a SHUFFLE parameter between 10 and 100!");
			return false;
		}
		
		if(S_NUMBER < 0) {
			System.out.println("Please insert a S_NUMBER parameter equal or higher than 0!");
			return false;
		}
		
		if(! (STRATEGY.equals("BS") || STRATEGY.equals("BS-AF") || STRATEGY.equals("HL") || STRATEGY.equals("HL-AF"))) {
			System.out.println("Please insert a valid combination for the counting cards’ strategy parameter!");
			return false;
		}

		
		
		
		return true;
	}

	/**
	 * This method is used in order to give to each attribute of an object of this class its respective value (inserted by the user)
	 * @param args is an array of Strings, where each String is an inserted parameter
	 */
	@Override
	public void setParameters(String[] args) {
		this.MIN_BET = Integer.parseInt(args[1]);
		this.MAX_BET = Integer.parseInt(args[2]);
		this.BALANCE = Float.parseFloat(args[3]);
		this.SHOE    = Integer.parseInt(args[4]);
		this.SHUFFLE = Integer.parseInt(args[5]);
		this.S_NUMBER = Integer.parseInt(args[6]); // number of shuffles to perform until ending the simulation
		this.STRATEGY = args[7]; // counting cards’ strategy to use (combinations of BS, HL and AF)
		
		return;
	}
	
	/**
	 * This method is used in order to start the blackjack game in the simulation mode
	 * @param args is an array of Strings, where each String is an inserted parameter
	 */
	@Override
	public void gameFunction(String[] args) {		
		// set parameters
		setParameters(args);
		
		// Parameter verification
		if(!parameterVerification()) {
			return;
		}
		
		BettingStrategy bettingStrategy = null;
		BasicStrategy simulationStrategy = null;
		
		
		// CREATE GAME WITH GAME OPTIONS
		game gameVar = new game( MIN_BET , MAX_BET , BALANCE , SHOE , SHUFFLE );
		
		// Use Standard Betting Strategy
		if( STRATEGY.equals("BS") || STRATEGY.equals("HL") ) {
			bettingStrategy = gameVar.standardBetting;
		}
		// Use Ace Five Betting Strategy
		else if( STRATEGY.equals("BS-AF") || STRATEGY.equals("HL-AF") ) {
			bettingStrategy = gameVar.aceFiveStrategy;
		}
		
		// Use Basic Strategy for action choice
		if( STRATEGY.equals("BS") || STRATEGY.equals("BS-AF") ) {
			simulationStrategy = gameVar.basicStrategy;
		}
		// Use Hi-Lo Strategy for action choice
		else if( STRATEGY.equals("HL")  || STRATEGY.equals("HL-AF") ) {
			simulationStrategy = gameVar.hiLoStrategy;
		}
		
		

		
		Choice nextChoice;
		int nextBetSize = 0;
		
		while(S_NUMBER >= gameVar.shuffleCount) {
			// If simulation is currently in the betting state use the bet command
			if(gameVar.gameState == gameVar.getBettingState()) {
				// Get bet size
				bettingStrategy.setLastResult(gameVar.player.getLastResult());
				nextBetSize = bettingStrategy.nextBet();
				
				// Update lastBet value
				gameVar.player.setBetSize(nextBetSize);
				bettingStrategy.setLastBet(nextBetSize); 
				
				// Make bet
				gameVar.bet(nextBetSize);
			}
			
			// If inside the dealing state use the deal command
			else if(gameVar.gameState == gameVar.getDealingState()){
				gameVar.deal();
			}
			
			
			else{
				gameVar.hiLoStrategy.updateTrueCount(gameVar.shoe.getNumOfDecks() * (gameVar.shoe.getInitialSize()/gameVar.shoe.getCurrentSize()));
				simulationStrategy.setInsurable( gameVar.isCommandAvailable( "i" , gameVar.gameState) );
				simulationStrategy.setDoubleable( gameVar.isCommandAvailable( "u" , gameVar.gameState) );
				simulationStrategy.setSurrenderable( gameVar.isCommandAvailable( "2" , gameVar.gameState) );
				nextChoice = simulationStrategy.choice(gameVar.dealer.getFirstCard(), gameVar.player.hands.get(gameVar.player.getHandIndex()));
				
				// Double if possible, otherwise hit
		        if(nextChoice == Choice.DH) {
		        	if( gameVar.isCommandAvailable("2",gameVar.gameState) ) 
		        	    nextChoice = Choice.D; // Double
		        	else
		        		nextChoice = Choice.H; // Hit
		        }
		        
		        // Double if possible, otherwise stand
		        else if(nextChoice == Choice.DS) {
		        	if( gameVar.isCommandAvailable("2",gameVar.gameState) )
		        	    nextChoice = Choice.D; // Double
		        	else
		        		nextChoice = Choice.S; // Stand
		        }
		        
		        // Surrender if possible, otherwise hit
		        else if(nextChoice == Choice.RH) {
		        	if( gameVar.isCommandAvailable("u",gameVar.gameState) )
		        	    nextChoice = Choice.U; // Surrender
		        	else
		        		nextChoice = Choice.H; // Hit
		        }
		        
		        if(nextChoice == Choice.H) 
		        	gameVar.hit(gameVar.player.getHandIndex());
		        else if(nextChoice == Choice.S)
		        	gameVar.stand(gameVar.player.getHandIndex());
		        else if(nextChoice == Choice.P) {
		        	gameVar.splitting(gameVar.player.getHandIndex());
		        }
		        	
		        else if(nextChoice == Choice.U)
		        	gameVar.surrender();
		        else if(nextChoice == Choice.I)
		        	gameVar.insurance();
		        else if(nextChoice == Choice.D)
		        	gameVar.doubledown(0);
			}
		}
		
		// In this mode the end balance is used
		gameVar.gameState = gameVar.getBettingState();
		gameVar.statistics(BALANCE);
		System.out.println("bye");
		
	}
}
