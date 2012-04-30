package mobidev.geowall;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class RegistrationActivity extends Activity implements OnClickListener {

	private Button saveButton,nextButton;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registrationlayout);
		
		nextButton=(Button) findViewById(R.id.nextButton);
		
		nextButton.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		Intent i = new Intent(this, Registration_AdvancedActivity.class);
		this.startActivity(i);
	}

}
