package mobidev.geowall;

import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

public class SignUpController extends AsyncTask<Context, Context, String> {

	String nick, email, pw, img, date, country, city, session;
	private String USER_PREFERENCES = "UserPreference";
	boolean error = false;
	private String ERROR = "Account alredy exist";
	private String ERRORCOM = "Impossible connect to server";
	public final int ERROR_DIALOG_ID = 0;
	public final int ERROR_COMMUNICATION = 1;
	ProgressDialog dialog;
	Context contextglobal;
	public SignUpController() {
		super();
	}

	protected String doInBackground(Context... c) {
		contextglobal=c[0];
		SharedPreferences sharePreferences =c[0].getSharedPreferences(USER_PREFERENCES, 0);
		UserData user;
		CommunicationController cc= new CommunicationController();
		nick = sharePreferences.getString("NICK", null);
		email = sharePreferences.getString("EMAIL", null);
		pw = sharePreferences.getString("PASS", null);
		img = sharePreferences.getString("IMG", null);
		date = sharePreferences.getString("DATE", null);
		country = sharePreferences.getString("COUNTRY", null);
		city = sharePreferences.getString("CITY", null);
		user = new UserData(nick, email, pw, img, date, country, city);
		session = null;
		try {
			publishProgress(c[0]);
			String tempSession = cc.sendRequest("register",	DataController.marshallUser(user));
			session = DataController.unMarshallSession(tempSession);
			
			if (session != null) {
				SharedPreferences.Editor se = sharePreferences.edit();
				se.putString("SESSION", session);
				se.commit();
				Intent i = new Intent(c[0], GeoMapActivity.class);
				c[0].startActivity(i);
				Log.i("Session",sharePreferences.getString("SESSION", null));
			}else{
				error=true;
				session=tempSession;
			}
		} catch (IOException e) {
			Log.e("Errore login", e.getLocalizedMessage());
			error = false;
		}
	return null;
	}

	protected void onProgressUpdate(Context... c) {
			dialog = ProgressDialog.show(c[0], "", "Loading. Please wait...",
					true);
		
	}

	protected void onPostExecute(String session) {
    	if(error==true){
        		AlertDialog.Builder alertDialog= new AlertDialog.Builder(contextglobal);
        		alertDialog.setMessage(session)
        		.setPositiveButton("Yes",
    					new DialogInterface.OnClickListener() {
    						public void onClick(DialogInterface dialog, int id) {
    							dialog.cancel();
    						}
    					});
        		alertDialog.create().show();
        	}
    
		dialog.dismiss();
    	dialog=null;
            return;
	}


}
