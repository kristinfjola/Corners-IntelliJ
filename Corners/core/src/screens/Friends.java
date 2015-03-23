/**
 * @author 	Edda Bjork Konradsdottir
 * @date 	19.03.2015
 * @goal 	Shows the users's friends list from facebook with scores
 */
package screens;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.corners.game.MainActivity;


public class Friends implements Screen{

	MainActivity main;
	Skin skin;
	InfoBar infoBar;
	Stage stage;
	SpriteBatch batch;
	TextButton btnTest;
	List<String> friends;
	LabelStyle friendsStyle;
	
	private InputProcessor inputProcessor;
	Table table;
	
	/**
	 * Constructor that sets the private variables and starts the screen
	 * 
	 * @param main
	 */
	public Friends(MainActivity main){
		this.main = main;
		batch = new SpriteBatch();
		stage = new Stage();
		skin = this.main.skin;
		infoBar = new InfoBar(main);
		Gdx.input.setInputProcessor(stage);
		addBackToProcessor();
		setAllProcessors();
		friends = main.facebookService.getFriendsList();
		friendsStyle = new LabelStyle(main.skin.getFont(main.screenSizeGroup+"-S"), Color.BLACK);
	}
	
	@Override
	public void show() {
		table = new Table();
		table.top().left();
		table.setFillParent(true);
		stage.addActor(table);
		
		setUpInfoBar();
		
		//list of scores from friends - not used right now
		List<Integer> scores = main.facebookService.getScores();
		double stars = 0;
		int finished_levels = 0;
		
		String stars_image;
		
		for(int i = 0; i < friends.size(); i++) {
			System.out.println("friend: "+friends.get(i));
			String score = Integer.toString(scores.get(i));
			if(!score.equals("-1")) { //if permission to see score
				if(score.length() == 4) {
					stars = Double.parseDouble(score.substring(0, 3));
					stars = stars / 100;
					finished_levels = Integer.parseInt(score.substring(3));
				} else if(score.length() == 3) {
					stars = Double.parseDouble(score.substring(0, 2));
					stars = stars / 10;
					finished_levels = Integer.parseInt(score.substring(2));
				} else {
					stars = Double.parseDouble(score.substring(0, 1));
					finished_levels = Integer.parseInt(score.substring(1));
				}
			}
			System.out.println("stars: "+stars);
			System.out.println("stars amount: "+infoBar.getStarAmount(stars));
			stars_image = "infoBar/"+infoBar.getStarAmount(stars)+".png";
			if(scores.get(i) != -1) {
				table.add(new Label(""+friends.get(i), friendsStyle)).left().padLeft(main.scrWidth/24f);
				table.add(new Image(new Texture(stars_image))).size(main.scrWidth/6).right().padRight(main.scrWidth/24f).row();
			}
		}
	}

	/**
	 * Renders the screen
	 */
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(21/255f, 149/255f, 136/255f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();
	}

	/**
	 * Sets the screen to its proper size
	 */
	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
		
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

	/**
	 * Disposes the screen
	 */
	@Override
	public void dispose() {
		stage.dispose();
		skin.dispose();
	}
	
	/**
	 * Creates a input processor that catches the back key 
	 */
	private void addBackToProcessor() {
		inputProcessor = new InputProcessor() {
			
			@Override
			public boolean touchUp(int screenX, int screenY, int pointer, int button) {
				return false;
			}
				
			@Override
			public boolean touchDragged(int screenX, int screenY, int pointer) {
				return false;
			}
				
			@Override
			public boolean touchDown(int screenX, int screenY, int pointer, int button) {
				return false;
			}
				
			@Override
			public boolean scrolled(int amount) {
				return false;
			}
				
			@Override
			public boolean mouseMoved(int screenX, int screenY) {
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
			
			/**
			 * Handles the back event
			 */
			@Override
			public boolean keyDown(int keycode) {
				if(keycode == Keys.BACK){
					main.setScreen(new Start(main));
				}
				return false;
			}
		};
	}
	
	/**
	 * Adds the game stage and the back button processors to a multiplexer
	 */
	private void setAllProcessors() {
		Gdx.input.setCatchBackKey(true);
		
		InputMultiplexer multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(stage);
		multiplexer.addProcessor(inputProcessor);
		Gdx.input.setInputProcessor(multiplexer);
	}
	
	/**
	 * Sets up the info bar
	 */
	public void setUpInfoBar() {
		InfoBar infoBar = new InfoBar(main);
		infoBar.setMiddleText("High Score");
	 	table.add(infoBar.getInfoBar()).size(main.scrWidth, main.scrHeight/10).expandX().left().row();
	}
}
