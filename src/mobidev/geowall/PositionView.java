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
	
	static public void drawMyPosition(Location loc, MapView map, Drawable icon) {

		MapView mapview = map;
		List<Overlay> mapOverlays = mapview.getOverlays();
		Context mContext = map.getContext();
		
		/* update my position removing old one */
		if (!mapOverlays.isEmpty())
			mapOverlays.remove(ICON_POSITION);

		/* create overlay with my position */
		MyItemizedOverlay itemizedoverlay = new MyItemizedOverlay(icon, mContext);
		GeoPoint point = location2geopoint(loc);
		OverlayItem overlayitem = new OverlayItem(point, "Geowall",
				point.getLatitudeE6() + " - " + point.getLongitudeE6());

		itemizedoverlay.addOverlay(overlayitem);
		mapOverlays.add(ICON_POSITION, itemizedoverlay);
		/* center Map */
		centerMap(loc, map);
		/* update near areas */
		drawNearArea(loc, map);
	}

	static private void drawNearArea(Location loc, MapView map) {
		double lat, lon;
		int latfact, lonfact;
		GeoPoint mGeo;
		Location mLoc = loc;
		MapView mapview = map;
		List<Overlay> mapOverlays = mapview.getOverlays();

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
			
			lon = (mLoc.getLongitude() * 1E6) + (int) (lonfact * MyAreaOverlay.LON_FACTOR);
			lat = (mLoc.getLatitude() * 1E6) + (int) (latfact * MyAreaOverlay.LAT_FACTOR);
		
			mGeo = new GeoPoint((int)lat, (int)lon);

			MyAreaOverlay areaoverlay = new MyAreaOverlay(mGeo, i+1);
			mapOverlays.add(i+1, areaoverlay);
		}
	}

	static public GeoPoint location2geopoint(Location loc) {
    	return new GeoPoint((int)(loc.getLatitude() * 1E6), (int)(loc.getLongitude() * 1E6));
	}
	
	static public void centerMap(Location loc, MapView map) {
		MapController mapc = map.getController();
		mapc.animateTo(location2geopoint(loc));
		mapc.setZoom(15);
	}	
	
}
