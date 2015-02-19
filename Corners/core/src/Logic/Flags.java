/**
 * @author: Kristin Fjola Tomasdottir
 * @date	05.02.2015
 * @goal: 	Delivers levels for the category Flags
 */
package logic;

import boxes.Box;
import boxes.ColorBox;
import boxes.FlagBox;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class Flags extends Category{
	
	String[] countries = {"Canada", "Ethiopia", "UK", "Sweeden", "Iceland"};
	
	/**
	 * 	Creates a new Flags category, delivers a question and possible answers
	 */
	public Flags(){
		qWidth = 120;
		qHeight = 80;
		int[] xcoords = {0, 0, screenWidth-qWidth, screenWidth-qWidth}; 
		int[] ycoords = {0, screenHeight-qHeight, screenHeight-qHeight, 0}; 
		
		//question
 	    question = new FlagBox(qWidth, qHeight, "Iceland");
 	    question.getRec().x = screenWidth / 2 - qWidth / 2;
 	    question.getRec().y = screenHeight / 2 - qHeight / 2;
 	    question.setTexture(new Texture(Gdx.files.internal("Iceland.png")));
 	    
 	    //answers
 	    answers = new Array<Box>();
 	    for(int i = 0; i < 4; i++){
 	    	FlagBox box = new FlagBox(qWidth, qHeight, countries[i]);
 	 	    box.getRec().x = xcoords[i];
 	 	    box.getRec().y = ycoords[i];
 	 	    box.setTexture(new Texture(Gdx.files.internal(countries[i] + ".png")));
 	 	    answers.add(box);
 	    }
	}
	
	@Override
	public Box checkIfHitAnswer(){
		for(Box answer : answers){
			FlagBox a = (FlagBox) answer;
			FlagBox q = (FlagBox) question;
			if(a.getRec().overlaps(q.getRec()) && a.getCountry().equals((q.getCountry()))){
				return a;
			}
		}
		return null;
	}

	/**
	 * @return countries selection
	 */
	public String[] getCountries() {
		return countries;
	}

	/**
	 * @param countries
	 */
	public void setCountries(String[] countries) {
		this.countries = countries;
	}
}
