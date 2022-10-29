package assignment;

import java.util.HashSet;
import java.io.IOException;

public interface MeshWriter {
	public void write(String filename, HashSet<Polygon> polygons) throws IOException;
}
