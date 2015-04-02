package comp1110.ass2;

import java.awt.Graphics;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Graphic User Interface of the Tantrix
 * Some codes are from Steve Blackburn
 * The idea of Check Button is from Minghan Wei
 * The idea that to control the appearance of the words through opacity is from Minghan Wei
 * @author Zhenyue Qin, u5505995
 *
 */

public class Board extends Application{
	
	/* The coordinates for the first tile of each row */
	/* The odd numbers are for x */
	/* The even numbers are for y */
	
	/* Row 1 */
	Double[] row1StartPosition11 ={130.0,400.0,185.0,431.8,240.0,400.0,240.0,336.5,185.0,304.7,130.0,336.5};
	Double[] row1StartPosition12 ={130.0 + 113*1,400.0,185.0 + 113*1,431.8,240.0 + 113*1,400.0,240.0 + 113*1,336.5,185.0 + 113*1,304.7,130.0 + 113*1,336.5};
	Double[] row1StartPosition13 ={130.0 + 113*2,400.0,185.0 + 113*2,431.8,240.0 + 113*2,400.0,240.0 + 113*2,336.5,185.0 + 113*2,304.7,130.0 + 113*2,336.5};
	Double[] row1StartPosition14 ={130.0 + 113*3,400.0,185.0 + 113*3,431.8,240.0 + 113*3,400.0,240.0 + 113*3,336.5,185.0 + 113*3,304.7,130.0 + 113*3,336.5};
	
	/* Row 2 */
	Double[] row1StartPosition21 ={75.0,434.8,130.0,403.0,185.0,434.8,185.0,498.3,130.0,530.1,75.0,498.3};
	Double[] row1StartPosition22 ={75.0 + 113*1,434.8,130.0 + 113*1,403.0,185.0 + 113*1,434.8,185.0 + 113*1,498.3,130.0 + 113*1,530.1,75.0 + 113*1,498.3};
	Double[] row1StartPosition23 ={75.0 + 113*2,434.8,130.0 + 113*2,403.0,185.0 + 113*2,434.8,185.0 + 113*2,498.3,130.0 + 113*2,530.1,75.0 + 113*2,498.3};
	Double[] row1StartPosition24 ={75.0 + 113*3,434.8,130.0 + 113*3,403.0,185.0 + 113*3,434.8,185.0 + 113*3,498.3,130.0 + 113*3,530.1,75.0 + 113*3,498.3};
	Double[] row1StartPosition25 ={75.0 + 113*4,434.8,130.0 + 113*4,403.0,185.0 + 113*4,434.8,185.0 + 113*4,498.3,130.0 + 113*4,530.1,75.0 + 113*4,498.3};
	
	/* Row 3 */
	Double[] row1StartPosition31 ={130.0,596.7,185.0,628.5,240.0,597.3,240.0,533.3,185.0,501.4,130.0,533.2};
	Double[] row1StartPosition32 ={130.0 + 113*1,596.7,185.0 + 113*1,628.5,240.0 + 113*1,597.3,240.0 + 113*1,533.3,185.0 + 113*1,501.4,130.0 + 113*1,533.2};
	Double[] row1StartPosition33 ={130.0 + 113*2,596.7,185.0 + 113*2,628.5,240.0 + 113*2,597.3,240.0 + 113*2,533.3,185.0 + 113*2,501.4,130.0 + 113*2,533.2};
	Double[] row1StartPosition34 ={130.0 + 113*3,596.7,185.0 + 113*3,628.5,240.0 + 113*3,597.3,240.0 + 113*3,533.3,185.0 + 113*3,501.4,130.0 + 113*3,533.2};
	
	/* The coordinates for the center of each tile position */
	/* The odd numbers are for x */
	/* The even numbers are for y */
	Double[] tileCenter = {
			185.0, 368.25, 298.0, 368.25, 411.0, 368.25, 524.0, 368.25, 
			130.0, 466.55, 243.0, 466.55, 356.0, 466.55, 469.0, 466.55, 582.0, 466.55, 
			185.0, 565.3, 298.0, 565.3, 411.0, 565.3, 524.0, 565.3
			};
	
	/* Define the class of game to use the methods in the Game class */
	private Game game = new Game();
	
	/* Recognize which solve the player choose (pointless) */
	private char whichHint = 'a';
	
	/* When click the solve button, record the completed solution */
	private char[] sol = new char[26];
	
	/* The solution with the hole inside to correspond the difficulty */
	private char[] solWithDifficulty = new char[26];
	
	/* Layout constants of the tile image */
	public final double tileWidth = 110;
	public final double tileHeight = 125;
	public final double halfWidth = 110 / 2;
	public final double halfHeight = 125 / 2;
	
	/* Layout constants of the playground */
	public final double deckLeftBorder = 75;
	public final double deckRightBorder = 637;
	public final double deckUpBorder = 628.5;
	public final double deckDownBorder = 304.7;
	private final int BOARD_WIDTH = 1120;
	private final int BOARD_HEIGHT = 800;
	
	/* Contains basic parameters of the game */
	private final int NUM_OF_TILE = 13;
	private final int NUM_OF_POSITION = 26;
	
	/* The cards the player will move around */
	private DraggableTile[] tiles = new DraggableTile[NUM_OF_TILE];
	
	/* The tiles on the playground */
	private DraggableTile[] images = new DraggableTile[NUM_OF_TILE];
	
	/* Loop and snap sounds */
	private static final String LOOP_URI = Board.class.getResource("/resources/music/211684__oceanictrancer__classic-house-loop-128-bpm.wav").toString();
	private static final String FONT_URI = Graphics.class.getResource("/resources/fonts/Faraco_Hand.ttf").toString();
	
	/* The music file */
	private AudioClip loop = new AudioClip(LOOP_URI);
	
	/* To know whether the music is on or off */
	private boolean loopPlaying;
	
	/**
	 * Create each of the draggable tiles.
	 * @param root The JavaFX root group for our scene
	 */
	private void makeTiles(Group root) {
		for (int i = 0; i < NUM_OF_TILE; i++) {
			tiles[i] = new DraggableTile(numberToTile(i), this);
			if (i == 0) {
				tiles[i].setLayoutX(960);
				tiles[i].setLayoutY(40);
			}
			
			if (i == 1) {
				tiles[i].setLayoutX(720);
				tiles[i].setLayoutY(180);
			}
			
			if (i == 2) {
				tiles[i].setLayoutX(840);
				tiles[i].setLayoutY(180);
			}
			
			if (i == 3) {
				tiles[i].setLayoutX(960);
				tiles[i].setLayoutY(180);
			}
			
			if (i == 4) {
				tiles[i].setLayoutX(720);
				tiles[i].setLayoutY(320);
			}
			
			if (i == 5) {
				tiles[i].setLayoutX(840);
				tiles[i].setLayoutY(320);
			}
			
			if (i == 6) {
				tiles[i].setLayoutX(960);
				tiles[i].setLayoutY(320);
			}
			
			if (i == 7) {
				tiles[i].setLayoutX(720);
				tiles[i].setLayoutY(460);
			}
			
			if (i == 8) {
				tiles[i].setLayoutX(840);
				tiles[i].setLayoutY(460);
			}
			
			if (i == 9) {
				tiles[i].setLayoutX(960);
				tiles[i].setLayoutY(460);
			}
			
			if (i == 10) {
				tiles[i].setLayoutX(720);
				tiles[i].setLayoutY(600);
			}
			
			if (i == 11) {
				tiles[i].setLayoutX(840);
				tiles[i].setLayoutY(600);
			}
			
			if (i == 12) {
				tiles[i].setLayoutX(960);
				tiles[i].setLayoutY(600);
			}
			root.getChildren().add(tiles[i]);
		}
	}
	
