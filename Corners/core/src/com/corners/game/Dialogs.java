/**
 * @author Kristin Fjola Tomasdottir
 * @date 	12.03.15
 * @goal 	Various native Android features
 */
package com.corners.game;

import screens.Play;

public interface Dialogs {
    /**
     * @param toastMessage
     * @param toastDuration
     * displays a toast with the message toastMessage
     */
    public void showToast(CharSequence toastMessage, int toastDuration);
    
    /**
     * @param alertBoxTitle
     * @param alertBoxMessage
     * @param alertBoxButtonText
     * @param playScreen
     * displays directions to play on playScreen
     */
    public void showDirections(String alertBoxTitle, String alertBoxMessage, String alertBoxButtonText, Play playScreen);
}