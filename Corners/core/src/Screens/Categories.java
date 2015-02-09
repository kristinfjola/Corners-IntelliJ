package Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.corners.game.MainActivity;
import Logic.Category;
import Logic.Colors;

public class Categories implements Screen {
	Texture carl; //the character, let's call it Carl
	SpriteBatch batch;
	MainActivity main;
	float screenWidth;
	float screenHeight;
	Skin skin;
	Stage stage;
	
	public Categories(final MainActivity main){
		this.main = main;
		this.batch = new SpriteBatch();
		this.carl = new Texture("carl1.jpg");
		stage = new Stage();
		this.screenWidth = Gdx.graphics.getWidth();
		this.screenHeight = Gdx.graphics.getHeight();
		Gdx.input.setInputProcessor(stage);
		
		// A skin can be loaded via JSON or defined programmatically, either is fine. Using a skin is optional but strongly
		// recommended solely for the convenience of getting a texture, region, etc as a drawable, tinted drawable, etc.
		skin = new Skin();

		// Generate a 1x1 white texture and store it in the skin named "white".
		Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();
		skin.add("white", new Texture(pixmap));

		// Store the default libgdx font under the name "default".
		skin.add("default", new BitmapFont());

		// Configure a TextButtonStyle and name it "default". Skin resources are stored by type, so this doesn't overwrite the font.
		TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = skin.newDrawable("white", Color.DARK_GRAY);
		textButtonStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
		textButtonStyle.checked = skin.newDrawable("white", Color.BLUE);
		textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);
		textButtonStyle.font = skin.getFont("default");
		textButtonStyle.font.setScale(5);
		skin.add("default", textButtonStyle);

		// Create a table that fills the screen. Everything else will go inside this table.
		Table table = new Table();
		table.top();
		table.setFillParent(true);
		stage.addActor(table);
		
		final TextButton btnMath = new TextButton("Math", skin);
		btnMath.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				main.levels = new Levels(main, "");
				main.setScreen(main.levels);
				System.out.println("MATH!");
			}
		});

		final TextButton btnColors = new TextButton("Colors", skin);
		btnColors.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				main.levels = new Levels(main, "");
				main.setScreen(main.levels);
				System.out.println("COLORS!");
			}
		});
		
		final TextButton btnFlags = new TextButton("Flags", skin);
		btnFlags.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				main.levels = new Levels(main, "");
				main.setScreen(main.levels);
				System.out.println("FLAGS!");
			}
		});
		
		table.add(btnMath).width(this.screenWidth/1.5f).height(this.screenHeight/8).padTop(this.screenHeight/2.8f).padBottom(this.screenHeight/20);
		table.row();
		table.add(btnColors).width(this.screenWidth/1.5f).height(this.screenHeight/8).padBottom(this.screenHeight/20);
		table.row();
		table.add(btnFlags).width(this.screenWidth/1.5f).height(this.screenHeight/8).padBottom(this.screenHeight/20);
		table.row();
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(54/255f, 83/255f, 139/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
		batch.draw(carl, (this.screenWidth/2)-(this.screenWidth/4), this.screenHeight-(this.screenHeight/3), this.screenWidth/2, this.screenHeight/3);
		batch.end();
		
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();
        
        if (Gdx.input.justTouched()) // use your own criterion here
        	main.play = new Play(main, new Colors());
            main.setScreen(main.play);
        /*if (Gdx.input.justTouched()) // use your own criterion here
        	main.levels = new Levels(main);
            main.setScreen(main.levels);*/
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
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

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		stage.dispose();
		skin.dispose();
	}
}
