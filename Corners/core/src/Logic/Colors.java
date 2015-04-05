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

public class Colors extends Category {

	Color[] colorColors = {Color.BLUE, Color.YELLOW, Color.GREEN, Color.RED,  Color.PURPLE, Color.PINK, Color.BLACK,
			Color.MAGENTA, Color.MAROON, Color.NAVY, Color.OLIVE, Color.ORANGE, Color.CYAN, 
			Color.TEAL, Color.WHITE, Color.DARK_GRAY, Color.LIGHT_GRAY};
	String[] colorNames = { "Blue", "Yellow", "Green", "Red", "Purple", "Pink", "Black",
			"Magneta", "Maroon", "Navy", "Olive", "Orange", "Cyan",
			"Teal", "White", "Dark gray", "Light gray" };
	Pixmap pm;
	
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
	
	public void setUpBoxes() {
		qWidth = playScreenWidth/4;
		qHeight = playScreenHeight/6;
		int[] xcoords = {0, 0, playScreenWidth-qWidth, playScreenWidth-qWidth}; 
		int[] ycoords = {0, playScreenHeight-qHeight, playScreenHeight-qHeight, 0}; 

		BitmapFont bmFont = (this.skin).getFont(this.screenSizeGroup+"-M");
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

	
	private void generateQuestion(CorColor color, boolean isText, Color background) {
		((ColorBox) question).setColor(color.getColor());
		((ColorBox) question).setName(color.getName());
		((ColorBox) question).setBackground(background);
		if(isText)
			((ColorBox) question).setText(color.getName());
	}
	
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
			if(isText)
				((ColorBox) answer).setText(colors[random].getName());
			else{
				((ColorBox) answer).setBackground(colors[random].getColor());
			}
				
		}
		
	}
	

	private void generateCorrectAnswer(CorColor color, boolean isText) {
		for(int i = 0; i < 4; i++){
			Boolean isAlreadyInBox = ((ColorBox) answers.get(i)).getColor().equals(color.getColor());
			if(isAlreadyInBox) return;
		}
		
		Random rand = new Random();
		int randomBox = rand.nextInt(4);
		((ColorBox)  answers.get(randomBox)).setColor(color.getColor());
		((ColorBox)  answers.get(randomBox)).setName(color.getName());
		if(isText)
			((ColorBox) answers.get(randomBox)).setText(color.getName());
		else{
			((ColorBox) answers.get(randomBox)).setBackground(color.getColor());
		}
	}
	
	public CorColor[] getColors(int to){
		CorColor[] colorsDTO = new CorColor[to];
		for(int i = 0; i < to; i++){
			colorsDTO[i] = new CorColor(colorColors[i], colorNames[i]);
		}
		return colorsDTO;
	}
	
	@Override
	public void generate1stLevelQuestions() {
		//White background, text to color
		CorColor[] questions = getColors(7);
		Random rand = new Random();
		int randomColor = rand.nextInt(questions.length);
		Color background = Color.WHITE;
		
		generateQuestion(questions[randomColor], true, background);
		generateAnswers(questions, false);
		generateCorrectAnswer(questions[randomColor], false);
	}

	@Override
	public void generate2ndLevelQuestions() {
		//Random background, text to color, colored text
		CorColor[] questions = getColors(7);
		Random rand = new Random();
		int randomColor = rand.nextInt(questions.length);
		
		Random rand2 = new Random();
		int randomBackground = rand2.nextInt(questions.length);
		Color background = questions[randomBackground].getColor();
		
		generateQuestion(questions[randomColor], true, background);
		generateAnswers(questions, false);
		generateCorrectAnswer(questions[randomColor], false);
	}
	
	@Override
	public void generate3rdLevelQuestions() {
		//Random colored background, black text, text to color
		CorColor[] questions = getColors(7);
		Random rand = new Random();
		int randomColor = rand.nextInt(questions.length);
		Color background = Color.WHITE;
		
		generateQuestion(questions[randomColor], true, background);
		generateAnswers(questions, false);
		generateCorrectAnswer(questions[randomColor], false);
	}
	
	@Override
	public void generate4thLevelQuestions() {
		//Colored background, black random text, background to color
		CorColor[] questions = getColors(13);
		Random rand = new Random();
		int randomColor = rand.nextInt(questions.length);
		Color background = Color.WHITE;
		
		generateQuestion(questions[randomColor], true, background);
		generateAnswers(questions, false);
		generateCorrectAnswer(questions[randomColor], false);
	}
	
	@Override
	public void generate5thLevelQuestions() {
		//White background, random text, color of text to color
		CorColor[] questions = getColors(13);
		Random rand = new Random();
		int randomColor = rand.nextInt(questions.length);
		Color background = Color.WHITE;
		
		generateQuestion(questions[randomColor], true, background);
		generateAnswers(questions, false);
		generateCorrectAnswer(questions[randomColor], false);
	}
	
	@Override
	public void generate6thLevelQuestions() {
		//Colored background, colored text, 
		CorColor[] questions = getColors(13);
		Random rand = new Random();
		int randomColor = rand.nextInt(questions.length);
		Color background = Color.WHITE;
		
		generateQuestion(questions[randomColor], true, background);
		generateAnswers(questions, false);
		generateCorrectAnswer(questions[randomColor], false);
	}
	
	@Override
	public void generate7thLevelQuestions() {
		//
		CorColor[] questions = getColors(17);
		Random rand = new Random();
		int randomColor = rand.nextInt(questions.length);
		Color background = Color.WHITE;
		
		generateQuestion(questions[randomColor], true, background);
		generateAnswers(questions, false);
		generateCorrectAnswer(questions[randomColor], false);
	}
	
	@Override
	public void generate8thLevelQuestions() {

		CorColor[] questions = getColors(17);
		Random rand = new Random();
		int randomColor = rand.nextInt(questions.length);
		Color background = Color.WHITE;
		
		generateQuestion(questions[randomColor], true, background);
		generateAnswers(questions, false);
		generateCorrectAnswer(questions[randomColor], false);
	}

	@Override
	public void generate9thLevelQuestions() {

		CorColor[] questions = getColors(17);
		Random rand = new Random();
		int randomColor = rand.nextInt(questions.length);
		Color background = Color.WHITE;
		
		generateQuestion(questions[randomColor], true, background);
		generateAnswers(questions, false);
		generateCorrectAnswer(questions[randomColor], false);
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
}
