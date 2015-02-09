package Logic;

import java.util.Random;

import Boxes.ColorBox;
import Boxes.Box;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.utils.Array;

public class Colors extends Category {

	Color[] colors = {Color.BLUE, Color.YELLOW, Color.GREEN, Color.CYAN, Color.PINK, Color.BLACK,
			Color.MAGENTA, Color.MAROON, Color.NAVY, Color.OLIVE, Color.ORANGE, Color.PURPLE, 
			Color.RED, Color.TEAL};
	Color[] colorsInUse = new Color[4];
	
	public Colors(){
		qWidth = 100;
		qHeight = 100;
		int[] xcoords = {0, 0, screenWidth-qWidth, screenWidth-qWidth}; 
		int[] ycoords = {0, screenHeight-qHeight, screenHeight-qHeight, 0}; 
		Random rand = new Random();
		
		//answers
 	    answers = new Array<Box>();
 	    for(int i = 0; i < 4; i++){
 	    	Pixmap pm = new Pixmap(qWidth, qHeight, Format.RGBA8888);
 	 	    Color randomColor = colors[rand.nextInt(colors.length)];
 			pm.setColor(randomColor);
 			colorsInUse[i] = randomColor;
 			pm.fill();
 			answerTextures[i] = new Texture(pm);
 	    	
 	    	ColorBox box = new ColorBox(qWidth, qHeight, randomColor);
 	 	    box.getRec().x = xcoords[i];
 	 	  	box.getRec().y = ycoords[i];
 	 	    answers.add(box);
 	    }
 	    
 	    //question
 		Pixmap pixmap = new Pixmap(qWidth, qHeight, Format.RGBA8888);
 		Color randomColor = colorsInUse[rand.nextInt(colorsInUse.length)];
 		pixmap.setColor(randomColor);
 		pixmap.fill();
 		questionTexture = new Texture(pixmap);
 		question = new ColorBox(qWidth, qHeight, randomColor);
  	    question.getRec().x = screenWidth / 2 - qWidth / 2;
  	    question.getRec().y = screenHeight / 2 - qHeight / 2;
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

	public Color[] getColors() {
		return colors;
	}

	public void setColors(Color[] colors) {
		this.colors = colors;
	}

	public Color[] getColorsInUse() {
		return colorsInUse;
	}

	public void setColorsInUse(Color[] colorsInUse) {
		this.colorsInUse = colorsInUse;
	}
}
