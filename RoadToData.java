import java.util.*;

public class RoadToData{

	public String [][] data; //all distinct roads split by commas

	public RoadToData(String[] list){ //splits each road into four groups: start, finish, miles, time
		data = new String[list.length][4];
		for(int i = 0; i < list.length; i++){
			data[i] = list[i].split(",");
		}
	}

	public int printRoadNum(){ //returns length of the number of roads
		return this.data.length;
	}

	public RoadsGraph Graphify(){ //returns a compelete graph
		int numCities; //number of citiess
		ArrayList<String> cities = new ArrayList<String>(); //list of all distinct cities
		for(int i = 0; i < data.length; i++){ //loops through road list 
			if(!cities.contains(data[i][0])){ //if city is not already in cities list
				cities.add(data[i][0]);
			}
			if(!cities.contains(data[i][1])){
				cities.add(data[i][1]);
			}
		}
		numCities = cities.size();
		RoadsGraph graph = new RoadsGraph(numCities, data, cities);

		return graph;
	}
}