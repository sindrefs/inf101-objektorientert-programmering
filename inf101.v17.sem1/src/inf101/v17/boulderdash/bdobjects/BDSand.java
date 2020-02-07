package inf101.v17.boulderdash.bdobjects;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;

import java.util.Optional;

import inf101.v17.boulderdash.maps.BDMap;

/**
 * An implementation of sand which simply disappears when the player walks over
 * it. Nothing to do here.
 *
 * @author larsjaffke
 *
 */
public class BDSand extends AbstractBDObject {
	private Optional<Image> sand = Optional.empty();

	public BDSand(BDMap owner) {
		super(owner);
	}

	@Override
	public Paint getColor() {
		// return Color.SANDYBROWN;
		// Image sand = new Image("file:pictures/sandCenter.png");
		// return new ImagePattern(sand);

		if (!(sand.isPresent())) {
			this.sand = Optional.of(new Image("file:pictures/sandCenter.png"));

		}
		return new ImagePattern(this.sand.get());
	}

	@Override
	public void step() {
		// DO NOTHING
	}
}
