import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Nick Waggoner
 * Course: Data Structures and Algorithms
 * 
 * Description:This program is designed to practice queues and priority queues, creating a simulation of an escape room.
 * 		The players are moved from the lobby into the waiting room queue before playing. After playing, players are sorted
 * 		into a results queue by their overall score and prints them out at the end. Also implements hash function to 
 * 		"randomly" generate the player's scores. Priority Queue class is homebrew and not part of Java Collections.
 * 
 * Tags: priority queue, queue, file i/o, linked list, selection sort
 */

public class WaggonerNicholasAssignment7 {

	public static void main(String[] args) throws FileNotFoundException {

		Player[] seats = new Player[25];

		File file = new File("players.txt");
		Scanner playerScan = new Scanner(file);
		
		EscapeGame escapeGame = new EscapeGame();
		EscapeGameController gameController = new EscapeGameController();
		
		while(playerScan.hasNextLine()) {
			String name = playerScan.next();
			int ranking = playerScan.nextInt();
			int seat = playerScan.nextInt();
			
			Player player = new Player(name, ranking, seat);
			seats[seat] = player; //adds the player to their respective seat assuming that seat 0 exists
		}
		playerScan.close();
		
		gameController.movePlayersIntoEscapeGame(seats, escapeGame);
		gameController.simulateEscapeGame(escapeGame);
		gameController.displayResults(escapeGame);
		
		
	}//main
}//WaggonerNicholasAssignment7

class Player implements Comparable<Player> {
	
	private String name;
	private int ranking;
	private int seat;
	private int score;
	
	public Player(String name, int ranking, int seat) {
		this.name = name;
		this.ranking = ranking;
		this.seat = seat;
		this.score = 0;
	}
	
	//public getters for player object
	public String getName() {
		return name;
	}
	public int getRank() {
		return ranking;
	}
	public int getSeat() {
		return seat;
	}
	public int getScore() {
		return score;
	}
	
	//setter for the score
	public void setScore(int score) {
		this.score += score;
	}
	
	//Custom compareTo method to sort the players in Priority Queue
	@Override
	public int compareTo(Player otherPlayer) {
		return (this.score - otherPlayer.getScore());
	}
}//Player

class EscapeRoom {
	
	//Returns a hash of the key. Key can be any length
	//Returns an integer >= 0
	private int hash(String key) {
		
		int hash = 0;
		for(int i = 0; i < key.length(); i++) {
			hash += key.charAt(i);
			hash += (hash << 10);
			hash ^= (hash >> 6);
		}
		hash += (hash << 3);
		hash ^= (hash >> 11);
		hash += (hash << 15);
		
		return Math.abs(hash);
	}//hash
	
	public int tryToEscape(String playerName, int playerRanking) {
		String key = (playerName + "" + playerRanking);
		int score = hash(key) % (101);
		
		return score;
	}
}//EscapeRoom

class EscapeGame{
	
	private Queue<Player> waitingQueue;
	private PriorityQueue resultsQueue;
	EscapeRoom escapeRoom;
	
	public EscapeGame() {
		waitingQueue = new LinkedList<>();
		resultsQueue = new PriorityQueue();
		escapeRoom = new EscapeRoom();
	}
	
	//deals with Waiting Queue
	public boolean isWaitingQueueEmpty() {
		//returns true when the queue is empty
		return waitingQueue.isEmpty();
	}
	
	public void addPlayerToWaitingQueue(Player player) {
		//adds (offers) player to waiting queue
		waitingQueue.offer(player);
		//waitingQueue.add(player);
	}
	
	public Player removePlayerFromWaitingQueue() {
		//removes player from waiting queue		
		return (Player)waitingQueue.poll();
	}
	
	//Deals with Results Queue
	public boolean isResultsQueueEmpty() {
		return resultsQueue.isEmpty();
	}
	
	public void addPlayerToResultsQueue(Player player) {
		resultsQueue.offer(player);
	}
	
	public Player removePlayerFromResultsQueue() {
		return resultsQueue.remove();
	}
	
	public Player peekResultsQueue() {
		return resultsQueue.peek();
	}
	
