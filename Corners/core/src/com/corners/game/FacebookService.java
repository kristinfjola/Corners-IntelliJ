package com.corners.game;

import screens.Settings;


public interface FacebookService {

	public boolean isLoggedIn();
	
	public FacebookUser logIn();
	 
	public void inviteFriends();
	
	public void logOut();
	
	public void showFacebookUser();
}
