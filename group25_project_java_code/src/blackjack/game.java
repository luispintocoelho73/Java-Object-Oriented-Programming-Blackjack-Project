package blackjack;

/**
 * This class houses game objects which are iterations of the blackjack game
 */
public class game {
	GameState bettingState;
	GameState dealingState;
	GameState sidesState;
	GameState doubleState;
	GameState splittingState;
	GameState playingState;
	
	GameState gameState;
	
	
	// Gamemodes
	GameMode interfaceMode;
	GameMode simulationMode;
	GameMode debugMode;
	
	GameMode gameMode;
	
	Player player;
	Dealer dealer;
	Shoe shoe;
	
	BasicStrategy basicStrategy;
	HiLoStrategy hiLoStrategy;
	AceFiveStrategy aceFiveStrategy;
	StandardBetting standardBetting;
	
	int shuffleCount;
	int handIndex;
	int shuffle;
	
	
	/**
	 * This method implements the statistics command while in the double state
	 * @param MIN_BET represents the minimum bet possible for the player
	 * @param MAX_BET represents the maximum bet possible for the player
	 * @param BALANCE represents the initial balance of the player
	 * @param SHOE represents the number of decks present in the shoe
	 * @param SHUFFLE represent the percentage of shoe played before shuffling
	 */
	// Param: game options for interactive game
	public game(int MIN_BET, int MAX_BET, float BALANCE, int SHOE, int SHUFFLE) {
		bettingState   =  new BettingState(this);
		dealingState   =  new DealingState(this);
		sidesState     =  new SidesState(this);
		doubleState    =  new DoubleState(this);
		splittingState =  new SplittingState(this);
		playingState   =  new PlayingState(this);
		
		gameState = bettingState;
		
		player = new Player();
		dealer = new Dealer();
		shoe = new Shoe(SHOE);
		shuffle = SHUFFLE;
		
		basicStrategy = new BasicStrategy();
		hiLoStrategy = new HiLoStrategy();
		aceFiveStrategy = new AceFiveStrategy( MIN_BET , MAX_BET );
		standardBetting = new StandardBetting( MIN_BET , MAX_BET );
		
		interfaceMode = new InterfaceMode();
		debugMode = new DebugMode();
		simulationMode = new SimulationMode();
		
		
		
		player.setBalance(BALANCE);	
		
		
		
		shuffleCount = 0;
		handIndex = 0;
	}
	
	/**
	 * This method implements the statistics command while in the double state
	 * @param MIN_BET represents the minimum bet possible for the player
	 * @param MAX_BET represents the maximum bet possible for the player
	 * @param BALANCE represents the initial balance of the player
	 * @param SHOE represents the number of decks present in the shoe
	 * @param shoeFile is an array of strings that contains all the cards in the shoe
	 */
	public game(int MIN_BET, int MAX_BET, float BALANCE, int SHOE, String[] shoeFile) {
		bettingState   =  new BettingState(this);
		dealingState   =  new DealingState(this);
		sidesState     =  new SidesState(this);
		doubleState    =  new DoubleState(this);
		splittingState =  new SplittingState(this);
		playingState   =  new PlayingState(this);
		this.shuffle = 100;
		gameState = bettingState;
		
		player = new Player();
		dealer = new Dealer();
		shoe = new Shoe(SHOE, shoeFile);
		
		basicStrategy = new BasicStrategy();
		hiLoStrategy = new HiLoStrategy();
		aceFiveStrategy = new AceFiveStrategy( MIN_BET , MAX_BET );
		standardBetting = new StandardBetting( MIN_BET , MAX_BET );
		
		interfaceMode = new InterfaceMode();
		debugMode = new DebugMode();
		simulationMode = new SimulationMode();
		
		
		player.setBalance(BALANCE);	
		
		
		handIndex = 0;
	}
	
	
	
	
	/**
	 * This method verifies the state of the game after a player hits (did the bust?)
	 */
	public void solveHandsHit() {
		// if the player busts, increment the number of wins of the dealer and the number of losses of the player
		if(this.player.hands.get(0).value() > 21) {
			System.out.println("player busts");
			System.out.println("dealer's hand " + this.dealer.hands.get(0).toString() + "("  + this.dealer.hands.get(0).value() +  ")");
			
			// Add seen cards to strategies' counters
            this.aceFiveStrategy.addToCount(this.dealer.hands.get(0).getCard(1));
            this.hiLoStrategy.addToCount(this.dealer.hands.get(0).getCard(1)); 
			
			// blackjack 
			if(this.dealer.hands.get(0).value() == 21) {
				System.out.println("blackjack!!");
				// insurance
				if(this.player.getHasInsured() == true) {
						System.out.println("player wins insurance");
						this.player.setBalance(this.player.getBalance() + this.player.getBetSize());
					}
				}
			System.out.println("player loses and his current balance is " + this.player.getBalance());
			this.dealer.setWins( this.dealer.getWins() + 1 );
			this.player.setLosses(this.player.getLosses() + 1);
			this.player.setLastResult("loss");
			
			// Clear hands
			player.hands.clear();
			dealer.hands.clear();
			
			// Check if it is time to shuffle and shuffle if it's the case
			if(timeToShuffle( this.shuffle , this.shoe.getCurrentSize(), this.shoe.getInitialSize() ) ) {
				this.shoe.shuffle();
				incrementShuffleCount();
				
				// Reset counts
				this.aceFiveStrategy.resetCount();
				this.hiLoStrategy.resetCount();
			}
			
			this.player.setHasInsured(false);
			this.setGameState(this.getBettingState());
			return;
		}
		
		else return;
	}
	
