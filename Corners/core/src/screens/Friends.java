/**
 * @author 	Edda Bjork Konradsdottir
 * @date 	19.03.2015
 * @goal 	Shows the users's friends list from facebook with scores
 */
package screens;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.corners.game.MainActivity;


public class Friends implements Screen{

	MainActivity main;
	Skin skin;
	InfoBar infoBar;
	Stage stage;
	SpriteBatch batch;
	TextButton btnTest;
	LabelStyle friendsStyle;
	LabelStyle friendsStyleM;
	
	private InputProcessor inputProcessor;
	Table table;
	
	//high score list
	double stars;
	int finished_levels;
	List<String> names;
	
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
		friendsStyleM = new LabelStyle(main.skin.getFont(main.screenSizeGroup+"-M"), Color.BLACK);
		friendsStyle = new LabelStyle(main.skin.getFont(main.screenSizeGroup+"-S"), Color.BLACK);
		names = new ArrayList<String>();
	}
	
	@Override
	public void show() {
		table = new Table();
		table.top().left();
		table.setFillParent(true);
		stage.addActor(table);
		
		setUpInfoBar();
		
		if(!main.activityRequestHandler.isConnectedToInternet()){
			main.dialogs.showNotConnectedToast();
		} else if(main.facebookService.isLoggedIn()){
			getInfoFromFacebookAndShow();
		} else {
			showNotLoggedIntoFacebookMessage();
		}
	}
	
	/**
	 * displays a message that tells the user he's not logged into Facebook
	 */
	public void showNotLoggedIntoFacebookMessage(){
		table.add(new Label("Oops! You're not logged into",friendsStyleM)).left().padTop(main.scrWidth/18f).padLeft(main.scrWidth/24f).row();
		table.add(new Label("facebook!",friendsStyleM)).left().padLeft(main.scrWidth/24f).row();
		table.add(new Label("Go to Settings on the start screen and log", friendsStyle)).left().padLeft(main.scrWidth/24f).padTop(main.scrWidth/24f).row();
		table.add(new Label("into facebook to see your friends!", friendsStyle)).left().padLeft(main.scrWidth/24f).row();
	}
	
	/**
	 * displays a message that tells the user he has no Corners friends
	 */
	public void showNoFriendsMessage(){
		table.add(new Label("I am so terribly sorry!",friendsStyleM)).left().padTop(main.scrWidth/18f).padLeft(main.scrWidth/24f).row();
		table.add(new Label("You don't have any Corners friends.", friendsStyle)).left().padLeft(main.scrWidth/24f).padTop(main.scrWidth/24f).row();
		table.add(new Label("Tell your friends to play!", friendsStyle)).left().padLeft(main.scrWidth/24f).row();
	}
	
	/**
	 * gets and displays friends list from facebook with scores
	 */
	public void getInfoFromFacebookAndShow() {
		new Thread(new Runnable() {
		   @Override
		   public void run() {
			   main.dialogs.showProgressBar();
			   final List<String> friends = main.facebookService.getFriendsList();
			   final boolean friends_is_empty = friends.isEmpty();
			   final List<Integer> scores = main.facebookService.getScores();
			   final int my_score = main.facebookService.getMyScore();
			   
			   Gdx.app.postRunnable(new Runnable() {
				   @Override
				   public void run() {
					   if(friends_is_empty) showNoFriendsMessage();
					   else showFriends(friends, scores, my_score);
					   main.dialogs.hideProgressBar();
				   }
			   });
		   }
		}).start();
	}
	
	/**
	 * shows friends list from facebook with score
	 * 
	 * @param friends - friends list from facebook
	 * @param scores - scores from friends
	 * @param myScore - user's score
	 */
	public void showFriends(List<String> friends, List<Integer> scores, Integer myScore) {
		HashMap<String, Integer> friends_hash_temp = new HashMap<String, Integer>();
		
		for(int i = 0; i< scores.size(); i++) {
			if(scores.get(i) != -1) friends_hash_temp.put(friends.get(i), scores.get(i));
		}
		//add user to high score list
		friends_hash_temp.put(main.facebookService.getUserName(),myScore);
		
		HashMap<String, Double> stars_hash = getStarsFromScore(friends_hash_temp);
		HashMap<String, Integer> levels_hash = getLevelsFromScore(friends_hash_temp);
		HashMap<String, Double> high_score_hash = getHighscoreHash(stars_hash, levels_hash);
		
		List<Double> high_score = getHighScoreList(high_score_hash);
		
		for(int i = 0; i < high_score.size(); i++) {
			List<String> names_list = getKeysByValue(high_score_hash, high_score.get(i));
			String name = "";
			//prevent from getting the same key for many values
			for(int j = 0; j < names_list.size(); j++) {
				if(!names.contains(names_list.get(j))) {
					name = names_list.get(j);
					names.add(name);
				}
			}
			stars = stars_hash.get(name);
			finished_levels = levels_hash.get(name);			
			Table starTable = new Table();
			String[] starAmount = main.getStarImgs(stars);
			for (int j=0; j<starAmount.length; j++) {
				starTable.add(new Image(new Texture(starAmount[j]))).size(main.scrWidth/18);
			}
			
			table.add(new Label(""+(i+1)+". "+name+"  ("+stars+"/"+finished_levels+")", friendsStyle)).left().padLeft(main.scrWidth/24f);
			table.add(starTable).size(main.scrWidth/6).right().padRight(main.scrWidth/26f).row();
		}
	}
	
	/**
	 * @param friends - friends list from facebook
	 * @return - stars for each user
	 */
	public HashMap<String, Double> getStarsFromScore(HashMap<String, Integer> friends) {
		double stars = 0;
		HashMap<String, Double> stars_hash = new HashMap<String, Double>();
		for (String key : friends.keySet()) {
			// TODO: TEMP FIX for facebook permission publish_friends
			if(friends.get(key) == 0) continue;
			if(friends.get(key).toString().substring(0, 1).equals("7")) {
				stars = 0;
			} else {
				String stars_string = "";
				String score = Integer.toString(friends.get(key));
				int i = 0;
				System.out.println("friend: " + key + " score: " + score);
				while(!score.substring(i, i+3).equals("777")) {
					stars_string += score.substring(i, i+1);
					i++;
				}
				stars = Double.parseDouble(stars_string);
				if(stars_string.length() == 2) {
					stars = stars / 10;
				} else if(stars_string.length() == 3) {
					stars = stars / 100;
				}
			}
			stars_hash.put(key, stars);
		}
		
		return stars_hash;
	}
	
	/**
	 * @param friends - friends list from facebook
	 * @return - finished levels for each user
	 */
	public HashMap<String, Integer> getLevelsFromScore(HashMap<String, Integer> friends) {
		int finished_levels = 0;
		int split_index = 0;
		HashMap<String, Integer> levels = new HashMap<String, Integer>();
		for (String key : friends.keySet()) {
			String string_levels = "";
			// TODO: TEMP FIX for facebook permission publish_friends
			if(friends.get(key) == 0) continue;
			String score = Integer.toString(friends.get(key));
			for(int i = 0; i < score.length(); i++) {
				if(score.substring(i, i+3).equals("777")) {
					split_index = i;
					break;
				}
			}
			int j = split_index+3;
			while(j < score.length()) {
				if(!score.substring(j, j+1).equals("7") || j+1 == score.length()) string_levels += score.substring(j, j+1);
				j++;
			}
			finished_levels = Integer.parseInt(string_levels);
			levels.put(key, finished_levels);
		}
		
		return levels;
	}
	
	/**
	 * @param friends - friends list from facebook
	 * @param stars
	 * @return - friends that have param-stars many stars
	 */
	public List<String> getKeysByValue(Map<String, Double> friends, Double stars) {
	    List<String> keys = new ArrayList<String>();
		for (Entry<String, Double> entry : friends.entrySet()) {
	        if (stars == entry.getValue()) {
	        	keys.add(entry.getKey());
	        }
	    }
	    return keys;
	}
	
	/**
	 * @param starsHash - each user with stars
	 * @param levelsHash - each user with finished levels
	 * @return - value used in high score for each user
	 */
	public HashMap<String, Double> getHighscoreHash(HashMap<String, Double> starsHash, HashMap<String, Integer> levelsHash) {
		HashMap<String, Double> high_score_hash = new HashMap<String, Double>();
		double high = 0;
		
		for(String key : starsHash.keySet()) {
			stars = starsHash.get(key);
			finished_levels = levelsHash.get(key);
			high = stars*finished_levels;
			high_score_hash.put(key, high);
		}
		
		return high_score_hash;
	}
	
	/**
	 * @param highScoreHash - high score values for each user
	 * @return - sorted high score
	 */
	public List<Double> getHighScoreList(HashMap<String, Double> highScoreHash) {
		List<Double> high_score = new ArrayList<Double>(highScoreHash.values());
		Collections.sort(high_score);
		Collections.reverse(high_score);
		
		return high_score;
	}

	/**
	 * Renders the screen
	 */
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(21/255f, 149/255f, 136/255f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
        batch.draw(main.background, 0, 0, main.scrWidth, main.scrHeight);
		batch.end();
        
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
