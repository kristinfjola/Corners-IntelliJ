/**
 * @author Kristin Fjola Tomasdottir
 * @date 	12.03.15
 * @goal 	Various pop-up dialogs and toasts to display
 */
package com.corners.game;

import com.badlogic.gdx.scenes.scene2d.ui.Label;

import screens.Play;

public interface Dialogs {
    /**
     * @param toastMessage
     * displays a toast with the message toastMessage when the user tries
     * to press the back button in the start screen
     */
    public void showBackToast(CharSequence toastMessage);
    
    /**
     * removes the toast that is displayed when the user presses the back
     * button in the start screen
     */
    public void removeBackToast();
    
    /**
     * displays a toast that tells the user he/she is not connected
     * to the internet
     */
    public void showNotConnectedToast();
    
    /**
     * Displays directions to play a level on playScreen
     * @param alertBoxTitle
     * @param alertBoxMessage
     * @param alertBoxButtonText
     * @param playScreen
     */
    public void showDirections(String alertBoxTitle, String alertBoxMessage, Play playScreen);
    
    /**
     * Shows a dialog with the title title, the images the directories starsImgDir and charImgDir represent
     * and the text messages. 
     * @param title
     * @param starsImgDir
     * @param charImgDir
     * @param messages
     */
    public void showEndLevelDialog(String title, String starsImgDir[], String charImgDir, String[] messages);
    
    /**
     * Shows a diallog the has the title title and saves the text that the user inserts into
     * the editText to main.data and label
     * @param title
     * @param main
     * @param label
     */
    public void showCharNameDialog(String title, MainActivity main, Label label);
            
    /**
     * Shows a loading progress bar when waiting for something
     */
    public void showProgressBar();
    
    /**
     * Hides a loading progress bar when waiting for something
     */
    public void hideProgressBar();
}
