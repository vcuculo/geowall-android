package mobidev.geowall;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public  class CheckEmail implements UtilityCheck {

	private String email;
	public CheckEmail(String email){
		this.email=email;
		
	}
	@Override
	public boolean check() {
		
		Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
		Matcher m = p.matcher(email);
		boolean matchFound = m.matches();

		if(matchFound)
			return true;
		
		return false;
	

	
}
}
