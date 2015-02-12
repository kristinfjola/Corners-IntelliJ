package screens;

import Logic.Category;
import Logic.Colors;

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
	
	public Levels(MainActivity main, Category category){
		this.main = main;
		cat = category;
		create();
	}
	
	public void create(){
		stage = new Stage();
		skin = new Skin();

		// Generate a 1x1 white texture and store it in the skin named "white".
		Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();
		skin.add("white", new Texture(pixmap));
		
		skin.add("levelButton", skin.newDrawable("white", Color.BLUE), Drawable.class);
		skin.add("star-filled", skin.newDrawable("white", Color.YELLOW), Drawable.class); 
		skin.add("star-unfilled", skin.newDrawable("white", Color.GRAY), Drawable.class);
		
		Gdx.input.setInputProcessor(stage);

		container = new Table();
		stage.addActor(container);
		container.setFillParent(true);

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

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();		
	}

	@Override
	public void resize(int width, int height) {
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
		stage.dispose();
		skin.dispose();
	}
	
	/**
	 * Creates a button to represent the level
	 * 
	 * @param level
	 * @return The button to use for the level
	 */
	public Button getLevelButton(int level) {
			
		ButtonStyle buttonStyle = new ButtonStyle();
		buttonStyle.up = skin.newDrawable("white", Color.DARK_GRAY);
		buttonStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
		buttonStyle.checked = skin.newDrawable("white", Color.BLUE);
		buttonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);
		skin.add("default", buttonStyle);
		
		Button button = new Button(skin);
		
		LabelStyle labelStyle = new LabelStyle();
		labelStyle.background = skin.newDrawable("white", new Color(240/255f, 240/255f, 102/255f, 1));
		labelStyle.font = new BitmapFont();
		skin.add("default", labelStyle);
		
		// Create the label to show the level number
		Label label = new Label(Integer.toString(level), skin);
		label.setAlignment(Align.center);		
		
		// Stack the image and the label at the top of our button
		button.stack(new Image(skin.getDrawable("levelButton")), label).expand().fill();
		
		// Randomize the number of stars earned for demonstration purposes
		Table starTable = new Table();
		starTable.background(skin.newDrawable("white", new Color(54/255f, 83/255f, 139/255f, 1)));
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
	 * Handle the click - in real life, we'd go to the level
	 */
	public ClickListener levelClickListener = new ClickListener() {
		@Override
		public void clicked (InputEvent event, float x, float y) {
			main.play = new Play(main, cat);
            main.setScreen(main.play);
		}
	};

}
