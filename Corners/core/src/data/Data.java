package data;

public class Data {
	private LevelStars math;
	private LevelStars flags;
	private LevelStars colors;
	private double averageStars;
	private String name; //name of avatar

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LevelStars getStarsByString(String category){
		if(category == "Math"){
			math.setCategorieName(category);
			return getMath();
		}
		if(category == "Flags"){
			flags.setCategorieName(category);
			return getFlags();
		}
		else{
			colors.setCategorieName(category);
			return getColors();
		}
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
		calcAverageStars();
		return averageStars;
	}
	
	public void calcAverageStars() {
		int count = getAllFinished();
		double sumStars = allStars(math) + allStars(colors) + allStars(flags);
		this.averageStars = sumStars/count;
	}
	
	public int getAllFinished(){
		return finished(math) + finished(colors) + finished(flags);
	}
	
	public int finished(LevelStars cat){
		return cat.getLevelsFinished();
	}
	
	public double allStars(LevelStars cat){
		return cat.getAverageStars() * cat.getLevelsFinished();
	}
}
