/**
 * @author: Kristin Fjola Tomasdottir
 * @date	05.02.2015
 * @goal: 	A Superclass for all categories of the game
 */
package logic;

import boxes.Box;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;

import data.Data;
import data.DataProcessor;
import data.LevelStars;

public class Category {
	
	Box question;
	Array<Box> answers;
	int qWidth;
	int qHeight;
	int screenWidth = Gdx.graphics.getWidth();
    int screenHeight = Gdx.graphics.getHeight();
    String type;
    int playScreenHeight;
    int playScreenWidth;
    Skin skin;
    String screenSizeGroup;
    
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
	 * @return hight of current question
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
	 * @return stars received for current category
	 */
	public LevelStars getStars(){
		Data data = new Data();
		DataProcessor.getData(data);
		return data.getStarsByString(getType());
	}
	
	/**
	 * @param level stars received for level in category
	 * @return
	 */
	public int getStarsByLevel(int level){
		LevelStars stars = getStars();
		return stars.getStarsByLevel(level);
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
}
