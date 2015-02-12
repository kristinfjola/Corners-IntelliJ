package screens;

import logic.Category;
import logic.Colors;
import logic.Flags;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.corners.game.MainActivity;

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
		
		TextureAtlas atlas = new TextureAtlas("fonts/uiskin.atlas");
		
		this.skin = new Skin(Gdx.files.internal("skins/skins.json"), atlas);
		
		String screenSizeGroup = getScreenSizeGroup();

		// Create a table that fills the screen. Everything else will go inside this table.
		Table table = new Table();
		table.top();
		table.setFillParent(true);
		stage.addActor(table);
		
		final TextButton btnMath = new TextButton("Math", skin, screenSizeGroup);
		btnMath.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				//System.out.println("Clicked! Is checked: " + btnCategories.isChecked());
				//System.out.println("start");
				//dispose();
				main.levels = new Levels(main, new Colors());
				main.setScreen(main.levels);
			}
		});

		final TextButton btnColors = new TextButton("Colors", skin, screenSizeGroup);
		btnColors.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				//System.out.println("Clicked! Is checked: " + btnSettings.isChecked());
				//System.out.println("start");
				//main.settings = new Settings(main);
				//main.setScreen(main.settings);
				//main.levels = new Levels(main, "");
				//main.setScreen(main.levels);
				System.out.println("COLORS!");
				main.levels = new Levels(main, new Colors());
	            main.setScreen(main.levels);
			}
		});
		
		final TextButton btnFlags = new TextButton("Flags", skin, screenSizeGroup);
		btnFlags.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				System.out.println("FLAGS!");
				main.levels = new Levels(main, new Flags());
	            main.setScreen(main.levels);
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
	
	public String getScreenSizeGroup(){
		if(screenWidth < 400) return "screen320";
		else if(400 <= screenWidth && screenWidth < 510) return "screen480";
		else if(510 <= screenWidth && screenWidth < 630) return "screen540";
		else if(630 <= screenWidth && screenWidth < 900) return "screen720";
		else return "screen1080"; //900 <= screenWidth
	}
}
