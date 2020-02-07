package inf101.simulator.objects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import inf101.simulator.Direction;
import inf101.simulator.GraphicsHelper;
import inf101.simulator.Habitat;
import inf101.simulator.MediaHelper;
import inf101.simulator.Position;
import inf101.simulator.Setup;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class SimAnimal extends AbstractMovingObject implements ISimListener {
	protected Habitat habitat;
	protected double energy = 1;
	protected double lifetime = 1;
	protected String imgAdress;
	protected static double defaultSpeed = 1.0;
	int mateCounter = 500;
	private static Random random = new Random();

	public SimAnimal(Position pos, Habitat hab, String imgAdress) {
		super(new Direction(random.nextInt(180)), pos, defaultSpeed);
		this.habitat = hab;
		habitat.addListener(this, this);
		this.imgAdress = imgAdress;
	}

	@Override
	public void draw(GraphicsContext context) {

		super.draw(context);

		context.save();
		// Shows viewangles
		// showViewangle(context);

		context.scale(-1.0, 1.0);
		context.translate(getWidth() * -1, 0.0);

		// Flips picture when upsidedown
		if (this.getDirection().toAngle() > -90 && this.getDirection().toAngle() < 90) {
			context.translate(0.0, getHeight());
			context.scale(1.0, -1.0);

		}

		context.drawImage(MediaHelper.getImage(imgAdress), 0.0, 0.0, getWidth(), getHeight());

		// Draw energybar
		this.drawBar(context, energy, -3, Color.RED, Color.GREEN);

		// Draw lifetimebar
		this.drawBar(context, lifetime, -1, Color.BLACK, Color.BLACK);

		context.restore();
	}

	private void showViewangle(GraphicsContext context) {
		context.setStroke(Color.GREY.deriveColor(0, 1, 1, 0.5));
		GraphicsHelper.strokeArcAt(context, getWidth() / 2, getHeight() / 2, getRepellantViewDistance(), 0, VIEW_ANGLE);
		GraphicsHelper.strokeArcAt(context, getWidth() / 2, getHeight() / 2, getViewDistance(), 0, VIEW_ANGLE);
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
			if (obj instanceof SimRepellant) {

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
		super.step();

		mateCounter++;
		checkLife();

		// by default, move slightly towards center
		Random generator = new Random();
		double rand = generator.nextDouble();
		dir = dir.turnTowards(directionTo(habitat.getCenter()), rand);

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
			mating();
			if (getClosestFood().size() >= 1)
				eatFood(getClosestFood().get(0));
			// if (findMate() != null)

		}

		loseEnergy();

	}

	public SimAnimal findMate() {
		for (ISimObject obj : habitat.nearbyObjects(this, this.getViewDistance())) {
			if (obj instanceof SimAnimal)
				return (SimAnimal) obj;
		}
		return null;
	}

	public void mating() {
		if (findMate() != null) {
			SimAnimal mate = findMate();
			if (energy >= 0.65 && findMate().getEnergy() >= 0.65 && lifetime <= 0.95 && mateCounter >= 250) {

				dir = dir.turnTowards(directionTo(mate), 2);
				accelerateTo(defaultSpeed + 2.0, 0.3);

				if (distanceToTouch(mate) <= mate.getRadius()) {
					accelerateTo(1.0, 5.0);
					for (int i = 0; i < 1000; i++) {
						dir = dir.turnTowardsPercent(mate.getPosition().directionTo(this.getPosition()), 100);
						dir = dir.turnTowards(this.getPosition().directionTo(mate.getPosition()), 100).turnBack();
					}
					accelerateTo(defaultSpeed, 5.0);

					if (this instanceof SimRabbit)
						Setup.newSimObject(new SimRabbit(this.getPosition(), habitat));
					else if (this instanceof SimFox)
						Setup.newSimObject(new SimFox(this.getPosition(), habitat));

					mateCounter = 0;
					energy = 0.64;

				}
			}
			if (distanceTo(mate) <= 20)
				dir = directionTo(findMate()).turnBack();
		}

	}

	public void setReadyToMate() {
		energy = 1.0;
		lifetime = 0.9;
		mateCounter = 300;
	}

	public double getEnergy() {
		return energy;
	}

	public double getLifetime() {
		return lifetime;
	}

	private void loseEnergy() {
		energy = energy - 0.0004;
		lifetime = lifetime - 0.00001;
	}

	private void checkLife() {
		// Die if no food or too old
		if (energy <= 0 || lifetime <= 0)
			this.destroy();
		// Avoid too much food
		else if (energy > 1)
			energy = 1;
	}

	public void avoidEnemy() {
		ArrayList<AbstractSimObject> enemyList = getRepellantAhead();

		if (enemyList.size() >= 1) {

			for (AbstractSimObject enemy : enemyList) {
				double distanceFactor = (getRepellantViewDistance() - distanceTo(enemy)) / getRepellantViewDistance()
						* 100;


				Direction away = directionTo(enemy).turnBack();
				dir = dir.turnTowardsPercent(away, distanceFactor);
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
					this.accelerateTo(defaultSpeed - 0.5, 5.0);
					food.eat(1);
					energy += 0.2;
				}
			}
			this.accelerateTo(defaultSpeed, 5.0);

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
