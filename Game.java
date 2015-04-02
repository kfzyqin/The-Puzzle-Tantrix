package comp1110.ass2;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import org.junit.Test;

/**
 * This is the authorship for the code
 * @author Zhenyue Ch'in
 * solveMatch is written by Zhenyue Ch'in
 * isMatch is written by Zhenyue Ch'in
 * isLine and isLoop is written by Zhenyue Ch'in
 */

/* This class defines how the game runs */

public class Game implements Tantrix{
	/* Contains basic parameters of the game */
	public static int NUM_OF_POSITION = 26;
	public static int NUM_OF_TILES = 13;
	
	/* This is for javaFX to distinguish which playing method the player choose*/
	public char whichHint = 'H';
	
	/* This is for solveMatchS */
	private boolean[] used = new boolean[13];
	private ArrayList<String> correctSol = new ArrayList<String>();
	
	/* This is for isLine to count the number of tiles that the line goes through, the abbreviation of NumberOfThrough */
	private int NoThrough = 0;
	
	/* This is for isLoop to record the positions of the loop going through */
	private HashSet<Integer> loopPosition = new HashSet<Integer>();
	
	/*
	 * The main() method for our puzzle class.   
	 * It simply starts the graphical user interface (GUI)
	 * (Steve Blackburn)
	 */
	//H0K1B1L4G1M5A3J5D5F1I0C3E5
	//B0E2H4F4J3A2    I0  L1K3C3
	//DGM
	public static void main(String[] args) {
		Board.start();
		//Game omega = new Game();
		//System.out.println(omega.solveMatch(0));
		//System.out.println(omega.solvable("B0                        ".toCharArray(), "ACDEFGHIJKLM".toCharArray()));
		//System.out.println(omega.isLoop('R', "H0F4J3E1I4D3L3C0K0M3B0A3G3".toCharArray()));
	}
	
	/**
	 * According to the difficulty, randomly delete some tiles to correspond the difficulty
	 * @param difficulty The inputed difficulty
	 * @param wholeSol The completed solved solution
	 * @return The solution with some holes
	 * @author Zhenyue Ch'in, u5505995
	 */
	public char[] withDifficulty(double difficulty, char[] wholeSol) {
		/* Copy the solution in order not to change the previous one */
		char[] copySol = new char[NUM_OF_POSITION];
		for (int i=0; i<NUM_OF_POSITION; i++) {
			copySol[i] = wholeSol[i];
		}
		
		/* Calculate how many tiles need to be deleted according to the difficulty */
		int delete = ((int) ((difficulty/100.0)*13));
		
		/* Define the Random */
		Random r = new Random();
		
		/* Define the set to store the position of deleted tiles */
		HashSet<Integer> toDelete = new HashSet<Integer>();
		
		/* To generate the deleting positions*/
		while (toDelete.size() != delete) {	
			/* Generate the random position */
			int toAdd = r.nextInt(NUM_OF_POSITION);
				
			/* Make sure it is the tile not the rotation number */
			if (toAdd % 2 == 0) {
				toDelete.add(toAdd);
			}
		} 
		
		/* Delete the deleting positions */
		for (int i=0; i<NUM_OF_POSITION; i++) {
			if (toDelete.contains(i)) {
				copySol[i] = ' ';
				copySol[i+1] = ' ';
			}
		}
		
		return copySol;
	} 
	
	/**
	 * Solvable is to judge whether the problem is solvable for the currently placed tiles
	 * @param onBoard The char tiles on the Playground
	 * @param left The char tiles have not been used
	 * @return true if it is solvable
	 * @author Zhenyue Ch'in, u5505995
	 */
	public boolean solvable(char[] onBoard, char[] left) {
		/* Copy the onBoard */
		/* I found that if I directly use onBoard, the change will continue */
		char[] copyOnBoard = new char[26];
		for (int i=0;i<26; i++) {
			copyOnBoard[i] = onBoard[i];
		}
		
		/* The current onBoard is not match */
		if (!myIsMatch(onBoard)) {
			return false;
		}
		
		/* Find the solution */
		char[] record = new char[26];
		record = solvableCore(onBoard, left);
		
		/* No solution is found */
		if (record == null) {
			return false;
		}
		
		/* Find the uncorrect solution */
		else if (!isMatch(record)){
			return false;
		}
		
		/* Otherwise */
		else {
			for (int i=0; i<26; i++) {
				if (copyOnBoard[i] != ' ' && copyOnBoard[i] != record[i]) {
					return false;
				}
			}
			return true;
		}
	}
	
	/**
	 * Find the solution for the current state
	 * @param onBoard The char tiles on the Playground
	 * @param left The char tiles have not been used
	 * @return Solution or null
	 * @author Zhenyue Ch'in, u5505995
	 */
	public char[] solvableCore(char[] onBoard, char[] left) {
		/* Pointless */
		int onBoardLength = onBoard.length;
		
		/* How many tiles have not been used */
		int leftLength = left.length;
		
		/* remain is the tile have not been used, will change by the procedure */
		/* remain2 does not change */
		ArrayList<Character> remain = new ArrayList<Character>();
		ArrayList<Character> remain2 = new ArrayList<Character>();
		
		/* Stop is pointless */
		ArrayList<Character> stop = new ArrayList<Character>();
		
		/* failed consists of all the failed situation */
		HashSet<String> failed = new HashSet<String>();
		
		/* Initialise remain and remain2 */
		for (int i=0; i<leftLength; i++) {
			remain.add(left[i]);
			remain2.add(left[i]);
		}
		
		/* Start to put */
		for (int i=0; i<NUM_OF_POSITION; i=i+2) {
			for (int p=0; p<26; p=p+2) {
				/* Make sure reamin consists all the tiles that have not been used */
				if (!remain.contains(onBoard[p]) && onBoard[p] != ' ') {
					remain.add(onBoard[p]);
				}
			}
			
			/* Find the hole! */
			if (onBoard[i] == ' ') {
				/* Try every tile that has not been used */
				for (int j=0; j<remain.size(); j++) {
					/* Try every rotation */
					for (int k=0; k<6; k++) {
						/* Put them in */
						onBoard[i] = remain.get(j);
						onBoard[i+1] = convertToChar(k);
						
						/* These for debuging */
						//System.out.println("chin");
						//System.out.println(onBoard);
						//System.out.print("1: ");
						//System.out.println(myIsMatch(onBoard));
						//System.out.print("2: ");
						//System.out.println(!failed.contains(onBoard));
						
						/* Make sure this is not the previous failed situation */
						/* For now it is temporarily matched, no more rotation */
						String temp1 = String.valueOf(onBoard);
						if (myIsMatch(onBoard) && !failed.contains(temp1)) {
							break;
						}
					}
					
					/* Make sure this is not the previous failed situation */
					/* For now it is temporarily matched, no more picking the unused tile*/
					String temp2 = String.valueOf(onBoard);
					if (myIsMatch(onBoard) && !failed.contains(temp2)) {
						remain.remove(j);
						break;
					}
					
					/* This tile is not suitable */
					else {
						/* For debugging */
						//System.out.println(remain);
						
						/* Try next tile remained */
						continue;
					}
				}
				
				/* Make sure this is not the previous failed situation */
				/* For now it is temporarily matched, find the next hole*/
				String temp3 = String.valueOf(onBoard);
				if (myIsMatch(onBoard) && !failed.contains(temp3)) {
					continue;
				}
				
				/* No remained tile is suitable, back tracking now */
				else {
					/* In case that we get the solution */
					if (isMatch(onBoard)) {
						return onBoard;
					}
					
					/* pointless */
					stop.add(onBoard[i-2]);
					stop.add(onBoard[i-1]);
					
					/* Put the previous tiles back to the remain */
					if (!remain.contains(onBoard[i])) {
						remain.add(onBoard[i]);
					}
					
					if (!remain.contains(onBoard[i-2])) {
						remain.add(onBoard[i-2]);
					}
					
					/* Empty the wrong tile */
					onBoard[i] = ' ';
					onBoard[i+1] = ' ';
					
					/* Record the wrong case */
					String temp = String.valueOf(onBoard);
					failed.add(temp);
					
					/* Empty the wrong tile */
					onBoard[i-2] = ' ';
					onBoard[i-1] = ' ';
					
					/* Back Tracking */
					i = i-4;
				}
				
			}
		}
		return onBoard;
		
		/*
		catch (IndexOutOfBoundsException e) {
			return e.toString().toCharArray();
		}
		*/
	
	}
	
	
	/**
	 * To solve a solution for isMatch
	 * @param difficulty The pointless inputed difficulty
	 * @author Zhenyue Ch'in, u5505995
	 * P.S. Discussed with Sakda Eamkulworapong without code sharing, get the randomly picking idea
	 */
	@Override
	public char[] solveMatch(double difficulty) {
		/* If the return result is not null, do it again */
		char[] result = new char[NUM_OF_POSITION];
		do {
			result = solveMatchCore(difficulty);
		} while (result == null);
		return result;
	}
	
