package mobidev.geowall;

public class NoticeBoard {

	protected int positionX;
	protected int positionY;
	protected String date;
	protected String hour;
	public NoticeBoard(int positionX,int positionY,String date, String hour){
		this.positionX=positionX;
		this.positionY=positionY;
		this.date=date;
		this.hour=hour;
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
	
	public String getHour(){
		return hour;
	}
	
}
