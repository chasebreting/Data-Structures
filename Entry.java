/**
 * @author Chase Breting
 */

public class Entry {
	// the distinct word that this entry stores
	private String key;
	// the number of times the key appears in the input file
	private int freq;

	public Entry(String key) {
		this.key = key;
		this.freq = 1;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public int getFreq() {
		return freq;
	}

	public void setFreq(int freq) {
		this.freq = freq;
	}
}