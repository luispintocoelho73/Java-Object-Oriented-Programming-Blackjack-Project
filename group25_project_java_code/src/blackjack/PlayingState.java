package blackjack;

/**
 * This class uses elements of the interface class Game state in order to implement the playing state
 */
public class PlayingState implements GameState{

	game attribute;
	game gameVar;
	
	/**
	 * This method implements the playing state after 
	 * @param newGame current blackjack game
	 */
	public PlayingState (game newGame) {
		gameVar = newGame;
	}
	
	/**
	 * This method implements the bet command while in the playing state
	 * @param betSize represents the v
	 */
	@Override
	public void bet(int betSize) {
		System.out.println("b: illegal command");
	}
	
	/**
	 * This method implements the balance command while in the playing state
	 */
	@Override
	public void balance() {
		System.out.println("player current balance is "  + gameVar.player.getBalance());
	}
	
	/**
	 * This method implements the deal command while in the playing state
	 */
	@Override
	public void deal() {
		System.out.println("d: illegal command");
	}
	
	/**
	 * This method implements the handIndex command while in the playing state
	 * @param handIndex represents the hand of the player that is being considered
	 */
	@Override
	public void hit(int handIndex) {
		Card tempCard;
		System.out.println("player hits");
		
		// Jump to the playingState if the player doesn't bust
		gameVar.setGameState(gameVar.getPlayingState());
		tempCard = gameVar.player.hands.get(handIndex).addCard( gameVar.shoe.pickCard() );
		
		// Add seen cards to strategies' counters
		gameVar.aceFiveStrategy.addToCount(tempCard);
		gameVar.hiLoStrategy.addToCount(tempCard);
		
		
		// If the player has more than 1 hand
		if(gameVar.player.hands.size() > 1) {
			// Print player's hand after hit
			// Show player's hand ("player's hand 3S 4S (7)")
			System.out.println("player's hand [" + (handIndex + 1 ) + "] " + gameVar.player.hands.get(handIndex).toString()+ " (" + gameVar.player.hands.get(handIndex).value() + ")");
			
			// Blackjack
			if((gameVar.player.hands.get(handIndex).value() == 21) && (gameVar.player.hands.get(handIndex).size() == 2)) {
				System.out.println("blackjack!!!");
				gameVar.player.setNumberOfBlackjacks(gameVar.player.getNumberOfBlackjacks()+1);
			}
			
			// Check if hit results in a bust for the player
			if(gameVar.player.hands.get(handIndex).isBust()) {
				
				// Inform the player that he busted
				System.out.println( "player busts [" + handIndex + "]");
				
				// Check if we are currently in the last hand
				if( handIndex ==  (gameVar.player.hands.size() - 1) ) {
					System.out.println("Last hand!!!!");
					// Sends the player to the bettingState because the player busted the last hand
					gameVar.solveHands(); 
					gameVar.gameState = gameVar.getBettingState();
				}
				else {
					// Go to next hand instead of beginning
					gameVar.player.incrementHandIndex();
					// Print current hand
		            System.out.println("playing " + gameVar.player.handIndexToString() + " hand..."); 
		            System.out.println("player's hand [" + (handIndex + 2 ) + "] " + gameVar.player.hands.get(handIndex + 1).toString()+ " (" + gameVar.player.hands.get(handIndex + 1).value() + ")"); 
					
					
					gameVar.gameState = gameVar.getSplittingState();	
				}
			}
			// If the player didn't bust keep playing the hand without the possibility to double or surrender
			else {
				// Go to playing state
				gameVar.setGameState(gameVar.getPlayingState());
			}
		}
		// If the player has only one hand
		else {
			// Show player's hand ("player's hand 3S 4S (7)")
			System.out.println("player's hand " + gameVar.player.hands.get(0).toString() + "("  + gameVar.player.hands.get(0).value() +  ")");
			if(gameVar.player.hands.get(0).value() > 21) {
				System.out.println("player busts");
				gameVar.solveHands();
				gameVar.gameState = gameVar.getBettingState();
				
			}
			else {
				gameVar.gameState = gameVar.getPlayingState();
				// Blackjack
				if((gameVar.player.hands.get(handIndex).value() == 21) && (gameVar.player.hands.get(handIndex).size() == 2)) {
					System.out.println("blackjack!!!");
					gameVar.player.setNumberOfBlackjacks(gameVar.player.getNumberOfBlackjacks()+1);
				}
			}
		}
		
	}

