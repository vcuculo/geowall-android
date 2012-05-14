package mobidev.geowall;

import android.R.color;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.graphics.Rect;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.google.android.maps.Projection;

public class MyAreaOverlay extends Overlay {

	private MapView mv;
	private GeoPoint mGp1, mGp2;

	public MyAreaOverlay(MapView map, GeoPoint gp) {
		mv = map;
		mGp1 = gp;
		mGp2 = new GeoPoint(mGp1.getLatitudeE6() - (int)(0.5 * 1E4), mGp1.getLongitudeE6() + (int)(1 * 1E4));
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

		/*float circleRadius = 100;

		Paint innerCirclePaint;

		innerCirclePaint = new Paint();
		innerCirclePaint.setARGB(255, 255, 255, 255);
		innerCirclePaint.setAntiAlias(true);

		innerCirclePaint.setStyle(Paint.Style.FILL);

		canvas.drawCircle((float) p1.x, (float) p1.y, circleRadius,
				innerCirclePaint);
*/
		Paint rectPaint = new Paint();

		rectPaint.setColor(Color.WHITE);
		rectPaint.setAntiAlias(true);
		rectPaint.setStyle(Paint.Style.FILL);
		rectPaint.setAlpha(150);
		rectPaint.setStrokeWidth(2);
		canvas.drawRect((float)p1.x, (float)p1.y, (float)p2.x, (float)p2.y, rectPaint);
		/*rectPaint.setColor(color.white);
		rectPaint.setStyle(Style.STROKE);
		canvas.drawRect(p1.x, p1.y, p2.x, p2.y, rectPaint);*/
	}
}
