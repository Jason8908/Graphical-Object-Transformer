package assignment;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedHashSet;

import org.junit.jupiter.api.Test;

import junit.framework.Assert;

class Tests {
	private String directory = "./tests";
	// Reading Valid Input Files.
	@Test
	void testReadValidOBJ() {
		// Expected values.
		Vertex a, b, c;
		LinkedHashSet<Vertex> d = new LinkedHashSet<Vertex>();
		HashSet<Polygon> expected = new HashSet<Polygon>();
		a = new Vertex(1.5, 2.5, 3.5);
		b = new Vertex(3.1, 42.4, 12.3);
		c = new Vertex(5.1, 52.0, 21.1);
		d.add(a);
		d.add(b);
		d.add(c);
		Polygon p = new Polygon(d);
		expected.add(p);
		// Testing.
		OBJMeshReader read = new OBJMeshReader();
		try {
			HashSet<Polygon> actual = read.read(directory+"/validOBJ1.obj");
			assertEquals(expected, actual);
		} catch(WrongFileFormatException e) {
			System.out.println(e.message);
			fail("Unexpected Exception.");
		} catch(IOException e) {
			System.out.println(e.getMessage());
			fail("Unexpected Exception.");
		}
	}
	@Test
	void testReadValidPLY() {
		// Expected values.
		Vertex a, b, c;
		LinkedHashSet<Vertex> d = new LinkedHashSet<Vertex>();
		HashSet<Polygon> expected = new HashSet<Polygon>();
		a = new Vertex(1.5, 2.5, 3.5);
		b = new Vertex(3.1, 42.4, 12.3);
		c = new Vertex(5.1, 52.0, 21.1);
		d.add(a);
		d.add(b);
		d.add(c);
		Polygon p = new Polygon(d);
		expected.add(p);
		// Testing.
		PLYMeshReader read = new PLYMeshReader();
		try {
			HashSet<Polygon> actual = read.read(directory+"/validPLY1.ply");
			assertEquals(expected, actual);
		} catch(WrongFileFormatException e) {
			System.out.println(e.message);
			fail("Unexpected Exception.");
		} catch(IOException e) {
			System.out.println(e.getMessage());
			fail("Unexpected Exception.");
		}
	}
	@Test
	void testReadValidOFF() {
		// Expected values.
		Vertex a, b, c;
		LinkedHashSet<Vertex> d = new LinkedHashSet<Vertex>();
		HashSet<Polygon> expected = new HashSet<Polygon>();
		a = new Vertex(1.5, 2.5, 3.5);
		b = new Vertex(3.1, 42.4, 12.3);
		c = new Vertex(5.1, 52.0, 21.1);
		d.add(a);
		d.add(b);
		d.add(c);
		Polygon p = new Polygon(d);
		expected.add(p);
		// Testing.
		OFFMeshReader read = new OFFMeshReader();
		try {
			HashSet<Polygon> actual = read.read(directory+"/validOFF1.off");
			assertEquals(expected, actual);
		} catch(WrongFileFormatException e) {
			System.out.println(e.message);
			fail("Unexpected Exception.");
		} catch(IOException e) {
			System.out.println(e.getMessage());
			fail("Unexpected Exception.");
		}
	}
	
	// Testing Mesh Creation.
	@Test
	void testMeshOBJ() {
		// Expected values.
		Vertex v1, v2, v3, v4;
		v1 = new Vertex(1.5, 2.5, 3.5);
		v2 = new Vertex(3.1, 42.4, 12.3);
		v3 = new Vertex(5.1, 52.0, 21.1);
		v4 = new Vertex(4.1, 5.5, 19.2);
		LinkedHashSet<Vertex> l1, l2;
		l1 = new LinkedHashSet<Vertex>();
		l2 = new LinkedHashSet<Vertex>();
		l1.add(v1);
		l1.add(v2);
		l1.add(v3);
		l2.add(v2);
		l2.add(v3);
		l2.add(v4);
		Polygon p1, p2;
		p1 = new Polygon(l1);
		p2 = new Polygon(l2);
		HashSet<Polygon> hs = new HashSet<Polygon>();
		hs.add(p1);
		hs.add(p2);
		Mesh m = new Mesh();
		m.polygons = hs;
		// Testing.
		Mesh m2 = new Mesh();
		m2.setReader(new OBJMeshReader());
		try {
			m2.readFromFile(directory+"/validOBJ2.obj");
			assertEquals(m2, m);
		} catch (IOException e) {
			System.out.println(e.getMessage());
			fail("Unexpected Exception");
		} catch (WrongFileFormatException e) {
			System.out.println(e.message);
			fail("Unexpected Exception");
		}
	}

