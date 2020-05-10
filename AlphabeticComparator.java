import java.util.Comparator;

public class AlphabeticComparator implements Comparator<Entry> {

 @Override
 public int compare(Entry e1, Entry e2) {
  if(e1.getKey().equals(e2.getKey()))
   return 0;
  return e1.getKey().compareToIgnoreCase(e2.getKey());
 }
}
