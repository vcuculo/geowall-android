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
	
	private float x1,y1,x2,y2;
	private GeoPoint mGp=null,p2=null;
	private MapView mv = null;
	private Paint paint = new Paint();
	private boolean isUp = false;
	
	public MyAreaOverlay(MapView mapV, GeoPoint gp){
		mv = mapV;
		mGp = gp;
	}
	
	@Override
	public boolean draw(Canvas canvas, MapView mapView, boolean shadow,
			long when) {
	
			
			//Get map projection
			Projection projection = mapView.getProjection();

			//Convert Points to on screen location
			Point p1 = new Point();
			projection.toPixels(mGp, p1);
			
			Point p2 = new Point(p1.x + 100 , p1.y - 100);
	        
	        paint.setColor(color.black);
	        paint.setStyle(Style.FILL);
	        canvas.drawRect(p1.x, p1.y, p2.x, p2.y, paint);
			paint.setColor(color.white);
			paint.setStyle(Style.STROKE);
			canvas.drawRect(p1.x, p1.y, p2.x, p2.y, paint);
		return true;
	}
}
