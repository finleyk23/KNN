package KNN;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.String.*;
import java.lang.Integer.*;
import java.lang.Long.*;
import java.util.ArrayList;

public class DataManagement {
	private static ArrayList<Digit> digitManager;

	public static void main (String[] args) {
		digitManager = new ArrayList<Digit>();
	}

	public void importData (String fileName) throws IOException {
		File file = new File(fileName);
		FileReader fileReader = new FileReader(file);
		BufferedReader input = new BufferedReader(fileReader);

		String line = input.readLine();
		int numOfElements = (int)Long.parseLong(line.substring(15, 19), 16);
		int[] data = new int[numOfElements];
		line = input.readLine();

		final int NUM_OF_COLUMNS = 28;
		final int NUM_OF_ROWS = 28;
		int columnPos = 0;
		int rowPos = 0;
		int linePos = 0;

		while (line != null) {
			while (rowPos < NUM_OF_ROWS) {
				while (columnPos < NUM_OF_COLUMNS) {
					int pixel = (int)Long.parseLong(line.substring(linePos, linePos + 2), 16); //pixels are width 2
					linePos += 2;
					if (linePos >= line.length()) {
						line = input.readLine();
						linePos = 0;
					} if (line.charAt(linePos) == ' ') {
						linePos++;
					}

					columnPos++;
				}
				columnPos = 0;
				rowPos++;
			}
			rowPos = 0;
			Digit newDigit = new Digit();
			newDigit.setData(data, numOfElements);
			digitManager.add(newDigit);
		}
	}

	public void importLabels(String fileName) throws IOException {
		File file = new File(fileName);
		FileReader fileReader = new FileReader(file);
		BufferedReader input = new BufferedReader(fileReader);

		String line = input.readLine();
		int linePos = 20;
		int labelsPos = 0;

		while (line != null) {
			while (linePos < line.length()) {
				if (line.charAt(linePos) != ' ') {
					Digit digit = digitManager.get(labelsPos);
					digit.setLabel(Integer.parseInt("" + line.charAt(linePos + 1))); // first digit in label (with width 2) is 0
				}
				linePos += 2;
			}

			line = input.readLine();
		}
	}

	public int size() {
		return digitManager.size();
	}

	public Digit get(int pos) {
		return digitManager.get(pos);
	}
}