	// Testing transformations.
	@Test
	void testRotateXVertex() {
		Vertex expected = new Vertex(2.0, -1.964101615137754, 4.598076211353316);
		Vertex original = new Vertex(2.0, 3.0, 4.0);
		original.rotateXAxis(Math.PI/3);
		assertEquals(expected, original);
	}
	@Test
	void testRotateYVertex() {
		Vertex expected = new Vertex(4.464101615137754, 3.0, 0.26794919243112325);
		Vertex original = new Vertex(2.0, 3.0, 4.0);
		original.rotateYAxis(Math.PI/3);
		assertEquals(expected, original);
	}
	@Test
	void testRotateZVertex() {
		Vertex expected = new Vertex(-1.5980762113533158, 3.2320508075688776, 4.0);
		Vertex original = new Vertex(2.0, 3.0, 4.0);
		original.rotateZAxis(Math.PI/3);
		assertEquals(expected, original);
	}
	
	@Test
	void testRotateXPolygon() {
		Vertex eV1, eV2, eV3;
		eV1 = new Vertex(2.0, -1.964101615137754, 4.598076211353316);
		eV2 = new Vertex(1.0, -1.5980762113533158, 3.2320508075688776);
		eV3 = new Vertex(4.0, 1.6339745962155618, 4.830127018922193);
		LinkedHashSet<Vertex> eHs = new LinkedHashSet<Vertex>();
		eHs.add(eV1);
		eHs.add(eV2);
		eHs.add(eV3);
		Polygon eP = new Polygon(eHs);
		
		Vertex oV1, oV2, oV3;
		oV1 = new Vertex(2.0, 3.0, 4.0);
		oV2 = new Vertex(1.0, 2.0, 3.0);
		oV3 = new Vertex(4.0, 5.0, 1.0);
		LinkedHashSet<Vertex> oHs = new LinkedHashSet<Vertex>();
		oHs.add(oV1);
		oHs.add(oV2);
		oHs.add(oV3);
		Polygon oP = new Polygon(oHs);
		
		oP.rotateXAxis(Math.PI/3);
		assertEquals(eP, oP);
	}
	@Test
	void testRotateYPolygon() {
		Vertex eV1, eV2, eV3;
		eV1 = new Vertex(4.464101615137754, 3.0, 0.26794919243112325);
		eV2 = new Vertex(3.098076211353316, 2.0, 0.6339745962155618);
		eV3 = new Vertex(2.8660254037844393, 5.0, -2.9641016151377544);
		LinkedHashSet<Vertex> eHs = new LinkedHashSet<Vertex>();
		eHs.add(eV1);
		eHs.add(eV2);
		eHs.add(eV3);
		Polygon eP = new Polygon(eHs);
		
		Vertex oV1, oV2, oV3;
		oV1 = new Vertex(2.0, 3.0, 4.0);
		oV2 = new Vertex(1.0, 2.0, 3.0);
		oV3 = new Vertex(4.0, 5.0, 1.0);
		LinkedHashSet<Vertex> oHs = new LinkedHashSet<Vertex>();
		oHs.add(oV1);
		oHs.add(oV2);
		oHs.add(oV3);
		Polygon oP = new Polygon(oHs);
		
		oP.rotateYAxis(Math.PI/3);
		assertEquals(eP, oP);
	}
	@Test
	void testRotateZPolygon() {
		Vertex eV1, eV2, eV3;
		eV1 = new Vertex(-1.5980762113533158, 3.2320508075688776, 4.0);
		eV2 = new Vertex(-1.2320508075688772, 1.8660254037844388, 3.0);
		eV3 = new Vertex(-2.3301270189221923, 5.964101615137755, 1.0);
		LinkedHashSet<Vertex> eHs = new LinkedHashSet<Vertex>();
		eHs.add(eV1);
		eHs.add(eV2);
		eHs.add(eV3);
		Polygon eP = new Polygon(eHs);
		
		Vertex oV1, oV2, oV3;
		oV1 = new Vertex(2.0, 3.0, 4.0);
		oV2 = new Vertex(1.0, 2.0, 3.0);
		oV3 = new Vertex(4.0, 5.0, 1.0);
		LinkedHashSet<Vertex> oHs = new LinkedHashSet<Vertex>();
		oHs.add(oV1);
		oHs.add(oV2);
		oHs.add(oV3);
		Polygon oP = new Polygon(oHs);
		
		oP.rotateZAxis(Math.PI/3);
		assertEquals(eP, oP);
	}
	
