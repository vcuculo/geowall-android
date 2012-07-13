package mobidev.geowall;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
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

	// The new size we want to scale to
	public static final int REQUIRED_SIZE = 70;

	/**
	 * This is used to capture image or video
	 * 
	 * @param mediatype
	 *            The mode to select image or video
	 */
	protected static Intent getMedia(int mediatype) {

		if (mediatype == MEDIA_TYPE_IMAGE) {
			Intent imageIntent = new Intent(Intent.ACTION_GET_CONTENT);
			imageIntent.setType(STOREIMAGE);
			return imageIntent;
		} else if (mediatype == MEDIA_TYPE_VIDEO) {
			Intent imageIntent = new Intent(Intent.ACTION_GET_CONTENT);
			imageIntent.setType(STOREVIDEO);
			return imageIntent;
		} else if (mediatype == TAKE_PHOTO) {

			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			intent.putExtra(MediaStore.EXTRA_OUTPUT,
					getOutputMediaFileUri(MEDIA_TYPE_IMAGE));
			intent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, 5000000);

			return intent;

		} else if (mediatype == TAKE_VIDEO) {
			Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
			intent.putExtra(MediaStore.EXTRA_OUTPUT,
					getOutputMediaFileUri(MEDIA_TYPE_VIDEO));
			return intent;
		} else
			return null;

	}

	/**
	 * Save a Bitmap in a specific path
	 */
	protected static boolean saveMedia(Bitmap image, int type) {
		if (image == null)
			return false;
		File temp = getOutputMediaFile(type);

		try {
			FileOutputStream stream = new FileOutputStream(temp);

			image.compress(Bitmap.CompressFormat.JPEG, 90, stream);
			stream.close();
			return true;
		} catch (IOException e) {
			Log.i("Error", e.getMessage());
			return false;
		}

	}

	/**
	 * 
	 * @return a specific file that contains a Bitmap
	 */
	protected static File getImage() {
		return getOutputMediaFile(MEDIA_TYPE_IMAGE);
	}

	public static void deleteImage() {
		getImage().delete();
	}

	public static String encodeBase64toString(File image) {

		InputStream is;
		try {
			is = new FileInputStream(image);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			byte[] b = new byte[1024];
			int bytesRead;
			while ((bytesRead = is.read(b)) != -1) {
				bos.write(b, 0, bytesRead);
			}
			byte[] bytes = bos.toByteArray();

			return Base64.encodeToString(bytes, Base64.DEFAULT);
		} catch (FileNotFoundException e) {
			return null;

		} catch (IOException i) {
			return null;
		}

	}

	public static Bitmap decodeBase64toBitmap(String image) {

		byte[] b = Base64.decode(image, Base64.DEFAULT);
		InputStream i = new ByteArrayInputStream(b);
		return BitmapFactory.decodeStream(i);

	}


	/**
	 * 
	 * @return a specific file that contains a Video
	 */
	protected static File getVideo() {
		return getOutputMediaFile(MEDIA_TYPE_VIDEO);
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

	/**
	 * 
	 * @param f
	 *            file that contains a Bitmap
	 * @return a compress bitmap
	 */
	static public Bitmap decodeFile(File f) {
		try {
			// Decode image size
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(new FileInputStream(f), null, o);

			// Find the correct scale value. It should be the power of 2.
			int scale = 1;
			while (o.outWidth / scale / 2 >= REQUIRED_SIZE
					&& o.outHeight / scale / 2 >= REQUIRED_SIZE)
				scale *= 2;

			// Decode with inSampleSize
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = scale;
			return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
		} catch (FileNotFoundException e) {
		}
		return null;
	}
	
}
