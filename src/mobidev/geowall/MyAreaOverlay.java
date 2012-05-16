package mobidev.geowall;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

public class MyAreaOverlay extends Overlay {

	final static int LON_FACTOR = (int) (1 * 1E4);
	final static int LAT_FACTOR = (int) (0.5 * 1E4);
	private GeoPoint mGp1, mGp2;

	public MyAreaOverlay(GeoPoint gp) {
		Log.i("INFO", (gp.getLatitudeE6()) + " - " + gp.getLongitudeE6());

		int lat = gp.getLatitudeE6() / (int) 1E4;
		int lon = gp.getLongitudeE6() / (int) 1E4;

		mGp1 = new GeoPoint(((Math.round(lat)) * 10000),
				((Math.round(lon)) * 10000));

		Log.i("INFO_ROUNDED",
				(mGp1.getLatitudeE6()) + " - " + mGp1.getLongitudeE6());

		mGp2 = new GeoPoint(mGp1.getLatitudeE6() + LAT_FACTOR,
				mGp1.getLongitudeE6() + LON_FACTOR);
	}

	@Override
	public void draw(Canvas canvas, MapView mapView, boolean shadow) {
		super.draw(canvas, mapView, shadow);

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
		canvas.drawRect((float) p1.x, (float) p1.y, (float) p2.x, (float) p2.y,
				areaPaint);

		areaPaint.setStyle(Paint.Style.STROKE);
		areaPaint.setStrokeWidth(1);
		areaPaint.setColor(Color.BLACK);
		areaPaint.setAlpha(70);
		canvas.drawRect((float) p1.x, (float) p1.y, (float) p2.x, (float) p2.y,
				areaPaint);
	}
}
