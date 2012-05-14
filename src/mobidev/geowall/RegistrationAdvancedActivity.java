package mobidev.geowall;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.backup.FileBackupHelper;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import android.net.Uri;

import android.os.Bundle;
import android.provider.MediaStore;

import android.util.DisplayMetrics;
import android.util.Log;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class RegistrationAdvancedActivity extends Activity implements
		OnClickListener {
	private TextView mDateDisplay, countryText;
	private ImageButton mPickDate, imageButton;
	private ImageView accountImage;
	private Button saveButton;
	private int mYear;
	private int mMonth;
	private int mDay;

	// is a static integer that uniquely identifies the Dialog that will display
	// the date picker
	static final int DATE_DIALOG_ID = 0;
	static final int COUNTRY_DIALOG_ID = 1;
	static final int MEDIA_DIALOG_ID = 2;

	// it is used to callback activity
	static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	static final int CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE = 200;

	static final String STOREIMAGE = "image/*";

	File imageAccountFile = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registrationadvancedlayout);

		mDateDisplay = (TextView) findViewById(R.id.dateRegistrationText);
		mPickDate = (ImageButton) findViewById(R.id.calendarButton);
		imageButton = (ImageButton) findViewById(R.id.putImageButton);
		saveButton = (Button) findViewById(R.id.saveButtonAdvanced);
		accountImage = (ImageView) findViewById(R.id.accountImage);
		countryText = (TextView) findViewById(R.id.countryText);

		mPickDate.setOnClickListener(this);
		imageButton.setOnClickListener(this);
		countryText.setOnClickListener(this);
		saveButton.setOnClickListener(this);

		// get the current date
		Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);
		updateBirthday();
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
		switch (v.getId()) {

		case R.id.calendarButton:
			showDialog(DATE_DIALOG_ID);
			break;
		case R.id.putImageButton:
			showDialog(MEDIA_DIALOG_ID);
			break;
		case R.id.countryText:
			showDialog(COUNTRY_DIALOG_ID);
			break;
		case R.id.saveButtonAdvanced:
			Intent i = new Intent(this, GeoMapActivity.class);
			this.startActivity(i);
			break;
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {

		// save state of the date
		outState.putInt("Day", mDay);
		outState.putInt("Month", mMonth);
		outState.putInt("Year", mYear);

		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {

		// pull the date

		super.onRestoreInstanceState(savedInstanceState);
		mDay = savedInstanceState.getInt("Day", mDay);
		mMonth = savedInstanceState.getInt("Month", mMonth);
		mYear = savedInstanceState.getInt("Year", mYear);

		updateBirthday();

	}

	// updates the date in the TextView
	private void updateBirthday() {
		mDateDisplay.setText(new StringBuilder()
				// Month is 0 based so add 1
				.append(mDay).append("/").append(mMonth + 1).append("/")
				.append(mYear).append(" "));
	}

	private void updateCountry(String country) {
		countryText.setText(country);
	}

	// the callback received when the user "sets" the date in the dialog
	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
			updateBirthday();
		}
	};

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this, mDateSetListener, mYear, mMonth,
					mDay);
		case COUNTRY_DIALOG_ID:
			final String[] mCountryArray = getResources().getStringArray(
					R.array.countries);
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle(R.string.countryText);
			builder.setItems(mCountryArray,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int item) {
							updateCountry(mCountryArray[item]);
						}
					});
			AlertDialog alert = builder.create();
			return alert;

		case MEDIA_DIALOG_ID:
			final String[] items = getResources().getStringArray(
					R.array.mediaPhoto);

			AlertDialog.Builder buildMedia = new AlertDialog.Builder(this);
			buildMedia.setTitle(R.string.mediaText);
			buildMedia.setIcon(R.drawable.camera);
			buildMedia.setItems(items, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int item) {
					if (item == 0)
						getMedia(MediaController.MEDIA_TYPE_IMAGE,
								CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
					if (item == 1)
						getMedia(MediaController.TAKE_PHOTO,
								CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
				}
			});
			AlertDialog alertMedia = buildMedia.create();
			return alertMedia;
		}
		return null;
	}

	/**
	 * This is used to capture image
	 */
	protected void getMedia(int option, int captureImage) {

		// if the image exist delete

	
		MediaController.getImage().delete();
		Bitmap b=null;
		

		Intent imageIntent = MediaController.getMedia(option);

		startActivityForResult(imageIntent, captureImage);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
			if (resultCode == RESULT_OK) {

				accountImage.setAdjustViewBounds(true);
				accountImage.setMaxHeight(40);
				accountImage.setMaxWidth(40);
				//unbindDrawables(findViewById(R.id.RootView));
				System.gc();
				if (data != null) {
					// user select image

					Uri image = data.getData();
					InputStream in;
					try {
						in = getContentResolver().openInputStream(image);
						
						OutputStream out=new FileOutputStream(MediaController.getImage());
						
						 byte[] buf = new byte[1024];
						  int len;
						  while ((len = in.read(buf)) > 0){
						  out.write(buf, 0, len);
						  }
						  in.close();
						  out.close();
						  
						  

						accountImage.setImageBitmap(MediaController.decodeFile(MediaController.getImage()));
					} catch (Exception e) {
						e.printStackTrace();
					}

				} else {
					// user take photo
					imageAccountFile = MediaController.getImage();

					Bitmap temp = MediaController.decodeFile(imageAccountFile);

					accountImage.setImageBitmap(temp);
					
				}

			} else if (resultCode == RESULT_CANCELED) {
				// User cancelled the image capture
			} else {
				// Image capture failed, advise user
			}
		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

	}

	private void unbindDrawables(View view) {
		if (view.getBackground() != null) {
			view.getBackground().setCallback(null);
		}
		if (view instanceof ViewGroup) {
			for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
				unbindDrawables(((ViewGroup) view).getChildAt(i));
			}
			((ViewGroup) view).removeAllViews();
		}
	}
}