	public int tryToEscape(String playerName, int playerRanking) {
		int score = escapeRoom.tryToEscape(playerName, playerRanking);
		return score;
	}
	
}//EscapeGame

class EscapeGameController {
	
	public void movePlayersIntoEscapeGame(Player[] seats, EscapeGame escapeGame) {
		//move each player in seats array into the escape game's waiting queue
		//print a message with player name and seat number - see output
		for(int i = 0; i < seats.length; i++) {
			Player player = seats[i];
		
			if(player == null) {}
			else {
				String name = player.getName();
				int seat = player.getSeat();
		
				System.out.printf("\nMoving to Escape game: %s from lobby seat %d", name, seat);
				escapeGame.addPlayerToWaitingQueue(player);
			}
		}
	}//movePlayersIntoEscapeRoom
	
	public void simulateEscapeGame(EscapeGame escapeGame) {
		//for each player in waiting queue, removes player from waiting queue
		//have player enter escape room. Call tryToEscape to give them a score
		//add player to results queue, queue must be implemented
		//prints name, score, and current leader
		
		//Header
		System.out.println("\n\nStarting Escape Game...\n");
		System.out.println("-------------------------------");
		System.out.println("Player\tScore\tCurrent\tLeader");
		System.out.printf("-------------------------------");
		
		while(!(escapeGame.isWaitingQueueEmpty())) {
			Player player = escapeGame.removePlayerFromWaitingQueue();
			String name = player.getName();
			int ranking = player.getRank();
			
			int score = escapeGame.tryToEscape(name, ranking);
			player.setScore(score);
			escapeGame.addPlayerToResultsQueue(player);
			
			Player leader =  escapeGame.peekResultsQueue();
			String leaderName = leader.getName();
			System.out.printf("\n%s\t%d\t%s", name, player.getScore(), leaderName);
		}
	}//simulateEscapeRoom
	
	public void displayResults(EscapeGame escapeGame) {
		//Displays final standings; removing each player from the results queue
		//Display those player's names and scores

		//Header
		System.out.println("\n\nEscape Room Results");
		System.out.println("-------------------------------");
		System.out.println("Player\tScore");
		System.out.printf("-------------------------------");
		
		while(!(escapeGame.isResultsQueueEmpty())) {
			Player player = escapeGame.removePlayerFromResultsQueue();
			String name = player.getName();
			int score = player.getScore();
			System.out.printf("\n%s\t%d", name, score);
		}
	}//displayResults
	
}//EscapeGameController

class PriorityQueue {
	//Priority queue of players, ordered by score
	//Removing a player from the priority queue returns the player with the highest score
	
	private Player[] list;
	private int numPlayers; //keeps track of how many players are in the array
	
	
	public PriorityQueue() {
		list = new Player[30];
		numPlayers = 0;
	}
	
	public boolean isEmpty() {
		//indicates if the array is empty - no players in the array
		boolean check = true;
		
		if(numPlayers != 0) {
			check = false;
		}
		
		return check;
	}
	
	public Player peek() {
		//returns player with highest score - does not remove
		if(!(isEmpty())) {
			return list[numPlayers - 1];
		}
		return null;
	}
	
	public boolean offer(Player player) {
		if(numPlayers == list.length) {
			return false;
		}
		else {
			list[numPlayers] = player;
			numPlayers++;
			selectionSort(list, numPlayers);
			return true;
		}
	}
	
	public Player remove() {
		//removes and returns player with the highest score
		Player hiScore = list[numPlayers -1];
		list[numPlayers - 1] = null;
		numPlayers--;
		
		return hiScore;
	}
	
	private void selectionSort(Player[] list, int numPlayers) {
		//sorts the array of players using selection sort method
		//utilizes compareTo method
		for(int i = 0; i < numPlayers-1; i++) {
			Player biggest = list[i];
			int biggestIndex = i;
			
			for(int j = i+1; j < numPlayers; j++) {
				int check = biggest.compareTo(list[j]);
			
				if(check > 0) {
					biggest = list[j];
					biggestIndex = j;
				}
			}
			
			if(biggestIndex != i) {
				list[biggestIndex] = list[i];
				list[i] = biggest;
			}
		}
	}//SelectionSort	
}//PriorityQueue
