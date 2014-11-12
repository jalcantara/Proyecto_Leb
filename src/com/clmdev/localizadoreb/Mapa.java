package com.clmdev.localizadoreb;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Mapa extends Activity {

	// Google Map
	private GoogleMap googleMap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mapa);

		try {
			// Loading map
			initilizeMap();

			// Changing map type
			googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			// googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
			// googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
			// googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
			// googleMap.setMapType(GoogleMap.MAP_TYPE_NONE);

			// Showing / hiding your current location
			googleMap.setMyLocationEnabled(true);

			// Enable / Disable zooming controls
			googleMap.getUiSettings().setZoomControlsEnabled(true);

			// Enable / Disable my location button
			googleMap.getUiSettings().setMyLocationButtonEnabled(true);

			// Enable / Disable Compass icon
			googleMap.getUiSettings().setCompassEnabled(true);

			// Enable / Disable Rotate gesture
			googleMap.getUiSettings().setRotateGesturesEnabled(true);

			// Enable / Disable zooming functionality
			googleMap.getUiSettings().setZoomGesturesEnabled(true);

			// lets place some 10 random markers
			for (int i = 0; i < 10; i++) {
				double[] location = { -8.0743, -79.006653 };
				// Adding a marker
				MarkerOptions marker = new MarkerOptions()
				.position(new LatLng(location[0], location[1]))
				.title("¡Hola! No sé que hago aquí pero aquí estoy :(")
				.snippet("This is my test app")
				.draggable(true)
				.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher));				
				googleMap.addMarker(marker);

				CameraPosition cameraPosition = new CameraPosition.Builder()
						.target(new LatLng(location[0], location[1])).zoom(25)
						.build();
				googleMap.animateCamera(CameraUpdateFactory
						.newCameraPosition(cameraPosition));
				
				/*googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                        new LatLng(location[0], location[1]), 7));
				googleMap.addCircle(new CircleOptions().center(new LatLng(location[0], location[1]))
                    .radius(5000)
                    .strokeColor(Color.RED)
                    .fillColor(Color.RED)); */

				
				
				// Move the camera to last position with a zoom level
				/*
				 * if (i == 9) { CameraPosition cameraPosition = new
				 * CameraPosition.Builder() .target(new LatLng(location[0],
				 * location[1])).zoom(15).build();
				 * 
				 * googleMap.animateCamera(CameraUpdateFactory
				 * .newCameraPosition(cameraPosition)); }
				 */
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	protected void onResume() {
		super.onResume();
		initilizeMap();
	}

	/**
	 * function to load map If map is not created it will create it for you
	 * */
	private void initilizeMap() {
		if (googleMap == null) {
			googleMap = ((MapFragment) getFragmentManager().findFragmentById(
					R.id.map)).getMap();

			// check if map is created successfully or not
			if (googleMap == null) {
				Toast.makeText(getApplicationContext(),
						"Sorry! unable to create maps", Toast.LENGTH_SHORT)
						.show();
			}
		}
	}

	/*
	 * creating random postion around a location for testing purpose only
	 */
	/*
	 * private double[] createRandLocation(double latitude, double longitude) {
	 * 
	 * return new double[] { latitude + ((Math.random() - 0.5) / 500), longitude
	 * + ((Math.random() - 0.5) / 500), 150 + ((Math.random() - 0.5) * 10) }; }
	 */
}
