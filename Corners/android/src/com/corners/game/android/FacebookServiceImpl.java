package com.corners.game.android;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//import screens.Request;
import screens.Settings;
import com.facebook.Request;

import android.content.Intent;
import android.os.Bundle;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.corners.game.FacebookService;
import com.facebook.HttpMethod;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;

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
        permissions.add("user_games_activity");
        permissions.add("friends_games_activity");
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
                        	//TwiddleGame.debug("Available Perms " + session.getPermissions());
                            if(!session.getPermissions().contains("publish_actions")) {
                            	 Session.NewPermissionsRequest newPermissionsRequest = new Session
                                         .NewPermissionsRequest(androidLauncher, Arrays.asList("publish_actions"));
                                 session.requestNewPublishPermissions(newPermissionsRequest);
                            }
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
			System.out.println(response.getGraphObject());
			id = response.getGraphObject().getInnerJSONObject().get("id").toString();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return id;
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return friends;
    }
    
    @Override
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
			// TODO Auto-generated catch block
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
    			// TODO Auto-generated catch block
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
    
    @Override
    public void checkPermission() {
    	Session session = Session.getActiveSession();
		Request request = Request.newGraphPathRequest(session, "me/permissions", null);
		Response response = Request.executeAndWait(request);
		System.out.println("permission: "+ response);
    }
}
