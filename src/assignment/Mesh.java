package assignment;

import java.io.IOException;
import java.util.HashSet;

public class Mesh extends GraphicalObject{
	// Fields
	HashSet<Polygon> polygons;
	MeshReader reader;
	MeshWriter writer;
	// Constructor
	// Methods
	public void setReader(MeshReader reader) {
		this.reader = reader;
	}
	public void setWriter(MeshWriter writer) {
		this.writer = writer;
	}
	public void readFromFile(String filename) throws IOException, WrongFileFormatException {
		this.polygons = reader.read(filename);
	}
	public void writeToFile(String filename) throws IOException {
		this.writer.write(filename, polygons);
	}
	// Overrides
	@Override
	public void transform(double[][] matrix) {
		// Looping through all the polygons and transforming them all.
		for (Polygon p : this.polygons) p.transform(matrix);
	}
	@Override
	public boolean equals(Object obj) {
		// Checking for instance then down-casting.
		if (!(obj instanceof Mesh)) return false;
		Mesh casted = (Mesh)obj;
		// Seeing if all the polygons are the same.
		return this.polygons.equals(casted.polygons); // Checks for length, and entries equal irrespective of order.
		
	}
	@Override
	public int hashCode() {
		// The hash code will be the sum of all of the polygons' hash codes.
		int code = 0;
		for (Polygon p : this.polygons) code += p.hashCode();
		return code;
	}
}
