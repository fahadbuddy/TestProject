import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;


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
			history.put(timestamp, data);
			workingMemory.put(id, history);
			return new Result(true, data, null);
		
		
	}
	
	public Result updateEntry(Integer id, Integer timestamp, String data){
		if(workingMemory.get(id) != null){
			SortedMap<Integer, String> history = workingMemory.get(id);
			String previousData = history.get(timestamp) == null ? getLatestData(id).data : history.get(timestamp);
			
			history.put(timestamp, data);
			return new Result(true, previousData, null);
		}
		
		return Result.createError(Errors.ID_NOT_FOUND, " '"+id+"'");
	
	}
	
	public Result getData(Integer id, Integer timestamp){
			
		//Note in the example interaction, it looks like we return the data witht 
		//the greatest timestamp available, 
		//But the spec says that should return an error if timestamp is not found.
		//What is the expected behaviour??
		
			SortedMap<Integer, String> history = workingMemory.get(id);
			
			if (history == null){
				return Result.createError(Errors.ID_NOT_FOUND, " '"+id+"'");
			}
			
			Integer timestampToFetch = 0;
			
			//if exists in history, return that value.
			if (history.containsKey(timestamp)){
				timestampToFetch = timestamp;
			}
			//else get the latest timestamp, or the one in range.
			else{
				int lastTimeStamp = getLastTimeStamp(id);
				timestampToFetch = timestamp >= lastTimeStamp ? lastTimeStamp : getTimeStampIdsGreaterThanCurrent(history, timestamp).first(); 
			}
		
			String data = workingMemory.get(id).get(timestampToFetch);	
	
			return new Result(true, data, null);	
		}

	
	private SortedSet<Integer> getTimeStampIdsGreaterThanCurrent(
			SortedMap<Integer, String> history, Integer id) {
		SortedSet<Integer> greaterIds = new TreeSet<Integer>();
		
		for(Integer currId: history.keySet()){
			if(currId >= id){				
				greaterIds.add(currId);
			}
		}
		return greaterIds;
	}



	public Result getLatestData(Integer id){
		if (workingMemory.get(id)!= null){
			String data = getData(id, getLastTimeStamp(id)).data;
			if(data != null){
				return new Result(true, getLastTimeStamp(id) + " " + data, null);
			}
		}
		
		return Result.createError(Errors.ID_NOT_FOUND , " '"+id+"'" );
	}



	private Integer getLastTimeStamp(Integer id) {
		return workingMemory.get(id).lastKey();
	}
	
	public Result deleteEntry(Integer id){
		if(workingMemory.get(id) != null){
			String previousData = getLatestData(id).data;
			workingMemory.remove(id);
			return new Result(true, previousData, null);
		}
		
		return Result.createError(Errors.ID_NOT_FOUND, " '"+id+"'");
		
	}
	
	public Result deleteEntry(Integer id, Integer timestamp){
		//if Id / timestamp not available return error
		Result getResult = getData(id,timestamp);
		if(getResult.data == null){
			return getResult;
		}
		
		SortedMap<Integer, String>  history = workingMemory.get(id);
		
			for ( Integer removeId: getTimeStampIdsGreaterThanCurrent(history, timestamp)){
				history.remove(removeId);
			}
		
		return new Result(true, getResult.data, null);
	}
}
