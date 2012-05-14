package mobidev.geowall;

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
	
	private float x1,y1,x2,y2;
	private GeoPoint p1=null,p2=null;
	private MapView mv = null;
	private Paint paint = new Paint();
	private boolean isUp = false;
	
	public MyAreaOverlay(MapView mapV, float x, float y){
		paint.setStrokeWidth(2.0f);
		x1 = x;
		y1 = y;
		mv = mapV;
		p1 = mapV.getProjection().fromPixels((int)x1,(int)y1);
	}
	@Override
	public boolean draw(Canvas canvas, MapView mapView, boolean shadow,
			long when) {
		
		if(p1 != null && p2 != null){
	
			Point screenPts1 = new Point();
	        mapView.getProjection().toPixels(p1, screenPts1);
	        Point screenPts2 = new Point();
	        mapView.getProjection().toPixels(p2, screenPts2);
	        
	        paint.setColor(0x4435EF56);
	        paint.setStyle(Style.FILL);
	        canvas.drawRect(screenPts1.x, screenPts1.y, screenPts2.x, screenPts2.y, paint);
			paint.setColor(0x88158923);
			paint.setStyle(Style.STROKE);
			canvas.drawRect(screenPts1.x, screenPts1.y, screenPts2.x, screenPts2.y, paint);
		}
		return true;
	}
}
