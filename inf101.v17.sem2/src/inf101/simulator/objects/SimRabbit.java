package inf101.simulator.objects;

import java.util.ArrayList;

import inf101.simulator.Habitat;
import inf101.simulator.Position;

public class SimRabbit extends SimAnimal implements IEdibleObject {

	private static final int NUTRITION_FACTOR = 11;
	private double size = 1.0;

	public SimRabbit(Position pos, Habitat hab) {
		super(pos, hab, "inf101/simulator/images/rabbit.png");
	}

	@Override
	public SimAnimal findMate() {
		for (ISimObject obj : habitat.nearbyObjects(this, this.getViewDistance())) {
			if (obj instanceof SimRabbit)
				return (SimAnimal) obj;
		}
		return null;
	}

	@Override
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
	public ArrayList<IEdibleObject> getClosestFood() {
		ArrayList<IEdibleObject> foodList = new ArrayList<>();

		for (ISimObject obj : habitat.nearbyObjects(this, this.getViewDistance())) {
			if (obj instanceof IEdibleObject && !(obj instanceof SimRabbit))

				if (Math.abs(this.getDirection().toAngle()
						- this.getPosition().directionTo(obj.getPosition()).toAngle()) < VIEW_ANGLE)

					foodList.add((IEdibleObject) obj);
		}

		return foodList;
	}

	@Override
	public double eat(double howMuch) {
		double deltaSize = Math.min(size, howMuch / NUTRITION_FACTOR);
		size -= deltaSize;
		if (size == 0)
			destroy();
		return deltaSize * NUTRITION_FACTOR;
	}

	@Override
	public double getNutritionalValue() {
		return size * NUTRITION_FACTOR;
	}

	

}
