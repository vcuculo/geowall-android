package mobidev.geowall;

import android.R.color;
import android.content.Context;
import android.graphics.Canvas;
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
	private Paint paint = new Paint();
	
	public MyAreaOverlay(MapView map, GeoPoint gp){
		mv = map;
		mGp1 = gp;
		mGp2 = new GeoPoint(mGp1.getLatitudeE6() - 1, mGp1.getLongitudeE6() - 1);
	}
	
	@Override
	public void draw(Canvas canvas, MapView mapView, boolean shadow) {
	
			super.draw (canvas, mapView, shadow);
			Projection projection = mapView.getProjection();

			//Convert Points to on screen location
			Point p1 = new Point();
			Point p2 = new Point();
			projection.toPixels(mGp1, p1);
			projection.toPixels(mGp2, p2);
			
	        paint.setColor(color.black);
	        paint.setStyle(Style.FILL);
	        canvas.drawRect(p1.x, p1.y, p2.x, p2.y, paint);
		/*	paint.setColor(color.white);
			paint.setStyle(Style.STROKE);
			canvas.drawRect(p1.x, p1.y, p2.x, p2.y, paint);*/
	}
}
