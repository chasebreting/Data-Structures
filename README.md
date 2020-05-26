# Data-Structures
Some Projects from EECS 233 Intro to Data Structures Spring 2020
Chase Breting, CWRU

This repository consists of java files from three of my projects from Data Structures. 
The first project deals with Huffman Encoding.Huffman Encoding is useful for generating efficient coded values for data based on their 
frequencies of appearance. The more times a datum appears, the smaller its encoded value will be. This will save storage space 
by reducing the total number of bits. It uses the Java files HuffmanNode.java and HuffmanCompressor.java. 
The purpose of the program is to take an input text file, read the characters one-by-one, count their frequencies, and generate 
their encoded values using Huffman Encoding. Then the original text file is reread and the coded values of the characters
are written to the output file.

The second project involves coding a hash table and using it to store frequencies of words of a text file. This is applicable for search
engines by checking the words in a query with documents and their frequencies of those words. The higher the frequency of words in the
document for which you are searching, the more likely it is for the document to be most relevant to you.
The java classes used in this project are WordCounter.java, HashTable.java, Entry.java, and AlphabeticComparator.java.
There are no java related hash-related classes used in this project. The hash function is based upon the hashCode() values of the strings 
of words read. When collisions occur, the hash table uses separate chaining. The table checks automatically if it needs to be resized 
by checking the load factor after each insert of a new key.
This project is run with an input file of text and an output file to which the word frequencies will be written. The words of the input
file are read in and compared with the keys of the hash table. If the key is present, then its frequency is simply incremented. If not
found, a new entry is inserted into the table with its key the same as the word and its location determined by its hashCode(). After
insertion, the load factor is calculated and the table is resized if needed. After the entire text file is read, the words and their
frequencies are written to the output text file.

The third project is about sorting, specifically writing a merge sort method. Sorting is a fundamental operation for computers and an
extremely important concept for computer scientists to understand. Merge sort is a divide and conquer method. It works by continually 
splitting an array until there is a group of subarrays of one element. Then it merges the subarrays together into larger, sorted
subarrays until the entire array is sorted.
The class files for this project are Sorter.java and fnTest.java.
My implementation works iteratively rather than recursively. This saves stack memory and avoids back copying of arrays. 
My algorithm first creates a new array to sort to and from after each pass (memory is O(n) where n is number of integers).
It then iteratively sorts groups of integers based on a "size" variable which is 2 raised to a "power" incremented after each pass.
My method finishes when the size of the subarray to sort is larger than the original array. If the final sorted contents in the 
array are in the temporary array, then the original array simply is updated to reference it.
I test my project two different ways: functionality and runtime (both included within the fnTest class). To test functionality,
a list of integers in an input text file is read and inserted into an int array, my sort method is called on the array, and
the sorted contents are outputted to a text file line-by-line. The runtime test benchmarks my sorting algorithm with sets 
of random integers of four sizes (250000, 500000, 1000000, and 1250000) three separate times to calculate the median runtime
of my implementation.

