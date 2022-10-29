package assignment;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PLYMeshReader implements MeshReader {
	@Override
	public HashSet<Polygon> read(String filename) throws IOException, WrongFileFormatException {
		//=================================== Part 0: Important Variables. =================================
		// Pattern to match the header.
		Pattern header = Pattern.compile("ply\nformat ascii 1.0\nelement vertex\s+[1-9]+[0-9]*\s*\nproperty "
												 + "float32 x\nproperty float32 y\nproperty float32 z\nelement face"
												 + "\s+[1-9]+[0-9]*\s*\nproperty list uint8 int32 vertex_indices\n"
												 + "end_header\n");
		Pattern vertexReg = Pattern.compile("(\s*(-)?[0-9]+(\\.[0-9]+)?){3}\s*");
		Pattern faceReg = Pattern.compile("(\s*[0-9]+)+(\s)*");
		
		
		//===================== Part 1: Read the data into a 2D ArrayList of Strings. =====================
		// Store the result.
		ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
		// Store the data.
		String data = "";
		// Setting up the reader.
		BufferedReader b = new BufferedReader(new FileReader(filename));
		// Read the entire file into a string.
		String line = b.readLine();
		while (line != null) {
			data += line + "\n";
			line = b.readLine();
		}
		b.close();
		// Checking for the presence of a header at the beginning of the file.
		Matcher match = header.matcher(data);
		if(!match.find()) throw new WrongFileFormatException("PLY File is not formatted properly. Header is formatted incorrectly.");
		// If found, check for position in the file.
		if(match.start() != 0) throw new WrongFileFormatException("PLY File is not formatted properly. Header is formatted incorrectly.");
		
		
		//=========================== Part 1.1: Get Number of Vertices and Faces. ============================
		// If all checks pass, get number of vertices and faces.
		int[] fields = new int[2];
		// Splitting the string into an array of lines.
		String[] lines = match.group().split("\n");
		// Getting the number of vertices.
		fields[0] = Integer.parseInt(lines[2].trim().split(" +")[2]);
		// Getting the number of faces.
		fields[1] = Integer.parseInt(lines[6].trim().split(" +")[2]);
		
		
		//================================ Part 1.2: Splitting the Data =======================================
		// Split the data into two ArrayLists of Strings.
		String[] lines1 = data.split("\n");
		ArrayList<String> vertices = new ArrayList<String>();
		// Vertices.
		int i;
		for (i = 9; (i-9) < fields[0]; i++) {
			Matcher match1 = vertexReg.matcher(lines1[i]);
			if (!match1.matches()) throw new WrongFileFormatException("PLY file is not formatted properly. " + lines1[i] + " is not a valid vertex.");
			vertices.add(lines1[i]);
		}
		// Faces.
		ArrayList<String> faces = new ArrayList<String>();
		for (int j = i; j-i < fields[1]; j++) {
			Matcher match1 = faceReg.matcher(lines1[j]);
			if (!match1.matches()) throw new WrongFileFormatException("PLY file is not formatted properly. " + lines1[j] + " is not a valid face.");
			faces.add(lines1[j]);
		}
		// Adding both to the ArrayList.
		result.add(vertices);
		result.add(faces);
		
		
		//================== Part 2: Parse the Vertices (Convert to ArrayList of Vertex). ==================
		ArrayList<Vertex> vertexArr = new ArrayList<Vertex>();
		for (String verLine : result.get(0)) {
			// Grabbing the individual doubles and putting them in an array.
			String [] seperate = verLine.trim().split(" +");
			double [] coords = new double[3];
			for (int j = 0; j < seperate.length; j++) coords[j] = Double.parseDouble(seperate[j]);
			// Creating a new vertex object and adding it to the ArrayList.
			Vertex v = new Vertex(coords[0], coords[1], coords[2]);
			vertexArr.add(v);
		}

		
		//===================================== Part 3: Parsing the Faces. ================================
		HashSet<Polygon> faceResult = new HashSet<Polygon>();
		// Building the HashSet of vertices.
		for (String line1 : result.get(1)) {
			LinkedHashSet<Vertex> set = new LinkedHashSet<Vertex>();
			// Separating the line.
			String [] seperated = line1.trim().split(" +");
			// Getting the number of vertices in a face.
			int num = Integer.parseInt(seperated[0]);
			// Extracting the line number for faces.
			for (int j = 1; j-1 < num; j++) {
				// Getting the index of the vertex then adding it to the LinkedHashSet.
				int index = Integer.parseInt(seperated[j]);
				// Getting the vertex and throwing an exception if not present.
				Vertex v;
				try {
					v = vertexArr.get(index);
				} catch (IndexOutOfBoundsException e) {
					throw new WrongFileFormatException("PLY File is formatted incorrectly. Index of vertex is out of bounds.");
				}
				// Adding the vertex to the LinkedHashSet.
				set.add(new Vertex(v.x, v.y, v.z));
			}
			// Creating a Polygon object and adding it to faces.
			Polygon p = new Polygon(set);
			faceResult.add(p);
		}
		// Returning the faces.
		return faceResult;
	}

}
