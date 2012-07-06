package mobidev.geowall;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class InsertMessageActivity extends Activity implements OnClickListener {

	ImageButton upload,facebook,twitter;
	Button send;
	TextView message;
	String imageMessageBase64, text;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.insertmessage);
		ImageButton upload = (ImageButton) findViewById(R.id.uploadButton);
		message = (TextView) findViewById(R.id.messageText);
		
		upload.setOnClickListener(this);
		send=(Button)findViewById(R.id.insertMessageButton);
		send.setOnClickListener(this);
		facebook=(ImageButton)findViewById(R.id.facebookShareButton);
		facebook.setOnClickListener(this);
		twitter=(ImageButton)findViewById(R.id.twitterShareButtom);
		twitter.setOnClickListener(this);
	}
	

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.uploadButton:

			Intent imageIntent = MediaController
					.getMedia(MediaController.TAKE_PHOTO);

			startActivityForResult(imageIntent,
					MediaController.CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
			// CustomDialog.this.dismiss();
			break;
		case R.id.insertMessageButton:
			text=message.getText().toString();
			/*
			 * chiamata al server
			 */
			Log.i("Stringa",text);
			break;
			
		case R.id.facebookShareButton:
			/*
			 * chiamata
			 */
			break;
		case R.id.twitterShareButtom:
			/*
			 * chiamata
			 */
			break;
		}
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == MediaController.CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {

			if (resultCode == RESULT_OK) {

				try {

					// user take photo
					File imageMessageFile = MediaController.getImage();
					Bitmap temp = MediaController.decodeFile(imageMessageFile);
					// MediaController.saveMedia(temp,
					// MediaController.MEDIA_TYPE_IMAGE);

					imageMessageFile = null;

					imageMessageBase64 = MediaController
							.encodeBase64toString(MediaController.getImage());
					Drawable imDr = new BitmapDrawable(temp);
					imDr.setBounds(0, 0, 60, 60);
					// text = "Entrato";
					// setSharedPreference(message);
					message.setCompoundDrawables(imDr, null, null, null);
					Log.i("DialogMetodo", "Entrato");
				} catch (Exception e) {
					Log.i("Camera", "CameraError");
					e.printStackTrace();
				}

			} else {
				text = null;
			}

		}
	}

}
