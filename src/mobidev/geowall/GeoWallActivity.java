package mobidev.geowall;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class GeoWallActivity extends Activity implements OnClickListener {
	private TextView nick, pw, forgot;
	private Button login, signup;
	private ImageButton fbSocial, twSocial;
	
	private String USER_PREFERENCES = "UserPreferncer";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		nick = (TextView) findViewById(R.id.nickText);
		pw = (TextView) findViewById(R.id.pwText);
		forgot = (TextView) findViewById(R.id.forgotLabel);
		login = (Button) findViewById(R.id.loginButton);
		signup = (Button) findViewById(R.id.registerButton);
		fbSocial = (ImageButton) findViewById(R.id.fbButton);
		twSocial = (ImageButton) findViewById(R.id.twButton);

		login.setOnClickListener(this);
		signup.setOnClickListener(this);
		forgot.setOnClickListener(this);
		fbSocial.setOnClickListener(this);
		twSocial.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		case R.id.registerButton:
			// Il codice che segue lo utlizzo solo per far partire
			// l'activity di registrazione per fare delle prove
			SharedPreferences settings = getSharedPreferences(USER_PREFERENCES, 0);
			SharedPreferences.Editor editor = settings.edit();
			editor.clear();
			editor.commit();
			
			Intent i = new Intent(this, RegistrationActivity.class);
			this.startActivity(i);

			// delete image temp if it exists
			File temp = MediaController.getImage();
			temp.delete();
			break;
		}
	}
	

}