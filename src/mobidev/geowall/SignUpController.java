package mobidev.geowall;

import java.io.IOException;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

public class SignUpController extends AsyncTask<Context,Context,String> {

	String nick,email,pw,img,date,country,city,session;
	private String USER_PREFERENCES = "UserPreference";
	boolean error=false;
	private String ERROR = "Account alredy exist";
	private String ERRORCOM="Impossible connect to server";
	public final int ERROR_DIALOG_ID = 0;
	public final int ERROR_COMMUNICATION = 1;
	public SignUpController(){
		super();
	}
	

    protected String doInBackground(Context... context){
    	CommunicationController cc=new CommunicationController();
    	SharedPreferences sharePreferences=context[0].getSharedPreferences(USER_PREFERENCES, 0);
    	publishProgress(context[0]);
    	UserData user;
    		nick=sharePreferences.getString("NICK", null);
    		email=sharePreferences.getString("EMAIL", null);
    		pw=sharePreferences.getString("PASS", null);
    		img=sharePreferences.getString("IMG", null);
    		date=sharePreferences.getString("DATE", null);
    		country=sharePreferences.getString("COUNTRY", null);
    		city=sharePreferences.getString("CITY", null);
    	user=new UserData(nick, email, pw, img, date, country, city);
		session=null;
		try{
			String tempSession=cc.sendRequest("register", DataController.marshallUser(user));
			session= DataController.unMarshallSession(tempSession);
			if(session!=null){
				SharedPreferences.Editor se=sharePreferences.edit();
				se.putString("SESSION", session);
				se.commit();
			}else{
				ErrorLog.put("Account alredy exist");
			}
		}catch(IOException e){
			ErrorLog.put("Error to comunicate with server");
			Log.e("Errore login", e.getMessage());
			error=true;
		}
		return session;
    }

    protected void onProgressUpdate(Context... c) {
    	if(error==false){
    	ProgressDialog dialog = ProgressDialog.show(c[0], "", 
                "Loading. Please wait...", true);
    	}
    }
    
    protected void onPostExecute(String session) {
        return;
    }
    
 
    

}
