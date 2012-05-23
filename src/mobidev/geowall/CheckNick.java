package mobidev.geowall;

public class CheckNick implements UtilityCheck {

	private String nick;
	public CheckNick(String nick){
		this.nick=nick;
	}
	@Override
	public boolean check() {
		if(nick=="")
			return false;
		return true;
	}

}
