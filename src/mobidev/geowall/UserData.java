package mobidev.geowall;




public class UserData  {

	private String email;
	private String nick;
	private String hash;
	private String img;
	private String country;
	private String city;
	private String date;
	
	
	public UserData(String nick, String email,  String hash){
		super();		
		this.email=email;
		this.nick=nick;
		this.hash=hash;
		
	}
	
	public UserData(String nick,String email, String hash, String img, String date,String country, String city){
		super();
		this.email=email;
		this.nick=nick;
		this.img=img;
		this.country=country;
		this.city=city;
		this.hash=hash;
		this.date=date;
	}
	
	
	
	public String getemail(){
		return email;
	}
	public String getnick(){
		return nick;
	}
	public String getimage(){
		return img;
	}
	public String getcountry(){
		return country;
	}
	public String getcity(){
		return city;
	}
	public String getpassword(){
		return hash;
	}
	public String getdate(){
		return date;
	}
}
