package mobidev.geowall;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

public class MediaController {

	static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	static final int CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE = 200;

	static final String STOREIMAGE = "image/*";
	static final String STOREVIDEO = "video/*";

	public static final int MEDIA_TYPE_IMAGE = 1;
	public static final int MEDIA_TYPE_VIDEO = 2;
	public static final int TAKE_PHOTO = 3;
	public static final int TAKE_VIDEO = 4;

	/**
	 * This is used to capture image
	 * 
	 * @param location
	 *            Path where image or video are stored
	 */
	protected static Intent getMedia(int mediatype) {

		if (mediatype == MEDIA_TYPE_IMAGE) {
			Intent imageIntent = new Intent(Intent.ACTION_GET_CONTENT);
			imageIntent.setType(STOREIMAGE);
			return imageIntent;
		}else if(mediatype== MEDIA_TYPE_VIDEO){
			Intent imageIntent = new Intent(Intent.ACTION_GET_CONTENT);
			imageIntent.setType(STOREVIDEO);
			return imageIntent;
		}
		else if (mediatype == TAKE_PHOTO) {
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			intent.putExtra(MediaStore.EXTRA_OUTPUT,
					getOutputMediaFileUri(MEDIA_TYPE_IMAGE));
			return intent;

		} else if (mediatype == TAKE_VIDEO) {
			Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
			intent.putExtra(MediaStore.EXTRA_OUTPUT,
					getOutputMediaFileUri(MEDIA_TYPE_VIDEO));
			return intent;
		} else
			return null;

	}

	

	/** Create a file Uri for saving an image or video */
	private static Uri getOutputMediaFileUri(int type) {
		return Uri.fromFile(getOutputMediaFile(type));
	}

	/** Create a File for saving an image or video */
	private static File getOutputMediaFile(int type) {
		// To be safe, you should check that the SDCard is mounted
		// using Environment.getExternalStorageState() before doing this.

		File mediaStorageDir = new File(
				Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				"MyCameraApp");
		// This location works best if you want the created images to be shared
		// between applications and persist after your app has been uninstalled.

		// Create the storage directory if it does not exist
		if (!mediaStorageDir.exists()) {
			if (!mediaStorageDir.mkdirs()) {
				Log.d("MyCameraApp", "failed to create directory");
				return null;
			}
		}

		// Create a media file name
		String name = "temp";
		File mediaFile;
		if (type == MEDIA_TYPE_IMAGE) {
			mediaFile = new File(mediaStorageDir.getPath() + File.separator
					+ "IMG_" + name + ".jpg");
		} else if (type == MEDIA_TYPE_VIDEO) {
			mediaFile = new File(mediaStorageDir.getPath() + File.separator
					+ "VID_" + name + ".mp4");
		} else {
			return null;
		}

		return mediaFile;
	}
}
