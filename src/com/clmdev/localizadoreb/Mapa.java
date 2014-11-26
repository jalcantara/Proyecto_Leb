package com.clmdev.localizadoreb;

import java.io.Console;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.renderscript.Double4;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

import com.clmdev.entidades.Servicio;
import com.clmdev.reutilizables.Util;
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
	ArrayList<Servicio> lstServicios;

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
			//llevarLocalizacion();
			googleMap.setOnCameraChangeListener(new OnCameraChangeListener() {
				@Override
				public void onCameraChange(CameraPosition cameraPosition) {
					// Make a web call for the locations
					LatLng latLng = googleMap.getCameraPosition().target;
					/*//Log.e("getCameraPosition().target", latLng.toString());
					Log.e("hashCode",cameraPosition.hashCode()+"");
					//Log.e("googleMap.getMyLocation().getLatitude()",googleMap.getMyLocation().getLatitude()+"");
					Log.e("getMaxZoomLevel",googleMap.getMaxZoomLevel()+"");
					Log.e("getProjection",googleMap.getProjection()+"");*/
					
					/*
					 * CameraPosition cameraPosition = new
					 * CameraPosition.Builder()
					 * .target(latLng).zoom(15).build();
					 * googleMap.animateCamera(
					 * CameraUpdateFactory.newCameraPosition(cameraPosition));
					 */
					String url = "http://192.168.0.46/proyectoleb_ws/localizador/result/lat/"
							+ latLng.latitude
							+ "/lon/"
							+ latLng.longitude
							+ "/format/json";
					new ReadCualidadesJSONFeedTask().execute(url);
				}
			});

			// llenarMapa();
			/*
			 * googleMap.setOnMapClickListener(new OnMapClickListener() {
			 * 
			 * @Override public void onMapClick(LatLng latLng) { LatLng lt=
			 * googleMap.getCameraPosition().target;
			 * Log.e("getCameraPosition().target",lt.toString()); CameraPosition
			 * cameraPosition = new CameraPosition.Builder()
			 * .target(latLng).zoom(15).build();
			 * googleMap.animateCamera(CameraUpdateFactory
			 * .newCameraPosition(cameraPosition));
			 * 
			 * String
			 * url="http://192.168.0.46/proyectoleb_ws/localizador/result/lat/"
			 * +latLng.latitude+"/lon/" +latLng.longitude+"/format/json"; new
			 * ReadCualidadesJSONFeedTask().execute(url); } });
			 */

			/*
			 * googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom( new
			 * LatLng(location[0], location[1]), 7)); googleMap.addCircle(new
			 * CircleOptions().center(new LatLng(location[0], location[1]))
			 * .radius(5000) .strokeColor(Color.RED)
			 * 
			 * .fillColor(Color.RED));
			 */

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

	void getCurrentLocation() {
		Location myLocation = googleMap.getMyLocation();
		if (myLocation != null) {
			double dLatitude = myLocation.getLatitude();
			double dLongitude = myLocation.getLongitude();
			Log.i("APPLICATION", " : " + dLatitude);
			Log.i("APPLICATION", " : " + dLongitude);
			googleMap.addMarker(new MarkerOptions()
					.position(new LatLng(dLatitude, dLongitude))
					.title("My Location")
					.icon(BitmapDescriptorFactory
							.fromResource(R.drawable.ic_launcher)));
			googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
					dLatitude, dLongitude), 8));

		} else {
			Toast.makeText(this, "Unable to fetch the current location",
					Toast.LENGTH_SHORT).show();
		}

	}

	private void llevarLocalizacion() {
		LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		String provider = service.getBestProvider(criteria, false);

		Location location = service.getLastKnownLocation(provider);
		LatLng userLocation = new LatLng(location.getLatitude(),
				location.getLongitude());
		CameraPosition cameraPosition = new CameraPosition.Builder()
				.target(userLocation).zoom(15).build();
		googleMap.animateCamera(CameraUpdateFactory
				.newCameraPosition(cameraPosition));
	}

	private void llenarMapaOnClick() {
		googleMap.clear();
		for (int i = 0; i < lstServicios.size(); i++) {
			LatLng latLng = new LatLng(lstServicios.get(i).getLatitud(),
					lstServicios.get(i).getLongitud());
			MarkerOptions markerOptions = new MarkerOptions();
			markerOptions
					.position(new LatLng(latLng.latitude, latLng.longitude));
			markerOptions.title(lstServicios.get(i).getLatitud() + " : "
					+ lstServicios.get(i).getLongitud());
			// googleMap.clear();
			// googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
			googleMap.addMarker(markerOptions);
			Log.e("desde", lstServicios.get(i).getLatitud() + " : "
					+ lstServicios.get(i).getLongitud());
		}

	}

	private void llenarMapa() {
		// lets place some 10 random markers
		double[] location = { -8.0743, -79.006653 };
		// Adding a marker
		MarkerOptions marker = new MarkerOptions()
				.position(new LatLng(location[0], location[1]))
				.title("¡Hola! No sé que hago aquí pero aquí estoy :(")
				.snippet("This is my test app")
				.draggable(true)
				.icon(BitmapDescriptorFactory
						.fromResource(R.drawable.ic_launcher));
		googleMap.addMarker(marker);

		CameraPosition cameraPosition = new CameraPosition.Builder()
				.target(new LatLng(location[0], location[1])).zoom(15).build();
		googleMap.animateCamera(CameraUpdateFactory
				.newCameraPosition(cameraPosition));
	}

	private class ReadCualidadesJSONFeedTask extends
			AsyncTask<String, Void, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			Util.MostrarDialog(Mapa.this);
		}

		protected String doInBackground(String... urls) {
			return Util.readJSONFeed(urls[0], getApplicationContext());
		}

		protected void onPostExecute(String result) {
			try {
				JSONArray jsonArray = new JSONArray(result);
				JSONObject datos = new JSONObject();
				lstServicios = new ArrayList<Servicio>();
				LatLng latLng = null;
				googleMap.clear();
				for (int i = 0; i < jsonArray.length(); i++) {
					Log.e("desde", jsonArray.get(i).toString());
					datos = jsonArray.getJSONObject(i);
					latLng = new LatLng(datos.getDouble("lat"),
							datos.getDouble("lon"));
					MarkerOptions markerOptions = new MarkerOptions();
					markerOptions.position(latLng);
					markerOptions.title(datos.getDouble("lat") + " : "
							+ datos.getDouble("lon"));
					// googleMap.clear();

					googleMap.addMarker(markerOptions);
					/*
					 * Servicio c = new Servicio(); datos =
					 * jsonArray.getJSONObject(i); c.setId(datos.getInt("id"));
					 * c.setLatitud(datos.getDouble("lat"));
					 * c.setLongitud(datos.getDouble("lon"));
					 * listaCualidades.add(c);
					 */

				}

			} catch (Exception e) {
				Toast.makeText(
						getApplicationContext(),
						"No se pudieron obtener datos del servidor: Lista de Cualidades",
						Toast.LENGTH_LONG).show();

			}
			Util.cerrarDialogLoad();
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
