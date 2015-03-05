package data;

import com.google.gson.JsonArray;

public class LevelStars {
	private int[] stars;
	private double averageStars;
	private String categorieName;
	
	public LevelStars(JsonArray array){
		int[] numbers = new int[array.size()];
		for (int i = 0; i < array.size(); ++i) {
		    numbers[i] = array.get(i).getAsInt();
		}
		setStars(numbers);
	}
	
	public String getCategorieName() {
		return categorieName;
	}

	public void setCategorieName(String categorieName) {
		this.categorieName = categorieName;
	}

	public int[] getStars() {
		return stars;
	}
	
	public void setStars(int[] stars) {
		this.stars = stars;
	}
	
	public double getAverageStars() {
		calcAverageStars();
		return averageStars;
	}
	
	public void calcAverageStars() {
		double count = 0.0;
		double sumStars = 0.0;
		for(int i = 0; i < stars.length; i++){
			if(stars[i] != -1 && stars[i] != 0){
				count = count + 1.0;
				sumStars += stars[i];
			}
		}
		this.averageStars = sumStars / count;
	}
	

	/*public int getLevelnumber() {
		return levelnumber;
	}
	
	public int getStars() {
		return stars;
	}

	public void setLevelnumber(int levelnumber) {
		this.levelnumber = levelnumber;
	}

	public void setStars(int stars) {
		this.stars = stars;
	}*/
}
