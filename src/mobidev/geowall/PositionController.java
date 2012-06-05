package mobidev.geowall;

import com.google.android.maps.MapView;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

public class PositionController implements LocationListener {

	private Location bestPosition;
	private MapView mapview;
	private Drawable icon;
	private Context cont;
	final static float DISTANZA_BACHECA = 100;
	
	
	public PositionController(MapView map, Drawable icon, Context context) {
		this.icon = icon;
		this.mapview = map;
		this.cont = context;
	}

	@Override
	public void onLocationChanged(Location arg0) {
		if (isBetterLocation(arg0)){
			bestPosition = arg0;
			PositionView.drawMyPosition(arg0, mapview, icon, cont);
		}
	}

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub

	}

	private boolean isBetterLocation(Location loc) {		
		if (bestPosition == null)
			return true;
		Log.i("UPDATE POSIZIONE", Float.toString(loc.distanceTo(bestPosition)));

		if (loc.distanceTo(bestPosition) > DISTANZA_BACHECA)
			return true;
		else
			return false;
		
	}


}
