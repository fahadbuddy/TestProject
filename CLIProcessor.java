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
			System.out.println(result);
		}
		
		System.out.println("\nTERMINATED...");
	}
	
	protected Result parseInsruction(String nextInstruction) {
		
		String[] parsed = nextInstruction.split("\\s+");
		
		if (parsed.length > 4){
			return Result.createError(Errors.TOO_MANY_PARAMS, nextInstruction);
		}
		
		if(allowableCommands.contains(parsed[CMDINDX])){
			//further processing
			if ( "GET".equals(parsed[CMDINDX]) || "LATEST".equals(parsed[CMDINDX])){
				return processGetLatest(parsed);
			}
			
			if("CREATE".equals(parsed[CMDINDX]) || "UPDATE".equals(parsed[CMDINDX])){
				return processCreateUpdate(parsed);
			}
			
			if("DELETE".equals(parsed[CMDINDX])){
				return processDelete(parsed);
			}
			if("QUIT".equals(parsed[CMDINDX])){
				return Result.createError(Errors.TERMINATION, "");
			}
			
			System.out.println(parsed);
		}
		
		return Result.createError(Errors.INVALID_COMMAND, parsed[CMDINDX]);
	}

	private Result processDelete(String[] parsed) {

		Result validateResult = Validator.validateArgTypes(parsed);
		if(validateResult != null){
			return validateResult;
		}
		
		if(parsed.length == 2){
			return store.deleteEntry(Integer.parseInt(parsed[ARG1INDX]));
		}
		
		if(parsed.length == 3){
			return store.deleteEntry(Integer.parseInt(parsed[ARG1INDX]), Integer.parseInt(parsed[ARG2INDX]));
		}
		
		return validateResult;
	}

	private Result processCreateUpdate(String[] parsed) {
		Result validateResult = Validator.validateArgTypes(parsed);
		if(validateResult != null){
			return validateResult;
		}
		
		if("CREATE".equals(parsed[CMDINDX])){
			return store.createEntry(Integer.parseInt(parsed[ARG1INDX]), Integer.parseInt(parsed[ARG2INDX]), parsed[ARG3INDX]);
		}
		
		if("UPDATE".equals(parsed[CMDINDX])){
			return store.updateEntry(Integer.parseInt(parsed[ARG1INDX]), Integer.parseInt(parsed[ARG2INDX]), parsed[ARG3INDX]);
		}
		
		return validateResult;
	}



	private Result processGetLatest(String[] parsed) {
		
		Result validateResult = Validator.validateArgTypes(parsed);
		if(validateResult != null){
			return validateResult;
		}
		
		if("LATEST".equals(parsed[CMDINDX])){
			return store.getLatestData(Integer.parseInt(parsed[ARG1INDX]));
		}
		
		if("GET".equals(parsed[CMDINDX])){
			return store.getData(Integer.parseInt(parsed[ARG1INDX]), Integer.parseInt(parsed[ARG2INDX]));
		}
		
		return validateResult;
		
	}

	
}
