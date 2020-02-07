package inf101.simulator.objects;

import inf101.simulator.Direction;
import inf101.simulator.GraphicsHelper;
import inf101.simulator.Habitat;
import inf101.simulator.MediaHelper;
import inf101.simulator.Position;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class SimBird extends AbstractMovingObject {

	protected static double defaultSpeed = 1.0;
	private Habitat habitat;
	private String imgAdress;
	private int animationCounter = 0;
	public static final int N_SPRITES = 4;

	public SimBird(Position pos, Habitat hab) {
		super(new Direction(0), pos, defaultSpeed);
		imgAdress = "inf101/simulator/images/frame-1.png";
		this.habitat = hab;
	}

	public double getViewDistance() {

		return getRadius() + 80;
	}

	public SimBird getClosestBird() {
		for (ISimObject obj : habitat.nearbyObjects(this, this.getViewDistance())) {
			if (obj instanceof SimBird)
				return (SimBird) obj;
		}
		return null;
	}

	@Override
	public void draw(GraphicsContext context) {
		super.draw(context);

		context.save();
		// Shows viewangle
		// context.setStroke(Color.GREY.deriveColor(0, 1, 1, 0.5));
		// GraphicsHelper.strokeArcAt(context, getWidth() / 2, getHeight() / 2,
		// getViewDistance(), 0, 360);

		// Flips picture when upsidedown
		if (this.getDirection().toAngle() > -90 && this.getDirection().toAngle() < 90) {
			context.translate(0.0, getHeight());
			context.scale(1.0, -1.0);

		}

		context.drawImage(MediaHelper.getImage("inf101/simulator/images/frame-" + animationCounter + ".png"), 0.0, 0.0,
				getWidth(), getHeight());

		// Image img = new Image("inf101/simulator/images/frame-" +
		// animationCounter + ".png");
		// context.drawImage(img, 0.0, 0.0, getWidth(), getHeight());

		context.restore();
	}

	@Override
	public void step() {

		super.step();

		// Fly in flocks
		accelerateTo(defaultSpeed, 0.1);
		if (getClosestBird() != null)
			dir = getClosestBird().getDirection();

		// by default, move slightly towards center
		dir = dir.turnTowards(directionTo(habitat.getCenter()), 0.1);

		// go towards center if we're close to the border
		if (!habitat.contains(getPosition(), getRadius() * 1.2)) {
			dir = dir.turnTowards(directionTo(habitat.getCenter()), 5);

			if (!habitat.contains(getPosition(), getRadius())) {
				// we're actually outside
				accelerateTo(5 * defaultSpeed, 0.3);
			}
		}

		animationCounter = ((animationCounter + 1) % 4) + 1;

	}

	@Override
	public double getHeight() {
		return 30;
	}

	@Override
	public double getWidth() {
		return 30;
	}

}
