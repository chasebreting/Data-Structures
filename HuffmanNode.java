/**
 * @author Chase Breting
 */
public class HuffmanNode {

	/** the character the node denotes */
	private Character inChar;

	/**
	 * the frequency of occurrences of all characters in the subtree rooted at this
	 * node
	 */
	private int frequency;

	/** the encoded value of the node */
	private String enc;

	/** the left child */
	private HuffmanNode left;

	/** the right child */
	private HuffmanNode right;

	/**
	 * the constructor
	 */
	public HuffmanNode(Character inChar, int frequency) {
		this.inChar = inChar;
		this.frequency = frequency;
	}

	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	public Character getInChar() {
		return inChar;
	}

	public void setInChar(Character inChar) {
		this.inChar = inChar;
	}

	public HuffmanNode getLeft() {
		return left;
	}

	public void setLeft(HuffmanNode left) {
		this.left = left;
	}

	public HuffmanNode getRight() {
		return right;
	}

	public void setRight(HuffmanNode right) {
		this.right = right;
	}

	public String getEnc() {
		return enc;
	}

	public void setEnc(String enc) {
		this.enc = enc;
	}

}
