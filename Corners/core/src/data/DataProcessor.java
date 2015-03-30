/**
 * @author 	Steinunn Fridgeirsdottir
 * @date 	26.02.2015
 * @goal 	DataProcessor processes the data from a json file
 * 			and adds to data transfer objects.
 */
package data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;


public class DataProcessor {
	
	/**
	 * Processes the data for a json file and adds to Data object
	 * 
	 * @param levelState
	 */
	public static void getData(Data data){
		JsonParser jp = new JsonParser();
        JsonElement root = null;
        String string = "";
        
        //Load in a db file if it does not exist
        try{
        	FileHandle file = Gdx.files.internal("db/db.json");
        	string = file.readString();
        }
        catch(Exception e){
        	e.printStackTrace();
        }
        
        //Get a readable db file
        try{
        	FileHandle file = Gdx.files.local("db/db.json");
        	string = file.readString();
        }
        catch(Exception e){
        	e.printStackTrace();
        }
        
        //Parse the db file
		try {
			root = jp.parse(string);
		} catch (JsonIOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonSyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Load the root into a json object
        if(root != null){
			JsonObject category = root.getAsJsonObject();
			JsonArray math = category.getAsJsonArray("Math");
			JsonArray colors = category.getAsJsonArray("Colors");
			JsonArray flags = category.getAsJsonArray("Flags");
			JsonArray reset = category.getAsJsonArray("Reset");
			JsonElement name = category.get("Name");
			JsonElement sound = category.get("Sound");
			JsonElement notifications = category.get("Notifications");
			
			data.setMath(new LevelStars(math));
			data.setFlags(new LevelStars(flags));
			data.setColors(new LevelStars(colors));
			data.setReset(new LevelStars(reset));
			data.setName(name.toString().replaceAll("\"", ""));
			data.setSound(sound.getAsInt() == 1 ? true : false);
			data.setNotifications(notifications.getAsInt() == 1 ? true : false);
	    }
        else
        	return;
	}
	
	/**
	 * Saves the data to a database
	 * 
	 * @param levelState
	 */
	public static void setData(Data data){
		Gson gson = new Gson();
		JsonData jData = convertData(data);
		String json = gson.toJson(jData);
		System.out.println("Json file-inn sem er verið að vista " + json);
		try {
			FileHandle file = Gdx.files.local("db/db.json");
			file.writeString(json, false);
	 
		} catch (Exception e) {
			Gdx.app.error("Trying to save", "Did not save :(");
			e.printStackTrace();
		}
	}
	
	/**
	 * Converts the Data data transfer object to a JsonData data transfer object
	 * that is easyer to parse to a json object
	 * @param data
	 * @return JsonData object with same info as data
	 */
	public static JsonData convertData(Data data){
		addAverageStars(data);
		
		JsonData jData = new JsonData();

		jData.setMath(data.getMath().getStars());
		jData.setColors(data.getColors().getStars());
		jData.setFlags(data.getFlags().getStars());
		jData.setName(data.getName());
		jData.setSound(data.getSound() == true ? 1 : 0);
		jData.setNotifications(data.getNotifications() == true ? 1 : 0);
		jData.setReset(data.getReset().getStars());
		
		return jData;
	}
	
	/**
	 * Adds the over all average stars to the data
	 * @param data
	 */
	private static void addAverageStars(Data data) {
		data.addAverageStars();
	}
	
	/**
	 * Resets the level to all levels locked and
	 * only the first level open with 0 stars
	 * @param data
	 */
	public static void resetAllLevels(Data data){
		resetLevel(data, "Math");
		resetLevel(data, "Flags");
		resetLevel(data, "Colors");
	}
	
	/**
	 * Resets levels in the specific category to 
	 * only the first level open with 0 stars and 
	 * all the other levels locked.
	 * @param data
	 * @param category
	 */
	public static void resetLevel(Data data, String category){
		LevelStars resetStars = data.getReset();
		if(category == "Math")
			data.setMath(resetStars);
		if(category == "Colors")
			data.setMath(resetStars);
		if(category == "Flags")
			data.setMath(resetStars);
	}
	
}
