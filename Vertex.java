

import java.util.*;

public class Vertex implements Comparable<Vertex>{ //Vertex for each city

		String name;
		int index;
		private List<Edge> adjacenciesList;
		public Vertex predecessor;
		private int distance = Integer.MAX_VALUE;
		private int time = Integer.MAX_VALUE;
		public int days;
		public int hours;
		public int minut;
		public Boolean visited;

		public Vertex(String name, int ind){
			this.name = name;
			this.index = ind;
			this.adjacenciesList = new ArrayList<>();
			this.visited = false;
		}

		public String getName(){
			return this.name;
		}	

		public void addEdge(Edge edge){
			this.adjacenciesList.add(edge);
		}

		public void setDistance(int num){
			this.distance = num;
		}

		public int getDistance(){
			return this.distance;
		}

		public void setTime(int num){
			this.time = num;
		}

		public int getTime(){
			return this.time;
		}

		public int getDays(){
			return this.days;
		}

		public int getHours(){
			return this.hours;
		}

		public int getMinutes(){
			return this.minut;
		}

		public int getAllMinutes(){
			return this.time;
		}

		public void setVisited(boolean visited){
			this.visited = visited;
		}

		public boolean isVisited(){
			return this.visited;
		}

		public void setVisitFalse(){
			this.visited = false;
		}

		public List<Edge> getAdjacenciesList(){
			return this.adjacenciesList;
		}

		public void setAdjacenciesList(List<Edge> adjacenciesList){
			this.adjacenciesList = adjacenciesList;
		}

		public Vertex getPredecessor(){
			return this.predecessor;
		}

		public void setPredecessor(Vertex predec){
			this.predecessor = predec;
		}

		

		public int getIndex(){
			return (this.index);
		}

		

		public void printEdges(){
			for(int i = 0; i < adjacenciesList.size(); i++){
				System.out.println(adjacenciesList.get(i));
			}
		}


		public boolean hasEdge(Edge edger){
			for(int i = 0; i < adjacenciesList.size(); i++){
				if(adjacenciesList.get(i) == edger)
					return true;
			}
			return false;
		}

		public String minToTime(){
			int minute = this.time;
			int days = minute / 1440;
			minute = minute % 1440;
			int hours = minute / 60;
			minute = minute % 60;
			String res =  days + " Days, "+ hours + " Hours, " + minute + " Minutes";
			this.days = days;
			this.hours = hours;
			this.minut = minute;
			return res;
		}

		public String minToTime(int timer){
			int minute = timer;
			int days = minute / 1440;
			minute = minute % 1440;
			int hours = minute / 60;
			minute = minute % 60;
			String res = days + " Days,"+ hours + " Hours," + minute + " Minutes";
			return res;
		}

		@Override
		public String toString(){
			return (this.name);//+ ", " + this.index
		}

		@Override
		public int compareTo(Vertex otherVertex){
			return Double.compare(this.distance, otherVertex.getDistance());
		}
	}
