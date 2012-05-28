package mobidev.geowall;

import java.util.Calendar;

public class CheckDateIsNotCurrent implements UtilityCheck {

	int mYear=0;
	int mMonth=0;
	int mDay=0;
	
	public CheckDateIsNotCurrent(int y,int m,int d){
		mYear=y;
		mMonth=m;
		mDay=d;
		
	}

	@Override
	public boolean check() {
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);
		
		if(mYear==year && mMonth==month && mDay==day)
			return false;
		else 
			return true;
	}
	
	
	
}