	@Test
	void testRotateXMesh() {
		Vertex eV1, eV2, eV3;
		eV1 = new Vertex(2.0, 3.0, 4.0);
		eV2 = new Vertex(1.0, 2.0, 3.0);
		eV3 = new Vertex(4.0, 5.0, 1.0);
		LinkedHashSet<Vertex> eHs1, eHs2;
		eHs1 = new LinkedHashSet<Vertex>();
		eHs1.add(eV1);
		eHs1.add(eV2);
		eHs1.add(eV3);
		eHs2 = new LinkedHashSet<Vertex>();
		eHs2.add(eV1);
		eHs2.add(eV3);
		eHs2.add(eV2);
		Polygon eP1, eP2;
		eP1 = new Polygon(eHs1);
		eP2 = new Polygon(eHs2);
		HashSet<Polygon> eHsP = new HashSet<Polygon>();
		eHsP.add(eP1);
		eHsP.add(eP2);
		Mesh eM = new Mesh();
		eM.polygons = eHsP;
		
		Vertex oV1, oV2, oV3;
		oV1 = new Vertex(2.0, 3.0, 4.0);
		oV2 = new Vertex(1.0, 2.0, 3.0);
		oV3 = new Vertex(4.0, 5.0, 1.0);
		LinkedHashSet<Vertex> oHs1, oHs2;
		oHs1 = new LinkedHashSet<Vertex>();
		oHs1.add(new Vertex(oV1.x, oV1.y, oV1.z));
		oHs1.add(new Vertex(oV2.x, oV2.y, oV2.z));
		oHs1.add(new Vertex(oV3.x, oV3.y, oV3.z));
		oHs2 = new LinkedHashSet<Vertex>();
		oHs2.add(new Vertex(oV1.x, oV1.y, oV1.z));
		oHs2.add(new Vertex(oV3.x, oV3.y, oV3.z));
		oHs2.add(new Vertex(oV2.x, oV2.y, oV2.z));
		Polygon oP1, oP2;
		oP1= new Polygon(oHs1);
		oP2 = new Polygon(oHs2);
		HashSet<Polygon> oHsP = new HashSet<Polygon>();
		oHsP.add(oP1);
		oHsP.add(oP2);
		Mesh oM = new Mesh();
		oM.polygons = oHsP;
		
		oM.rotateXAxis(0);
		oM.setWriter(new OBJMeshWriter());
		
		assertEquals(eM, oM);
	}
	@Test
	void testRotateYMesh() {
		Vertex eV1, eV2, eV3;
		eV1 = new Vertex(2.0, 3.0, 4.0);
		eV2 = new Vertex(1.0, 2.0, 3.0);
		eV3 = new Vertex(4.0, 5.0, 1.0);
		LinkedHashSet<Vertex> eHs1, eHs2;
		eHs1 = new LinkedHashSet<Vertex>();
		eHs1.add(eV1);
		eHs1.add(eV2);
		eHs1.add(eV3);
		eHs2 = new LinkedHashSet<Vertex>();
		eHs2.add(eV1);
		eHs2.add(eV3);
		eHs2.add(eV2);
		Polygon eP1, eP2;
		eP1 = new Polygon(eHs1);
		eP2 = new Polygon(eHs2);
		HashSet<Polygon> eHsP = new HashSet<Polygon>();
		eHsP.add(eP1);
		eHsP.add(eP2);
		Mesh eM = new Mesh();
		eM.polygons = eHsP;
		
		Vertex oV1, oV2, oV3;
		oV1 = new Vertex(2.0, 3.0, 4.0);
		oV2 = new Vertex(1.0, 2.0, 3.0);
		oV3 = new Vertex(4.0, 5.0, 1.0);
		LinkedHashSet<Vertex> oHs1, oHs2;
		oHs1 = new LinkedHashSet<Vertex>();
		oHs1.add(new Vertex(oV1.x, oV1.y, oV1.z));
		oHs1.add(new Vertex(oV2.x, oV2.y, oV2.z));
		oHs1.add(new Vertex(oV3.x, oV3.y, oV3.z));
		oHs2 = new LinkedHashSet<Vertex>();
		oHs2.add(new Vertex(oV1.x, oV1.y, oV1.z));
		oHs2.add(new Vertex(oV3.x, oV3.y, oV3.z));
		oHs2.add(new Vertex(oV2.x, oV2.y, oV2.z));
		Polygon oP1, oP2;
		oP1= new Polygon(oHs1);
		oP2 = new Polygon(oHs2);
		HashSet<Polygon> oHsP = new HashSet<Polygon>();
		oHsP.add(oP1);
		oHsP.add(oP2);
		Mesh oM = new Mesh();
		oM.polygons = oHsP;
		
		oM.rotateYAxis(0);
		oM.setWriter(new OBJMeshWriter());
		
		assertEquals(eM, oM);
	}
	@Test
	void testRotateZMesh() {
		Vertex eV1, eV2, eV3;
		eV1 = new Vertex(2.0, 3.0, 4.0);
		eV2 = new Vertex(1.0, 2.0, 3.0);
		eV3 = new Vertex(4.0, 5.0, 1.0);
		LinkedHashSet<Vertex> eHs1, eHs2;
		eHs1 = new LinkedHashSet<Vertex>();
		eHs1.add(eV1);
		eHs1.add(eV2);
		eHs1.add(eV3);
		eHs2 = new LinkedHashSet<Vertex>();
		eHs2.add(eV1);
		eHs2.add(eV3);
		eHs2.add(eV2);
		Polygon eP1, eP2;
		eP1 = new Polygon(eHs1);
		eP2 = new Polygon(eHs2);
		HashSet<Polygon> eHsP = new HashSet<Polygon>();
		eHsP.add(eP1);
		eHsP.add(eP2);
		Mesh eM = new Mesh();
		eM.polygons = eHsP;
		
		Vertex oV1, oV2, oV3;
		oV1 = new Vertex(2.0, 3.0, 4.0);
		oV2 = new Vertex(1.0, 2.0, 3.0);
		oV3 = new Vertex(4.0, 5.0, 1.0);
		LinkedHashSet<Vertex> oHs1, oHs2;
		oHs1 = new LinkedHashSet<Vertex>();
		oHs1.add(new Vertex(oV1.x, oV1.y, oV1.z));
		oHs1.add(new Vertex(oV2.x, oV2.y, oV2.z));
		oHs1.add(new Vertex(oV3.x, oV3.y, oV3.z));
		oHs2 = new LinkedHashSet<Vertex>();
		oHs2.add(new Vertex(oV1.x, oV1.y, oV1.z));
		oHs2.add(new Vertex(oV3.x, oV3.y, oV3.z));
		oHs2.add(new Vertex(oV2.x, oV2.y, oV2.z));
		Polygon oP1, oP2;
		oP1= new Polygon(oHs1);
		oP2 = new Polygon(oHs2);
		HashSet<Polygon> oHsP = new HashSet<Polygon>();
		oHsP.add(oP1);
		oHsP.add(oP2);
		Mesh oM = new Mesh();
		oM.polygons = oHsP;
		
		oM.rotateZAxis(0);
		oM.setWriter(new OBJMeshWriter());
		
		assertEquals(eM, oM);
	}
	
