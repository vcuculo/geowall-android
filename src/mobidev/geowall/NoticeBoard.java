package mobidev.geowall;

import java.util.ArrayList;

public class NoticeBoard {

	protected int positionX;
	protected int positionY;
	protected String date;
	protected ArrayList<Message> m;
	public NoticeBoard(int positionX,int positionY,String date, ArrayList<Message> m){
		this.positionX=positionX;
		this.positionY=positionY;
		this.date=date;
		this.m=m;
	}
	
	public int getPositionY(){
		return positionY;
	}
	
	public int getPositionX(){
		return positionX;
	}
	public String getDate(){
		return date;
	}
	
	public ArrayList<Message> getMessages(){
		return m;
	}
	
}