	/**
	 * The core thing for solveMatch with random permutation
	 * @param difficulty the pointless inputed difficulty
	 * @return the completed solution or null
	 * @author Zhenyue Ch'in, u5505995
	 * P.S. Discussed with Sakda Eamkulworapong,and  without code sharing, got the randomly picking idea
	 */
	public char[] solveMatchCore(double difficulty) {
		/* The toJudge is the current uncompleted solution, maybe not solvable */
		char[] toJudge = {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
		
		/* The notUsed is the tiles have not been used */
		char[] notUsed = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M'};
		
		/* Define the random */
		Random rand = new Random();
		
		/* The numLeft is the number that how many tiles have not been used */
		int numLeft = NUM_OF_TILES;
		
		/* Throught the loop to put the tiles into the solution and judge it */
		for (int i=0; i<NUM_OF_TILES; i++) {
			/* Randomly pick one tile position*/
			int next = rand.nextInt(numLeft);
				
			/* Find the tile in the corresponding position */
			char start = notUsed[next];
			
			/* Delete the picked tile from the not-used */
			/* Take　over the position with the last tile */
			notUsed[next] = notUsed[numLeft-1];
			notUsed[numLeft-1] = ' ';
			
			/* Put the picked one into the solution */
			toJudge[i*2] = start;
			numLeft--;
			
			/* Rotate the picked tile */
			for (int j=0; j<6; j++) {
				toJudge[i*2 + 1] = convertToChar(j);
				/* judge whether the rotation is accepted */
				/* If so, use this rotation and break the rotation loop */
				if (myIsMatch(toJudge)) {
					break;
				}
			}
			
			/* The current solution is not solvable */
			if (!myIsMatch(toJudge)) {
				return null;
			}
		}
		return toJudge;
	}
	
	/**
	 * The core thing for solveLine with random permutation
	 * @param color The color for the line
	 * @param difficulty The pointless input difficulty
	 * @author Zhenyue Ch'in, u5505995
	 * P.S. Discussed with Sakda Eamkulworapong without code sharing, got the counting head idea
	 */
	@Override
	public char[] solveLine(char color, double difficulty) {	
		/* Define the random */
		Random r = new Random();
		
		/* Define the char array to store the result or null */
		char[] result = new char[NUM_OF_POSITION];
		
		/* Make sure the solved line is not null */
		do {
			result = solveLineCore(color, difficulty);
		} while (result == null); 
		return result;
		
	}
	
	/**
	 * The core for solveLine If it is a line
	 * If it is a line, the number of line heads on the Border has to be less than 2
	 * @param color The color for the line
	 * @param difficulty The pointless input difficulty
	 * @return the completed solution or null
	 * @author Zhenyue Ch'in, u5505995
	 * P.S. Discussed with Sakda Eamkulworapong without code sharing, got the counting head idea
	 */
	public char[] solveLineCore(char color, double difficulty) {
		/* The toJudge is the current uncompleted solution, maybe not solvable */
		char[] toJudge = {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
		
		/* The notUsed is the tiles have not been used */
		char[] notUsed = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M'};	
		
		/* Define the random */
		Random rand = new Random();
		
		/* The number of heads of the line on the border */
		/* The head is the new number of head */
		int head = 0;
		
		/* The anoHead is the previous total head */
		int anoHead = 0;		
		
		/* Create the array of Tiles to convert the char array of tiles to Tile array */
		Tile[] storeTile = new Tile[NUM_OF_TILES];		
		
		/* Translate the char of color to the corresponding edge */
		Edge lineColor;
		if (color == 'R') lineColor = Edge.RED;		
		else if (color == 'G') lineColor = Edge.GREEN;
		else lineColor = Edge.YELLOW;
			
		/* The numLeft is the number that how many tiles have not been used */
		int numLeft = NUM_OF_TILES; 
			
		/* This loop is used to fill every position of the tile, i is the position of the tile board */
		for (int i=0; i<NUM_OF_TILES; i++) {
			/* Randomly pick one tile position*/
			int next = rand.nextInt(numLeft);
			
			/* Find the tile in the corresponding position */
			char start = notUsed[next];
			
			/* Delete the picked tile from the not-used */
			/* Take　over the position with the last tile */
			notUsed[next] = notUsed[numLeft-1]; 
			toJudge[i*2] = start;
			numLeft--;
			
			/* Record the previous number of line head */
			anoHead = head;
			
			/* If the current solution is not solvable, this one is to record the last position for that solution */
			int backNext = 0;
				
			/* Rotate the picked rotation */
			for (int j=0; j<6; j++) {
				/* This head is to record the new generated head */
				head = 0;
				
				/* Convert the rotation to char */
				toJudge[i*2 + 1] = (char) (j+'0'); 
				
				/* Convert the present char tile into the tile with tile type */
				storeTile[i] = singleCharToTile(start, toJudge[i*2 + 1]); 
				
				/* Count the new tile's line head and add it with the previous number of head */
				head = countBorder(storeTile, lineColor, i) + anoHead; 
				
				/* the added result's line head is less than 2 and it is temporarily matched */
				if (head <= 2 && myIsMatch(toJudge)) {
					break; 
				}	
			}
			
			/* Otherwise, which means the current solution is not solvable */
			if (head > 2 || !myIsMatch(toJudge)) {
				
				/* This loop is to try all the not-used tiles in that location to remedy */
				for (int k=0; k<numLeft; k++) {				
					/* backNext is the position of picked remedying tile */
					backNext = k;
					
					/* This loop is to rotate */
					for (int l=0; l<6; l++) {
						/* The backStart is the picked remedying tile */
						char backStart = notUsed[k];
						
						/* Put the remedying tile into the solution */
						toJudge[i*2] = backStart;
						
						/* This head is to record the new generated head */
						head = 0;
						
						/* Convert the rotation to char */
						toJudge[i*2 + 1] = (char) (l+'0');
						
						/* Count the new tile's line head and add it with the previous number of head */
						storeTile[i] = singleCharToTile(backStart, toJudge[i*2 + 1]);
						
						/* Count the new tile's line head and add it with the previous number of head */
						head = countBorder(storeTile, lineColor, i) + anoHead; 
						
						/* the added result's line head is less than 2 and it is temporarily matched, which means remedying is successful*/
						if (head <= 2 && myIsMatch(toJudge)) {
							break; 
						}
					}
				}
				
				/* the added result's line head is less than 2 and it is temporarily matched, which means remedying is successful*/
				if (head <=2 && myIsMatch(toJudge)) {
					/* Mark the picked tile before remedying as notUsed */
					notUsed[backNext] = start;
				}
				
				/* Remedying failed */
				else {
					return null;
				}
			}
		}
		
		/* Sometimes, there is a loop and a line, which is not a line. Therefore, isLine is needed to make sure it is a line */
		if (isLine(color, toJudge)) {
			return toJudge; // success
		}
		return null;
	}
	
	/**
	 * The core for solveLoop If it is a line
	 * If it is a line, the number of line heads on the Border has to be 0
	 * Just change the number of line head in solveLine to 0, with nothing new, therefore, there is no new comments
	 * Please refer to the comment in the solveLine
	 * Can only solve red loop (not due to the code but the rule of the game)
	 * @param color The color for the line
	 * @param difficulty The pointless input difficulty
	 * @author Zhenyue Ch'in, u5505995
	 */
	public char[] solveLoop(char color, double difficulty) {
		char[] result = new char[NUM_OF_POSITION];
		do {
			result = solveLoopCore(color, difficulty);
		} while (result == null);
		return result;
	}
	
	/**
	 * The core thing for solveLoop with random permutation
	 * @param color
	 * @param difficulty
	 * @author Zhenyue Ch'in, u5505995
	 * P.S. Discussed with Sakda Eamkulworapong without code sharing, got the counting head idea
	 */
	public char[] solveLoopCore(char color, double difficulty) {
		/* The toJudge is the current uncompleted solution, maybe not solvable */
		char[] toJudge = {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
		
		/* The notUsed is the tiles have not been used */
		char[] notUsed = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M'};	
		
		/* Define the random */
		Random rand = new Random();
		
		/* The number of heads of the line on the border */
		/* The head is the new number of head */
		int head = 0;
		
		/* The anoHead is the previous total head */
		int anoHead = 0;		
		
		/* Create the array of Tiles to convert the char array of tiles to Tile array */
		Tile[] storeTile = new Tile[NUM_OF_TILES];		
		
		/* Translate the char of color to the corresponding edge */
		Edge lineColor;
		if (color == 'R') lineColor = Edge.RED;		
		else if (color == 'G') lineColor = Edge.GREEN;
		else lineColor = Edge.YELLOW;
			
		/* The numLeft is the number that how many tiles have not been used */
		int numLeft = NUM_OF_TILES; 
			
		/* This loop is used to fill every position of the tile, i is the position of the tile board */
		for (int i=0; i<NUM_OF_TILES; i++) {
			/* Randomly pick one tile position*/
			int next = rand.nextInt(numLeft);
			
			/* Find the tile in the corresponding position */
			char start = notUsed[next];
			
			/* Delete the picked tile from the not-used */
			/* Take　over the position with the last tile */
			notUsed[next] = notUsed[numLeft-1]; 
			toJudge[i*2] = start;
			numLeft--;
			
			/* Record the previous number of line head */
			anoHead = head;
			
			/* If the current solution is not solvable, this one is to record the last position for that solution */
			int backNext = 0;
				
			/* Rotate the picked rotation */
			for (int j=0; j<6; j++) {
				/* This head is to record the new generated head */
				head = 0;
				
				/* Convert the rotation to char */
				toJudge[i*2 + 1] = (char) (j+'0'); 
				
				/* Convert the present char tile into the tile with tile type */
				storeTile[i] = singleCharToTile(start, toJudge[i*2 + 1]); 
				
				/* Count the new tile's line head and add it with the previous number of head */
				head = countBorder(storeTile, lineColor, i) + anoHead; 
				
				/* the added result's line head is less than 2 and it is temporarily matched */
				if (head == 0 && myIsMatch(toJudge)) {
					break; 
				}	
			}
			
			/* Otherwise, which means the current solution is not solvable */
			if (head > 0 || !myIsMatch(toJudge)) {
				
				/* This loop is to try all the not-used tiles in that location to remedy */
				for (int k=0; k<numLeft; k++) {				
					/* backNext is the position of picked remedying tile */
					backNext = k;
					
					/* This loop is to rotate */
					for (int l=0; l<6; l++) {
						/* The backStart is the picked remedying tile */
						char backStart = notUsed[k];
						
						/* Put the remedying tile into the solution */
						toJudge[i*2] = backStart;
						
						/* This head is to record the new generated head */
						head = 0;
						
						/* Convert the rotation to char */
						toJudge[i*2 + 1] = (char) (l+'0');
						
						/* Count the new tile's line head and add it with the previous number of head */
						storeTile[i] = singleCharToTile(backStart, toJudge[i*2 + 1]);
						
						/* Count the new tile's line head and add it with the previous number of head */
						head = countBorder(storeTile, lineColor, i) + anoHead; 
						
						/* the added result's line head is less than 2 and it is temporarily matched, which means remedying is successful*/
						if (head == 0 && myIsMatch(toJudge)) {
							break; 
						}
					}
				}
				
				/* the added result's line head is less than 2 and it is temporarily matched, which means remedying is successful*/
				if (head == 0 && myIsMatch(toJudge)) {
					/* Mark the picked tile before remedying as notUsed */
					notUsed[backNext] = start;
				}
				
				/* Remedying failed */
				else {
					return null;
				}
			}
		}
		/* Sometimes, there is a loop and a line, which is not a line. Therefore, isLine is needed to make sure it is a line */
		if (isLoop(color, toJudge)) {
			return toJudge; // success
		}
		return null;
	}

	/** 
	 * isMatch is to Check whether the solution is matched or not
	 * @param tiles The char array of tiles to check
	 * @author Zhenyue Ch'in, u5505995
	 */
	@Override
	public boolean isMatch(char[] tiles) {
		/* There is no solution to check */
		if (tiles == null) return false;
		
		/* There are 12 positions to be check, the recordTrue is to record every position is matched or not */
		boolean[] recordTrue = new boolean[NUM_OF_TILES];
		
		/* Initializing: to give the beginning value true */
		for (int i=0; i<NUM_OF_TILES; i++) recordTrue[i] = true;
		
		/* Initializing: change the array of chars to the array of tiles */
		Tile[] storeTile = new Tile[NUM_OF_TILES];
		for (int i=0; i<NUM_OF_POSITION; i=i+2) {
			if (tiles[i] == 'A') storeTile[i/2] = new Tile('A', tiles[i+1]);
			if (tiles[i] == 'B') storeTile[i/2] = new Tile('B', tiles[i+1]);
			if (tiles[i] == 'C') storeTile[i/2] = new Tile('C', tiles[i+1]);		
			if (tiles[i] == 'D') storeTile[i/2] = new Tile('D', tiles[i+1]);
			if (tiles[i] == 'E') storeTile[i/2] = new Tile('E', tiles[i+1]);
			if (tiles[i] == 'F') storeTile[i/2] = new Tile('F', tiles[i+1]);		
			if (tiles[i] == 'G') storeTile[i/2] = new Tile('G', tiles[i+1]);	
			if (tiles[i] == 'H') storeTile[i/2] = new Tile('H', tiles[i+1]);		
			if (tiles[i] == 'I') storeTile[i/2] = new Tile('I', tiles[i+1]);		
			if (tiles[i] == 'J') storeTile[i/2] = new Tile('J', tiles[i+1]);
			if (tiles[i] == 'K') storeTile[i/2] = new Tile('K', tiles[i+1]);
			if (tiles[i] == 'L') storeTile[i/2] = new Tile('L', tiles[i+1]);
			if (tiles[i] == 'M') storeTile[i/2] = new Tile('M', tiles[i+1]);
			if (tiles[i] == ' ') storeTile[i/2] = null;
		}
		
			/* According to the rule of game, check every position */
			/* No position can be null */
			if (storeTile[0] == null) recordTrue[0] = false;			
			else recordTrue[0] = storeTile[0].compatibleRight(storeTile[1]) 
					&& storeTile[0].compatibleBottomLeft(storeTile[4]) 
					&& storeTile[0].compatibleBottomRight(storeTile[5]);
			
			if (storeTile[1] == null) recordTrue[1] = false;			
			else recordTrue[1] = storeTile[1].compatibleRight(storeTile[2]) 
					&& storeTile[1].compatibleBottomLeft(storeTile[5]) 
					&& storeTile[1].compatibleBottomRight(storeTile[6]);
			
			if (storeTile[2] == null) recordTrue[2] = false;
			else recordTrue[2] = storeTile[2].compatibleRight(storeTile[3]) 
					&& storeTile[2].compatibleBottomLeft(storeTile[6]) 
					&& storeTile[2].compatibleBottomRight(storeTile[7]);
						
			if (storeTile[3] == null) recordTrue[3] = false;		
			else recordTrue[3] = storeTile[3].compatibleBottomLeft(storeTile[7]) 
					&& storeTile[3].compatibleBottomRight(storeTile[8]);
			
			if (storeTile[4] == null) recordTrue[4] = false;			
			else recordTrue[4] = storeTile[4].compatibleRight(storeTile[5]) 
					&& storeTile[4].compatibleBottomRight(storeTile[9]);
			
			if (storeTile[5] == null) recordTrue[5] = false;
			else recordTrue[5] = storeTile[5].compatibleRight(storeTile[6]) 
					&& storeTile[5].compatibleBottomLeft(storeTile[9]) 
					&& storeTile[5].compatibleBottomRight(storeTile[10]);
			
			if (storeTile[6] == null) recordTrue[6] = false;
			else recordTrue[6] = storeTile[6].compatibleRight(storeTile[7]) 
					&& storeTile[6].compatibleBottomLeft(storeTile[10]) 
					&& storeTile[6].compatibleBottomRight(storeTile[11]);
			
			
			if (storeTile[7] == null) recordTrue[7] = false;
			else recordTrue[7] = storeTile[7].compatibleRight(storeTile[8]) 
					&& storeTile[7].compatibleBottomLeft(storeTile[11]) 
					&& storeTile[7].compatibleBottomRight(storeTile[12]);
			
			if (storeTile[8] == null) recordTrue[8] = false;			
			else recordTrue[8] = storeTile[8].compatibleBottomLeft(storeTile[12]);
			
			if (storeTile[9] == null) recordTrue[9] = false;
			else recordTrue[9] = storeTile[9].compatibleRight(storeTile[10]);
			
			if (storeTile[10] == null) recordTrue[10] = false;			
			else recordTrue[10] = storeTile[10].compatibleRight(storeTile[11]);
			
			if (storeTile[11] == null) recordTrue[11] = false;
			else recordTrue[11] = storeTile[11].compatibleRight(storeTile[12]);
			
			if (storeTile[12] == null) recordTrue[12] = false;
					
			/* This loop is to promise every position is matched */
			for (int i=0; i<NUM_OF_TILES; i++) {
				if (recordTrue[i]==false)
					return false;
				}
			return true;	
	}
	
	/**
	 * isLine is to check whether the solution is line or not
	 * The idea is to trace the line
	 * @param color The color of the line
	 * @param tiles The char array of tiles to be check
	 * @author Zhenyue Ch'in, u5505995
	 */
	@Override
	public boolean isLine(char color, char[] tiles) {
		/* lineColor is the lineColor with the type of Edge */
		Edge lineColor;
		
		/* The startTile is to record the position that there is line head in that tile */
		int startTile = 0;
		
		/* The entrance is to record the number of edge that there exists line head */
		int entrance = 0;
		
		/* x is the number that how many positions the line has passed */
		int x = 0;
		
		/* If the soltion is not completed, return false */
		for (int i=0; i<NUM_OF_POSITION; i++) {
			if (tiles[i] == ' ') {
				return false;
			}
		}
		
		/* initializing: change the array of chars to the array of tiles  */
		Tile[] storeTile = new Tile[NUM_OF_TILES];
		for (int i=0; i<NUM_OF_POSITION; i=i+2) {
			if (tiles[i] == 'A') storeTile[i/2] = new Tile('A', tiles[i+1]);
			if (tiles[i] == 'B') storeTile[i/2] = new Tile('B', tiles[i+1]);
			if (tiles[i] == 'C') storeTile[i/2] = new Tile('C', tiles[i+1]);		
			if (tiles[i] == 'D') storeTile[i/2] = new Tile('D', tiles[i+1]);
			if (tiles[i] == 'E') storeTile[i/2] = new Tile('E', tiles[i+1]);
			if (tiles[i] == 'F') storeTile[i/2] = new Tile('F', tiles[i+1]);		
			if (tiles[i] == 'G') storeTile[i/2] = new Tile('G', tiles[i+1]);	
			if (tiles[i] == 'H') storeTile[i/2] = new Tile('H', tiles[i+1]);		
			if (tiles[i] == 'I') storeTile[i/2] = new Tile('I', tiles[i+1]);		
			if (tiles[i] == 'J') storeTile[i/2] = new Tile('J', tiles[i+1]);
			if (tiles[i] == 'K') storeTile[i/2] = new Tile('K', tiles[i+1]);
			if (tiles[i] == 'L') storeTile[i/2] = new Tile('L', tiles[i+1]);
			if (tiles[i] == 'M') storeTile[i/2] = new Tile('M', tiles[i+1]);
		}
		
		/* Red is the chosen color */
		if (color == 'R') {			
			/* Set the lineColor to Red */
			lineColor = Edge.RED;
			
			/* Find the position of tile with line head */
			for (int i=0; i<NUM_OF_TILES; i++) {
				if (countBorder(storeTile, lineColor, i) >= 1) {
					startTile = i;
					break;
				}
			}
			
			/* Find which edge consists the line head */
			entrance = whichEdgeStart(storeTile, lineColor, startTile);
			
			/* From here, start recursion */
			/* The recursion is to find how many locations the line has passed */
			x = recursiveIsLine(storeTile, entrance, startTile, lineColor);
			
			/* If the line has passed 12 positions, which is all, return true */
			if (x == 12) {
				NoThrough = 0;
				return true;
			}
			
			/* Otherwise return false */
			else {
				NoThrough = 0;
				return false;
			}
		}
		
		/* The other two color is just the same idea */
		if (color == 'G') {
			lineColor = Edge.GREEN;
			for (int i=0; i<NUM_OF_TILES; i++) {
				if (countBorder(storeTile, lineColor, i) >= 1) {
					startTile = i;
					break;
				}
			}
			entrance = whichEdgeStart(storeTile, lineColor, startTile);
			x = recursiveIsLine(storeTile, entrance, startTile, lineColor);
			if (x == 12) {
				NoThrough = 0;
				return true;
			}
			else {
				NoThrough = 0;
				return false;
			}
		}
		
		if (color == 'Y') {
			lineColor = Edge.YELLOW;
			for (int i=0; i<NUM_OF_TILES; i++) {
				if (countBorder(storeTile, lineColor, i) >= 1) {
					startTile = i;
					break;
				}
			}
			entrance = whichEdgeStart(storeTile, lineColor, startTile);
			x = recursiveIsLine(storeTile, entrance, startTile, lineColor);
			if (x == 12) {
				NoThrough = 0;
				return true;
			}
			else {
				NoThrough = 0;
				return false;
			}
		}
		return true;
	}
	
	/**
	 * The recursive part of isLine
	 * @param tiles All the tiles
	 * @param start A tile has two edges which has the line color. This parameter is to record the one that the line firstly passes  
	 * @param tilePosition The coordinate of the current tile in the playGround
	 * @param lineColor The color of the Line
	 * @return How many positions the line has passed
	 * @author Zhenyue Ch'in, u5505995
	 */
	public int recursiveIsLine(Tile[] tiles, int start, int tilePosition, Edge lineColor) {
		/* Come across loop must jump out */
		if (NoThrough > NUM_OF_TILES) {
			return NoThrough;
		}
		
		/* The connection is the edge that the line passes */
		int connection = 0;
		
		/* The howFar is the distance from the current tile to the next tile */
		int howFar = 0;
		
		/* The position of the tile that the line will pass next */
		int next = 0;
		
		/* The next tile's edge that the line will come in */
		int nextStart = 0;
		
		/* Find the edge having the same color with the current edge in the same tile */
		for (int i=0; i<6; i++) {
			if (tiles[tilePosition].getEdge(i) == lineColor && (i != start)) {
				connection = i;
				break;
			}
		}
		
		/* The border that the line can come out */
		if (connection == 0 && (tilePosition == 8)) return NoThrough;
		if (connection == 3 && (tilePosition == 4)) return NoThrough;	
		if (connection == 4 && (tilePosition == 4)) return NoThrough;
		if (connection == 4 && (tilePosition == 9)) return NoThrough;
		if ((connection == 1) && (tilePosition == 3) || (connection == 1) && (tilePosition == 8) || (connection == 1) && (tilePosition == 12)) 
			return NoThrough;
		
		/* According to the location of the Edge, find the distance to the next Edge */
		if (connection == 0) {
			howFar = -4;
			nextStart = 3;
		}
		
		if (connection == 1) {
			howFar = 1;
			nextStart = 4;
		}
		
		if (connection == 2) {
			howFar = 5;
			nextStart = 5;
		}
		
		if (connection == 3) {
			howFar = 4;
			nextStart = 0;
		}
		
		if (connection == 4) {
			howFar = -1;
			nextStart = 1;
		}
		
		if (connection == 5) {
			howFar = -5;
			nextStart = 2;
		}
		
		/* The position of the next tile that the line will pass */
		next = tilePosition + howFar;
		
		/* Keep tracing the line */
		if (next>=0 && next<=12) {
			NoThrough++; 
			recursiveIsLine(tiles, nextStart, next, lineColor);
		}
		
		return NoThrough;
	}
	
	/**
	 * isLoop is to check whether the solution is a loop or not
	 * It is based on the isLine
	 * @param color The color of the loop
	 * @param tiles The provided solution
	 * @author Zhenyue Ch'in, u5505995
	 */
	@Override
	public boolean isLoop(char color, char[] tiles) {
		/* Empty the previous trails */
		loopPosition.clear();
		
		/* Come across loop must jump out */
		for (int i=0; i<NUM_OF_POSITION; i++) {
			if (tiles[i] == ' ') {
				return false;
			}
		}
		
		/* initializing: change the array of chars to the array of tiles  */
		Tile[] storeTile = new Tile[NUM_OF_TILES];
		for (int i=0; i<NUM_OF_POSITION; i=i+2) {
			if (tiles[i] == 'A') storeTile[i/2] = new Tile('A', tiles[i+1]);
			if (tiles[i] == 'B') storeTile[i/2] = new Tile('B', tiles[i+1]);
			if (tiles[i] == 'C') storeTile[i/2] = new Tile('C', tiles[i+1]);		
			if (tiles[i] == 'D') storeTile[i/2] = new Tile('D', tiles[i+1]);
			if (tiles[i] == 'E') storeTile[i/2] = new Tile('E', tiles[i+1]);
			if (tiles[i] == 'F') storeTile[i/2] = new Tile('F', tiles[i+1]);		
			if (tiles[i] == 'G') storeTile[i/2] = new Tile('G', tiles[i+1]);	
			if (tiles[i] == 'H') storeTile[i/2] = new Tile('H', tiles[i+1]);		
			if (tiles[i] == 'I') storeTile[i/2] = new Tile('I', tiles[i+1]);		
			if (tiles[i] == 'J') storeTile[i/2] = new Tile('J', tiles[i+1]);
			if (tiles[i] == 'K') storeTile[i/2] = new Tile('K', tiles[i+1]);
			if (tiles[i] == 'L') storeTile[i/2] = new Tile('L', tiles[i+1]);
			if (tiles[i] == 'M') storeTile[i/2] = new Tile('M', tiles[i+1]);
		}
		
		/* The color of the loop */
		Edge loopColor;
		
		/* The startTile is to record the position that there is line head in that tile */
		int startTile = 0;
		
		/* The entrance is to record the number of edge that there exists line head */
		int entrance = 0;
		
		/* The side effect of isLine */
		int x =0;
		
		/* Red is the choose color */
		if (color == 'R') {
			/* Set the loopColor to RED */
			loopColor = Edge.RED;
			
			/* Find the edge with the color of red in the first position */
			for (int i=0; i<6; i++) {
				if (storeTile[0].getEdge(i) == loopColor) {
					entrance = i;
					break;
				}
			}
			
			/* Start recursive tracing */
			x = recursiveIsLoop(storeTile, entrance, startTile, loopColor);
			
			/* The loop has passed all the tiles */
			/* Make sure there is no line head */
			int head2 = 0;
			for (int i=0; i<13; i++) {
				head2 = head2 + countBorder(storeTile, loopColor, i);
			}
			
			if (loopPosition.size() == NUM_OF_TILES && head2 == 0) {
				NoThrough = 0;
				return true;
			}
			
			/* Otherwise */
			else {
				NoThrough = 0;
				return false;
			}
		}
		
		/* The other color */
		/* WARNNING: ONLY RED COLOR IS VALID */
		if (color == 'G') {
			loopColor = Edge.GREEN;
			
			for (int i=0; i<6; i++) {
				if (storeTile[0].getEdge(i) == loopColor) {
					entrance = i;
					break;
				}
			}
			x = recursiveIsLoop(storeTile, entrance, startTile, loopColor);
			if (loopPosition.size() == NUM_OF_TILES) {
				NoThrough = 0;
				return true;
			}
			else {
				NoThrough = 0;
				return false;
			}
		}
		
		if (color == 'Y') {
			loopColor = Edge.YELLOW;
			
			for (int i=0; i<6; i++) {
				if (storeTile[0].getEdge(i) == loopColor) {
					entrance = i;
					break;
				}
			}
			x = recursiveIsLoop(storeTile, entrance, startTile, loopColor);
			
			int head = 0;
			
			if (loopPosition.size() == NUM_OF_TILES) {
				NoThrough = 0;
				return true;
			}
			else {
				NoThrough = 0;
				return false;
			}
		}
		return false;
	}
	
	/**
	 * The recursive part of isLoop based on isLine
	 * The core is the Set of loopPosition
	 * @param tiles All the tiles
	 * @param start A tile has two edges which has the line color. This parameter is to record the one that the line firstly passes  
	 * @param tilePosition The coordinate of the current tile in the playGround
	 * @param lineColor The color of the Line
	 * @return How many positions the loop has passed (pointless)
	 * @author Zhenyue Ch'in, u5505995
	 */
	public int recursiveIsLoop(Tile[] tiles, int start, int tilePosition, Edge lineColor) {
		/* Pointless for loop */
		if (NoThrough > NUM_OF_TILES) {
			return NoThrough;
		}
		
		/* The connection is the edge that the line passes */
		int connection = 0;
		
		/* The howFar is the distance from the current tile to the next tile */
		int howFar = 0;
		
		/* The position of the tile that the line will pass next */
		int next = 0;
		
		/* The next tile's edge that the line will come in */
		int nextStart = 0;
		
		/* Find the edge having the same color with the current edge in the same tile */
		for (int i=0; i<6; i++) {
			if (tiles[tilePosition].getEdge(i) == lineColor && (i != start)) {
				connection = i;
				break;
			}
		}
		
		/* The border that the line can come out */
		/* Pointless for loop */
		if (connection == 0 && (tilePosition == 8)) return NoThrough;
		if (connection == 3 && (tilePosition == 4)) return NoThrough;	
		if (connection == 4 && (tilePosition == 4)) return NoThrough;
		if (connection == 4 && (tilePosition == 9)) return NoThrough;
		if (connection == 1 && (tilePosition == 8)) return NoThrough;
		if (connection == 1 && (tilePosition == 3)) return NoThrough;
		if ((connection == 1) 
				&& (tilePosition == 3) || (connection == 1) 
				&& (tilePosition == 8) || (connection == 1) 
				&& (tilePosition == 12)) return NoThrough;
		
		/* According to the location of the Edge, find the distance to the next Edge */
		if (connection == 0) {
			howFar = -4;
			nextStart = 3;
		}
		
		if (connection == 1) {
			howFar = 1;
			nextStart = 4;
		}
		
		if (connection == 2) {
			howFar = 5;
			nextStart = 5;
		}
		
		if (connection == 3) {
			howFar = 4;
			nextStart = 0;
		}
		
		if (connection == 4) {
			howFar = -1;
			nextStart = 1;
		}
		
		if (connection == 5) {
			howFar = -5;
			nextStart = 2;
		}
		
		/* The position of the next tile that the line will pass */
		next = tilePosition + howFar;
		
		/* Keep tracing the line */
		if (next>=0 && next<=12) {
			NoThrough++;
			recursiveIsLoop(tiles, nextStart, next, lineColor);
			loopPosition.add(tilePosition);
		}
		
		return NoThrough;
	}
	
	/**
	 * Convert the integer to char for rotation
	 * @param n The integer
	 * @return The char number
	 * @author Zhenyue Ch'in, u5505995
	 */
	public char convertToChar(int n) {
		if (n == 0) return '0';
		if (n == 1) return '1';
		if (n == 2) return '2';
		if (n == 3) return '3';
		if (n == 4) return '4';
		if (n == 5) return '5';	
		return 'e';
	}
	
	/** 
	 * Count the line head on the Border
	 * @param storeTile All the tiles
	 * @param lineColor The color of the line
	 * @param position The current position
	 * @return The number of line head in the given position
	 * @author Zhenyue Ch'in, u5505995
	 */
	public int countBorder(Tile[] storeTile, Edge lineColor, int position) {
		int lineCounter = 0;
		if (position == 0) {
			if (storeTile[position].getEdge(4) == (lineColor)) {
				lineCounter++;
			}
			
			if (storeTile[position].getEdge(5) == (lineColor)) {
				lineCounter++;
			}
			
			if (storeTile[position].getEdge(0) == (lineColor)) {
				lineCounter++;
			}
		}
		
		else if (position == 1) {
			if (storeTile[position].getEdge(5) == lineColor) {
				lineCounter++;
			}
			
			if (storeTile[position].getEdge(0) == lineColor) {
				lineCounter++;
			}
		}
		
		else if (position == 2) {
			if (storeTile[position].getEdge(5) == lineColor) {
				lineCounter++;
			}
			
			if (storeTile[position].getEdge(0) == lineColor) {
				lineCounter++;
			}
		}
		
		else if (position == 3) {
			if (storeTile[position].getEdge(5) == lineColor) {
				lineCounter++;
			}
			
			if (storeTile[position].getEdge(0) == lineColor) {
				lineCounter++;
			}
			
			if (storeTile[position].getEdge(1) == lineColor) {
				lineCounter++;
			}
		}
		
		else if (position == 4) {
			if (storeTile[position].getEdge(3) == lineColor) {
				lineCounter++;
			}
			
			if (storeTile[position].getEdge(4) == lineColor) {
				lineCounter++;
			}
			
			if (storeTile[position].getEdge(5) == lineColor) {
				lineCounter++;
			}
		}
		
		else if (position == 8) {
			if (storeTile[position].getEdge(0) == lineColor) {
				lineCounter++;
			}
			
			if (storeTile[position].getEdge(1) == lineColor) {
				lineCounter++;
			}
			
			if (storeTile[position].getEdge(2) == lineColor) {
				lineCounter++;
			}
		}
		
		else if (position == 9) {
			if (storeTile[position].getEdge(2) == lineColor) {
				lineCounter++;
			}
			
			if (storeTile[position].getEdge(3) == lineColor) {
				lineCounter++;
			}
			
			if (storeTile[position].getEdge(4) == lineColor) {
				lineCounter++;
			}
		}
		
		else if (position == 10) {
			if (storeTile[position].getEdge(2) == lineColor) {
				lineCounter++;
			}
			
			if (storeTile[position].getEdge(3) == lineColor) {
				lineCounter++;
			}
		}
		
		else if (position == 11) {
			
			if (storeTile[position].getEdge(2) == lineColor) {
				lineCounter++;
			}
			
			if (storeTile[position].getEdge(3) == lineColor) {
				lineCounter++;
			}
		}
		
		else if (position == 12) {
			if (storeTile[position].getEdge(1) == lineColor) {
				lineCounter++;
			}
			
			if (storeTile[position].getEdge(2) == lineColor) {
				lineCounter++;
			}
			
			if (storeTile[position].getEdge(3) == lineColor) {
				lineCounter++;
			}
		}
		return lineCounter;
	}
	
	/** 
	 * Find the edge that there is given color of edge
	 * @param storeTile All the tiles
	 * @param lineColor The color of the line
	 * @param position The give position
	 * @return The number of edge
	 * @author Zhenyue Ch'in, u5505995
	 */
	public int whichEdgeStart(Tile[] storeTile, Edge lineColor, int position) {
		int lineCounter = 0;
		if (position == 0) {
			if (storeTile[position].getEdge(4) == lineColor) {
				return 4;
			}
			
			if (storeTile[position].getEdge(5) == lineColor) {
				return 5;
			}
			
			if (storeTile[position].getEdge(0) == lineColor) {
				return 0;
			}
		}
		
		if (position == 1) {
			if (storeTile[position].getEdge(5) == lineColor) {
				return 5;
			}
			
			if (storeTile[position].getEdge(0) == lineColor) {
				return 0;
			}
		}
		
		if (position == 2) {
			if (storeTile[position].getEdge(5) == lineColor) {
				return 5;
			}
			
			if (storeTile[position].getEdge(0) == lineColor) {
				return 0;
			}
		}
		
		if (position == 3) {
			if (storeTile[position].getEdge(5) == lineColor) {
				return 5;
			}
			
			if (storeTile[position].getEdge(0) == lineColor) {
				return 0;
			}
			
			if (storeTile[position].getEdge(1) == lineColor) {
				return 1;
			}
		}
		
		if (position == 4) {
			if (storeTile[position].getEdge(3) == lineColor) {
				return 3;
			}
			
			if (storeTile[position].getEdge(4) == lineColor) {
				return 4;
			}
			
			if (storeTile[position].getEdge(5) == lineColor) {
				return 5;
			}
		}
		
		if (position == 8) {
			if (storeTile[position].getEdge(0) == lineColor) {
				return 0;
			}
			
			if (storeTile[position].getEdge(1) == lineColor) {
				return 1;
			}
			
			if (storeTile[position].getEdge(2) == lineColor) {
				return 2;
			}
		}
		
		if (position == 9) {
			if (storeTile[position].getEdge(2) == lineColor) {
				return 2;
			}
			
			if (storeTile[position].getEdge(3) == lineColor) {
				return 3;
			}
			
			if (storeTile[position].getEdge(4) == lineColor) {
				return 4;
			}
		}
		
		if (position == 10) {
			if (storeTile[position].getEdge(2) == lineColor) {
				return 2;
			}
			
			if (storeTile[position].getEdge(3) == lineColor) {
				return 3;
			}
		}
		
		if (position == 11) {
			
			if (storeTile[position].getEdge(2) == lineColor) {
				return 2;
			}
			
			if (storeTile[position].getEdge(3) == lineColor) {
				return 3;
			}
		}
		
		if (position == 12) {
			if (storeTile[position].getEdge(1) == lineColor) {
				return 1;
			}
			
			if (storeTile[position].getEdge(2) == lineColor) {
				return 2;
			}
			
			if (storeTile[position].getEdge(3) == lineColor) {
				return 3;
			}
		}
		
		return lineCounter;
	}
	
	
	/**
	 * This function is just like the isMatch function. However, the difference is that when the parameter 'tiles' is not complete,
	 * it is just partly matched, this method will return true. For the isMatch, it will return false.
	 * @param tiles the tile series to judge
	 * @return true if the tiles on the board are match to each other.
	 * @author Zhenyue Ch'in, u5505995
	 */
	public boolean myIsMatch(char[] tiles) {	
		/* an array of boolean to store that every tile is match with others or not */
		boolean[] recordTrue = new boolean[NUM_OF_TILES];
		for (int i=0; i<NUM_OF_TILES; i++) {
			recordTrue[i] = true;
		}
		
		/* change the char to the class of tiles */
		Tile[] storeTile = new Tile[NUM_OF_TILES];	

			for (int i=0; i<NUM_OF_POSITION; i=i+2) {
				if (tiles[i] == 'A') {
					storeTile[i/2] = new Tile('A', tiles[i+1]);
				}
		
				if (tiles[i] == 'B') {
					storeTile[i/2] = new Tile('B', tiles[i+1]);
				}

				if (tiles[i] == 'C') {
					storeTile[i/2] = new Tile('C', tiles[i+1]);
				}
		
				if (tiles[i] == 'D') {
					storeTile[i/2] = new Tile('D', tiles[i+1]);
				}
		
				if (tiles[i] == 'E') {
					storeTile[i/2] = new Tile('E', tiles[i+1]);
				}
		
				if (tiles[i] == 'F') {
					storeTile[i/2] = new Tile('F', tiles[i+1]);
				}
		
				if (tiles[i] == 'G') {
					storeTile[i/2] = new Tile('G', tiles[i+1]);
				}
		
				if (tiles[i] == 'H') {
					storeTile[i/2] = new Tile('H', tiles[i+1]);
				}
		
				if (tiles[i] == 'I') {
					storeTile[i/2] = new Tile('I', tiles[i+1]);
				}
		
				if (tiles[i] == 'J') {
					storeTile[i/2] = new Tile('J', tiles[i+1]);
				}

				if (tiles[i] == 'K') {
					storeTile[i/2] = new Tile('K', tiles[i+1]);
				}
		
				if (tiles[i] == 'L') {
					storeTile[i/2] = new Tile('L', tiles[i+1]);
				}
		
				if (tiles[i] == 'M') {
					storeTile[i/2] = new Tile('M', tiles[i+1]);
				}
			
				if (tiles[i] == ' ') {
					storeTile[i/2] = null;
				}
			}
			
			/* define the rule for every match */
			if (storeTile[0] == null) {
				recordTrue[0] = true;
			}
			
			else {
				recordTrue[0] = storeTile[0].compatibleRight(storeTile[1]) && storeTile[0].compatibleBottomLeft(storeTile[4]) && storeTile[0].compatibleBottomRight(storeTile[5]);
			}
			
			if (storeTile[1] == null) {
				recordTrue[1] = true;
			}
			
			else {
				recordTrue[1] = storeTile[1].compatibleRight(storeTile[2]) && storeTile[1].compatibleBottomLeft(storeTile[5]) && storeTile[1].compatibleBottomRight(storeTile[6]);
			}
			
			if (storeTile[2] == null) {
				recordTrue[2] = true;
			}
			
			else {
				recordTrue[2] = storeTile[2].compatibleRight(storeTile[3]) && storeTile[2].compatibleBottomLeft(storeTile[6]) && storeTile[2].compatibleBottomRight(storeTile[7]);
			}
			
			if (storeTile[3] == null) {
				recordTrue[3] = true;
			}
			
			else {
				recordTrue[3] = storeTile[3].compatibleBottomLeft(storeTile[7]) && storeTile[3].compatibleBottomRight(storeTile[8]);
			}
			
			if (storeTile[4] == null) {
				recordTrue[4] = true;
			}
			
			else {
				recordTrue[4] = storeTile[4].compatibleRight(storeTile[5]) && storeTile[4].compatibleBottomRight(storeTile[9]);
			}
			
			if (storeTile[5] == null) {
				recordTrue[5] = true;
			}
			
			else {
				recordTrue[5] = storeTile[5].compatibleRight(storeTile[6]) && storeTile[5].compatibleBottomLeft(storeTile[9]) && storeTile[5].compatibleBottomRight(storeTile[10]);
			}
			
			if (storeTile[6] == null) {
				recordTrue[6] = true;
			}
			
			else {
				recordTrue[6] = storeTile[6].compatibleRight(storeTile[7]) && storeTile[6].compatibleBottomLeft(storeTile[10]) && storeTile[6].compatibleBottomRight(storeTile[11]);
			}
			
			if (storeTile[7] == null) {
				recordTrue[7] = true;
			}
			
			else {
				recordTrue[7] = storeTile[7].compatibleRight(storeTile[8]) && storeTile[7].compatibleBottomLeft(storeTile[11]) && storeTile[7].compatibleBottomRight(storeTile[12]);
			}
			
			if (storeTile[8] == null) {
				recordTrue[8] = true;
			}
			
			else {
				recordTrue[8] = storeTile[8].compatibleBottomLeft(storeTile[12]);
			}
			
			if (storeTile[9] == null) {
				recordTrue[9] = true;
			}
			
			else {
				recordTrue[9] = storeTile[9].compatibleRight(storeTile[10]);
			}
			
			if (storeTile[10] == null) {
				recordTrue[10] = true;
			}
			
			else {
				recordTrue[10] = storeTile[10].compatibleRight(storeTile[11]);
			}
			
			if (storeTile[11] == null) {
				recordTrue[11] = true;
			}
			
			else {
				recordTrue[11] = storeTile[11].compatibleRight(storeTile[12]);
			}
			
			if (storeTile[12] == null) {
				recordTrue[12] = true;
			}
			
			/* return the result with add */
			for (int i=0; i<NUM_OF_TILES; i++) {
				if (recordTrue[i]==false)
					return false;
				}
			return true;
	}
	
	/** 
	 * Change a char tile to Tile
	 * @param chars The char tile to convert
	 * @param rotate The rotation times
	 * @return The Tile
	 * @author Zhenyue Ch'in, u5505995
	 */
	public Tile singleCharToTile(char chars, char rotate) {
		Tile rtn = null;
		if (chars == 'A') rtn = new Tile('A', rotate);
		else if (chars == 'B') rtn = new Tile('B', rotate);
		else if (chars == 'C') rtn = new Tile('C', rotate);		
		else if (chars == 'D') rtn = new Tile('D', rotate);
		else if (chars == 'E') rtn = new Tile('E', rotate);
		else if (chars == 'F') rtn = new Tile('F', rotate);		
		else if (chars == 'G') rtn = new Tile('G', rotate);	
		else if (chars == 'H') rtn = new Tile('H', rotate);		
		else if (chars == 'I') rtn = new Tile('I', rotate);		
		else if (chars == 'J') rtn = new Tile('J', rotate);
		else if (chars == 'K') rtn = new Tile('K', rotate);
		else if (chars == 'L') rtn = new Tile('L', rotate);
		else if (chars == 'M') rtn = new Tile('M', rotate);
		return rtn;
	}
	
	/** 
	 * Change all the char tiles to the Tiles
	 * @param chars The char tiles
	 * @return The Tiles
	 * @author Zhenyue Ch'in, u5505995
	 */
	public Tile[] charToTileMachine(char[] chars) {
		int howLong = 0;
		for (int i=0; i<chars.length; i++) {
			if (chars[i] != ' ') {
				howLong++;
			}
		}
		Tile[] storeTile = new Tile[howLong/2];
		for (int i=0; i<chars.length; i++) {
			if (chars[i] == 'A') storeTile[i/2] = new Tile('A', chars[i+1]);			
			if (chars[i] == 'B') storeTile[i/2] = new Tile('B', chars[i+1]);
			if (chars[i] == 'C') storeTile[i/2] = new Tile('C', chars[i+1]);		
			if (chars[i] == 'D') storeTile[i/2] = new Tile('D', chars[i+1]);
			if (chars[i] == 'E') storeTile[i/2] = new Tile('E', chars[i+1]);
			if (chars[i] == 'F') storeTile[i/2] = new Tile('F', chars[i+1]);		
			if (chars[i] == 'G') storeTile[i/2] = new Tile('G', chars[i+1]);	
			if (chars[i] == 'H') storeTile[i/2] = new Tile('H', chars[i+1]);		
			if (chars[i] == 'I') storeTile[i/2] = new Tile('I', chars[i+1]);		
			if (chars[i] == 'J') storeTile[i/2] = new Tile('J', chars[i+1]);
			if (chars[i] == 'K') storeTile[i/2] = new Tile('K', chars[i+1]);
			if (chars[i] == 'L') storeTile[i/2] = new Tile('L', chars[i+1]);
			if (chars[i] == 'M') storeTile[i/2] = new Tile('M', chars[i+1]);
		}
		return storeTile;
	}
	
	
	/***********************************************************************
	 * The following are the TEST methods for the game
	 * 
	 */
	@Test
	public void testSovleMatch() {
		assertTrue(isMatch(solveMatch(0)));
	}
	
	@Test
	public void testSovleLine() {
		assertTrue(isLine('R', (solveLine('R', 0))));
		assertTrue(isLine('G', (solveLine('G', 0))));
		assertTrue(isLine('Y', (solveLine('Y', 0))));
	}
	
	@Test
	public void testSovleLoop() {
		assertTrue(isLoop('R', (solveLoop('R', 0))));
	}
	
	@Test
	public void isMatchTester() {
		String tempIsMatch1 = "J0E0G1L4A0B0C5D5F1H1K4I2M1";
		String tempIsMatch2 = "F0C0A1L0I3E2D5J0B2H5K3G2M2";
		String tempIsMatch3 = "F0J1I0L4A3B3E2M1K3D5C1G1H0";
		String tempIsMatch4 = "I0M4E4C1K2J5B1F2H1L1G4D1A4";
		String tempIsMatch5 = "I0M4E4C1H2G0L2F2B3A5D3J4K2";
		String tempIsMatch6 = "M0K2G1H0J3B4I5L3C2A1F0D4E3";
		String tempIsMatch7 = "F0H4I0M4D4B3E2G0L2J5A5C1K2";
		String tempIsMatch8 = "D0M4K2G1F0L0C5I5B0J1A0H3E2";
		String tempIsMatch9 = "I0A1B1L4K2J5G0H4M2D5F1C4E2";
		String tempIsMatch10 = "I0E0H1D4K2C5G0B1L4J3A2F3M2";
		assertTrue(isMatch(tempIsMatch1.toCharArray()));
		assertTrue(isMatch(tempIsMatch2.toCharArray()));
		assertTrue(isMatch(tempIsMatch3.toCharArray()));
		assertTrue(isMatch(tempIsMatch4.toCharArray()));
		assertTrue(isMatch(tempIsMatch5.toCharArray()));
		assertFalse(isMatch(tempIsMatch6.toCharArray()));
		assertFalse(isMatch(tempIsMatch7.toCharArray()));
		assertFalse(isMatch(tempIsMatch8.toCharArray()));
		assertFalse(isMatch(tempIsMatch9.toCharArray()));
		assertFalse(isMatch(tempIsMatch10.toCharArray()));
	}
	
	@Test
	public void isLineTester() {
		String red =  "G5H0A0M0B1D2F3C2J5I3L3K2E3";
		String green = "A5I3D0H0E4M5L3J1K1B5G4C4F2";
		String yellow = "E1D4M0C0A2B1L4J5H5K5G0I5F4";
		String red2 =  "G5H0A0M0B1D2F3C2J5I3L3K2E4";
		String green2 = "A5I3D0H0E4M5L3J1K1B5G4C4F3";
		String yellow2 = "E1D4M0C0A2B1L4J5H5K5G0I5F5";
		assertTrue(isLine('R', red.toCharArray()));
		assertTrue(isLine('G', green.toCharArray()));
		assertTrue(isLine('Y', yellow.toCharArray()));
		assertFalse(isLine('R', red2.toCharArray()));
		assertFalse(isLine('G', green2.toCharArray()));
		assertFalse(isLine('Y', yellow2.toCharArray()));
	}
	
	@Test
	public void isLoopTester() {
		String red = "H0F4J3E1I4D3L3C0K0M3B0A3G1";
		String red2 = "H0F4J3E1I4D3L3C0K0M3B0A3G3";
		assertTrue(isLoop('R', red.toCharArray()));
		assertFalse(isLoop('R', red2.toCharArray()));
		
	}
}
