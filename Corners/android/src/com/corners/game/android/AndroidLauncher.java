package com.corners.game.android;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.corners.game.MainActivity;
import com.facebook.AppEventsLogger;

public class AndroidLauncher extends AndroidApplication {
	protected FacebookServiceImpl facebookService;
    
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
        initialize(mainActivity, cfg);
        
        checkKeyHash();
		
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.media.RINGER_MODE_CHANGED");
        registerReceiver(new RingerModeHelper(mainActivity,this.getContext()) , filter);
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
}
