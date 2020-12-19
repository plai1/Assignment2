

public class Edge{ //edge class for each vertex

		int miles, time;
		Vertex start;
		Vertex end;

		public Edge(Vertex start, Vertex end, int miles, int time){
			this.start = start;
			this.end = end;
			this.miles = miles;
			this.time = time;
		}

		public String toString(){
			return this.start + ", " + this.end + ", " + this.miles + ", " + this.time;
		}

		public void setStart(Vertex start){
			this.start = start;
		}

		public void setEnd(Vertex end){
			this.end = end;
		}

		public int getTime(){
			return this.time;
		}

		public int getMiles(){
			return this.miles;
		}

		public Vertex getStartVertex(){
			return this.start;
		}

		public Vertex getEndVertex(){
			return this.end;
		}

	}
