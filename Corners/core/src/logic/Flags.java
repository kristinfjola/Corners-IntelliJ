/**
 * @author: Kristin Fjola Tomasdottir
 * @date	05.02.2015
 * @goal: 	Delivers levels for the category Flags
 */
package logic;

import java.util.Random;

import boxes.Box;
import boxes.FlagBox;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Array;

public class Flags extends Category{
	/**
	 * 	Creates a new Flags category, delivers a question and possible answers
	 */
	public Flags(){
		type = "Flags";
		qWidth = 120;
		qHeight = 80;
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
	
	@Override
	public Box checkIfHitBox() {
		for(Box answer : answers){
			FlagBox a = (FlagBox) answer;
			FlagBox q = (FlagBox) question;
			if(a.getRec().overlaps(q.getRec())){
				return a;
			}
		}
		return null;
	}
	
	/**
	 * Delivers a question and possible answers
	 */
	public void setUpBoxes() {	
		qWidth = playScreenWidth*2/7;
		qHeight = playScreenHeight/8;
		
		int[] xcoords = {0, 0, playScreenWidth-qWidth, playScreenWidth-qWidth}; 
		int[] ycoords = {0, playScreenHeight-qHeight, playScreenHeight-qHeight, 0};
		BitmapFont bmFont = (this.skin).getFont(this.screenSizeGroup+"-M");
		//answers
 	    answers = new Array<Box>();
 	    for(int i = 0; i < 4; i++){
 	    	FlagBox box = new FlagBox(qWidth, qHeight, "Hey", "Hey", bmFont);
 	 	    box.getRec().x = xcoords[i];
 	 	  	box.getRec().y = ycoords[i];
 	 	  	box.setTexture(new Texture(Gdx.files.internal("mathBoxes/aBox" + (i + 1) + ".png")));
 	 	    answers.add(box);
 	    }
 	    
 	    //question
 		question = new FlagBox(qWidth+playScreenWidth/6, qHeight, "Bla", "Bla", bmFont);
  	    question.getRec().x = screenWidth / 2 - qWidth / 2;
  	    question.getRec().y = screenHeight / 2 - qHeight / 2;
  	    question.setTexture(new Texture(Gdx.files.internal("mathBoxes/qBox.png")));
	}

	
	public void generateQuestion(Flag country, Boolean isCapital){
		((FlagBox) question).setCountry(country.getCountry());
		((FlagBox) question).setCapital(country.getCapital());
		if(isCapital)
			((FlagBox) question).setText(country.getCapital());
		else
			((FlagBox) question).setText(country.getCountry());
			//((FlagBox) question).setTexture(new Texture(Gdx.files.internal("flags/"+country.getFlag())));
	}
	
	public void generateAnswers(Flag[] flags, Boolean isText){
		// answers
		boolean[] alreadyAnAnswer = new boolean[flags.length];
		for(Box answer : answers){
			Random rand = new Random();
			int random = rand.nextInt(flags.length);
			//Check if this country is already in a box
			while(alreadyAnAnswer[random]){
				rand = new Random();
				random = rand.nextInt(flags.length);
			}
			//State that this country is in a box.
			alreadyAnAnswer[random] = true;
			//TODO: Check if flag is already in a box
			((FlagBox) answer).setCountry(flags[random].getCountry());
			((FlagBox) answer).setCapital(flags[random].getCountry());
			if(isText)
				((FlagBox) answer).setText(flags[random].getCountry());
			else
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
		if(isText)
			((FlagBox) answers.get(randomBox)).setText(country.getCountry());
		else
			((FlagBox) answers.get(randomBox)).setTexture(new Texture(Gdx.files.internal("flags/"+country.getFlag())));
	}
	
	public Flag[] allFlags(int to){
		String[] countries = new String[]{"Sweden", "Norway", "Denmark", "Finland", "Iceland", "Greenland", "Faroe Islands", "Aland Islands",
				 "Greece", "Spain", "France", "Germany", "Austria", "Belgium", "Ireland", "Italy", "Luxembourg",
				 "Netherlands", "Portugal", "Switzerland", "United Kingdom", "Albania", "Belarus", "Bosnia and Herzegovina",
				 "Bulgaria", "Croatia", "Czech Republic", "Estonia", "Gibraltar", "Hungary", "Kosovo", "Latvia",
				 "Liechtenstein", "Lithuania", "Macedonia", "Malta", "Moldova", "Monaco", "Montenegro", 
				 "Poland", "Romania", "Russia", "San Marino", "Serbia", "Slovakia", "Slovenia", "Ukraine",
				 "Vatican City", "Andorra", "Ethiopia", "Scotland", "Wales"};
		
		String[] capitals = new String[]{"Stockholm", "Oslo", " Copenhagen", "Helsinki", "Reykjavík", "Nuuk", "Tórshavn", "Mariehamn",
				"Athens", "Madrid", "Paris", "Berlin", "Vienna", "Brussels", "Dublin", "Rome", "Luxembourg",
				"Amsterdam", "Lisabon", "Bern", "London", "Tirana", "Minsk", "Sarajevo",
				"Sofia", "Zagreb", "Prague", "Tallinn", "Gibraltar", "Budapest", "Pristina", "Riga", 
				"Vaduz", "Vilnius", "Skopje", "Valletta", "Chisinau", "Monaco", "Podgorica",
				"Warsaw", "Bucharest", "Moscow", "San Marino", "Belgrade", "Bratislava", "Lubljana", "Kiev",
				"Vatican City", "Andorra la Vella", "Addis Ababa", "Edinburgh", "Cardiff"}; //52
		Flag[] flags = new Flag[to];
		for(int i = 0; i < to; i++){
			flags[i] = new Flag(countries[i], capitals[i], countries[i] + ".png");
		}
		
		return flags;
	}
	
	/**
	 * Group toghether Scandinavian countries
	 * @return Flag array with the scandinavian countries
	 */
	public Flag[] getScandinavia(){
		Flag[] flags = allFlags(8);
		return flags;
	}
	
	public Flag[] getWesternEurope(){
		Flag[] flags = allFlags(20);
		return flags;
	}
	
	public Flag[] getEurope(){
		Flag[] flags = allFlags(45);
		return flags;
	}
	
	public Flag[] getAllCountries(){
		Flag[] flags = allFlags(52);
		return flags;
	}

	@Override
	public void generate1stLevelQuestions(){
		//Scandinavian flags + name question
		Flag[] countries = getScandinavia();

		Random rand = new Random();
		int randomFlag = rand.nextInt(countries.length);
		
		generateQuestion(countries[randomFlag], false);
		generateAnswers(countries, false);
		generateCorrectAnswer(countries[randomFlag], false);
	}
	
	@Override
	public void generate2ndLevelQuestions(){
		//Scandinavian flags + capitals question
		Flag[] countries = getScandinavia();
		
		Random rand = new Random();
		int randomFlag = rand.nextInt(countries.length);
		
		generateQuestion(countries[randomFlag], true);
		generateAnswers(countries, false);
		generateCorrectAnswer(countries[randomFlag], false);
	}
	
	@Override
	public void generate3rdLevelQuestions() {
		// Western Europe flags
		Flag[] countries = getWesternEurope();
		
		Random rand = new Random();
		int randomFlag = rand.nextInt(countries.length);
		
		generateQuestion(countries[randomFlag], false);
		generateAnswers(countries, false);
		generateCorrectAnswer(countries[randomFlag], false);
	}
	
	@Override
	public void generate4thLevelQuestions() {
		//Western Europe flags capitals
		Flag[] countries = getWesternEurope();
		
		Random rand = new Random();
		int randomFlag = rand.nextInt(countries.length);
		
		generateQuestion(countries[randomFlag], true);
		generateAnswers(countries, false);
		generateCorrectAnswer(countries[randomFlag], false);
	}
	
	@Override
	public void generate5thLevelQuestions() {
		//Europe flags capitals
		Flag[] countries = getEurope();
		
		Random rand = new Random();
		int randomFlag = rand.nextInt(countries.length);
		
		generateQuestion(countries[randomFlag], false);
		generateAnswers(countries, false);
		generateCorrectAnswer(countries[randomFlag], false);
	}
	
	@Override
	public void generate6thLevelQuestions() {
		//Europe flags capitals
		Flag[] countries = getEurope();
		
		Random rand = new Random();
		int randomFlag = rand.nextInt(countries.length);
		
		generateQuestion(countries[randomFlag], true);
		generateAnswers(countries, false);
		generateCorrectAnswer(countries[randomFlag], false);
	}
	
	@Override
	public void generate7thLevelQuestions() {
		//Europe countries and capitals, no flags
		Flag[] countries = getEurope();
		
		Random rand = new Random();
		int randomFlag = rand.nextInt(countries.length);
		
		generateQuestion(countries[randomFlag], true);
		generateAnswers(countries, true);
		generateCorrectAnswer(countries[randomFlag], true);
	}
	
	@Override
	public void generate8thLevelQuestions() {
		//All countries flags and countries
		Flag[] countries = getAllCountries();
		
		Random rand = new Random();
		int randomFlag = rand.nextInt(countries.length);
		
		generateQuestion(countries[randomFlag], false);
		generateAnswers(countries, false);
		generateCorrectAnswer(countries[randomFlag], false);
	}
	
	@Override
	public void generate9thLevelQuestions() {
		//All countries flags and capitals
		Flag[] countries = getAllCountries();
		
		Random rand = new Random();
		int randomFlag = rand.nextInt(countries.length);
		
		generateQuestion(countries[randomFlag], true);
		generateAnswers(countries, false);
		generateCorrectAnswer(countries[randomFlag], false);
	}
}
