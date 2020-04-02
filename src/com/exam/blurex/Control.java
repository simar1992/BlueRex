package com.exam.blurex;

import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Control extends Activity {
	
	 
	  String message;
	  private static final int REQUEST_ENABLE_BT = 1;
	  private BluetoothAdapter btAdapter = null;
	  private BluetoothSocket btSocket = null;
	  private OutputStream outStream = null;
	  private String TAG="CONTROL";

	  private static final UUID MY_UUID =
		      UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
	 
	 private static String address = "00:14:01:21:28:01";

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {									//on Create
		super.onCreate(savedInstanceState);
		setContentView(R.layout.device_control);
		
		btAdapter = BluetoothAdapter.getDefaultAdapter();
		BluetoothDevice device = btAdapter.getRemoteDevice(address);
		checkBTState();
		
		try {
			  btSocket = device.createRfcommSocketToServiceRecord(MY_UUID);
		    }
		catch (IOException e)
			{
		    	Log.e(TAG, "----------CRAHSED WHILE CREATING RFCOMM SOCKET----------");
		    	errorExit("Fatal Error", "In onResume() and socket create failed: " + e.getMessage() + ".");
		    }

	    
		try{
			if(btAdapter.getState()==REQUEST_ENABLE_BT)
			btAdapter.cancelDiscovery();
			Log.i(TAG, "-------Connecting to Remote--------");
		}
		catch(Exception iException)
		{
			Log.e(TAG, "----------CRAHSED WHILE Cancelling Discovery----------");
		}
	    
	    
	    
		
		try {
		      btSocket.connect();
		      Log.i(TAG, "-----------Connection established and data link opened-----------");
		    } 
		catch (IOException e) 
		    	{
		    		try {
		    			btSocket.close();
		    			}
		    		catch (IOException e2)
		    			{
		    			errorExit("Fatal Error", "In onResume() and unable to close socket during connection failure" + e2.getMessage() + ".");
		    			}
		    	}
		
		try {
		      outStream = btSocket.getOutputStream();
		    }
		catch (IOException e)
			{
		      errorExit("Fatal Error", "In onResume() and output stream creation failed:" + e.getMessage() + ".");
		    }
		
		
//------------------------------Button declaration and initialisation--------------------------------------------------------------------		
		
		Button up=(Button)findViewById(R.id.up_button);
		Button left=(Button)findViewById(R.id.left_button);
		Button right=(Button)findViewById(R.id.right_button);
		Button stop=(Button)findViewById(R.id.stop_button);
		Button down=(Button)findViewById(R.id.down_button);
		
		
		up.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				message="1";
				
				try {
					//CT.write(message.getBytes());
					byte[] msgBuffer = message.getBytes();
					outStream.write(msgBuffer);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					Log.e(TAG, "---------Crashed in CONTROL JAVA FILE while sending 1-----------");
				}
				
//				try{
//					new Thread(){
//					public void run(){
//						MediaPlayer mp=MediaPlayer.create(Control.this, R.raw.buttonsound);
//						mp.setOnCompletionListener(new OnCompletionListener() {
//							
//							@Override
//							public void onCompletion(MediaPlayer mp) {
//								// TODO Auto-generated method stub
//								mp.release();
//							}
//						});
//						mp.start();
//					}
//				}.start();
//				}
//				catch(Exception i)
//				{
//					Toast.makeText(getApplicationContext(), "MEDIA PLAYER DISABLED BY USER", Toast.LENGTH_SHORT).show();
//				}
			
			}
		});
		
		left.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				message="2";
				
				try {
					byte[] msgBuffer = message.getBytes();
					outStream.write(msgBuffer);
				}
				catch (IOException e)
				{
					// TODO Auto-generated catch block
					Log.e(TAG, "---------Crashed in CONTROL JAVA FILE while sending 2-----------");
				}
				
			}
		});

		right.setOnClickListener(new View.OnClickListener() {
	
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				message="4";
				
				try {
					byte[] msgBuffer = message.getBytes();
					outStream.write(msgBuffer);
				}
				catch (IOException e)
				{
					// TODO Auto-generated catch block
					Log.e(TAG, "---------Crashed in CONTROL JAVA FILE while sending 4-----------");
		
				}

			}
		});

		stop.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				message="3";
				
				try {
					byte[] msgBuffer = message.getBytes();
					outStream.write(msgBuffer);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					Log.e(TAG, "---------Crashed in CONTROL JAVA FILE while sending 3-----------");
		
				}
				
			}
		});
		
		down.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				message="5";
				
				try {
					byte[] msgBuffer = message.getBytes();
					outStream.write(msgBuffer);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					Log.e(TAG, "---------Crashed in CONTROL JAVA FILE while sending 5-----------");
		
				}
			}
		});

		
		
	}
	
	
	 @Override
	  public void onPause() {
	    super.onPause();

	    Log.i(TAG, "---------In onPause()---------");

	    if (outStream != null) {
	      try {
	        outStream.flush();
	      	  }
	      catch (IOException e) 
	      	{
	        errorExit("Fatal Error", "In onPause() and failed to flush output stream: " + e.getMessage() + ".");
	        Log.e(TAG, "--------Crahsed in Flushing outStream------------");
	      	}
	    }

	    try {
	    		btSocket.close();
	    	}
	    catch (IOException e2)
	    	{
	    		errorExit("Fatal Error", "In onPause() and failed to close socket." + e2.getMessage() + ".");
	    	}
	  }
	 
	 
	 
	 
	  private void checkBTState() {

		  	if(btAdapter==null)
		  	{ 
		      errorExit("Fatal Error", "Bluetooth Not supported. Aborting.");
		      Toast.makeText(getApplicationContext(), "PHONE DOES NOT SUPPORT BLUETOOTH", Toast.LENGTH_SHORT).show();
		    }
		  	else
		  	{
		      if (btAdapter.isEnabled())
		      {
		        Log.i(TAG, "----------Bluetooth enabled  SUCCESSFULLY-------");
		        Toast.makeText(getApplicationContext(), "BLUETOOTH already enabled", Toast.LENGTH_SHORT).show();
		      }
		      else
		      {
		    	  Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
		    	  startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
		    	  
		      }
		    }
		  }	
	  
	  
	  private void errorExit(String title, String message){
		   Toast.makeText(getBaseContext(),  title + " - " + message, Toast.LENGTH_SHORT).show();
		   finish();
		  }
	 
	 

	public void cancel() {
        try {
            btSocket.close();
        	}
        catch (IOException e) 
        { 
        	Log.e(TAG, "---------CRASHED WHILE CLOSING BT SOCKET IN CANCEL()--------");
        }
    }
	
	public Control() {
		// TODO Auto-generated constructor stub
		
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		cancel();
		super.onDestroy();
	}
	
	
	
}
