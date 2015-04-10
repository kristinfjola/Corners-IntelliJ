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
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Array;
import com.corners.game.MainActivity;

public class Flags extends Category{
	
	List<String> previousQuestions = new ArrayList<String>();
	
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
	
	@Override
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
	
	public void adjustFontSize(String text, FlagBox box) {
		if(text.length() >= 14) {
			BitmapFont bmFont = (this.skin).getFont(this.screenSizeGroup+"-S");
			bmFont.setColor(Color.BLACK);
			box.setBmFont(bmFont);
		}
	}
	
	public String replaceAndInString(String stringBefore) {
		return (stringBefore.contains(" and ")) ? stringBefore.replace(" and ",  " & ") : stringBefore;
	}

	
	/**
	 * @param country
	 * @param isCapital
	 */
	public void generateQuestion(Flag country, Boolean isCapital){
		((FlagBox) question).setCountry(country.getCountry());
		((FlagBox) question).setCapital(country.getCapital());
		String quest = "";
		if(isCapital) {
			quest = country.getCapital();
			((FlagBox) question).setText(quest);
		} else {
			quest = country.getCountry();
			quest = replaceAndInString(quest);
			((FlagBox) question).setText(quest);
		}
		adjustFontSize(quest, (FlagBox) question);
		previousQuestions.add(country.getCountry());
	}
	
