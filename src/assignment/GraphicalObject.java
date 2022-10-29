package assignment;

public abstract class GraphicalObject {
	// Void Methods.
	public abstract void transform(double [][] matrix);
	public void rotateXAxis(double theta) {
		double [][] matrix = {{1, 0, 0},
							  {0, Math.cos(theta), -(Math.sin(theta))},
							  {0, Math.sin(theta), Math.cos(theta)}};
		this.transform(matrix);
	}
	public void rotateYAxis(double theta) {
		double [][] matrix = {{Math.cos(theta), 0, Math.sin(theta)},
				  			  {0, 1, 0},
				  			  {-(Math.sin(theta)), 0, Math.cos(theta)}};
		this.transform(matrix);
	}
	public void rotateZAxis(double theta) {
		double [][] matrix = {{Math.cos(theta), -(Math.sin(theta)), 0},
				  			  {Math.sin(theta), Math.cos(theta), 0},
				  			  {0, 0, 1}};
		this.transform(matrix);
	}

}
