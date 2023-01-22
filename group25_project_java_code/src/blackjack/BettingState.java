package blackjack;
/**
 * This class uses elements of the interface class GameState and in order to implement the state of the game where the player has to make their bet
 */
public class BettingState implements GameState {

	game attribute;
	game gameVar; 
	/**
	 * This method initiates the betting state
	 * @param newGame object of class Game that represents the current blackjack game
	 */
	public BettingState(game newGame) {
		gameVar = newGame;
	}
	
	/**
	 * This method implements the bet command while in the betting state
	 * @param betSize defines the value of the bet made by the player
	 */
	@Override
	public void bet(int betSize) {
		
		// For good measure
		
		gameVar.player.hands.clear();
		gameVar.dealer.hands.clear();
		gameVar.player.resetHandIndex();
		
		// Setting end balance for statistics usage
		gameVar.player.setEndBalance(gameVar.player.getBalance());
		
		gameVar.player.setBalance( (gameVar.player.getBalance() - betSize));
		gameVar.player.setBetSize(betSize);
		
		gameVar.standardBetting.setLastBet(betSize);
		
		gameVar.setGameState(gameVar.getDealingState());
	}
	
	/**
	 * This method implements the balance command while in the betting state
	 */
	@Override
	public void balance() {
		System.out.println("player current balance is " + gameVar.player.getBalance());
	}
	
	/**
	 * This method implements the deal command while in the betting state
	 */
	@Override
	public void deal() {
		System.out.println("d: illegal command");
	}

	/**
	 * This method implements the hit command while in the betting state
	 * @param handIndex represents the hand of the player that is being considered
	 */
	@Override
	public void hit(int handIndex) {
		System.out.println("h: illegal command");
	}

	/**
	 * This method implements the stand command while in the betting state
	 */
	@Override
	public void stand(int handIndex) {
		System.out.println("s: illegal command");
	}
	
	/**
	 * This method implements the insurance command while in the betting state
	 */
	@Override
	public void insurance() {
		System.out.println("i: illegal command");
	}
	
	/**
	 * This method implements the surrender command while in the betting state
	 */
	@Override
	public void surrender() {
		System.out.println("u: illegal command");
	}

	/**
	 * This method implements the splitting command while in the betting state
	 */
	@Override
	public void splitting(int handIndex) {
		System.out.println("p: illegal command");
	}

	/**
	 * This method implements the doubledown command while in the betting state
	 * @param handIndex represents the hand of the player that is being considered
	 */
	@Override
	public void doubledown(int handIndex) {
		System.out.println("2: illegal command");
	}

	/**
	 * This method implements the advice command while in the betting state
	 */
	@Override
	public void advice(int handIndex) {
		// In this stage the advice is related to the betting size 
		System.out.println("standard	bet " + gameVar.standardBetting.nextBet());
		
		// For that we use the AceFiveStrategy
		System.out.println("ace-five	bet " + gameVar.aceFiveStrategy.nextBet());
		
	}
	/**
	 * This method implements the statistics command while in the betting state
	 * @param BALANCE represents the player's initial balance
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
			// getBalance because in this state there haven't been any bets
			double n6 = (double)gameVar.player.getBalance();
			double n7 = (100*((double)(gameVar.player.getBalance()-BALANCE)/(double)(BALANCE)));
			
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
