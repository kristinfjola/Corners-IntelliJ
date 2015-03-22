package com.corners.game;

import java.util.List;

import screens.Settings;


public interface FacebookService {

	public boolean isLoggedIn();
	
	public void logIn();
	 
	public void inviteFriends();
	
	public void logOut();
	 
	public void setScreen(Settings screen);
	
	public String getUserId();
	
	public List<String> getFriendsList();
	
	public List<Integer> getScores();
	
	public void updateScore(int score);
	
	public void checkPermission();
	
	public List<String> getFriendsListIds();
}
