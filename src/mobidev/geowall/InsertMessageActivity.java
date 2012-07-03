package mobidev.geowall;


import java.io.File;


import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class InsertMessageActivity extends Activity implements OnClickListener {

	ImageButton upload;
	TextView message;
	String imageMessageBase64,text;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.insertmessage);
		ImageButton upload = (ImageButton) findViewById(R.id.uploadButton);
		message = (TextView) findViewById(R.id.messageText);
		upload.setOnClickListener(this);
	
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
			//CustomDialog.this.dismiss();
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
					//imDr = new BitmapDrawable(temp);
					//imDr.setBounds(0, 0, 40, 40);
					text = "Entrato";
					//setSharedPreference(message);
					message.setText(text);	
					Log.i("DialogMetodo","Entrato");
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

