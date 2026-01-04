package finalproject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;


public class MyHashTable<K,V> implements Iterable<MyPair<K,V>>{
	// num of entries to the table
	private int size;
	// num of buckets
	private int capacity = 16;
	// load factor needed to check for rehashing
	private static final double MAX_LOAD_FACTOR = 0.75;
	// ArrayList of buckets. Each bucket is a LinkedList of HashPair
	private ArrayList<LinkedList<MyPair<K,V>>> buckets;


	// constructors
	public MyHashTable() {

		this.size = 0;
		this.capacity = 16;
		this.buckets = new ArrayList<LinkedList<MyPair<K,V>>>(this.capacity);
		for (int i = 0; i < capacity; i++){
			buckets.add(new LinkedList<MyPair<K, V>>());
		}

	}

	public MyHashTable(int initialCapacity) {

		this.capacity = initialCapacity;
		this.size = 0;
		this.buckets = new ArrayList<LinkedList<MyPair<K,V>>>(this.capacity);
		for (int i = 0; i < capacity; i++){
			buckets.add(new LinkedList<MyPair<K, V>>());
		}

	}

	public int size() {
		return this.size;
	}

	public boolean isEmpty() {
		return this.size == 0;
	}

	public int numBuckets() {
		return this.capacity;
	}

	/**
	 * Returns the buckets variable. Useful for testing  purposes.
	 */
	public ArrayList<LinkedList< MyPair<K,V> > > getBuckets(){
		return this.buckets;
	}

	/**
	 * Given a key, return the bucket position for the key.
	 */
	public int hashFunction(K key) {
		int hashValue = Math.abs(key.hashCode())%this.capacity;
		return hashValue;
	}

	/**
	 * Takes a key and a value as input and adds the corresponding HashPair
	 * to this HashTable. Expected average run time  O(1)
	 */
	public V put(K key, V value) {

		int bIndex = hashFunction(key);

		LinkedList<MyPair<K,V>> bucket = this.buckets.get(bIndex);
		MyPair<K, V> pair = new MyPair(key, value);

		for (MyPair<K,V> p : bucket){
			// iterates through list of pairs to check if key is already present in list
			if (p.getKey().equals(key)){
				// changes key if value already present
				V prev = p.getValue();
				p.setValue(value);
				return prev;
			}
		}

		// else if key not present:
		bucket.add(pair);
		this.size++;
		double loadFactor = (double) size / capacity;
		if (loadFactor > MAX_LOAD_FACTOR){
			// resizes if no space after adding
			rehash();
		}
		return null;

	}


	/**
	 * Get the value corresponding to key. Expected average runtime O(1)
	 */

	public V get(K key) {

		int bIndex = hashFunction(key);
		LinkedList<MyPair<K,V>> bucket = this.buckets.get(bIndex);

		for (MyPair<K,V> p : bucket) {
			//iterates list until key is found, then returns it
			if (p.getKey().equals(key)) {
				return p.getValue();
			}
		}

		return null;

	}

	/**
	 * Remove the HashPair corresponding to key . Expected average runtime O(1)
	 */
	public V remove(K key) {

		int bIndex = hashFunction(key);
		LinkedList<MyPair<K,V>> bucket = this.buckets.get(bIndex);

		for(MyPair<K,V> p : bucket){
			// iterates list until key is found, removes value and decreases size
			if (p.getKey().equals(key)){
				V val = p.getValue();
				bucket.remove(p);
				size--;
				return val;
			}
		}

		return null;

	}


	/**
	 * Method to double the size of the hashtable if load factor increases
	 * beyond MAX_LOAD_FACTOR.
	 * Made public for ease of testing.
	 * Expected average runtime is O(m), where m is the number of buckets
	 */
	public void rehash() {

		int rehashedCap = capacity * 2;
		ArrayList<LinkedList<MyPair<K, V>>> newBuckets = new ArrayList<>(rehashedCap);

		for (int i = 0; i < rehashedCap; i++) {
			// adds linked list to new spaces
			newBuckets.add(new LinkedList<>());
		}

		for (LinkedList<MyPair<K, V>> bucket : buckets) {

			for (MyPair<K, V> pair : bucket) {

				int index = Math.abs(pair.getKey().hashCode() % rehashedCap);
				// gets hashcode with new capacity
				LinkedList<MyPair<K, V>> buck = newBuckets.get(index);
				buck.add(pair);
				// adds previous values to new list
			}
		}

		buckets = newBuckets;
		capacity = rehashedCap;

	}


	/**
	 * Return a list of all the keys present in this hashtable.
	 * Expected average runtime is O(m), where m is the number of buckets
	 */

	public ArrayList<K> getKeySet() {

		ArrayList<K> keyset = new ArrayList<K>();

		for (LinkedList<MyPair<K,V>> list : buckets){

			for (MyPair<K,V> p : list){
				// adds every key to arrayList by iterating LinkedList
				keyset.add(p.getKey());
			}
		}

		return keyset;
	}

	/**
	 * Returns an ArrayList of unique values present in this hashtable.
	 * Expected average runtime is O(m) where m is the number of buckets
	 */
	public ArrayList<V> getValueSet() {

		ArrayList<V> values = new ArrayList<>();

		for (LinkedList<MyPair<K,V>> list : buckets){

			for (MyPair<K,V> p : list){

				V value = p.getValue();

				if(!values.contains(value)){
					// prevents repeating values
					values.add(value);
				}
			}
		}

		return values;
	}


	/**
	 * Returns an ArrayList of all the key-value pairs present in this hashtable.
	 * Expected average runtime is O(m) where m is the number of buckets
	 */
	public ArrayList<MyPair<K, V>> getEntries() {

		ArrayList<MyPair<K,V>> Pairs = new ArrayList<MyPair<K,V>>();

		for (LinkedList<MyPair<K,V>> list : buckets){

			Pairs.addAll(list);
			// adds all pairs from list to Array
		}

		return Pairs;

	}


	@Override
	public MyHashIterator iterator() {
		return new MyHashIterator();
	}


	private class MyHashIterator implements Iterator<MyPair<K,V>> {
		private Iterator<MyPair<K,V>> iterator;
		private int bindex;

		private MyHashIterator() {

			this.bindex = 0;
			// current bucket index

			while (buckets.get(bindex).isEmpty() && this.bindex < capacity) {
				// iterates until finds a non-empty bucket, also prevents index from going beyond capacity

				this.bindex++;
			}

			if (bindex < capacity) {
				// sets iterator over bucket

				iterator = buckets.get(bindex).iterator();
			}


		}

		@Override
		public boolean hasNext() {

			if (iterator.hasNext()){
				// checks there are more elements
				return true;
			}
			else {
				bindex++;
				// increments to move to next bucket
				while (bindex < capacity && buckets.get(bindex).isEmpty()) {
					// iterates over remaining buckets until end, or it hits a bucket that isn't empty
					bindex++;
				}
				if (bindex < capacity) {
					// if bucket found, sets iterator to that found bucket
					iterator = buckets.get(bindex).iterator();
					return true;
				}
				return false;
			}

		}

		@Override
		public MyPair<K,V> next() {

			if (this.hasNext()){
				// returns next element
				return iterator.next();
			}

			return null;

		}

	}
}
