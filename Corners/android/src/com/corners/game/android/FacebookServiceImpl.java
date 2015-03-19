package com.corners.game.android;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;
import screens.Settings;
import com.facebook.Request;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.corners.game.FacebookService;
import com.facebook.FacebookRequestError;
import com.facebook.Request;
import com.facebook.RequestBatch;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.corners.game.FacebookService;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphUser;
import com.facebook.widget.WebDialog;

public class FacebookServiceImpl implements FacebookService{
    private final AndroidLauncher androidLauncher;
    private final UiLifecycleHelper uiHelper;
    private Settings screen;
    
   
    public FacebookServiceImpl(AndroidLauncher androidLauncher) {
        this.androidLauncher = androidLauncher;
        this.uiHelper = new UiLifecycleHelper(androidLauncher, new Session.StatusCallback() {
			@Override
			public void call(Session session, SessionState state, Exception exception) {
			}
        });
    }
    
    @Override
    public void setScreen(Settings screen){
    	this.screen = screen;
    }

	@Override
	public boolean isLoggedIn() {
		 return Session.getActiveSession() != null && Session.getActiveSession().isOpened();
	}

	@Override
	public void logIn() {
		List permissions = new ArrayList<String>();
        permissions.add("user_friends");
        permissions.add("public_profile");
        permissions.add("email");

        Session.openActiveSession(
        		androidLauncher,
                true,
                permissions,
                new Session.StatusCallback() {

                    // callback when session changes state
                    @Override
                    public void call(Session session, SessionState state, Exception exception) {
                        if (session.isOpened()) {
                            if (!session.getPermissions().contains("user_friends")) {
                                Session.NewPermissionsRequest newPermissionsRequest = new Session
                                        .NewPermissionsRequest(androidLauncher, Arrays.asList("user_friends"));
                                session.requestNewReadPermissions(newPermissionsRequest);
                            } else {
                            	System.out.println("success yeahhh");
                            }
                        }
                        screen.setLoginListener();
                    }
                }
        );
	}
	

	@Override
	public void inviteFriends() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void logOut() {
		 if (isLoggedIn()) {
            Session.getActiveSession().closeAndClearTokenInformation();
            screen.setLoginListener();
        }
	}
    
	public void onCreate(Bundle savedInstanceState) {
        uiHelper.onCreate(savedInstanceState);
    }

    public void onResume() {
        uiHelper.onResume();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        uiHelper.onActivityResult(requestCode, resultCode, data);
    }

    public void onSaveInstanceState(Bundle outState) {
        uiHelper.onSaveInstanceState(outState);
    }

    public void onPause() {
        uiHelper.onPause();
    }

    public void onStop() {
        uiHelper.onStop();
    }

    public void onDestroy() {
        uiHelper.onDestroy();
    }
    
    @Override
    public String getUserId() {
    	Session session = Session.getActiveSession();
		Request request = Request.newGraphPathRequest(session, "me", null);
		Response response = Request.executeAndWait(request);
		String id = "";
		try {
			System.out.println("nafn: "+response.getGraphObject().getInnerJSONObject().get("name"));
			id = response.getGraphObject().getInnerJSONObject().get("id").toString();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return id;
    }
}
