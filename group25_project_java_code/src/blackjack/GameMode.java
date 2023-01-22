package blackjack;

/**
 * This is an interface for the game mode in question (interactive, simulation or debug)
 */
public interface GameMode {
	boolean parameterVerification();
	void setParameters(String[] args);
    void gameFunction(String[] args);
}
