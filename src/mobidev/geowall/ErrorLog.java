package mobidev.geowall;

public class ErrorLog {

	private static String errorMessage="";
	
	public static void put(String message){
		errorMessage=message;
	}
	public static String get(){
		String temp=errorMessage;
		errorMessage="";
		return temp;
	}
	public static boolean empty(){
		if(errorMessage=="")
			return true;
		return false;
	}
}
