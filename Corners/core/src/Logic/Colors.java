/**
 * @author: Kristin Fjola Tomasdottir
 * @date	05.02.2015
 * @goal: 	Delivers levels for the category Colors
 */
package logic;

import java.util.Random;
import boxes.Box;
import boxes.ColorBox;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Array;
import com.corners.game.MainActivity;

public class Colors extends Category {

	Color[] colorColors = {Color.BLUE, Color.YELLOW, Color.GREEN, Color.RED,  Color.PURPLE, Color.PINK, Color.BLACK,
			Color.MAGENTA, Color.MAROON, Color.NAVY, Color.OLIVE, Color.ORANGE, Color.CYAN, 
			Color.TEAL, Color.WHITE, Color.DARK_GRAY, Color.LIGHT_GRAY};
	String[] colorNames = { "Blue", "Yellow", "Green", "Red", "Purple", "Pink", "Black",
			"Magneta", "Maroon", "Navy", "Olive", "Orange", "Cyan",
			"Teal", "White", "Dark gray", "Light gray" };
	Boolean[] lightColor = {true, true, true, true, false, true, false,
			true, true, false, false, true, true, 
			true, true, false, true};
	Pixmap pm;

	BitmapFont bmFont;
	
	/**
	 * 	Creates a new Colors category, delivers a question and possible answers
	 */
	public Colors(){
		type = "Colors";
		qWidth = 100;
		qHeight = 100;
		
    	pm = new Pixmap(qWidth, qHeight, Format.RGBA8888);
		pm.setColor(Color.WHITE);
		pm.fill();
	}
	
	@Override
	public void setUpBoxes() {
		qWidth = playScreenWidth/4;
		qHeight = playScreenHeight/6;
		int[] xcoords = {0, 0, playScreenWidth-qWidth, playScreenWidth-qWidth}; 
		int[] ycoords = {0, playScreenHeight-qHeight, playScreenHeight-qHeight, 0}; 

		bmFont = (this.skin).getFont(this.screenSizeGroup+"-M");
		//answers
 	    answers = new Array<Box>();
 	    for(int i = 0; i < 4; i++){ 	    	
 	    	ColorBox box = new ColorBox(qWidth, qHeight, Color.WHITE, "nafn", bmFont);
 	 	    box.getRec().x = xcoords[i];
 	 	  	box.getRec().y = ycoords[i];
 	 	  	box.setTexture(new Texture(pm));
 	 	    answers.add(box);
 	    }
 	    
 	    //question
 		Pixmap pixmap = new Pixmap(qWidth, qHeight, Format.RGBA8888);
 		pixmap.setColor(Color.WHITE);
 		pixmap.fill();
 		question = new ColorBox(qWidth + playScreenWidth/12, qHeight, Color.WHITE, "nafn", bmFont);
  	    question.getRec().x = screenWidth / 2 - qWidth / 2;
  	    question.getRec().y = screenHeight / 2 - qHeight / 2;
  	    question.setTexture(new Texture(pixmap));
	}
	
	@Override
	public Box checkIfHitAnswer(){
		for(Box answer : answers){
			ColorBox a = (ColorBox) answer;
			ColorBox q = (ColorBox) question;
			if(a.getRec().overlaps(q.getRec()) && a.getColor().equals((q.getColor()))){
				return a;
			}
		}
		return null;
	}
	
	@Override
	public Box checkIfHitBox() {
		for(Box answer : answers){
			ColorBox a = (ColorBox) answer;
			ColorBox q = (ColorBox) question;
			if(a.getRec().overlaps(q.getRec())){
				return a;
			}
		}
		return null;
	}

	/**
	 * @param color - color of the question
	 * @param isText - is the  question a text question
	 * @param background - background color of the question
	 */
	private void generateQuestion(CorColor color, boolean isText) {
		((ColorBox) question).setColor(color.getColor());
		((ColorBox) question).setName(color.getName());
		((ColorBox) question).setBackground(color.getColor());
		((ColorBox) question).setLight(color.getLight());
		if(isText)
			((ColorBox) question).setText(color.getName());
			 
	}
	
