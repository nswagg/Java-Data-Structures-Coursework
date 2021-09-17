import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 * Nick Waggoner
 * Course: Data Structures and Algorithms
 * Due 4/29/2020 
 * 
 * Description: 
 * 	This program creates a binary tree of Parrot objects and implements a level-order
 * 	traversal of the tree so the parrots will "sing". Then, the program visits every 
 * 	leaf node, traversing in order to print out the parrot's names. 
 * 
 * Tags: file i/o, object class, binary tree, linked list, doubly linked list
 */

public class WaggonerNickAssignment10 {

	public static void main(String[] args) throws FileNotFoundException {
		File bird = new File("parrots.txt");
		Scanner pTest = new Scanner(bird);
		
		BinaryTree oak = new BinaryTree();
		
		while(pTest.hasNext()) {
			int id = pTest.nextInt();
			String name = pTest.next();
			String songWord = pTest.next();
			
			Parrot parrot = new Parrot(id, name, songWord);
			oak.insert(parrot);
		}
		pTest.close();
		
		System.out.println("Parrot's Song");
		System.out.println("-------------------------------");
		oak.levelOrder();
		
		System.out.println("\n\n\nLeaf Node Parrots");
		System.out.print("-------------------------------");
		oak.visitLeaves();
	}
}//WaggonerNickAssignment10

class Parrot { 
	private int id;
	private String name;
	private String songWord;
	
	public Parrot(int id, String name, String songWord) {
		this.id = id;
		this.name = name;
		this.songWord = songWord;
	}
	
	public String getName() {
		return name;
	}
	
	public String sing() {
		return songWord;
	}
	
	public int compareTo(Parrot otherParrot) {
		//helps sorts parrots as they are being added to the tree
		return id-otherParrot.id;
	}
}//Parrot class

class BinaryTree {
	//Each node contains a parrot
	private TreeNode root;
	
	public BinaryTree() {
		root = null;
	}
	
	public boolean insert(Parrot parrotToAdd) {
		//adds a parrot to the tree
		if(root == null) {
			//takes care of first case
			root = new TreeNode(parrotToAdd);
		}
		else {
			TreeNode parent = null;
			TreeNode insert = root;
			
			//searching for location to insert
			while(insert != null) {
				if(parrotToAdd.compareTo(insert.parrot) < 0) {
					parent = insert;
					insert = insert.left;
				}
				else if(parrotToAdd.compareTo(insert.parrot) > 0){
					parent = insert;
					insert = insert.right;
				}
				else {
					//case for duplicates - exits the method and does not add the parrot
					return false;
				}
			}
			
			if(parrotToAdd.compareTo(parent.parrot) < 0) {
				parent.left = new TreeNode(parrotToAdd);
			}
			else {
				parent.right = new TreeNode(parrotToAdd);
			}
		}
		
		return true;
	}//insert
	
	public void levelOrder() {
		//Traverses the tree in level order, printing each parrot as it is visited
		//Needs a queue to keep track
		
		if(root != null) {
			Queue<TreeNode> pQueue = new LinkedList();
			pQueue.add(root);
			
			while(!pQueue.isEmpty()) {
				TreeNode temp = pQueue.remove();

				System.out.printf("%s ", temp.parrot.sing());
				
				if(temp.left != null) {
					pQueue.add(temp.left);
				}
				if(temp.right != null) {
					pQueue.add(temp.right);
				}
			}
		}
	}//levelOrder
	
	public void visitLeaves() {
		//Visits leaves in L->N->R order
		//calls recursive method, starting at the root node
		visitLeaves(root);
	}
	
	private void visitLeaves(TreeNode aNode) {
		//prints the names of the parrots in the sub-tree that starts at the node
		//Note: recursive which finds the base case
		if(aNode != null) {
			visitLeaves(aNode.left);
			System.out.printf("\n%s",aNode.parrot.getName());
			visitLeaves(aNode.right);
		}
	}//visitLeaves
	
	class TreeNode {
		private Parrot parrot;
		private TreeNode left;
		private TreeNode right;
		
		public TreeNode(Parrot parrot) {
			this.parrot = parrot;
			left = null;
			right = null;
		}
	}//TreeNode
}//Binary Tree
