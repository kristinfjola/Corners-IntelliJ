/**
 * @author: Kristin Fjola Tomasdottir
 * @date	05.02.2015
 * @goal: 	Delivers levels for the category Colors
 */
package logic;

import java.util.Random;
import boxes.Box;
import boxes.ColorBox;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
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

	BitmapFont bmFontB;
	BitmapFont bmFontW;
	
	/**
	 * 	Creates a new Colors category, delivers a question and possible answers
	 */
	public Colors(){
		type = "Colors";
		qWidth = 100;
		qHeight = 100;
	}
	
	@Override
	public void setUpBoxes() {
		qWidth = playScreenWidth/3;
		qHeight = playScreenHeight/6;
		int[] xcoords = {0, 0, playScreenWidth-qWidth, playScreenWidth-qWidth}; 
		int[] ycoords = {0, playScreenHeight-qHeight, playScreenHeight-qHeight, 0}; 

		bmFontB = (this.skin).getFont(this.screenSizeGroup+"-M-Black");
		bmFontB.setColor(Color.BLACK);
		bmFontW = (this.skin).getFont(this.screenSizeGroup+"-M-White");
		bmFontW.setColor(Color.WHITE);
		//answers
 	    answers = new Array<Box>();
 	    for(int i = 0; i < 4; i++){ 	    	
 	    	ColorBox box = new ColorBox(qWidth, qHeight, Color.WHITE, "nafn", bmFontB);
 	 	    box.getRec().x = xcoords[i];
 	 	  	box.getRec().y = ycoords[i];
 	 	  	box.setTexture(new Texture(Gdx.files.internal("colorBoxes/aBox"+(i+1)+".png")));
 	 	    answers.add(box);
 	    }
 	    
 	    //question
 		question = new ColorBox(qWidth + playScreenWidth/12, qHeight, Color.WHITE, "nafn", bmFontB);
  	    question.getRec().x = screenWidth / 2 - qWidth / 2;
  	    question.getRec().y = screenHeight / 2 - qHeight / 2;
  	    question.setTexture(new Texture(Gdx.files.internal("colorBoxes/qBox.png")));
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
			//State that this color is in a box.
			alreadyAnAnswer[random] = true;
			((ColorBox) answer).setColor(colors[random].getColor());
			((ColorBox) answer).setName(colors[random].getName());
			((ColorBox) answer).setBackground(colors[random].getColor());
			((ColorBox) answer).setLight(colors[random].getLight());
			if(isText)
				((ColorBox) answer).setText(colors[random].getName());
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
		((ColorBox) answers.get(randomBox)).setBackground(color.getColor());
		if(isText)
			((ColorBox) answers.get(randomBox)).setText(color.getName());
	}
	
	private void generateTrickQuestion(CorColor color, boolean changeBackground, boolean changeText){
		Random rand = new Random();
		int randomBox = rand.nextInt(4);
		while(color.getColor().equals(((ColorBox)answers.get(randomBox)).getColor())){
			rand = new Random();
			randomBox = rand.nextInt(4);
		}
		 
		boolean light = ((ColorBox) question).getLight();
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
			((ColorBox) question).setBmFont(bmFontB);
		}
		else{
			((ColorBox) question).setBmFont(bmFontW);
		}
	}
	
	private void generateTrickAnswers(CorColor[] colors, CorColor color, Boolean changeBackground, Boolean changeText){
		boolean[] alreadyAnAnswer = new boolean[colors.length];
		
		if(changeBackground){
			alreadyAnAnswer = new boolean[colors.length];
			for(int i = 0; i < 4; i++){
				Random rand = new Random();
				int random = rand.nextInt(colors.length);
				//Check if this color is already in a box
				while(alreadyAnAnswer[random]){
					rand = new Random();
					random = rand.nextInt(colors.length);
				}
				alreadyAnAnswer[random] = true;
				((ColorBox)answers.get(i)).setBackground(colors[random].getColor());
				boolean light = colors[random].getLight();
				if(light){
					((ColorBox)answers.get(i)).setBmFont(bmFontB);
				}
				else{
					((ColorBox)answers.get(i)).setBmFont(bmFontW);
				}
			}
		}
		if(changeText){
			alreadyAnAnswer = new boolean[colors.length];
			for(int i = 0; i < 4; i++){
				Random rand = new Random();
				int random = rand.nextInt(colors.length);
				//Check if this color is already in a box
				while(alreadyAnAnswer[random]){
					rand = new Random();
					random = rand.nextInt(colors.length);
				}
				alreadyAnAnswer[random] = true;
				((ColorBox)answers.get(i)).setText(colors[random].getName());
				boolean light = ((ColorBox)answers.get(i)).getLight();
				if(light){
					((ColorBox)answers.get(i)).setBmFont(bmFontB);
				}
				else{
					((ColorBox)answers.get(i)).setBmFont(bmFontW);
				}
			}
		}
	}
	
	/**
	 * @param to - the count of colors to get
	 * @return CorColor array with colors from 0 to 'to'
	 */
	public CorColor[] getColors(int to){
		if(to == -1){
			to = colorColors.length;
		}
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
		generateTrickQuestion(questions[randomColor], false, true);
	}
	
	@Override
	public void generate3rdLevelQuestions() {
		//Background to text on answers, random backgrounds on answers
		CorColor[] questions = getColors(13);
		Random rand = new Random();
		int randomColor = rand.nextInt(questions.length);
		
		generateQuestion(questions[randomColor], true);
		generateAnswers(questions, false);
		generateCorrectAnswer(questions[randomColor], false);
		generateTrickQuestion(questions[randomColor], true, false);
	}
	
	@Override
	public void generate4thLevelQuestions() {
		//
		CorColor[] questions = getColors(13);
		Random rand = new Random();
		int randomColor = rand.nextInt(questions.length);

		generateQuestion(questions[randomColor], true);
		generateAnswers(questions, true); //texti á svörin
		generateCorrectAnswer(questions[randomColor], true); //Setja eitt rétt svar, og setja textann á það
		generateTrickQuestion(questions[randomColor], true, false); //breyta bakgrunni á spurningu á spurningu
		generateTrickAnswers(questions, questions[randomColor], false, true); //breyta texta á spurningum
	}
	
	@Override
	public void generate5thLevelQuestions() {
		//White background, random text, color of text to color
		CorColor[] questions = getColors(17);
		Random rand = new Random();
		int randomColor = rand.nextInt(questions.length);

		generateQuestion(questions[randomColor], true);
		generateAnswers(questions, true); //texti á svörin
		generateCorrectAnswer(questions[randomColor], true); //Setja eitt rétt svar, og setja textann á það
		generateTrickQuestion(questions[randomColor], false, true); //breyta texta á spurningu
		generateTrickAnswers(questions, questions[randomColor], true, false); //breyta bakgrunni á spurningum
	}
	
	@Override
	public void generate6thLevelQuestions() {
		//Colored background, colored text, 
		CorColor[] questions = getColors(-1);
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
		CorColor[] questions = getColors(-1);
		Random rand = new Random();
		int randomColor = rand.nextInt(questions.length);

		generateQuestion(questions[randomColor], true);
		generateAnswers(questions, true); //texti á svörin
		generateCorrectAnswer(questions[randomColor], true); //Setja eitt rétt svar, og setja textann á það
		generateTrickQuestion(questions[randomColor], true, false); //breyta bakgrunni á spurningu á spurningu
		generateTrickAnswers(questions, questions[randomColor], false, true); //breyta texta á spurningum
	}
	
	@Override
	public void generate8thLevelQuestions() {

		CorColor[] questions = getColors(-1);
		Random rand = new Random();
		int randomColor = rand.nextInt(questions.length);

		generateQuestion(questions[randomColor], true);
		generateAnswers(questions, true); //texti á svörin
		generateCorrectAnswer(questions[randomColor], true); //Setja eitt rétt svar, og setja textann á það
		generateTrickQuestion(questions[randomColor], false, true); //breyta texta á spurningu
		generateTrickAnswers(questions, questions[randomColor], true, false); //breyta bakgrunni á spurningum
	}

	@Override
	public void generate9thLevelQuestions() {

		CorColor[] questions = getColors(-1);
		Random rand = new Random();
		int randomColor = rand.nextInt(questions.length);

		generateQuestion(questions[randomColor], true);
		generateAnswers(questions, true); //texti á svörin
		generateCorrectAnswer(questions[randomColor], true); //Setja eitt rétt svar, og setja textann á það
		generateTrickQuestion(questions[randomColor], false, true); //breyta texta á spurningu
		generateTrickAnswers(questions, questions[randomColor], true, false); //breyta bakgrunni á spurningum
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
   		case 2: directions = "Swipe the background color in the middle to the matching color int the corners!";
   				break;
   		case 3: directions = "Swipe the color name in the middle to the matching color in the corners!";
				break;
   		case 4: directions = "Swipe the color name in the middle to the matching background color in the corners!";
				break;
   		case 5: directions = "Swipe the background color in the middle to the matching color name in the corners!";
				break;
   		case 6: directions = "Swipe the color name in the middle to the matching color in the corners!";
				break;
   		case 7: directions = "Swipe the color name in the middle to the matching background color in the corners!";
				break;
   		case 8: directions = "Swipe the background color in the middle to the matching color name in the corners!";
				break;
   		case 9: directions = "Swipe the background color in the middle to the matching color name in the corners!";
				break;
   		default: directions = "";
   				break;
   		}
		
		if(!directions.isEmpty()) super.showDirections(main, directions);
   }
}
