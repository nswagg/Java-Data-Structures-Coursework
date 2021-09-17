import java.util.PriorityQueue;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/** 
 *  Nicholas Waggoner
 *  Course: Data Structures and Algorithms
 *  
 *  Tags: linked list, queue, stack, priority queue
 */

public class WaggonerNicholasAssignment11 {

	public static void main(String[] args) {

		Queue<Integer> aQueue = new LinkedList<>();
		Stack<Integer> aStack = new Stack<>();
		Node head = null;
		
		// Example queue and stack to test your code on
		aQueue.offer(2);
		aQueue.offer(-40);
		aQueue.offer(-7);
		aQueue.offer(6);
		aQueue.offer(5);
		
		aStack.push(-53);
		aStack.push(1);
		aStack.push(4);
		aStack.push(-2);
		aStack.push(3);
		aStack.push(-63);
		
		//******* ADD SOLUTION HERE *******
		// Write all code in main - no need to create methods
		// Create your own linked list using the provided head and Node class
		
		PriorityQueue<Integer> orderedList = new PriorityQueue<>();
		
		while(!aQueue.isEmpty()) {
			int temp = aQueue.remove();
			if(temp > 0) {
				orderedList.offer(temp);
			}
		}
		
		while(!aStack.isEmpty()) {
			int temp = aStack.pop();
			if(temp > 0) {
				orderedList.offer(temp);
			}
		}
		
			Node tail = null;
			
			while(!orderedList.isEmpty()) {
				Node input = new Node(orderedList.remove());
				
				if(head == null) {
					head = tail = input;
				}
				else {
					tail.next = input;
					tail = input;
				}
			}
	
		// Print the list
		System.out.println("Reverse Sorted Linked List");
		Node current = head;
		while (current != null) {
			int data = current.data;
			current = current.next;
			System.out.print(data + "-->");
		}
		System.out.println("Null");

	} // main
	
	// Inner Node Class
	private static class Node {
		private int data;
		private Node next;
		
		public Node (int data) {
			this.data = data;
			next = null;
		}
	} // Node

} //WaggonerNicholasAssignment11

