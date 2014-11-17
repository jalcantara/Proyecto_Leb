package com.clmdev.localizadoreb;

import java.io.Console;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
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
						.target(new LatLng(location[0], location[1])).zoom(15)
						.build();
				googleMap.animateCamera(CameraUpdateFactory
						.newCameraPosition(cameraPosition));
				googleMap.setOnMapClickListener(new OnMapClickListener() {
					 
		            @Override
		            public void onMapClick(LatLng latLng) {
		            	
		            	Log.e("desde",latLng+"");
		            	
		                // Creating a marker
		                MarkerOptions markerOptions = new MarkerOptions();
		 
		                // Setting the position for the marker
		                markerOptions.position(latLng);
		 
		                // Setting the title for the marker.
		                // This will be displayed on taping the marker
		                markerOptions.title(latLng.latitude + " : " + latLng.longitude);
		 
		                // Clears the previously touched position
		                googleMap.clear();
		 
		                // Animating to the touched position
		                googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
		 
		                // Placing a marker on the touched position
		                googleMap.addMarker(markerOptions);
		            }
		        });
				
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

	
}


