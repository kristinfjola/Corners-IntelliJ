/**
 * @author Kristin Fjola Tomasdottir
 * @date 	13.05.15
 * @goal 	Handles requests for the Android layout
 */
package com.corners.game;

public interface ActivityRequestHandler {
	/**
	 * @param show
	 * shows and hides Facebook information
	 */
	public void showFacebook(boolean show);
	
	/**
	 * Unregisters the ringermode receiver
	 */
	public void unregisterRingerReceiver();
}
