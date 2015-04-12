/**
 * @author  automatically made
 * @date 	05.02.2015
 * @goal 	Handles everything for native Android functionality
 */
package com.corners.game.android;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.corners.game.ActivityRequestHandler;
import com.corners.game.FacebookUser;
import com.corners.game.MainActivity;
import com.facebook.AppEventsLogger;
import com.facebook.widget.ProfilePictureView;

public class AndroidLauncher extends AndroidApplication implements ActivityRequestHandler {
	protected FacebookServiceImpl facebookService;
	DialogsImpl dialogs;
	NotificationsImpl notifications;
	private final int SHOW = 1;
    private final int HIDE = 0;
    protected View fbView;
    protected View splashView;
    private ProfilePictureView profilePictureView;
    private TextView userNameView;
    RingerModeHelper ringerModeHelper;
    
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		System.out.println("AndroidLauncher: starting create");
		super.onCreate(savedInstanceState);

        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        cfg.useAccelerometer = false;
        cfg.useCompass = false;
        cfg.useWakelock = true;
        
        //checkKeyHash();

        facebookService = new FacebookServiceImpl(this);
        facebookService.onCreate(savedInstanceState);      
        MainActivity mainActivity = new MainActivity();
        mainActivity.setFacebookService(facebookService);  
        mainActivity.activityRequestHandler = this;
        
        dialogs = new DialogsImpl(this);
        mainActivity.dialogs = dialogs;
        
        notifications = new NotificationsImpl(this);
        mainActivity.notificationsService = notifications;

        // -----    two layouts to include facebook ------
        RelativeLayout layout = new RelativeLayout(this);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

        View gameView = initializeForView(mainActivity);

        LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        fbView = vi.inflate(R.layout.main, null);
        splashView = vi.inflate(R.layout.splash, null);
        profilePictureView = (ProfilePictureView) fbView.findViewById(R.id.selection_profile_pic);
        userNameView = (TextView) fbView.findViewById(R.id.selection_user_name);

        
        RelativeLayout.LayoutParams fbParams = 
            new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
        fbParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        fbParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        
        layout.addView(gameView);
        layout.addView(fbView, fbParams);
        layout.addView(splashView, fbParams);
        showFacebook(false);

        // Hook it all up
        setContentView(layout);

        // sound
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.media.RINGER_MODE_CHANGED");
        ringerModeHelper = new RingerModeHelper(mainActivity,this.getContext());
        registerReceiver(ringerModeHelper , filter);
        
        // hax to set position of facebook login
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
		int height = display.getHeight()/4;
        ImageView img = (ImageView)findViewById(R.id.charImg);
        Bitmap bmp=BitmapFactory.decodeResource(getResources(),R.drawable.happycarl);                                                           
        bmp=Bitmap.createScaledBitmap(bmp, 100,height, true);
        img.setImageBitmap(bmp);
        
        System.out.println("AndroidLauncher: finishing create");
	}
	
	/**
	 *  prints your Facebook developer key hash
	 */
	public void checkKeyHash(){
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
	
	/**
	 * @param user
	 * set's the user's profile picture from Facebook
	 */
	public void setProfilePicture(FacebookUser user){
		profilePictureView.setCropped(true);
		profilePictureView.setProfileId(user != null ? user.getId() : "");
		userNameView.setText(user != null ? user.getFullName() : "");
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
    
    /**
     * handler to show and hide Facebook info
     */
    protected Handler handler = new Handler() {
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
    
    /**
     * handler to show and hide splash screen
     */
    protected Handler splashHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what) {
                case SHOW:
                {
                    splashView.setVisibility(View.VISIBLE);
                    break;
                }
                case HIDE:
                {
                	splashView.setVisibility(View.GONE);
                    break;
                }
            }
        }
    };

	@Override
	public void showFacebook(boolean show) {
		handler.sendEmptyMessage(show ? SHOW : HIDE);
	}
	
	@Override
	public void unregisterRingerReceiver() {
		unregisterReceiver(ringerModeHelper);
	}

	@Override
	public boolean isConnectedToInternet() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);		 
		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();	
		return isConnected;	
	}

	@Override
	public void showSplash(boolean show) {
		splashHandler.sendEmptyMessage(show ? SHOW : HIDE);
	}
}
