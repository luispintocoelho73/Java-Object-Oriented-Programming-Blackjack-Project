package blackjack;

/**
 * This is an interface for the game state (for example for the betting stage or the side stage, where side rules apply)
 */
public interface GameState {
    void bet(int betSize);
    void balance();
    void deal();
    void hit(int handIndex);
    void stand(int handIndex);
    void insurance();
    void surrender();
    void splitting(int handIndex);
    void doubledown(int handIndex);
    void advice(int handIndex);
    void statistics(double BALANCE);
}
