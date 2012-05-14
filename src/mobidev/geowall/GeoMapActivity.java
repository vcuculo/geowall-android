package mobidev.geowall;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

public class GeoMapActivity extends MapActivity {

	private MapView mapView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.maplayout);
		mapView = (MapView) findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);

		LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		
		Drawable icon = this.getResources().getDrawable(R.drawable.ic_launcher);
		
		if (lastKnownLocation != null){
			PositionView.drawMyPosition(lastKnownLocation, mapView, icon, this);
			PositionView.centerMap(lastKnownLocation, mapView);

		}
		
		PositionController locationListener = new PositionController(mapView, icon, this);
		
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,	0, locationListener);

	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
}
