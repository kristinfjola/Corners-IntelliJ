/**
 * @author Kristin Fjola Tomasdottir
 * @date 	12.03.15
 * @goal 	Various native Android features
 */
package com.corners.game;

import com.badlogic.gdx.scenes.scene2d.ui.Label;

import logic.Category;
import screens.Play;

public interface Dialogs {
    /**
     * @param toastMessage
     * @param toastDuration
     * displays a toast with the message toastMessage
     */
    public void showToast(CharSequence toastMessage);
    
    public void removeAllToast();
    
    /**
     * @param alertBoxTitle
     * @param alertBoxMessage
     * @param alertBoxButtonText
     * @param playScreen
     * displays directions to play on playScreen
     */
    public void showDirections(String alertBoxTitle, String alertBoxMessage, Play playScreen);
    
    public void showEndLevelDialog(String title, String starsImgDir, String charImgDir, String message, Play playScreen);
    
    public void showCharNameDialog(String title, MainActivity main, Label label);
            
    public void showProgressBar();
    
    public void hideProgressBar();
}
