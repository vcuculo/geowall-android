package mobidev.geowall;


import java.io.IOException;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

public class NoticeboardController extends AsyncTask<Context, Context, Void> {
	String nick, email, pw, session;
	private String USER_PREFERENCES = "UserPreference";
	boolean error = false;
	public final int ERROR_DIALOG_ID = 0;
	public final int ERROR_COMMUNICATION = 1;
	ProgressDialog dialog;
	Context contextglobal;

	public NoticeboardController(){
		super();
	}

	protected Void doInBackground(Context... context) {
		contextglobal = context[0];
		CommunicationController cc = new CommunicationController();
		SharedPreferences sharePreferences = context[0].getSharedPreferences(
				USER_PREFERENCES, 0);
		publishProgress(context[0]);
		session = sharePreferences.getString("SESSION", null);
		if (session != null) {
			try {
				
				cc.sendRequest("logout",
						DataController.marshallSession(session));
				SharedPreferences.Editor editor = sharePreferences.edit();
				editor.clear();
				editor.commit();
				Intent i =new Intent(contextglobal,GeoWallActivity.class);
				contextglobal.startActivity(i);
			} catch (IOException e) {
				Log.e("Errore login", e.getLocalizedMessage());
				error = false;
			}
		}
		return null;
	}

	protected void onProgressUpdate(Context... c) {

		dialog = ProgressDialog.show(c[0], "", "Loading. Please wait...", true);

	}

	protected void onPostExecute(Void parameter) {
		dialog.dismiss();
		dialog = null;
		return;
	}
}
