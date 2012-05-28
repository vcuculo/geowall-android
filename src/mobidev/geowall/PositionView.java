package mobidev.geowall;

import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.util.Log;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class PositionView {
	/* icon position in overlays list */
	static final int ICON_POSITION = 0;
	/* areas positions goes from 1 to 10 */
	
	static final int ICON_REF = R.drawable.ic_launcher;
	
	static public void drawMyPosition(GeoPoint mGp, MapView map) {
		
     	GeoPoint point = mGp;
		MapView mapview = map;
		
		/* center Map */
		centerMap(point, map);
		/* update near areas */
		drawNearArea(point, map);
		
		List<Overlay> mapOverlays = mapview.getOverlays();
		Context mContext = map.getContext();
		Drawable icon = mContext.getResources().getDrawable(ICON_REF);
		
		/* create overlay with my position */
		MyItemizedOverlay itemizedoverlay = new MyItemizedOverlay(icon, mContext);
		OverlayItem overlayitem = new OverlayItem(point, "Geowall",
				point.getLatitudeE6() + " - " + point.getLongitudeE6());

		itemizedoverlay.addOverlay(overlayitem);
		mapOverlays.add(itemizedoverlay);

	}

	static private void drawNearArea(GeoPoint mGp, MapView map) {
		double lat, lon;
		int latfact, lonfact;
		GeoPoint mGeo;
		MapView mapview = map;
		List<Overlay> mapOverlays = mapview.getOverlays();
	
		/* update my position removing old one */
		if (!mapOverlays.isEmpty())
			mapOverlays.clear();
					
		/* 
		 * Areas are drawn as follow, #4 is my current area.
		 *  --- --- ---
		 * | 0 | 1 | 2 |
		 *  --- --- ---
		 * | 3 | 4 | 5 |
		 *  --- --- ---
		 * | 6 | 7 | 8 |
		 *  --- --- ---
		 */
		
		for (int i = 0; i < 9; i++) {
			
			lonfact = ((i % 3) - 1);
			latfact = (-(i / 3) + 1); 
			
			Log.i("LONFACT", Double.toString(lonfact * 1E4));
			Log.i("LATFACT", Double.toString(latfact * 1E4));
			
			lon = (mGp.getLongitudeE6()) + (int) (lonfact * MyAreaOverlay.LON_FACTOR);
			lat = (mGp.getLatitudeE6()) + (int) (latfact * MyAreaOverlay.LAT_FACTOR);
		
			mGeo = new GeoPoint((int)lat, (int)lon);

			MyAreaOverlay areaoverlay = new MyAreaOverlay(mGeo, i);
			mapOverlays.add(i, areaoverlay);
		}
	}

	static public GeoPoint location2geopoint(Location loc) {
    	return new GeoPoint((int)(loc.getLatitude() * 1E6), (int)(loc.getLongitude() * 1E6));
	}
	
	static public void centerMap(GeoPoint gp, MapView map) {
		MapController mapc = map.getController();
		mapc.animateTo(gp);
		mapc.setZoom(15);
	}	
	
}
