package com.corners.game;

import java.util.List;


/**
 * @author Kristin Fjola Tomasdottir
 * @date 	05.03.2015
 * @goal 	Functionality for everything regarding the user's Facebook account
 */
public interface FacebookService {

	/**
	 * @return true if user is logged in to Facebook
	 */
	public boolean isLoggedIn();
	
	/**
	 * @return Facebook user object if user logs in
	 */
	public FacebookUser logIn();
	
	/**
	 *  logs user out of Facebook
	 */
	public void logOut();
	
	/**
	 * @return
	 */
	public List<String> getFriendsList();
	
	/**
	 * @return
	 */
	public List<Integer> getScores();
	
	/**
	 * @param score
	 */
	public void updateScore(int score);
	
	/**
	 * 
	 */
	public void checkPermission();
	
	/**
	 * @return
	 */
	public List<String> getFriendsListIds();
	
	/**
	 * displays information about the user's Facebook account
	 */
	public void showFacebookUser();
}
