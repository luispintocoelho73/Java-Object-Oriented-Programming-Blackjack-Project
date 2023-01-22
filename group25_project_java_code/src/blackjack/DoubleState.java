package blackjack;


/**
 * This class uses elements of the interface class Game state in order to implement the double state
 */
public class DoubleState implements GameState{

	game attribute;
	game gameVar;
	
	/**
	 * 
	 * @param newGame is a Game object that allows us to update the state of the blackjack game
	 */
	public DoubleState(game newGame) {
		gameVar = newGame;
	}
	
	/**
	 * The method DoubleState initializes the state of the game after the player makes a doubledown command
	 * @param betSize represents the size of the bet
	 */
	@Override
	public void bet(int betSize) {
		// TODO Auto-generated method stub
		System.out.println("b: illegal command");
	}
	
	/**
	 * This method implements the balance command while in the double state
	 */
	@Override
	public void balance() {
		System.out.println("player current balance is " + gameVar.player.getBalance());
		// Ver qual é o formato do balance que a professora quer
	}
	
	/**
	 * This method implements the deal command while in the double state
	 */
	@Override
	public void deal() {
		// TODO Auto-generated method stub
		System.out.println("d: illegal command");
	}
	/**
	 * This method implements the hit command while in the double state
	 * @param handIndex represents the hand of the player that is being considered
	 */
	@Override
	public void hit(int handIndex) {
		// TODO Auto-generated method stub
		// It is not possible to hit. 
		// Because we have doubled down we will only receive 1 card automatically
		System.out.println("h: illegal command");
	}

	/**
	 * This method implements the stand command while in the double state
	 * @param handIndex is an integer whose purpose is to identify which hand is being played
	 */
	@Override
	public void stand(int handIndex) {
		System.out.println("player stands");
        // If all the hands have been played -> Solve hands method
		// If there are hands to solve -> Go to next hand
		//                             -> Go to splitting state
		
		// Solve hands method
		// Show dealer's hole card
		// Hit until dealer's hand value >= 17
		// Solve hands
		gameVar.solveHands();
		gameVar.setGameState(gameVar.getBettingState());
	}

	/**
	 * This method implements the insurance command while in the dealing state
	 */
	@Override
	public void insurance() {
		// TODO Auto-generated method stub
		System.out.println("i: illegal command");
	}

	/**
	 * This method implements the surrender command while in the double state
	 */
	@Override
	public void surrender() {
		// TODO Auto-generated method stub
		System.out.println("Implement surrender");
		// Decidir para que estado voltar
		// gameVar.setGameState(gameVar.getPlayingState());
	}

	/**
	 * This method implements the splitting command while in the double state
	 */
	@Override
	public void splitting(int handIndex) {
		// TODO Auto-generated method stub
		System.out.println("p: illegal command");
	}
	
	/**
	 * This method implements the doubledown command while in the double state
	 * @param handIndex represents the hand of the player that is being considered
	 */
	@Override
	public void doubledown(int handIndex) {
		// TODO Auto-generated method stub
		System.out.println("2: illegal command");
	}

	/**
	 * This method implements the advice command while in the double state
	 */
	@Override
	public void advice(int handIndex) {
		// TODO Auto-generated method stub
		System.out.println("Implement advice");
	}
	
	/**
	 * This method implements the statistics command while in the double state
	 * @param BALANCE represents the initial balance of the player
	 */
	@Override
	public void statistics(double BALANCE) {
		if(gameVar.dealer.getNumberOfHands()>0) {
			System.out.println("Player: Number of Hands Played: " + gameVar.player.getNumberOfHands());
			System.out.println("Dealer: Number of Hands Played: " + gameVar.player.getNumberOfHands());
			System.out.println("Player: Number of Blackjacks: " + gameVar.player.getNumberOfBlackjacks());
			System.out.println("Dealer: Number of Blackjacks: " + gameVar.dealer.getNumberOfBlackjacks());
			System.out.println("Player: Number of Wins: " + gameVar.player.getWins());
			System.out.println("Player: Number of Loses: " + gameVar.player.getLosses());
			System.out.println("Player: Number of Pushes: " + gameVar.player.getPushes());
			System.out.println("Player: Balance: " + gameVar.player.getBalance());
			System.out.println("BALANCE: " + BALANCE);
			
			double n1 = (double)gameVar.player.getNumberOfBlackjacks()/(double)gameVar.player.getNumberOfHands();
			double n2 = (double)gameVar.dealer.getNumberOfBlackjacks()/(double)gameVar.dealer.getNumberOfHands();
			double n3 = (double)gameVar.player.getWins()/(double)gameVar.player.getNumberOfHands();
			double n4 = (double)gameVar.player.getLosses()/(double)gameVar.player.getNumberOfHands();
			double n5 = (double)gameVar.player.getPushes()/(double)gameVar.player.getNumberOfHands();
			double n6 = (double)gameVar.player.getBalance();
			double n7 = (100*((double)(gameVar.player.getBalance()-BALANCE)/(double)(BALANCE)));
			// TODO Auto-generated method stub
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
