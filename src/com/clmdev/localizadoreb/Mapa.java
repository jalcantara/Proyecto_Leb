package com.clmdev.localizadoreb;

import java.io.Console;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

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
import com.google.android.gms.internal.es;
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
			//llevarLocalizacion();
			googleMap.setOnCameraChangeListener(new OnCameraChangeListener() {
				@Override
				public void onCameraChange(CameraPosition cameraPosition) {
					// Make a web call for the locations
					LatLng latLng = googleMap.getCameraPosition().target;
					Log.e("LATLON", getDistance(cameraPosition.zoom)+" zoom -> "+cameraPosition.zoom);
					/*//Log.e("hashCode",cameraPosition.hashCode()+"");
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
					
					int entidad = 1;
					if(cameraPosition.zoom>=11){
						String url = "http://192.168.0.14/proyectoleb_ws/localizador/result/lat/"
								+ latLng.latitude
								+ "/lon/"
								+ latLng.longitude
								+ "/dis/"+getDistance(cameraPosition.zoom)+"/ent/"+entidad+"/format/json";
						Log.e("URL",url);
						new ReadCualidadesJSONFeedTask().execute(url);
					}
					
					
					
				}
			});
			
			googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom( new LatLng(-9.3623528, -75.9594727), 5));

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
	
	public double getDistance(double zoom){
		int promedio = (int) Math.round(zoom);
		double res=2;
		switch (promedio){
			case 18:
				res=0.3;
				break;
			case 17:
				res=0.5;
				break;
			case 16:
				res=1;
				break;
			case 15:
				res=2;
				break;
			case 14:
				res=4;
				break;
			case 13:
				res=6;
				break;
			case 12:
				res=7;
				break;
			case 11:
				res=8;
				break;
			default:
				res=2;
				break;
		}
		return res;
		
	}
	
	/*private int second=0;
	boolean ejecuto = false;
	private void hilo() {
		second=0;
		ejecuto=false;
		long UPDATE_INTERVAL = 1;
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				
				if(second>1000){					
					second=0;
					ejecuto=true;	
					super.cancel();
					Log.e("Segundo", "Procede a Ejecutarse");
				}				
				second++;				
				Log.e("Segundo", second+"");
			}
		}, 0, UPDATE_INTERVAL);
	}*/
	
	public void mostrarMapa(ArrayList<Servicio> lstServicio){
		googleMap.clear();
		for (Servicio servicio : lstServicio) {
			Log.e("Aqui",servicio.getDireccion());
			LatLng latLng = new LatLng(servicio.getLatitud(),servicio.getLongitud());
			MarkerOptions markerOptions = new MarkerOptions();
			markerOptions.position(latLng);
			markerOptions.title(servicio.getLocal());
			markerOptions.snippet(servicio.getDireccion());		
			if(servicio.getTipo()==1){
				markerOptions.icon(BitmapDescriptorFactory
						.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
			}
			if(servicio.getTipo()==2){
				markerOptions.icon(BitmapDescriptorFactory
						.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
			}
			if(servicio.getTipo()==3){
				markerOptions.icon(BitmapDescriptorFactory
						.defaultMarker(BitmapDescriptorFactory.HUE_RED));
			}
			googleMap.addMarker(markerOptions);
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
			//Util.MostrarDialog(Mapa.this);
		}

		protected String doInBackground(String... urls) {
			return Util.readJSONFeed(urls[0], getApplicationContext());
		}

		protected void onPostExecute(String result) {
			try {
				
				JSONArray jsonArray = new JSONArray(result);
				JSONObject datos = new JSONObject();
				ArrayList<Servicio> lstServicio = new ArrayList<Servicio>();				
				for (int i = 0; i < jsonArray.length(); i++) {
					datos = jsonArray.getJSONObject(i);
					Servicio servicio = new Servicio();
					servicio.setLocal(datos.getString("local"));
					servicio.setDireccion(datos.getString("direccion"));
					servicio.setLatitud(datos.getDouble("lat"));
					servicio.setTipo(datos.getInt("tipo"));
					servicio.setLongitud(datos.getDouble("lon"));
					lstServicio.add(servicio);
				}
				mostrarMapa(lstServicio);
				Log.e("res", lstServicio.get(0).getDireccion());
				super.cancel(true);
				//Util.cerrarDialogLoad();

			} catch (Exception e) {
				Toast.makeText(
						getApplicationContext(),
						"No se pudieron obtener datos del servidor",
						Toast.LENGTH_LONG).show();
				this.cancel(true);
				//Util.cerrarDialogLoad();

			}
			
			
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
