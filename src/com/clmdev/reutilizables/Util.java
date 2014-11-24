package com.clmdev.reutilizables;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

public class Util {
	
	public static ProgressDialog dialog;
	
	public static String readJSONFeed(String url, Context context) {
		StringBuilder stringBuilder = new StringBuilder();
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		try {
			HttpResponse response = httpClient.execute(httpGet);
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			Log.e("Code",statusCode+"");
			if (statusCode == 200) {
				HttpEntity entity = response.getEntity();
				InputStream inputStream = entity.getContent();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(inputStream,"utf-8"));
				String line;
				while ((line = reader.readLine()) != null) {
					stringBuilder.append(line);
				}
				inputStream.close();
			} else {
				Log.e("Error readJSONFeed", "No se descargaron los datos del servidor");
			}
		} catch (Exception e) {
			Log.e("Error readJSONFeed",
					"No se descargaron los datos del servidor ");
		}
		return stringBuilder.toString();
	}
	
	public static void MostrarDialog(Context contexto) {
		dialog = new ProgressDialog(contexto);
		dialog.setMessage("Cargando, por favor espere");
		dialog.show();
		dialog.setCancelable(false);
	}

	public static void cerrarDialogLoad() {
		dialog.dismiss();
	}
}
