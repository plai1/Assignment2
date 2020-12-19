

import java.util.*;

public class RoadsGraph{



	public static int index = 0; //Index for city

	public static Vertex[] cities; //List of cities vertexes
	public static ArrayList<Vertex> vertex  = new ArrayList<Vertex>(); //list of vertexes
	public static String[] locations; //List of unique locations
	public static LinkedList<Integer> adj[]; //Adjacency Lists
	public static ArrayList<Edge> roadEdge = new ArrayList<Edge>();


	public void addIndexByOne(){
		index++;
	}

	public RoadsGraph(int verticies, String[][] data, ArrayList<String> city){
		cities = new Vertex[verticies];
		locations = new String[verticies];	

		for(int i = 0; i < city.size(); i++){ //creates vertexes of all unique cities
			Vertex v = new Vertex(city.get(i), index);
			addIndexByOne();
			cities[v.getIndex()] = v;
			locations[i] = city.get(i);
		}		

		for(int i = 0; i < cities.length; i++){ //create edges 
			for(int j = 0; j < data.length; j++){
				if(data[j][0].equals(cities[i].getName())){ //edge start equals to vertex
					Vertex start = cities[i]; //starting vertex
					Vertex end = findCity(data[j][1]);
					Edge newEdge = new Edge(start, end, Integer.parseInt(data[j][2]), Integer.parseInt(data[j][3]));
					Edge reverEdge = new Edge(end, start, Integer.parseInt(data[j][2]), Integer.parseInt(data[j][3]));
					if(!cities[i].hasEdge(newEdge)){
						cities[i].addEdge(newEdge); //adds edge to vertex
						roadEdge.add(newEdge);
					}
					if(!newEdge.getEndVertex().hasEdge(reverEdge)){
						newEdge.getEndVertex().addEdge(reverEdge);
						roadEdge.add(reverEdge);
					}
				}
			}
		}
	}

	public static void calculateShortestPath(Vertex source){//calculate shortest path to every vertex by inputing distance weight to each vertex from source
		
		source.setDistance(0);
		source.setTime(0);
		PriorityQueue<Vertex> priorityQueue = new PriorityQueue<>();
		priorityQueue.add(source);
		source.setVisited(true);

		while(!priorityQueue.isEmpty()){

			Vertex actualVertex = priorityQueue.poll();

			for(Edge edge : actualVertex.getAdjacenciesList()){

				Vertex v = edge.getEndVertex();
				if(!v.isVisited())
				{
					int newDistance = actualVertex.getDistance() + edge.getMiles();
					int newTime = actualVertex.getTime() + edge.getTime();

					if(newDistance < v.getDistance()){
						priorityQueue.remove(v);
						v.setDistance(newDistance);
						v.setTime(newTime);
						v.setPredecessor(actualVertex);
						priorityQueue.add(v);
					}
				}
			}
			actualVertex.setVisited(true);
		}
	}

	public List<Vertex> getShortestPath(Vertex targetVertex){ //returns vertex path from source vertext to target vertex
		List<Vertex> path = new ArrayList();

		for(Vertex vertex=targetVertex; vertex != null; vertex = vertex.getPredecessor()){
			path.add(vertex);
		}

		Collections.reverse(path);
		return path;
	}

	public Vertex findCity(String name){ //searches city vertex based on partial input
		for(int i = 0; i < cities.length; i++){
			if (cities[i].getName().contains(name)){
				return cities[i];
			} else if(cities[i].getName().equals(name)){
				return cities[i];
			}
		}
		return null;
	}

	public boolean isStartVert(Vertex vert){
		int numStart = 0;
		for(int i = 0; i < roadEdge.size(); i++){
			if(roadEdge.get(i).getStartVertex() == vert){
				numStart++;
			}
		}
		return (numStart == 0);
	}

	public void resetVisited(){
		for(int i = 0; i < cities.length; i++){
			(cities[i]).setVisited(false);
			(cities[i]).setDistance(Integer.MAX_VALUE);
			(cities[i]).setTime(Integer.MAX_VALUE);
		}
	}
};