/**
 * @author 	Steinunn Fridgeirsdottir
 * @date 	05.02.2015
 * @goal 	Levels is a screen that shows the user the levels that he/she can play in a category.
 * 			It also shows the stars that the user has won.
 */
package screens;

import logic.Category;
import logic.Colors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.corners.game.MainActivity;

public class Levels implements Screen{

	private MainActivity main;
	private Skin skin;
	private Stage stage;
	private Table container;
	private Category cat;
	
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
		
		skin.add("levelButton", skin.newDrawable("white", Color.BLUE), Drawable.class);
		skin.add("star-filled", skin.newDrawable("white", Color.YELLOW), Drawable.class); 
		skin.add("star-unfilled", skin.newDrawable("white", Color.GRAY), Drawable.class);
		
		Gdx.input.setInputProcessor(stage);

		container = new Table();
		stage.addActor(container);
		container.setFillParent(true);

		//TODO: Fix the padding to a % of a screen size
		int c = 1;
		Table levels = new Table().pad(50);
		levels.defaults().pad(40, 20, 40, 20);
		for (int y = 0; y < 3; y++) {
			levels.row();
			for (int x = 0; x < 3; x++) {
				levels.add(getLevelButton(c++)).expand().fill();
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
		
		button.stack(new Image(skin.getDrawable("levelButton")), label).expand().fill();
		
		Table starTable = new Table();
		//starTable.background(skin.newDrawable("white", new Color(54/255f, 83/255f, 139/255f, 1)));
		starTable.defaults().pad(5);

		for (int star = 0; star < 3; star++) {
			if(level == 1)
				starTable.add(new Image(skin.getDrawable("star-filled"))).width(20).height(20);
			else
				starTable.add(new Image(skin.getDrawable("star-unfilled"))).width(20).height(20);
				
		}			
		
		button.row();
		button.add(starTable).height(30);
		
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
