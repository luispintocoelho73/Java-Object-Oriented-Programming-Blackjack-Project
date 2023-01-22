package blackjack;

import java.util.ArrayList;

/**
 * This class uses elements of the interface class GameState and in order to implement the splitting state of the game
 */
public class SplittingState implements GameState{

	game attribute;
	game gameVar;

	/**
	 * This method implements the splitting state
	 * @param newGame represents the current game object of class Game
	 */
	public SplittingState(game newGame) {
		gameVar = newGame;
	}
	
	/**
	 * This method implements the bet command while in the splitting state
	 * @param betSize represents the amount of the bet made by the player
	 */
	@Override
	public void bet(int betSize) {
		System.out.println("b: illegal command");
	}

	/**
	 * This method implements the balance command while in the splitting state
	 */
	@Override
	public void balance() {
		System.out.println("player current balance is " + gameVar.player.getBalance());
	}

	/**
	 * This method implements the deal command while in the splitting state
	 */
	@Override
	public void deal() {
		System.out.println("d: illegal command");
	}

	/**
	 * This method implements the hit command while in the playing state
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
					// Blackjack
					if((gameVar.player.hands.get(handIndex + 1).value() == 21) && (gameVar.player.hands.get(handIndex + 1).size() == 2)) {
						System.out.println("blackjack!!!");
						gameVar.player.setNumberOfBlackjacks(gameVar.player.getNumberOfBlackjacks()+1);
					}
					
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
	 * This method implements the stand command while in the splitting state
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
			// Blackjack
			if((gameVar.player.hands.get(handIndex + 1).value() == 21) && (gameVar.player.hands.get(handIndex + 1).size() == 2)) {
				System.out.println("blackjack!!!");
				gameVar.player.setNumberOfBlackjacks(gameVar.player.getNumberOfBlackjacks()+1);
			}
            // Go to splitting state
			gameVar.gameState = gameVar.getSplittingState();
		}				
	}

	/**
	 * This method implements the insurance command while in the splitting state
	 */
	@Override
	public void insurance() {
		System.out.println("i: illegal command");
	}
	
	/**
	 * This method implements the surrender command while in the splitting state
	 */
	@Override
	public void surrender() {
		// Verify surrender condition
		
		System.out.println("player is surrendering");
		System.out.println("dealer's hand " + gameVar.dealer.hands.get(0).toString());
		
        // Add seen cards to strategies' counters
		gameVar.aceFiveStrategy.addToCount(gameVar.dealer.hands.get(0).getCard(1));
		gameVar.hiLoStrategy.addToCount(gameVar.dealer.hands.get(0).getCard(1));
		
		// Blackjack
		if (gameVar.dealer.hands.get(0).value() == 21) {
			System.out.println("blackjack!!");
			gameVar.dealer.setNumberOfBlackjacks(gameVar.dealer.getNumberOfBlackjacks()+1);
		}

		System.out.println("player loses and his current balance is " + (gameVar.player.getBalance() + gameVar.player.getBetSize()*0.5));

		gameVar.player.setBalance(gameVar.player.getBalance() + gameVar.player.getBetSize()*0.5);

		gameVar.player.hands.clear();
		gameVar.dealer.hands.clear();

		gameVar.setGameState(gameVar.getBettingState());
		
		gameVar.standardBetting.setLastResult("loss");
		
		// Check if it is time to shuffle and shuffle if it's the case
		if(gameVar.timeToShuffle( gameVar.shuffle , gameVar.shoe.getCurrentSize(), gameVar.shoe.getInitialSize() ) ) {
			gameVar.shoe.shuffle();
		}
		
	}
	
