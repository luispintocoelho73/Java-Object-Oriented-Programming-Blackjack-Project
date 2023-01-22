package blackjack;

import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Class InterfaceMode uses elements of the interface class GameMode that allows for the implementation of the interface (interactive) mode
 */
public class InterfaceMode implements GameMode{
	
	int   MIN_BET = 0; // minimum bet (MIN_BET >= 1)
	int   MAX_BET = 0; // maximum bet (10 x MIN_BET<= MAX_BET <= 20 x MIN_BET)
    float BALANCE = 0; // initial balance (balance >= 50 x MIN_BET)
	int   SHOE    = 0; // number of 52-card decks in the shoe (4<= SHOE <=8)
	int   SHUFFLE = 0; // shuffle percentage of shoe played before shuffling (10 <= SHUFFLE <= 100)
	
	/**
	 * This Method verifies if the parameters inserted by the user are valid
	 * @return boolean that is true if the parameters are valid
	 */
	@Override
	public boolean parameterVerification() {
		// Check if minimum bet is >= 1
		if (MIN_BET < 1) {
			System.out.println("Please insert a Minimum Bet greater or equal to $1!");
			return false;
		}
		if (!((10*MIN_BET) <= MAX_BET && MAX_BET <= (20*MIN_BET))) {
			System.out.println("Please insert a Maximum Bet between 10*Minimum Bet and 20*Minimum Bet!");
			return false;
		}
		
		if (BALANCE  < 50*MIN_BET) {
			System.out.println("Please insert a Minimum Bet greater or equal to 50*Minimum Bet!");
			return false;
		}
		
		if (!(SHOE >= 4 && SHOE <= 8)) {
			System.out.println("Please insert a SHOE number between 4 and 8!");
			return false;
		}
		
		if (!(SHUFFLE >= 10 && SHUFFLE <= 100)) {
			System.out.println("Please insert a SHUFFLE parameter between 10 and 100!");
			return false;
		}
		
		return true;
	}
		
	/**
	 * This method attributes to variables the parameters inserted by the user
	 * @param args is an array of Strings with each parameter
	 */
	@Override
	public void setParameters(String[] args) {
		this.MIN_BET = Integer.parseInt(args[1]);
		this.MAX_BET = Integer.parseInt(args[2]);
		this.BALANCE = Float.parseFloat(args[3]);
		this.SHOE    = Integer.parseInt(args[4]);
		this.SHUFFLE = Integer.parseInt(args[5]);
		return;
	}
	
	/**
	 * This method implements the interactive mode
	 * @param args is an array of Strings with each parameter
	 */
    public void gameFunction(String[] args) {
		int betSize = 0; // updates betSize for when user doesn't write the betSize in the b command
		int betSizeInput = 0; // for validation of betSizeInput
		
		String userInput;		
		
		// Set parameters
		setParameters(args);
		
		// Parameter verification
		if(!parameterVerification()) {
			return;
		}
		
		// By definition if the user doesn't input a betSize in the b command 
		// and there is no "last bet" the betSize should be the minimum bet
		betSize = MIN_BET; 
		
		// CREATE GAME WITH GAME OPTIONS
		game gameVar = new game( MIN_BET , MAX_BET , BALANCE , SHOE , SHUFFLE );
		
		Scanner sc = new Scanner(System.in);
        
		while((userInput = sc.nextLine()).equals("q") != true) {
			
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
                    //System.out.println("BETSIZE: " + betSizeInput);
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
				gameVar.hit(gameVar.player.getHandIndex());
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
		System.out.println("bye");
		sc.close(); // close scanner
		
	
    	
    	return;
    }

}
