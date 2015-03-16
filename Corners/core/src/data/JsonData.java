package data;

import java.util.Arrays;

public class JsonData {
	public int[] Math;
	public int[] Colors;
	public int[] Flags;
	public String Name;
	public int[] getMath() {
		return Math;
	}
	public void setMath(int[] math) {
		Math = math;
	}
	public int[] getColors() {
		return Colors;
	}
	public void setColors(int[] color) {
		Colors = color;
	}
	public int[] getFlags() {
		return Flags;
	}
	public void setFlags(int[] flags) {
		Flags = flags;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	@Override
	public String toString() {
		return "JsonData [Math=" + Arrays.toString(Math) + ", Colors="
				+ Arrays.toString(Colors) + ", Flags=" + Arrays.toString(Flags)
				+ ", Name=" + Name + "]";
	}
	
	
	
}
