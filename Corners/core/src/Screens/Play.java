package screens;

import logic.Category;

import boxes.Box;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.corners.game.MainActivity;

public class Play implements Screen, InputProcessor{

	MainActivity main;
	Category cat;
	SpriteBatch batch;
	OrthographicCamera camera;
	Vector3 touchPos;
    boolean hit = false;
    int screenWidth = 480;
    int screenHeight = 800;
    int qSize = 100;
	
	public Play(MainActivity main, Category cat){
		this.main = main;
		this.cat = cat;
		Gdx.input.setInputProcessor(this);
		
		camera = new OrthographicCamera();
 	    camera.setToOrtho(false, screenWidth, screenHeight);
 	    batch = new SpriteBatch();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		
		// draw sprites
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		for(Box answer : cat.getAnswers()){
			batch.draw(cat.getAnswerTextures()[cat.getAnswers().indexOf(answer, false)], 
					answer.getRec().x, answer.getRec().y, answer.getRec().getWidth(), answer.getRec().getHeight());
		}
		batch.draw(cat.getQuestionTexture(), cat.getQuestion().getRec().x, cat.getQuestion().getRec().y, 
				cat.getQuestion().getRec().width, cat.getQuestion().getRec().height);
		batch.end();
		
		// swipe question
		if(Gdx.input.isTouched()) {
			touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			cat.getQuestion().getRec().x = touchPos.x - qSize / 2;
			cat.getQuestion().getRec().y = touchPos.y - qSize / 2;
		}
		
		// hit answer
		Box hit = cat.checkIfHitAnswer();
		if(hit != null){
			cat.getAnswers().removeValue(hit, false);
		}
		
	}
	
	@Override
	public void show() {
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void dispose() {
		batch.dispose();
	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		cat.getQuestion().getRec().x = screenWidth / 2 - qSize / 2;
		cat.getQuestion().getRec().y = screenHeight / 2 - qSize / 2;
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
	
	

}
