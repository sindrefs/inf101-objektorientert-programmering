package inf101.v17.boulderdash.bdobjects;

import java.util.Optional;

//import java.awt.Color;

import inf101.v17.boulderdash.Direction;
import inf101.v17.boulderdash.IllegalMoveException;
import inf101.v17.boulderdash.Position;
import inf101.v17.boulderdash.maps.BDMap;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;

public class BDRock extends AbstractBDFallingObject {

	private Optional<Image> rock = Optional.empty();

	public BDRock(BDMap owner) {
		super(owner);

	}

	@Override
	public Paint getColor() {
		if (!(rock.isPresent())) {
			this.rock = Optional.of(new Image("file:pictures/weight.png"));

		}
		return new ImagePattern(this.rock.get());

	}

	public boolean push(Direction dir) throws Exception {
		if (!(dir.equals(Direction.EAST) || dir.equals(Direction.WEST))) {
			throw new IllegalMoveException("Ugyldig retning");
		}

		Position current = super.getPosition();
		Position target = current.moveDirection(dir);

		IBDObject targetObject = owner.get(target);

		if ((targetObject instanceof BDEmpty) && (owner.canGo(target))) {
			super.prepareMove(target);
			super.step();
			return true;
		}

		return false;

	}
}
