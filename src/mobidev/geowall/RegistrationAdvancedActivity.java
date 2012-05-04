package mobidev.geowall;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

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
	
	private int optionMedia;

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

	// updates the date in the TextView
	private void updateBirthday() {
		mDateDisplay.setText(new StringBuilder()
				// Month is 0 based so add 1
				.append(mMonth + 1).append("/").append(mDay).append("/")
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
			final CharSequence[] items = getResources().getStringArray(
					R.array.mediaOption);

			AlertDialog.Builder buildOption = new AlertDialog.Builder(this);
			buildOption.setTitle(R.string.mediaText);
			buildOption.setItems(items, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int item) {
					getMedia(item+1);
				}
			});
			AlertDialog alertOtpion = buildOption.create();

			return alertOtpion;
		}
		return null;
	}

	/**
	 * This is used to capture image
	 */
	protected void getMedia(int option) {

		
		Intent imageIntent= MediaController.getMedia(option);
	
		startActivityForResult(imageIntent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
			if (resultCode == RESULT_OK) {
				// Image captured and saved to fileUri specified in the Intent
				Toast.makeText(this, "Image saved to:\n" + data,
						Toast.LENGTH_LONG).show();

			} else if (resultCode == RESULT_CANCELED) {
				// User cancelled the image capture
			} else {
				// Image capture failed, advise user
			}
		}

		if (requestCode == CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE) {
			if (resultCode == RESULT_OK) {
				// Video captured and saved to fileUri specified in the Intent
				Toast.makeText(this,
						"Video saved to:\n" + data.getData().getPath(),
						Toast.LENGTH_LONG).show();
			} else if (resultCode == RESULT_CANCELED) {
				// User cancelled the video capture
			} else {
				// Video capture failed, advise user
			}
		}

	}

}
