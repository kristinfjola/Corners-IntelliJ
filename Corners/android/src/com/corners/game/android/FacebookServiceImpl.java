package com.corners.game.android;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;
import screens.Settings;
import com.facebook.Request;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.corners.game.FacebookService;
import com.corners.game.FacebookUser;
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

public class FacebookServiceImpl implements FacebookService{
    private final AndroidLauncher androidLauncher;
    private final UiLifecycleHelper uiHelper;
    private FacebookUser user;
    
   
    public FacebookServiceImpl(AndroidLauncher androidLauncher) {
        this.androidLauncher = androidLauncher;
        this.uiHelper = new UiLifecycleHelper(androidLauncher, new Session.StatusCallback() {
			@Override
			public void call(Session session, SessionState state, Exception exception) {
			}
        });
    }

	@Override
	public boolean isLoggedIn() {
		System.out.println("checking if logged in");
		boolean isLoggedIn = Session.getActiveSession() != null && Session.getActiveSession().isOpened();
		System.out.println("logged in: " + isLoggedIn);
		return isLoggedIn;
	}

	@Override
	public FacebookUser logIn() {
		System.out.println("logging in");
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
                    	System.out.println("Session state change");
                        if (session.isOpened()) {
                        	System.out.println("Session opened");
                            if (!session.getPermissions().contains("user_friends")) {
                                Session.NewPermissionsRequest newPermissionsRequest = new Session
                                        .NewPermissionsRequest(androidLauncher, Arrays.asList("user_friends"));
                                session.requestNewReadPermissions(newPermissionsRequest);
                                System.out.println("setting user_friends permission");
                            } else {
                            	System.out.println("success yeahhh");
                            }
                            new GetFacebookUserTask().execute(session);
                        }
                        
                    }
                }
        );
        return user;
	}
	

	@Override
	public void inviteFriends() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void logOut() {
		System.out.println("trying to log out");
		 if (isLoggedIn()) {
			System.out.println("loggin out");
            Session.getActiveSession().closeAndClearTokenInformation();
            removeUser();
        }
	}
	
	@Override
	public void showFacebookUser(){
		if(isLoggedIn()){
			Session session = Session.getActiveSession();
			new GetFacebookUserTask().execute(session);
		}
	}
	
	public void removeUser(){
		System.out.println("removing user");
		user = null;
		new SetProfilePicTask().execute(user);
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
    
    public void getFacebookUser(Session session){
    	//Session session = Session.getActiveSession();
		Request request = Request.newGraphPathRequest(session, "me", null);
		Response response = Request.executeAndWait(request);
		try{
			JSONObject JSONuser = response.getGraphObject().getInnerJSONObject();
			user = setUser(JSONuser);
			new SetProfilePicTask().execute(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    public FacebookUser setUser(JSONObject jo){
    	FacebookUser u = new FacebookUser();
    	try {
			u.setId(jo.get("id").toString());
			u.setFirstName(jo.get("first_name").toString());
			u.setFullName(jo.get("name").toString());
			u.setGender(jo.get("gender").toString());
			u.setEmail(jo.get("email").toString());
			u.setUrl(jo.get("link").toString());
		} catch (JSONException e) {
			e.printStackTrace();
			Log.e("facebook", "could not create user");
			u = null;
		}
    	return u;    	
    }
    
    public class GetFacebookUserTask extends AsyncTask<Session, Void, Session> {
    	public GetFacebookUserTask() {}
		protected Session doInBackground(Session... sessions) {
			System.out.println("starting GetFacebookUserTask");
			Session session = sessions[0];
			getFacebookUser(session);
			return session;
		}
    	protected void onPostExecute(Session session) {
    		System.out.println("finishing GetFacebookUserTask");
    	}
    }
    
    public class SetProfilePicTask extends AsyncTask<FacebookUser, Void, FacebookUser> {
    	public SetProfilePicTask() {}
    	protected FacebookUser doInBackground(FacebookUser... users) {
    		System.out.println("starting SetProfilePicTask");
    		FacebookUser user = users[0];
    		return user;
    	}
    	protected void onPostExecute(FacebookUser user) {
    		androidLauncher.setProfilePicture(user);
    		System.out.println("finishing SetProfilePicTask");
    	}
    }
}
