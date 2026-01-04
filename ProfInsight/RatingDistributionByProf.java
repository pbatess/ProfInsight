package finalproject;

public class RatingDistributionByProf extends DataAnalyzer {
	private MyHashTable<String, MyHashTable<String, Integer>> professorRatings;
    public RatingDistributionByProf(Parser p) {
        super(p);
    }

	@Override
	public MyHashTable<String, Integer> getDistByKeyword(String keyword) {

		String lcKeyword = keyword.trim().toLowerCase();
		// makes sure string is lowercase, with no extra spaces before the words
		System.out.println(lcKeyword);
		return professorRatings.get(lcKeyword);

	}

	@Override
	public void extractInformation() {

		professorRatings = new MyHashTable<>(10000);

		for (String[] data : parser.data) {
			// iterates over all data

			String prof = data[parser.fields.get("professor_name")].trim().toLowerCase().replaceAll("\\s{2,}", " ");
			// extracts name of professor, with one space between name

			double ratings = Double.parseDouble((data[parser.fields.get("student_star")]));
			// extracts student rating for professor

			String ratingCategory = ratingString(ratings);
			// gets number ratings double is closest to as a string

			MyHashTable<String, Integer> professor = professorRatings.get(prof);

			if (professor == null) {
				// if professor has not been added to Hash Table, adds professor and creates hashtable to store ratings

				professor = new MyHashTable<>(5);
				professor.put("1", 0);
				professor.put("2", 0);
				professor.put("3", 0);
				professor.put("4", 0);
				professor.put("5", 0);
				professorRatings.put(prof, professor);

			}

			int count = professor.get(ratingCategory);
			// gets count for corresponding rating given and increments
			professor.put(ratingCategory, count + 1);
		}

	}

	private String ratingString(double quality) {
		// converts double rating into whole number string

		if (quality >= 1 && quality < 2) {
			return "1";
		} else if (quality >= 2 && quality < 3) {
			return "2";
		} else if (quality >= 3 && quality < 4) {
			return "3";
		} else if (quality >= 4 && quality < 5) {
			return "4";
		} else {
			return "5";
		}
	}
}
