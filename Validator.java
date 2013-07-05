import java.util.Arrays;
import java.util.List;


public class Validator {

	//valid 4 arg commands
	private final static List<String> FourArgsAcceptable = Arrays.asList(new String[]{"CREATE", "UPDATE"});
	
	//valid 3 arg commands
	private final static List<String> ThreeArgsAcceptable = Arrays.asList(new String[]{"GET" , "DELETE"});
	
	//valid 2 arg commands
	private final static List<String> TwoArgsAcceptable = Arrays.asList(new String[]{"LATEST" , "DELETE"});

	private static final int ARG1 = 1;

	private static final int ARG2 = 2;

	private static final int ARG3 = 3;
	
	private static final int CMDINDX = 0;
	
	public static Result validateArgLength(String[] parsed) {
		
		String command = parsed[CMDINDX];
		
		if (parsed.length == 4 && !FourArgsAcceptable.contains(command)){
			return invalidLengthError(4,parsed.length);
		}
		else if(parsed.length == 3 && !ThreeArgsAcceptable.contains(command)){
			return invalidLengthError(3,parsed.length);
		}
		else if(parsed.length == 2 && !TwoArgsAcceptable.contains(command)){
			return invalidLengthError(2, parsed.length);
		}
		return null;
	}

	private static Result invalidLengthError(int expectedArgs, int parsedArgs) {
		return Result.createError(Errors.TOO_MANY_PARAMS, "Expected Args : "+expectedArgs + " Received Args: " + parsedArgs);
	}
	
}
