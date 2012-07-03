package mobidev.geowall;


import java.io.File;

import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

public class InsertMessageDF extends DialogFragment implements OnClickListener {

	
	ImageButton upload;
	TextView message;
	String imageMessageBase64;
	


	  @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	        View v = inflater.inflate(R.layout.insertmessage, container, false);
	        return v;
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
					imDr.setBounds(0, 0, 40, 40);
					//setSharedPreference(message);
					message.setCompoundDrawablesWithIntrinsicBounds(imDr, null, null, null);	
					Log.i("DialogMetodo","Entrato");
				} catch (Exception e) {
					Log.i("Camera", "CameraError");
					e.printStackTrace();
				}

		

		}
	}
	

}

