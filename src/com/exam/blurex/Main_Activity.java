package com.exam.blurex;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;


public class Main_Activity extends Activity {
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {													//on Create
		super.onCreate(savedInstanceState);

		Intent in1=new Intent(Main_Activity.this,Splash.class);
		startActivity(in1);	
		
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		finish();
		super.onDestroy();
	}
}
	
