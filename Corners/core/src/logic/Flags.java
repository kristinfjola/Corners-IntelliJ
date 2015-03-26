/**
 * @author: Kristin Fjola Tomasdottir
 * @date	05.02.2015
 * @goal: 	Delivers levels for the category Flags
 */
package logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import boxes.Box;
import boxes.FlagBox;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

public class Flags extends Category{
	
	//String[] countries = {"Canada", "Ethiopia", "UK", "Sweeden", "Iceland"};
	//String[] capitals = {"Höfuðborg", "Höfuðborg", "London", "Stokkhólmur", "Reykjavík"};
	
	/**
	 * 	Creates a new Flags category, delivers a question and possible answers
	 */
	public Flags(){
		type = "Flags";
		qWidth = 120;
		qHeight = 80;
		int[] xcoords = {0, 0, screenWidth-qWidth, screenWidth-qWidth}; 
		int[] ycoords = {0, screenHeight-qHeight, screenHeight-qHeight, 0}; 
		
		
		/*//question
 	    question = new FlagBox(qWidth, qHeight, "Sweeden", "Stokkhólmur");
 	    question.getRec().x = screenWidth / 2 - qWidth / 2;
 	    question.getRec().y = screenHeight / 2 - qHeight / 2;
 	    question.setTexture(new Texture(Gdx.files.internal("flags/Sweeden.png")));
 	    
 	    //answers
 	    answers = new Array<Box>();
 	    for(int i = 0; i < 4; i++){
 	    	FlagBox box = new FlagBox(qWidth, qHeight, countries[i], capitals[i]);
 	 	    box.getRec().x = xcoords[i];
 	 	    box.getRec().y = ycoords[i];
 	 	    box.setTexture(new Texture(Gdx.files.internal("flags/" + countries[i] + ".png")));
 	 	    answers.add(box);
 	    }*/
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
	 * Delivers a question and possible answers
	 */
	public void setUpBoxes() {		
		int[] xcoords = {0, 0, playScreenWidth-qWidth, playScreenWidth-qWidth}; 
		int[] ycoords = {0, playScreenHeight-qHeight, playScreenHeight-qHeight, 0};
		//answers
 	    answers = new Array<Box>();
 	    for(int i = 0; i < 4; i++){
 	    	FlagBox box = new FlagBox(qWidth, qHeight, "Hey", "Hey");
 	 	    box.getRec().x = xcoords[i];
 	 	  	box.getRec().y = ycoords[i];
 	 	  	box.setTexture(new Texture(Gdx.files.internal("flags/Ethiopia.png")));
 	 	    answers.add(box);
 	    }
 	    
 	    //question
 		question = new FlagBox(qWidth, qHeight, "Bla", "Bla");
  	    question.getRec().x = screenWidth / 2 - qWidth / 2;
  	    question.getRec().y = screenHeight / 2 - qHeight / 2;
  	    question.setTexture(new Texture(Gdx.files.internal("flags/UK.png")));
	}

	
	public void generateQuestion(Flag country, Boolean isText){
		((FlagBox) question).setCountry(country.getCountry());
		((FlagBox) question).setCapital(country.getCapital());
		((FlagBox) question).setTexture(new Texture(Gdx.files.internal("flags/"+country.getFlag())));
	}
	
	public void generateAnswers(Flag[] flags, Boolean isText){
		// answers
		boolean[] alreadyAnAnswer = new boolean[flags.length];
		for(Box answer : answers){
			Random rand = new Random();
			int random = rand.nextInt(7);
			//Check if this country is already in a box
			while(alreadyAnAnswer[random]){
				rand = new Random();
				random = rand.nextInt(7);
			}
			//State that this country is in a box.
			alreadyAnAnswer[random] = true;
			//TODO: Check if flag is already in a box
			((FlagBox) answer).setCountry(flags[random].getCountry());
			((FlagBox) answer).setCapital(flags[random].getCountry());
			((FlagBox) answer).setTexture(new Texture(Gdx.files.internal("flags/"+flags[random].getFlag())));
		}
	}
	
	public void generateCorrectAnswer(Flag country, Boolean isText){
		for(int i = 0; i < 4; i++){
			Boolean isAlreadyInBox = ((FlagBox) answers.get(i)).getCountry().equals(country.getCountry());
			if(isAlreadyInBox) return;
		}
		
		Random rand = new Random();
		int randomBox = rand.nextInt(4);
		((FlagBox)  answers.get(randomBox)).setCountry(country.getCountry());
		((FlagBox)  answers.get(randomBox)).setCapital(country.getCapital());
		((FlagBox)  answers.get(randomBox)).setTexture(new Texture(Gdx.files.internal("flags/"+country.getFlag())));
	}
	
	public Flag[] getScandinavia(){
		String[] countries = new String[]{"Sweeden", "Norway", "Denmark", "Finland", "Iceland", "Greenland", "Faroe Islands"};
		String [] capitals = new String[]{"Stockholm", "Oslo", " Copenhagen", "Helsinki", "Reykjavík", "Nuuk", "Tórshavn"};
		Flag[] flags = new Flag[7];
		for(int i = 0; i < countries.length; i++){
			flags[i] = new Flag(countries[i], capitals[i], countries[i] + ".png");
		}
		return flags;
	}

	@Override
	public void generate1stLevelQuestions(){
		//Scandinavia flags + name question
		Flag[] countries = getScandinavia();

		Random rand = new Random();
		int randomFlag = rand.nextInt(countries.length);
		
		generateQuestion(countries[randomFlag], false);
		generateAnswers(countries, false);
		generateCorrectAnswer(countries[randomFlag], false);
	}
	
	@Override
	public void generate2ndLevelQuestions(){
		//Scandinavia flags + capitals question
		Flag[] countries = getScandinavia();
		
		Random rand = new Random();
		int randomFlag = rand.nextInt(countries.length);
		
		generateQuestion(countries[randomFlag], true);
		generateAnswers(countries, false);
		generateCorrectAnswer(countries[randomFlag], false);
	}
	
	@Override
	public void generate3rdLevelQuestions() {
		// Europe flags
		Flag[] countries = getScandinavia();
		
		generateQuestion(countries[2], false);
		generateAnswers(countries, false);
		generateCorrectAnswer(countries[2], false);
	}
	
	
	/**
	 * @return countries selection
	 */
	/*public String[] getCountries() {
		return countries;
	}

	/**
	 * @param countries
	 *
	public void setCountries(String[] countries) {
		this.countries = countries;
	}
	
	public String[] getCapitals() {
		return capitals;
	}
	
	public void setCapitals(String[] capitals) {
		this.capitals = capitals;
	}
	*/
}
