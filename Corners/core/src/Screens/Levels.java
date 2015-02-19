/**
 * @author 	Steinunn Fridgeirsdottir
 * @date 	05.02.2015
 * @goal 	Levels is a screen that shows the user the levels that he/she can play in a category.
 * 			It also shows the stars that the user has won.
 */
package screens;

import logic.Category;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.corners.game.MainActivity;

public class Levels implements Screen{

	private MainActivity main;
	private Skin skin;
	private Stage stage;
	private Table container;
	private Category cat;
	final float screenWidth = Gdx.graphics.getWidth();
	final float screenHeight = Gdx.graphics.getHeight();
	
	/**
	 * Constructor that sets the private variable and starts the screen.
	 * 
	 * @param main
	 * @param category
	 */
	public Levels(MainActivity main, Category category){
		this.main = main;
		cat = category;
		create();
	}
	
	/**
	 * Sets up the buttons on the level screen
	 */
	public void create(){
		stage = new Stage();
		skin = main.skin;
		
		// TODO setja inn retta liti og setja inn stjornur
		skin.add("levelButton-finished", skin.newDrawable("white", new Color(255/255f,197/255f,1/255f,1)), Drawable.class);
		skin.add("levelButton-unfinished", skin.newDrawable("white", new Color(202/255f,170/255f,54/255f,8/9f)), Drawable.class);
			
		Gdx.input.setInputProcessor(stage);

		container = new Table();
		stage.addActor(container);
		container.setFillParent(true);

		int cnt = 1;
		Table levels = new Table();
		levels.defaults().size(screenWidth/4.2f,screenWidth/2.5f);
		for (int rows = 0; rows < 3; rows++) {
			levels.row();
			for (int columns = 0; columns < 3; columns++) {
				levels.add(getLevelButton(cnt++)).expand().fill();
			}
		}
		container.add(levels).expand().fill();
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Renders the stuff on the screen 
	 *
	 * @param delta
	 */
	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();		
	}

	/**
	 * Sets the screen to its proper size
	 * @param width
	 * @param height
	 */
	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
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

	/**
	 * Disposes the screen
	 */
	@Override
	public void dispose() {
		stage.dispose();
	}
	
	/**
	 * Creates a button to represent a level
	 * level is a int number that represents the level number of the button
	 * 
	 * @param level
	 * @return The button to use for the level
	 */
	public Button getLevelButton(int level) {
		Button button = new Button(skin);
		
		String screenSizeGroup = main.screenSizeGroup;
		
		Label label = new Label(Integer.toString(level), skin, screenSizeGroup);
		label.setAlignment(Align.center);		
				
		Table starTable = new Table();
		starTable.defaults().size(screenWidth/5f);

		for (int star = 0; star < 3; star++) {
			if(level == 1)
				starTable.add(new Image(main.fullStar)).size(screenWidth/5/4f).pad(screenWidth/5/4/6f);
			else
				starTable.add(new Image(main.emptyStar)).size(screenWidth/5/4f).pad(screenWidth/5/4/6f);
		}
		
		if(level == 1)
			button.stack(new Image(skin.getDrawable("levelButton-finished")), label).expand().fill();
		else
			button.stack(new Image(skin.getDrawable("levelButton-unfinished")), label).expand().fill();

		button.row();
		button.add(starTable);
		
		button.setName("Level" + Integer.toString(level));
		button.addListener(levelClickListener);		
		return button;
	}
	
	/**
	 * A handler for the level-button click
	 */
	public ClickListener levelClickListener = new ClickListener() {
		
		/**
		 * Sends the user to a new play screen
		 * 
		 * @param event
		 * @param x
		 * @param y
		 */
		@Override
		public void clicked (InputEvent event, float x, float y) {
			main.play = new Play(main, cat);
            main.setScreen(main.play);
		}
	};

}
