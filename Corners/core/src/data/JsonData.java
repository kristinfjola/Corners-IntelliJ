/**
 * @author 	Steinunn Fridgeirsdottir
 * @date 	12.03.2015
 * @goal 	JsonData is a data transfer object that holds all the same parameters
 * 			as the database and is built so it is easy to parse the data to a json file.
 */
package data;

import java.util.Arrays;

public class JsonData {
	public int[] Math;
	public int[] Colors;
	public int[] Flags;
	public int[] Reset;
	public String Name;
	public int Sound;
	
	/**
	 * @return Math - array with the level state of math
	 */
	public int[] getMath() {
		return Math;
	}
	/**
	 * @param math
	 */
	public void setMath(int[] math) {
		Math = math;
	}
	/**
	 * @return Colors - array with the level state of colors category
	 */
	public int[] getColors() {
		return Colors;
	}
	/**
	 * @param color
	 */
	public void setColors(int[] color) {
		Colors = color;
	}
	/**
	 * @return Flags - array with the level state of Flags category
	 */
	public int[] getFlags() {
		return Flags;
	}
	/**
	 * @param flags
	 */
	public void setFlags(int[] flags) {
		Flags = flags;
	}
	/**
	 * @return Name - the name of 'Carl' that is saved in the database
	 */
	public String getName() {
		return Name;
	}
	/**
	 * @param name
	 */
	public void setName(String name) {
		Name = name;
	}
	/**
	 * @return Reset - a array of the reset level state
	 * where all the level are locked exept level 1
	 */
	public int[] getReset() {
		return Reset;
	}
	/**
	 * @param reset
	 */
	public void setReset(int[] reset) {
		Reset = reset;
	}
	/**
	 * @return Sound - 1 if the sound is on and 0 if the sound is of
	 */
	public int getSound() {
		return Sound;
	}
	/**
	 * @param sound
	 */
	public void setSound(int sound) {
		Sound = sound;
	}
	@Override
	public String toString() {
		return "JsonData [Math=" + Arrays.toString(Math) + ", Colors="
				+ Arrays.toString(Colors) + ", Flags=" + Arrays.toString(Flags)
				+ ", Name=" + Name + "]";
	}
	
	
	
}
