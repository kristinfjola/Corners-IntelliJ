package com.corners.game.android;

import android.os.Bundle;
import android.content.Intent;
import android.content.IntentFilter;
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
}
