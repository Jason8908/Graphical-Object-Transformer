package assignment;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;

public class OFFMeshReader implements MeshReader {
	@Override
	public HashSet<Polygon> read(String filename) throws IOException, WrongFileFormatException {
		//==================== Part 1: Reading the data into the string while validating each line. ====================
		// RegEx's and data string variable.
		Pattern vertexReg = Pattern.compile("(\s*(-)?[0-9]+(\\.[0-9]+)?){3}\s*");
		Pattern faceReg = Pattern.compile("(\s*[0-9]+)+(\s)*");
		Pattern off = Pattern.compile("\s*OFF\s*");
		Pattern items = Pattern.compile("(\s*[0-9]+){2}\s0(\s)*");
		// 2D ArrayList to store data.
		ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
		// Setting up the reader.
		BufferedReader b = new BufferedReader(new FileReader(filename));
		// Reading all the lines and validating each one.
		String line = b.readLine();
		if (line == null) throw new WrongFileFormatException("OFF File is formatted incorrectly. Empty file.");
		// If the file does not start with OFF.
		Matcher matcher = off.matcher(line);
		if (!matcher.matches()) throw new WrongFileFormatException("OFF File is formatted incorrectly.");
		// Reading the next line (which should contain 3 items).
		line = b.readLine();
		// Parsing the line (getting the number of vertices and faces.
		int numVert, numFace;
		String[] lineSplit = line.split(" +");
		numVert = Integer.parseInt(lineSplit[0]);
		numFace = Integer.parseInt(lineSplit[1]);
		// Reading all of the vertices.
		ArrayList<String> verticesL = new ArrayList<String>();
		for (int i = 0; i < numVert; i++) {
			// Updating the line and the matcher.
			line = b.readLine();
			// Updating the matcher
			matcher = vertexReg.matcher(line);
			// If not formatted correctly.
			if (!matcher.matches()) throw new WrongFileFormatException("OFF File is formatted incorrectly. " + line + " is not a valid vertex.");
			// Adding the line to the string.
			verticesL.add(line);
		}
		// Reading all of the faces.
		ArrayList<String> facesL = new ArrayList<String>();
		for (int i = 0; i < numFace; i++) {
			// Updating the line and the matcher.
			line = b.readLine();
			// Updating the matcher
			matcher = faceReg.matcher(line);
			// If the line does not match a face line.
			if (!matcher.matches()) throw new WrongFileFormatException("OFF File is formatted incorrectly. " + line + " is not a valid face.");
			// Adding the line to the string.
			facesL.add(line);
		}
		// Closing the file.
		b.close();
		// Adding the collected lists into the 2D list.
		data.add(verticesL);
		data.add(facesL);		
		
		
		//========================================== Part 2: Parsing vertices. ==========================================
		ArrayList<Vertex> vertices = new ArrayList<Vertex>();
		// Splitting up the strings into an array.
		for (String line1 : data.get(0)) {
			// Grabbing the individual doubles and putting them in an array.
			String [] seperate = line1.trim().split(" +");
			double [] coords = new double[3];
			for (int i = 0; i < seperate.length; i++) coords[i] = Double.parseDouble(seperate[i]);
			// Creating a new vertex object and adding it to the ArrayList.
			Vertex v = new Vertex(coords[0], coords[1], coords[2]);
			vertices.add(v);
		}

		
		//============================================ Part 3: Parsing faces.============================================
		HashSet<Polygon> faceSet = new HashSet<Polygon>();
		// Building the LinkedHashSet of vertices.
		for (String line1 : data.get(1)) {
			LinkedHashSet<Vertex> set = new LinkedHashSet<Vertex>();
			// Separating the line.
			String [] seperated = line1.trim().split(" +");
			// Getting the number of vertices per face.
			int faceVert = Integer.parseInt(seperated[0]);
			// Extracting the line number for faces.
			for (int i = 1; (i-1) < faceVert; i++) {
				// Getting the index of the vertex then adding it to the LinkedHashSet.
				int index = Integer.parseInt(seperated[i]);
				// Getting the vertex and throwing an exception if not present.
				Vertex v;
				try {
					v = vertices.get(index);
				} catch (IndexOutOfBoundsException e) {
					throw new WrongFileFormatException("OFF File is formatted incorrectly. Index of vertex is out of bounds.");
				}
				// Adding the vertex to the LinkedHashSet.
				set.add(new Vertex(v.x, v.y, v.z));
			}
			// Creating a Polygon object and adding it to faces.
			Polygon p = new Polygon(set);
			faceSet.add(p);
		}
		// Returning the faces.
		return faceSet;
	}
}
