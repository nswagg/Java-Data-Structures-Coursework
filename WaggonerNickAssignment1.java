import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Nick Waggoner
 * Course: Data Structures and Algorithms
 * 1/29/2020
 * Description:
 *	This program creates two arrays of random length between 1 and 10 and each element
 *  is filled with random numbers between 1 and 25.
 *	After sorting each array by element, display the arrays
 *	
 *	Write the sorted array values to a file.
 *
 *	After reopening the file, the program should be capable of reading values from the file, and
 *	assign each value to an array, sorting them and removing duplicates
 *
 * Tags: File I/O, Array
 */

public class WaggonerNickAssignment1 {

	public static void main(String[] args) throws IOException{
		
		File textFile = new File("assignment1.txt");
		
		//Task 1: Chunk of code creates the random numbers for the length of arrays
		int size0 = 1 + (int)(Math.random() * 10);
		int size1 = 1 + (int)(Math.random() * 10);
		
		int[] arr0 = new int[size0];
		int[] arr1 = new int[size1];
		int[] full = new int[size0 + size1];
		
		int count0 = 0;
		int count1 = 0;
		
		//This assigns random values between 1 and 25 to each element
		for(int i = 0; i < size0; i++){
			arr0[i] = (int)(Math.random() * 25)+1;
		}
		
		for(int j = 0; j < size1; j++){
			arr1[j] = (int)(Math.random() * 25)+1;
		}

		//This will sort the values in each array.
		Arrays.sort(arr0);
		Arrays.sort(arr1);
		
		System.out.print("\nArray 1>> ");
		for(int k = 0; k < arr0.length; k++){
			System.out.print(arr0[k] + " ");
		}
		
		System.out.print("\n\nArray 2>> ");
		for(int m = 0; m < arr1.length; m++){
			System.out.print(arr1[m] + " ");
		}
		
		PrintWriter fileWrite = new PrintWriter(textFile);
		
		//statement prints arrays to the file
		while(count0 < arr0.length && count1 < arr1.length)	{
			if(arr0[count0] < arr1[count1])	{
				fileWrite.println(arr0[count0]);
				System.out.println("Written>> " + arr0[count0]);
				count0++;
			}
			else {
				fileWrite.println(arr1[count1]);
				System.out.println("Written>> " + arr1[count1]);
				count1++;
			}
		}
		
		while(count0 < arr0.length)	{
			fileWrite.println(arr0[count0]);
			System.out.println("Written>> " + arr0[count0]);
			count0++;
		}
		
		while(count1 < arr1.length)	{
			fileWrite.println(arr1[count1]);
			System.out.println("Written>> " + arr1[count1]);
			count1++;
		}
		
		fileWrite.close();
		
		Scanner readFile = new Scanner(textFile);
		
		int index = 0;
		int counter = 0;
		
		while(readFile.hasNext()) {
			if(full[counter] > index) {
				full[counter] = readFile.nextInt();
				
				index = full[counter];
				counter++;				
			}
			else {
				full[counter] = 0;
			}
		}
		
		int[] lastArr = new int[counter];
		int call = 0;
		
		for(int num = 0; num < lastArr.length; num++) {
			if(full[num] != 0)
			{
				lastArr[call] = full[num];
				call++;
			}
		}
		
		for(int printing = 0; printing < lastArr.length; printing++) {
			System.out.println("Final>> " + lastArr[printing]);
		}
	}
} //WaggonerNickAssignment1
