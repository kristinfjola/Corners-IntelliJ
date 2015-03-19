package com.corners.game;

import screens.Settings;


public interface FacebookService {

	public boolean isLoggedIn();
	
	public void logIn();
	 
	public void inviteFriends();
	
	public void logOut();
	 
	public void setScreen(Settings screen);
	
	public String getUserId();
}