	// Testing for File Not Found.
	@Test
	void testNotFoundOBJ() {
		OBJMeshReader read = new OBJMeshReader();
		try {
			HashSet<Polygon> test = read.read("Not_A_File.obj");
		} catch(IOException e) {
			String expected = "Not_A_File.obj (The system cannot find the file specified)";
			assertEquals(e.getMessage(), expected);
			
		} catch(WrongFileFormatException e) {
			System.out.println(e.message);
			fail("Unexpected Exception.");
		}
	}
	@Test
	void testNotFoundPLY() {
		PLYMeshReader read = new PLYMeshReader();
		try {
			HashSet<Polygon> test = read.read("Not_A_File.ply");
		} catch(IOException e) {
			String expected = "Not_A_File.ply (The system cannot find the file specified)";
			assertEquals(e.getMessage(), expected);
			
		} catch(WrongFileFormatException e) {
			System.out.println(e.message);
			fail("Unexpected Exception.");
		}
	}
	@Test
	void testNotFoundOFF() {
		OFFMeshReader read = new OFFMeshReader();
		try {
			HashSet<Polygon> test = read.read("Not_A_File.off");
		} catch(IOException e) {
			String expected = "Not_A_File.off (The system cannot find the file specified)";
			assertEquals(e.getMessage(), expected);
			
		} catch(WrongFileFormatException e) {
			System.out.println(e.message);
			fail("Unexpected Exception.");
		}
	}
	
