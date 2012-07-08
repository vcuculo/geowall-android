package mobidev.geowall;

public class RequestNoticeBoard {

	int px,py;
	String date;
	
	public RequestNoticeBoard(int px,int py,String date){
		this.px=px;
		this.py=py;
		this.date=date;
	}
	
	public int getPx(){
		return px;
	}
	public int getPy(){
		return py;
	}
	public String getDate(){
		return date;
	}
}
