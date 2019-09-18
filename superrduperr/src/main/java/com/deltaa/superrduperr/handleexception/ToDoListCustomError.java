package com.deltaa.superrduperr.handleexception;

public class ToDoListCustomError {
	
	
		int errorcode;
		String message;
		double timestamp;
		
		public  ToDoListCustomError () {
			
		}
	
		public int getErrorcode() {
			return errorcode;
		}
	
		public void setErrorcode(int errorcode) {
			this.errorcode = errorcode;
		}
	
		public String getMessage() {
			return message;
		}
	
		public void setMessage(String message) {
			this.message = message;
		}
	
		public double getTimestamp() {
			return timestamp;
		}
	
		public void setTimestamp(double timestamp) {
			this.timestamp = timestamp;
		}

}
