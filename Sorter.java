
/**
 * @author Chase Breting
 */
public class Sorter {

 /**
  * A method to iteratively sort an integer array using the merge sort algorithm
  * 
  * @param arr  the integer array to be sorted
  */
 public static void mergeSort(int[] arr) {
  // create a new temporary array
  int[] temp = new int[arr.length];
  // an int to control the size of the subarrays
  int power = 0;
  // subarray size
  int size = (int) Math.pow(2.0, power);
  // int to flip which array is read/wrote to
  int swap = 1;

  // only sort array if size is greater than 1
  if (arr.length > 2) {
   // while the size of the subarrays is less than the size of the original array
   while (size < arr.length) {
    // reset position
    int position = 0;
    // while there are more subarrays in the array
    while (position < arr.length) {
     int middle = Math.min(position + size - 1, arr.length - 1);
     int end = Math.min(position + 2 * size - 1, arr.length - 1);
     if (swap % 2 == 1) {
      merge(arr, temp, position, middle, middle + 1, end);
     } else if (swap % 2 == 0) {
      merge(temp, arr, position, middle, middle + 1, end);
     }
     // merge(arr, position, middle, end);
     position += size * 2;
    }
    swap++;
    power++;
    size = (int) Math.pow(2.0, power);
   }
  }
  
  // need to write temp contents to arr if temp is even
  if (swap % 2 == 0) {
   for(int i = 0; i < temp.length; i++)
     arr[i] = temp[i];
  }
 }

 /**
  * a helper method to merge two parts of an array
  * 
  * @param inputArr  the array from which the data is read
  * @param outputArr  the array to output the merged data
  * @param leftStart  the index where the left interval begins
  * @param leftEnd  the index where the left interval ends
  * @param rightStart  the interval where the right interval begins
  * @param rightEnd  the interval where the right interval ends
  */
 public static void merge(int[] inputArr, int[] outputArr, int leftStart, int leftEnd, int rightStart,
   int rightEnd) {
  int i = leftStart; // index into left subarray
  int j = rightStart; // index into right subarray
  int k = leftStart; // index into output array
  // while elements still need to be compared
  while (i <= leftEnd && j <= rightEnd) {
   // compare elements
   if (inputArr[i] < inputArr[j]) {
    outputArr[k] = inputArr[i];
    k++;
    i++;
   } else {
    outputArr[k] = inputArr[j];
    k++;
    j++;
   }
  }
  while (j <= rightEnd) {
   outputArr[k] = inputArr[j];
   k++;
   j++;
  }
  // if right array out of elements to compare, copy elements from left array to
  // output array
  while (i <= leftEnd) {
   outputArr[k] = inputArr[i];
   k++;
   i++;
  }
 }

 /**
  * a method which prints the integers contained in an array
  * 
  * @param array  the array to have its contents printed
  */
 public static void printArrayContents(int[] array) {
  for (int i = 0; i < array.length; i++) {
   System.out.print(array[i] + " ");
  }
  System.out.print("\n");
 }

}
