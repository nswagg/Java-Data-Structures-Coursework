import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Scanner;

/**
 * Nick Waggoner
 * Course: Data Structures and Algorithms
 * Due: 4/15/20
 * 
 * Description:
 * 		This program will read from several text files and create a List relative to its information.
 * 		The list of slots will use both numbers on the text line to initialize a Slot object to be
 * 		stored in the ArrayList or Queue. Those lists are given iterators which are sent to a 2D array,
 * 		iterating through them and putting every character in its corresponding location noted by the
 * 		slot object. The program then prints out every value in the array, with blank spaces being 
 * 		represented by an underscore character.
 * 
 * Tags: array list, queue, file i/o, iterator, linked list, array, object class
 */

public class WaggonerNicholasAssignment8 {

	public static void main(String[] args) throws FileNotFoundException {
		
		File lLetters = new File("listLetters.txt");
		File lSlots = new File("listSlots.txt");
		File qLetters = new File("queueLetters.txt");
		File qSlots = new File("queueSlots.txt");
		
		Scanner listLetters = new Scanner(lLetters);
		Scanner listSlots = new Scanner(lSlots);
		Scanner queueLetters = new Scanner(qLetters);
		Scanner queueSlots = new Scanner(qSlots);
		
		ArrayList<Character> ALetters = new ArrayList<>();
		ArrayList<Slot> ASlots = new ArrayList<>();
		Crossword aCrossword = new Crossword(listSlots.nextInt(), listSlots.nextInt());
		
		Queue<Character> QLetters = new LinkedList<>();
		Queue<Slot> QSlots = new LinkedList<>();
		Crossword qCrossword = new Crossword(queueSlots.nextInt(), queueSlots.nextInt());
		
		//Filling in the ArrayLists and Queues
		while(listSlots.hasNextLine()) {
			int arow = listSlots.nextInt();
			int acolumn = listSlots.nextInt();
			
			Slot aSlot = new Slot(arow,acolumn);
			String aletters = listLetters.next();
			char acharacter = aletters.charAt(0);

			ALetters.add(acharacter);
			ASlots.add(aSlot);
		}
		
		while(queueSlots.hasNextLine()) {
			int qrow = queueSlots.nextInt();
			int qcolumn = queueSlots.nextInt();
			Slot qSlot = new Slot(qrow,qcolumn);
			
			String qletters = queueLetters.next();
			char qcharacter = qletters.charAt(0);
			
			QLetters.add(qcharacter);
			QSlots.add(qSlot);
		}
		
		//Iterators
		Iterator<Character> AChar = ALetters.iterator();
		Iterator<Slot> ASlot = ASlots.iterator();
		Iterator<Character> QChar = QLetters.iterator();
		Iterator<Slot> QSlot = QSlots.iterator();
		
		//Enter Letters into the crossword
		aCrossword.enterLetters(AChar, ASlot);
		qCrossword.enterLetters(QChar, QSlot);
		
		//Output
		System.out.println("-------------");
		System.out.println("Crossword #1");
		System.out.println("-------------\n");
		aCrossword.printCrossword();

		System.out.println("\n\n-------------");
		System.out.println("Crossword #2");
		System.out.println("-------------\n");
		qCrossword.printCrossword();
		
	}//main
}//WaggonerNicholasAssignment8

class Slot {
	private int row;
	private int column;
	
	public Slot(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	//Getters
	public int getRow() {
		return row;
	}
	public int getColumn() {
		return column;
	}
}//Sort

class Crossword {
	private char[][] array;
	private int numRows;
	private int numColumns;
	private char EMPTY_CHARACTER = '_';
	
	public Crossword(int numRows, int numColumns) {
		this.numRows = numRows;
		this.numColumns = numColumns;
		array = new char[numRows][numColumns];
		
		//Fills every slot with the EMPTY_CHARACTER
		for(int i = 0; i < numRows; i++) {
			for(int j = 0; j < numColumns; j++) {
				array[i][j] = EMPTY_CHARACTER;
			}
		}
		
	}//Crossword Constructor
	
	public void enterLetters (Iterator<Character> letterIterator, Iterator<Slot> slotIterator) {
		//Gets letter from the letter Iterator
		// Get a slot object from the slot iterator to collect the row and column
		//Place the letter in that specific row and column in the array
		//Repeat these steps until the iterators have reached end of the Array lists
		while(slotIterator.hasNext()) {
			Character character = letterIterator.next();
			Slot slot = slotIterator.next();

			array[slot.getRow()][slot.getColumn()] = character;
		}
	}//enterLetters
	
	public void printCrossword() {
		//Print every slot in the crossword
		//Starting at row 0 and column 0. print every slot in each row
		//Each row of letters should be printed on its own line
		for(int k = 0; k < numRows; k++) {
			for(int l = 0; l < numColumns; l++) {
				System.out.print(array[k][l] + " ");
			}
			System.out.println();
		}
		
	}//printCrossword
}//Crossword