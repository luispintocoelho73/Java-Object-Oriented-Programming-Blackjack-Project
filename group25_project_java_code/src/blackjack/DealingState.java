package blackjack;

/**
 * This class uses elements of the interface class GameState in order to implement the dealing state
 */
public class DealingState implements GameState {

	game attribute;
	game gameVar; 
	/**
	 * This method implements the doubledown command while in the betting state
	 * @param newGame is a Game object that allows us to transit into the dealing state
	 */
	public DealingState(game newGame) {
		gameVar = newGame;
	}
	
	/**
	 * This method implements the bet command while in the dealing state
	 * @param betSize represents amount of the bet made by the player
	 */
	@Override
	public void bet(int betSize) {
		System.out.println("b: illegal command");

	}
	
	/**
	 * This method implements the balance command while in the dealing state
	 */
	@Override
	public void balance() {
		System.out.println("player current balance is "  + gameVar.player.getBalance());
	}

	/**
	 * This method implements the deal command while in the dealing state
	 */
	@Override
	public void deal() {
		// Deal 2 cards to dealer (1 hole)
		// Print: "dealer's hand 2S X"
		// Deal 2 cards to player
		// Print: player's hand 3S 4S (7)		
		
		// Create dealer's hand
		gameVar.dealer.hands.add(new Hand());
		gameVar.dealer.hands.get(0).addCard( gameVar.shoe.pickCard() );
		gameVar.dealer.hands.get(0).addCard( gameVar.shoe.pickCard() );
		
		// Show dealer's first card ("dealer's hand 2S X")
		System.out.println("dealer's hand " + gameVar.dealer.getFirstCard().toString() + " " + "X");
		
        // Add dealers' seen card to strategies' counters
        gameVar.aceFiveStrategy.addToCount(gameVar.dealer.getFirstCard());
        gameVar.hiLoStrategy.addToCount(gameVar.dealer.getFirstCard());		

		
		// Create player's hand
		gameVar.player.hands.add(new Hand());
		gameVar.player.hands.get(0).addCard( gameVar.shoe.pickCard() );
		gameVar.player.hands.get(0).addCard( gameVar.shoe.pickCard() );
		
		gameVar.player.hands.get(0).setBetSize(gameVar.player.getBetSize());
		
		// Add seen cards to strategies' counters
		gameVar.aceFiveStrategy.addToCount(gameVar.player.hands.get(0).getCard(0));
		gameVar.hiLoStrategy.addToCount(gameVar.player.hands.get(0).getCard(0));
		
		gameVar.aceFiveStrategy.addToCount(gameVar.player.hands.get(0).getCard(1));
		gameVar.hiLoStrategy.addToCount(gameVar.player.hands.get(0).getCard(1));
		
		
		
		// Show player's hand ("player's hand 3S 4S (7)")
		System.out.println("player's hand " + gameVar.player.hands.get(0).toString() + "("  + gameVar.player.hands.get(0).value() +  ")");
		
		// Blackjack
		if((gameVar.player.hands.get(0).value() == 21) && (gameVar.player.hands.get(0).size() == 2)) {
			System.out.println("blackjack!!!");
			gameVar.player.setNumberOfBlackjacks(gameVar.player.getNumberOfBlackjacks()+1);
		}

		gameVar.setGameState(gameVar.getSidesState());
	}
	/**
	 * This method implements the hit command while in the dealing state
	 * @param handIndex represents the hand of the player that is being considered
	 */
	@Override
	public void hit(int handIndex) {
		System.out.println("h: illegal command");
	}
	/**
	 * This method implements the stand command while in the dealing state
	 * @param handIndex represent the hand of the player that is being considered
	 */
	@Override
	public void stand(int handIndex) {
		System.out.println("s: illegal command");
	}
	/**
	 * This method implements the insurance command while in the dealing state
	 */
	@Override
	public void insurance() {
		System.out.println("i: illegal command");
	}
	/**
	 * This method surrender the insurance command while in the dealing state
	 */
	@Override
	public void surrender() {
		System.out.println("u: illegal command");
	}
	/**
	 * This method splitting the insurance command while in the dealing state
	 */
	@Override
	public void splitting(int handIndex) {
		System.out.println("p: illegal command");
	}
	/**
	 * This method implements the doubledown command while in the dealing state
	 * @param handIndex represents the hand of the player that is being considered
	 */
	@Override
	public void doubledown(int handIndex) {
		System.out.println("2: illegal command");
	}
	/**
	 * This method advice the stand command while in the dealing state
	 */
	@Override
	public void advice(int handIndex) {
		System.out.println("ad: illegal command");
	}
	/**
	 * This method statistics the stand command while in the dealing state
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

