package com.clmdev.localizadoreb;

import java.util.ArrayList;

import com.clmdev.entidades.menu;
import com.clmdev.reutilizables.adaptador_menu;
import com.google.android.gms.drive.internal.GetDriveIdFromUniqueIdentifierRequest;

import android.app.Activity;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.HeterogeneousExpandableList;
import android.widget.ListView;


public class MainActivity extends Activity {
	
	private DrawerLayout NavDrawerLayout;
	private ListView NavList;
	private String[] titulos;
	private ArrayList<menu> NavItems;
	private TypedArray NavIcons;
	adaptador_menu NavAdapter;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NavDrawerLayout=(DrawerLayout) findViewById(R.id.drawer_layout);
        NavList=(ListView)findViewById(R.id.left_drawer);
        
        View header=getLayoutInflater().inflate(R.layout.header, null);
        NavList.addHeaderView(header);
        NavIcons=getResources().obtainTypedArray(R.array.navigation_icons);
        titulos=getResources().getStringArray(R.array.nav_options);
        NavItems=new ArrayList<menu>();
        NavItems.add(new menu(titulos[0], NavIcons.getResourceId(0,-1)));
        NavItems.add(new menu(titulos[1], NavIcons.getResourceId(1,-1)));
        NavItems.add(new menu(titulos[2], NavIcons.getResourceId(2,-1)));
        NavItems.add(new menu(titulos[3], NavIcons.getResourceId(3,-1)));
        NavItems.add(new menu(titulos[4], NavIcons.getResourceId(4,-1)));
        NavItems.add(new menu(titulos[5], NavIcons.getResourceId(5,-1)));
        NavItems.add(new menu(titulos[6], NavIcons.getResourceId(6,-1)));
        NavAdapter=new adaptador_menu(this,NavItems);
        NavList.setAdapter(NavAdapter);
        
        
        
        
        findViewById(R.id.button1).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {				
				Intent i = new Intent(getApplicationContext(),
						Mapa.class);
				startActivity(i);
			}
		});
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
