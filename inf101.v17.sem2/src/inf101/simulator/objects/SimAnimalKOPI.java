package inf101.simulator.objects;

import java.awt.List;
import java.util.ArrayList;
import java.util.Collections;

import inf101.simulator.Direction;
import inf101.simulator.GraphicsHelper;
import inf101.simulator.Habitat;
import inf101.simulator.MediaHelper;
import inf101.simulator.Position;
import inf101.simulator.SimMain;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class SimAnimalKOPI extends AbstractMovingObject implements ISimListener{
	private static final double defaultSpeed = 1.0;
	private Habitat habitat;
	private double energy = 1;
	private double lifetime = 1;

	public SimAnimalKOPI(Position pos, Habitat hab) {
		super(new Direction(0), pos, defaultSpeed);
		this.habitat = hab;
		habitat.addListener(this, this);
	}

	@Override
	public void draw(GraphicsContext context) {
		super.draw(context);

		// Shows viewangle, and
		context.setStroke(Color.GREY.deriveColor(0, 1, 1, 0.5));
		GraphicsHelper.strokeArcAt(context, getWidth() / 2, getHeight() / 2, getViewDistance(), 0, VIEW_ANGLE);
		GraphicsHelper.strokeArcAt(context, getWidth() / 2, getHeight() / 2, getRepellantViewDistance(), 0, VIEW_ANGLE);

		// Flips picture when uposidedown
		if (this.getDirection().toAngle() > -90 && this.getDirection().toAngle() < 90) {
			context.translate(0.0, 50.0);
			context.scale(1.0, -1.0);
		}

		Image pipp = new Image("inf101/simulator/images/pipp.png");
		context.drawImage(pipp, 0.0, 0.0, getWidth(), getHeight());

		// Draw energybar
		this.drawBar(context, energy, -3, Color.RED, Color.GREEN);

		// Draw lifetimebar
		this.drawBar(context, lifetime, -1, Color.BLACK, Color.BLACK);
	}

	public double getViewDistance() {

		return getRadius() + 400;
	}

	public double getRepellantViewDistance() {

		return getRadius() + 200;
	}

	public IEdibleObject getBestFood() {
		ArrayList<IEdibleObject> foodList = getClosestFood();

		Collections.sort(foodList, new CompareFood());

		return (foodList.isEmpty() ? null : foodList.get(0));
	}

	public ArrayList<IEdibleObject> getClosestFood() {
		ArrayList<IEdibleObject> foodList = new ArrayList<>();

		for (ISimObject obj : habitat.nearbyObjects(this, this.getViewDistance())) {
			if (obj instanceof IEdibleObject)

				if (Math.abs(this.getDirection().toAngle()
						- this.getPosition().directionTo(obj.getPosition()).toAngle()) < VIEW_ANGLE)

					foodList.add((IEdibleObject) obj);
		}

		return foodList;
	}

	public ArrayList<AbstractSimObject> getRepellantAhead() {

		ArrayList<AbstractSimObject> enemyList = new ArrayList<>();
		for (ISimObject obj : habitat.nearbyObjects(this, getRepellantViewDistance())) {
			if (obj instanceof SimRepellant || obj instanceof SimFox) {
				
				if (Math.abs(this.getDirection().toAngle()
						- this.getPosition().directionTo(obj.getPosition()).toAngle()) < VIEW_ANGLE)

					enemyList.add((AbstractSimObject) obj);
			}
		}

		return enemyList;
	}

	@Override
	public double getHeight() {
		return 50;
	}

	@Override
	public double getWidth() {
		return 50;
	}

	@Override
	public void step() {

		// Die if no food or too old
		if (energy <= 0 || lifetime <= 0)
			this.destroy();
		// Avoid too much food
		else if (energy > 1)
			energy = 1;

		// by default, move slightly towards center IF energy
		dir = dir.turnTowards(directionTo(habitat.getCenter()), 0.5);

		// go towards center if we're close to the border
		if (!habitat.contains(getPosition(), getRadius() * 1.2)) {
			dir = dir.turnTowards(directionTo(habitat.getCenter()), 5);
			if (!habitat.contains(getPosition(), getRadius())) {
				// we're actually outside
				accelerateTo(5 * defaultSpeed, 0.3);
			}
		}

		accelerateTo(defaultSpeed, 0.1);

		// Food is closer than repellant
		if (getClosestFood().size() >= 1 && getRepellantAhead().size() >= 1) {
			if (this.distanceTo(getClosestFood().get(0)) < this.distanceTo(getRepellantAhead().get(0)))
				eatFood(getClosestFood().get(0));
			else
				avoidEnemy();
		} else {
			avoidEnemy();
			eatFood(getBestFood());
		}

		energy = energy - 0.0004;
		lifetime = lifetime - 0.00001;
		super.step();
	}

	public void avoidEnemy() {
		ArrayList<AbstractSimObject> enemyList = getRepellantAhead();

		if (enemyList.size() >= 1) {

			for (AbstractSimObject enemy : enemyList) {
				double distance = (getRepellantViewDistance() - distanceTo(enemy)) / 10;
				Direction away = directionTo(enemy).turnBack();
				dir = dir.turnTowardsPercent(away, distance);
			}
			habitat.triggerEvent(new SimEvent(enemyList.get(0), "Run!", this, null));

		}
	}

	public void eatFood(IEdibleObject food) {
		if (getBestFood() != null) {

			dir = dir.turnTowards(directionTo(food), 2);
			accelerateTo(defaultSpeed + 0.5, 0.3);

			// Eat the food
			if (energy < 1) {
				if (distanceToTouch(food) <= food.getRadius()) {
					food.eat(1);
					energy += 0.2;
				}
			}

		}
	}

	@Override
	public void eventHappened(SimEvent event) {
		if (getRepellantAhead().size() >= 1) {
			if (event.getSource().equals(getRepellantAhead().get(0)))
				say("Run!");
		}

	}
}
