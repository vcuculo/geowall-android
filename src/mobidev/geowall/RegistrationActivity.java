package mobidev.geowall;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.DigestException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class RegistrationActivity extends Activity implements OnClickListener {

	private Button saveButton, nextButton;
	private ImageView accountImage;
	private TextView nick, email, pw;
	File imageAccountFile = null;

	private UserData userPreferences;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registrationlayout);

		nextButton = (Button) findViewById(R.id.nextButton);
		saveButton= (Button) findViewById(R.id.saveButton);
		accountImage = (ImageView) findViewById(R.id.accountImage);

		nick = (TextView) findViewById(R.id.nickRegistrationText);
		email = (TextView) findViewById(R.id.emailRegistrationText);
		pw = (TextView) findViewById(R.id.pwRegistrationText);

		nextButton.setOnClickListener(this);
		saveButton.setOnClickListener(this);

	}

	public void onResume() {
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
		Intent i;
		switch (v.getId()) {
		case R.id.saveButton:
			String nickUser = (String) nick.getText();
			String emailUser = (String) email.getText();
			String pwUset = (String) pw.getText();
			/*
			 * inserire il controllo sull'email
			 */
			try {
				userPreferences = saveUserData(nickUser, emailUser, pwUset);
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DigestException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			i = new Intent(this, GeoMapActivity.class);
			this.startActivity(i);
			break;
			
			
		case R.id.nextButton:
			i = new Intent(this, RegistrationAdvancedActivity.class);
			this.startActivity(i);
			break;
		}
	}

	private UserData saveUserData(String nick, String email, String pw)
			throws NoSuchAlgorithmException, IOException, DigestException {
		InputStream in = getContentResolver().openInputStream(Uri.parse(pw));

		MessageDigest digester = MessageDigest.getInstance("MD5");
		byte[] bytes = new byte[8192];
		int byteCount;
		while ((byteCount = in.read(bytes)) > 0) {
			digester.update(bytes, 0, byteCount);
		}
		digester.
		int digest = digester.digest(bytes, 0, bytes.length);
		return new UserData(nick, email, digest);

	}

	private void setSharedPreference() {
		SharedPreferences settings = getSharedPreferences("USER_PREFENRENCES",
				0);
		SharedPreferences.Editor editor = settings.edit();
		/*
		 * preference
		 */
		editor.commit();

	}
}