	/* Grey 'target' zones (Playground) that identify the place where the cards can go */
	private void makePlayGround(Group root) {
		Polygon polygon1 = new Polygon();
		polygon1.getPoints().addAll(row1StartPosition11);
		polygon1.setFill(Color.LIGHTGREY);
		root.getChildren().add(polygon1);
		
		Polygon polygon2 = new Polygon();
		polygon2.getPoints().addAll(row1StartPosition12);
		polygon2.setFill(Color.LIGHTGREY);
		root.getChildren().add(polygon2);
		
		Polygon polygon3 = new Polygon();
		polygon3.getPoints().addAll(row1StartPosition13);
		polygon3.setFill(Color.LIGHTGREY);
		root.getChildren().add(polygon3);
		
		Polygon polygon4 = new Polygon();
		polygon4.getPoints().addAll(row1StartPosition14);
		polygon4.setFill(Color.LIGHTGREY);
		root.getChildren().add(polygon4);
		
		Polygon polygon5 = new Polygon();
		polygon5.getPoints().addAll(row1StartPosition21);
		polygon5.setFill(Color.LIGHTGREY);
		root.getChildren().add(polygon5);
		
		Polygon polygon6 = new Polygon();
		polygon6.getPoints().addAll(row1StartPosition22);
		polygon6.setFill(Color.LIGHTGREY);
		root.getChildren().add(polygon6);
		
		Polygon polygon7 = new Polygon();
		polygon7.getPoints().addAll(row1StartPosition23);
		polygon7.setFill(Color.LIGHTGREY);
		root.getChildren().add(polygon7);
		
		Polygon polygon8 = new Polygon();
		polygon8.getPoints().addAll(row1StartPosition24);
		polygon8.setFill(Color.LIGHTGREY);
		root.getChildren().add(polygon8);
		
		Polygon polygon9 = new Polygon();
		polygon9.getPoints().addAll(row1StartPosition25);
		polygon9.setFill(Color.LIGHTGREY);
		root.getChildren().add(polygon9);
		
		Polygon polygon10 = new Polygon();
		polygon10.getPoints().addAll(row1StartPosition31);
		polygon10.setFill(Color.LIGHTGREY);
		root.getChildren().add(polygon10);
		
		Polygon polygon11 = new Polygon();
		polygon11.getPoints().addAll(row1StartPosition32);
		polygon11.setFill(Color.LIGHTGREY);
		root.getChildren().add(polygon11);
		
		Polygon polygon12 = new Polygon();
		polygon12.getPoints().addAll(row1StartPosition33);
		polygon12.setFill(Color.LIGHTGREY);
		root.getChildren().add(polygon12);
		
		Polygon polygon13 = new Polygon();
		polygon13.getPoints().addAll(row1StartPosition34);
		polygon13.setFill(Color.LIGHTGREY);
		root.getChildren().add(polygon13);
	}
	
