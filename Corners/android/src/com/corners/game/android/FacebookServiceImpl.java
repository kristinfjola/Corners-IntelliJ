/**
 * @author Kristin Fjola Tomasdottir
 * @date 	05.03.2015
 * @goal 	Functionality for everything regarding the user's Facebook account
 */
package com.corners.game.android;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.json.JSONObject;
import org.json.JSONException;
import com.facebook.Request;

import android.app.Activity;
import android.content.Intent;
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
import com.facebook.HttpMethod;
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
		boolean isLoggedIn = Session.getActiveSession() != null && Session.getActiveSession().isOpened();
		return isLoggedIn;
	}

	@Override
	public FacebookUser logIn() {
		System.out.println("logging in to facebook");
		List permissions = new ArrayList<String>();
        permissions.add("user_friends");
        permissions.add("user_games_activity");
        permissions.add("friends_games_activity");
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
                            if(!session.getPermissions().contains("publish_actions")) {
                            	 Session.NewPermissionsRequest newPermissionsRequest = new Session
                                         .NewPermissionsRequest(androidLauncher, Arrays.asList("publish_actions"));
                                 session.requestNewPublishPermissions(newPermissionsRequest);
                            }
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
	public void logOut() {
		 if (isLoggedIn()) {
			System.out.println("logging out of facebook");
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
	
	/**
	 * removing information from Facebook about the user 
	 */
	public void removeUser(){
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
    
    /**
     * @param session - current Facebook session
     * fetches information about the user's Facebook account
     */
    public void getFacebookUser(Session session){
		Request request = Request.newGraphPathRequest(session, "me", null);
		Response response = Request.executeAndWait(request);
		try{
			JSONObject JSONuser = response.getGraphObject().getInnerJSONObject();
			user = setUser(JSONuser);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    /**
     * @param jo - JSON object representing the user's Facebook account
     * @return FacebookUser - information about the user's Facebook account
     */
    public FacebookUser setUser(JSONObject jo){
    	FacebookUser u = new FacebookUser();
    	try {
			u.setId(jo.get("id").toString());
			u.setFirstName(jo.get("first_name").toString());
			u.setFullName(jo.get("name").toString());
		} catch (JSONException e) {
			e.printStackTrace();
			Log.e("facebook", "could not create user");
			u = null;
		}
    	return u;    	
    }
    
    /**
     * @author Kristin Fjola Tomasdottir
     * @date 	19.03.2015
     * @goal 	Fetches information about the user's Facebook account
     */
    public class GetFacebookUserTask extends AsyncTask<Session, Void, Session> {
    	public GetFacebookUserTask() {}
		protected Session doInBackground(Session... sessions) {
			Session session = sessions[0];
			getFacebookUser(session);
			return session;
		}
    	protected void onPostExecute(Session session) {
    		new SetProfilePicTask().execute(user);
    	}
    }
    
    /**
     * @author Kristin Fjola Tomasdottir
     * @date 	19.03.2015
     * @goal 	Displays the user's Facebook photo
     */
    public class SetProfilePicTask extends AsyncTask<FacebookUser, Void, FacebookUser> {
    	public SetProfilePicTask() {}
    	protected FacebookUser doInBackground(FacebookUser... users) {
    		FacebookUser user = users[0];
    		return user;
    	}
    	protected void onPostExecute(FacebookUser user) {
    		androidLauncher.setProfilePicture(user);
    	}
    }
    
    @Override
    public List<String> getFriendsList() {
    	Session session = Session.getActiveSession();
		Request request = Request.newGraphPathRequest(session, "me/friends", null);
		Response response = Request.executeAndWait(request);
		List<String> friends = new ArrayList<String>();
		try {
			JSONArray data = (JSONArray) response.getGraphObject().getInnerJSONObject().get("data");
			for(int i = 0; i < data.length(); i++) {
				String name = (String) data.getJSONObject(i).get("name");
				friends.add(name);
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return friends;
    }
    
    /**
     * used in getScores method - to get score from id
     * 	 (the list is in the same order as the list from getFriendsList)
     * 
	 * @return list of ids of facebook friends
	 */
    public List<String> getFriendsListIds() {
    	Session session = Session.getActiveSession();
		Request request = Request.newGraphPathRequest(session, "me/friends", null);
		Response response = Request.executeAndWait(request);
		List<String> friends = new ArrayList<String>();
		try {
			JSONArray data = (JSONArray) response.getGraphObject().getInnerJSONObject().get("data");
			for(int i = 0; i < data.length(); i++) {
				String id = (String) data.getJSONObject(i).get("id");
				friends.add(id);
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return friends;
    }
    
    @Override
    public List<Integer> getScores() {
    	Session session = Session.getActiveSession();
    	List<String> friends = getFriendsListIds();
    	List<Integer> scores = new ArrayList<Integer>();
    	Integer score = 0;
    	
    	for(int i = 0; i < friends.size(); i++) {
    		String id = friends.get(i);
    		boolean is_score = false;
    		Request request = Request.newGraphPathRequest(session, id+"/scores", null);
    		Response response = Request.executeAndWait(request);
    		System.out.println(response);
    		try {
    			JSONArray data = (JSONArray) response.getGraphObject().getInnerJSONObject().get("data");
    			if(data.length() != 0) {
    				for(int j = 0; j < data.length(); j++) {
    					JSONObject app = (JSONObject) data.getJSONObject(j).get("application");
    					String app_name = (String) app.get("name");
    					if(app_name.equals("Corners")) {
    						is_score = true;
    						score = (Integer) data.getJSONObject(j).get("score");
    					}
        			}
    			}
    			if(is_score) {
    				scores.add(score);
    			} else {
    				scores.add(-1); //no permission to see score
    			}
    			
    		} catch (JSONException e) {
    			e.printStackTrace();
    		}
    	}
    	
    	for(int i = 0; i < scores.size(); i++) {
    		System.out.println("scores:"+scores.get(i));
    	}
    	
    	return scores;
    }
    
    @Override
    public void updateScore(int score) {
    	Session session = Session.getActiveSession();
    	Bundle params = new Bundle();
    	params.putString("score", ""+score);
    	Request updateScores = new Request(
    	    session,
    	    "/me/scores",
    	    params,
    	    HttpMethod.POST,
    	    new Request.Callback() {
    	        public void onCompleted(Response response) {
    	            System.out.println("update response: "+response);
    	        }
    	    }
    	);
    	
    	Response response = updateScores.executeAndWait();
    	System.out.println("update scores response: "+response);
    }
    
    /**
	 * writes permessions for facebook user in console
	 */
    public void checkPermission() {
    	Session session = Session.getActiveSession();
		Request request = Request.newGraphPathRequest(session, "me/permissions", null);
		Response response = Request.executeAndWait(request);
		System.out.println("permission: "+ response);
    }
}
