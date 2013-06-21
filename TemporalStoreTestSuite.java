
public class TemporalStoreTestSuite {

	private static final String TEST_DATA = "TESTDATA";
	private static final String UPDATED_DATA = "UPDATEDDATA";
	//Class under test
	static TemporalStore store;
	
	public static void initialise(){
		store = new TemporalStore();
	}
	
	public static void testCreateEntry(){
		initialise();
		Result expectedResult = new Result(true,TEST_DATA,null);
		Result actualResult = store.createEntry(1, 101, TEST_DATA);
		assert(expectedResult.toString().equals(actualResult.toString()));
		System.out.println(actualResult);
	}
	
	public static void testCreateEntryError(){
		testCreateEntry();
		Result actualResult = store.createEntry(1, 101, TEST_DATA);
		System.out.println(actualResult);
	}
	
	public static void testSuccessfulUpdate(){
		initialise();
		testCreateEntry();
		Result actualResult = store.updateEntry(1, 104, UPDATED_DATA);
		System.out.println(actualResult);
		
	}
	
	public static void testUpdateError(){
		testCreateEntry();
		Result actualResult = store.updateEntry(2, 104, UPDATED_DATA);
		System.out.println(actualResult);
	}
	
	public static void testParsedInsructions(){
		String TEST_COMMAND = "GET";
		
		String[] split = TEST_COMMAND.split("\t");
		
		for(String s: split){			
			System.out.println(s);
		}
		
	}
	
	public static void main(String[] args){
		//testCreateEntry();
		//testSuccessfulUpdate();
		//testCreateEntryError();
		//testUpdateError();
		testParsedInsructions();
	}
	
}
