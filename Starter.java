import java.io.*;
import java.util.*;
import javax.swing.*;
import java.lang.*;

public class Starter{
	

	public static void main(String args[]) throws Exception{

		File roads = new File("roads.txt");
		File attracts = new File("attractions.txt");
		
		BufferedReader rds = new BufferedReader(new FileReader(roads));
		BufferedReader atr = new BufferedReader(new FileReader(attracts));

		ArrayList<String> roadStr = new ArrayList<String>();
		ArrayList<String> attrStr = new ArrayList<String>();

		String rd;
		String at;
				
		while ((rd = rds.readLine()) != null){ //adds text to array
		 	roadStr.add(rd);
		};
	
		while ((at = atr.readLine()) != null){ //adds text to array
		 	attrStr.add(at);
		};


		String[] roadsArray = roadStr.toArray(new String[roadStr.size()]);
		String[] attraArray = attrStr.toArray(new String[attrStr.size()]);

		String [][] attractData = new String[attraArray.length][2]; //2d array of attraction site and city

		for(int i = 0; i < attraArray.length; i++){
			String[] att = attraArray[i].split(",");
			String[] words = att[0].split("\\s");
			String capitalizeWord = "";
			for(String w:words){
				String first = w.substring(0,1);
				String afterfirst = w.substring(1);
				capitalizeWord += first.toUpperCase() + afterfirst;
			}
			attractData[i][0] = capitalizeWord;
			attractData[i][1] = att[1];
		}



		//Program starts to execute the rest of the program

		Scanner input = new Scanner(System.in);

		System.out.println("*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
		System.out.println("Thank You for using the America Tour Guide Map.");
		System.out.println("Please input your Starting City");

		String startCity = input.nextLine();

		System.out.println("Please input your Ending City");

		String endCity = input.nextLine();

		System.out.println("Would you like see any attractions along the way? Y/N");

		String response = input.nextLine();

		RoadToData roader = new RoadToData(roadsArray);
		RoadsGraph mapGraph = roader.Graphify();

		if(response.toUpperCase().equals("N"))//finding direction between two cities
		{ 
			
			mapGraph.resetVisited();

			Vertex source = mapGraph.findCity(startCity);
			Vertex target = mapGraph.findCity(endCity);

			mapGraph.calculateShortestPath(source);

			System.out.println("shortest number of miles from " + startCity + " to " + endCity + ": " + target.getDistance());
			System.out.println("Shortest amount of time to get from " + startCity+ " to " + endCity + ": " + target.minToTime());
			System.out.println("--------------------------------------------------------------------------------------");
			System.out.println("Thank you for choosing to use the American Tour Guid Map.");
			System.out.println("I hope to see you again soon.");

		} else{ //finding shortest routes between cities and attractioins
			
			ArrayList<String> attractions = new ArrayList<String>(); //list of inputed attractions
			ArrayList<String> attracCities = new ArrayList<String>(); //list of cities of attractions
			ArrayList<Vertex> attCities = new ArrayList<Vertex>(); //vertexes of cities that has the attraction

			int allTime = 0; //takes in total amount of time from all travels
			int allMiles = 0; //takes in total amount of miles from all travels

			Vertex attra = null;

			System.out.println("Please enter the attractions you wish to see along the way. To stop, enter STOP.");

			String place = input.nextLine();

			while(!place.toUpperCase().equals("STOP")){ //asks for attractions 
				String[] location = place.split("\\s");
				String finalWord = "";
				
				for(String w:location){ //
					String first = w.substring(0,1);
					String afterfirst = w.substring(1);
					finalWord += first.toUpperCase() + afterfirst;
				}
				attractions.add(finalWord);
				System.out.println("Please input the next location. To stop, enter STOP.");
				place = input.nextLine();
			}

			for(int i = 0; i < attractions.size(); i++){ //adds the string version of the cities into a arraylist
				for(int j = 0; j < attractData.length; j++){
					if(attractData[j][0].equals(attractions.get(i))){
						attracCities.add(attractData[j][1]);
					}
				}
			}

			for(int i = 0; i < attracCities.size(); i++){//adds all city vertexes that has the attractions
				if(!attCities.contains(mapGraph.findCity(attracCities.get(i)))) //if the city of an attraction is not in the list, it is added
					attCities.add(mapGraph.findCity(attracCities.get(i)));
			}


			ArrayList<Integer> milesDistance = new ArrayList<Integer>(); //records the shrotest distance miles list
			ArrayList<String> timeDistance = new ArrayList<String>(); //records the shortest time 
			ArrayList<List<Vertex>> routes = new ArrayList<List<Vertex>>(); //records the vertex path

			int shortestRoute = Integer.MAX_VALUE; //set current shortest route to maximum alue
			String shortestTime  = "";

			Vertex shortestAttraction = null;
			Vertex source = mapGraph.findCity(startCity);
			Vertex target = mapGraph.findCity(endCity);

			for(int i = 0; i < attCities.size(); i++){ //calulates shortest route in beginning
				attra = attCities.get(i); //getting the attraction vertex
				mapGraph.calculateShortestPath(source); //calulates its shortest path
				int miles = attra.getDistance(); //gets that attraction path shortest destance
				if(shortestRoute > miles){ //if new distance is smaller than current distance, set that site up as the first shortest route
					shortestAttraction = attra;
					shortestRoute = miles;
					shortestTime = shortestAttraction.minToTime();
					
				}
			}

			milesDistance.add(shortestRoute); //adds shortest distance to list of shortest distance
			timeDistance.add(shortestTime); //adds shortest time to list of shortest time

			allTime += shortestAttraction.getTime(); //adds current shortest time to total time
			allMiles += shortestRoute; //adds curreent shortest miles to total miles
			
			routes.add(mapGraph.getShortestPath(shortestAttraction));

			mapGraph.resetVisited();//reset map to find next shortest path

			if(attCities.size() == 1){ //if list of attraction is one

				shortestAttraction = attCities.get(0);

				mapGraph.calculateShortestPath(shortestAttraction);

				shortestRoute = target.getDistance();
				shortestTime = target.minToTime();

				milesDistance.add(shortestRoute);
				timeDistance.add(shortestTime);

				allTime += target.getTime();
				allMiles += shortestRoute;

			} else { //if attraction list is more than one

				while(attCities.size() > 0){ //calculates distance from one attraction to the rest

					attCities.remove(shortestAttraction); //remove city vertex of the first shortest route from attracCities 

					int shortRoute = Integer.MAX_VALUE;
					Vertex tempVert = null;

					if(attCities.size() > 1){ //if the attraction list size is greater than one
						for(int i = 0; i < attCities.size(); i++){
							mapGraph.resetVisited();
							attra = attCities.get(i); //getting the attraction vertex
							mapGraph.calculateShortestPath(shortestAttraction); //calulates its shortest path
							int mil = attra.getDistance(); //gets that attraction path shortest destance
							if(shortRoute > mil){ //if new distance is smaller than current distance, set that site up as the first shortest route
								tempVert = attra;
								shortRoute = mil;
								shortestTime = attra.minToTime();
								

							}

						}
						shortestAttraction = tempVert;
						milesDistance.add(shortRoute); //adds shortest distance to 
						timeDistance.add(shortestTime);

						allTime += attra.getTime(); //adds current shortest time to total time
						allMiles += shortestRoute; //adds curreent shortest miles to total miles

						mapGraph.resetVisited();
						
					} else{ //if there is only one attraction site left
						tempVert = attCities.get(0);
						 

						mapGraph.calculateShortestPath(shortestAttraction);
						
						int miler = tempVert.getDistance();

						shortestRoute = miler;
						shortestTime = tempVert.minToTime();

						milesDistance.add(shortestRoute);
						timeDistance.add(shortestTime);

						allTime += tempVert.getTime(); //adds current shortest time to total time
						allMiles += shortestRoute; //adds curreent shortest miles to total miles

						attCities.remove(tempVert); //remove city vertex of the first shortest route from attracCities 
						shortestAttraction = tempVert;
						mapGraph.resetVisited();//reset map to find next shortest path
					}
					mapGraph.resetVisited();//reset map to find next shortest path 
				}
				//calculates last attraction site to target city
				mapGraph.calculateShortestPath(shortestAttraction);

				int shortestRout = target.getDistance();
				shortestTime = target.minToTime();

				milesDistance.add(shortestRout);
				timeDistance.add(shortestTime);

				allTime += target.getTime(); //adds current shortest time to total time
				allMiles += shortestRoute; //adds curreent shortest miles to total miles
			}

			System.out.println("*&*&**&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&\n");
			System.out.println("Here are the mile distances for each place from starting city to ending city.");
			System.out.println(milesDistance);
			System.out.println("here are the time it takes to reach each destination.");
			System.out.println(timeDistance + "\n");
			System.out.println("*&*&**&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&\n");
			System.out.println("The shortest amount of distance it will take to traverse all these locations from " + startCity + " to " + endCity +  ": " + allMiles + " miles");
			System.out.println("The shortsest amount of time it will take to traverse all these locations from " + startCity + " to " + endCity +  ": " + target.minToTime(allTime));
			System.out.println("--------------------------------------------------------------------------------------");
			System.out.println("Thank you for choosing to use the American Tour Guid Map.");
			System.out.println("I hope to see you again soon.");

		}
	};

}