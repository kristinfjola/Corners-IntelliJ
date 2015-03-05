
package data;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;


public class DataProcessor {
	
	public static void getData(Data levelState){
		JsonParser jp = new JsonParser();
        JsonElement root = null;
		try {
			root = jp.parse(new InputStreamReader((InputStream) DataProcessor.class.getResourceAsStream("db.json"), "UTF-8"));
		} catch (JsonIOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonSyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if(root != null){
			JsonObject category = root.getAsJsonObject();
			JsonArray math = category.getAsJsonArray("Math");
			JsonArray colors = category.getAsJsonArray("Colors");
			JsonArray flags = category.getAsJsonArray("Flags");
			JsonElement name = category.get("name");
			levelState.setMath(new LevelStars(math));
			levelState.setFlags(new LevelStars(flags));
			levelState.setColors(new LevelStars(colors));
	    }
        else
        	return;
	}
	
	public void setData(Data levelState){
		
	}
}
