package inf101.v17.boulderdash.bdobjects;

import java.util.Random;

import inf101.v17.boulderdash.Direction;
import inf101.v17.boulderdash.IllegalMoveException;
import inf101.v17.boulderdash.Position;
import inf101.v17.boulderdash.maps.BDMap;

/**
 * Contains most of the logic associated with objects that fall such as rocks
 * and diamonds.
 *
 * @author larsjaffke
 *
 */
public abstract class AbstractBDFallingObject extends AbstractBDKillingObject {

	// Enums for checking possibilities for movement. Not yet implemented
	public enum movement {
		LEFT_OKAY, RIGHT_OKAY, BOTH_OKAY, NOT_OKAY
	}

	/**
	 * A timeout between the moment when an object can fall (e.g. the tile
	 * underneath it becomes empty) and the moment it does. This is necessary to
	 * make sure that the player doesn't get killed immediately when walking
	 * under a rock.
	 */
	protected static final int WAIT = 3;

	protected boolean falling = false;

	/**
	 * A counter to keep track when the falling should be executed next, see the
	 * WAIT constant.
	 */
	protected int fallingTimeWaited = 0;

	public AbstractBDFallingObject(BDMap owner) {
		super(owner);
	}

	/**
	 * This method implements the logic of the object falling. It checks whether
	 * it can fall, depending on the object in the tile underneath it and if so,
	 * tries to prepare the move.
	 */
	public void fall() {
		// Wait until its time to fall
		if (falling && fallingTimeWaited < WAIT) {
			fallingTimeWaited++;
			return;
		}
		// The timeout is over, try and prepare the move
		fallingTimeWaited = 0;

		Position pos = owner.getPosition(this);
		// The object cannot fall if it is on the lowest row.
		if (pos.getY() > 0) {
			try {
				// Get the object in the tile below.
				Position below = pos.moveDirection(Direction.SOUTH);
				IBDObject under = owner.get(below);

				if (falling) {
					// fall one step if tile below is empty or killable
					if (under instanceof BDEmpty || under instanceof IBDKillable) {
						prepareMoveTo(Direction.SOUTH);
						
						//"Falling" sideways still possible
					} else {

						boolean fortsett = true;

						if (owner.canGo(pos.moveDirection(Direction.EAST))
								&& owner.canGo(pos.moveDirection(Direction.WEST))) {

							IBDObject right = owner.get(pos.moveDirection(Direction.EAST));
							IBDObject underRight = owner.get(below.moveDirection(Direction.EAST));
							IBDObject left = owner.get(pos.moveDirection(Direction.WEST));
							IBDObject underLeft = owner.get(below.moveDirection(Direction.WEST));

							if (((right instanceof BDEmpty || right instanceof IBDKillable)
									&& (underRight instanceof BDEmpty || underRight instanceof IBDKillable))
									&& ((left instanceof BDEmpty || left instanceof IBDKillable)
											&& (underLeft instanceof BDEmpty || underLeft instanceof IBDKillable))) {

								fortsett = false;

								Random rand = new Random();
								int tall = rand.nextInt(2) + 1;
								System.out.println("\n" + tall);

								if (tall == 2) {
									prepareMove(below.moveDirection(Direction.EAST));
								}
								if (tall == 1) {
									prepareMove(below.moveDirection(Direction.WEST));
								}
							}
						}

						if (owner.canGo(pos.moveDirection(Direction.EAST)) && fortsett) {

							IBDObject right = owner.get(pos.moveDirection(Direction.EAST));
							IBDObject underRight = owner.get(below.moveDirection(Direction.EAST));

							if ((right instanceof BDEmpty || right instanceof IBDKillable)
									&& (underRight instanceof BDEmpty || underRight instanceof IBDKillable)) {
								prepareMove(below.moveDirection(Direction.EAST));
							}

						}

						if (owner.canGo(pos.moveDirection(Direction.WEST)) && fortsett) {

							IBDObject left = owner.get(pos.moveDirection(Direction.WEST));
							IBDObject underLeft = owner.get(below.moveDirection(Direction.WEST));

							if ((left instanceof BDEmpty || left instanceof IBDKillable)
									&& (underLeft instanceof BDEmpty || underLeft instanceof IBDKillable)) {
								prepareMove(below.moveDirection(Direction.WEST));
							}

						}

						falling = false;
					}
				} else {
					// start falling if tile below is empty
					falling = under instanceof BDEmpty;
					fallingTimeWaited = 1;
				}
			} catch (IllegalMoveException e) {
				// This should never happen.
				System.out.println(e);
				System.exit(1);
			}
		}
	}

	@Override
	public void step() {
		
		fall();
		super.step();
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	//TODO
	//No time to complete class with enums for direction
	public movement possibleMovement(Position pos) {
		if (owner.canGo(pos.moveDirection(Direction.WEST)) && owner.canGo(pos.moveDirection(Direction.EAST)))
			return movement.BOTH_OKAY ;
		else if (owner.canGo(pos.moveDirection(Direction.WEST)))
			return movement.LEFT_OKAY;
		else if (owner.canGo(pos.moveDirection(Direction.WEST)))
			return movement.RIGHT_OKAY;
		else
			return movement.NOT_OKAY;
	}

}
