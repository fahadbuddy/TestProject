import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;


public class Main {

	private static final int CMDINDX = 0;
	/**
	 * @param args
	 */
	public static List<String> allowableCommands = new ArrayList<String>(Arrays.asList("CREATE", "UPDATE", "GET", "LATEST", 
		"DELETE", "QUIT"));
	
	public static void main(String[] args) {
		TemporalStore store = new TemporalStore();		
		Scanner sc = new Scanner(System.in);
		System.out.println("READY...");
		String nextInstruction = "";
		
		while(!nextInstruction.equals("QUIT")){
			nextInstruction = sc.nextLine();
			Result result = parseInsruction(nextInstruction);
			System.out.println(">> " + result);
		}
		
		System.out.println("TERMINATED...");
	}

	private static Result parseInsruction(String nextInstruction) {
		String[] parsed = nextInstruction.split("\\S+");
		
		if(allowableCommands.contains(parsed[CMDINDX])){
			//further processing
			System.out.println(parsed);
		}
		
		return new Result(false, null, Errors.INVALID_COMMAND + " " + parsed[CMDINDX]);
	}

}
