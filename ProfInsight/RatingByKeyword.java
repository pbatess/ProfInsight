package finalproject;

import java.util.ArrayList;


public class RatingByKeyword extends DataAnalyzer {
	private MyHashTable<String, MyHashTable<String,Integer>> wordPerRating;
	
    public RatingByKeyword(Parser p) {
        super(p);
    }

	@Override
	public MyHashTable<String, Integer> getDistByKeyword(String keyword) {

		String lcKeyword = keyword.trim().toLowerCase();
		return wordPerRating.get(lcKeyword);

	}

	@Override
	public void extractInformation() {

		wordPerRating = new MyHashTable<>(100);

		for (String[] data : parser.data){

			String[] sentence = data[parser.fields.get("comments")].toLowerCase().replaceAll("[^a-z']", " ").split("\\s+");
			Double ratings = Double.parseDouble(data[parser.fields.get("student_star")]);
			String quality = getRating(ratings);
			// splits sentence into individual words and creates quality String from rating

			MyHashTable<String, Integer> Tracker = new MyHashTable<>(100);
			// Hash Table to track occurrences of word in current string

			for (String s : sentence){

				if(!s.isEmpty()){
					// if a word

					ArrayList<String> occurrences = Tracker.getKeySet();

					if (!occurrences.contains(s)){
						// if string not yet occurred in sentence, add to table
						Tracker.put(s,0);
					}

					MyHashTable<String,Integer> occurrencesPerRating = wordPerRating.get(s);

					if (occurrencesPerRating == null){
						// if string not yet in HashTable, add string and HashTable storing rating strings and num occurrences

						occurrencesPerRating = new MyHashTable<>(5);
						occurrencesPerRating.put("1",0);
						occurrencesPerRating.put("2",0);
						occurrencesPerRating.put("3",0);
						occurrencesPerRating.put("4",0);
						occurrencesPerRating.put("5",0);
						wordPerRating.put(s, occurrencesPerRating);
					}

					int times = Tracker.get(s);

					if (times == 0) {
						// if string hasn't appeared in current string yet, increment and add back to hash table.

						int count = occurrencesPerRating.get(quality);
						count++;
						occurrencesPerRating.put(quality, count);
						Tracker.put(s,1);
					}
				}
			}
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
}
