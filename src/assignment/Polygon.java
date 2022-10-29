package assignment;
import java.util.LinkedHashSet;

public class Polygon extends GraphicalObject {
	// Fields
	LinkedHashSet<Vertex> vertices;
	// Constructor
	public Polygon(LinkedHashSet<Vertex> new_vertices) {
		// Initializing the value of vertices.
		this.vertices = new_vertices;
	}
	// Implementations
	public void transform(double [][] matrix) {
		// Looping through all the vertices.
		for (Vertex vert : this.vertices) {
			// Transforming the vertex.
			vert.transform(matrix);
		}
	};
	// Overrides
	@Override
	public boolean equals(Object obj) {
		// Checking for instance then down-casting.
		if (!(obj instanceof Polygon)) return false;
		Polygon casted = (Polygon)obj;
		// Seeing if all the coordinates are the same.
		return this.vertices.equals(casted.vertices); // Checks for length, and corresponding entries equal.
		
	}
	@Override
	public int hashCode() {
		// Converting the LinkedHashSet to an array.
		Object[] temp = this.vertices.toArray();
		// The hash code will be the sum of all of the vertices' hash codes.
		int code = 0;
		for (int i = 1; i <= temp.length; i++) code += i*2*temp[i-1].hashCode();
		return code;
	}
}
