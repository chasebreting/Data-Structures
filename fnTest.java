import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

public class fnTest {

	/**
	 * a method to benchmark the mergeSort program
	 */
	public static void runtime() {
		long startTimeTotal = System.nanoTime();
		// this factor controls the size of the arrays being benchmarked
		int multFactor = 10000;
		// initialize the arrays
		int[] arr1 = new int[25 * multFactor];
		int[] arr2 = new int[50 * multFactor];
		int[] arr3 = new int[100 * multFactor];
		int[] arr4 = new int[125 * multFactor];
		long[] times = new long[12];
		Random randGen = new Random();
		long startTime;
		long estimatedTime;
		// loop to test all four arrays three times
		for (int i = 0; i < 12; i++) {
			if (i < 3) {
				// fill array with random ints
				for (int j = 0; j < arr1.length; j++)
					arr1[j] = randGen.nextInt();

				// sort array
				startTime = System.nanoTime();
				Sorter.mergeSort(arr1);
				estimatedTime = System.nanoTime() - startTime;

				// store time
				times[i] = estimatedTime;
			}

			if (i >= 3 && i < 6) {
				// fill array with random ints
				for (int j = 0; j < arr2.length; j++)
					arr2[j] = randGen.nextInt();

				// sort array
				startTime = System.nanoTime();
				Sorter.mergeSort(arr2);
				estimatedTime = System.nanoTime() - startTime;

				// store time
				times[i] = estimatedTime;
			}
			if (i >= 6 && i < 9) {
				// fill array with random ints
				for (int j = 0; j < arr3.length; j++)
					arr3[j] = randGen.nextInt();

				// sort array
				startTime = System.nanoTime();
				Sorter.mergeSort(arr3);
				estimatedTime = System.nanoTime() - startTime;

				// store time
				times[i] = estimatedTime;
			}
			if (i >= 9) {
				// fill array with random ints
				for (int j = 0; j < arr4.length; j++)
					arr4[j] = randGen.nextInt();

				// sort array
				startTime = System.nanoTime();
				Sorter.mergeSort(arr4);
				estimatedTime = System.nanoTime() - startTime;

				// store time
				times[i] = estimatedTime;
			}
		}

		// calculate the medians
		long med1 = getMedian(times, 0);
		long med2 = getMedian(times, 3);
		long med3 = getMedian(times, 6);
		long med4 = getMedian(times, 9);
		// print the results
		System.out.print("\nResults of Runtime of Merge Sort:");
		System.out.print("\nMedian Running Time of " + arr1.length + " size array:    " + med1 + "  ns");
		System.out.print("\nMedian Running Time of " + arr2.length + " size array:    " + med2 + "  ns");
		System.out.print("\nMedian Running Time of " + arr3.length + " size array:   " + med3 + " ns");
		System.out.print("\nMedian Running Time of " + arr4.length + " size array:   " + med4 + " ns");

		long estimatedTimeTotal = System.nanoTime() - startTimeTotal;
		System.out.print("\n\nTotal Running Time of Benchmark: " + estimatedTimeTotal + " ns\n");
	}

	/**
	 * a method to get the median value of three elements
	 * 
	 * @param arr        the array containing the elements
	 * @param lowerBound the index of the first of three consecutive elements
	 * @return the median value
	 */
	public static long getMedian(long[] arr, int lowerBound) {
		long temp1 = arr[lowerBound];
		long temp2 = arr[lowerBound + 1];
		long temp3 = arr[lowerBound + 2];
		if (temp1 < temp3 && temp1 > temp2 || temp1 > temp3 && temp1 < temp2)
			return temp1;
		if (temp2 < temp3 && temp2 > temp1 || temp2 > temp3 && temp2 < temp1)
			return temp2;
		else
			return temp3;
	}

	/**
	 * a method to test the functionality of mergeSort()
	 * 
	 * @param inputFile
	 * @param outputFile
	 * @throws Exception
	 */
	public static void fnTester(String inputFile, String outputFile) throws Exception {
		FileReader reader = new FileReader(inputFile);
		StringBuilder builder = new StringBuilder();
		LinkedList<String> list = new LinkedList<String>();
		boolean reachedEnd = false;
		// build strings out of the numbers and add them to the list
		while (!reachedEnd) {
			int tempInt = reader.read();
			char temp = (char) tempInt;
			if (Character.isDigit(temp) || temp == '-')
				builder.append(temp);
			if (temp == '\n') {
				if (builder.toString().length() != 0)
					list.add(builder.toString());
				builder = new StringBuilder();
			}
			if (tempInt == -1) {
				if (builder.toString().length() != 0)
					list.add(builder.toString());
				reachedEnd = true;
			}
		}
		reader.close();
		// make an int array to sort
		int[] arr = new int[list.size()];
		// fill the array with the numbers from the input file
		Iterator<String> ref = list.iterator();
		int i = 0;
		while (ref.hasNext()) {
			String temp = ref.next();
			arr[i++] = (int) Integer.parseInt(temp);
		}
		System.out.println("Unsorted Array");
		Sorter.printArrayContents(arr);
		// sort the array of integers
		long startTime = System.nanoTime();
		Sorter.mergeSort(arr);
		long estimatedTime = System.nanoTime() - startTime;
		System.out.println("Sorted Array");
		Sorter.printArrayContents(arr);
		
		// write the sorted ints to the output file
		@SuppressWarnings("resource")
		FileWriter writer = new FileWriter(outputFile);
		for (i = 0; i < arr.length; i++)
			writer.write(arr[i] + "\n");
		// close resource (the warning wouldn't go away so i had to suppress it)
		writer.close();
		// print results
		System.out.println("\nNumber of integers:  " + arr.length);
		System.out.println("Time to sort array:  " + estimatedTime + " ns");
	}

	/**
	 * a method to fill a text file with random ints
	 * 
	 * @param file    the input file path
	 * @param numInts the number of random ints to insert
	 * @throws IOException if there is an issue writing to file
	 */
	public static void fillRandInts(String file, int numInts) throws IOException {
		FileWriter writer = new FileWriter(file);
		Random randGen = new Random();
		for (int i = 0; i < 20; i++)
			writer.write(randGen.nextInt() + "\n");
		writer.close();
	}

	/**
	 * the main method which takes an input file of integers, sorts them, and sends
	 * them to the output file
	 * 
	 * @param args the input and output files
	 */
	public static void main(String[] args) throws Exception {
		if (args.length == 2)
			fnTester(args[0], args[1]);
		else
			System.out.println("Incorrect input parameters. Please enter input and output file paths.");
	}
}
