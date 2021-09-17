import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/** 
 *  Nicholas Waggoner
 *  Course: Data Structures and Algorithms
 *  02/05/20
 *  Description:
 *  	Takes in a txt file of trains and creates an array of Train objects
 *  	Outputs the train type, top speed, name of train, and its benefit.
 *  	Utilizes object-class inheritance through use of train subclasses.
 *  
 *  Tags: file I/O, array, object class, inheritence, override
 */

public class WaggonerNicholasAssignment2 {

	public static void main(String[] args) throws FileNotFoundException {
		//temp variables
		String type = "";
		double speed = 0;
		String name = "";
		int counter = 0;
		
		File file = new File("Trains.txt");
		Scanner readFile = new Scanner(file);
		
		//Creates array with length of first number in file
		Train[] station = new Train[readFile.nextInt()];
		readFile.nextLine();
		
		//Reading the file to initialize objects
		for(int i = 0; i < station.length; i++)	{
			type = readFile.next();
			if(readFile.hasNextInt()) {
				speed = readFile.nextInt();
			}
			else {
				speed = readFile.nextDouble();
			}

			name = readFile.nextLine();
			name = name.trim();
			
			//Instantiating train objects
			if(type.equals("highspeed")) {
				Highspeed zoom = new Highspeed(name, speed);
				station[counter] = zoom;
			}
			else if(type.equals("monorail")) {
				Monorail uno = new Monorail(name, speed);
				station[counter] = uno;
			}
			else if(type.equals("lightrail")) {
				Lightrail light = new Lightrail(name, speed);
				station[counter] = light;
			}
			else if(type.equals("cog")) {
				Cog gear = new Cog(name, speed);
				station[counter] = gear;
			}
			counter++;
		}
		
		readFile.close();
		
		//Format Header
		System.out.println("------------------------------------------------------------------------------------------------------");
		System.out.println("Type		  Name				Top Speed	 Benefit");
		System.out.println("------------------------------------------------------------------------------------------------------");
		
		for(int j = 0; j < station.length; j++) {
			System.out.printf("%-10s \t %-24s \t %-6.1f \t %-40s\n", station[j].getType(), station[j].getName(), station[j].getSpeed(), station[j].benefit() );
		}

	}
}//main
	
	class Train {
		//Instance variables
		private String type;
		private double topSpeed;
		private String name;
		
		//Default Constructor for Train **NECESSARY
		public Train() {}
		
		public Train(String type, double topSpeed, String name)	{
			this.type = type;
			this.topSpeed = topSpeed;
			this.name = name;
		}
		
		//Getters - can access private materials
		public String getType()	{
			return type;
		}
		public double getSpeed() {
			return topSpeed;
		}
		public String getName() {
			return name;
		}
		
		//Creates an Open Method to be overwritten
		public String benefit()	{
			return "00";
		}
		
	}//Train
	
	class Highspeed extends Train {
		public Highspeed() {}
		
		public Highspeed(String name, double topSpeed) {
			super("Highspeed", topSpeed, name);
		}
		
		@Override
		public String benefit()	{
			return "Travels at speeds between 125 and 267 mph";
		}
		
	}//Highspeed
	
	class Monorail extends Train {		
		public Monorail() {}
		
		public Monorail(String name, double topSpeed) {
			super("Monorail", topSpeed, name);
		}
		
		@Override
		public String benefit()	{
			return "Minimal footprint and quieter";
		}
	}//Monorail
	
	class Lightrail extends Train
	{
		public Lightrail() {}
		
		public Lightrail(String name, double topSpeed){
			super("Lightrail", topSpeed, name);
		}
		
		@Override
		public String benefit()	{
			return "Tighter Turning radius";
		}
	}//Lightrail
	
	class Cog extends Train {
		public Cog() {}
		
		public Cog(String name, double topSpeed) {
			super("Cog", topSpeed, name);
		}
		
		@Override
		public String benefit()	{
			return "Can climb grades up to 48%";
		}
	}//Cog
