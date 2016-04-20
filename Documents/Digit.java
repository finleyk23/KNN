package KNN;
import java.lang.Math.*;

public class Digit {
	public int label;
	public int[] data;

	public static void main(String[] args) {
	}

	public int getLabel() {
		return label;
	}

	public void setLabel(int newLabel) {
		label = newLabel;
	}

	public int[] getData() {
		return data;
	}

	public void setData(int[] newData, int numOfElements) {
		data = new int[numOfElements];
		data = newData;
	}

	public float distance(Digit digit) {
		int[] digitData = digit.getData();
		float distance = 0.0f;

		for (int i = 0; i < data.length; i++) {
			distance += Math.pow((data[i] - digitData[i]), 2);
		}

		return distance;
	}
}