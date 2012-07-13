package mobidev.geowall;

import java.util.Date;

import org.w3c.dom.UserDataHandler;

public class Message {

	protected int id;
	protected String text;
	protected String img;
	protected String video;
	protected String nick;
	protected String social;

	public Message(int id, String nick, String text, String img, String video,
			String social) {
		this.id = id;
		this.nick = nick;
		this.text = text;
		this.img = img;
		this.video = video;
		this.social = social;

	}

	public Message(String nick, String text, String img, String video,
			String social) {
		this.nick = nick;
		this.text = text;
		this.img = img;
		this.video = video;
		this.social = social;

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

}
