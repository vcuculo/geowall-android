package mobidev.geowall;

import java.io.IOException;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

public class LoginController extends AsyncTask<Context, Context, String> {
	String nick,email,pw,session;
	private String USER_PREFERENCES = "UserPreference";
	boolean error=false;
	private String ERROR = "Account error";
	private String ERRORCOM="Impossible connect to server";
	public final int ERROR_DIALOG_ID = 0;
	public final int ERROR_COMMUNICATION = 1;
	
	public LoginController(){
		super();
	}

    protected String doInBackground(Context... context){
    	CommunicationController cc=new CommunicationController();
    	SharedPreferences sharePreferences=context[0].getSharedPreferences(USER_PREFERENCES, 0);
    	publishProgress(context[0]);
    	LoginData user;
    		nick=sharePreferences.getString("NICK", null);
    		email=sharePreferences.getString("EMAIL", null);
    		pw=sharePreferences.getString("PASS", null);
    	
    	user=new LoginData(nick, email, pw);
		session=null;
		try{
			String tempSession=cc.sendRequest("login", DataController.marshallLogin(user));
			session= DataController.unMarshallSession(tempSession);
			if(session!=null){
				SharedPreferences.Editor se=sharePreferences.edit();
				se.putString("SESSION", session);
				se.commit();
			}else{
				onCreateDialog(ERROR_DIALOG_ID, context[0]);
			}
		}catch(IOException e){
			Log.e("Errore login", e.getMessage());
			error=true;
		}
		return session;
    }

    protected void onProgressUpdate(Context... c) {
    	if(error=false){
    	ProgressDialog dialog = ProgressDialog.show(c[0], "", 
                "Loading. Please wait...", true);
    	}else{
    		onCreateDialog(ERROR_COMMUNICATION, c[0]);
    	}
    }
    
    protected void onPostExecute(String session) {
        return;
    }
    
    protected void onCreateDialog(int id, Context c) {
		AlertDialog alert = null;
		switch (id) {
		case ERROR_DIALOG_ID:

			createAlert(ERROR,c).show();
			break;
		case ERROR_COMMUNICATION:
			createAlert(ERRORCOM,c).show();
		}
		
	}
    
	private AlertDialog createAlert(String messaggeError,Context c) {
		AlertDialog.Builder builder = new AlertDialog.Builder(c);
		builder.setMessage(messaggeError.toString())
				.setTitle("Error")
				.setCancelable(false)
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						});
		AlertDialog alert = builder.create();
		return alert;
	}
}
