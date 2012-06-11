package mobidev.geowall;

import android.app.Activity;
import android.app.Dialog;
import android.app.SearchManager.OnCancelListener;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

public class WallActivity extends Activity implements OnClickListener{
	/** Called when the activity is first created. */

	final static int CUSTOM_DIALOG=1;
	private String USER_PREFERENCES = "UserPreferncer";
	SharedPreferences setting;
	ImageView accountImage;
	Button insert;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wall_layout);
		addElementResult();
		accountImage=(ImageView) findViewById(R.id.accountImage);		
		insert=(Button) findViewById(R.id.insertMessageButton);
		insert.setOnClickListener(this);
		
	}

	public void addElementResult() {
		TableLayout messages = (TableLayout) findViewById(R.id.tableMessages);
		LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		for (int i = 0; i < 20; i++) {
			View itemView = inflater.inflate(R.layout.wall_layout_item, null);

			TextView t1 = (TextView) itemView.findViewById(R.id.title);
			t1.setText("Messaggio " + i);
			TextView t2 = (TextView) itemView.findViewById(R.id.date);
			t2.setText((i+1) + "/05/2012");

			messages.addView(itemView);
		}
	}
	
	public void onResume() {
		super.onResume();
		setting = getSharedPreferences(USER_PREFERENCES, 0);
		String photo=setting.getString("IMG", null);

		if (photo!=null) {
			accountImage.setAdjustViewBounds(true);
			accountImage.setMaxHeight(40);
			accountImage.setMaxWidth(40);
			accountImage.setImageBitmap(MediaController.decodeBase64toBitmap(photo));
			
		}

	}
	
	protected Dialog onCreateDialog(int id) {
	    Dialog dialog=new Dialog(this);
	    switch(id) {
	    case CUSTOM_DIALOG:

	    	dialog.setContentView(R.layout.custom_account_dialog);
	    	dialog.setTitle(R.string.dialogTitle);
	    	
	    	break;
	    }
	    return dialog;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.insertMessageButton:
			Context mContext = getApplicationContext();
			Dialog dialog = new Dialog(mContext);

			dialog.setContentView(R.layout.custom_account_dialog);
			showDialog(CUSTOM_DIALOG);
			Toast.makeText(this, "Geo", Toast.LENGTH_LONG).show();
			break;

	
		}
	}

	
	
}