	// Testing For File Writing.
	@Test
	void testOBJWrite() {
		// Reading using OBJReader.
		Mesh m = new Mesh();
		m.setReader(new OBJMeshReader());
		m.setWriter(new OBJMeshWriter());
		try {
			m.readFromFile(directory+"/validOBJ1.obj");
			m.writeToFile(directory+"/outOBJ1.obj");
		} catch (IOException e) {
			System.out.println(e.getMessage());
			fail("Unexpected Exception");
		} catch (WrongFileFormatException e) {
			System.out.println(e.message);
			fail("Unexpected Exception");
		}
		// Checking if written properly.
		String expected = "v 1.5 2.5 3.5\n"
				+ "v 3.1 42.4 12.3\n"
				+ "v 5.1 52.0 21.1\n"
				+ "f 1 2 3\n"
				+ "";
		String actual = "";
		try {
			BufferedReader b = new BufferedReader(new FileReader(directory+"/outOBJ1.obj"));
			String line = b.readLine();
			while(line != null) {
				actual += line+"\n";
				line = b.readLine();
			}
			b.close();
			assertEquals(expected, actual);
		} catch (IOException e) {
			System.out.println(e.getMessage());
			fail("Unexpected Exception");
		}
		
	}
	@Test
	void testPLYWrite() {
		// Reading using OBJReader.
		Mesh m = new Mesh();
		m.setReader(new OBJMeshReader());
		m.setWriter(new PLYMeshWriter());
		try {
			m.readFromFile(directory+"/validOBJ1.obj");
			m.writeToFile(directory+"/outPLY1.ply");
		} catch (IOException e) {
			System.out.println(e.getMessage());
			fail("Unexpected Exception");
		} catch (WrongFileFormatException e) {
			System.out.println(e.message);
			fail("Unexpected Exception");
		}
		// Checking if written properly.
		String expected = "ply\n"
				+ "format ascii 1.0\n"
				+ "element vertex 3\n"
				+ "property float32 x\n"
				+ "property float32 y\n"
				+ "property float32 z\n"
				+ "element face 1\n"
				+ "property list uint8 int32 vertex_indices\n"
				+ "end_header\n"
				+ "1.5 2.5 3.5\n"
				+ "3.1 42.4 12.3\n"
				+ "5.1 52.0 21.1\n"
				+ "3 0 1 2\n"
				+ "";
		String actual = "";
		try {
			BufferedReader b = new BufferedReader(new FileReader(directory+"/outPLY1.ply"));
			String line = b.readLine();
			while(line != null) {
				actual += line+"\n";
				line = b.readLine();
			}
			b.close();
			assertEquals(expected, actual);
		} catch (IOException e) {
			System.out.println(e.getMessage());
			fail("Unexpected Exception");
		}
	}
	@Test
	void testOFFWrite() {
		// Reading using OBJReader.
		Mesh m = new Mesh();
		m.setReader(new OBJMeshReader());
		m.setWriter(new OFFMeshWriter());
		try {
			m.readFromFile(directory+"/validOBJ1.obj");
			m.writeToFile(directory+"/outOFF1.off");
		} catch (IOException e) {
			System.out.println(e.getMessage());
			fail("Unexpected Exception");
		} catch (WrongFileFormatException e) {
			System.out.println(e.message);
			fail("Unexpected Exception");
		}
		// Checking if written properly.
		String expected = "OFF\n"
				+ "3 1 0\n"
				+ "1.5 2.5 3.5\n"
				+ "3.1 42.4 12.3\n"
				+ "5.1 52.0 21.1\n"
				+ "3 0 1 2\n"
				+ "";
		String actual = "";
		try {
			BufferedReader b = new BufferedReader(new FileReader(directory+"/outOFF1.off"));
			String line = b.readLine();
			while(line != null) {
				actual += line+"\n";
				line = b.readLine();
			}
			b.close();
			assertEquals(expected, actual);
		} catch (IOException e) {
			System.out.println(e.getMessage());
			fail("Unexpected Exception");
		}	
	}
	
