/**
 * @author: Kristin Fjola Tomasdottir
 * @date	05.02.2015
 * @goal: 	Delivers levels for the category Math
 */
package logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import boxes.Box;
import boxes.MathBox;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.utils.Array;

public class Math extends Category{

	int[] numbers = {1, 2, 3, 4};
	/**
	 * 	Creates a new Math category, delivers a question and possible answers
	 */
	public Math(){
		qWidth = 100;
		qHeight = 100;
		int[] xcoords = {0, 0, screenWidth-qWidth, screenWidth-qWidth}; 
		int[] ycoords = {0, screenHeight-qHeight, screenHeight-qHeight, 0}; 

		//answers
 	    answers = new Array<Box>();
 	    for(int i = 0; i < 4; i++){
 	    	Pixmap pm = new Pixmap(qWidth, qHeight, Format.RGBA8888);
 			pm.setColor(Color.YELLOW);
 			pm.fill();
 	    	
 	    	MathBox box = new MathBox(qWidth, qHeight, 2, "1+1");
 	 	    box.getRec().x = xcoords[i];
 	 	  	box.getRec().y = ycoords[i];
 	 	  	box.setTexture(new Texture(pm));
 	 	    answers.add(box);
 	    }
 	    
 	    //question
 		Pixmap pm = new Pixmap(qWidth, qHeight, Format.RGBA8888);
 		pm.setColor(Color.LIGHT_GRAY);
 		pm.fill();
 		question = new MathBox(qWidth, qHeight, 2, "2+2");
  	    question.getRec().x = screenWidth / 2 - qWidth / 2;
  	    question.getRec().y = screenHeight / 2 - qHeight / 2;
  	    question.setTexture(new Texture(pm));
  	    
  	    generateNewQuestion();
	}
	
	@Override
	public Box checkIfHitAnswer(){
		for(Box answer : answers){
			MathBox a = (MathBox) answer;
			MathBox q = (MathBox) question;
			if(a.getRec().overlaps(q.getRec()) && a.getNumber() == q.getNumber()){
				return a;
			}
		}
		return null;
	}
	
	@Override
	public void generate1stLevelQuestions(){
		// question
		Random rand = new Random();
		int a = 1 + rand.nextInt(20);
		int b = 1 + rand.nextInt(20);
		int sum = a + b;
		String str = a + " + " + b;
		((MathBox) question).setText(str);
		((MathBox) question).setNumber(sum);
		
		// answers
		List<Integer> numbers = new ArrayList<>();
		for(Box answer : answers){
			int ans = 1 + rand.nextInt(sum+10);
			while(ans == sum || numbers.contains(ans)){
				ans = 1 + rand.nextInt(sum+10);
			}
			((MathBox) answer).setText(Integer.toString(ans));
			((MathBox) answer).setNumber(ans);
			numbers.add(ans);
		}
		// overwrite random box to match answer
		int randomBox = rand.nextInt(4);
		((MathBox) answers.get(randomBox)).setText(Integer.toString(sum));
		((MathBox) answers.get(randomBox)).setNumber(sum);
	}
	
	@Override
	public void generate2ndLevelQuestions(){
		// question
		Random rand = new Random();
		int a = 1 + rand.nextInt(20);
		int b = rand.nextInt(a);
		int diff = a - b;
		String str = a + " - " + b;
		((MathBox) question).setText(str);
		((MathBox) question).setNumber(diff);
		
		// answers
		List<Integer> numbers = new ArrayList<>();
		for(Box answer : answers){
			int ans = 1 + rand.nextInt(diff+5);
			while(ans == diff || numbers.contains(ans)){
				ans = 1 + rand.nextInt(a+b);
			}
			((MathBox) answer).setText(Integer.toString(ans));
			((MathBox) answer).setNumber(ans);
			numbers.add(ans);
		}
		// overwrite random box to match answer
		int randomBox = rand.nextInt(4);
		((MathBox) answers.get(randomBox)).setText(Integer.toString(diff));
		((MathBox) answers.get(randomBox)).setNumber(diff);
	}
	
	@Override
	public void generate3rdLevelQuestions() {
	}

	@Override
	public void generate4thLevelQuestions() {
	}

	@Override
	public void generate5thLevelQuestions() {
	}

	@Override
	public void generate6thLevelQuestions() {
	}

	@Override
	public void generate7thLevelQuestions() {
	}

	public void generate8thLevelQuestions(){
		//+: question to question
		
		// question
		Random rand = new Random();
		int qa = rand.nextInt(20);
		int qb = rand.nextInt(20);
		int qn = qa + qb;
		String qs = qa + " + " + qb;
		((MathBox) question).setText(qs);
		((MathBox) question).setNumber(qn);
		
		// answers
		for(Box answer : answers){
			int a = rand.nextInt(20);
			int b = rand.nextInt(20);
			int n = a + b;
			while(n == qn){
				a = rand.nextInt(20);
				b = rand.nextInt(20);
				n = a + b;
			}
			String s = a + " + " + b;
			((MathBox) answer).setText(s);
			((MathBox) answer).setNumber(n);
		}
		// overwrite random box to match answer
		int randomBox = rand.nextInt(4);
		int a = rand.nextInt(qn);
		int b = qn-a;
		String s = a + " + " + b;
		((MathBox) answers.get(randomBox)).setText(s);
		((MathBox) answers.get(randomBox)).setNumber(qn);
	}
	
	@Override
	public void generate9thLevelQuestions() {
	}
}
