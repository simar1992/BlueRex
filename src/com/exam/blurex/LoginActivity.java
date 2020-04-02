package com.exam.blurex;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity{
	
	EditText txtUsername, txtPassword;
	Button BtnLogin;
	
	//AlertDialogueManager alert=new  AlertDialogueManager();
	SessionManagement session;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		Toast.makeText(getApplicationContext(), "Welcome to login class", Toast.LENGTH_SHORT).show();
		
		session = new SessionManagement(getApplicationContext());                
        
        // Email, Password input text
        txtUsername = (EditText) findViewById(R.id.txtUsername);
        txtPassword = (EditText) findViewById(R.id.txtPassword); 
         
        //Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();
         
         
        // Login button
        BtnLogin = (Button) findViewById(R.id.btnLogin);
        BtnLogin.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String username = txtUsername.getText().toString();
                String password = txtPassword.getText().toString();
                
                if(username.trim().length()>0  && password.trim().length()>0)
                {
                	if(username.equals("simar") && password.equals("pass"))
                	{
                		session.createLoginSession(username, password);
                		Intent i=new Intent(LoginActivity.this, Switch_on.class);
                		startActivity(i);
                		finish();
                		
                	}
                	else
                	{
                		Toast.makeText(getApplicationContext(), "LOGIN FAILED", Toast.LENGTH_SHORT).show();
                	}
                }
                else
                {
                	Toast.makeText(getApplicationContext(), "LOGIN FAILED", Toast.LENGTH_SHORT).show();
                }
				
			}
		});
         
	}

	public LoginActivity() {
		// TODO Auto-generated constructor stub
	}

}