	// Test HashCode Functions.
	@Test
	void testMeshHash() {
		Mesh m = new Mesh();
		m.setReader(new OBJMeshReader());
		try {
			m.readFromFile(directory+"/validOBJ1.obj");
			assertEquals(m.hashCode(), 32900);
		} catch (IOException e) {
			System.out.println(e.getMessage());
			fail("Unexpected Exception");
		} catch (WrongFileFormatException e) {
			System.out.println(e.message);
			fail("Unexpected Exception");
		}
	}
	
	// Test Equals with wrong type
	@Test
	void testWrongInstanceVertex() {
		Vertex v = new Vertex(1.0, 1.0, 1.0);
		assertEquals(v.equals(new Mesh()), false);
	}
	@Test
	void testWrongInstancePolygon() {
		Vertex v = new Vertex(1.0, 1.0, 1.0);
		LinkedHashSet<Vertex> hs = new LinkedHashSet<Vertex>();
		Polygon p = new Polygon(hs);
		assertEquals(p.equals(new Mesh()), false);
	}
	@Test
	void testWrongInstanceMesh() {
		Mesh m = new Mesh();
		assertEquals(m.equals(new Vertex(1.0, 2.0, 3.0)), false);
	}
	
	// Testing for Index out of Bounds.
	@Test
	void testIndexOutOfBoundOBJ() {
		Mesh m = new Mesh();
		m.setReader(new OBJMeshReader());
		try {
			m.readFromFile(directory+"/indexOutOBJ1.obj");
		} catch (IOException e) {
			System.out.println(e.getMessage());
			fail("Unexpected Exception");
		} catch (WrongFileFormatException e) {
			assertEquals(e.message, "OBJ File is formatted incorrectly. Index of vertex is out of bounds.");
		}
	}
	@Test
	void testIndexOutOfBoundPLY() {
		Mesh m = new Mesh();
		m.setReader(new PLYMeshReader());
		try {
			m.readFromFile(directory+"/indexOutPLY1.ply");
		} catch (IOException e) {
			System.out.println(e.getMessage());
			fail("Unexpected Exception");
		} catch (WrongFileFormatException e) {
			assertEquals(e.message, "PLY File is formatted incorrectly. Index of vertex is out of bounds.");
		}
	}
	@Test
	void testIndexOutOfBoundOFF() {
		Mesh m = new Mesh();
		m.setReader(new OFFMeshReader());
		try {
			m.readFromFile(directory+"/indexOutOFF1.off");
		} catch (IOException e) {
			System.out.println(e.getMessage());
			fail("Unexpected Exception");
		} catch (WrongFileFormatException e) {
			assertEquals(e.message, "OFF File is formatted incorrectly. Index of vertex is out of bounds.");
		}
	}
	
	// Testing PLY Formatting.
	@Test
	void testInvalidVertexPLY() {
		Mesh m = new Mesh();
		m.setReader(new PLYMeshReader());
		try {
			m.readFromFile(directory+"/invalidVertexPLY.ply");
		} catch (IOException e) {
			System.out.println(e.getMessage());
			fail("Unexpected Exception");
		} catch (WrongFileFormatException e) {
			assertEquals(e.message, "PLY file is not formatted properly. 1.5 2s.5 3.5 is not a valid vertex.");
		}
	}
	@Test
	void testInvalidFacePLY() {
		Mesh m = new Mesh();
		m.setReader(new PLYMeshReader());
		try {
			m.readFromFile(directory+"/invalidFacePLY.ply");
		} catch (IOException e) {
			System.out.println(e.getMessage());
			fail("Unexpected Exception");
		} catch (WrongFileFormatException e) {
			assertEquals(e.message, "PLY file is not formatted properly. 3 0 1s 2 is not a valid face.");
		}
	}
	@Test
	void testHeaderWrongPLY() {
		Mesh m = new Mesh();
		m.setReader(new PLYMeshReader());
		try {
			m.readFromFile(directory+"/wrongHeaderPLY1.ply");
		} catch (IOException e) {
			System.out.println(e.getMessage());
			fail("Unexpected Exception");
		} catch (WrongFileFormatException e) {
			assertEquals(e.message, "PLY File is not formatted properly. Header is formatted incorrectly.");
		}
	}
	@Test
	void testHeaderPositionPLY() {
		Mesh m = new Mesh();
		m.setReader(new PLYMeshReader());
		try {
			m.readFromFile(directory+"/wrongHeaderPLY2.ply");
		} catch (IOException e) {
			System.out.println(e.getMessage());
			fail("Unexpected Exception");
		} catch (WrongFileFormatException e) {
			assertEquals(e.message, "PLY File is not formatted properly. Header is formatted incorrectly.");
		}
	}
	