	/**
	 * The standard JavaFX start method
	 */
	public void start(Stage primaryStage) throws Exception {	
		/* The title of the game */
		primaryStage.setTitle("Tantrix");
		
		/* Create group and scene */
		Group root = new Group();
        Scene scene = new Scene(root, BOARD_WIDTH, BOARD_HEIGHT);
        
        /* Set the music keep playing */
        loop.setCycleCount(AudioClip.INDEFINITE);
        loopPlaying = false;
        
        /* Set the Key Code */
        scene.setOnKeyPressed(event -> {
        	/* Press M to play the music */
        	if (event.getCode() == KeyCode.M) { toggleSoundLoop(); event.consume();}
        	
        	/* Press Q to quit the game */
        	else if (event.getCode() == KeyCode.Q) { Platform.exit(); event.consume();}
        	
        	/* Press ? to get the hint */
        	else if (event.getCode() == KeyCode.SLASH) {        		
        		for (int i=0; i<NUM_OF_POSITION; i=i+2) {
        			/* Delete the tiles to correspond the difficulty */
        			if (solWithDifficulty[i] == ' ') {
        				/* The tiles */
        				solWithDifficulty[i] = sol[i];
        				
        				/* The rotations */
        				solWithDifficulty[i+1] = sol[i+1];
        				
        				/* Convert the char tiles to the tile images and set their locations */
        				if (sol[i] == 'A') {
        					images[i/2] = tiles[0];
        					images[(i/2)].setLayoutX(tileCenter[i] - halfWidth);
        					images[(i/2)].setLayoutY(tileCenter[i+1] - halfHeight);
        					images[(i/2)].rotation = charToInt(sol[i+1]) * 60;
        					images[(i/2)].setRotate(charToInt(sol[i+1]) * 60);
        				}
        					
        				if (sol[i] == 'B') {
        					images[i/2] = tiles[1];
        					images[(i/2)].setLayoutX(tileCenter[i] - halfWidth);
        					images[(i/2)].setLayoutY(tileCenter[i+1] - halfHeight);
        					images[(i/2)].rotation = charToInt(sol[i+1]) * 60;
        					images[(i/2)].setRotate(charToInt(sol[i+1]) * 60);
        				}
        					
        				if (sol[i] == 'C') {
        					images[i/2] = tiles[2];
        					images[(i/2)].setLayoutX(tileCenter[i] - halfWidth);
        					images[(i/2)].setLayoutY(tileCenter[i+1] - halfHeight);
        					images[(i/2)].rotation = charToInt(sol[i+1]) * 60;
        					images[(i/2)].setRotate(charToInt(sol[i+1]) * 60);
        				}
        					
        				if (sol[i] == 'D') {
        					images[i/2] = tiles[3];
        					images[(i/2)].setLayoutX(tileCenter[i] - halfWidth);
        					images[(i/2)].setLayoutY(tileCenter[i+1] - halfHeight);
        					images[(i/2)].rotation = charToInt(sol[i+1]) * 60;
        					images[(i/2)].setRotate(charToInt(sol[i+1]) * 60);
        				}
        					
        				if (sol[i] == 'E') {
        					images[i/2] = tiles[4];
        					images[(i/2)].setLayoutX(tileCenter[i] - halfWidth);
        					images[(i/2)].setLayoutY(tileCenter[i+1] - halfHeight);
        					images[(i/2)].rotation = charToInt(sol[i+1]) * 60;
        					images[(i/2)].setRotate(charToInt(sol[i+1]) * 60);
        				}
        					
        				if (sol[i] == 'F') {
        					images[i/2] = tiles[5];
        					images[(i/2)].setLayoutX(tileCenter[i] - halfWidth);
        					images[(i/2)].setLayoutY(tileCenter[i+1] - halfHeight);
        					images[(i/2)].rotation = charToInt(sol[i+1]) * 60;
        					images[(i/2)].setRotate(charToInt(sol[i+1]) * 60);
        				}
        					
        				if (sol[i] == 'G') {
        					images[i/2] = tiles[6];
        					images[(i/2)].setLayoutX(tileCenter[i] - halfWidth);
        					images[(i/2)].setLayoutY(tileCenter[i+1] - halfHeight);
        					images[(i/2)].rotation = charToInt(sol[i+1]) * 60;
        					images[(i/2)].setRotate(charToInt(sol[i+1]) * 60);
        				}
        					
        				if (sol[i] == 'H') {
        					images[i/2] = tiles[7];
        					images[(i/2)].setLayoutX(tileCenter[i] - halfWidth);
        					images[(i/2)].setLayoutY(tileCenter[i+1] - halfHeight);
        					images[(i/2)].rotation = charToInt(sol[i+1]) * 60;
        					images[(i/2)].setRotate(charToInt(sol[i+1]) * 60);
        				}
        					
        				if (sol[i] == 'I') {
        					images[i/2] = tiles[8];
        					images[(i/2)].setLayoutX(tileCenter[i] - halfWidth);
        					images[(i/2)].setLayoutY(tileCenter[i+1] - halfHeight);
        					images[(i/2)].rotation = charToInt(sol[i+1]) * 60;
        					images[(i/2)].setRotate(charToInt(sol[i+1]) * 60);
        				}
        					
        				if (sol[i] == 'J') {
        					images[i/2] = tiles[9];
        					images[(i/2)].setLayoutX(tileCenter[i] - halfWidth);
        					images[(i/2)].setLayoutY(tileCenter[i+1] - halfHeight);
        					images[(i/2)].rotation = charToInt(sol[i+1]) * 60;
        					images[(i/2)].setRotate(charToInt(sol[i+1]) * 60);
        				}
        					
        				if (sol[i] == 'K') {
        					images[i/2] = tiles[10];
        					images[(i/2)].setLayoutX(tileCenter[i] - halfWidth);
        					images[(i/2)].setLayoutY(tileCenter[i+1] - halfHeight);
        					images[(i/2)].rotation = charToInt(sol[i+1]) * 60;
        					images[(i/2)].setRotate(charToInt(sol[i+1]) * 60);
        				}
        					
        				if (sol[i] == 'L') {
        					images[i/2] = tiles[11];
        					images[(i/2)].setLayoutX(tileCenter[i] - halfWidth);
        					images[(i/2)].setLayoutY(tileCenter[i+1] - halfHeight);
        					images[(i/2)].rotation = charToInt(sol[i+1]) * 60;
        					images[(i/2)].setRotate(charToInt(sol[i+1]) * 60);
        				}
        					
        				if (sol[i] == 'M') {
        					images[i/2] = tiles[12];
        					images[(i/2)].setLayoutX(tileCenter[i] - halfWidth);
        					images[(i/2)].setLayoutY(tileCenter[i+1] - halfHeight);
        					images[(i/2)].rotation = charToInt(sol[i+1]) * 60;
        					images[(i/2)].setRotate(charToInt(sol[i+1]) * 60);
        				}
        				
        				/* After find one, break to save time */
        				break;
        			}	
        		}
        	}
        });
        
        
        
    	/* Create the descriptive text */
    	/* Set the font */
        Font f = Font.loadFont(FONT_URI, 26);
        
        /* Please refer the words */
        Text instruction = new Text(20, 30, "Instruction:");
        instruction.setFont(f);
        instruction.setOpacity(0.5);
        root.getChildren().add(instruction);
        
        Text instruction1 = new Text(20, 60, "Click to rotate  Drag to move");
        instruction1.setFont(f);
        instruction1.setOpacity(0.5);
        root.getChildren().add(instruction1);
        
        Text instruction2 = new Text(20, 90, "Input R OR G OR Y to Color  Difficulty is from 0 to 100");
        instruction2.setFont(f);
        instruction2.setOpacity(0.5);
        root.getChildren().add(instruction2);
        
        Text instruction3 = new Text(20, 120, "Press ? for Help But first you need to press any Solve Button");
        instruction3.setFont(f);
        instruction3.setOpacity(0.5);
        root.getChildren().add(instruction3);
        
        Text instruction4 = new Text(20, 150, "Loop can only be red!");
        instruction4.setFont(f);
        instruction4.setOpacity(0.5);
        root.getChildren().add(instruction4);
        
        /* To show which kind of solution is achieved */
        Text win1 = new Text(730, 60, "isMatch DONE!");
        win1.setFont(f);
        win1.setOpacity(0);
        root.getChildren().add(win1);
        
        Text win2 = new Text(730, 90, "isLine DONE!");
        win2.setFont(f);
        win2.setOpacity(0);
        root.getChildren().add(win2);
        
        Text win3 = new Text(730, 90, "isLoop DONE!");
        win3.setFont(f);
        win3.setOpacity(0);
        root.getChildren().add(win3);
        
        /* Whether the problem is solveable for the currently placed tiles */
        Text canSolve = new Text(40, 750, "Can be solved! Keep WORKING");
        canSolve.setFont(f);
        canSolve.setOpacity(0);
        root.getChildren().add(canSolve);
        
        Text cantSolve = new Text(40, 750, "Can't be solved!");
        cantSolve.setFont(f);
        cantSolve.setOpacity(0);
        root.getChildren().add(cantSolve);
        
        Text colorPrompt = new Text(80, 275, "Color:");
		colorPrompt.setFont(f);
		colorPrompt.setOpacity(0.5);
        root.getChildren().add(colorPrompt);
        
        /* Define the buttons */
        Button check = new Button("Check");
        Button reset = new Button("Reset");
        Button solvable = new Button("Solvable?");
        Button solveMatch = new Button("SolveMatch");
		Button solveLine = new Button("SolveLine");
		Button solveLoop = new Button("SolveLoop");
        
		/* To let the player know what should they input in the TextField */
        Text difficultyPrompt = new Text(402, 275, "Difficulty:");
        difficultyPrompt.setFont(f);
        difficultyPrompt.setOpacity(0.5);
        root.getChildren().add(difficultyPrompt);
        
        /* P.S. This code is from Oracle */
        /* Color TextField */
        TextField hb = new TextField();
		hb.setLayoutX(140);
		hb.setLayoutY(250);
		hb.setPromptText("Enter the color here");
		hb.getText();
		root.getChildren().add(hb);
		
		/* P.S. This code is from Oracle */
		/* Difficulty TextField */
		TextField difficulty = new TextField();
		difficulty.setLayoutX(500);
		difficulty.setLayoutY(250);
		difficulty.setPromptText("Enter the difficulty here");
		difficulty.getText();
		root.getChildren().add(difficulty);
        
		/* Set the parameters for buttons */
		solvable.setFont(f); 
		solvable.setLayoutX(370);
		solvable.setLayoutY(165);
		solvable.setStyle("-fx-font-size: 15pt;");
		root.getChildren().add(solvable);
		
		check.setFont(f); 
		check.setLayoutX(500);
		check.setLayoutY(165);
		check.setStyle("-fx-font-size: 15pt;");
		root.getChildren().add(check);
		
		reset.setFont(f); 
		reset.setLayoutX(600);
		reset.setLayoutY(165);
		reset.setStyle("-fx-font-size: 15pt;");
		root.getChildren().add(reset);
		
		solveMatch.setFont(f); 
		solveMatch.setLayoutX(80);
		solveMatch.setLayoutY(650);
		solveMatch.setStyle("-fx-font-size: 15pt;");
		root.getChildren().add(solveMatch);
		
		solveLine.setFont(f); 
		solveLine.setLayoutX(250);
		solveLine.setLayoutY(650);
		solveLine.setStyle("-fx-font-size: 15pt;");
		root.getChildren().add(solveLine);
		
		solveLoop.setFont(f); 
		solveLoop.setLayoutX(400);
		solveLoop.setLayoutY(650);
		solveLoop.setStyle("-fx-font-size: 15pt;");
		root.getChildren().add(solveLoop);
		
		/* Execute the makePlayGround */
		makePlayGround(root);
		
		/* Execute the makeTiles */ 
        makeTiles(root);
        
        /** 
         * This button is to judge whether the problem is solvable for the currently placed tiles
         */
        solvable.setOnMouseClicked(event -> {
        	/* The tiles on the Playground */
        	char[] onBoard = new char[NUM_OF_POSITION];
        	
        	/* All the char tiles */
        	char[] tileLibrary = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M'};
        	
        	/* Convert the image tiles to char tiles */
        	for (int i=0; i<NUM_OF_TILE; i++) {
        		if (images[i] == tiles[0]) {
    				onBoard[i*2] = 'A';
    				tileLibrary[0] = ' ';
    				onBoard[i*2+1] = intToChar(images[i].rotation);
    			}
    			
    			else if (images[i] == tiles[1]) {
    				onBoard[i*2] = 'B';
    				tileLibrary[1] = ' ';
    				onBoard[i*2+1] = intToChar(images[i].rotation);
    			}
    			
    			else if (images[i] == tiles[2]) {
    				onBoard[i*2] = 'C';
    				tileLibrary[2] = ' ';
    				onBoard[i*2+1] = intToChar(images[i].rotation);
    			}
    			
    			else if (images[i] == tiles[3]) {
    				onBoard[i*2] = 'D';
    				tileLibrary[3] = ' ';
    				onBoard[i*2+1] = intToChar(images[i].rotation);
    			}
    			
    			else if (images[i] == tiles[4]) {
    				onBoard[i*2] = 'E';
    				tileLibrary[4] = ' ';
    				onBoard[i*2+1] = intToChar(images[i].rotation);
    			}
    			
    			else if (images[i] == tiles[5]) {
    				onBoard[i*2] = 'F';
    				tileLibrary[5] = ' ';
    				onBoard[i*2+1] = intToChar(images[i].rotation);
    			}
    			
    			else if (images[i] == tiles[6]) {
    				onBoard[i*2] = 'G';
    				tileLibrary[6] = ' ';
    				onBoard[i*2+1] = intToChar(images[i].rotation);
    			}
    			
    			else if (images[i] == tiles[7]) {
    				onBoard[i*2] = 'H';
    				tileLibrary[7] = ' ';
    				onBoard[i*2+1] = intToChar(images[i].rotation);
    			}
    			
    			else if (images[i] == tiles[8]) {
    				onBoard[i*2] = 'I';
    				tileLibrary[8] = ' ';
    				onBoard[i*2+1] = intToChar(images[i].rotation);
    			}
    			
    			else if (images[i] == tiles[9]) {
    				onBoard[i*2] = 'J';
    				tileLibrary[9] = ' ';
    				onBoard[i*2+1] = intToChar(images[i].rotation);
    			}
    			
    			else if (images[i] == tiles[10]) {
    				onBoard[i*2] = 'K';
    				tileLibrary[10] = ' ';
    				onBoard[i*2+1] = intToChar(images[i].rotation);
    			}
    			
    			else if (images[i] == tiles[11]) {
    				onBoard[i*2] = 'L';
    				tileLibrary[11] = ' ';
    				onBoard[i*2+1] = intToChar(images[i].rotation);
    			}
    			
    			else if (images[i] == tiles[12]) {
    				onBoard[i*2] = 'M';
    				tileLibrary[12] = ' ';
    				onBoard[i*2+1] = intToChar(images[i].rotation);
    			}
        		
    			else if (images[i] == null ) {
    				onBoard[i*2] = ' ';
    				onBoard[i*2+1] = ' ';
    			}
        	}
        	
        	/* Count how many tiles do not show in the Playground */
        	int howManyTileNotUsed = 0;
        	for (int i=0; i<NUM_OF_TILE; i++) {
        		if (tileLibrary[i] != ' ') {
        			howManyTileNotUsed++;
        		}
        	}
        	
        	/* Find those unused tiles */
        	int count = 0;
        	char[] arrayOnDeck = new char[howManyTileNotUsed];
        	for (int i=0; i<NUM_OF_TILE; i++) {
        		if (tileLibrary[i] != ' ') {
        			arrayOnDeck[count] = tileLibrary[i];
        			count++;
        		}
        	}
        	Boolean unChanged = true;
        	for (int i=0; i<NUM_OF_POSITION; i++) {
        		if (onBoard[i] != solWithDifficulty[i]) {
        			unChanged = false;
        			break;
        		}
        	}
        	System.out.println(unChanged);
        	/* Give the corresponding information that whether it is solvable */
        	/* Through control the Opacity of the words */
        	if (unChanged || game.solvable(onBoard, arrayOnDeck)) {
        		cantSolve.setOpacity(0);
        		canSolve.setOpacity(1);
        	}
        	
        	else {
        		cantSolve.setOpacity(1);
        		canSolve.setOpacity(0);
        	}
        	
        	
        });
        
        /**
         * Check which solution has been achieved
         * P.S. Idea is from Minghan Wei
         */
        check.setOnMouseClicked(event -> {
        	/* To store the char array of tiles */
        	char[] toCheck = new char[NUM_OF_POSITION];
        	
        	/* The solution comprises of 26 tiles */
        	if (images.length == NUM_OF_TILE) {
        		
        		/* Convert the image tiles to char tiles */
        		for (int i=0; i<NUM_OF_TILE; i++) {
        			if (images[i] == tiles[0]) {
        				toCheck[i*2] = 'A';
        				toCheck[i*2+1] = intToChar(images[i].rotation);
        			}
        			
        			else if (images[i] == tiles[1]) {
        				toCheck[i*2] = 'B';
        				toCheck[i*2+1] = intToChar(images[i].rotation);
        			}
        			
        			else if (images[i] == tiles[2]) {
        				toCheck[i*2] = 'C';
        				toCheck[i*2+1] = intToChar(images[i].rotation);
        			}
        			
        			else if (images[i] == tiles[3]) {
        				toCheck[i*2] = 'D';
        				toCheck[i*2+1] = intToChar(images[i].rotation);
        			}
        			
        			else if (images[i] == tiles[4]) {
        				toCheck[i*2] = 'E';
        				toCheck[i*2+1] = intToChar(images[i].rotation);
        			}
        			
        			else if (images[i] == tiles[5]) {
        				toCheck[i*2] = 'F';
        				toCheck[i*2+1] = intToChar(images[i].rotation);
        			}
        			
        			else if (images[i] == tiles[6]) {
        				toCheck[i*2] = 'G';
        				toCheck[i*2+1] = intToChar(images[i].rotation);
        			}
        			
        			else if (images[i] == tiles[7]) {
        				toCheck[i*2] = 'H';
        				toCheck[i*2+1] = intToChar(images[i].rotation);
        			}
        			
        			else if (images[i] == tiles[8]) {
        				toCheck[i*2] = 'I';
        				toCheck[i*2+1] = intToChar(images[i].rotation);
        			}
        			
        			else if (images[i] == tiles[9]) {
        				toCheck[i*2] = 'J';
        				toCheck[i*2+1] = intToChar(images[i].rotation);
        			}
        			
        			else if (images[i] == tiles[10]) {
        				toCheck[i*2] = 'K';
        				toCheck[i*2+1] = intToChar(images[i].rotation);
        			}
        			
        			else if (images[i] == tiles[11]) {
        				toCheck[i*2] = 'L';
        				toCheck[i*2+1] = intToChar(images[i].rotation);
        			}
        			
        			else if (images[i] == tiles[12]) {
        				toCheck[i*2] = 'M';
        				toCheck[i*2+1] = intToChar(images[i].rotation);
        			}
        			else {
        				win3.setOpacity(0);
            			win2.setOpacity(0);
            			win1.setOpacity(0);
        			}
        		}
        		
        		System.out.println(toCheck);
        		/* No solution is achieved */
        		if (!game.isMatch(toCheck) && (!game.isLine('R', toCheck) || game.isLine('G', toCheck) || game.isLine('Y', toCheck)) && (!game.isLoop('R', toCheck) || game.isLoop('G', toCheck) || game.isLoop('Y', toCheck))) {
        			win3.setOpacity(0);
        			win2.setOpacity(0);
        			win1.setOpacity(0);
        		}
        		
        		/* isMatch is achieved */
        		if (game.isMatch(toCheck)) {
        			win3.setOpacity(0);
        			win2.setOpacity(0);
        			win1.setOpacity(1);
        		}
        		
        		/* isLine is achieved */
        		if (game.isLine('R', toCheck) || game.isLine('G', toCheck) || game.isLine('Y', toCheck)) {
        			win3.setOpacity(0);
        			win2.setOpacity(1);
        		}
        		
        		/* isLoop is achieved */
        		if (game.isLoop('R', toCheck) || game.isLoop('G', toCheck) || game.isLoop('Y', toCheck)) {
        			win2.setOpacity(0);
        			win3.setOpacity(1);
        		}
        	}
        });
		
        /** 
         * Reset the game to the initial state
         */
        reset.setOnMouseClicked(event -> {
        	/* Empty the tiles on the Playground */
			for (int i=0; i<NUM_OF_TILE; i++) {
				images[i] = null;
			}
			
			/* Reset every tile */
			tiles[0].setLayoutX(960);
			tiles[0].setLayoutY(40);
			tiles[0].rotation = 300;
			tiles[0].rotate();
			
			tiles[1].setLayoutX(720);
			tiles[1].setLayoutY(180);
			tiles[1].rotation = 300;
			tiles[1].rotate();
			
			tiles[2].setLayoutX(840);
			tiles[2].setLayoutY(180);
			tiles[2].rotation = 300;
			tiles[2].rotate();
			
			tiles[3].setLayoutX(960);
			tiles[3].setLayoutY(180);
			tiles[3].rotation = 300;
			tiles[3].rotate();
			
			tiles[4].setLayoutX(720);
			tiles[4].setLayoutY(320);
			tiles[4].rotation = 300;
			tiles[4].rotate();
			
			tiles[5].setLayoutX(840);
			tiles[5].setLayoutY(320);
			tiles[5].rotation = 300;
			tiles[5].rotate();
			
			tiles[6].setLayoutX(960);
			tiles[6].setLayoutY(320);
			tiles[6].rotation = 300;
			tiles[6].rotate();
			
			tiles[7].setLayoutX(720);
			tiles[7].setLayoutY(460);
			tiles[7].rotation = 300;
			tiles[7].rotate();
			
			tiles[8].setLayoutX(840);
			tiles[8].setLayoutY(460);
			tiles[8].rotation = 300;
			tiles[8].rotate();
			
			tiles[9].setLayoutX(960);
			tiles[9].setLayoutY(460);
			tiles[9].rotation = 300;
			tiles[9].rotate();
			
			tiles[10].setLayoutX(720);
			tiles[10].setLayoutY(600);
			tiles[10].rotation = 300;
			tiles[10].rotate();
			
			tiles[11].setLayoutX(840);
			tiles[11].setLayoutY(600);
			tiles[11].rotation = 300;
			tiles[11].rotate();
			
			tiles[12].setLayoutX(960);
			tiles[12].setLayoutY(600);
			tiles[12].rotation = 300;
			tiles[12].rotate();
		});
        
        /** 
         * Give an isMatch solution
         */
        solveMatch.setOnMouseClicked(event -> {
        	/* The solution is for matching */
        	whichHint = 'M';
        	
        	/* First reset the tiles */
        	tiles[0].setLayoutX(960);
			tiles[0].setLayoutY(40);
			tiles[0].rotation = 300;
			tiles[0].rotate();
			
			tiles[1].setLayoutX(720);
			tiles[1].setLayoutY(180);
			tiles[1].rotation = 300;
			tiles[1].rotate();
			
			tiles[2].setLayoutX(840);
			tiles[2].setLayoutY(180);
			tiles[2].rotation = 300;
			tiles[2].rotate();
			
			tiles[3].setLayoutX(960);
			tiles[3].setLayoutY(180);
			tiles[3].rotation = 300;
			tiles[3].rotate();
			
			tiles[4].setLayoutX(720);
			tiles[4].setLayoutY(320);
			tiles[4].rotation = 300;
			tiles[4].rotate();
			
			tiles[5].setLayoutX(840);
			tiles[5].setLayoutY(320);
			tiles[5].rotation = 300;
			tiles[5].rotate();
			
			tiles[6].setLayoutX(960);
			tiles[6].setLayoutY(320);
			tiles[6].rotation = 300;
			tiles[6].rotate();
			
			tiles[7].setLayoutX(720);
			tiles[7].setLayoutY(460);
			tiles[7].rotation = 300;
			tiles[7].rotate();
			
			tiles[8].setLayoutX(840);
			tiles[8].setLayoutY(460);
			tiles[8].rotation = 300;
			tiles[8].rotate();
			
			tiles[9].setLayoutX(960);
			tiles[9].setLayoutY(460);
			tiles[9].rotation = 300;
			tiles[9].rotate();
			
			tiles[10].setLayoutX(720);
			tiles[10].setLayoutY(600);
			tiles[10].rotation = 300;
			tiles[10].rotate();
			
			tiles[11].setLayoutX(840);
			tiles[11].setLayoutY(600);
			tiles[11].rotation = 300;
			tiles[11].rotate();
			
			tiles[12].setLayoutX(960);
			tiles[12].setLayoutY(600);
			tiles[12].rotation = 300;
			tiles[12].rotate();
        	
			/* Empty the tiles on the Playground */
        	for (int i=0; i<NUM_OF_TILE; i++) {
				images[i] = null;
			}
        	
        	/* Record the difficulty */
			double matchNumDifficulty;
			
			/* If the difficulty input is null, the defult difficulty is 0 (does not work) */
			if (difficulty.getText() == null) {matchNumDifficulty = 0.0;}
			
			/* The player has input the difficulty */
			/* Change the difficulty to Double */
			else {
				String matchDifficulty = difficulty.getText();
				matchNumDifficulty = Double.parseDouble(matchDifficulty);
			}
			
			/* The record is to record the completed solution */
			char[] record = new char[NUM_OF_POSITION];
			do {
				record = game.solveMatch(matchNumDifficulty);
			} while(record == null);
			
			/* Initialise the sol and solWithDifficulty */
			for (int i=0; i<NUM_OF_POSITION; i++) {
				sol[i] = ' ';
				solWithDifficulty[i] = ' ';
			}
			
			/* Give the full solution to sol */
			sol = record;
			
			/* Give the solution with difficulty to withDifficultyMatch and matchSol */
			char[] withDifficultyMatch = game.withDifficulty(matchNumDifficulty, record);
			char[] matchSol = withDifficultyMatch; // pointless
			
			/* Give the solution with difficulty to solWithDifficulty */
			solWithDifficulty = withDifficultyMatch;
			
			/* Convert the char tiles to image tiles */
			for (int i=0; i<NUM_OF_POSITION; i = i+2) {
				if (matchSol[i] == 'A') {
					images[i/2] = tiles[0];
				}
				
				if (matchSol[i] == 'B') {
					images[i/2] = tiles[1];
				}
				
				if (matchSol[i] == 'C') {
					images[i/2] = tiles[2];
				}
				
				if (matchSol[i] == 'D') {
					images[i/2] = tiles[3];
				}
				
				if (matchSol[i] == 'E') {
					images[i/2] = tiles[4];
				}
				
				if (matchSol[i] == 'F') {
					images[i/2] = tiles[5];
				}
				
				if (matchSol[i] == 'G') {
					images[i/2] = tiles[6];
				}
				
				if (matchSol[i] == 'H') {
					images[i/2] = tiles[7];
				}
				
				if (matchSol[i] == 'I') {
					images[i/2] = tiles[8];
				}
				
				if (matchSol[i] == 'J') {
					images[i/2] = tiles[9];
				}
				
				if (matchSol[i] == 'K') {
					images[i/2] = tiles[10];
				}
				
				if (matchSol[i] == 'L') {
					images[i/2] = tiles[11];
				}
				
				if (matchSol[i] == 'M') {
					images[i/2] = tiles[12];
				}
				
				if (matchSol[i] == ' ') {
					images[i/2] = null;
				}
			}
			
			/* Rotate the tiles */
			for (int i=1; i<NUM_OF_POSITION; i=i+2) {
				int rotateTimes = (int) (matchSol[i]) - 48;
				if (images[(int) (i/2)] != null) {
					images[(int) (i/2)].rotation = rotateTimes * 60;
					images[(int) (i/2)].setRotate(rotateTimes * 60);
				}
			}
			
			/* Set the tiles' locations */
			for (int i=0; i<NUM_OF_POSITION; i++) {
				if (images[(int) (i/2)] != null) {
					if (i % 2 == 0) {
						images[(int) (i/2)].setLayoutX(tileCenter[i] - halfWidth);
					}
					if (i % 2 == 1) {
						images[(int) (i/2)].setLayoutY(tileCenter[i] - halfHeight);
					}
				}
			}
		});
        
       /** 
        * Give an isLine solution
        * Based on the button of solveMatch, therefore some names of variables are still in the form of match
        * Apologise for that
        */
        solveLine.setOnMouseClicked(event -> {
        	whichHint = 'L';
        	for (int i=0; i<NUM_OF_TILE; i++) {
				images[i] = null;
			}
        	
        	/* First reset the tiles */
        	tiles[0].setLayoutX(960);
			tiles[0].setLayoutY(40);
			tiles[0].rotation = 300;
			tiles[0].rotate();
			
			tiles[1].setLayoutX(720);
			tiles[1].setLayoutY(180);
			tiles[1].rotation = 300;
			tiles[1].rotate();
			
			tiles[2].setLayoutX(840);
			tiles[2].setLayoutY(180);
			tiles[2].rotation = 300;
			tiles[2].rotate();
			
			tiles[3].setLayoutX(960);
			tiles[3].setLayoutY(180);
			tiles[3].rotation = 300;
			tiles[3].rotate();
			
			tiles[4].setLayoutX(720);
			tiles[4].setLayoutY(320);
			tiles[4].rotation = 300;
			tiles[4].rotate();
			
			tiles[5].setLayoutX(840);
			tiles[5].setLayoutY(320);
			tiles[5].rotation = 300;
			tiles[5].rotate();
			
			tiles[6].setLayoutX(960);
			tiles[6].setLayoutY(320);
			tiles[6].rotation = 300;
			tiles[6].rotate();
			
			tiles[7].setLayoutX(720);
			tiles[7].setLayoutY(460);
			tiles[7].rotation = 300;
			tiles[7].rotate();
			
			tiles[8].setLayoutX(840);
			tiles[8].setLayoutY(460);
			tiles[8].rotation = 300;
			tiles[8].rotate();
			
			tiles[9].setLayoutX(960);
			tiles[9].setLayoutY(460);
			tiles[9].rotation = 300;
			tiles[9].rotate();
			
			tiles[10].setLayoutX(720);
			tiles[10].setLayoutY(600);
			tiles[10].rotation = 300;
			tiles[10].rotate();
			
			tiles[11].setLayoutX(840);
			tiles[11].setLayoutY(600);
			tiles[11].rotation = 300;
			tiles[11].rotate();
			
			tiles[12].setLayoutX(960);
			tiles[12].setLayoutY(600);
			tiles[12].rotation = 300;
			tiles[12].rotate();
        	
			/* Get the color from the TextBox and convert it to char */
			String aColor = hb.getText();
			char color = 'R';
			if (aColor.equals("R")) color = 'R';
			if (aColor.equals("G")) color = 'G';
			if (aColor.equals("Y")) color = 'Y';
			
			/*To record the difficulty  */
			String lineDifficulty = difficulty.getText();
			double lineNumDifficulty;
			
			/* If the difficulty input is null, the defult difficulty is 0 (does not work) */
			if (lineDifficulty == null) {lineNumDifficulty = 0.0;}
			
			/* The player has input the difficulty */
			/* Change the difficulty to Double */
			else lineNumDifficulty = Double.parseDouble(lineDifficulty);
			
			/* Get a solution for record */
			char[] record = new char[NUM_OF_POSITION];
			do {
				record = game.solveLine(color, lineNumDifficulty);
			} while(record == null);
			for (int i=0; i<NUM_OF_POSITION; i++) {
				sol[i] = ' ';
				solWithDifficulty[i] = ' ';
			}
			
			/* Give the full solution to sol */
			sol = record;
			char[] withDifficultyMatch = game.withDifficulty(lineNumDifficulty, record);
			char[] matchSol = withDifficultyMatch;
			
			/* Give the solution with difficulty to withDifficultyMatch and matchSol */
			solWithDifficulty = withDifficultyMatch;
			
			/* Convert the char tiles to image tiles */
			for (int i=0; i<NUM_OF_POSITION; i = i+2) {
				if (matchSol[i] == 'A') {
					images[i/2] = tiles[0];
				}
				
				if (matchSol[i] == 'B') {
					images[i/2] = tiles[1];
				}
				
				if (matchSol[i] == 'C') {
					images[i/2] = tiles[2];
				}
				
				if (matchSol[i] == 'D') {
					images[i/2] = tiles[3];
				}
				
				if (matchSol[i] == 'E') {
					images[i/2] = tiles[4];
				}
				
				if (matchSol[i] == 'F') {
					images[i/2] = tiles[5];
				}
				
				if (matchSol[i] == 'G') {
					images[i/2] = tiles[6];
				}
				
				if (matchSol[i] == 'H') {
					images[i/2] = tiles[7];
				}
				
				if (matchSol[i] == 'I') {
					images[i/2] = tiles[8];
				}
				
				if (matchSol[i] == 'J') {
					images[i/2] = tiles[9];
				}
				
				if (matchSol[i] == 'K') {
					images[i/2] = tiles[10];
				}
				
				if (matchSol[i] == 'L') {
					images[i/2] = tiles[11];
				}
				
				if (matchSol[i] == 'M') {
					images[i/2] = tiles[12];
				}
				
				if (matchSol[i] == ' ') {
					images[i/2] = null;
				}
				
			}
			
			/* Rotate the tiles */
			for (int i=1; i<NUM_OF_POSITION; i=i+2) {
				int rotateTimes = (int) (matchSol[i]) - 48;
				if (images[(int) (i/2)] != null) {
					images[(int) (i/2)].rotation = rotateTimes * 60;
					images[(int) (i/2)].setRotate(rotateTimes * 60);
				}
			}
			
			/* Set the tiles' locations */
			for (int i=0; i<NUM_OF_POSITION; i++) {
				if (images[(int) (i/2)] != null) {
					if (i % 2 == 0) {
						images[(int) (i/2)].setLayoutX(tileCenter[i] - halfWidth);
					}
					if (i % 2 == 1) {
						images[(int) (i/2)].setLayoutY(tileCenter[i] - halfHeight);
					}
				}
			}
		});
        
        /** 
         * Give an isLoop solution
         * Based on the button of solveMatch, therefore some names of variables are still in the form of match
         * Apologise for that
         */
         solveLoop.setOnMouseClicked(event -> {
         	whichHint = 'P';
         	for (int i=0; i<NUM_OF_TILE; i++) {
 				images[i] = null;
 			}
         	
         	/* First reset the tiles */
         	tiles[0].setLayoutX(960);
 			tiles[0].setLayoutY(40);
 			tiles[0].rotation = 300;
 			tiles[0].rotate();
 			
 			tiles[1].setLayoutX(720);
 			tiles[1].setLayoutY(180);
 			tiles[1].rotation = 300;
 			tiles[1].rotate();
 			
 			tiles[2].setLayoutX(840);
 			tiles[2].setLayoutY(180);
 			tiles[2].rotation = 300;
 			tiles[2].rotate();
 			
 			tiles[3].setLayoutX(960);
 			tiles[3].setLayoutY(180);
 			tiles[3].rotation = 300;
 			tiles[3].rotate();
 			
 			tiles[4].setLayoutX(720);
 			tiles[4].setLayoutY(320);
 			tiles[4].rotation = 300;
 			tiles[4].rotate();
 			
 			tiles[5].setLayoutX(840);
 			tiles[5].setLayoutY(320);
 			tiles[5].rotation = 300;
 			tiles[5].rotate();
 			
 			tiles[6].setLayoutX(960);
 			tiles[6].setLayoutY(320);
 			tiles[6].rotation = 300;
 			tiles[6].rotate();
 			
 			tiles[7].setLayoutX(720);
 			tiles[7].setLayoutY(460);
 			tiles[7].rotation = 300;
 			tiles[7].rotate();
 			
 			tiles[8].setLayoutX(840);
 			tiles[8].setLayoutY(460);
 			tiles[8].rotation = 300;
 			tiles[8].rotate();
 			
 			tiles[9].setLayoutX(960);
 			tiles[9].setLayoutY(460);
 			tiles[9].rotation = 300;
 			tiles[9].rotate();
 			
 			tiles[10].setLayoutX(720);
 			tiles[10].setLayoutY(600);
 			tiles[10].rotation = 300;
 			tiles[10].rotate();
 			
 			tiles[11].setLayoutX(840);
 			tiles[11].setLayoutY(600);
 			tiles[11].rotation = 300;
 			tiles[11].rotate();
 			
 			tiles[12].setLayoutX(960);
 			tiles[12].setLayoutY(600);
 			tiles[12].rotation = 300;
 			tiles[12].rotate();
         	
 			/* Get the color from the TextBox and convert it to char */
 			String aColor = hb.getText();
 			char color = 'R';
 			/* The loop can only be red */
 			if (aColor.equals("R")) color = 'R';
 			if (aColor.equals("G")) color = 'R';
 			if (aColor.equals("Y")) color = 'R';
 			
 			/*To record the difficulty  */
 			String lineDifficulty = difficulty.getText();
 			double lineNumDifficulty;
 			
 			/* If the difficulty input is null, the defult difficulty is 0 (does not work) */
 			if (lineDifficulty == null) {lineNumDifficulty = 0.0;}
 			
 			/* The player has input the difficulty */
 			/* Change the difficulty to Double */
 			else lineNumDifficulty = Double.parseDouble(lineDifficulty);
 			
 			/* Get a solution for record */
 			char[] record = new char[NUM_OF_POSITION];
 			do {
 				record = game.solveLoop(color, lineNumDifficulty);
 			} while(record == null);
 			for (int i=0; i<NUM_OF_POSITION; i++) {
 				sol[i] = ' ';
 				solWithDifficulty[i] = ' ';
 			}
 			
 			/* Give the full solution to sol */
 			sol = record;
 			char[] withDifficultyMatch = game.withDifficulty(lineNumDifficulty, record);
 			char[] matchSol = withDifficultyMatch;
 			
 			/* Give the solution with difficulty to withDifficultyMatch and matchSol */
 			solWithDifficulty = withDifficultyMatch;
 			
 			/* Convert the char tiles to image tiles */
 			for (int i=0; i<NUM_OF_POSITION; i = i+2) {
 				if (matchSol[i] == 'A') {
 					images[i/2] = tiles[0];
 				}
 				
 				if (matchSol[i] == 'B') {
 					images[i/2] = tiles[1];
 				}
 				
 				if (matchSol[i] == 'C') {
 					images[i/2] = tiles[2];
 				}
 				
 				if (matchSol[i] == 'D') {
 					images[i/2] = tiles[3];
 				}
 				
 				if (matchSol[i] == 'E') {
 					images[i/2] = tiles[4];
 				}
 				
 				if (matchSol[i] == 'F') {
 					images[i/2] = tiles[5];
 				}
 				
 				if (matchSol[i] == 'G') {
 					images[i/2] = tiles[6];
 				}
 				
 				if (matchSol[i] == 'H') {
 					images[i/2] = tiles[7];
 				}
 				
 				if (matchSol[i] == 'I') {
 					images[i/2] = tiles[8];
 				}
 				
 				if (matchSol[i] == 'J') {
 					images[i/2] = tiles[9];
 				}
 				
 				if (matchSol[i] == 'K') {
 					images[i/2] = tiles[10];
 				}
 				
 				if (matchSol[i] == 'L') {
 					images[i/2] = tiles[11];
 				}
 				
 				if (matchSol[i] == 'M') {
 					images[i/2] = tiles[12];
 				}
 				
 				if (matchSol[i] == ' ') {
 					images[i/2] = null;
 				}
 				
 			}
 			
 			/* Rotate the tiles */
 			for (int i=1; i<NUM_OF_POSITION; i=i+2) {
 				int rotateTimes = (int) (matchSol[i]) - 48;
 				if (images[(int) (i/2)] != null) {
 					images[(int) (i/2)].rotation = rotateTimes * 60;
 					images[(int) (i/2)].setRotate(rotateTimes * 60);
 				}
 			}
 			
 			/* Set the tiles' locations */
 			for (int i=0; i<NUM_OF_POSITION; i++) {
 				if (images[(int) (i/2)] != null) {
 					if (i % 2 == 0) {
 						images[(int) (i/2)].setLayoutX(tileCenter[i] - halfWidth);
 					}
 					if (i % 2 == 1) {
 						images[(int) (i/2)].setLayoutY(tileCenter[i] - halfHeight);
 					}
 				}
 			}
 		});
        
         /* The format of javaFx */
        primaryStage.setScene(scene);
        primaryStage.show();
        
	}
	
