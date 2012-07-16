package mobidev.geowall;

import java.sql.Timestamp;
import java.util.Date;

public class Message {

	protected int id;
	protected String text;
	protected String img;
	protected String video;
	protected String nick;
	protected String social;
	protected String date;
	
	public Message(int id, String nick, String text, String img, String video,
			String social) {
		this.id = id;
		this.nick = nick;
		this.text = text;
		this.img = img;
		this.video = video;
		this.social = social;
		Timestamp dateSql=new Timestamp(new Date().getTime()); 
		date=dateSql.toString();
	}

	public Message(String nick, String text, String img, String video,
			String social) {
		this.nick = nick;
		this.text = text;
		this.img = img;
		this.video = video;
		this.social = social;
		Timestamp dateSql=new Timestamp(new Date().getTime()); 
		date=dateSql.toString();
	}
	
	public Message(int id, String nick, String text, String img, String video,
			String social, String date) {
		this.id = id;
		this.nick = nick;
		this.text = text;
		this.img = img;
		this.video = video;
		this.social = social;
		this.date=date;
	}
	
	public Message(String nick, String text, String img, String video,
			String social, String date) {
		this.nick = nick;
		this.text = text;
		this.img = img;
		this.video = video;
		this.social = social;
		this.date=date;
	}

	public int getid() {
		return id;
	}

	public String getnick() {
		return nick;
	}

	public String gettext() {
		return text;
	}

	public String getimg() {
		return img;
	}

	public String getvideo() {
		return video;
	}

	public String getsocial() {
		return social;
	}
	
	public String getdate(){
		return date;
	}
}
