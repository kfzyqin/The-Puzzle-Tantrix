package comp1110.ass2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * This class defines the tile used to play the game.
 * @author Zhenyue Ch'in, u5505995
 */

public class Tile {
	
	/* Define the thirteen tiles of this game 
	 * A: YRGGRY
	 * B: GRGYRY
	 * C: RGYGYR
	 * D: YGGRRY
	 * E: YRRGGY
	 * F: RYGGYR
	 * G: GYGRYR
	 * H: YRGRGY
	 * I: YGRGRY
	 * J: RGYYGR
	 * K: YGYRGR
	 * L: GRYRYG
	 * M: GYRYRG */
	
	private Edge[] edges = new Edge[6];
	private char tile;
	
	public Tile(char t, char r) {
		tile = t;
		if(t == 'A') {
			this.edges = new Edge[] {Edge.YELLOW, Edge.RED, Edge.GREEN, Edge.GREEN, Edge.RED, Edge.YELLOW};
			this.rotate(Character.getNumericValue(r));
		}
		
		if(t == 'B') {
			this.edges = new Edge[] {Edge.GREEN, Edge.RED, Edge.GREEN, Edge.YELLOW, Edge.RED, Edge.YELLOW};
			this.rotate(Character.getNumericValue(r));
		}
		
		if(t == 'C') {
			this.edges = new Edge[] {Edge.RED, Edge.GREEN, Edge.YELLOW, Edge.GREEN, Edge.YELLOW, Edge.RED};
			this.rotate(Character.getNumericValue(r));
		}
		
		if(t == 'D') {
			this.edges = new Edge[] {Edge.YELLOW, Edge.GREEN, Edge.GREEN, Edge.RED, Edge.RED, Edge.YELLOW};
			this.rotate(Character.getNumericValue(r));
		}
		
		if(t == 'E') {
			this.edges = new Edge[] {Edge.YELLOW, Edge.RED, Edge.RED, Edge.GREEN, Edge.GREEN, Edge.YELLOW};
			this.rotate(Character.getNumericValue(r));
		}
		
		if(t == 'F') {
			this.edges = new Edge[] {Edge.RED, Edge.YELLOW, Edge.GREEN, Edge.GREEN, Edge.YELLOW, Edge.RED};
			this.rotate(Character.getNumericValue(r));
		}
		
		if(t == 'G') {
			this.edges = new Edge[] {Edge.GREEN, Edge.YELLOW, Edge.GREEN, Edge.RED, Edge.YELLOW, Edge.RED};
			this.rotate(Character.getNumericValue(r));
		}
		
		if(t == 'H') {
			this.edges = new Edge[] {Edge.YELLOW, Edge.RED, Edge.GREEN, Edge.RED, Edge.GREEN, Edge.YELLOW};
			this.rotate(Character.getNumericValue(r));
		}
		
		if(t == 'I') {
			this.edges = new Edge[] {Edge.YELLOW, Edge.GREEN, Edge.RED, Edge.GREEN, Edge.RED, Edge.YELLOW};
			this.rotate(Character.getNumericValue(r));
		}
		
		if(t == 'J') {
			this.edges = new Edge[] {Edge.RED, Edge.GREEN, Edge.YELLOW, Edge.YELLOW, Edge.GREEN, Edge.RED};
			this.rotate(Character.getNumericValue(r));
		}
		
		if(t == 'K') {
			this.edges = new Edge[] {Edge.YELLOW, Edge.GREEN, Edge.YELLOW, Edge.RED, Edge.GREEN, Edge.RED};
			this.rotate(Character.getNumericValue(r));
		}
		
		if(t == 'L') {
			this.edges = new Edge[] {Edge.GREEN, Edge.RED, Edge.YELLOW, Edge.RED, Edge.YELLOW, Edge.GREEN};
			this.rotate(Character.getNumericValue(r));
		}
		
		if(t == 'M') {
			this.edges = new Edge[] {Edge.GREEN, Edge.YELLOW, Edge.RED, Edge.YELLOW, Edge.RED, Edge.GREEN};
			this.rotate(Character.getNumericValue(r));
		}
	}
	
	/**
	 * Rotate the card to the right by 60 degrees.  This means changing
	 * the order of the edges within the edge array.
	 */
	public void rotate(int times) {
		for (int i=0; i<times; i++) {
			Edge temp0 = edges[0];
			Edge temp1 = edges[1];
			Edge temp2 = edges[2];
			Edge temp3 = edges[3];
			Edge temp4 = edges[4];
			Edge temp5 = edges[5];
			
			edges[0] = temp5;
			edges[1] = temp0;
			edges[2] = temp1;
			edges[3] = temp2;
			edges[4] = temp3;
			edges[5] = temp4;
		}
	}
	
	/**
	 * Determine whether the card to the corresponding part of this one is
	 * compatible with it (do they make a pair?).
	 * No tile returns true
	 */
	public boolean compatibleRight(Tile right) {
		if (right == null) {
			return true;
		}
		
		if (this.edges[1] == right.edges[4]) {
			return true;
		}
		
		else {
			return false;
		}
	}
	
	
	public boolean compatibleLeft(Tile left) {
		if (left == null) {
			return true;
		}
		
		if (this.edges[4] == left.edges[1]) {
			return true;
		}
		
		else {
			return false;
		}
	}
	
	public boolean compatibleTopRight(Tile topRight) {
		if (topRight == null) {
			return true;
		}
		
		if (this.edges[0] == topRight.edges[3]) {
			return true;
		}
		
		else {
			return false;
		}
	}
	
	public boolean compatibleTopLeft(Tile topLeft) {
		if (topLeft == null) {
			return true;
		}
		
		if (this.edges[5] == topLeft.edges[2]) {
			return true;
		}
		
		else {
			return false;
		}
	}
	
	public boolean compatibleBottomRight(Tile bottomRight) {
		if (bottomRight == null) {
			return true;
		}
		
		if (this.edges[2] == bottomRight.edges[5]) {
			return true;
		}
		
		else {
			return false;
		}
	}
	
	public boolean compatibleBottomLeft(Tile bottomLeft) {
		if (bottomLeft == null) {
			return true;
		}
		
		if (this.edges[3] == bottomLeft.edges[0]) {
			return true;
		}
		
		else {
			return false;
		}
	}
	
	public Edge getEdge(int x) {
		return this.edges[x];
	}
	
	public char getTile() {
		return tile;
	}
	
}
