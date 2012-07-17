package mobidev.geowall;

import java.io.IOException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

public class NoticeboardController extends AsyncTask<Context, Context, Context> {
	String nick, email, pw, session;
	private String USER_PREFERENCES = "UserPreference";
	boolean error = false;
	public final int ERROR_DIALOG_ID = 0;
	public final int ERROR_COMMUNICATION = 1;
	ProgressDialog dialog;
	Context contextglobal;
	RequestNoticeBoard rnb;
	NoticeBoard nb;
	WallActivity a;
	public NoticeboardController(RequestNoticeBoard rnb, WallActivity a) {
		super();
		this.rnb = rnb;
		this.a=a;
	}

	protected Context doInBackground(Context... context) {
		contextglobal = context[0];
		SharedPreferences setting = contextglobal.getSharedPreferences(
				USER_PREFERENCES, 0);
		publishProgress(contextglobal);
		try {
			CommunicationController cc = new CommunicationController();
			String result = cc.sendRequest(
					"getnoticeboard",
					DataController.marshallGetNoticeBoard(
							setting.getString("SESSION", null), rnb));

			NoticeBoard nb = DataController.unMarshallGetNoticeBoard(result);
			if (nb != null){
				DataBaseController.writeAllMessage(new DataBaseGeowall(
						a), nb);
			}
		} catch (IOException e) {
			Log.e("Errore login", e.toString());
			error = false;
		}
		return contextglobal;
	}

	protected void onProgressUpdate(Context c) {

    	dialog = ProgressDialog.show(a, "", "Loading. Please wait...", true);
	}

	protected void onPostExecute(Context c) {
		if (dialog != null) {
			dialog.dismiss();
			dialog = null;
		}
		a.addElementResult();
		return;
	}

}