	/**
	 * This method implements the splitting command while in the splitting state
	 */
	@Override
	public void splitting(int handIndex) {
		// Verify splitting condition (the player's hand is a pair)
		// The player doesn't have more than three hands - Always the case inside this state
		if(gameVar.player.hands.get(handIndex).isPair() && (gameVar.player.hands.size() <= 3)) {
			
			// Remove betSize from balance
			gameVar.player.setBalance(gameVar.player.getBalance() -  gameVar.player.getBetSize());			
			
			ArrayList<Hand> tempHands;
			tempHands = new ArrayList<>();
			
			// Add already existing hands before handIndex
			for(int j = 0; j < handIndex ; j++) {
				tempHands.add(gameVar.player.hands.get(j));
			}
			
			// Make first hand
			tempHands.add(new Hand());
			tempHands.get(handIndex).addCard(gameVar.player.hands.get(handIndex).getCard(0));
			// Add bet value to hand
			tempHands.get(handIndex).setBetSize(gameVar.player.getBetSize());
			
			// Make second hand
			tempHands.add(new Hand());
			tempHands.get(handIndex + 1).addCard(gameVar.player.hands.get(handIndex).getCard(1));
			// Add bet value to hand
			tempHands.get(handIndex + 1).setBetSize(gameVar.player.getBetSize());
			
			// Add already existing hands after handIndex
			int numOfHands = gameVar.player.hands.size();
            for(int i = handIndex + 1 ; i < numOfHands; i++) {
            	tempHands.add(gameVar.player.hands.get(i));
            }
            
            // Replace player's hands
            gameVar.player.setHands(tempHands);
            
            // Deal 1 card for each new hand of the player
            gameVar.player.hands.get(handIndex).addCard(gameVar.shoe.pickCard());
            gameVar.player.hands.get(handIndex + 1).addCard(gameVar.shoe.pickCard());
            
            System.out.println("player is splitting...");
            System.out.println("playing " + gameVar.player.handIndexToString() + " hand..."); 
            
            // Print current hand
            System.out.println("player's hand [" + (handIndex + 1 ) + "] " + gameVar.player.hands.get(handIndex).toString()+ " (" + gameVar.player.hands.get(handIndex).value() + ")"); //player's hand [1] JS 5S (15)
            if((gameVar.player.hands.get(handIndex).value() == 21) && (gameVar.player.hands.get(handIndex).size() == 2)) {
				System.out.println("blackjack!!!");
				gameVar.player.setNumberOfBlackjacks(gameVar.player.getNumberOfBlackjacks()+1);
			}
            
            // Add seen cards to strategies' counters
            gameVar.aceFiveStrategy.addToCount(gameVar.player.hands.get(handIndex).getCard(1));
            gameVar.aceFiveStrategy.addToCount(gameVar.player.hands.get(handIndex + 1).getCard(1));
            gameVar.hiLoStrategy.addToCount(gameVar.player.hands.get(handIndex).getCard(1));
            gameVar.hiLoStrategy.addToCount(gameVar.player.hands.get(handIndex + 1).getCard(1));    
            
            // Go to splitting state           
            gameVar.gameState = gameVar.getSplittingState();
            
		}
		else {
			System.out.println("p: illegal command");
		}
		
		
		
	}

	/**
	 * This method implements the doubledown command while in the splitting state
	 * @param handIndex represents the hand of the player that is being considered
	 */
	@Override
	public void doubledown(int handIndex) {
		// TODO Auto-generated method stub
		// Verify double down condition (opening hand worth 11, 10 or 9)
		if((gameVar.player.hands.get(0).value() == 11) || (gameVar.player.hands.get(0).value() == 10)  || (gameVar.player.hands.get(0).value() == 9)) {
		    gameVar.player.setBalance(gameVar.player.getBalance() - gameVar.player.getBetSize());
		    
		    // set bet size to double
		    gameVar.player.hands.get(handIndex).setBetSize( gameVar.player.getBetSize() * 2 );
		    
		    Card tempCard = gameVar.player.hands.get(handIndex).addCard( gameVar.shoe.pickCard() );
		    // Add seen cards to strategies' counters
		    gameVar.aceFiveStrategy.addToCount(tempCard);
		    gameVar.hiLoStrategy.addToCount(tempCard); 
		    
		    // Print hand
		    System.out.println("player's hand [" + (handIndex + 1 ) + "] " + gameVar.player.hands.get(handIndex).toString()+ " (" + gameVar.player.hands.get(handIndex+1).value() + ")");
		    
		    // Player can't bust
	
		    
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
				// Blackjack
				if((gameVar.player.hands.get(handIndex + 1).value() == 21) && (gameVar.player.hands.get(handIndex + 1).size() == 2)) {
					System.out.println("blackjack!!!");
					gameVar.player.setNumberOfBlackjacks(gameVar.player.getNumberOfBlackjacks()+1);
				}
	            // Go to splitting state
				gameVar.gameState = gameVar.getSplittingState();
			}						

	    }else {
	    	System.out.println("2: illegal command");
	    }
	}
	
	/**
	 * This method implements the advice command while in the splitting state
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
        	basicChoice = Choice.D; // Double
        }
        // Double if possible, otherwise stand
        else if(basicChoice == Choice.DS) {
        	basicChoice = Choice.D; // Double
        }
        // Surrender if possible, otherwise hit
        else if(basicChoice == Choice.RH) {
        	basicChoice = Choice.U; // Surrender
        }
        
		// Double if possible, otherwise hit
        if(hiLoChoice == Choice.DH) {
        	hiLoChoice = Choice.D; // Double
        }
        // Double if possible, otherwise stand
        else if(hiLoChoice == Choice.DS) {
        	hiLoChoice = Choice.D; // Double
        }
        // Surrender if possible, otherwise hit
        else if(hiLoChoice == Choice.RH) {
        	hiLoChoice = Choice.U; // Surrender
        }
        
        System.out.println("basic		  " + basicChoice.abbr);
        System.out.println("hi-lo		  " + hiLoChoice.abbr);
        
	}
	
	/**
	 * This method implements the statistics command while in the splitting state
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
