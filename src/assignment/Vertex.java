package assignment;
import java.lang.Math;

public class Vertex extends GraphicalObject {
	// Fields
	double x, y, z;
	// Constructor
	public Vertex(double new_x, double new_y, double new_z) {
		// Initializing the values.
		this.x = new_x;
		this.y = new_y;
		this.z = new_z;
	}
	// Implementations.
	public void transform(double [][] matrix) {
		// Adjusting the coordinates according to matrix multiplication.
		double new_x = (this.x*matrix[0][0]) + (this.y*matrix[0][1]) + (this.z*matrix[0][2]);
		double new_y = (this.x*matrix[1][0]) + (this.y*matrix[1][1]) + (this.z*matrix[1][2]);
		double new_z = (this.x*matrix[2][0]) + (this.y*matrix[2][1]) + (this.z*matrix[2][2]);
		// Changing the coordinates.
		this.x = new_x;
		this.y = new_y;
		this.z = new_z;
	}
	// Overrides.
	@Override
	public boolean equals(Object obj) {
		// Checking for instance then down-casting.
		if (!(obj instanceof Vertex)) return false;
		Vertex casted = (Vertex)obj;
		// Comparing if the two vertexes are equal.
		return casted.x == this.x && casted.y == this.y && casted.z == this.z;
	}
	@Override
	public int hashCode() {
		// The hash code is adding up the coordinates then flooring the resulting value.
		return (int)Math.floor(2*this.x+31*this.y+97*this.z);
	}
	@Override
	public String toString() {
		// Returning the coordinates separated by spaces.
		return this.x + " " + this.y + " " + this.z;
	}
}
