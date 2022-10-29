package assignment;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class PLYMeshWriter implements MeshWriter{
	@Override
	public void write(String filename, HashSet<Polygon> polygons) throws IOException {
		//=============== Part 1: Creating a 2D ArrayList of data to store: Vertices and Faces. =============== 
		// The variable to store the resulting data.
		ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
		data.add(new ArrayList<String>());
		data.add(new ArrayList<String>());
		// List to keep track of the vertices.
		ArrayList<Vertex> vertices = new ArrayList<Vertex>();
		// Loop through all polygons.
		for (Polygon p : polygons) {
			String face = p.vertices.size() + "";
			// Collecting all the vertices in the list.
			for (Vertex v : p.vertices) {
				if (!vertices.contains(v)) {
					// If not already in the list.
					vertices.add(v);
					data.get(0).add(v.toString()+"\n");
				}
				face += " " + ((int)(vertices.indexOf(v)));
			}
			// Adding the current face to the result.
			data.get(1).add(face+"\n");
		}
		
		//================================== Part 2: Writing to the File. ==================================
		// Setting up the writer.
		BufferedWriter b = new BufferedWriter(new FileWriter(filename));
		// Adding the header into the file.
		b.write("ply\nformat ascii 1.0\nelement vertex " + data.get(0).size() + "\nproperty float32 x\n"
				+ "property float32 y\nproperty float32 z\nelement face " + data.get(1).size()
				+ "\nproperty list uint8 int32 vertex_indices\nend_header\n");
		// Writing the strings to the file line by line.
		for (ArrayList<String> section : data) for (String line : section) b.write(line);
		// Closing the writer.
		b.close();
	}
}
