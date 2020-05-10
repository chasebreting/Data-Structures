import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Iterator;

/**
 * @author Chase Breting
 */
public class HashTable {

 // the hash table which uses separate chaining
 private ArrayList<LinkedList<Entry>> table;

 // the number of lists the hash table can store
 private int size;

 public HashTable(int size) {
  // the size controls what the size will be doubles of (when resizing occurs)
  // table size shouldn't be made of a multiple of 31
  if (size % 31 == 0)
   size++;
  this.table = new ArrayList<LinkedList<Entry>>(size);
  // initialize the lists inside the table
  for (int i = 0; i < size; i++)
   table.add(new LinkedList<Entry>());
  this.size = size;
 }

 public ArrayList<LinkedList<Entry>> getTable() {
  return table;
 }

 public void setTable(ArrayList<LinkedList<Entry>> table) {
  this.table = table;
 }

 public int getSize() {
  return size;
 }

 public void setSize(int size) {
  this.size = size;
 }

 /**
  * a method to resize the table if needed
  */
 public void resize() {
  // calculate total number of entries in table
  int counter = 0;
  Iterator<LinkedList<Entry>> listPtr = getTable().iterator();
  while (listPtr.hasNext()) {
   Iterator<Entry> ptr = listPtr.next().iterator();
   while (ptr.hasNext()) {
    counter++;
    ptr.next();
   }
  }
  // if load capacity >= 1, then resize
  if (counter / getSize() >= 1) {
   ArrayList<LinkedList<Entry>> newTable = new ArrayList<LinkedList<Entry>>(getSize() * 2);
   // initialize lists in new table
   while (newTable.size() < getSize() * 2)
    newTable.add(new LinkedList<Entry>());
   // iterate through entries in current table, recalculate their hash codes, and
   // insert them at the index of their new hash codes in the new table.
   // refresh the list ptr
   listPtr = getTable().iterator();
   while (listPtr.hasNext()) {
    Iterator<Entry> ptr = listPtr.next().iterator();
    while (ptr.hasNext()) {
     Entry temp = ptr.next();
     newTable.get(Math.abs(temp.getKey().hashCode()) % (getSize() * 2)).add(temp);
    }
   }
   setTable(newTable);
   setSize(getSize() * 2);
  }
 }

 /**
  * a method to return an entry in the table based on the key
  * 
  * @param key the string to search for
  * @return the entry if found or null if not
  */
 public Entry search(String key) {
  int hashCode = Math.abs(key.hashCode()) % getSize();
  // iterate through linked list with entry possibly present
  Iterator<Entry> ptr = getTable().get(hashCode).iterator();
  while (ptr.hasNext()) {
   Entry temp = ptr.next();
   if (temp.getKey().equals(key)) {
    return temp;
   }
  }
  return null;
 }

 /**
  * a method to add a new entry to the hash table
  * 
  * @param e the entry to add
  */
 public void insert(Entry e) {
  int hashCode = Math.abs(e.getKey().hashCode()) % getSize();
  getTable().get(hashCode).add(e);
  // resize if needed
  resize();
 }

 /**
  * a method to print the table's entries to the terminal
  */
 public void printTableContents() {
  Iterator<LinkedList<Entry>> listPtr = getTable().iterator();
  int ctr = 0;
  while (listPtr.hasNext()) {
   System.out.print("Index: " + ctr + "  ");
   Iterator<Entry> ptr = listPtr.next().iterator();
   while (ptr.hasNext()) {
    System.out.print(ptr.next() + ", ");
   }
   System.out.print("\n");
   ctr++;
  }
 }

 /**
  * a method to calculate the average number of collisions of using separate
  * chaining and then print the results to the terminal
  */
 public void printAvgNumCollisions() {
  int numEntries = 0;
  int numSlots = getSize();
  // iterate through all entries in table
  Iterator<LinkedList<Entry>> listPtr = getTable().iterator();
  while (listPtr.hasNext()) {
   Iterator<Entry> ptr = listPtr.next().iterator();
   while (ptr.hasNext()) {
    numEntries++;
    ptr.next();
   }
  }
  System.out.println("Number of entries: " + numEntries);
  System.out.println("Number of slots in table: " + numSlots);
  System.out.println("Average number of collisions (load factor): " + (double) numEntries / numSlots);
 }

 /**
  * a method to sort the entries alphabetically by their keys
  * 
  * @param table the hash table where the entries are located
  * @return a new array list with the entries sorted alphabetically
  */
 public ArrayList<Entry> sortAlphabetically(ArrayList<LinkedList<Entry>> table) {
  ArrayList<Entry> list = new ArrayList<Entry>(getSize());
  // copy entries into new list to be sorted
  Iterator<LinkedList<Entry>> tablePtr = table.iterator();
  while (tablePtr.hasNext()) {
   Iterator<Entry> entryPtr = tablePtr.next().iterator();
   while (entryPtr.hasNext()) {
    list.add(entryPtr.next());
   }
  }
  // sort list by alphabetical order
  list.sort(new AlphabeticComparator());
  return list;
 }

 /**
  * a method to write the keys and their frequencies to the output file
  * 
  * @param list the array list of entries
  * @param outputFile the output file to write to
  * @throws IOException thrown if issues with writing to output file
  */
 public void writeFrequencies(ArrayList<Entry> list, String outputFile) throws IOException {
  FileWriter writer = new FileWriter(outputFile);
  Iterator<Entry> listPtr = list.iterator();
  int ctr = 0;
  while (listPtr.hasNext()) {
   ctr++;
   Entry temp = listPtr.next();
   writer.write("(" + temp.getKey() + "  " + temp.getFreq() + ")   ");
   if (ctr % 10 == 0)
    writer.write("\n");
  }
  writer.close();
 }

}
