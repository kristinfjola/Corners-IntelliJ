/**
 * @author Kristin Fjola Tomasdottir
 * @date 	12.03.15
 * @goal 	Various native Android features
 */
package com.corners.game;

public interface ActionResolver {
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
     * displays an alert with alertBoxMessage
     */
    public void showAlertBox(String alertBoxTitle, String alertBoxMessage, String alertBoxButtonText);
}
