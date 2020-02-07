package inf101.v17.boulderdash.bdobjects;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;

import java.util.Optional;

import inf101.v17.boulderdash.maps.BDMap;

/**
 * Implementation of a piece of a wall.
 *
 * @author larsjaffke
 *
 */
public class BDWall extends AbstractBDObject {

	private Optional<Image> wall = Optional.empty();

	public BDWall(BDMap owner) {
		super(owner);
	}

	@Override
	public Paint getColor() {
		if (!(wall.isPresent())) {
			this.wall = Optional.of(new Image("file:pictures/castleCenter.png"));

		}
		return new ImagePattern(this.wall.get());
	}

	@Override
	public void step() {
		// DO NOTHING, IT'S A WALL
	}
}
