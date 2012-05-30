package mobidev.geowall;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.GestureDetector;

public class GeoMapActivity extends MapActivity {

	public GestureDetector data;
	private MapView mapView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.maplayout);
	
		mapView = (MapView) findViewById(R.id.mapview);

		LocationManager locationManager = (LocationManager) this
				.getSystemService(Context.LOCATION_SERVICE);
		Location lastKnownLocation = locationManager
				.getLastKnownLocation(LocationManager.GPS_PROVIDER);

		if (lastKnownLocation != null)
			PositionView.drawMyPosition(PositionView.location2geopoint(lastKnownLocation), mapView);

		PositionController locationListener = new PositionController(mapView);

		locationManager.requestLocationUpdates(
				LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
				0, locationListener);
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
}