	/**
	 * @param colors - colors that are available to be answers
	 * @param isText - true if the box is supposed to have text on it, else false 
	 */
	private void generateAnswers(CorColor[] colors, boolean isText) {
		// answers
		boolean[] alreadyAnAnswer = new boolean[colors.length];
		for(Box answer : answers){
			Random rand = new Random();
			int random = rand.nextInt(colors.length);
			//Check if this color is already in a box
			while(alreadyAnAnswer[random]){
				rand = new Random();
				random = rand.nextInt(colors.length);
			}
			
			//State that this country is in a box.
			alreadyAnAnswer[random] = true;
			((ColorBox) answer).setColor(colors[random].getColor());
			((ColorBox) answer).setName(colors[random].getName());
			((ColorBox) answer).setBackground(colors[random].getColor());
			((ColorBox) answer).setLight(colors[random].getLight());
			if(isText)
				((ColorBox) answer).setText(colors[random].getName());
			else{
				((ColorBox) answer).setBackground(colors[random].getColor());
			}
				
		}
		
	}
	
	/**
	 * @param color - color of the box
	 * @param isText - true if text is on the box, else false
	 */
	private void generateCorrectAnswer(CorColor color, boolean isText) {
		for(int i = 0; i < 4; i++){
			Boolean isAlreadyInBox = ((ColorBox) answers.get(i)).getColor().equals(color.getColor());
			if(isAlreadyInBox) return;
		}
		
		Random rand = new Random();
		int randomBox = rand.nextInt(4);
		((ColorBox)  answers.get(randomBox)).setColor(color.getColor());
		((ColorBox)  answers.get(randomBox)).setName(color.getName());
		((ColorBox)  answers.get(randomBox)).setLight(color.getLight());
		if(isText)
			((ColorBox) answers.get(randomBox)).setText(color.getName());
		else{
			((ColorBox) answers.get(randomBox)).setBackground(color.getColor());
		}
	}
	
	private void generateTrickQuestion(CorColor color, Boolean changeBackground, Boolean changeText){
		Random rand = new Random();
		int randomBox = rand.nextInt(4);
		while(color.getColor().equals(((ColorBox)answers.get(randomBox)).getColor())){
			rand = new Random();
			randomBox = rand.nextInt(4);
		}
		Boolean light = ((ColorBox) question).getLight();
		if(changeBackground){
			Color background = ((ColorBox)answers.get(randomBox)).getColor();
			light = ((ColorBox)answers.get(randomBox)).getLight();
			((ColorBox) question).setBackground(background);
		}
		if(changeText){
			String name = ((ColorBox)answers.get(randomBox)).getName();
			((ColorBox) question).setText(name);
			light = ((ColorBox) question).getLight();
		}
		if(light){
			bmFont.setColor(Color.BLACK);
			((ColorBox) question).setBmFont(bmFont);
		}
		else{
			bmFont.setColor(Color.WHITE);
			((ColorBox) question).setBmFont(bmFont);
		}
	}
	
	private void generateTrickAnswers(CorColor[] colors, CorColor color, Boolean changeBackground, Boolean changeText){
		int rightAnswer = -1;
		for(int i = 0; i < 4; i++){
			if(((ColorBox)answers.get(i)).getColor().equals(color.getColor()))
				rightAnswer = i;
		}
		
		if(changeBackground){
			
		}
		if(changeText){
			
		}
	}
	
	/**
	 * @param to - the count of colors to get
	 * @return CorColor array with colors from 0 to 'to'
	 */
	public CorColor[] getColors(int to){
		CorColor[] corColors = new CorColor[to];
		for(int i = 0; i < to; i++){
			corColors[i] = new CorColor(colorColors[i], colorNames[i], lightColor[i]);
		}
		return corColors;
	}
	
	@Override
	public void generate1stLevelQuestions() {
		//White background, text to color
		CorColor[] questions = getColors(7);
		Random rand = new Random();
		int randomColor = rand.nextInt(questions.length);
		
		generateQuestion(questions[randomColor], true);
		generateAnswers(questions, false);
		generateCorrectAnswer(questions[randomColor], false);
		generateTrickQuestion(questions[randomColor], false, false);
	}

