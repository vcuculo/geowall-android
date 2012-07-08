package mobidev.geowall;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

public class WallActivity extends Activity implements OnClickListener{
	/** Called when the activity is first created. */

	final static int CUSTOM_DIALOG=1;
	private String USER_PREFERENCES = "UserPreference";
	SharedPreferences setting;
	ImageView accountImage, messageImage;
	String imageMessageBase64=null;//immagine da passare al server
	
	Button insert;
	ImageButton upload;
	TextView mesText;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wall_layout);
		int i= getIntent().getIntExtra("ID", 5);
		Log.i("INTENT2", Integer.toString(i));
		addElementResult();
		accountImage=(ImageView) findViewById(R.id.accountImage);		
		insert=(Button) findViewById(R.id.insertMessageButton);
		if(i==4){
			insert.setVisibility(0);
			insert.setOnClickListener(this);
		}
		
		
		
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
	


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.insertMessageButton:
			
			Intent intent=new Intent(this,mobidev.geowall.InsertMessageActivity.class);
			startActivity(intent);
			break;
		
	
		}
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		MenuInflater inflater= getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		Intent i;
		switch(item.getItemId()){
		case R.id.logoutMenu:
			new LogoutController().execute(this);
		
			break;
		case R.id.settingMenu:
			i = new Intent(this, RegistrationActivity.class);
			this.startActivity(i);
			break;
		default :
			return super.onOptionsItemSelected(item);
		}
		return true;
	}
	




}
