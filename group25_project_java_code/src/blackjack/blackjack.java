package blackjack;
import java.util.Scanner;

import java.util.regex.Pattern;
/**
 * This class implements the main method of the blackjack game
 * @author group 25
 *
 */
public class blackjack {
	public static void main(String[] args) {

		// Process arguments
		if(args[0].equals("-i")) {
			int   MIN_BET = 0; // minimum bet (MIN_BET >= 1)
			int   MAX_BET = 0; // maximum bet (10 x MIN_BET<= MAX_BET <= 20 x MIN_BET)
	        float BALANCE = 0; // initial balance (balance >= 50 x MIN_BET)
			int   SHOE    = 0; // number of 52-card decks in the shoe (4<= SHOE <=8)
			int   SHUFFLE = 0; // shuffle percentage of shoe played before shuffling (10 <= SHUFFLE <= 100)
			
			int betSize = 0; // updates betSize for when user doesn't write the betSize in the b command
			int betSizeInput = 0; // for validation of betSizeInput
			
			String userInput;
			
			System.out.println("Interface mode");
			// Invalid number of parameters
			if(args.length != 6) {
				System.out.println("Invalid number of parameters"); 
				return;
			}
			MIN_BET = Integer.parseInt(args[1]);
			MAX_BET = Integer.parseInt(args[2]);
			BALANCE = Float.parseFloat(args[3]);
			SHOE    = Integer.parseInt(args[4]);
			SHUFFLE = Integer.parseInt(args[5]);
			
			System.out.println("java -i " + Integer.toString(MIN_BET) + " " + Integer.toString(MAX_BET) + " "
			        + Float.toString(BALANCE) + " " + Integer.toString(SHOE) + " " + Integer.toString(SHUFFLE));
			
			// Parameter verification
			if (MIN_BET < 1) 
			{
				System.out.println("Please insert a Minimum Bet greater or equal to $1!");
				return;
			}
			if (!((10*MIN_BET) <= MAX_BET && MAX_BET <= (20*MIN_BET))) 
			{
				System.out.println("Please insert a Maximum Bet between 10*Minimum Bet and 20*Minimum Bet!");
				return;
			}
			
			if (BALANCE  < 50*MIN_BET) 
			{
				System.out.println("Please insert a Minimum Bet greater or equal to 50*Minimum Bet!");
				return;
			}
			
			if (!(SHOE >= 4 && SHOE <= 8)) 
			{
				System.out.println("Please insert a SHOE number between 4 and 8!");
				return;
			}
			
			if (!(SHUFFLE >= 10 && SHUFFLE <= 100)) 
			{
				System.out.println("Please insert a SHUFFLE parameter between 10 and 100!");
				return;
			}
			
			
			// By definition if the user doesn't input a betSize in the b command 
			// and there is no "last bet" the betSize should be the minimum bet
			betSize = MIN_BET; 
			
			// CREATE GAME WITH GAME OPTIONS
			game gameVar = new game( MIN_BET , MAX_BET , BALANCE , SHOE , SHUFFLE );
			
			Scanner sc = new Scanner(System.in);
	        
			while((userInput = sc.nextLine()).equals("q") != true) {
				//System.out.println(userInput);
				//System.out.println("balance: " + gameVar.player.getBalance());
				
				// User inputs bet command
				if(userInput.startsWith("b")) {
					// Process bet command
					// Validate bet command
					if(userInput.length() == 1) {
						System.out.println("player is betting " + betSize);
						gameVar.bet(betSize);
					}
				    else if(Pattern.matches("[b]\\s\\d+",userInput)) {
	                    betSizeInput = Integer.parseInt(userInput.replaceAll("[\\D]", ""));
	                    System.out.println("BETSIZE: " + betSizeInput);
						if( (betSizeInput  >=  MIN_BET )  &&  (betSizeInput <= MAX_BET)) {
						    betSize = betSizeInput;
						    
						    System.out.println("player is betting " + betSize);
						    gameVar.bet(betSize);
						}
						else {
							System.out.println("Invalid bet size");
						}
					}
				    else {
				    	System.out.println(userInput + ": illegal command");
				    }
					
				}
				
				// User inputs balance command
				else if (userInput.equals("$")) {
					gameVar.balance();
				}
				
				// User inputs deal command
				else if (userInput.equals("d")) {
					gameVar.deal();
				}
				
				// User inputs hit command
				else if (userInput.equals("h")) {
					gameVar.hit(0);
				}
				
				// User inputs stand command
				else if (userInput.equals("s")) {
					gameVar.stand(gameVar.player.getHandIndex());
				}
				
				// User inputs insurance command
				else if (userInput.equals("i")) {
					gameVar.insurance();
				}
				
				// User inputs surrender command
				else if (userInput.equals("u")) {
					gameVar.surrender();
				}
				
				// User inputs split command
				else if (userInput.equals("p")) {
					gameVar.splitting(gameVar.player.getHandIndex());
				}
				
				// User inputs doubledown command
				else if (userInput.equals("2")) {
					gameVar.doubledown(0);
				}
				
				// User inputs advice command
				else if (userInput.equals("ad")) {
					gameVar.advice(gameVar.player.getHandIndex());
				}
				
				// User inputs statistics command
				else if (userInput.equals("st")) {
					gameVar.statistics(BALANCE);
				}
				
				// User inputs quit command
				else if (userInput.equals("q")) {
					sc.close(); // close scanner
					return;
				}
				
				else {
					System.out.println(userInput + ": illegal command");
					
				}
			}
			sc.close(); // close scanner
			
		}
		
		
//////////////////////////////////////////////////////////////////////////////////////////////////////////
		// Simulation mode
		else if(args[0].equals("-s")) {
			System.out.println("Simulation mode");
			// Invalid number of parameters
			if(args.length != 8) {
				System.out.println("Invalid number of parameters"); 
				return;
			}
			int   MIN_BET  = Integer.parseInt(args[1]); // minimum bet (MIN_BET >= 1)
			int   MAX_BET  = Integer.parseInt(args[2]); // maximum bet (10 x MIN_BET<= MAX_BET <= 20 x MIN_BET)
	        float BALANCE  = Float.parseFloat(args[3]); // initial balance (balance >= 50 x MIN_BET)
			int   SHOE     = Integer.parseInt(args[4]); // number of 52-card decks in the shoe (4<= SHOE <=8)
			int   SHUFFLE  = Integer.parseInt(args[5]); // shuffle percentage of shoe played before shuffling (10 <= SHUFFLE <= 100)
			int   S_NUMBER = Integer.parseInt(args[6]); // number of shuffles to perform until ending the simulation
			String STRATEGY = args[7]; // counting cards’ strategy to use (combinations of BS, HL and AF)
			
			int numShuffles = 0;
			
			int betSize = 0; // updates betSize for when user doesn't write the betSize in the b command
			int betSizeInput = 0; // for validation of betSizeInput
			
			// Parameter verification
			if (MIN_BET < 1) 
			{
				System.out.println("Please insert a Minimum Bet greater or equal to $1!");
				return;
			}
			if (!((10*MIN_BET) <= MAX_BET && MAX_BET <= (20*MIN_BET))) 
			{
				System.out.println("Please insert a Maximum Bet between 10*Minimum Bet and 20*Minimum Bet!");
				return;
			}
			
			if (BALANCE  < 50*MIN_BET) 
			{
				System.out.println("Please insert a Minimum Bet greater or equal to 50*Minimum Bet!");
				return;
			}
			
			if (!(SHOE >= 4 && SHOE <= 8)) 
			{
				System.out.println("Please insert a SHOE number between 4 and 8!");
				return;
			}
			
			if (!(SHUFFLE >= 10 && SHUFFLE <= 100)) 
			{
				System.out.println("Please insert a SHUFFLE parameter between 10 and 100!");
				return;
			}
			
			// By definition if the user doesn't input a betSize in the b command 
			// and there is no "last bet" the betSize should be the minimum bet
			betSize = MIN_BET; 
			
			BettingStrategy bettingStrategy = null;
			BasicStrategy simulationStrategy = null;
			
			System.out.println("Strategy: " + STRATEGY);
			
			// Use Standard Betting Strategy
			if( STRATEGY.equals("BS") || STRATEGY.equals("HL") ) {
				bettingStrategy = new StandardBetting( MIN_BET , MAX_BET );
				System.out.println("Standard Betting strategy defined");
			}
			// Use Ace Five Betting Strategy
			else if( STRATEGY.equals("BS-AF") || STRATEGY.equals("HL-AF") ) {
				bettingStrategy = new AceFiveStrategy( MIN_BET , MAX_BET );
				System.out.println("Ace-Five Betting strategy defined");
			}
			
			if( STRATEGY.equals("BS") || STRATEGY.equals("BS-AF") ) {
				simulationStrategy = new BasicStrategy();
				System.out.println("Basic strategy defined");
			}
			else if( STRATEGY.equals("HL")  || STRATEGY.equals("HL-AF") ) {
				simulationStrategy = new HiLoStrategy();
				System.out.println("Hi-Lo strategy defined");
			}
			
			
			// CREATE GAME WITH GAME OPTIONS
			game gameVar = new game( MIN_BET , MAX_BET , BALANCE , SHOE , SHUFFLE );
			
			Choice nextChoice;
			int nextBetSize = 0;
			
			
			while(S_NUMBER > gameVar.shuffleCount) {
				// If simulation is currently in the betting state use the bet command
				if(gameVar.gameState == gameVar.getBettingState()) {
					// Get bet size
					bettingStrategy.setLastResult(gameVar.player.getLastResult());
					nextBetSize = bettingStrategy.nextBet();
					
					// Update lastBet value
					gameVar.player.setBetSize(nextBetSize);
					bettingStrategy.setLastBet(nextBetSize); 
					
					// Make bet
					gameVar.bet(nextBetSize);
				}
				
				// If inside the dealing state use the deal command
				else if(gameVar.gameState == gameVar.getDealingState()){
					gameVar.deal();
				}
				
				
				else{
					simulationStrategy.setInsurable( gameVar.isCommandAvailable( "i" , gameVar.gameState) );
					nextChoice = simulationStrategy.choice(gameVar.dealer.getFirstCard(), gameVar.player.hands.get(0));
					
					// Double if possible, otherwise hit
			        if(nextChoice == Choice.DH) {
			        	if( gameVar.isCommandAvailable("2",gameVar.gameState) ) 
			        	    nextChoice = Choice.D; // Double
			        	else
			        		nextChoice = Choice.H; // Hit
			        }
			        
			        // Double if possible, otherwise stand
			        else if(nextChoice == Choice.DS) {
			        	if( gameVar.isCommandAvailable("2",gameVar.gameState) )
			        	    nextChoice = Choice.D; // Double
			        	else
			        		nextChoice = Choice.S; // Stand
			        }
			        
			        // Surrender if possible, otherwise hit
			        else if(nextChoice == Choice.RH) {
			        	if( gameVar.isCommandAvailable("u",gameVar.gameState) )
			        	    nextChoice = Choice.U; // Surrender
			        	else
			        		nextChoice = Choice.H; // Hit
			        }
			        
			        if(nextChoice == Choice.H) 
			        	gameVar.hit(0);
			        else if(nextChoice == Choice.S)
			        	gameVar.stand(gameVar.player.getHandIndex());
			        else if(nextChoice == Choice.P) {
			        	//gameVar.splitting();
			        	System.out.println("splitting not implemented");
			        	return;
			        }
			        	
			        else if(nextChoice == Choice.U)
			        	gameVar.surrender();
			        else if(nextChoice == Choice.I)
			        	gameVar.insurance();
			        else if(nextChoice == Choice.D)
			        	gameVar.doubledown(0);
					
					
				}
				
			}
			
			
			
		}
		// 

	}
}
