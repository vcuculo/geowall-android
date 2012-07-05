package mobidev.geowall;

public class LoginData {

	//email | nick
	
	private String email; 
	private String nick;
	private String hash;

	
	
	public LoginData(String nick, String email,  String hash){
		super();		
		this.email=email;
		this.nick=nick;
		this.hash=hash;
		
	}

	public String getemail(){
		return email;
	}
	public String getnick(){
		return nick;
	}

	public String getpassword(){
		return hash;
	}

	
}
