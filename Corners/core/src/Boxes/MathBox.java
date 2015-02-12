/**
 * @author: Kristin Fjola Tomasdottir
 * @date	09.02.2015
 * @goal: 	A class for representing question and answer boxes 
 * 			for the category Math
 */
package boxes;

public class MathBox extends Box{

	int number;
	
	/**
	 * @param width	- width of box
	 * @param height - height of box
	 * @param number - answer for box
	 */
	public MathBox(int width, int height, int number){
		super(width, height);
		this.number = number;
	}

	/**
	 * @return answer for box
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * @param number
	 */
	public void setNumber(int number) {
		this.number = number;
	}
}
