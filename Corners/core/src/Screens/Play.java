package Screens;

import java.util.ArrayList;
import java.util.List;

import Logic.Category;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.corners.game.MainActivity;

public class Play implements Screen, InputProcessor{

	MainActivity main;
	Category cat;
	SpriteBatch batch;
	OrthographicCamera camera;
	Rectangle question;
	Texture questionTexture;
	Array<Rectangle> answers;
	Color[] colors = {Color.BLUE, Color.YELLOW, Color.CYAN, Color.PINK};
	Texture[] answerTextures = new Texture[4];
	Vector3 touchPos;
	int width = Gdx.graphics.getWidth();
    int height = Gdx.graphics.getHeight();
    boolean hit = false;
	
	public Play(MainActivity main, Category cat){
		this.main = main;
		this.cat = cat;
		Gdx.input.setInputProcessor(this);
		
		Pixmap pixmap = new Pixmap(100, 100, Format.RGBA8888);
		pixmap.setColor(Color.RED);
		pixmap.fill();
		questionTexture = new Texture(pixmap);
		
		camera = new OrthographicCamera();
 	    camera.setToOrtho(false, 480, 800);
 	    batch = new SpriteBatch();
 	    
 	    //question
 	    question = new Rectangle();
 	    question.x = 480 / 2 - 100 / 2;
 	    question.y = 800 / 2 - 100 / 2;
 	    question.width = 100;
 	    question.height = 100;
 	    
 	    //answers
 	    answers = new Array<Rectangle>();
 	    int[] xcoords = {0, 0, 480-100, 480-100}; 
 	    int[] ycoords = {0, 800-100, 800-100, 0}; 
 	    for(int i = 0; i < 4; i++){
 	    	Rectangle rec = new Rectangle();
 	 	    rec.x = xcoords[i];
 	 	    rec.y = ycoords[i];
 	 	    rec.width = 64;
 	 	    rec.height = 64;
 	 	    answers.add(rec);
 	 	    
 	 	    Pixmap pm = new Pixmap(100, 100, Format.RGBA8888);
 			pm.setColor(colors[i]);
 			pm.fill();
 			answerTextures[i] = new Texture(pm);
 	    }
 	    
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 1, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camera.update();
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		for(Rectangle answer : answers){
			batch.draw(answerTextures[answers.indexOf(answer, false)], answer.x, answer.y);
		}
		batch.draw(questionTexture, question.x, question.y);
		batch.end();
		
		if(Gdx.input.isTouched()) {
			touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			question.x = touchPos.x - 100 / 2;
			question.y = touchPos.y - 100 / 2;
		}
		Rectangle hitAnswer = null;
		for(Rectangle answer : answers){
			if(answer.overlaps(question)){
				System.out.println("HIT");
				hitAnswer = answer;
			}
		}
		if(hitAnswer != null){
			answers.removeValue(hitAnswer, false);
		}
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		batch.dispose();
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		question.x = 480 / 2 - 100 / 2;
 	    question.y = 800 / 2 - 100 / 2;
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
	
	

}
