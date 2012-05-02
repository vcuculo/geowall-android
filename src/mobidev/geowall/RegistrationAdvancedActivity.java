package mobidev.geowall;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;

import android.view.View;
import android.view.View.OnClickListener;

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
	private int mYear;
	private int mMonth;
	private int mDay;

	// is a static integer that uniquely identifies the Dialog that will display
	// the date picker
	static final int DATE_DIALOG_ID = 0;
	static final int COUNTRY_DIALOG_ID = 1;

	// it is used to callback activity
	static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;


	static final String STOREIMAGE = "image/*";


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registrationadvancedlayout);

		mDateDisplay = (TextView) findViewById(R.id.dateRegistrationText);
		mPickDate = (ImageButton) findViewById(R.id.calendarButton);
		imageButton = (ImageButton) findViewById(R.id.putImageButton);

		accountImage=(ImageView) findViewById(R.id.accountImage);

		countryText = (TextView) findViewById(R.id.countryText);


		mPickDate.setOnClickListener(this);
		imageButton.setOnClickListener(this);
		countryText.setOnClickListener(this);
		// get the current date
		Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {

		case R.id.calendarButton:
			showDialog(DATE_DIALOG_ID);
			break;
		case R.id.putImageButton:
			captureImage();
			break;
		case R.id.countryText:
			showDialog(COUNTRY_DIALOG_ID);
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
		}
		return null;
	}

	/**
	 * This is used to capture image
	 */
	protected void captureImage() {


		


		Intent imageIntent = MediaController.captureMedia(STOREIMAGE);

		startActivityForResult(
				Intent.createChooser(imageIntent, "Select Picture"),
				CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);



	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
			if (resultCode == RESULT_OK) {
				// Image captured and saved to fileUri specified in the Intent
				Toast.makeText(this, "Image saved to:\n" + data.getData(),
						Toast.LENGTH_LONG).show();

				
				accountImage.setImageURI(data.getData());
				

			} else if (resultCode == RESULT_CANCELED) {
				// User cancelled the image capture
			} else {
				// Image capture failed, advise user
			}
		}

	

	}
}
