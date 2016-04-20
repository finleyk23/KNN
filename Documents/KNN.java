package KNN;
import java.util.ArrayList;
import java.io.IOException;
import java.util.Map.*;
import java.lang.Integer.*;
import java.util.*;

public class KNN {
	public static void main(String[] args) throws IOException {
		DataManagement trainingSet = new DataManagement();
		DataManagement testSet = new DataManagement();
		int k = Integer.parseInt(args[2]);

		trainingSet.importData("trainImages.txt");
		trainingSet.importLabels("trainLabels.txt");
		testSet.importData("testImages.txt");
		testSet.importLabels("testLabels.txt");

		//start KNN

		Map<Digit, Float> neighborDiff = new HashMap<Digit, Float>();
		Map<Digit, Float> kNearest = new HashMap<Digit, Float>();

		for (int i = 0; i < testSet.size(); i++) {
			for (int j = 0; j < trainingSet.size(); j++) {
				float distance = testSet.get(i).distance(trainingSet.get(j));
				neighborDiff.put(trainingSet.get(j), distance);
			}
			// find k Nearest for particular test Digit
			List<Digit> keys = new ArrayList<Digit>(neighborDiff.keySet());
			List<Digit> kNearestKeys = new ArrayList<Digit>();
			while (keys.size() > 0) {
				Digit key = keys.remove(0);
				if (kNearest.size() < k) {
					kNearest.put(key, neighborDiff.get(key));
					neighborDiff.remove(key);
					kNearestKeys.add(key);
				} else {
					float largestDist = neighborDiff.get(kNearestKeys.get(0));
					for (int j = 0; j < k; j++) {
						if (neighborDiff.get(kNearestKeys.get(j)) > largestDist) {
							largestDist = neighborDiff.get(kNearestKeys.get(j));
						}
					}
					if (neighborDiff.get(key) < largestDist) {
						kNearestKeys.remove(largestDist);
						kNearestKeys.add(key);
						kNearest.put(key, neighborDiff.get(key));
					}
				}
			}

			// find probable class

			List<Integer> classes = new ArrayList<Integer>();
			List<Float> totalDistances = new ArrayList<Float>();
			List<Integer> numOfInstances = new ArrayList<Integer>();

			for (int j = 0; j < k; j++) {
				int label = kNearestKeys.get(j).getLabel();
				float distance = kNearest.get(label);

				if (classes.contains(label)) {
					int pos = classes.indexOf(label);
					distance += totalDistances.get(pos);
					totalDistances.set(pos, distance);
					int instances = numOfInstances.get(pos);
					instances++;
					numOfInstances.set(pos, instances);
				} else {
					classes.add(label);
					totalDistances.add(distance);
					numOfInstances.add(1);
				}
			}
			float shortestDistance = -1;
			int probableClass = -1;
			for (int j = 0; j < classes.size(); j++) {
				float distance = totalDistances.get(j) / (float)numOfInstances.get(j);
				if ((shortestDistance < 0 && shortestDistance > distance) || shortestDistance >= 0) {
					shortestDistance = distance;
					probableClass = classes.get(j);
				}
			}

			System.out.println(probableClass);

			neighborDiff.clear();
			kNearest.clear();
		}
	}
}