import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;


public class TemporalStore {

	
	//In memory data store
	// id --> (timestamp --> data)*
	Map<Integer, SortedMap<Integer, String>> workingMemory;
	
	public TemporalStore(){
		//initialise our in memory data store
		workingMemory = new HashMap<Integer, SortedMap<Integer, String>>();
	}


	
	public Result createEntry(Integer id, Integer timestamp, String data){
		
		if(workingMemory.get(id) != null){
			return Result.createError(Errors.HISTORY_EXISTS_ERROR, " '"+id+"'");
			
		}
			SortedMap<Integer, String> history = new TreeMap<Integer, String>();
			history.put(id, data);
			workingMemory.put(id, history);
			return new Result(true, data, null);
		
		
	}
	
	public Result updateEntry(Integer id, Integer timestamp, String data){
		if(workingMemory.get(id) != null){
			SortedMap<Integer, String> history = workingMemory.get(id);
			String previousData = history.get(id);
			history.put(id, data);
			return new Result(true, previousData, null);
		}
		
		return Result.createError(Errors.ID_NOT_FOUND, " '"+id+"'");
	
	}
	
	public Result getData(Integer id, Integer timestamp){
		String data = workingMemory.get(id).get(timestamp);
		if(data == null){
			return Result.createError(Errors.ID_NOT_FOUND, " '"+id+"'");
		}	
			return new Result(true, data, null);
	}
	
	public Result getLatestData(Integer id){
		if (workingMemory.get(id)!= null){
			return getData(id, workingMemory.get(id).lastKey());
		}
		
		return Result.createError(Errors.ID_NOT_FOUND , " '"+id+"'" );
	}
	
	public Result deleteEntry(Integer id){
		return null;
	}
	
	public Result deleteEntry(Integer id, Integer timestamp){
		return null;
	}
}
