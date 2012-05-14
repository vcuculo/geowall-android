package mobidev.geowall;

import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.location.Location;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class PositionView {

	static final int ICON_POSITION = 0;

	static public void drawMyPosition(Location loc, MapView map, Drawable icon, Context context) {
		
		MapView mapview = map;
		List<Overlay> mapOverlays = mapview.getOverlays();
		if (!mapOverlays.isEmpty())
			mapOverlays.remove(ICON_POSITION);
		
		MyItemizedOverlay itemizedoverlay = new MyItemizedOverlay(icon, context);

		GeoPoint point = location2geopoint(loc);
		OverlayItem overlayitem = new OverlayItem(point, "Geowall",
				"Tu sei qui!");

		itemizedoverlay.addOverlay(overlayitem);
		mapOverlays.add(ICON_POSITION, itemizedoverlay);
		centerMap(loc, map);
	}

	static private GeoPoint location2geopoint(Location loc) {

		Double latitude = loc.getLatitude() * 1E6;
		Double longitude = loc.getLongitude() * 1E6;

		return new GeoPoint(latitude.intValue(), longitude.intValue());
	}
	
	static public void centerMap(Location loc, MapView map){
		MapController mapc = map.getController();
		mapc.animateTo(location2geopoint(loc));
	}
	
	
}
