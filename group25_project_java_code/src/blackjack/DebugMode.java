package blackjack;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;

/**
 * The class DebugMode uses elements of the interface class of the GameMode class and has the purpose of housing objects related to an iteration  of the debug mode
 */
public class DebugMode implements GameMode{
	int   MIN_BET  = 0; // minimum bet (MIN_BET >= 1)
	int   MAX_BET  = 0; // maximum bet (10 x MIN_BET<= MAX_BET <= 20 x MIN_BET)
    float BALANCE  = 0; // initial balance (balance >= 50 x MIN_BET)
	String SHOEFILE;
	String COMMANDFILE;
	
	int shoeSize = 0;
	
	/**
	 * Method that verifies if the inserted parameters are valid
	 * @return boolean that is true when all the parameters are valid
	 */
	@Override
	public boolean parameterVerification() {
		// TODO Auto-generated method stub
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
		return true;
	}

	/**
	 * Method that assigns each attribute its respective values
	 * @param args is an array of strings that houses the parameters inserted by the user
	 */
	@Override
	public void setParameters(String[] args) {
		// TODO Auto-generated method stub
		this.MIN_BET = Integer.parseInt(args[1]);
		this.MAX_BET = Integer.parseInt(args[2]);
		this.BALANCE = Float.parseFloat(args[3]);
		this.SHOEFILE = fileToString(args[4]);
		this.shoeSize = SHOEFILE.length()/52;
		COMMANDFILE = fileToString(args[5]);	
		
	}
	
	/**
	 * Method that executes the debug mode
	 * @param args is an array of Strings that contains the user's parameters
	 */
	@Override
	public void gameFunction(String[] args) {
		
		int betSize = 0; // updates betSize for when user doesn't write the betSize in the b command
		int betSizeInput = 0; // for validation of betSizeInput
		
		setParameters(args);
		betSize = MIN_BET;
		if(!(this.parameterVerification())){
			return;
		}	
		String[] wordsShoeFile = this.SHOEFILE.split(" ");

		String[] wordsCommandFile = this.COMMANDFILE.split(" ");
		// CREATE GAME WITH GAME OPTIONS
		game gameVar = new game( MIN_BET , MAX_BET , BALANCE , shoeSize , wordsShoeFile);
		for(int i = 0; i<wordsCommandFile.length; i++) {
			String inputCommand = wordsCommandFile[i];
			if((i+1)!=wordsCommandFile.length) {
					if(Character.isDigit(wordsCommandFile[i+1].charAt(0))) {
						int number = Integer.parseInt(wordsCommandFile[i+1]);
						if (number >= MIN_BET) {
							wordsCommandFile[i] = wordsCommandFile[i] + " " + wordsCommandFile[i+1];
							inputCommand = wordsCommandFile[i];
							i = i + 1;
						}
					}
			}
			System.out.println("-cmd" + " " + inputCommand);
						
			// User inputs bet command
			if(inputCommand.startsWith("b")) {
				// Process bet command
				// Validate bet command
				if(inputCommand.length() == 1) {
					System.out.println("player is betting " + betSize);
					gameVar.bet(betSize);
				}
			    else if(Pattern.matches("[b]\\s\\d+",inputCommand)) {
                    betSizeInput = Integer.parseInt(inputCommand.replaceAll("[\\D]", ""));
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
			    	System.out.println(inputCommand + ": illegal command");
			    }
				
			}
						
			// User inputs balance command
			else if (inputCommand.equals("$")) {
				gameVar.balance();
			}
			
			// User inputs deal command
			else if (inputCommand.equals("d")) {
				gameVar.deal();
			}
			
			// User inputs hit command
			else if (inputCommand.equals("h")) {
				gameVar.hit(gameVar.player.getHandIndex());
			}
			
			// User inputs stand command
			else if (inputCommand.equals("s")) {
				gameVar.stand(gameVar.player.getHandIndex());
			}
			
			// User inputs insurance command
			else if (inputCommand.equals("i")) {
				gameVar.insurance();
			}
			
			// User inputs surrender command
			else if (inputCommand.equals("u")) {
				gameVar.surrender();
			}
			
			// User inputs split command
			else if (inputCommand.equals("p")) {
				gameVar.splitting(gameVar.player.getHandIndex());
			}
			
			// User inputs doubledown command
			else if (inputCommand.equals("2")) {
				gameVar.doubledown(0);
			}
			
			// User inputs advice command
			else if (inputCommand.equals("ad")) {
				gameVar.advice(gameVar.player.getHandIndex());
			}
			
			// User inputs statistics command
			else if (inputCommand.equals("st")) {
				gameVar.statistics(BALANCE);
			}
			
			// User inputs quit command
			else if (inputCommand.equals("q")) {
				System.out.println("bye"); // close scanner
				return;
			}
			
			else {
				System.out.println(inputCommand + ": illegal command");
				
			}
		}	
	}
	
	/**
	 * Method that receives a String with a given file name and returns the contents of the file on a String
	 * @return String with the contents of the file with the inserted name
	 */
	public static String fileToString(String fileName) {
        try {
        	BufferedReader my_Reader = new BufferedReader(new FileReader(new File(
        			fileName)));
        	String line = "";
        	StringBuilder str = new StringBuilder();
        	String stringReturn;
        	while((line = my_Reader.readLine()) != null)
        	{
        		str.append(line);
        	}
        	stringReturn = str.toString();
	        my_Reader.close();
	        return stringReturn;
	    	} catch (FileNotFoundException e) {
	        	System.out.println("File not exists or insufficient rights");
	        	e.printStackTrace();
	        	return null;
	    	} catch (IOException e) {
	        	System.out.println("An exception occured while reading the file");
	        	e.printStackTrace();
	        	return null;
	    }	
	}
}


