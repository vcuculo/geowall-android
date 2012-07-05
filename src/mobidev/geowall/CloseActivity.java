package mobidev.geowall;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

public class CloseActivity extends Activity {

	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		finish();
	}
	
	public void onDestroy(){
		super.onDestroy();
	}
}
