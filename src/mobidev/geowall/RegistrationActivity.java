package mobidev.geowall;

import java.io.File;
import java.io.IOException;
import java.security.DigestException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class RegistrationActivity extends Activity implements OnClickListener {

	private Button saveButton, nextButton;
	private ImageView accountImage;
	private TextView nick, email, pw;
	File imageAccountFile = null;

	private UserData userPreferences;
	private String USER_PREFERENCES = "UserPreferncer";
	private String ERROR = "- Nick is required\n\n- Check the email is correct\n\n- Password is required and it is max 10 character";

	public final int ERROR_DIALOG_ID = 0;
	String cNick;
	String cEmail;
	String cPw;

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

		SharedPreferences setting = getSharedPreferences(USER_PREFERENCES, 0);

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
		Intent i;
		switch (v.getId()) {
		case R.id.saveButton:
			String nickUser = nick.getText().toString();
			String emailUser = email.getText().toString();
			String pwUser = pw.getText().toString();

			UtilityCheck checkUser = new CheckNick(nickUser),
			checkEmail = new CheckEmail(emailUser),
			checkPassword = new CheckPassword(pwUser);

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

				Log.i("PreferencesPw", userPreferences.getpassword());

			} catch (NoSuchAlgorithmException e) {
				Toast.makeText(
						this,
						"Problema nella criptazione della password\nSegnalare Errore",
						Toast.LENGTH_LONG).show();
			} catch (DigestException e) {
				Toast.makeText(
						this,
						"Problema nella criptazione della password\nSegnalare Errore",
						Toast.LENGTH_LONG).show();
				;
			} catch (IOException e) {
				Toast.makeText(this, "Problema Digest\nSegnalare Errore",
						Toast.LENGTH_LONG).show();
				Log.i("PreferencesNick", nickUser);
				Log.i("PreferencesEmail", emailUser);
				Log.i("Preferences", e.getMessage());
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

	protected Dialog onCreateDialog(int id) {
		AlertDialog alert = null;
		switch (id) {
		case ERROR_DIALOG_ID:

			alert = createAlert(ERROR);

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

	private String getDigest(String pw) throws NoSuchAlgorithmException,
			IOException, DigestException {

		StringBuffer hexString = null;
		MessageDigest digester = MessageDigest.getInstance("MD5");

		digester.update(pw.getBytes());
		byte[] messageDigest = digester.digest();

		// Hex
		hexString = new StringBuffer();
		for (int i = 0; i < messageDigest.length; i++)
			hexString.append(Integer.toHexString(0xff & messageDigest[i]));

		return hexString.toString();

	}

	private void setSharedPreference(UserData userPreferences) {
		if (userPreferences == null)
			return;

		SharedPreferences settings = getSharedPreferences(USER_PREFERENCES, 0);
		SharedPreferences.Editor editor = settings.edit();

		editor.putString("NICK", userPreferences.getnick());
		editor.putString("EMAIL", userPreferences.getemail());
		editor.putString("PASS", userPreferences.getpassword());

		editor.commit();

	}

	@Override
	protected void onStop() {

		super.onStop();
		setSharedPreference(userPreferences);
	}

}
