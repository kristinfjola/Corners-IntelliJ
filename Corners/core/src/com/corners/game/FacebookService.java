package com.corners.game;

import java.util.List;


public interface FacebookService {

	public boolean isLoggedIn();
	
	public FacebookUser logIn();
	 
	public void inviteFriends();
	
	public void logOut();
	
	public List<String> getFriendsList();
	
	public List<Integer> getScores();
	
	public void updateScore(int score);
	
	public void checkPermission();
	
	public List<String> getFriendsListIds();
	
	public void showFacebookUser();
}
