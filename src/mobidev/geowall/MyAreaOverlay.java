package mobidev.geowall;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

public class MyAreaOverlay extends Overlay {

	final static int LON_FACTOR = (int) (1 * 1E4);
	final static int LAT_FACTOR = (int) (5 * 1E3);

	private GeoPoint myPosition, outsidePosition;
	private GeoPoint[][] areas;
	private boolean[] clicked;
	private MapView mMap;
	private GestureDetector mGestureDetector;
	private boolean longpress = false;

	public MyAreaOverlay(GeoPoint gp, MapView map) {
		myPosition = gp;
		mMap = map;
		clicked = new boolean[9];
		areas = new GeoPoint[9][2];
		mGestureDetector = new GestureDetector(new MapGestureDetector(this));
	}

	public void longPress(MotionEvent e) {
		outsidePosition = mMap.getProjection().fromPixels((int) e.getX(),
				(int) e.getY());

		if (isInsideArea(outsidePosition) == -1) {
			longpress = true;
			mMap.invalidate();
		}
	}

	public void onTap(MotionEvent e) {
		GeoPoint p = mMap.getProjection().fromPixels((int) e.getX(),
				(int) e.getY());

		int i = isInsideArea(p);

		if (i != -1) {
			clicked[i] = false;
			mMap.invalidate();
			// TODO mostra bacheca
		}
	}

	public void onDown(MotionEvent e) {
		GeoPoint p = mMap.getProjection().fromPixels((int) e.getX(),
				(int) e.getY());

		int i = isInsideArea(p);

		if (i != -1) {
			clicked[i] = true;
			mMap.invalidate();
		}
	}

	private void drawAreas(Canvas canvas, MapView mapView) {
		Canvas mCanvas = canvas;
		Projection projection = mapView.getProjection();
		int latfact, lonfact;
		double lat, lon;

		for (int i = 0; i < 9; i++) {

			latfact = (-(i / 3) + 1) * LAT_FACTOR;
			lonfact = ((i % 3) - 1) * LON_FACTOR;

			lat = (myPosition.getLatitudeE6() + latfact) / LAT_FACTOR;
			lon = (myPosition.getLongitudeE6() + lonfact) / LON_FACTOR;

			areas[i][0] = new GeoPoint((int) (Math.round(lat)) * LAT_FACTOR,
					(int) (Math.round(lon)) * LON_FACTOR);

			areas[i][1] = new GeoPoint(
					areas[i][0].getLatitudeE6() + LAT_FACTOR,
					areas[i][0].getLongitudeE6() + LON_FACTOR);

			// Convert Points to on screen location
			Point p1 = new Point();
			Point p2 = new Point();
			projection.toPixels(areas[i][0], p1);
			projection.toPixels(areas[i][1], p2);

			Paint areaPaint = new Paint();

			if (!clicked[i]) {
				areaPaint.setStyle(Paint.Style.FILL);
				areaPaint.setColor(Color.WHITE);
				areaPaint.setStrokeWidth(0);
				areaPaint.setAntiAlias(true);
				areaPaint.setAlpha(60);
				mCanvas.drawRect((float) p1.x, (float) p1.y, (float) p2.x,
						(float) p2.y, areaPaint);

			} else {
				areaPaint.setStyle(Paint.Style.FILL);
				areaPaint.setColor(Color.RED);
				areaPaint.setStrokeWidth(0);
				areaPaint.setAntiAlias(true);
				areaPaint.setAlpha(70);
				mCanvas.drawRect((float) p1.x, (float) p1.y, (float) p2.x,
						(float) p2.y, areaPaint);
			}

			areaPaint.setStyle(Paint.Style.STROKE);
			areaPaint.setStrokeWidth(1);
			areaPaint.setColor(Color.BLACK);
			areaPaint.setAlpha(70);
			mCanvas.drawRect((float) p1.x, (float) p1.y, (float) p2.x,
					(float) p2.y, areaPaint);
		}
	}

	private void drawOutsideArea(Canvas canvas, MapView mapView) {
		Canvas mCanvas = canvas;
		Projection projection = mapView.getProjection();
		double lat, lon;
		GeoPoint mGp1 = outsidePosition;
		GeoPoint mGp2;

		lat = mGp1.getLatitudeE6() / LAT_FACTOR;
		lon = mGp1.getLongitudeE6() / LON_FACTOR;

		mGp1 = new GeoPoint((int) (Math.round(lat)) * LAT_FACTOR,
				(int) (Math.round(lon)) * LON_FACTOR);

		mGp2 = new GeoPoint(mGp1.getLatitudeE6() + LAT_FACTOR,
				mGp1.getLongitudeE6() + LON_FACTOR);
		// Convert Points to on screen location
		Point p1 = new Point();
		Point p2 = new Point();
		projection.toPixels(mGp1, p1);
		projection.toPixels(mGp2, p2);

		Paint areaPaint = new Paint();
		areaPaint.setStyle(Paint.Style.FILL);
		areaPaint.setColor(Color.RED);
		areaPaint.setStrokeWidth(0);
		areaPaint.setAntiAlias(true);
		areaPaint.setAlpha(70);
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
	public void draw(Canvas canvas, MapView mapView, boolean shadow) {
		super.draw(canvas, mapView, shadow);
		Canvas mCanvas = canvas;
		MapView mMap = mapView;

		if (longpress)
			drawOutsideArea(mCanvas, mMap);

		drawAreas(mCanvas, mMap);
	}

	@Override
	public boolean onTouchEvent(MotionEvent e, MapView mapView) {
		if (e.getAction() == MotionEvent.ACTION_UP) {
			longpress = false;
			mMap.invalidate();
		}
		mGestureDetector.onTouchEvent(e);
		return super.onTouchEvent(e, mapView);
	}

	private int isInsideArea(GeoPoint p) {
		int minX, maxX, minY, maxY, i;

		if (areas != null && areas.length != 0)
			for (i = 0; i < 9; i++) {
				minX = areas[i][0].getLatitudeE6();
				minY = areas[i][0].getLongitudeE6();
				maxX = areas[i][1].getLatitudeE6();
				maxY = areas[i][1].getLongitudeE6();

				if (p.getLatitudeE6() >= minX && p.getLongitudeE6() >= minY
						&& p.getLatitudeE6() <= maxX
						&& p.getLongitudeE6() <= maxY)
					return i;
			}
		return -1;
	}
}
