package inf101.v17.boulderdash.bdobjects;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;

import java.util.Optional;

import inf101.v17.boulderdash.maps.BDMap;

/**
 * A diamond object. All its logic is implemented in the abstract superclass.
 *
 * @author larsjaffke
 *
 */
public class BDDiamond extends AbstractBDFallingObject {

	private Optional<Image> diamond  = Optional.empty();

	
	public BDDiamond(BDMap owner) {
		super(owner);
	}

	@Override
	public Paint getColor() {
		
		if (!(diamond.isPresent())) {
			this.diamond = Optional.of(new Image("file:pictures/coinGold.png"));

		}
		return new ImagePattern(this.diamond.get());
	
	}
	

}
