package comp1110.ass2;

import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;

/**
 * This class implements a draggable game tile by subclassing
 * the JavaFX ImageView class
 * @author Zhenyue Qin, u5505995
 * P.S. Almost all the codes are from Steve Blackburn
 */

public class DraggableTile extends ImageView {
	/* Location within the jar where the images are */
	static final String TILE_BASE_URI = "/resources/images/tile";
	
	/* The root of the snap voice */
	private static final String SNAP_URI = Board.class.getResource("/resources/music/128919__ecfike__click-2.wav").toString();
	private AudioClip snap = new AudioClip(SNAP_URI);
	
	/* Keep track of the orientation of the image */
	public int rotation = 0;
	
	/* Keep track of where the mouse was last time we got an event */
	private double mousex, mousey; 
		
	public double tilex;
	public double tiley;
	
	/* Is a mouse-drag underway? */
	private boolean dragged = false;
	
	/* The underlying game tile that we represent */
	Tile tile;
	
	/* The board the card is linked to */
	Board board;
	
	public DraggableTile(Tile tile, Board board) {
		super(DraggableTile.class.getResource(TILE_BASE_URI+tile.getTile()+".png").toString());
		//System.out.println(TILE_BASE_URI+tile.getTile()+".png");
		this.tile = tile;
		this.board = board;
		
		/* if the mouse is pressed, remember where the mouse is and bring to front */
		this.setOnMousePressed(event -> { 
			mousex = event.getSceneX();
			mousey = event.getSceneY();
			tilex = this.getLayoutX();
			tiley = this.getLayoutY();
			toFront();
			event.consume();
		});
		
		/* this event tells us for sure we're being dragged */
		this.setOnMouseDragged(event -> { 
			dragged = true;
			/* move card by same amount as the mouse */
			setLayoutX(getLayoutX() + event.getSceneX() - mousex);
			setLayoutY(getLayoutY() + event.getSceneY() - mousey);
			mousex = event.getSceneX();
			mousey = event.getSceneY();
			/* show the player which square is nearest to where we are */
			//board.highlightNearestSquare(this);
			event.consume();
		});
		
		/* the mouse is released; if we were not being dragged, it was a click */
		this.setOnMouseReleased(event -> { 
			snap.play();
			if (!dragged)
				rotate();
			else {
				board.executeMove(this);
				dragged = false;
			}
			event.consume();
		});
	}
	
	public void rotate() {
		rotation += 60;
		rotation %= 360;
		tile.rotate(rotation / 60);   // change underlying game card
		setRotate(rotation);  // rotate image
	}
	
	
}
