package mobidev.geowall;

import android.app.Activity;
import android.content.Intent;

public class MediaController {

	/**
	 * This is used to capture image
	 * 
	 * @param location
	 *            Path where image or video are stored
	 */
	protected static Intent captureMedia(String location) {

		Intent imageIntent = new Intent(Intent.ACTION_GET_CONTENT);
		imageIntent.setType(location);
		return imageIntent;

	}

}
