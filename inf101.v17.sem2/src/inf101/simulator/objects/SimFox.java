package inf101.simulator.objects;

import java.util.ArrayList;
import java.util.Collections;

import inf101.simulator.Habitat;
import inf101.simulator.Position;

public class SimFox extends SimAnimal {

	public SimFox(Position pos, Habitat hab) {
		super(pos, hab, "inf101/simulator/images/fox.png");
	}
	

	@Override
	public SimAnimal findMate() {
		for (ISimObject obj : habitat.nearbyObjects(this, this.getViewDistance())) {
			if (obj instanceof SimFox)
				return (SimAnimal) obj;
		}
		return null;
	}
	
	@Override
	public IEdibleObject getBestFood() {
		ArrayList<IEdibleObject> foodList = getClosestFood();

		Collections.sort(foodList, new CompareFoodFox());

		return (foodList.isEmpty() ? null : foodList.get(0));
	}

	@Override
	public double getViewDistance() {

		return this.getRadius() + 200;
	}

	@Override
	public void eatFood(IEdibleObject food) {
		if (getBestFood() != null) {

			dir = dir.turnTowards(directionTo(food), 2);
			
			// Does only bother to run if it sees a rabbit
			if (getBestFood() instanceof SimRabbit)
				accelerateTo(defaultSpeed + 1.0, 0.3);

			// Eat the food
			if (energy < 1) {
				if (distanceToTouch(food) <= food.getRadius()) {
					food.eat(1);
					energy += 0.2;
					

				}
			}

		}
	}

}
