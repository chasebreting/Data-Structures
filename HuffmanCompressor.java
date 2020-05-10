import java.io.FileReader;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Chase Breting
 */
public class HuffmanCompressor {

	/**
	 * an array list to store Huffman Nodes. i used an array list rather than a
	 * linked list because I mainly iterate through the lists in my methods and an
	 * array list is faster for that application. Also, I can easily get the initial
	 * size of the list from the hash map of characters created in initialFileScan
	 * so that is not an issue. I only need to resize the array in a single method
	 * (generateTree), so it does not affect performance much overall. Plus, I would
	 * rather have a contiguous chunk of memory for my data.
	 */
	private static ArrayList<HuffmanNode> fullList = new ArrayList<HuffmanNode>();

	/** a field to store a tree of Huffman Nodes */
	private static HuffmanNode tree = new HuffmanNode(null, 0);

	/**
	 * A method to scan the file and generate the list of Huffman Nodes
	 * 
	 * @param inputFileName the string that holds the location of the input file
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @return the array list of Huffman Nodes storing one type of character and its
	 *         frequency within the input file
	 */
	public static ArrayList<HuffmanNode> initialFileScan(String inputFileName)
			throws FileNotFoundException, IOException {
		HashMap<Character, Integer> table = new HashMap<>();
		try {
			FileReader reader = new FileReader(inputFileName);
			FileReader readerptr = new FileReader(inputFileName);
			// while the pointer is still reading characters, fill the hash table with the
			// characters and frequencies
			while (readerptr.read() != -1) {
				char c = (char) reader.read();
				if (table.containsKey(c))
					table.put(c, table.get(c) + 1);
				else {
					table.put(c, 1);
				}
			}
			reader.close();
			readerptr.close();
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException();
		} catch (IOException e) {
			throw new IOException();
		}
		// create an array list of huffman nodes with its size the same as the number of
		// keys in the hash table
		ArrayList<HuffmanNode> list = new ArrayList<HuffmanNode>(table.size());
		Set<Character> set = new HashSet<>(table.size());
		set = table.keySet();
		Iterator<Character> ptr = set.iterator();
		// for each key in the table, create a new Huffman Node storing the character
		// and frequency
		while (ptr.hasNext()) {
			char c = ptr.next();
			list.add(new HuffmanNode(c, table.get(c)));
		}
		return list;
	}

	/**
	 * a method to iterate through a list of Huffman Nodes and return the node with
	 * the lowest frequency
	 * 
	 * @param list the array list storing the full list of huffman nodes
	 * @return the huffman node with the smallest frequency
	 */
	public static HuffmanNode findSmallest(ArrayList<HuffmanNode> list) {
		Iterator<HuffmanNode> ptr = list.iterator();
		HuffmanNode smallest = ptr.next();
		while (ptr.hasNext()) {
			HuffmanNode temp = ptr.next();
			if (temp != null && temp.getFrequency() < smallest.getFrequency())
				smallest = temp;
		}
		return smallest;
	}

	/**
	 * a method to take two Huffman Nodes and return the "merged" node
	 * 
	 * @param smallerNode the node with the smaller character frequency
	 * @param largerNode  the node with the larger character frequency
	 * @return the "merged" huffman node
	 */
	public static HuffmanNode mergeNodes(HuffmanNode smallerNode, HuffmanNode largerNode) {
		// create new node with the sum of the two node's frequencies
		HuffmanNode mergedNode = new HuffmanNode(null, smallerNode.getFrequency() + largerNode.getFrequency());
		// set left and right children
		mergedNode.setLeft(smallerNode);
		mergedNode.setRight(largerNode);
		return mergedNode;
	}

	/**
	 * a method to produce the Huffman tree
	 * 
	 * @param list the full list of huffman nodes
	 * @return the huffman node representing a huffman tree
	 */
	public static HuffmanNode generateTree(ArrayList<HuffmanNode> list) {
		if (list.size() == 0)
			return null;
		// make a copy of the full list
		ArrayList<HuffmanNode> copy = new ArrayList<>();
		Iterator<HuffmanNode> iterator = list.iterator();
		while (iterator.hasNext()) {
			copy.add((HuffmanNode) iterator.next());
		}
		// until one node remains, merge the two nodes with smallest frequencies
		while (copy.size() > 1) {
			HuffmanNode smallerNode = HuffmanCompressor.findSmallest(copy);
			copy.remove(smallerNode);
			copy.trimToSize();
			HuffmanNode largerNode = HuffmanCompressor.findSmallest(copy);
			copy.set(copy.indexOf(largerNode), HuffmanCompressor.mergeNodes(smallerNode, largerNode));
		}
		return copy.get(0);
	}

