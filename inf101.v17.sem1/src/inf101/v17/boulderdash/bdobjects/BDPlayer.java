package inf101.v17.boulderdash.bdobjects;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;

import java.util.ArrayList;
import java.util.Optional;

import inf101.v17.boulderdash.Direction;
import inf101.v17.boulderdash.Position;
import inf101.v17.boulderdash.maps.BDMap;

/**
 * An implementation of the player.
 *
 * @author larsjaffke
 *
 */
public class BDPlayer extends AbstractBDMovingObject implements IBDKillable {

	// Variables for spritesheet and player pic
	private static Optional<ArrayList<ImagePattern>> playerR = Optional.empty();
	private static Optional<ArrayList<ImagePattern>> playerL = Optional.empty();
	private static Optional<ImagePattern> playerF = Optional.empty();
	public static final int N_SPRITES = 11;
	private int animationCounter = 0;

	private KeyCode lastKeyPressed;
	private int timeWaited = 0;
	private int stopAnimation = 10;

	/**
	 * Is the player still alive?
	 */
	protected boolean alive = true;

	/**
	 * The direction indicated by keypresses.
	 */
	protected Direction askedToGo;

	/**
	 * Number of diamonds collected so far.
	 */
	protected int diamondCnt = 0;

	public BDPlayer(BDMap owner) {
		super(owner);
	}

	@Override
	public Paint getColor() {
		//Sets pic for player only first time needed
		if (!playerR.isPresent() || !playerL.isPresent()) {

			ArrayList<ImagePattern> tempListL = new ArrayList<>();
			Image sprites = new Image("file:pictures/walkingSprite.png");

			for (int i = 0; i < N_SPRITES; i++) {
				ImagePattern image = new ImagePattern(sprites, i, 2, N_SPRITES, 2, true);
				tempListL.add(image);
			}
			playerL = Optional.of(tempListL);

			ArrayList<ImagePattern> tempListR = new ArrayList<>();
			for (int i = 0; i < N_SPRITES; i++) {
				ImagePattern image = new ImagePattern(sprites, i, 1, N_SPRITES, 2, true);
				tempListR.add(image);
			}
			playerR = Optional.of(tempListR);

			Image playerFImage = new Image("file:pictures/p1_front.png");
			ImagePattern playerFPattern = new ImagePattern(playerFImage);
			playerF = Optional.of(playerFPattern);
		}
		
		
		if (lastKeyPressed == KeyCode.RIGHT)
			return playerR.get().get(animationCounter);
		else if (lastKeyPressed == KeyCode.LEFT)
			return playerL.get().get(animationCounter);
		else
			return playerF.get();

	}

	/**
	 * @return true if the player is alive
	 */
	public boolean isAlive() {
		return alive;
	}

	public void keyPressed(KeyCode key) {
		lastKeyPressed = key;

		switch (key) {
		case LEFT:
			askedToGo = Direction.WEST;
			break;
		case RIGHT:
			askedToGo = Direction.EAST;
			break;
		case DOWN:
			askedToGo = Direction.SOUTH;
			break;
		case UP:
			askedToGo = Direction.NORTH;
			break;

		}

	}

	@Override
	public void kill() {
		this.alive = false;
	}

	/**
	 * Returns the number of diamonds collected so far.
	 *
	 * @return
	 */
	public int numberOfDiamonds() {
		return diamondCnt;
	}

	@Override
	public void step() {

		Position current = super.getPosition();

		try {
			if (askedToGo != null) {

				timeWaited = 0;

				Position target = current.moveDirection(askedToGo);

				if (owner.canGo(target)) {
					IBDObject targetObject = owner.get(target);

					if (targetObject instanceof BDDiamond) {
						diamondCnt++;
						this.prepareMoveTo(askedToGo);
					} else if (targetObject instanceof BDRock) {
						boolean canPush = ((BDRock) owner.get(target)).push(askedToGo);

						if (canPush) {
							this.prepareMoveTo(askedToGo);
						}
					} else if (targetObject instanceof BDBug)
						this.kill();
					else if (targetObject instanceof BDSand || targetObject instanceof BDEmpty)
						this.prepareMoveTo(askedToGo);
				}

			} else {
				if (timeWaited >= stopAnimation) {
					lastKeyPressed = null;
					timeWaited = 0;
				} else {
					timeWaited++;
				}

			}

		} catch (

		Exception e) {

		}

		super.step();
		askedToGo = null;
		animationCounter = (animationCounter + 1) % N_SPRITES;

	}

	@Override
	public boolean isKillable() {
		return true;
	}
}
