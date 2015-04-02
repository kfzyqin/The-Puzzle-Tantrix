package comp1110.ass2;

/**
 * Define the Edge
 * @author Zhenyue Ch'in, u5505995
 */

public enum Edge {
	/* Define each of the eight possible edges */
	YELLOW	(Color.Y),
	RED (Color.R),
	GREEN (Color.G);
		
	/* The color of the edge */
	private Color color;

	/**
	 * Constructor for an edge
	 * @param color The color of the edge
	 */
	Edge(Color aColor) {
		this.color = aColor;
	}
	
	/**
	 * Determine whether two edges are compatible
	 * @return true if this edge and the other edge are
	 * compatible
	 */
	public boolean Compatible(Edge other) {
		if (other.color == this.color) {
			return true;
		}
		
		else {
			return false;
		}
	}
	
	/* Pointless Just for debugging */
	@Override
	public String toString() {
		if (color == Color.Y) {
			return "Yellow";
		}
		
		if (color == Color.G) {
			return "Green";
		}
		
		else {
			return "Red";
		}
		
		
	}
}
