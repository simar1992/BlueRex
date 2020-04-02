package com.exam.blurex;

import java.util.HashMap;

import android.R.integer;
import android.R.string;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SessionManagement {
	SharedPreferences pref;
	Editor editor;
	Context context;
	int PRIVATE_MODE=0;
	
	public static final String PREF_NAME="preferenceFile_number_1"; 
	public static final String IS_LOGIN="IsLoggedIn";
	public static final String KEY_NAME="USERNAME";
	public static final String KEY_PASS="PASSWORD";
	
	public SessionManagement(Context context1){
		this.context=context1;
		pref=context1.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
		editor=pref.edit();
		editor.apply();
	}
	
/*
 * TO CREATE A LOGIN SESSION
 */
	public void createLoginSession(String name, String password){
		editor.putBoolean(IS_LOGIN, true);
		editor.putString(KEY_NAME, name);
		editor.putString(KEY_PASS, password);
		editor.commit();
	}
	
/*
 * Get Stored Session DATA
 */
	public HashMap<String,String> getUserDetails(){
		HashMap<String, String> user=new HashMap<String, String>();
		user.put(KEY_NAME, pref.getString(KEY_NAME, null));
		user.put(KEY_PASS, pref.getString(KEY_PASS, null));
		return user;
	}
	
	public void chechLogin()
	{
		if(!this.isLoggedIn())
		{
			Intent i=new Intent(context, LoginActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(i);
			
		}
	}
	
	public void logoutUser()
	{
		editor.clear();
		editor.commit();
		Intent i=new Intent(context, LoginActivity.class);
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(i);
	}
	
	
	public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
}
