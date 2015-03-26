/**
 * @author Kristin Fjola Tomasdottir
 * @date 	26.03.2015
 * @goal 	Handles the notifications for the game
 */
package com.corners.game;

public interface Notifications {

	/**
	 * turns on notifications
	 */
	public void setNotifications();
	
	/**
	 * turns off notifications
	 */
	public void cancelNotifications();
	
	/**
	 * @return whether notifications are on
	 */
	public boolean isOn();
}