	/**
	 * The standard JavaFX start method
	 */
	public static void start() {
		Application.launch();
	}
	
	/**
	 * According to the order of alphabet, return the corresponding tile.
	 */
	public Tile numberToTile(int number) {
		if (number == 0) 		return new Tile('A', '0');
		else if (number == 1) 	return new Tile('B', '0');
		else if (number == 2) 	return new Tile('C', '0');
		else if (number == 3) 	return new Tile('D', '0');
		else if (number == 4) 	return new Tile('E', '0');
		else if (number == 5) 	return new Tile('F', '0');
		else if (number == 6) 	return new Tile('G', '0');
		else if (number == 7) 	return new Tile('H', '0');
		else if (number == 8) 	return new Tile('I', '0');
		else if (number == 9) 	return new Tile('J', '0');
		else if (number == 10) 	return new Tile('K', '0');
		else if (number == 11) 	return new Tile('L', '0');
		else				 	return new Tile('M', '0');
	}
	
	/**
	 * Toggle a sound loop (there's no particular need for this,
	 * it's just to show how you might do it).
	 * P.S. From Steve Blackburn
	 */
	private void toggleSoundLoop() {
		if (loopPlaying) 
			loop.stop();
		else
			loop.play();
		loopPlaying = !loopPlaying;
	}
	