	/**
	 * @param flags
	 * @param isText
	 */
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
			((FlagBox) answer).setCountry(flags[random].getCountry());
			((FlagBox) answer).setCapital(flags[random].getCountry());
			if(isText) {
				String answ = flags[random].getCountry();
				answ = replaceAndInString(answ);
				((FlagBox) answer).setText(answ);
				adjustFontSize(answ, (FlagBox) answer);
			} else {
				((FlagBox) answer).setTexture(new Texture(Gdx.files.internal("flags/"+flags[random].getFlag())));
		
			}
		}
	}
	
	/**
	 * @param country
	 * @param isText
	 */
	public void generateCorrectAnswer(Flag country, Boolean isText){
		for(int i = 0; i < 4; i++){
			Boolean isAlreadyInBox = ((FlagBox) answers.get(i)).getCountry().equals(country.getCountry());
			if(isAlreadyInBox) return;
		}
		
		Random rand = new Random();
		int randomBox = rand.nextInt(4);
		((FlagBox)  answers.get(randomBox)).setCountry(country.getCountry());
		((FlagBox)  answers.get(randomBox)).setCapital(country.getCapital());
		if(isText) {
			String answ = country.getCountry();
			answ = replaceAndInString(answ);
			((FlagBox) answers.get(randomBox)).setText(answ);
			adjustFontSize(answ, (FlagBox) answers.get(randomBox));
		} else {
			((FlagBox) answers.get(randomBox)).setTexture(new Texture(Gdx.files.internal("flags/"+country.getFlag())));
		}
	}
	
	/**
	 * @param to - count of flags
	 * @return Flag array with flags from 0 to 'to'
	 */
	public Flag[] allFlags(int from, int to){
		String[] countries = new String[]{
				 "Sweden", "Norway", "Denmark", "Finland", "Iceland", "Greenland", "Faroe Islands", "Aland Islands",
				 "United Kingdom", "Germany", "France",
				 // ^ scandinavia + easy europe 11
				 "Greece", "Spain", "Austria", "Belgium", "Ireland", "Italy", "Luxembourg", "Netherlands", 
				 "Portugal", "Switzerland", "Scotland", "Wales", "Vatican City", "Andorra", "San Marino",
				 // ^ western europe 26
				 "Albania", "Belarus", "Bosnia and Herzegovina",
				 "Bulgaria", "Croatia", "Czech Republic", "Estonia", "Gibraltar", "Hungary", "Kosovo", "Latvia",
				 "Liechtenstein", "Lithuania", "Macedonia", "Malta", "Moldova", "Monaco", "Montenegro", 
				 "Poland", "Romania", "Russia", "Serbia", "Slovakia", "Slovenia", "Ukraine", "Armenia",
				 // ^ europe 52
				 "Ethiopia",  "Bahamas", "China", "Cuba", "East Timor", "Kiribati", "Lebanon", "Puerto Rico", 
				 "South Africa", "Thailand", "Tonga", "United States", "Vanuatu", "Venezuela"
				 // ^ all countries 66
				 };
		
		String[] capitals = new String[]{
				"Stockholm", "Oslo", " Copenhagen", "Helsinki", "Reykjavik", "Nuuk", "Torshavn", "Mariehamn",
				"London", "Berlin", "Paris", 
				// ^ scandinavia + easy europe 11
				"Athens", "Madrid", "Vienna", "Brussels", "Dublin", "Rome", "Luxembourg", "Amsterdam", 
				"Lisabon", "Bern", "Edinburgh", "Cardiff", "Vatican City", "Andorra la Vella", "San Marino",
				// ^ western europe 26
				"Tirana", "Minsk", "Sarajevo",
				"Sofia", "Zagreb", "Prague", "Tallinn", "Gibraltar", "Budapest", "Pristina", "Riga", 
				"Vaduz", "Vilnius", "Skopje", "Valletta", "Chisinau", "Monaco", "Podgorica",
				"Warsaw", "Bucharest", "Moscow", "Belgrade", "Bratislava", "Lubljana", "Kiev", "Yerevan", 
				// ^ europe 52
				"Addis Ababa", "Nassau", "Beijing", "Havana", "Dili","South Tarawa", "Beirut", "San Juan", 
				"Cape Town", "Bangkok", "Nuku'alofa", "Washington, D.C.", "Port Vila", "Caracas"
				// ^ all countries 66
				};
		if(to == -1){
			to = countries.length;
		}
		Flag[] flags = new Flag[to-from];
		for(int i = from; i < to; i++){
			flags[i-from] = new Flag(countries[i], capitals[i], countries[i] + ".png");
		}
		
		return flags;
	}
	
	/**
	 * Group together Scandinavian countries
	 * @return Flag array with the Scandinavian countries
	 */
	public Flag[] getScandinaviaAndEasyEurope(){
		Flag[] flags = allFlags(0, 11);
		return flags;
	}
	
	/**
	 * Group together Western countries
	 * @return Flag array with western Europe flags
	 */
	public Flag[] getWesternEurope(){
		Flag[] flags = allFlags(0, 26);
		return flags;
	}
	
	/**
	 * Group together Europe countries
	 * @return Flag array with Europe flags
	 */
	public Flag[] getEurope(){
		Flag[] flags = allFlags(0, 52);
		return flags;
	}
	
	/**
	 * Get all flags available
	 * @return Flag array with all the flags
	 */
	public Flag[] getAllCountries(){
		Flag[] flags = allFlags(0, -1);
		return flags;
	}
	
	/**
	 * Get all flags available
	 * @return Flag array with all the flags
	 */
	public Flag[] getDifficultCountries(){
		Flag[] flags = allFlags(11, -1);
		
		return flags;
	}

	@Override
	public void generate1stLevelQuestions(){
		//Scandinavian + easy europe flags + name question
		Flag[] countries = getScandinaviaAndEasyEurope();

		int randomFlag = findRandomFlag(countries);
		generateQuestion(countries[randomFlag], false);
		generateAnswers(countries, false);
		generateCorrectAnswer(countries[randomFlag], false);
	}
	
	/**
	 * @param countries
	 * @return a random flag that has not been used already in current level
	 */
	public int findRandomFlag(Flag[] countries){
		Random rand = new Random();
		int randomFlag = rand.nextInt(countries.length);
		while(previousQuestions.contains(countries[randomFlag].getCountry())){
			randomFlag = rand.nextInt(countries.length);
		}
		return randomFlag;
	}
	
	@Override
	public void generate2ndLevelQuestions(){
		//Scandinavian flags + capitals question
		Flag[] countries = getScandinaviaAndEasyEurope();
		
		int randomFlag = findRandomFlag(countries);
		generateQuestion(countries[randomFlag], true);
		generateAnswers(countries, false);
		generateCorrectAnswer(countries[randomFlag], false);
	}
	
	@Override
	public void generate3rdLevelQuestions() {
		// Western Europe flags
		Flag[] countries = getWesternEurope();
		
		int randomFlag = findRandomFlag(countries);
		generateQuestion(countries[randomFlag], false);
		generateAnswers(countries, false);
		generateCorrectAnswer(countries[randomFlag], false);
	}
	
	@Override
	public void generate4thLevelQuestions() {
		//Western Europe flags capitals
		Flag[] countries = getWesternEurope();
		
		int randomFlag = findRandomFlag(countries);
		generateQuestion(countries[randomFlag], true);
		generateAnswers(countries, false);
		generateCorrectAnswer(countries[randomFlag], false);
	}
	
	@Override
	public void generate5thLevelQuestions() {
		//Europe flags capitals
		Flag[] countries = getEurope();
		
		int randomFlag = findRandomFlag(countries);
		generateQuestion(countries[randomFlag], false);
		generateAnswers(countries, false);
		generateCorrectAnswer(countries[randomFlag], false);
	}
	
	@Override
	public void generate6thLevelQuestions() {
		//Europe flags capitals
		Flag[] countries = getEurope();
		
		int randomFlag = findRandomFlag(countries);
		generateQuestion(countries[randomFlag], true);
		generateAnswers(countries, false);
		generateCorrectAnswer(countries[randomFlag], false);
	}
	
	@Override
	public void generate7thLevelQuestions() {
		//Europe countries and capitals, no flags
		Flag[] countries = getEurope();
		
		int randomFlag = findRandomFlag(countries);
		generateQuestion(countries[randomFlag], true);
		generateAnswers(countries, true);
		generateCorrectAnswer(countries[randomFlag], true);
	}
	
	@Override
	public void generate8thLevelQuestions() {
		//All countries flags and countries
		Flag[] countries = getDifficultCountries();
		
		int randomFlag = findRandomFlag(countries);
		generateQuestion(countries[randomFlag], false);
		generateAnswers(countries, false);
		generateCorrectAnswer(countries[randomFlag], false);
	}
	
	@Override
	public void generate9thLevelQuestions() {
		//All countries flags and capitals
		Flag[] countries = getDifficultCountries();
		
		int randomFlag = findRandomFlag(countries);
		generateQuestion(countries[randomFlag], true);
		generateAnswers(countries, false);
		generateCorrectAnswer(countries[randomFlag], false);
	}
	
	@Override
	 public void setDirections(MainActivity main, int level){
    	String directions = "";
		if(main.data.getLevelsFinished(this) == 0 && level == 1){
			directions = "Swipe the country name in the middle to the correct flag in the corners!";
		} else if(main.data.getLevelsFinished(this) == 1 && level == 2){
			directions = "Swipe the capital name to the correct country flag!";
		} else if(main.data.getLevelsFinished(this) == 6 && level == 7){
			directions = "Swipe the capital name to the correct country name!";
		}
		
		if(!directions.isEmpty()) super.showDirections(main, directions);
    }
	
	@Override
	public void refreshQuestions(){
		this.previousQuestions = new ArrayList<String>();
	}
}
