package mobidev.geowall;

import java.io.IOException;
import java.util.Date;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

public class MessageController extends AsyncTask<Context, Context, Context> {

	ProgressDialog dialog;
	Context contextglobal;
	RequestNoticeBoard nb;
	Message m;
	private String USER_PREFERENCES = "UserPreference";
	Activity a;
	
	public MessageController(RequestNoticeBoard nb,Message m, Activity a){
		super();
		this.nb=nb;
		this.m=m;
		this.a=a;
	}
	
	@Override
	protected Context doInBackground(Context... params) {
		// TODO Auto-generated method stub
		contextglobal = params[0];
		publishProgress(contextglobal);
		try {
			CommunicationController cc = new CommunicationController();
			SharedPreferences setting = contextglobal.getSharedPreferences(
					USER_PREFERENCES, 0);
			String result = cc.sendRequest(
					"insert",
					DataController.marshallMessage(
							setting.getString("SESSION", null),nb,m));
			int id=DataController.unMarshallMessage(result);
			Message message= new Message(id,m.getnick(), m.gettext(), m.getimg(), m.getvideo(), m.getsocial());
			if (id != -1)
				DataBaseController.writeMessage(new DataBaseGeowall(
						contextglobal), nb,message);

		} catch (IOException e) {
			Log.e("Errore login", e.getLocalizedMessage());
		
		}

		return contextglobal;
	}

	protected void onProgressUpdate(Context... c) {

		dialog = ProgressDialog.show(c[0], "", "Loading. Please wait...", true);

	}

	protected void onPostExecute(Context c) {
		if (dialog != null) {
			dialog.dismiss();
			dialog = null;
		}
		a.finish();
		Intent i=new Intent(contextglobal,WallActivity.class);
		i.putExtra("ID", 4);
		i.putExtra("NoRequest", true);
		i.putExtra("pxNb", nb.getPx());
		i.putExtra("pyNb", nb.getPy());
		i.putExtra("date", nb.getDate());
		contextglobal.startActivity(i);
		return;
	}
}
