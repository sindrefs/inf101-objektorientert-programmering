package inf101.simulator.objects;

import java.util.ArrayList;
import java.util.function.Consumer;

import inf101.simulator.Direction;
import inf101.simulator.GraphicsHelper;
import inf101.simulator.MediaHelper;
import inf101.simulator.Position;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

public class SimRepellant extends AbstractSimObject {

	public static final int N_SPRITES = 3;
	private int animationCounter = 0;
	private int stepWait = 0;
	private static final double DIAMETER = 55;

	public SimRepellant(Position pos) {
		super(new Direction(0), pos);
	}

	@Override
	public void draw(GraphicsContext context) {

		context.save();
		context.rotate(180);

		context.drawImage(MediaHelper.getImage("fire.png"), 32 * animationCounter, 32 * animationCounter, 32, 32, 0, 0,
				getWidth(), getHeight());
		context.restore();

	}

	@Override
	public double getHeight() {
		return DIAMETER;
	}

	@Override
	public double getWidth() {
		return DIAMETER;
	}

	@Override
	public void step() {
		stepWait++;
		if (stepWait == 10) {
			animationCounter = (animationCounter + 1) % N_SPRITES;
			stepWait = 0;
		}

	}
}
