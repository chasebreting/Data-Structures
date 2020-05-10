import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * @author Chase Breting
 */
public class WordCounter {

 /**
  * A method to input a text file, collect distinct words and their frequencies,
  * and print that data to the output file
  * 
  * @param inputFile  the text file you want to read from
  * @param outputFile the text file to output the words and frequencies
  * @throws FileNotFoundException the input or output files are incorrect
  * @throws IOException there is an issue reading the file
  */
 public static void wordCount(String inputFile, String outputFile) throws FileNotFoundException, IOException {
  // create a new hash table of size 2
  // when resized, the table size is doubled
  HashTable table = new HashTable(2);
  FileReader reader = new FileReader(inputFile);
  FileReader readerptr = new FileReader(inputFile);
  StringBuilder builder = new StringBuilder();
  // build the input file into a string of text
  while (readerptr.read() != -1) {
   builder.append((char) reader.read());
  }
  reader.close();
  readerptr.close();
  // tokenize the string to retrieve words one at a time
  StringTokenizer tokenizer = new StringTokenizer(builder.toString(), new String(" \t\n\r\f-\'"));
  // loop until all words have been read
  while (tokenizer.hasMoreTokens()) {
   // scan in the next word
   String token = tokenizer.nextToken();
   // format the word
   builder = new StringBuilder();
   boolean wordContainsLetter = false;
   for (int i = 0; i < token.length(); i++) {
    if (Character.isLetter(token.charAt(i))) {
     builder.append(token.toLowerCase().charAt(i));
     wordContainsLetter = true;
    }
    if (token.charAt(i) == '\'' || token.charAt(i) == '-')
     builder.append(token.toLowerCase().charAt(i));
   }
   token = builder.toString();
   // only add the word to the list if it contains at least one letter
   if (wordContainsLetter) {
    // search for this word in the hash table
    Entry e = table.search(token);
    // if in table, increment its frequency
    if (e != null)
     e.setFreq(e.getFreq() + 1);
    // if not in table, insert
    else {
     table.insert(new Entry(token));
    }
   }
  }
  table.printAvgNumCollisions();
  ArrayList<Entry> list = table.sortAlphabetically(table.getTable());
  // write word frequencies to output file
  table.writeFrequencies(list, outputFile);
 }

 /**
  * the main method
  * 
  * @param args the input and output file paths
  * @throws FileNotFoundException the input or output files are incorrect
  * @throws IOException there is an issue reading the file
  */
 public static void main(String[] args) throws FileNotFoundException, IOException {
  if (args.length == 2)
   WordCounter.wordCount(args[0], args[1]);
  else
   System.out.println("Incorrect input parameters. Please enter input and output file paths.");
 }
}