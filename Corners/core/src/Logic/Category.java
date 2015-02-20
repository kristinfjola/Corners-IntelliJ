/**
 * @author: Kristin Fjola Tomasdottir
 * @date	05.02.2015
 * @goal: 	A Superclass for all categories of the game
 */
package logic;

import boxes.Box;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Array;

public class Category {
	
	Box question;
	Array<Box> answers;
	int qWidth;
	int qHeight;
	int screenWidth = 480;
    int screenHeight = 800;
    BitmapFont bmFont;
    
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
    
    public void generate1stLevelQuestions() {
	}
    
    public void generate2ndLevelQuestions() {
	}

    public void generate3rdLevelQuestions() {
	}

    public void generate4thLevelQuestions() {
	}

    public void generate5thLevelQuestions() {
	}

    public void generate6thLevelQuestions() {
	}

    public void generate7thLevelQuestions() {
	}

    public void generate8thLevelQuestions() {
	}
    
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
	
}