	// add conditions related to splitting and blackjack
	/**
	 * This method verifies the state of the game after the player stands and the dealer reveals their second card
	 */
	public void solveHands() {
		int i = 1;
		Card tempCard;
		// Show dealer's hand: "dealer's hand 2S 5H (7)"
		System.out.println("dealer's hand " + this.dealer.hands.get(0).toString() + "("  + this.dealer.hands.get(0).value() +  ")");
		
		
		// Add seen cards to strategies' counters
		this.aceFiveStrategy.addToCount(this.dealer.hands.get(0).getCard(1));
		this.hiLoStrategy.addToCount(this.dealer.hands.get(0).getCard(1)); 
		
		// blackjack 
		if(this.dealer.hands.get(0).value() == 21) {
			System.out.println("dealer stands");
			System.out.println("blackjack!!");
			// insurance
			if(this.player.getHasInsured() == true) {
				System.out.println("player wins insurance");
				this.player.setBalance(this.player.getBalance() + this.player.getBetSize());
			}
		}
		// If the dealer's hand value is below 17 hit until it reaches at least 17
		if(this.dealer.hands.get(0).value() < 17) {
			while(this.dealer.hands.get(0).value() < 17) {
			    tempCard = this.dealer.hands.get(0).addCard( this.shoe.pickCard() );
			    
				// Add seen cards to strategies' counters
				this.aceFiveStrategy.addToCount(tempCard);
				this.hiLoStrategy.addToCount(tempCard); 
			    
			    System.out.println("dealer hits");
			    System.out.println("dealer's hand " + this.dealer.hands.get(0).toString() + "("  + this.dealer.hands.get(0).value() +  ")");
			}
			System.out.println("dealer stands");
		}
		
		
		
		// If dealer busts, then every hand wins except if they are also a bust
		if(this.dealer.hands.get(0).isBust()) {
			for(Hand h : this.player.hands) {
				// Check each hand for bust and add points and balance accordingly
				if(!(h.isBust())) {
					
					if(this.player.hands.size()>1)
						System.out.println("player wins [" + i + "] and his current balance is " + (this.player.getBalance()+2*h.getBetSize() ));
					else
						System.out.println("player wins and his current balance is " + (this.player.getBalance() + 2*h.getBetSize() ));
					
					this.player.setBalance( this.player.getBalance() + 2*h.getBetSize());
					
					this.player.setWins( this.player.getWins() + 1 );
					this.dealer.setLosses(this.dealer.getLosses() + 1);
					this.player.setLastResult("win");

				}
			    i++;
			}
		}
		// Compare hand values and check for busts
		else {
			for(Hand h : this.player.hands) {
				
				// Check for win
				if( this.dealer.hands.get(0).value() < h.value() ) {
					if(h.isBust()) {
						// dealer wins// increment dealer's wins and player's losses
						if(this.player.hands.size()>1)
							System.out.println("player loses [" + i + "] and his current balance is " + this.player.getBalance());
						else
							System.out.println("player loses and his current balance is " + this.player.getBalance());
						this.dealer.setWins( this.dealer.getWins() + 1 );
						this.player.setLosses(this.player.getLosses() + 1);
						this.player.setLastResult("loss");
						
					}
					else {
						// player wins// increment players's wins and dealer's losses
						// blackjack (natural 21) + win
						if( (h.value() == 21) && (h.size() == 2)) {
							if(this.player.hands.size()>1)
							    System.out.println("player wins [" + i + "] and his current balance is " + (this.player.getBalance() + 2.5*h.getBetSize()));
							else
								System.out.println("player wins and his current balance is " + (this.player.getBalance() + 2.5*h.getBetSize()));
							this.player.setBalance( this.player.getBalance() + 2.5*h.getBetSize());
							this.player.setWins( this.player.getWins() + 1 );
							this.dealer.setLosses(this.dealer.getLosses() + 1);		
							this.player.setLastResult("win");
						}
						// win with less than 21 points
						else {
							if(this.player.hands.size()>1)
								System.out.println("player wins [" + i + "] and his current balance is " + (this.player.getBalance() + 2*h.getBetSize()));
							else
							    System.out.println("player wins and his current balance is " + (this.player.getBalance() + 2*h.getBetSize()));
							this.player.setBalance( this.player.getBalance() + 2*h.getBetSize());	
							this.player.setWins( this.player.getWins() + 1 );
							this.player.setLastResult("win");
							this.dealer.setLosses(this.dealer.getLosses() + 1);	
						}
					}
				}
				// Check for push 
				else if ( this.dealer.hands.get(0).value() == h.value() ) {
					// Increment push statistics
					if(this.player.hands.size()>1)
						System.out.println("player pushes [" + i + "] and his current balance is " + (this.player.getBalance()+h.getBetSize()));
					else
					    System.out.println("player pushes and his current balance is " + (this.player.getBalance()+h.getBetSize()));
					this.player.setBalance( this.player.getBalance() + h.getBetSize());
					this.player.setPushes( this.player.getPushes() + 1 );
					this.dealer.setPushes(this.dealer.getPushes() + 1);	
					this.player.setLastResult("push");
				}
				// Check for loss
				else if(this.dealer.hands.get(0).value() > h.value()){
					// dealer wins// increment dealer's wins and player's losses
					if(this.player.hands.size()>1)
						System.out.println("player loses [" + i + "] and his current balance is " + this.player.getBalance());
					else
					    System.out.println("player loses and his current balance is " + this.player.getBalance());
					this.dealer.setWins( this.dealer.getWins() + 1 );
					this.player.setLosses(this.player.getLosses() + 1);
					this.player.setLastResult("loss");
				}
				
				i++;
				
			}
		}
		this.player.setHasInsured(false);
		// Clear hands
		player.hands.clear();
		dealer.hands.clear();
		
		// Check if it is time to shuffle and shuffle if it's the case
		if(timeToShuffle( this.shuffle , this.shoe.getCurrentSize(), this.shoe.getInitialSize() ) ) {
			this.shoe.shuffle();
			incrementShuffleCount();
			
			// Reset counts
			this.aceFiveStrategy.resetCount();
			this.hiLoStrategy.resetCount();
		}
		this.standardBetting.setLastResult(this.player.lastResult);
		
		return;
		
	}
	
