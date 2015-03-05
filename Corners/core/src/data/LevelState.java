package data;

import java.util.List;

public class LevelState {
	private LevelStars math;
	private LevelStars flags;
	private LevelStars colors;
	private double averageStars;
	
	/*public int[] levelStarsToArray(){
		List<LevelStars> levelStars = getLevelStars();
		int[] starsArray = new int[levelStars.size()];
		for(int i = 0; i < levelStars.size(); i++){
			starsArray[i] = levelStars.get(i).getStars();
		}
		return starsArray;
	}*/
	
	public LevelStars getStarsByString(String category){
		if(category == "Math")
			return getMath();
		if(category == "Flags")
			return getFlags();
		else
			return getColors();
	}
	
	public LevelStars getMath() {
		return math;
	}

	public void setMath(LevelStars math) {
		this.math = math;
	}

	public LevelStars getFlags() {
		return flags;
	}

	public void setFlags(LevelStars flags) {
		this.flags = flags;
	}

	public LevelStars getColors() {
		return colors;
	}

	public void setColors(LevelStars colors) {
		this.colors = colors;
	}

	public void setAverageStars(double averageStars) {
		this.averageStars = averageStars;
	}

	public double getAverageStars() {
		return averageStars;
	}
	
	/*public void calcAverageStars() {
		double sumStars = 0;
		int[] starsArray = levelStarsToArray();
		for(int stars : starsArray){
			sumStars += stars;
		}
		this.averageStars = sumStars/starsArray.length;
	}*/
	
}
