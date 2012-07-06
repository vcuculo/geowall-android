package mobidev.geowall;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
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
	private TextView nick, email, pw;
	Bitmap imageAccount = null;

	private UserData userPreferences;
	private String USER_PREFERENCES = "UserPreference";
	private String ERROR = "- Nick is required\n\n- Check the email is correct\n\n- Password is required and it is max 10 character";
	private String ERRORCOM = "Impossible connect to server";
	public final int ERROR_DIALOG_ID = 0;
	public final int ERROR_COMMUNICATION = 1;
	String cNick;
	String cEmail;
	String cPw;
	String session;
	SharedPreferences settings;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registrationlayout);

		nextButton = (Button) findViewById(R.id.nextButton);
		saveButton = (Button) findViewById(R.id.saveButton);
		accountImage = (ImageView) findViewById(R.id.accountImage);

		nick = (TextView) findViewById(R.id.nickRegistrationText);
		email = (TextView) findViewById(R.id.emailRegistrationText);
		pw = (TextView) findViewById(R.id.pwRegistrationText);

		cNick = (String) nick.getText().toString();
		cEmail = (String) email.getText().toString();
		cPw = (String) pw.getText().toString();

		nextButton.setOnClickListener(this);
		saveButton.setOnClickListener(this);

		settings = getSharedPreferences(USER_PREFERENCES, MODE_MULTI_PROCESS);
		nick.setText(settings.getString("NICK", ""));
		email.setText(settings.getString("EMAIL", ""));

	}

	public void onResume() {
		super.onResume();

		if (settings.contains("IMG")) {
			imageAccount = MediaController.decodeBase64toBitmap(settings.getString("IMG",null));

				accountImage.setAdjustViewBounds(true);
				accountImage.setMaxHeight(40);
				accountImage.setMaxWidth(40);
				accountImage.setImageBitmap(imageAccount);

			
		}
	}

	@Override
	public void onClick(View v) {
		Intent i;

		String nickUser = nick.getText().toString();
		String emailUser = email.getText().toString();
		String pwUser = pw.getText().toString();

		UtilityCheck checkUser = new CheckNick(nickUser), checkEmail = new CheckEmail(
				emailUser), checkPassword = new CheckPassword(pwUser);

		boolean controll = true;
		if (!checkUser.check() || nickUser.equals(cNick)) {
			controll = false;
		}
		if (!checkEmail.check()) {
			controll = false;
		}
		if (!checkPassword.check() || pwUser.equals(cPw)) {
			controll = false;
		}
		if (controll == false) {
			showDialog(ERROR_DIALOG_ID);
			return;
		}

		try {

			userPreferences = new UserData(nickUser, emailUser,
					getDigest(pwUser));

		} catch (NoSuchAlgorithmException e) {
			Toast.makeText(
					this,
					"Problema nella criptazione della password\nSegnalare Errore",
					Toast.LENGTH_LONG).show();
		}
		switch (v.getId()) {
		case R.id.saveButton:
			setSharedPreference(userPreferences);
			new SignUpController().execute(this);
			break;

		case R.id.nextButton:
			setSharedPreference(userPreferences);
			i = new Intent(this, RegistrationAdvancedActivity.class);
			this.startActivity(i);
			break;
		}
	}

	protected Dialog onCreateDialog(int id) {
		AlertDialog alert = null;
		switch (id) {
		case ERROR_DIALOG_ID:
			alert = createAlert(ERROR);
			break;
		}
		return alert;
	}

	private AlertDialog createAlert(String messaggeError) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(messaggeError.toString())
				.setTitle("Error")
				.setCancelable(false)
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						});
		AlertDialog alert = builder.create();
		return alert;
	}

	private String getDigest(String pw) throws NoSuchAlgorithmException {
		MessageDigest digester = MessageDigest.getInstance("MD5");
		digester.update(pw.getBytes());
		return String.format("%032x", new BigInteger(1, digester.digest()));
	}

	private void setSharedPreference(UserData userPreferences) {
		if (userPreferences == null)
			return;
		SharedPreferences.Editor editor = settings.edit();

		editor.putString("NICK", userPreferences.getnick());
		editor.putString("EMAIL", userPreferences.getemail());
		editor.putString("PASS", userPreferences.getpassword());
		editor.commit();

	}

}
