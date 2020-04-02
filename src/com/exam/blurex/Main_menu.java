package com.exam.blurex;

import java.util.Set;

import com.exam.blurex.util.SystemUiHider;

import android.annotation.TargetApi;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class Main_menu extends Activity {
	/**
	 * Whether or not the system UI should be auto-hidden after
	 * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
	 */

	public static String EXTRA_DEVICE_ADDRESS = "device_address";
	
	private static final int REQUEST_ENABLE_BT = 1;
	BroadcastReceiver mReceiver;
	public ArrayAdapter<String> mArrayAdapter;
	BluetoothDevice device;
	private static final boolean AUTO_HIDE = true;
	
	public String Recieved=null; 
	

	/**
	 * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
	 * user interaction before hiding the system UI.
	 */
	private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

	/**
	 * If set, will toggle the system UI visibility upon interaction. Otherwise,
	 * will show the system UI visibility upon interaction.
	 */
	private static final boolean TOGGLE_ON_CLICK = true;

	/**
	 * The flags to pass to {@link SystemUiHider#getInstance}.
	 */
	private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;

	/**
	 * The instance of the {@link SystemUiHider} for this activity.
	 */
	private SystemUiHider mSystemUiHider;
	
	final BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
	final Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();


	@Override
	protected void onCreate(Bundle savedInstanceState) {																//on Create
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		final View controlsView = findViewById(R.id.fullscreen_content_controls);
		final View contentView = findViewById(R.id.fullscreen_content);

		
		//		Typeface tf=Typeface.createFromAsset(getAssets(), "fonts/Notera_PersonalUseOnly.tff");
//		b1.setTypeface(tf);
//		tV.setTypeface(tf);
		
		final ListView List=(ListView)findViewById(R.id.listView1);
		
		Button b1=(Button)findViewById(R.id.dummy_button);
		b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
//				if (mBluetoothAdapter == null) {
//					
//					Toast.makeText(getApplicationContext(), "Device does not support Bluetooth", Toast.LENGTH_SHORT).show();
//					// Device does not support Bluetooth
//				}

				 if (!mBluetoothAdapter.isEnabled()) {
				    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
				    startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
				    //device supports bluetooth but bluetooth is not enabled
				}
				
				else if (mBluetoothAdapter.isEnabled()) {
				    Toast.makeText(getApplicationContext(), "BLUETOOTH is already ON", Toast.LENGTH_SHORT).show();
				}

				
				try{
				// If there are paired devices
				if (pairedDevices.size() > 0) 
					{
				    // Loop through paired devices
				    for (BluetoothDevice device : pairedDevices) 
				    	{
				        // Add the name and address to an array adapter to show in a ListView
				         //mArrayAdapter.add("DEVICE NAME : "+ device.getName() + "\n" + "DEVICE CLASS"+device.getClass()+"DEVICE MAC ADDRESS :"+ device.getAddress());
				    	//mArrayAdapter.add(device.getName() + "\n" + device.getAddress());
				    	Toast.makeText(getApplicationContext(), "No Paired Devices", Toast.LENGTH_SHORT).show();
				    	}
					}
				}
				catch(Exception I)
				{
					//Toast.makeText(getApplicationContext(), "Paired Device Crashed", Toast.LENGTH_SHORT).show();
					Log.e("MAIN_MENU", "----------PAIRED DEVICE CRASHED---------");
				}
				
				
				try
				{
				// Create a BroadcastReceiver for ACTION_FOUND
				 mReceiver = new BroadcastReceiver() 
				{
				    public void onReceive(Context context, Intent intent) {
			            String action = intent.getAction();

			            // When discovery finds a device
			            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
			                // Get the BluetoothDevice object from the Intent
			                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
			                // If it's already paired, skip it, because it's been listed already
			                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
			                    mArrayAdapter.add(device.getName() + "\n" + device.getAddress());
			                    Log.i("BLUE-REX", "------------new devices added successfully--------------");
			                }
			            // When discovery is finished, change the Activity title
			            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
			                setProgressBarIndeterminateVisibility(false);
			                
			                if (mArrayAdapter.getCount() == 0) {
			                    String noDevices = getResources().getText(R.string.none_found).toString();
			                    mArrayAdapter.add(noDevices);
			                    Log.i("BLUE-REX", "------------new devices 2 added successfully--------------");
			                }
			            }
			            
			            
			        }

					
				};
				// Register the BroadcastReceiver

				//We have to unregister this during onDestroy()
				}
				catch(Exception e)
				{
					//Toast.makeText(getApplicationContext(), "Broadcast Reciever Crashed", Toast.LENGTH_SHORT).show();
					Log.e("MAIN_MENU", "----------BROADCAST RECIEVER CRASHED---------");
				}
				
				try
				{
					List.setAdapter(mArrayAdapter);
					List.setVisibility(View.VISIBLE);
				}catch(Exception ie)
				{
					Log.e("MAIN_MENU", "----------FAILED TO ADD ARRAY ADAPTER TO LIST---------");
				}
				
			}
		});
		
		
		
		IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		registerReceiver(mReceiver, filter);
		
		 filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
	     this.registerReceiver(mReceiver, filter);
		
		
		List.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long ID) {
				// TODO Auto-generated method stub
				try
				{
					//cancel the discovery process first of all, as it consumes a lot of battery and chances of connection failure are HIGH
					mBluetoothAdapter.cancelDiscovery();

			            // Get the device MAC address, which is the last 17 chars in the View
					//String info = (String)List.getItemAtPosition(position);
						String info = ((TextView) view).getText().toString();
			            String address = info.substring(info.length() - 17);

			            // Create the result Intent and include the MAC address
			            Intent intent = new Intent();
			            intent.putExtra(EXTRA_DEVICE_ADDRESS, address);

			            // Set result and finish this Activity
			            setResult(Activity.RESULT_OK, intent);
			            
			            Intent i=new Intent(Main_menu.this, Control.class);
			            i.putExtra("EXTRA_DEVICE_ADDRESS", address);
			            startActivity(i);
			            finish();
//				int itemPosition=position;
//				String  itemValue    = (String) List.getItemAtPosition(position);
//				ConnectThread CT =new ConnectThread(device);
//				CT.run();
//				Intent i=new Intent(Main_menu.this, Control.class);
//				startActivity(i);
				}
				catch(Exception e)
				{
					Log.e("MAIN_MENU", "--------Crashed in ON-ITEM-CLICK--------");
				}
			}
			
		});
		
		
		
		
		
		
		
		
		
		
		Button B2=(Button)findViewById(R.id.Settings);
		B2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent in1= new Intent(Main_menu.this, Connection.class);
				startActivity(in1);
			}
		});
		
		
		
		
		
		
		
		
		
		// Set up an instance of SystemUiHider to control the system UI for
		// this activity.
		mSystemUiHider = SystemUiHider.getInstance(this, contentView,
				HIDER_FLAGS);
		mSystemUiHider.setup();
		mSystemUiHider
				.setOnVisibilityChangeListener(new SystemUiHider.OnVisibilityChangeListener() {
					// Cached values.
					int mControlsHeight;
					int mShortAnimTime;

					@Override
					@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
					public void onVisibilityChange(boolean visible) {
						if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
							// If the ViewPropertyAnimator API is available
							// (Honeycomb MR2 and later), use it to animate the
							// in-layout UI controls at the bottom of the
							// screen.
							if (mControlsHeight == 0) {
								mControlsHeight = controlsView.getHeight();
							}
							if (mShortAnimTime == 0) {
								mShortAnimTime = getResources().getInteger(
										android.R.integer.config_shortAnimTime);
							}
							controlsView
									.animate()
									.translationY(visible ? 0 : mControlsHeight)
									.setDuration(mShortAnimTime);
						} else {
							// If the ViewPropertyAnimator APIs aren't
							// available, simply show or hide the in-layout UI
							// controls.
							controlsView.setVisibility(visible ? View.VISIBLE
									: View.GONE);
						}

						if (visible && AUTO_HIDE) {
							// Schedule a hide().
							delayedHide(AUTO_HIDE_DELAY_MILLIS);
						}
					}
				});

		// Set up the user interaction to manually show or hide the system UI.
		contentView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (TOGGLE_ON_CLICK) {
					mSystemUiHider.toggle();
				} else {
					mSystemUiHider.show();
				}
			}
		});

		// Upon interacting with UI controls, delay any scheduled hide()
		// operations to prevent the jarring behavior of controls going away
		// while interacting with the UI.
		findViewById(R.id.dummy_button).setOnTouchListener(
				mDelayHideTouchListener);
		
		
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);

		// Trigger the initial hide() shortly after the activity has been
		// created, to briefly hint to the user that UI controls
		// are available.
		delayedHide(250);
	}

	/**
	 * Touch listener to use for in-layout UI controls to delay hiding the
	 * system UI. This is to prevent the jarring behavior of controls going away
	 * while interacting with activity UI.
	 */
	View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
		@Override
		public boolean onTouch(View view, MotionEvent motionEvent) {
			if (AUTO_HIDE) {
				delayedHide(AUTO_HIDE_DELAY_MILLIS);
			}
			return false;
		}
	};

	Handler mHideHandler = new Handler();
	Runnable mHideRunnable = new Runnable() {
		@Override
		public void run() {
			mSystemUiHider.hide();
		}
	};

	/**
	 * Schedules a call to hide() in [delay] milliseconds, canceling any
	 * previously scheduled calls.
	 */
	private void delayedHide(int delayMillis) {
		mHideHandler.removeCallbacks(mHideRunnable);
		mHideHandler.postDelayed(mHideRunnable, delayMillis);
	}

	@Override
    protected void onDestroy() {
                // Make sure we're not doing discovery anymore
        if (mBluetoothAdapter != null) {
        	mBluetoothAdapter.cancelDiscovery();
        }
        // Unregister broadcast listeners
        this.unregisterReceiver(mReceiver);
        super.onDestroy();
    }

}
	
