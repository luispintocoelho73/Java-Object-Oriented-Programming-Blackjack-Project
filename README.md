# Java-Object-Oriented-Programming-Blackjack-Project

This project consisted of designing and implementating a simulator of the blackjack game together with card counting techniques.

This implementation included:

one player only, besides the dealer;

chips: white ($1), red ($5), green ($25), black ($100);

a hole card;

no restriction in the number of cards taken as long as it does not bust;

the dealer must stand in all 17’s;

blackjack pays 3 to 2;

side rules:

– insurance (pays 2 to 1);

– surrender (no late surrender);

– splitting (allow resplitting until the player has as many as four hands and doubling a hand after a splitting);

– doubling down (only on an opening hand worth 9, 10 or 11, and always doubles the bet; take only one more card from the dealer).

card count techniques (basic, hi-lo and ace-five strategies).

In the final implementation there are three different modes for playing blackjack. An interactive mode, where the player is playing with the dealer through commands in the command line. A debug mode, where the game is fully loaded from a file. A simulation mode where the game is automatically played with a card counting strategy to understand the average gain in the balance of the player.
