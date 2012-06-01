package mobidev.geowall;

import android.os.AsyncTask;

public class SignUpController extends AsyncTask<UserData, Void, Void> {

	@Override
	protected Void doInBackground(UserData... params) {
		String jsonRequest= DataController.marshallUser(params[0]);
		
		return null;
	}

	
}
