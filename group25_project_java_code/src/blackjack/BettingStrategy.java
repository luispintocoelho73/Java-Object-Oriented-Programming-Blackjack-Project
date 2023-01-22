package blackjack;

/**
 * The class BettingStrategy is an abstract class that serves as a superclass of the AceFive and the Standard Betting Strategies
 */
public abstract class BettingStrategy {

	int lastBet;
	int minBet;
	int maxBet;
	String lastResult;
	
	/**
	 * This protected method will have different implementations based on different sub classes
	 * @returns int which is the value of the next bet according to the followed strategy
	 */
	protected abstract int nextBet();
	
	/**
	 * This method is a setter of the attribute lastBest
	 * @param newBetVal the new bet value to be assigned to the attribute
	 */
	public void setLastBet(int newBetVal) {
		this.lastBet = newBetVal;
		return;
	}
	/**
	 * This method is a setter of the attribute minBet
	 * @param newBetVal the new bet value to be assigned to the attribute
	 */
	public void setMinBet(int newBetVal) {
		this.minBet = newBetVal;
		return;
	}
	/**
	 * This method is a setter of the attribute maxBet
	 * @param newBetVal the new bet value to be assigned to the attribute
	 */
	public void setMaxBet(int newBetVal) {
		this.maxBet = newBetVal;
		return;
	}
	/**
	 * This class uses elements of the interface class GameState and in order to implement the state of the game where the player has to make their bet
	 * @param newResult the new bet value to be assigned to the attribute
	 */
	public void setLastResult(String newResult){
		this.lastResult = new String(newResult);
		return;
	}
}
