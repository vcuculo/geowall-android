package mobidev.geowall;

import org.w3c.dom.UserDataHandler;

public class Message {

	
	protected String text;
	protected String img;
	protected String video;
	protected String nick;
	protected NoticeBoard nb;
	
	public Message (String nick,NoticeBoard nb,String text,String img,String video){
		
		this.nick=nick;
		this.nb=nb;
		this.text=text;
		this.img=img;
		this.video=video;
		
	}
	
	
	public String getnick(){
		return nick;
	}
	
	public NoticeBoard getnb(){
		return nb;
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
