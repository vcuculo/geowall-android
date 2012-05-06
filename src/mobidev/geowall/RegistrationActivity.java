package mobidev.geowall;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class RegistrationActivity extends Activity implements OnClickListener {

	private Button saveButton, nextButton;
	private ImageView accountImage;
	File imageAccountFile = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registrationlayout);

		nextButton = (Button) findViewById(R.id.nextButton);
		accountImage= (ImageView) findViewById(R.id.accountImage);
		
		nextButton.setOnClickListener(this);

	}
	
	public void onResume(){
		super.onResume();
		
		imageAccountFile = MediaController.getImage();

		if (imageAccountFile.exists()) {
			accountImage.setAdjustViewBounds(true);
			accountImage.setMaxHeight(40);
			accountImage.setMaxWidth(40);
			accountImage.setImageURI(Uri.fromFile(imageAccountFile));
			
		}
	
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		Intent i = new Intent(this, RegistrationAdvancedActivity.class);
		this.startActivity(i);

	}

}
