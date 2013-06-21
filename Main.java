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

	public static Result parseInsruction(String nextInstruction) {
		
		String[] parsed = nextInstruction.split("\t");
		
		if (parsed.length > 3){
			return Result.createError(Errors.TOO_MANY_PARAMS, nextInstruction);
		}
		
		if(allowableCommands.contains(parsed[CMDINDX])){
			//further processing
			if (parsed[CMDINDX].equals("GET") || parsed[CMDINDX].equals("LATEST")){
				return processGetLatest(parsed);
			}
			
			if(parsed[CMDINDX].equals("CREATE") || parsed[CMDINDX].equals("UPDATE")){
				return processCreateUpdate(parsed);
			}
			
			if(parsed[CMDINDX].equals("DELETE")){
				return processDelete(parsed);
			}
			
			System.out.println(parsed);
		}
		
		return new Result(false, null, Errors.INVALID_COMMAND + " " + parsed[CMDINDX]);
	}

	private static Result processDelete(String[] parsed) {
		// TODO Auto-generated method stub
		return null;
	}

	private static Result processCreateUpdate(String[] parsed) {
		// TODO Auto-generated method stub
		return null;
	}

	private static Result processGetLatest(String[] parsed) {
		// TODO Auto-generated method stub
		return null;
	}

}
