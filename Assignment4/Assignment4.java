import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/** 
 *  Nicholas Waggoner
 *  Course: Data Structures and Algorithms
 *  Due 02/05/20
 *  
 *   This Program is designed to simulate a game of pinball. It takes the input from a file 
 *   of pinball target objects and creates a matrix with dimensions indicated by the first
 *   two numbers of the file. Then, after displaying the "playing field", it reads from a 
 *   second file, indicating locations where the ball is at. If the ball lands on a location
 *   where a target is, it increments the hits. It displays every target hit in a list and 
 *   then compares all of the target's hit numbers to sort them in the overridden compareTo
 *   method
 *   
 *   Tags: file i/o, array list, compare to, matrix, object class, abstraction
 */



public class WaggonerNicholasAssignment4 
{
	public static void main(String[] args) throws FileNotFoundException
	{
		//temp variables to initialize the matrix
		int locationR = 0;
		int locationC = 0;
		
		File machine = new File("PinballMachineTargets.txt");
		Scanner machineRead = new Scanner(machine);
		
		PinballMachine field = new PinballMachine(machineRead.nextInt(),machineRead.nextInt());
		
		//Sets up the targets in the matrix
		while(machineRead.hasNext())
		{
			//stores locations for later use
			locationR = machineRead.nextInt();
			locationC = machineRead.nextInt();
			
			//Creates new target object with attributes
			Target target = new Target(machineRead.next(), machineRead.nextInt(), machineRead.nextInt());
			
			//places object at location
			field.addTargetToPlayingField(locationR,locationC, target);
		}
		machineRead.close();
		
		field.displayPlayingField();
		play(field);
		printReport(field);

	}//main
	
	public static void play(PinballMachine pinballMachine) throws FileNotFoundException 	
	{
		int score = 0;
		File play = new File("play.txt");
		Scanner playRead = new Scanner(play);
		
        System.out.println("\n\n\n---------------------------------------");
        System.out.println("         Simulate Pinball Game");
        System.out.println("---------------------------------------");
        System.out.println("Target Hit\tID\tPoints\tScore");
        System.out.println("---------------------------------------");
        
        while(playRead.hasNextInt())
        {
        	Target obj = pinballMachine.getTarget(playRead.nextInt(), playRead.nextInt());
        	
        	if(obj != null)
        	{
        		obj.incrementHits();
        		score +=obj.getPoints();
        		System.out.printf("\n  %-10s\t%d\t%d\t%d", obj.getType(), obj.getId(), obj.getPoints(), score);
        	}
        }
        playRead.close();        
	}//play
	
	public static void printReport (PinballMachine pinballMachine)
	{
		ArrayList<TargetReport> reports = new ArrayList<>();
		
		for(int x = 0; x < pinballMachine.getRows(); x++)
		{
			for(int y = 0; y < pinballMachine.getColumns(); y++)
			{
				Target obj = pinballMachine.getTarget(x, y);
				if(obj != null)
				{
					reports.add(new TargetReport(x, y, obj.getType(), obj.getId(), obj.getPoints(), obj.getHits()));
				}
			}
		}
		
		//Compares and sorts the objects in the array
		reports.sort(TargetReport :: compareTo);
		
        System.out.println("\n\n***************************************************************");
        System.out.println("               PINBALL MACHINE TARGET HIT REPORT");
        System.out.println("                (From Most Hits to Least Hits)");
        System.out.println("***************************************************************");
        System.out.println("Row\tColumn\tType\t\tNumber\tPoints\tHits");
        System.out.println("---------------------------------------------------------------");
        
        for(TargetReport report : reports)
        {
        	System.out.printf(report.print());
        }
	}

}//Assignment4

class PinballMachine
{
	private int numberRows;
	private int numberColumns;
	private Target[][] playingField;
	
	public PinballMachine() {}
	
	public PinballMachine(int numberRows, int numberColumns)
	{
		this.numberRows = numberRows;
		this.numberColumns = numberColumns;
		playingField = new Target[numberRows][numberColumns];
	}
	
	//getters for Pinball Machine
	public int getRows()
	{
		return numberRows;
	}
	public int getColumns()
	{
		return numberColumns;
	}
	
	//Assigns object to the location with its attributes
	public void addTargetToPlayingField(int row, int column, Target target)
	{
		playingField[row][column] = target;
	}
	
	//Returns the object at the designated location
	public Target getTarget(int row, int column)
	{
		return playingField[row][column];
	}
	
	public void displayPlayingField()
	{
		System.out.println("\nSetting up targets in pinball machine...\n");
		int count = 1;
		//Prints the first line
		while(count <= numberColumns)
		{
			System.out.printf("\tColumn %d", count);
			count++;
		}
		count = 1;
		
		for(int i = 0; i < numberRows; i++)
		{
			System.out.printf("\nRow %d\t", count);
			for(int j = 0; j < numberColumns; j++)
			{
				
				if(playingField[i][j] != null)
				{
					System.out.printf("%-10s\t", playingField[i][j].getType());
				}
				else
				{
					System.out.printf("-------\t\t");
				}
			}
			count++;
		}
		
	}
}//PinballMachine

class Target
{
	private String type;
	private int id;
	private int points;
	private int hits;
	
	public Target() {}
	
	public Target(String type, int id, int points)
	{
		this.type = type;
		this.id = id;
		this.points = points;
	}
	
	//getters for target
	public String getType()
	{
		return type;
	}
	public int getId()
	{
		return id;
	}
	public int getPoints()
	{
		return points;
	}
	public int getHits()
	{
		return hits;
	}
	
	//Updates value in hits by one
	public void incrementHits()
	{
		this.hits++;
	}
}//Target

class TargetReport implements Comparable<TargetReport>
{
	private int rowNumber;
	private int columnNumber;
	private String type;
	private int id;
	private int points;
	private int hits;
	
	public TargetReport() {}
	
	public TargetReport(int rowNumber, int columnNumber, String type, int id, int points, int hits)
	{
		this.rowNumber = rowNumber;
		this.columnNumber = columnNumber;
		this.type = type;
		this.id = id;
		this.points = points;
		this.hits = hits;
	}
	
	public String print()
	{
		return String.format("\n%d\t%d\t%-10s\t%-1d\t%-4d\t%-4d", rowNumber, columnNumber, type, id, points, hits);
	}

	@Override
	public int compareTo(TargetReport otherReport)
	{
		return Integer.compare(otherReport.hits - this.hits, 0);
	}
}//TargetReport