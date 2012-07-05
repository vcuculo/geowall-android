package mobidev.geowall;

import java.io.IOException;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

public class SignUpController extends AsyncTask<Context,Context,String> {

	public SignUpController(){
		super();
	}
	
	String nick,email,pw,img,date,country,city;
	private String USER_PREFERENCES = "UserPreferncer";
    protected String doInBackground(Context... context) {
    	CommunicationController cc=new CommunicationController();
    	SharedPreferences sharePreferences=context[0].getSharedPreferences(USER_PREFERENCES, 0);
    	UserData user;
    		nick=sharePreferences.getString("NICK", null);
    		email=sharePreferences.getString("EMAIL", null);
    		pw=sharePreferences.getString("PASS", null);
    		img=sharePreferences.getString("IMG", null);
    		date=sharePreferences.getString("DATE", null);
    		country=sharePreferences.getString("COUNTRY", null);
    		city=sharePreferences.getString("CITY", null);
    	user=new UserData(nick, email, pw, img, date, country, city);
		String session=null;
		try{
			publishProgress(context[0]);
			session=cc.sendRequest("register", DataController.marshallUser(user));
			SharedPreferences.Editor se=sharePreferences.edit();
			se.putString("SESSION", session);
			se.commit();
		}catch(IOException e){
			Log.e("Errore login", e.getMessage());
		}
		return session;
    }

    protected void onProgressUpdate(Context... c) {
    	ProgressDialog dialog = ProgressDialog.show(c[0], "", 
                "Loading. Please wait...", true);
    }
    
    protected void onPostExecute(String session) {
        return;
    }
}
