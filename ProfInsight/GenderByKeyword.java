package finalproject;

import java.util.ArrayList;
import java.util.LinkedList;

public class GenderByKeyword extends DataAnalyzer {
	private MyHashTable<String, MyHashTable<String,Integer>> numTimes;
	
	public GenderByKeyword(Parser p) {
		super(p);
	}

	@Override
	public MyHashTable<String, Integer> getDistByKeyword(String keyword) {

		String lcKeyword = keyword.trim().toLowerCase();
		return numTimes.get(lcKeyword);

	}

	@Override
	public void extractInformation() {

		numTimes = new MyHashTable<>(1000);

		for (String[] data : parser.data){

			String[] sentence = data[parser.fields.get("comments")].toLowerCase().replaceAll("[^a-z']", " ").split("\\s+");
			// splits sentence into array of words, without extra characters not in the alphabet
			String gender = data[parser.fields.get("gender")];
			// extracts review and gender of professor

			MyHashTable<String, Integer> Tracker = new MyHashTable<>(100);
			// Hash Table to track occurrences of word in current string

			for (String s : sentence){
				// iterates every word

				if (!s.isEmpty()){
					// if word isn't empty

					MyHashTable<String, Integer> genderCount = numTimes.get(s);
					// extracts hashtable storing gender and count (number of times word appears)
					ArrayList<String> occurrences = Tracker.getKeySet();
					// list of all strings currently in tracker

					if (!occurrences.contains(s)){
						// if current string not in tracker, add it
						Tracker.put(s,0);
					}

					if (genderCount == null){
						// if string not in HashTable, adds string and creates HashTable of Genders and occurrences
						genderCount = new MyHashTable<>(3);
						genderCount.put("M", 0);
						genderCount.put("F", 0);
						genderCount.put("X", 0);
						numTimes.put(s, genderCount);
					}

					int times = Tracker.get(s);

					if (times == 0){
						// if string hasn't appeared in current string yet, increment and add back to hash table.
						int count = genderCount.get(gender);
						count++;
						genderCount.put(gender, count);
						Tracker.put(s, 1);
					}
				}
			}
		}
	}
}