	/**
	 * Convert the image tile to char tile
	 * @param toChange The image tile
	 * @return The char tile
	 */
	public char draggableTileToChar(DraggableTile toChange) {
		if (toChange == tiles[0]) {
			return 'A';
		}
		if (toChange == tiles[1]) {
			return 'B';
		}
		if (toChange == tiles[2]) {
			return 'C';
		}
		if (toChange == tiles[3]) {
			return 'D';
		}
		if (toChange == tiles[4]) {
			return 'E';
		}
		if (toChange == tiles[5]) {
			return 'F';
		}
		if (toChange == tiles[6]) {
			return 'G';
		}
		if (toChange == tiles[7]) {
			return 'H';
		}
		if (toChange == tiles[8]) {
			return 'I';
		}
		if (toChange == tiles[9]) {
			return 'J';
		}
		if (toChange == tiles[10]) {
			return 'K';
		}
		if (toChange == tiles[11]) {
			return 'L';
		}
		if (toChange == tiles[12]) {
			return 'M';
		}
		else {
			return '#';
		}
	}
	
	/**
	 * Convert integer to char for rotation
	 * @param letter The char number
	 * @return The int number
	 */
	public int charToInt(char letter) {
		if (letter == '0') {
			return 0;
		}
		if (letter == '1') {
			return 1;
		}
		if (letter == '2') {
			return 2;
		}
		if (letter == '3') {
			return 3;
		}
		if (letter == '4') {
			return 4;
		}
		if (letter == '5') {
			return 5;
		}
		else {
			return -1;
		}
	}
	
