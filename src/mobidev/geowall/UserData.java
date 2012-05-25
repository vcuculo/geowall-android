package mobidev.geowall;


import android.graphics.Bitmap;

public class UserData  {

	private String email;
	private String nick;
	private String hash;
	private String img;
	private String country;
	private String city;
	
	
	public UserData( String email, String nick, String hash){
		super();		
		this.email=email;
		this.nick=nick;
		this.hash=hash;
		
	}
	
	public UserData(String email, String nick, String hash, String img, String country, String city){
		super();
		this.email=email;
		this.nick=nick;
		this.img=img;
		this.country=country;
		this.city=city;
		this.hash=hash;
	}
	
	
	
	public String getemail(){
		return email;
	}
	public String getnick(){
		return nick;
	}
	public String getbitmap(){
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
}