	/**
	 * This method increments the number of shuffles taken
	 */
	public void incrementShuffleCount() {
		this.shuffleCount = this.shuffleCount + 1;
	}
	
	/**
	 * This method is a setter
	 */
	void setGameState(GameState newGameState) {
	    gameState = newGameState;
	}
	/**
	 * This method implements the bet command
	 * @param betSize represents the amount of the bet made by the player
	 */
	public void bet(int betSize) {
		gameState.bet(betSize);
	} 
	/**
	 * This method implements the balance command
	 */
	public void balance() {
		gameState.balance();
	} 
	
	/**
	 * This method implements the deal command
	 */
	public void deal(){
		gameState.deal();
	}
	
	/**
	 * This method implements the hit command
	 * @param handIndex represents which player hand is being considered (after splitting for example)
	 */
	public void hit(int handIndex){
		gameState.hit(handIndex);
	}
	
	/**
	 * This method implements the stand command
	 * @param handIndex represents which player hand is being considered (after splitting for example)
	 */
	public void stand(int handIndex){
		gameState.stand(handIndex);
	}
	
	/**
	 * This method implements the insurance command
	 */
	public void insurance(){
		gameState.insurance();
	}
	
	/**
	 * This method implements the surrender command
	 */
	public void surrender(){
		gameState.surrender();
	}
	
	/**
	 * This method implements the splitting command
	 * @param handIndex represents which player hand is being considered (after splitting for example)
	 */
	public void splitting(int handIndex){
		gameState.splitting(handIndex);
	}
	
	/**
	 * This method implements the doubledown command
	 * @param handIndex represents which player hand is being considered (after splitting for example)
	 */
	public void doubledown(int handIndex){
		gameState.doubledown(handIndex);
	}
	
	/**
	 * This method implements the advice command
	 */
	public void advice(int handIndex){
		gameState.advice(handIndex);
	}
	
	/**
	 * This method implements the statistics command
	 * @param BALANCE represents the initial balance of the player
	 */
	public void statistics(double BALANCE){
		gameState.statistics(BALANCE);
	}
	
