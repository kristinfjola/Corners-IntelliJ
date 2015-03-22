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
	 * @return list of facebook friends that use Corners
	 */
	public List<String> getFriendsList();
	
	/**
	 * @return list of scores from facebook friends that use Corners
	 * 		(the list is in the same order as the list from getFriendsList)
	 */
	public List<Integer> getScores();
	
	/**
	 * updates the score on facebook - score is the new score
	 * 
	 * @param score 
	 */
	public void updateScore(int score);
	
	/**
	 * displays information about the user's Facebook account
	 */
	public void showFacebookUser();
}
