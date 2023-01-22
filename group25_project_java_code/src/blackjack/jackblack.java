package blackjack;

/**
 *  This class is used as the main class for the blackjack application
 */
public class jackblack {
	public static void main(String[] args) {
		
		
		GameMode gameMode = null;
		if(args[0].equals("-i")) {
			if(args.length != 6) {
				System.out.println("Invalid number of parameters"); 
				return;
			}
			else {
				gameMode = new InterfaceMode();
				gameMode.gameFunction(args);
				return;
			}
		}
		
		else if(args[0].equals("-s")) {
			if(args.length != 8) {
				System.out.println("Invalid number of parameters"); 
				return;
			}
			else {
				gameMode = new SimulationMode();
				gameMode.gameFunction(args);
				return;
			}
		}
		
		else if(args[0].equals("-d")) {
			if(args.length != 6) {
				System.out.println("Invalid number of parameters"); 
				return;
			}
			else {
				gameMode = new DebugMode();
				gameMode.gameFunction(args);
				return;
			}
		}

		
	}
}
