package com.exam.blurex;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;
import android.widget.Toast;

public class Connection extends Activity{
	//private static final String TAG = "SIMAR MANN SINGH";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
		 
		final Switch mf=(Switch)findViewById(R.id.toggleSwitch1);
		
		mf.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton ButtonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked)
				{
//					Intent getVisible = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
//					startActivityForResult(getVisible, 0);
					if(mf.isEnabled()==true)
			        {
			            //mf.setTextSize(13);
			        	Intent getVisible = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
			  	    	startActivityForResult(getVisible, 0);
			  	    	
			  	    }
					
					
				}
				else
				{
					Toast.makeText(getApplicationContext(), "Discoverability Turned OFF", Toast.LENGTH_SHORT).show();
				}
				
			}
		});

		
        
        
	
		
}
}