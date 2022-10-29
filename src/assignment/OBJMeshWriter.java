package assignment;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class OBJMeshWriter implements MeshWriter{
	@Override
	public void write(String filename, HashSet<Polygon> polygons) throws IOException {
		//=============== Part 1: Converting the Polygon objects into a 2D ArrayList of Strings. ===============
		// The variable to store the resulting data.
		ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
		result.add(new ArrayList<String>());
		result.add(new ArrayList<String>());
		// List to keep track of the vertices.
		ArrayList<Vertex> vertices = new ArrayList<Vertex>();
		// Loop through all polygons.
		for (Polygon p : polygons) {
			String face = "f";
			// Collecting all the vertices in the list.
			for (Vertex v : p.vertices) {
				if (!vertices.contains(v)) {
					// If not already in the list.
					vertices.add(v);
					result.get(0).add("v " + v.toString()+"\n");
				}
				face += " " + ((int)(vertices.indexOf(v))+1);
			}
			// Adding the current face to the result.
			result.get(1).add(face+"\n");
		}
				
				

		//==================================== Part 2: Writing to the File. ====================================
		// Setting up the writer.
		BufferedWriter b = new BufferedWriter(new FileWriter(filename));
		// Writing the strings to the file line by line.
		for (ArrayList<String> section : result) for (String line : section) b.write(line);
		// Closing the writer.
		b.close();
	}
}
