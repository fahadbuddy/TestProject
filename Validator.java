import java.text.ParseException;
import java.util.Arrays;
import java.util.List;


public class Validator {

	//valid 4 arg commands
	private final static List<String> FourArgsAcceptable = Arrays.asList(new String[]{"CREATE", "UPDATE"});
	
	//valid 3 arg commands
	private final static List<String> ThreeArgsAcceptable = Arrays.asList(new String[]{"GET" , "DELETE"});
	
	//valid 2 arg commands
	private final static List<String> TwoArgsAcceptable = Arrays.asList(new String[]{"LATEST" , "DELETE"});
	
	private static final int CMDINDX = 0;

	private static final int ARG1INDX = 1;

	private static final int ARG2INDX = 2;
	
	public static Result validateArgLength(String[] parsed) {
		
		String command = parsed[CMDINDX];
		
		if (parsed.length == 4 && !FourArgsAcceptable.contains(command)){
			return invalidLengthError();
		}
		else if(parsed.length == 3 && !ThreeArgsAcceptable.contains(command)){
			return invalidLengthError();
		}
		else if(parsed.length == 2 && !TwoArgsAcceptable.contains(command)){
			return invalidLengthError();
		}
		else if(parsed.length == 1){
			return invalidLengthError();
		}
		return null;
	}

	private static Result invalidLengthError() {
		return Result.createError(Errors.INSUFFICIENT_PARAMS, "");
	}
	
	public static Result validateArgTypes(String[] parsed){
		Result validateResult = validateArgLength(parsed);
		if (validateResult != null){
			//error verifying no. of args
			return validateResult;
		}
		
		
		boolean isValidArg = true;
		
		if(parsed.length >= 2 && isValidArg){
			isValidArg = verifyInteger(parsed[ARG1INDX]);
		}
		
		if(parsed.length >= 3 && isValidArg){
			isValidArg = verifyInteger(parsed[ARG2INDX]);
		}
		
		//failed validation
		if(!isValidArg){
			return Result.createError(Errors.NOT_A_VALID_INTEGER, "");
		}
		
		//successful validation
		return null;
	}

	private static boolean verifyInteger(String string) {
		try{
			Integer.parseInt(string);
			return true;
		}catch(Exception e){
			return false;
		}
	}
	
}
