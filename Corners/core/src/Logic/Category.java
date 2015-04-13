/**
 * @author: Kristin Fjola Tomasdottir
 * @date	05.02.2015
 * @goal: 	A Superclass for all categories of the game
 */
package logic;

import boxes.Box;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.corners.game.MainActivity;

public class Category {
	
	public Box question;
	public Array<Box> answers;
	public int qWidth;
	public int qHeight;
	public int screenWidth = Gdx.graphics.getWidth();
	public int screenHeight = Gdx.graphics.getHeight();
	public String type;
	public int playScreenHeight;
	public int playScreenWidth;
	public Skin skin;
	public String screenSizeGroup;
	public int[] levelTimes;
	public int[] starsLimits = {9, 18, 27}; // 1*9, 2*9, 3*9
    
    /**
     * Generates questions for level
     * @param level
     */
    public void generateNewQuestion(int level){
		switch(level){
			case 1: generate1stLevelQuestions();
					break;
			case 2: generate2ndLevelQuestions();
					break;
			case 3: generate3rdLevelQuestions();
					break;
			case 4: generate4thLevelQuestions();
					break;
			case 5: generate5thLevelQuestions();
					break;
			case 6: generate6thLevelQuestions();
					break;
			case 7: generate7thLevelQuestions();
					break;
			case 8: generate8thLevelQuestions();
					break;
			case 9: generate9thLevelQuestions();
					break;
			default: break;
		}
	}
    
    /**
     * Generates questions for first level of current category
     */
    public void generate1stLevelQuestions() {
	}
    
    /**
     * Generates questions for 2nd level of current category
     */
    public void generate2ndLevelQuestions() {
	}

    /**
     * Generates questions for 3rd level of current category
     */
    public void generate3rdLevelQuestions() {
	}

    /**
     * Generates questions for 4th level of current category
     */
    public void generate4thLevelQuestions() {
	}

    /**
     * Generates questions for 5th level of current category
     */
    public void generate5thLevelQuestions() {
	}

    /**
     * Generates questions for 6th level of current category
     */
    public void generate6thLevelQuestions() {
	}

    /**
     * Generates questions for 7th level of current category
     */
    public void generate7thLevelQuestions() {
	}

    /**
     * Generates questions for 8th level of current category
     */
    public void generate8thLevelQuestions() {
	}
    
    /**
     * Generates questions for 9th level of current category
     */
    public void generate9thLevelQuestions() {
	}

    

	/**
	 * @param
	 * @return	true if question box collapses with answer box
	 */
    public Box checkIfHitAnswer(){
		return null;
	}
    
    /**
	 * @param
	 * @return	true if question box collapses with some answer box
	 */
    public Box checkIfHitBox() {
    	return null;
    }
    
    /**
     * @param playScreen
     * @param level
     * Sets directions for level
     */
    public void setDirections(MainActivity main, int level){

    }
    
    /**
     * @param playScreen
     * @param level
     * Sets directions for level
     */
    public void showDirections(MainActivity main, String directions){
    	main.dialogService.showDirections("Corners", directions, main.play);
    	main.play.pause();
    }

	/**
	 * @return current question
	 */
	public Box getQuestion() {
		return question;
	}

	/**
	 * @param question
	 */
	public void setQuestion(Box question) {
		this.question = question;
	}

	/**
	 * @return current answers
	 */
	public Array<Box> getAnswers() {
		return answers;
	}

	/**
	 * @param answers
	 */
	public void setAnswers(Array<Box> answers) {
		this.answers = answers;
	}

	/**
	 * @return width of current question
	 */
	public int getqWidth() {
		return qWidth;
	}

	/**
	 * @param qWidth
	 */
	public void setqWidth(int qWidth) {
		this.qWidth = qWidth;
	}

	/**
	 * @return height of current question
	 */
	public int getqHeight() {
		return qHeight;
	}

	/**
	 * @param qHeight
	 */
	public void setqHeight(int qHeight) {
		this.qHeight = qHeight;
	}

	/**
	 * @return width of screen
	 */
	public int getScreenWidth() {
		return screenWidth;
	}

	/**
	 * @param screenWidth
	 */
	public void setScreenWidth(int screenWidth) {
		this.screenWidth = screenWidth;
	}

	/**
	 * @return height of screen
	 */
	public int getScreenHeight() {
		return screenHeight;
	}

	/**
	 * @param screenHeight
	 */
	public void setScreenHeight(int screenHeight) {
		this.screenHeight = screenHeight;
	}
	
	/**
	 * @return type of category
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * @param width of the screen in Play (without the infoBar and progressBar)
	 */
	public void setPlayScreenWidth(int width) {
		playScreenWidth = width;
	}
	
	/**
	 * @param height of the screen in Play (without the infoBar and progressBar)
	 */
	public void setPlayScreenHeight(int height) {
		playScreenHeight = height;
	}
	
	/**
	 * @param skin
	 */
	public void setSkin(Skin skin) {
		this.skin = skin;
	}
	
	/**
	 * @param screenSizeGroup
	 */
	public void setScreenSizeGroup(String screenSizeGroup) {
		this.screenSizeGroup = screenSizeGroup;
	}
	
	/**
	 * Sets up the question and possible answer boxes
	 */
	public void setUpBoxes() {
	}
	
	/**
	 * 
	 * @return the height of the play screen
	 */
	public int getPlayScreenHeight(){
		return this.playScreenHeight;
	}
	
	/**
	 * 
	 * @return the width of the play screen
	 */
	public int getPlayScreenWidth(){
		return this.playScreenWidth;
	}
	
	/**
	 * Makes a new list of previously asked questions for the category
	 */
	public void refreshQuestions(){}
	
	/**
	 * @param level
	 * @return time for level
	 */
	public int getTimeForLevel(int level){
		return levelTimes[level-1];
	}
	
	/**
	 * @return the time limit for reaching 3 stars
	 * seconds that are allowed to pass
	 */
	public int get3StarsTimeLimit(){
		return starsLimits[0];
	}
	
	/**
	 * @return the time limit for reaching 2 stars
	 * seconds that are allowed to pass
	 */
	public int get2StarsTimeLimit(){
		return starsLimits[1];
	}
	
	/**
	 * @return the time limit for reaching 1 star
	 * seconds that are allowed to pass
	 */
	public int get1StarTimeLimit(){
		return starsLimits[2];
	}
	
}
