package mobidev.geowall;

public class CheckPassword implements UtilityCheck {

	private String pw;
	public CheckPassword(String pw){
		this.pw=pw;
	}
	@Override
	public boolean check() {
		if(pw=="" || pw.length()>10)
			return false;
		return true;
	}
	

}
