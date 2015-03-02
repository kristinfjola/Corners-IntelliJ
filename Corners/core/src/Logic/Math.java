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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Array;

public class Math extends Category{
	/**
	 * 	Creates a new Math category, delivers a question and possible answers
	 */
	public Math(BitmapFont bmFont){
		type = "Math";
		this.bmFont = bmFont;
		qWidth = (int) (screenWidth/3.5);
		qHeight = (int) (screenWidth/3.5);
		int[] xcoords = {0, 0, screenWidth-qWidth, screenWidth-qWidth}; 
		int[] ycoords = {0, (screenHeight-screenHeight/10)-qHeight, (screenHeight-screenHeight/10)-qHeight, 0};
		//answers
 	    answers = new Array<Box>();
 	    for(int i = 0; i < 4; i++){
 	    	MathBox box = new MathBox(qWidth, qHeight, 2, "1+1", bmFont);
 	 	    box.getRec().x = xcoords[i];
 	 	  	box.getRec().y = ycoords[i];
 	 	  	box.setTexture(new Texture(Gdx.files.internal("mathBoxes/aBox"+(i+1)+".png")));
 	 	    answers.add(box);
 	    }
 	    
 	    //question
 		question = new MathBox(qWidth, qHeight, 2, "2+2", bmFont);
  	    question.getRec().x = screenWidth / 2 - qWidth / 2;
  	    question.getRec().y = screenHeight / 2 - qHeight / 2;
  	    question.setTexture(new Texture(Gdx.files.internal("mathBoxes/qBox.png")));
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
	
	public Box checkIfHitBox() {
		for(Box answer : answers){
			MathBox a = (MathBox) answer;
			MathBox q = (MathBox) question;
			if(a.getRec().overlaps(q.getRec())){
				return a;
			}
		}
		return null;
	}
	
	public void generateQuestion(int ans, String str){
		((MathBox) question).setText(str);
		((MathBox) question).setNumber(ans);
	}
	
	public void generateCorrectAnswer(int ans, String str){
		Random rand = new Random();
		int randomBox = rand.nextInt(4);
		((MathBox) answers.get(randomBox)).setText(str);
		((MathBox) answers.get(randomBox)).setNumber(ans);
	}
	
	public void generateAnswersForLevels2to7(int ans){
		// answers
		List<Integer> numbers = new ArrayList<Integer>();
		Random rand = new Random();
		for(Box answer : answers){
			int a = (ans < 6 ? 1 : ans-5) + rand.nextInt(10);
			while(a == ans || numbers.contains(a)){
				a = (ans < 6 ? 1 : ans-5) + rand.nextInt(10);
			}
			((MathBox) answer).setText(Integer.toString(a));
			((MathBox) answer).setNumber(a);
			numbers.add(a);
		}
	}
	
	@Override
	public void generate1stLevelQuestions(){
		// numbers
		Random rand = new Random();
		int ans = 1 + rand.nextInt(20);
		
		generateQuestion(ans, Integer.toString(ans));
		generateAnswersForLevels2to7(ans);
		generateCorrectAnswer(ans, Integer.toString(ans));
	}
	
	
	@Override
	public void generate2ndLevelQuestions(){
		// +
		Random rand = new Random();
		int a = 1 + rand.nextInt(20);
		int b = 1 + rand.nextInt(20);
		int ans = a + b;
		
		generateQuestion(ans, a + " + " + b);
		generateAnswersForLevels2to7(ans);
		generateCorrectAnswer(ans, Integer.toString(ans));
	}
	
	@Override
	public void generate3rdLevelQuestions() {
		// -
		Random rand = new Random();
		int a = 1 + rand.nextInt(30);
		int b = rand.nextInt(a);
		int ans = a - b;
		
		generateQuestion(ans, a + " - " + b);
		generateAnswersForLevels2to7(ans);
		generateCorrectAnswer(ans, Integer.toString(ans));
	}

	@Override
	public void generate4thLevelQuestions() {
		// x
		Random rand = new Random();
		int a = 1 + rand.nextInt(12);
		int b = 1 + rand.nextInt(12);
		int ans = a * b;
		
		generateQuestion(ans, a + " x " + b);
		generateAnswersForLevels2to7(ans);
		generateCorrectAnswer(ans, Integer.toString(ans));
	}

	@Override
	public void generate5thLevelQuestions() {
		// /
		Random rand = new Random();
		int a = 1 + rand.nextInt(10);
		int b = (1 + rand.nextInt(10)) * a;
		int ans = b / a;
		
		generateQuestion(ans, b + " / " + a);
		generateAnswersForLevels2to7(ans);
		generateCorrectAnswer(ans, Integer.toString(ans));
	}

	@Override
	public void generate6thLevelQuestions() {
		// + and -
		Random rand = new Random();
		double op = rand.nextDouble();
		int ans;
		String str;
		if(op < 0.5){
			// +
			int a = 1 + rand.nextInt(100);
			int b = 1 + rand.nextInt(100);
			ans = a + b;
			str = a + " + " + b;
		} else {
			// -
			int a = 1 + rand.nextInt(100);
			int b = rand.nextInt(a);
			ans = a - b;
			str = a + " - " + b;
		}
		generateQuestion(ans, str);
		generateAnswersForLevels2to7(ans);
		generateCorrectAnswer(ans, Integer.toString(ans));
	}

	@Override
	public void generate7thLevelQuestions() {
		// + and -
		Random rand = new Random();
		double op = rand.nextDouble();
		int ans;
		String str;
		if(op < 0.5){
			// *
			int a = 1 + rand.nextInt(25);
			int b = 1 + rand.nextInt(25);
			ans = a * b;
			str = a + " x " + b;
		} else {
			// /
			int a = 1 + rand.nextInt(30);
			int b = (1 + rand.nextInt(30)) * a;
			ans = b / a;
			str = b + " / " + a;
		}
		generateQuestion(ans, str);
		generateAnswersForLevels2to7(ans);
		generateCorrectAnswer(ans, Integer.toString(ans));
	}

	public void generate8thLevelQuestions(){
		// + and -: question to question
		Random rand = new Random();
		double op = rand.nextDouble();
		int ans;
		String strQuestion;
		String strAnswer;
		if(op < 0.5){
			// +
			int a = 1 + rand.nextInt(20);
			int b = 1 + rand.nextInt(20);
			ans = a + b;
			strQuestion = a + " + " + b;
			List<Integer> numbers = new ArrayList<Integer>();
			for(Box answer : answers){
				int a2 = (a < 10 ? 1 : a-10) + rand.nextInt(10);
				int b2 = (b < 10 ? 1 : b-10) + rand.nextInt(10);
				int ans2 = a2 + b2;
				while(ans2 == ans || numbers.contains(ans2)){
					a2 = (a < 10 ? 1 : a-10) + rand.nextInt(10);
					b2 = (b < 10 ? 1 : b-10) + rand.nextInt(10);
					ans2 = a2 + b2;
				}
				String s = a2 + " + " + b2;
				((MathBox) answer).setText(s);
				((MathBox) answer).setNumber(ans2);
				numbers.add(ans2);
			}
			
			int a3 = rand.nextInt(ans);
			int b3 = ans-a3;
			strAnswer = a3 + " + " + b3;
		} else {
			// -
			int a = 1 + rand.nextInt(100);
			int b = rand.nextInt(a);
			ans = a - b;
			strQuestion = a + " - " + b;
			List<Integer> numbers = new ArrayList<Integer>();
			for(Box answer : answers){
				int a2 = (a < 10 ? 1 : a-10) + rand.nextInt(10);
				int b2 = (b < 10 ? 1 : b-10) + rand.nextInt(10);
				int ans2 = a2 - b2;
				while(ans2 == ans || numbers.contains(ans2)){
					a2 = (a < 10 ? 1 : a-10) + rand.nextInt(10);
					b2 = (b < 10 ? 1 : b-10) + rand.nextInt(10);
					ans2 = a2 - b2;
				}
				String s = a2 + " - " + b2;
				((MathBox) answer).setText(s);
				((MathBox) answer).setNumber(ans2);
				numbers.add(ans2);
			}

			int a3 = a + rand.nextInt(20);
			int b3 = a3-ans;
			strAnswer = a3 + " - " + b3;
		}
		generateQuestion(ans, strQuestion);
		generateCorrectAnswer(ans, strAnswer);
	}
	
	@Override
	public void generate9thLevelQuestions() {
		// x and /: question to question
		Random rand = new Random();
		double op = rand.nextDouble();
		int ans;
		String strQuestion;
		String strAnswer;
		if(op < 0.5){
			// x
			int a = 1 + rand.nextInt(20);
			int b = 1 + rand.nextInt(20);
			ans = a * b;
			strQuestion = a + " x " + b;
			List<Integer> numbers = new ArrayList<Integer>();
			for(Box answer : answers){
				int a2 = (a < 10 ? 1 : a-10) + rand.nextInt(10);
				int b2 = (b < 10 ? 1 : b-10) + rand.nextInt(10);
				int ans2 = a2 * b2;
				while(ans2 == ans || numbers.contains(ans2)){
					a2 = (a < 10 ? 1 : a-10) + rand.nextInt(10);
					b2 = (b < 10 ? 1 : b-10) + rand.nextInt(10);
					ans2 = a2 * b2;
				}
				String s = a2 + " x " + b2;
				((MathBox) answer).setText(s);
				((MathBox) answer).setNumber(ans2);
				numbers.add(ans2);
			}

			int a3 = getRandomDivisor(ans);
			int b3 = ans/a3;
			strAnswer = a3 + " x " + b3;
		} else {
			// /
			int a = 1 + rand.nextInt(50);
			int b = (1 + rand.nextInt(5)) * a;
			ans = b / a;
			strQuestion = b + " / " + a;
			List<Integer> numbers = new ArrayList<Integer>();
			for(Box answer : answers){
				int a2 = (a < 5 ? 1 : a-5) + rand.nextInt(5);
				int b2 = (1 + rand.nextInt(5))*a2;
				int ans2 = b2 / a2;
				while(ans2 == ans || numbers.contains(ans2)){
					a2 = (a < 5 ? 1 : a-5) + rand.nextInt(5);
					b2 = (1 + rand.nextInt(5))*a2;
					ans2 = b2 / a2;
				}
				String s = b2 + " / " + a2;
				((MathBox) answer).setText(s);
				((MathBox) answer).setNumber(ans2);
				numbers.add(ans2);
			}

			int a3 = a + rand.nextInt(100);
			int b3 = a3*ans;
			strAnswer = b3 + " / " + a3;
		}
		generateQuestion(ans, strQuestion);
		generateCorrectAnswer(ans, strAnswer);
	}
	
	private int getRandomDivisor(int num){
		List<Integer> divisors = new ArrayList<Integer>(); 
		for (int i = 2; i < num / 2; i++) {
            if (num % i == 0) {
                divisors.add(i);
            }
        }
		Random rand = new Random();
		if(divisors.size() > 0) {
			return divisors.get(rand.nextInt(divisors.size()));
		} else {
			return 1;
		}
	}
}