	/**
	 * Convert the char number to int number
	 * @param number The int number
	 * @return The char number
	 */
	public char intToChar(int number) {
		System.out.println("number: " + number);
		if (number == 0) {
			return '0';
		}
		if (number == 60) {
			return '1';
		}
		if (number == 120) {
			return '2';
		}
		if (number == 180) {
			return '3';
		}
		if (number == 240) {
			return '4';
		}
		if (number == 300) {
			return '5';
		}
		else {
			return '#';
		}
 	}
	
	/**
	 * When the image tile is moved, this is the reaction after the mouse has been released
	 * @param movingTile The tile is being moved
	 */
	public void executeMove(DraggableTile movingTile) { 
		/* These two things are for calculating the distance to the every target zone */
		int minimal = 1000;
		double tempMinimal = 1000;
		
		/* Every tile's distance to the target zone */
		Double[] distances = new Double[NUM_OF_TILE];
		
		/* The coordinate of the center of the image tile */
		double centerX = movingTile.getLayoutX()+110/2;
		double centerY = movingTile.getLayoutY()+125/2;
		
		/* The tile is not in the Playground */
		if (centerX <= deckLeftBorder || centerX >= deckRightBorder || centerY <= deckDownBorder || centerY >= deckUpBorder) {
			for (int i=0; i<NUM_OF_TILE; i++) {
				/* It is moving out */
				if (images[i] == movingTile) {
					images[i] = null;
				}
			}
			
			/* Reset the moving tile to the initial position */
			if (movingTile == tiles[0]) {
				movingTile.setLayoutX(960);
				movingTile.setLayoutY(40);
			}
			
			if (movingTile == tiles[1]) {
				movingTile.setLayoutX(720);
				movingTile.setLayoutY(180);
			}
			
			if (movingTile == tiles[2]) {
				movingTile.setLayoutX(840);
				movingTile.setLayoutY(180);
			}
			
			if (movingTile == tiles[3]) {
				movingTile.setLayoutX(960);
				movingTile.setLayoutY(180);
			}
			
			if (movingTile == tiles[4]) {
				movingTile.setLayoutX(720);
				movingTile.setLayoutY(320);
			}
			
			if (movingTile == tiles[5]) {
				movingTile.setLayoutX(840);
				movingTile.setLayoutY(320);
			}
			
			if (movingTile == tiles[6]) {
				movingTile.setLayoutX(960);
				movingTile.setLayoutY(320);
			}
			
			if (movingTile == tiles[7]) {
				movingTile.setLayoutX(720);
				movingTile.setLayoutY(460);
			}
			
			if (movingTile == tiles[8]) {
				movingTile.setLayoutX(840);
				movingTile.setLayoutY(460);
			}
			
			if (movingTile == tiles[9]) {
				movingTile.setLayoutX(960);
				movingTile.setLayoutY(460);
			}
			
			if (movingTile == tiles[10]) {
				movingTile.setLayoutX(720);
				movingTile.setLayoutY(600);
			}
			
			if (movingTile == tiles[11]) {
				movingTile.setLayoutX(840);
				movingTile.setLayoutY(600);
			}
			
			if (movingTile == tiles[12]) {
				movingTile.setLayoutX(960);
				movingTile.setLayoutY(600);
			}
				
		} 
		
		/* The tile is in the playground */
		else {
			double presentX = movingTile.getLayoutX();
			double presentY = movingTile.getLayoutY();
			
			if ((movingTile.tilex >= deckLeftBorder && movingTile.tilex <= deckRightBorder && movingTile.tiley >= deckDownBorder && movingTile.tiley <= deckUpBorder)) {
				/* It is from Playground to the Playground */
				/* Empty the position occupation before moving */
				for (int i=0; i<NUM_OF_TILE; i++) {
					if (movingTile == images[i]) {
						images[i] = null;
					}
				}
				
				/* Calculate the distance from the center of the moving tile to every position of the Playground */
				for (int i=0; i<NUM_OF_POSITION; i=i+2) {
					distances[(int) (i/2)] = Math.sqrt(Math.pow((centerX - tileCenter[i]), 2) + Math.pow((centerY - tileCenter[i+1]), 2));
				}
				
				/* Find the position with minimal distance */
				for (int i=0; i<NUM_OF_TILE; i++) {
					if (tempMinimal > distances[i]) {
						tempMinimal = distances[i];
						minimal = i;
					}
				}
				
				/* The position is not occupied */
				if (images[minimal] == null) {
					movingTile.setLayoutX(tileCenter[minimal*2] - halfWidth);
					movingTile.setLayoutY(tileCenter[minimal*2 + 1] - halfHeight);
					images[minimal] = movingTile;
				}
				
				/* The position is occupied */
				else {
				movingTile.setLayoutX(presentX);
				movingTile.setLayoutY(presentY);
				}
			}
			
			/* It is from deck to the Playground */
			/* Calculate the distance from the center of the moving tile to every position of the Playground */
			else {
			for (int i=0; i<NUM_OF_POSITION; i=i+2) {
				distances[(int) (i/2)] = Math.sqrt(Math.pow((centerX - tileCenter[i]), 2) + Math.pow((centerY - tileCenter[i+1]), 2));
			}
			
			/* Find the position with minimal distance */
			for (int i=0; i<NUM_OF_TILE; i++) {
				if (tempMinimal > distances[i]) {
					tempMinimal = distances[i];
					minimal = i;
				}
			}
			
			/* The position is not occupied */
			if (images[minimal] == null) {
				movingTile.setLayoutX(tileCenter[minimal*2] - halfWidth);
				movingTile.setLayoutY(tileCenter[minimal*2 + 1] - halfHeight);
				images[minimal] = movingTile;
			}
			
			/* The position is occupied */
			else {
			movingTile.setLayoutX(presentX);
			movingTile.setLayoutY(presentY);
			}
			
			}
		}
	
		
	}
}