	/**
	 * This method implements the stand command while in the playing state
	 */
	@Override
	public void stand(int handIndex) {
		System.out.println("player stands");
        // If all the hands have been played -> Solve hands method
		// If there are still hands to play  -> Go to next hand
		//                                   -> Go to splitting state
				
		// Check if it's the player's last hand
		if( (handIndex + 1)  == gameVar.player.hands.size()) {

			// If so solve all hands
			gameVar.solveHands();
			gameVar.setGameState(gameVar.getBettingState());
		}
		else {
			// Else go to next hand instead of beginning
			gameVar.player.incrementHandIndex();
			
			// Print current hand
            System.out.println("playing " + gameVar.player.handIndexToString() + " hand..."); 
            System.out.println("player's hand [" + (handIndex + 2 ) + "] " + gameVar.player.hands.get(handIndex + 1).toString()+ " (" + gameVar.player.hands.get(handIndex+1).value() + ")");
            
            // Go to splitting state
			gameVar.gameState = gameVar.getSplittingState();
		}				
	}

	/**
	 * This method implements the insurance command while in the playing state
	 */
	@Override
	public void insurance() {
		System.out.println("i: illegal command");
	}
	
	/**
	 * This method implements the surrender command while in the playing state
	 */
	@Override
	public void surrender() {
		System.out.println("u: illegal command");
	}
	
	/**
	 * This method implements the split command while in the playing state
	 */
	@Override
	public void splitting(int handIndex) {
		System.out.println("p: illegal command");
	}
	
	/**
	 * This method implements the doubledown command while in the playing state
	 * @param handIndex represents the hand of the player that is being considered
	 */
	@Override
	public void doubledown(int handIndex) {
		System.out.println("2: illegal command");
	}

	/**
	 * This method implements the advice command while in the playing state
	 */
	@Override
	public void advice(int handIndex) {
		
		Choice basicChoice =  gameVar.basicStrategy.choice( gameVar.dealer.getFirstCard() , gameVar.player.hands.get(handIndex) );
		gameVar.hiLoStrategy.setSurrenderable(false);
		gameVar.hiLoStrategy.setInsurable(false);
		gameVar.hiLoStrategy.updateTrueCount(gameVar.shoe.getNumOfDecks() * (gameVar.shoe.getInitialSize()/gameVar.shoe.getCurrentSize()));
		Choice hiLoChoice = gameVar.hiLoStrategy.choice( gameVar.dealer.getFirstCard() , gameVar.player.hands.get(handIndex)); //not insurable
		
		// Double if possible, otherwise hit
        if(basicChoice == Choice.DH) {
        	basicChoice = Choice.H; // Hit
        }
        // Double if possible, otherwise stand
        else if(basicChoice == Choice.DS) {
        	basicChoice = Choice.S; // Stand
        }
        // Surrender if possible, otherwise hit
        else if(basicChoice == Choice.RH) {
        	basicChoice = Choice.H; // Hit
        }
        
		// Double if possible, otherwise hit
        if(hiLoChoice == Choice.DH) {
        	hiLoChoice = Choice.H; // Hit
        }
        // Double if possible, otherwise stand
        else if(hiLoChoice == Choice.DS) {
        	hiLoChoice = Choice.S; // Stand
        }
        // Surrender if possible, otherwise hit
        else if(hiLoChoice == Choice.RH) {
        	hiLoChoice = Choice.H; // Hit
        }
        
        System.out.println("basic		  " + basicChoice.abbr);
        System.out.println("hi-lo		  " + hiLoChoice.abbr);
        
	}
	
	/**
	 * This method implements the statistics command while in the playing state
	 * @param BALANCE represents the initial balance of the player
	 */
	@Override
	public void statistics(double BALANCE) {
		int numOfPlayerHands = gameVar.player.getWins() + gameVar.player.getPushes() + gameVar.player.getLosses();
		int numOfDealerHands = gameVar.dealer.getWins() + gameVar.dealer.getPushes() + gameVar.dealer.getLosses();
		if( numOfPlayerHands > 0 ) {
			
			double n1 = (double)gameVar.player.getNumberOfBlackjacks()/(double) numOfPlayerHands;
			double n2 = (double)gameVar.dealer.getNumberOfBlackjacks()/(double) numOfDealerHands;
			double n3 = (double)gameVar.player.getWins()/(double) numOfPlayerHands;
			double n4 = (double)gameVar.player.getLosses()/(double)numOfPlayerHands;
			double n5 = (double)gameVar.player.getPushes()/(double)numOfPlayerHands;
			// getEndBalance because in this state there have been bets in hands that where not resolved
			double n6 = (double)gameVar.player.getEndBalance();
			double n7 = (100*((double)(gameVar.player.getEndBalance()-BALANCE)/(double)(BALANCE)));

			System.out.println("BJ P/D   " + n1 + "/" + n2);
			System.out.println("Win      " + n3);
			System.out.println("Lose     " + n4);
			System.out.println("Push     " + n5);
			System.out.println("Balance     " + n6 + "(" + n7 + "%)");
		}
		else {
			System.out.println("st: illegal command");
		}
	}

}