	/**
	 * a recursive helper method for generateEncoding(HuffmanNode tree). this method
	 * generates the encoded values for each character and stores the value in the
	 * corresponding huffman node
	 * 
	 * @param ptr the node in the tree that is being referenced
	 * @param enc the string to store the nodes encoded value
	 */
	public static void generateEncodingHelper(HuffmanNode ptr, String enc) {
		// if child node exists, move to it and add a corresponding 0/1 to the string
		if (ptr.getLeft() == null || ptr.getRight() == null) // the base case
			ptr.setEnc(enc);
		if (ptr.getLeft() != null) {
			generateEncodingHelper(ptr.getLeft(), enc + "0");
		}
		if (ptr.getRight() != null) {
			generateEncodingHelper(ptr.getRight(), enc + "1");
		}
	}

	/**
	 * a method to output the character encoding of the huffman tree
	 * 
	 * @param tree the node representing a huffman tree
	 */
	public static void generateEncoding(HuffmanNode tree) {
		HuffmanCompressor.generateEncodingHelper(tree, new String());
	}

	/**
	 * a method to calculate the number of bits saved by Huffman encoding
	 * 
	 * @param fullList the entire list of Huffman Nodes
	 * @return the number of bits saved
	 */
	public static int calcSavings(ArrayList<HuffmanNode> fullList) {
		// the sum of the bits that the encoded file stores
		int sum = 0;
		// the total number of bits used for the original ASCII encoding of input file
		int numBitsInput = 0;
		for (HuffmanNode node : fullList) {
			sum += node.getFrequency() * node.getEnc().length();
			numBitsInput += node.getFrequency() * 8;
		}
		// calculate difference between number of bits in input and output files
		return numBitsInput - sum;
	}

	/**
	 * A method to rescan the input file and produce the encoded output
	 * 
	 * @param inputFileName  the string that holds the location of the input file
	 * @param outputFileName the string that holds the location of the output file
	 * @throws IOException
	 */
	public static void generateOutputFile(String inputFileName, String outputFileName, ArrayList<HuffmanNode> fullList)
			throws IOException {
		FileReader reader = new FileReader(inputFileName);
		FileReader readerptr = new FileReader(inputFileName);
		FileWriter writer = new FileWriter(outputFileName);
		// convert the array list into a hash map
		HashMap<Character, String> table = new HashMap<>();
		for (HuffmanNode node : fullList) {
			table.put(node.getInChar(), node.getEnc());
		}
		// while the pointer is still reading characters, write the encoded values to
		// the output text file
		while (readerptr.read() != -1) {
			writer.write(table.get((char) reader.read()));
		}
		reader.close();
		readerptr.close();
		writer.close();
	}

	/**
	 * a method to print the Huffman coding and the amount of space saved
	 * 
	 * @return the results of the Huffman Encoding
	 */
	public static String outputResults() {
		String output = null;
		output = "Huffman Encoding of Characters:" + "\n";
		// for each character, output its char value, frequency, and encoded value
		for (HuffmanNode node : HuffmanCompressor.fullList) {
			output += escapeSpecialCharacter("Character: " + node.getInChar()) + "\tFrequency: " + node.getFrequency()
					+ "\tEncoded Value: " + node.getEnc() + "\n";
		}
		// add the bit savings to the end of the table
		output += "\n" + "Calculated amount of storage saved: ";
		output += HuffmanCompressor.calcSavings(HuffmanCompressor.fullList) + " bits\n";
		return output;
	}

	/**
	 * a method to output a string that may contain a special character
	 * 
	 * @param x the string that may contain a special character
	 * @return the modified string
	 */
	public static String escapeSpecialCharacter(String x) {
		StringBuilder sb = new StringBuilder();
		for (char c : x.toCharArray()) {
			if (c >= 32 && c < 127)
				sb.append(c);
			else
				sb.append("[0x" + Integer.toOctalString(c) + "]");
		}
		return sb.toString();
	}

	/**
	 * the "main" method which takes an input file name, compresses it using huffman
	 * encoding, and exports the resulting text file with the name of the output
	 * file name.
	 * 
	 * @param inputFileName  the string that holds the location of the input file
	 * @param outputFileName the string that holds the location of the output file
	 * @return the results
	 */
	public static String huffmanCoder(String inputFileName, String outputFileName) {
		try {
			HuffmanCompressor.fullList = HuffmanCompressor.initialFileScan(inputFileName);
			HuffmanCompressor.tree = HuffmanCompressor.generateTree(HuffmanCompressor.fullList);
			HuffmanCompressor.generateEncoding(HuffmanCompressor.tree);
			HuffmanCompressor.generateOutputFile(inputFileName, outputFileName, HuffmanCompressor.fullList);
			System.out.print(HuffmanCompressor.outputResults());
			return "\n\nFile successfully compressed. Encoded text written to output file.\n";
		} catch (FileNotFoundException e) {
			return "File Not Found.";
		} catch (IOException e) {
			return "IO error.";
		}
	}

	public static void main(String[] args) {
		if (args.length == 2)
			System.out.println(HuffmanCompressor.huffmanCoder(args[0], args[1]));
		else
			System.out.println("Incorrect input parameters. Please enter input and output file paths.");
	}
}
