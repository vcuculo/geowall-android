package mobidev.geowall;

import org.w3c.dom.UserDataHandler;

public class Message {

	
	protected int id;
	protected String text;
	protected String img;
	protected String video;
	protected String nick;

	public Message (int id,String nick,String text,String img,String video){
		this.id=id;
		this.nick=nick;
		this.text=text;
		this.img=img;
		this.video=video;
		
	}
	
	
	public int getid(){
		return id;
	}
	
	public String getnick(){
		return nick;
	}
	
	
	public String gettext(){
		return text;
	}
	
	public String getimg(){
		return img;
	}
	
	public String getvideo(){
		return video;
	}
}
