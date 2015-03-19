package com.corners.game.android;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import screens.Levels;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.corners.game.ActivityRequestHandler;
import com.corners.game.MainActivity;
import com.facebook.AppEventsLogger;
import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.Session;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.facebook.widget.ProfilePictureView;
import com.facebook.widget.WebDialog;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;

public class AndroidLauncher extends AndroidApplication implements ActivityRequestHandler {
	protected FacebookServiceImpl facebookService;
	ActionResolverImpl actionResolver;
	private final int SHOW = 1;
    private final int HIDE = 0;
    protected View fbView;
    private ProfilePictureView profilePictureView;
    private TextView userNameView;
    
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        cfg.useAccelerometer = false;
        cfg.useCompass = false;
        cfg.useWakelock = true;

        facebookService = new FacebookServiceImpl(this);
        facebookService.onCreate(savedInstanceState);      

        MainActivity mainActivity = new MainActivity();
       
        mainActivity.setFacebookService(facebookService);  
        mainActivity.activityRequestHandler = this;
        
        actionResolver = new ActionResolverImpl(this);
        mainActivity.actionResolver = actionResolver;
        
        //initialize(mainActivity, cfg);
        
        // -----    two layouts to include facebook ------
        RelativeLayout layout = new RelativeLayout(this);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

        View gameView = initializeForView(mainActivity);

        LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        fbView = vi.inflate(R.layout.main, null);
        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        // trying to connect actual facebook button to login
        /*loginButton.setOnClickListener( loginButton.new LoginClickListener(){
            @Override
            public void onClick(View v) {
                //Do whatever u want
                //super.onClick(v);
                
                System.out.println("LOGIN CLICKED");
            }
        });*/
        
        layout.addView(gameView);
        
        RelativeLayout.LayoutParams fbParams = 
            new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
        fbParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        fbParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        
        layout.addView(fbView, fbParams);

        // Hook it all up
        setContentView(layout);
	}
	
	public void checkKeyHash(){
		// if you want to know which key hash you're using
		 try {
	        	PackageInfo info = this.getPackageManager().getPackageInfo(this.getPackageName(), PackageManager.GET_SIGNATURES);
	        	for (Signature signature : info.signatures) {
	        	    MessageDigest md = MessageDigest.getInstance("SHA");
	        	    md.update(signature.toByteArray());
	        	    Log.e("MY KEY HASH:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
	        	}
	    	} catch (NameNotFoundException e) {
	    		Log.e("key hash", "name not found");

	    	} catch (NoSuchAlgorithmException e) {
	    		Log.e("key hash", "no such algorithm");
	    	}
	}
	
	public void setUserInfo(GraphUser user){
		profilePictureView = (ProfilePictureView) fbView.findViewById(R.id.selection_profile_pic);
		profilePictureView.setCropped(true);
		userNameView = (TextView) fbView.findViewById(R.id.selection_user_name);
		profilePictureView.setProfileId(user.getId());
        userNameView.setText(user.getName());
	}
	
	@Override
	protected void onResume() {
	  super.onResume();
	  AppEventsLogger.activateApp(this);
      facebookService.onResume();
	}
	
	@Override
	protected void onPause() {
	  super.onPause();
	  AppEventsLogger.deactivateApp(this);
      facebookService.onPause();
	}
	
	@Override
    protected void onStop() {
        super.onStop();    
        facebookService.onStop();
    
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        facebookService.onDestroy();      
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        facebookService.onSaveInstanceState(outState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        facebookService.onActivityResult(requestCode, resultCode, data);       
    }
    
    protected Handler handler = new Handler() {
    	// handler to show and hide facebook info
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what) {
                case SHOW:
                {
                    fbView.setVisibility(View.VISIBLE);
                    break;
                }
                case HIDE:
                {
                    fbView.setVisibility(View.GONE);
                    break;
                }
            }
        }
    };

	@Override
	public void showFacebook(boolean show) {
		handler.sendEmptyMessage(show ? SHOW : HIDE);
	}
}
