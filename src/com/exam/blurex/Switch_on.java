package com.exam.blurex;

import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class Switch_on extends Activity implements TextToSpeech.OnInitListener{
	  
	  private static final String TAG = "SWITCH-ON ACTIVITY";
	  private TextToSpeech tts_Switch_on;
	  @Override
	  public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.switch_on);
	    
	    tts_Switch_on=new TextToSpeech(this, this);									//Text to speech Implementation
	    Log.i(TAG, "---------In onCreate()-------------");
	    
	    Button Connect=(Button)findViewById(R.id.ConnectWheelchair);
	    Connect.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			    Log.i(TAG, "------Creating Intent to Control class---------");

			    Intent i=new Intent(Switch_on.this, Control.class);
			    startActivity(i);
			     
  		    
			}
		});
	    
	  }	  

	  
	  @Override
	  public void onResume() {
	    super.onResume();
	      
	   	  }

		 

//		  private void sendData(String message) {
//		    byte[] msgBuffer = message.getBytes();
//
//		    Log.d(TAG, "...Sending data: " + message + "...");
//
//		    try {
//		      outStream.write(msgBuffer);
//		    } catch (IOException e) {
//		      String msg = "In onResume() and an exception occurred during write: " + e.getMessage();
//		      if (address.equals("00:00:00:00:00:00")) 
//		        msg = msg + ".\n\nUpdate your server address from 00:00:00:00:00:00 to the correct address on line 37 in the java code";
//		      msg = msg +  ".\n\nCheck that the SPP UUID: " + MY_UUID.toString() + " exists on server.\n\n";
//		      
//		      errorExit("Fatal Error", msg);       
//		    }
//		  }
	  

	
	public Switch_on() {
	}

	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		if(tts_Switch_on!=null){
			tts_Switch_on.stop();
			tts_Switch_on.shutdown();
		}
		finish();
		super.onDestroy();
	}


	@Override
	public void onInit(int status) {
		// TODO Auto-generated method stub
		if(status==TextToSpeech.SUCCESS){
			int result=tts_Switch_on.setLanguage(Locale.US);
			if(	result==TextToSpeech.LANG_MISSING_DATA||result==TextToSpeech.LANG_NOT_SUPPORTED){
				Log.e("TEXT_TO_SPEECH", "This language is not supported");
			}
			else{
				String SAY_IT;
//				SAY_IT="Press the button below to connect wirelessly to your pre-Defined Wheelchair.";
//				tts_Switch_on.speak(SAY_IT.toString(), TextToSpeech.QUEUE_FLUSH, null);
				SAY_IT="This action requires you to turn ON your Phone's Bluetooth to wirelessly Connect to the Bluetooth slave machine. In this case, the bluetooth slave machine is the WHEELCHAIR.";
				tts_Switch_on.speak(SAY_IT.toString(), TextToSpeech.QUEUE_FLUSH,null);
			}
		
	}
	

}
}