	@Override
	public void generate2ndLevelQuestions() {
		//Random background, text to color,
		CorColor[] questions = getColors(7);
		Random rand = new Random();
		int randomColor = rand.nextInt(questions.length);
	
		generateQuestion(questions[randomColor], true);
		generateAnswers(questions, false);
		generateCorrectAnswer(questions[randomColor], false);
		generateTrickQuestion(questions[randomColor], true, false);
	}
	
	@Override
	public void generate3rdLevelQuestions() {
		//Background to text on answers, random backgrounds on answers
		CorColor[] questions = getColors(7);
		Random rand = new Random();
		int randomColor = rand.nextInt(questions.length);
		
		generateQuestion(questions[randomColor], true);
		generateAnswers(questions, false);
		generateCorrectAnswer(questions[randomColor], false);
		generateTrickQuestion(questions[randomColor], false, true);
	}
	
	@Override
	public void generate4thLevelQuestions() {
		//Colored background, black random text, background to color
		CorColor[] questions = getColors(13);
		Random rand = new Random();
		int randomColor = rand.nextInt(questions.length);
		
		generateQuestion(questions[randomColor], true);
		generateAnswers(questions, false);
		generateCorrectAnswer(questions[randomColor], false);
		generateTrickQuestion(questions[randomColor], true, false);
	}
	
	@Override
	public void generate5thLevelQuestions() {
		//White background, random text, color of text to color
		CorColor[] questions = getColors(13);
		Random rand = new Random();
		int randomColor = rand.nextInt(questions.length);
		
		generateQuestion(questions[randomColor], true);
		generateAnswers(questions, false);
		generateCorrectAnswer(questions[randomColor], false);
		generateTrickQuestion(questions[randomColor], true, false);
	}
	
	@Override
	public void generate6thLevelQuestions() {
		//Colored background, colored text, 
		CorColor[] questions = getColors(13);
		Random rand = new Random();
		int randomColor = rand.nextInt(questions.length);
		
		generateQuestion(questions[randomColor], true);
		generateAnswers(questions, false);
		generateCorrectAnswer(questions[randomColor], false);
		generateTrickQuestion(questions[randomColor], true, false);
	}
	
	@Override
	public void generate7thLevelQuestions() {
		//
		CorColor[] questions = getColors(17);
		Random rand = new Random();
		int randomColor = rand.nextInt(questions.length);
		
		generateQuestion(questions[randomColor], true);
		generateAnswers(questions, false);
		generateCorrectAnswer(questions[randomColor], false);
		generateTrickQuestion(questions[randomColor], true, false);
	}
	
	@Override
	public void generate8thLevelQuestions() {

		CorColor[] questions = getColors(17);
		Random rand = new Random();
		int randomColor = rand.nextInt(questions.length);
		
		generateQuestion(questions[randomColor], true);
		generateAnswers(questions, false);
		generateCorrectAnswer(questions[randomColor], false);
		generateTrickQuestion(questions[randomColor], true, false);
	}

	@Override
	public void generate9thLevelQuestions() {

		CorColor[] questions = getColors(17);
		Random rand = new Random();
		int randomColor = rand.nextInt(questions.length);
		
		generateQuestion(questions[randomColor], true);
		generateAnswers(questions, false);
		generateCorrectAnswer(questions[randomColor], false);
		generateTrickQuestion(questions[randomColor], true, false);
	}
	
	/**
	 * @return	selection of colors
	 */
	public Color[] getColors() {
		return colorColors;
	}

	/**
	 * @param colors
	 */
	public void setColors(Color[] colors) {
		this.colorColors = colors;
	}
	
	@Override
	public void setDirections(MainActivity main, int level){
   	String directions = "";
   	switch(level){
   		case 1: directions = "Swipe the color in the middle to the matching color in the corners!";
   				break;
   		case 2: directions = "level 2";
   				break;
   		case 3: directions = "level 3";
				break;
   		case 4: directions = "level 4";
				break;
   		case 5: directions = "level 5";
				break;
   		case 6: directions = "level 6";
				break;
   		case 7: directions = "level 7";
				break;
   		case 8: directions = "level 8";
				break;
   		case 9: directions = "level 9";
				break;
   		default: directions = "";
   				break;
   		}
		
		if(!directions.isEmpty()) super.showDirections(main, directions);
   }
}
