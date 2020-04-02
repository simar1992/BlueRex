package com.exam.blurex;

import java.util.Locale;

import android.R.color;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;


public class Splash extends Activity implements TextToSpeech.OnInitListener, AnimationListener {

	Animation animFade_In, animRot,animBounce;
	public static int SPLASH_TIME_OUT=4500;
	public static float Rot=200.0f;
	ImageView IV;																		//GLOBAL DECLARATION
	TextView tv1,tv2;																	//Declaring various variables used in the CODE	
	private TextToSpeech tts_Splash;
	
	String FLAG=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {							//onCreate method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_screen);
		
		
		animFade_In=AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);   //Initializing Animation variables
		animRot=AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
		animBounce=AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
		tts_Splash=new TextToSpeech(this, this);									//Text to speech Implementation
				
		
		animFade_In.setAnimationListener(this);
		animRot.setAnimationListener(this);
		
		tv1=(TextView)findViewById(R.id.Copyright_protected);						//Initialising textView and imageView variables
		tv2=(TextView)findViewById(R.id.please_wait);
		IV=(ImageView)findViewById(R.id.BluRex);
		
		
		animRot.setStartOffset(600L);												//used to delay the animation for a specific milliseconds of time
		IV.startAnimation(animRot); 												//used to start animation
		
		
	
		
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				tv1.setText("(C) SIMAR MANN COPYRIGHTS");
				tv1.setTextColor(color.white);
				tv1.setTextSize(18.0F);
				tv1.setVisibility(View.VISIBLE);
				animBounce.setStartOffset(200L);
				tv1.startAnimation(animBounce);
				
				// TODO Auto-generated method stub
				new CountDownTimer(3200,100) {
					
					@Override
					public void onTick(long millisUntilFinished) {
						// TODO Auto-generated method stub
						tv2.setTextColor(Color.WHITE);
						tv2.setVisibility(View.VISIBLE);
						tv2.setText("Please Wait For :"+ millisUntilFinished/1000+" seconds");
					}
					
					@Override
					public void onFinish() {
						// TODO Auto-generated method stub
						tv2.setVisibility(View.INVISIBLE);
						
						//Animation_TTS();
						Intent i=new Intent(Splash.this,LoginActivity.class);
						startActivity(i);
						
					}
				}.start();

				
			}
		}, SPLASH_TIME_OUT);
			}
	
//	private void Animation_TTS() {
//		// TODO Auto-generated method stub
//		
//		new CountDownTimer(2000,100) {
//			
//			@Override
//			public void onTick(long millisUntilFinished) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//			@Override
//			public void onFinish() {
//				// TODO Auto-generated method stub
//				tv1.setVisibility(View.VISIBLE);
//				tv1.setText("(C) SIMAR MANN COPYRIGHTS");
//				//tv1.setTextColor(color.white);
//				//tv1.setTextSize(18.0F);
//				
//				tv1.startAnimation(animBounce);
//			}
//		}.start();
//		
//	}

	public Splash() {
		// TODO Auto-generated constructor stub
	}

	private void speakOut() {
		// TODO Auto-generated method stub
		String SAY_IT;
		SAY_IT="WELCOME";
		tts_Splash.speak(SAY_IT.toString(), TextToSpeech.QUEUE_FLUSH, null);
	}
	
	@Override
	public void onInit(int status) {	
		// TODO Auto-generated method stub
		if(status==TextToSpeech.SUCCESS){
			int result=tts_Splash.setLanguage(Locale.US);
			if(result==TextToSpeech.LANG_MISSING_DATA||result==TextToSpeech.LANG_NOT_SUPPORTED){
				Log.e("TEXT_TO_SPEECH", "This language is not supported");
			}
			else{
				speakOut();
			}
		}
		else{
			Log.e("TEXT_TO_SPEECH", "Initialisation Failed");
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		if(tts_Splash!=null){
			tts_Splash.stop();
			tts_Splash.shutdown();
		}
		finish();
		super.onDestroy();
	}

	@Override
	public void onAnimationEnd(Animation arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAnimationRepeat(Animation arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAnimationStart(Animation arg0) {
		// TODO Auto-generated method stub
		
	}
	
}

