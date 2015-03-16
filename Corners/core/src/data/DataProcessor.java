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
        try{
        	FileHandle file = Gdx.files.internal("db/db.json");
        	string = file.readString();
        }
        catch(Exception e){
        	e.printStackTrace();
        }
        try{
        	FileHandle file = Gdx.files.local("db/db.json");
        	string = file.readString();
        }
        catch(Exception e){
        	e.printStackTrace();
        }
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
        if(root != null){
			JsonObject category = root.getAsJsonObject();
			JsonArray math = category.getAsJsonArray("Math");
			JsonArray colors = category.getAsJsonArray("Colors");
			JsonArray flags = category.getAsJsonArray("Flags");
			JsonElement nameJson = category.get("Name");
			
			data.setMath(new LevelStars(math));
			data.setFlags(new LevelStars(flags));
			data.setColors(new LevelStars(colors));
			data.setName(nameJson.toString().replaceAll("\"", ""));
	    }
        else
        	return;
		
		/*Preferences prefs = Gdx.app.getPreferences("Level data");
		
		prefs.putString("Data", "Langur strengur");
		String name = prefs.getString("name", "No name stored");
		prefs.putBoolean("Sound On", true);*/
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
	
	public static JsonData convertData(Data data){
		addAverageStars(data);
		
		JsonData jData = new JsonData();

		jData.setMath(data.getMath().getStars());
		jData.setColors(data.getColors().getStars());
		jData.setFlags(data.getFlags().getStars());
		jData.setName(data.getName());
		
		return jData;
	}
	
	private static void addAverageStars(Data data) {
		data.addAverageStars();
	}
	
	public static void resetAllLevels(Data data){
		resetLevel(data, "Math");
		resetLevel(data, "Flags");
		resetLevel(data, "Colors");
	}
	
	public static void resetLevel(Data data, String category){
		LevelStars stars = data.getStarsByString(category);
		int[] array = {0,0,-1,-1,-1,-1,-1,-1,-1,-1};
		JsonArray jarray = new JsonArray();
		LevelStars resetStars = new LevelStars(new JsonArray());
		if(category == "Math")
			data.setMath(resetStars);
	}
	
}
