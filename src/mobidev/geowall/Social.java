package mobidev.geowall;

public class Social {

	protected String nick;
	protected String type;
	protected String token;
	
	public Social(String nick,String type,String token){
		this.nick=nick;
		this.type=type;
		this.token=token;
	}
	
	public String getnick(){
		return nick;
	}
	
	public String gettype(){
		return type;
	}
	
	public String gettoken(){
		return token;
	}
	
}
