package mobidev.geowall;

import java.io.IOException;
import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

	public NoticeboardController(RequestNoticeBoard rnb) {
		super();
		this.rnb = rnb;
		Log.i("positionX", Integer.toString(rnb.getPx()));
		Log.i("positionY", Integer.toString(rnb.getPy()));

	}

	protected Context doInBackground(Context... context) {
		contextglobal = context[0];
		publishProgress(contextglobal);
		SharedPreferences setting = contextglobal.getSharedPreferences(
				USER_PREFERENCES, 0);
		try {
			CommunicationController cc = new CommunicationController();
			String result = cc.sendRequest(
					"getnoticeboard",
					DataController.marshallGetNoticeBoard(
							setting.getString("SESSION", null), rnb));
			Log.i("RequestNoticeBoard", Integer.toString(rnb.getPx()));
			Log.i("RequestNoticeBoard", Integer.toString(rnb.getPy()));
			NoticeBoard nb = DataController.unMarshallGetNoticeBoard(result);
			if (nb != null)
				DataBaseController.writeAllMessage(new DataBaseGeowall(
						contextglobal), nb);

		} catch (IOException e) {
			Log.e("Errore login", "errore");
			error = false;
		}

		// Log.i("ResultNoticeBoard","scrive");
		return null;
	}

	protected void onProgressUpdate(Context... c) {

		dialog = ProgressDialog.show(c[0], "", "Loading. Please wait...", true);

	}

	protected void onPostExecute(Context c) {
		if (dialog != null) {
			dialog.dismiss();
			dialog = null;
		}

		return;
	}

}
