import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class CLIProcessor {

	public static List<String> allowableCommands = new ArrayList<String>(Arrays.asList("CREATE", "UPDATE", "GET", "LATEST", 
			"DELETE", "QUIT"));
	
	private static final int CMDINDX = 0;
	private static final int ARG1INDX = 1;
	private static final int ARG2INDX = 2;
	private static final int ARG3INDX = 3;
	
	private TemporalStore store;
	
	public void run(){		
		store = new TemporalStore();		
		Scanner sc = new Scanner(System.in);
		System.out.println("READY...");
		String nextInstruction = "";
		
		//Main Instruction Loop
		while(!nextInstruction.equals("QUIT")){
			nextInstruction = sc.nextLine();
			Result result = parseInsruction(nextInstruction);
			System.out.println(">> " + result);
		}
		
		System.out.println("\nTERMINATED...");
	}
	
	protected Result parseInsruction(String nextInstruction) {
		
		String[] parsed = nextInstruction.split("\\s");
		
		if (parsed.length > 4){
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
		
		return Result.createError(Errors.INVALID_COMMAND, parsed[CMDINDX]);
	}

	private Result processDelete(String[] parsed) {
		// TODO Auto-generated method stub
		return null;
	}

	private Result processCreateUpdate(String[] parsed) {
		Result createResult = Validator.validateArgLength(parsed);
		if(createResult != null){
			return createResult;
		}
		
		if("CREATE".equals(parsed[CMDINDX])){
			return store.createEntry(Integer.parseInt(parsed[ARG1INDX]), Integer.parseInt(parsed[ARG2INDX]), parsed[ARG3INDX]);
		}
		
		if("UPDATE".equals(parsed[CMDINDX])){
			return store.updateEntry(Integer.parseInt(parsed[ARG1INDX]), Integer.parseInt(parsed[ARG2INDX]), parsed[ARG3INDX]);
		}
		
		return createResult;
	}



	private Result processGetLatest(String[] parsed) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
