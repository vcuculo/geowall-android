package mobidev.geowall;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;

import android.content.ClipData.Item;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class GeoMapActivity extends MapActivity {

	public GestureDetector data;
	private MapView mapView;

	private String USER_PREFERENCES = "UserPreference";
	SharedPreferences setting;
	ImageView accountImage;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.maplayout);
		accountImage=(ImageView) findViewById(R.id.accountImage);
		

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
	
	public void onResume() {
		super.onResume();
		setting = getSharedPreferences(USER_PREFERENCES, 0);
		String photo=setting.getString("IMG", null);

		if (photo!=null) {
			accountImage.setAdjustViewBounds(true);
			accountImage.setMaxHeight(40);
			accountImage.setMaxWidth(40);
			accountImage.setImageBitmap(MediaController.decodeBase64toBitmap(photo));
			
		}

	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
	/*
	public void showWall(){
		Intent intent = new Intent(this, WallActivity.class);
		this.startActivity(intent);
	
	}
	*/
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		MenuInflater inflater= getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		Intent i;
		switch(item.getItemId()){
		case R.id.logoutMenu:
			new LogoutController(this).execute(this);
			break;
		default :
			return super.onOptionsItemSelected(item);
		}
		return true;
	}
}