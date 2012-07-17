package mobidev.geowall;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class UpdateNBController extends AsyncTask<Context, Context, Context> {
	ProgressDialog dialog;
	Context contextglobal;
	WallActivity a;
	
	public UpdateNBController(WallActivity a){
		this.a=a;
	}
	
	@Override
	protected Context doInBackground(Context... params) {
		// TODO Auto-generated method stub
		contextglobal=params[0];
		
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
	}
	
}
