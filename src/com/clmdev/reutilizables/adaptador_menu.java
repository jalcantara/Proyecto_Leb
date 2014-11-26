package com.clmdev.reutilizables;

import java.util.ArrayList;

import com.clmdev.entidades.menu;
import com.clmdev.localizadoreb.R;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class adaptador_menu extends BaseAdapter {
	
	private Activity activity;
	ArrayList<menu> arrayitems;
	
	
	
	public adaptador_menu(Activity activity, ArrayList<menu> listarray) {
		super();
		this.activity = activity;
		this.arrayitems = listarray;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return arrayitems.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return arrayitems.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
	public static class File{
		TextView  titulo_itm;
		ImageView icono;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		File view;
		LayoutInflater inflator=activity.getLayoutInflater();
		if(convertView==null){
			view=new File();
			menu itm=arrayitems.get(position);
			convertView =inflator.inflate(R.layout.itm, null);

			view.titulo_itm=(TextView)convertView.findViewById(R.id.tittle_item);
			view.titulo_itm.setText(itm.getTitulo());
			view.icono=(ImageView)convertView.findViewById(R.id.icon);
			view.icono.setImageResource(itm.getImg());
			
			convertView.setTag(view);
		}else {
			view=(File)convertView.getTag();
		}
		
		return convertView;
	}
	
}
