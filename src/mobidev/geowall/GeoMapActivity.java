package mobidev.geowall;

import java.util.List;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class GeoMapActivity extends MapActivity implements LocationListener {

	private MapView mapView;
	private Location bestPosition;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.maplayout);
		mapView = (MapView) findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);

		LocationManager locationManager = (LocationManager) this
				.getSystemService(Context.LOCATION_SERVICE);

		Location lastKnownLocation = locationManager
				.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		if (lastKnownLocation != null)
			drawMyPosition(lastKnownLocation);
		locationManager.requestLocationUpdates(
				LocationManager.NETWORK_PROVIDER, 0, 0, this);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
				0, this);

	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	public void drawMyPosition(Location loc) {

		List<Overlay> mapOverlays = mapView.getOverlays();
		Drawable drawable = this.getResources().getDrawable(
				R.drawable.ic_launcher);
		MyItemizedOverlay itemizedoverlay = new MyItemizedOverlay(drawable,
				this);
		GeoPoint point = location2geopoint(loc);
		OverlayItem overlayitem = new OverlayItem(point, "Geowall",
				"Tu sei qui!");

		itemizedoverlay.addOverlay(overlayitem);
		mapOverlays.add(itemizedoverlay);
	}

	private GeoPoint location2geopoint(Location loc) {

		Double latitude = loc.getLatitude() * 1E6;
		Double longitude = loc.getLongitude() * 1E6;

		return new GeoPoint(latitude.intValue(), longitude.intValue());
	}

	@Override
	public void onLocationChanged(Location arg0) {
		if (isBetterLocation(arg0))
			drawMyPosition(arg0);

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
		if (loc.getTime() > bestPosition.getTime())
			return true;
		else
			return false;
	}

}