	public GameState getBettingState() {return bettingState;}
	public GameState getDealingState() {return dealingState;}
	public GameState getSidesState() {return sidesState;}	
	public GameState getDoubleState() {return doubleState;}	
	public GameState getSplittingState() {return splittingState;}	
	public GameState getPlayingState() {return playingState;}	
	
	
	/**
	 * This method  is a setter for the game mode
	 */
	void setGameMode(GameMode newGameMode) {
	    gameMode= newGameMode;
	}
	
	/**
	 * This method chooses is used to enter a specific mode (interactive, simulation or debug)
	 * @param args is an array of strings with the parameters inserted by the user
	 */
	public void gameFunction(String[] args) {
		gameMode.gameFunction(args);
	}
	
	public GameMode getInterfaceMode() {return interfaceMode;}
	public GameMode getDebugMode() {return debugMode;}
	public GameMode getSimulationMode() {return simulationMode;}
	
	
	
	
	
	/**
	 * This method checks if a given command is valid or not 
	 * @param command in form of a String
	 * @param state which represents the current game state
	 * @return boolean which is true when the command is valid
	 */
	public boolean isCommandAvailable(String command, GameState state) {
		// In the betting state the following commands are available:
		// Bet; Balance; Advice; Statistics.
		if( state == getBettingState() ) {
			if( command.equals("b") || command.equals("$") || command.equals("ad") || command.equals("st") ) 
				return true;
			else
				return false;
		}
		
		// In the dealing state the following commands are available:
		// Deal; Balance; Advice; Statistics.
		else if( state == getDealingState() ) {
			if( command.equals("d") || command.equals("$") || command.equals("ad") || command.equals("st") ) 
				return true;
			else
				return false;
		}
		
		// In the sides state the following commands are available:
		// Balance; Hit; Stand; Surrender; Doubledown; Split; Insurance; Advice; Statistics.
		else if ( state == getSidesState() ) {
			//These commands require no further verification
			if( command.equals("$") || command.equals("h") || command.equals("s") || command.equals("u")  || command.equals("ad") || command.equals("st"))
				return true;
			
			// Doubledown command requires verification
			else if(command.equals("2")) {
				int handVal = this.player.hands.get(handIndex).value();
				if( ( handVal == 11) || (handVal == 10)  || (handVal == 9) && ((this.gameState == this.getSidesState()) || (this.gameState == this.getSplittingState())))
					return true;
				else
					return false;
			}
			// Split command requires verification
			else if(command.equals("p")) {
				if(this.player.hands.get(handIndex).isPair() && (this.player.hands.size() <=3) ) //Verify if number of hands <=3
					return true;
				else 
					return false;
			}
			
			// Insurance command requires verification
			else if(command.equals("i")) {
				if(this.dealer.getFirstCard().isAce())
					return true;
				else
					return false;
			}
			
			else
				return false;
		}
		
		// Balance; Hit; Stand; Advice; Statistics.
		else if( state == getPlayingState() ) {
			//These commands require no further verification
			if( command.equals("$") || command.equals("h") || command.equals("s") || command.equals("ad") || command.equals("st") )
				return true;
			else
				return false;
		}
		
		else if( state == getSplittingState() ) {
			//These commands require no further verification
			if( command.equals("$") || command.equals("h") || command.equals("s") || command.equals("ad") || command.equals("st") )
				return true;
			
			// Doubledown command requires verification
			else if(command.equals("2")) {
				int handVal = this.player.hands.get(handIndex).value();
				if( ( handVal == 11) || (handVal == 10)  || (handVal == 9) )
					return true;
				else
					return false;
			}
			// Split command requires verification
			else if(command.equals("p")) {
				if(this.player.hands.get(handIndex).isPair() && (this.player.hands.size() <=3) ) //Verify if number of hands <=3
					return true;
				else 
					return false;
			}
		}
		
		else
			return false;
		
		return false;
	}
	
	/**
	 * This method checks if the shoe needs to be shuffled 
	 * @param SHUFFLE represents the shuffle percentage 
	 * @param ShoeSize represents the size of the shoe
	 * @param ShoeInitialSize represents the shoe initial size
	 * @return boolean which is true if the shoe is to be shuffled
	 */
	public boolean timeToShuffle( int SHUFFLE , int  ShoeSize , int ShoeInitialSize) {
        float usedPercentage = 100 * (ShoeInitialSize - ShoeSize) / (ShoeInitialSize);
        // If the reached percentage is lower than the defined percentage to shuffle on: shuffle
       
        if(SHUFFLE <= usedPercentage) {
        	return true;
        }
        else
		    return false;
	}
	
	
	

}
