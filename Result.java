	public class Result{
		final boolean status;
		final String data;
		final String error;
		
		Result(boolean status, String data, String error){
			this.status = status;
			this.data = data;
			this.error = error;
		}
		@Override
		public String toString() {
			return status ? "OK " + data : "ERR " + error;
		}
		
		
	}