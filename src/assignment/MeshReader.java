package assignment;

import java.util.HashSet;
import java.io.IOException;

public interface MeshReader {
	public HashSet<Polygon> read(String filename) throws IOException, WrongFileFormatException;
}
