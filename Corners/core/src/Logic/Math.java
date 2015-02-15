/**
 * @author: Kristin Fjola Tomasdottir
 * @date	05.02.2015
 * @goal: 	Delivers levels for the category Math
 */
package logic;

import java.util.Random;

import boxes.Box;
import boxes.ColorBox;
import boxes.MathBox;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.BitmapFont.BitmapFontData;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;

public class Math extends Category{

	int[] numbers = {1, 2, 3, 4};
	private Skin skin;
	private Stage stage;
	/**
	 * 	Creates a new Math category, delivers a question and possible answers
	 */
	public Math(){
		qWidth = 100;
		qHeight = 100;
		int[] xcoords = {0, 0, screenWidth-qWidth, screenWidth-qWidth}; 
		int[] ycoords = {0, screenHeight-qHeight, screenHeight-qHeight, 0}; 
		Random rand = new Random();
		/*
		stage = new Stage();
		skin = new Skin();
		
		Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();
		skin.add("white", new Texture(pixmap));
		
		skin.add("levelButton", skin.newDrawable("white", Color.BLUE), Drawable.class);
		Label label = new Label(Integer.toString(1), skin);
		label.setAlignment(Align.center);	
		
		Image img = new Image(skin.getDrawable("levelButton"));
		
*/
		//answers
 	    answers = new Array<Box>();
 	    for(int i = 0; i < 4; i++){
 	    	Pixmap pm = new Pixmap(qWidth, qHeight, Format.RGBA8888);
 			pm.setColor(Color.YELLOW);
 			pm.fill();
 			answerTextures[i] = new Texture(pm);
 	    	
 	    	MathBox box = new MathBox(qWidth, qHeight, 2, "1+1");
 	 	    box.getRec().x = xcoords[i];
 	 	  	box.getRec().y = ycoords[i];
 	 	    answers.add(box);
 	    }
 	    
 	    //question
 		Pixmap pm = new Pixmap(qWidth, qHeight, Format.RGBA8888);
 		pm.setColor(Color.LIGHT_GRAY);
 		pm.fill();
 		questionTexture = new Texture(pm);
 		question = new MathBox(qWidth, qHeight, 2, "2+2");
  	    question.getRec().x = screenWidth / 2 - qWidth / 2;
  	    question.getRec().y = screenHeight / 2 - qHeight / 2;
	}
}
