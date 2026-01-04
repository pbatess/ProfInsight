package finalproject;

import java.util.ArrayList;

public class RatingByGender extends DataAnalyzer{
	private MyHashTable<String,MyHashTable<String,MyHashTable<String,Integer>>> extracted;

	public RatingByGender(Parser p) {
		super(p);
	}

	@Override
	public MyHashTable<String, Integer> getDistByKeyword(String keyword) {

		String lcKeyword = keyword.toLowerCase().trim();
		String[] split = lcKeyword.split(",");
		// splits string
		String Gender = split[0];
		String TypeRating = split[1];
		if (!TypeRating.equals("difficulty") && !TypeRating.equals(" difficulty") && !TypeRating.equals("quality") && !TypeRating.equals(" quality")){
			return null;
		}
		// create two strings for gender and type of rating, trim in case of extra spaces
		String Rating = TypeRating.trim();
		// final rating string after checking validity of input

		return extracted.get(Gender).get(Rating);

	}

	@Override
	public void extractInformation() {

		extracted = new MyHashTable<>(100);

		for (String[] data : parser.data){

			String gender = data[parser.fields.get("gender")].toLowerCase();
			double quality = Double.parseDouble(data[parser.fields.get("student_star")]);
			String difficulty = data[parser.fields.get("student_difficult")];
			// extracts gender, quality rating, and difficulty rating

			MyHashTable<String, MyHashTable<String,Integer>> ratings = extracted.get(gender);

			if (ratings == null){
				// if gender not yet in HashTable, add gender and HashTables for both quality and difficulty

				ratings = new MyHashTable<>(100);
				ratings.put("quality", new MyHashTable<>(100));
				ratings.put("difficulty", new MyHashTable<>(100));
				extracted.put(gender, ratings);
			}

			MyHashTable<String,Integer> numRatingsQuality = ratings.get("quality");
			MyHashTable<String,Integer> numRatingsDifficulty = ratings.get("difficulty");

			if (numRatingsQuality.getKeySet().isEmpty()){
				// if keys of the HashTable for quality is empty, add them

				MyHashTable<String,Integer> Qual = putKeys(numRatingsQuality);
				ratings.put("quality",Qual);

			}

			if (numRatingsDifficulty.getKeySet().isEmpty()){
				// if keys of the HashTable for difficulty is empty, add them

				MyHashTable<String,Integer> Diff = putKeys(numRatingsDifficulty);
				ratings.put("difficulty", Diff);

			}

			String qualString = getRating(quality);
			Integer ratingQ = numRatingsQuality.get(qualString);
			ratingQ++;
			// increment rating for quality and add back to HashTable
			numRatingsQuality.put(qualString, ratingQ);

			Integer ratingD = numRatingsDifficulty.get(difficulty);
			ratingD++;
			// increment rating for difficulty and add back to HashTable
			numRatingsDifficulty.put(difficulty,ratingD);

		}

	}

	private String getRating(double rating){
		// converts double rating into whole number string

		if (rating >= 1 && rating < 2) {
			return "1";
		} else if (rating >= 2 && rating < 3) {
			return "2";
		} else if (rating >= 3 && rating < 4) {
			return "3";
		} else if (rating >= 4 && rating < 5) {
			return "4";
		} else {
			return "5";
		}

	}

	private MyHashTable<String,Integer> putKeys(MyHashTable<String,Integer> table){
		// adds rating keys to given HashTable

		table.put("1",0);
		table.put("2",0);
		table.put("3",0);
		table.put("4",0);
		table.put("5",0);

		return table;

	}
}
