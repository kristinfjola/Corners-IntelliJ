package Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.corners.game.MainActivity;

public class Start implements Screen{
	MainActivity main;
	Stage stage;
	SpriteBatch batch;
	Skin skin;
	float screenWidth = Gdx.graphics.getWidth();
	float screenHeight = Gdx.graphics.getHeight();
	Texture carl = new Texture("carl4.jpg");
	
	public Start(final MainActivity main){
		this.main = main;		
		stage = new Stage();
		batch = new SpriteBatch();
		skin = new Skin(Gdx.files.internal("skins/skins.json"));
		Gdx.input.setInputProcessor(stage);
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		Table table = new Table();
		table.top();
		table.setFillParent(true);
		stage.addActor(table);
		
		String screenSizeGroup = getScreenSizeGroup();
		
		final TextButton btnCategories = new TextButton("Categories", skin, screenSizeGroup);
		btnCategories.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				System.out.println("Clicked! Is checked: " + btnCategories.isChecked());
				System.out.println("start");
				dispose();
				//main.play = new Play(main, new Logic.Math());
	            //main.setScreen(main.play);
				main.categories = new Categories(main);
				main.setScreen(main.categories);
			}
		});
		
		final TextButton btnSettings = new TextButton("Settings", skin, screenSizeGroup);
		btnSettings.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				System.out.println("Clicked! Is checked: " + btnSettings.isChecked());
				System.out.println("start");
				main.settings = new Settings(main);
				main.setScreen(main.settings);
			}
		});
		
		TextButton btnFriends = new TextButton("Friends", skin, screenSizeGroup);
		
		table.add(btnCategories).padTop(screenHeight/2.8f).size(screenWidth/1.5f, screenHeight/8).padBottom(screenHeight/20).row();
		table.add(btnSettings).size(screenWidth/1.5f, screenHeight/8).padBottom(screenHeight/20).row();
		table.add(btnFriends).size(screenWidth/1.5f, screenHeight/8).padBottom(screenHeight/20).row();
		
	}

	@Override
	public void render(float delta) {	
		Gdx.gl.glClearColor(54/255f, 83/255f, 139/255f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		batch.draw(carl, screenWidth/4, screenHeight*2/3, screenWidth/2, screenHeight/3);
		batch.end();
		
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();
		
	}

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

	@Override
	public void dispose() {
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