	// Testing OBJ Formatting
	@Test
	void testEmptyOBJ() {
		Mesh m = new Mesh();
		m.setReader(new OBJMeshReader());
		try {
			m.readFromFile(directory+"/emptyOBJ.obj");
		} catch (IOException e) {
			System.out.println(e.getMessage());
			fail("Unexpected Exception");
		} catch (WrongFileFormatException e) {
			assertEquals(e.message, "OBJ File is formatted incorrectly. Empty file.");
		}
	}
	@Test
	void testInvalidVertexOBJ() {
		Mesh m = new Mesh();
		m.setReader(new OBJMeshReader());
		try {
			m.readFromFile(directory+"/invalidOBJ1.obj");
		} catch (IOException e) {
			System.out.println(e.getMessage());
			fail("Unexpected Exception");
		} catch (WrongFileFormatException e) {
			assertEquals(e.message, "OBJ File is formatted incorrectly. The first line: vw 1.5 2.5 3.5 is not a valid vertex.");
		}
	}
	@Test
	void testNoFacesOBJ() {
		Mesh m = new Mesh();
		m.setReader(new OBJMeshReader());
		try {
			m.readFromFile(directory+"/invalidOBJ2.obj");
		} catch (IOException e) {
			System.out.println(e.getMessage());
			fail("Unexpected Exception");
		} catch (WrongFileFormatException e) {
			assertEquals(e.message, "OBJ File is formatted incorrectly. There are no face lines in the file.");
		}
	}
	@Test
	void testInvalidFaceOBJ() {
		Mesh m = new Mesh();
		m.setReader(new OBJMeshReader());
		try {
			m.readFromFile(directory+"/invalidOBJ3.obj");
		} catch (IOException e) {
			System.out.println(e.getMessage());
			fail("Unexpected Exception");
		} catch (WrongFileFormatException e) {
			assertEquals(e.message, "OBJ File is formatted incorrectly. fs 1 2 3 is not a valid face.");
		}
	}
	
	
	// Testing OFF Formatting
	@Test
	void testEmptyOFF() {
		Mesh m = new Mesh();
		m.setReader(new OFFMeshReader());
		try {
			m.readFromFile(directory+"/emptyOFF.off");
		} catch (IOException e) {
			System.out.println(e.getMessage());
			fail("Unexpected Exception");
		} catch (WrongFileFormatException e) {
			assertEquals(e.message, "OFF File is formatted incorrectly. Empty file.");
		}
	}
	@Test
	void testNoOFF() {
		Mesh m = new Mesh();
		m.setReader(new OFFMeshReader());
		try {
			m.readFromFile(directory+"/noOFF.off");
		} catch (IOException e) {
			System.out.println(e.getMessage());
			fail("Unexpected Exception");
		} catch (WrongFileFormatException e) {
			assertEquals(e.message, "OFF File is formatted incorrectly.");
		}
	}
	@Test
	void testInvalidVertexOFF() {
		Mesh m = new Mesh();
		m.setReader(new OFFMeshReader());
		try {
			m.readFromFile(directory+"/invalidVertexOFF.off");
		} catch (IOException e) {
			System.out.println(e.getMessage());
			fail("Unexpected Exception");
		} catch (WrongFileFormatException e) {
			assertEquals(e.message, "OFF File is formatted incorrectly. 1.5s 2.5 3.5 is not a valid vertex.");
		}
	}
	@Test
	void testInvalidFaceOFF() {
		Mesh m = new Mesh();
		m.setReader(new OFFMeshReader());
		try {
			m.readFromFile(directory+"/invalidFaceOFF.off");
		} catch (IOException e) {
			System.out.println(e.getMessage());
			fail("Unexpected Exception");
		} catch (WrongFileFormatException e) {
			assertEquals(e.message, "OFF File is formatted incorrectly. 3 0 1 2s 220 220 200 is not a valid face.");
		}
	}
}
