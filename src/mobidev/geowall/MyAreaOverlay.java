package mobidev.geowall;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

public class MyAreaOverlay extends Overlay {

	final static int LON_FACTOR = (int) (1 * 1E4);
	final static int LAT_FACTOR = (int) (5 * 1E3);

	private GeoPoint mGp1, mGp2;
	private int id;

	public MyAreaOverlay(GeoPoint gp, int i) {
		id = i;
		Log.i("INFO", (gp.getLatitudeE6()) + " - " + gp.getLongitudeE6());

		int lat = gp.getLatitudeE6() / LAT_FACTOR;
		int lon = gp.getLongitudeE6() / LON_FACTOR;

		mGp1 = new GeoPoint((Math.round(lat) * LAT_FACTOR),
				(Math.round(lon) * LON_FACTOR));

		Log.i("INFO_ROUNDED",
				(mGp1.getLatitudeE6()) + " - " + mGp1.getLongitudeE6());

		mGp2 = new GeoPoint(mGp1.getLatitudeE6() + LAT_FACTOR,
				mGp1.getLongitudeE6() + LON_FACTOR);
	}

	@Override
	public void draw(Canvas canvas, MapView mapView, boolean shadow) {
		super.draw(canvas, mapView, shadow);

		Canvas mCanvas = canvas;
		Projection projection = mapView.getProjection();

		// Convert Points to on screen location
		Point p1 = new Point();
		Point p2 = new Point();
		projection.toPixels(mGp1, p1);
		projection.toPixels(mGp2, p2);

		Paint areaPaint = new Paint();

		areaPaint.setStyle(Paint.Style.FILL);
		areaPaint.setColor(Color.WHITE);
		areaPaint.setStrokeWidth(0);
		areaPaint.setAntiAlias(true);
		areaPaint.setAlpha(60);
		mCanvas.drawRect((float) p1.x, (float) p1.y, (float) p2.x,
				(float) p2.y, areaPaint);

		areaPaint.setStyle(Paint.Style.STROKE);
		areaPaint.setStrokeWidth(1);
		areaPaint.setColor(Color.BLACK);
		areaPaint.setAlpha(70);
		mCanvas.drawRect((float) p1.x, (float) p1.y, (float) p2.x,
				(float) p2.y, areaPaint);
	}

	@Override
	public boolean onTap(GeoPoint p, MapView m) {

		int minX = mGp1.getLatitudeE6();
		int minY = mGp1.getLongitudeE6();

		int maxX = mGp2.getLatitudeE6();
		int maxY = mGp2.getLongitudeE6();

		if (p.getLatitudeE6() >= minX && p.getLongitudeE6() >= minY
				&& p.getLatitudeE6() <= maxX && p.getLongitudeE6() <= maxY) {

			/*
			 * Context contexto = m.getContext(); Geocoder geoCoder = new
			 * Geocoder(contexto, Locale.getDefault());
			 * 
			 * List<Address> addresses; try { addresses =
			 * geoCoder.getFromLocation(p.getLatitudeE6() / 1E6,
			 * p.getLongitudeE6() / 1E6, 1);
			 * 
			 * String add = ""; if (addresses.size() > 0) { for (int i = 0; i <
			 * addresses.get(0) .getMaxAddressLineIndex(); i++) add +=
			 * addresses.get(0).getAddressLine(i) + "\n"; }
			 */
			Toast.makeText(m.getContext(), "Area #" + id, Toast.LENGTH_SHORT)
					.show();

			/*
			 * } catch (IOException e) { // TODO Auto-generated catch block
			 * e.printStackTrace(); }
			 */

			return true;
		}

		return false;
	}
}
