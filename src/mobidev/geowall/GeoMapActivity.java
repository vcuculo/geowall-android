package mobidev.geowall;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class GeoMapActivity extends MapActivity {

	public GestureDetector data;
	private MapView mapView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.maplayout);
	
		mapView = (MapView) findViewById(R.id.mapview);

		data = new GestureDetector(new MapGestureDetector(mapView));
		data.setIsLongpressEnabled(true);
		
		mapView.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return data.onTouchEvent(event);
			}
		});
		
		LocationManager locationManager = (LocationManager) this
				.getSystemService(Context.LOCATION_SERVICE);
		Location lastKnownLocation = locationManager
				.getLastKnownLocation(LocationManager.GPS_PROVIDER);

		if (lastKnownLocation != null)
			PositionView.drawMyPosition(PositionView.location2geopoint(lastKnownLocation), mapView);

		PositionController locationListener = new PositionController(mapView, this);

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
