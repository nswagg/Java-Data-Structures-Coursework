import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Nick Waggoner
 * Course: Data Structures and Algorithms
 * Due: 4/22/20
 * 
 * Description:
 * 	This program creates a linked list of Elephants (name and weight). The program
 * 	uses the manually created list to print out the elephants outright, iterate through
 * 	the list adding up all weights of the elephants before printing it, and then finds the 
 * 	heaviest elephants, which it prints before removing them from the list.
 * 
 * Tags: linked list, file i/o, object class, iterator
 */

public class WaggonerNicholasAssignment9 {

	public static void main(String[] args) throws FileNotFoundException{
		ElephantLinkedList linkedList = new ElephantLinkedList();
		
		File file = new File("elephants.txt");
		Scanner eleScan = new Scanner(file);

		//fills the list with the elephants
		while(eleScan.hasNext()) {
			String name = eleScan.next();
			int weight = eleScan.nextInt();
			Elephant elephant = new Elephant(name, weight);
			linkedList.add(elephant);
		}
		eleScan.close();
		
		System.out.println("Step 1: Created and placed elephants in the linked list");
		System.out.println("Elephant\tWeight");
		System.out.printf("--------------------------");
		linkedList.printList();
		
		// print the total weight
		System.out.println("\n\nStep 2: Find the total weight for all elephants");
		System.out.printf("Total weight of all elephants >> %d lbs.", linkedList.getTotalWeight());
		
		//Remove and print elephants from heaviest to lightest
		System.out.print("\n\n\nStep 3: Find and remove elephants, heaviest to lightest\n");

		while(!(linkedList.isEmpty())) {
			Elephant heaviest = linkedList.findHeaviest();
			String name = heaviest.getName();
			int weight = heaviest.getWeight();
			
			System.out.printf("\nRemoving: %s weighing in at %d lbs", name, weight);
			linkedList.remove(heaviest);
		}
	}

}//main

class Elephant {
	
	private String name;
	private int weight;
	
	public Elephant(String name, int weight) {
		this.name = name;
		this.weight = weight;
	}
	
	public String getName() {
		return name;
	}
	
	public int getWeight() {
		return weight;
	}
	
	public void print() {
		System.out.printf("%s %s %d", name, "weighs", weight);
	}
	
}//Elephant

class ElephantLinkedList {
	
	private Node head;
	private Node tail;
	
	public ElephantLinkedList() {
		head = null;
		tail = null;
	}
	
	public boolean isEmpty() {
		//Returns true if the list is empty
		if(head == null) {
			return true;
		}
		return false;
		
	}//isEmpty
	
	public int getTotalWeight() {
		//Returns the total weight of every elephant in the list
		int totalWeight = 0;
		
		Node cumulative = head;
		while(cumulative != null) {
			totalWeight += cumulative.elephant.getWeight();
			cumulative = cumulative.next;
		}
		return totalWeight;
		
	}//getTotalWeight
	
	public void add(Elephant elephant) {
		//adds a new node with the elephant to the end of the linked list
		Node newNode = new Node(elephant);
		
		if(isEmpty()) {
			head = tail = newNode;
		}
		else {
			tail.next = newNode;
			tail = newNode;
		}
	}//add
	
	public void remove(Elephant elephant) {
		//Removes an elephant from the list
		Node previous = null;
		Node current = head;
		boolean found = false;
				
		while(current != null && !found) {
			if(current.elephant == elephant) {
				found = true;
			}
			else {
				previous = current;
				current = current.next;
			}
		}
		if(found) {
			if(previous == null) {
				head = current.next;
			}
			else {
				previous.next = current.next;
			}
		}
	}
	
	public Elephant findHeaviest() {
		////returns the elephant that weighs the most. If list is empty, return null
		if(!isEmpty()) {
			Node current = head;
			Node next = head.next;
			Elephant heaviest = current.elephant;
			int maxWeight = heaviest.getWeight();
			while(next != null) {
				if(next.elephant.getWeight() > maxWeight) {
					heaviest = next.elephant;
					maxWeight = heaviest.getWeight();
				}
				next = next.next;
			}
			return heaviest;
		}
		else {
			return null;
		}
		
	}//findHeaviest
	
	public void printList() {
		//Displays the name and weight of all elephants in the linked list
		Node current = head;
		
		while(current != null) {
			String name = current.elephant.getName();
			int weight = current.elephant.getWeight();
			
			System.out.printf("\n%s\t\t%d",name, weight);
			current = current.next;
		}
	}//printList
	
	private class Node {
		//represents a node of the linked list
		
		private Elephant elephant;
		private Node next;
		
		public Node(Elephant elephant) {
			this.elephant = elephant;
			next = null;
		}
		
	}//Node
	
}//ElephantLinkedList
