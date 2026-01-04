package finalproject;

import java.util.ArrayList;

public class RatingDistributionBySchool extends DataAnalyzer {
	private MyHashTable<String, MyHashTable<String, MyPair<Integer,Double>>> profAverages;

	public RatingDistributionBySchool(Parser p) {
		super(p);
	}

	@Override
	public MyHashTable<String, Integer> getDistByKeyword(String keyword) {

		String lcKeyword = keyword.trim().toLowerCase();
		MyHashTable<String, Integer> finalProfRatings = new MyHashTable<String, Integer>(20);
		MyHashTable<String, MyPair<Integer, Double>> ratingsForProfs = profAverages.get(lcKeyword);
		// gets HashTable of professors for given school

		if (ratingsForProfs == null){
			// checks Hash Table is not null (the keyword is a valid school)
			return null;
		}

		ArrayList<String> professors = ratingsForProfs.getKeySet();
		// gets all professors in an array

		for (String p : professors) {
			// iterates professor array

			MyPair<Integer, Double> pairRatings = ratingsForProfs.get(p);
			int numRatings = pairRatings.getKey();
			double average = pairRatings.getValue() / numRatings;
			// averages ratings by dividing sum by the total number of ratings
			String roundedAverage = Double.toString(Math.round(average * 100.0) / 100.0);
			// rounds double to two decimal places and converts to string
			String profAndAverage = p + "\n" + roundedAverage;
			// creates professor and average string
			finalProfRatings.put(profAndAverage, numRatings);
			// adds string and number of ratings to final HashTable

		}

		return finalProfRatings;
	}

	@Override
	public void extractInformation() {

		profAverages = new MyHashTable<>(100000);

		for (String[] data : parser.data){
			// iterates data

			String school = data[parser.fields.get("school_name")].trim().toLowerCase();
			double rating = Double.parseDouble(data[parser.fields.get("student_star")]);
			String prof = data[parser.fields.get("professor_name")].trim().toLowerCase();
			// extracts school, given reviews quality rating, and the professors name

			MyHashTable<String, MyPair<Integer, Double>> schoolProfs = profAverages.get(school);

			if (schoolProfs == null){
				// if school hasn't been added to HashTable, adds school and new hashTable to store professors
				schoolProfs = new MyHashTable<>(100);
				profAverages.put(school, schoolProfs);
			}

			MyPair<Integer, Double> profStats = schoolProfs.get(prof);

			if (profStats == null){
				// if professor hasn't been added, add professor and MyPair to store the sum of ratings and number of ratings
				profStats = new MyPair(0,0.0);
				schoolProfs.put(prof, profStats);
			}

			Integer count = profStats.getKey() + 1;
			// increments number of ratings
			Double sum = profStats.getValue() + rating;
			// adds rating to sum

			schoolProfs.put(prof, new MyPair(count, sum));
			// adds new values back to MyPair
			profAverages.put(school, schoolProfs);

		}
	}
}
