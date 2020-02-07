package inf101.simulator;

import java.util.ArrayDeque;
import java.util.Stack;

import inf101.simulator.objects.ISimObject;
import inf101.simulator.objects.SimAnimalFactory;
import inf101.simulator.objects.SimAnimalFactoryBird;
import inf101.simulator.objects.SimAnimalFactoryFox;
import inf101.simulator.objects.SimApple;
import inf101.simulator.objects.SimBird;
import inf101.simulator.objects.SimRabbit;
import inf101.simulator.objects.SimRepellant;

public class Setup {

	private static ArrayDeque<ISimObject> newObjects = new ArrayDeque<>();

	/** This method is called when the simulation starts */
	public static void setup(SimMain main, Habitat habitat) {
		// Old chick
		// habitat.addObject(new SimAnimalKOPI(new Position(400, 400),
		// habitat));

		// Blob does nothing
		// habitat.addObject(new Blob(new Direction(0), new Position(400, 400),
		// 1));

		// Initialize rabbits
		for (int i = 0; i < 3; i++)
			habitat.addObject(new SimRabbit(main.randomPos(), habitat));

		//-birds
		for (int i = 0; i < 15; i++)
			habitat.addObject(new SimBird(main.randomPos(), habitat));

		//-repellants
		for (int i = 0; i < 6; i++)
			habitat.addObject(new SimRepellant(main.randomPos()));

		// Old SimRepellant
		// SimMain.registerSimObjectFactory(
		// (Position pos, Habitat hab) -> new SimFeed(pos,
		// main.getRandom().nextDouble() * 2 + 0.5), "SimFeed™",
		// SimFeed.PAINTER);

		// Fire
		SimMain.registerSimObjectFactory((Position pos, Habitat hab) -> new SimRepellant(pos), "SimRepellant™",
				"fire1.png");

		// Apple
		SimMain.registerSimObjectFactory(
				(Position pos, Habitat hab) -> new SimApple(pos, main.getRandom().nextDouble() * 2 + 0.5), "Apple™",
				"apple.png");

		// Rabbit
		SimAnimalFactory factory = new SimAnimalFactory();
		SimMain.registerSimObjectFactory(factory, "SimChick", "rabbit.png");

		// Fox
		SimAnimalFactoryFox factoryFox = new SimAnimalFactoryFox();
		SimMain.registerSimObjectFactory(factoryFox, "SimFox", "fox.png");

		// Bird
		SimAnimalFactoryFox factoryBird = new SimAnimalFactoryBird();
		SimMain.registerSimObjectFactory(factoryBird, "SimBird", "frame-1.png");

	}

	/**
	 * This method is called for each step, you can use it to add objects at
	 * random intervals
	 */
	public static void step(SimMain main, Habitat habitat) {
		if (main.getRandom().nextInt(100) == 0) {
			// habitat.addObject(new SimFeed(main.randomPos(),
			// main.getRandom().nextDouble() * 2 + 0.5));
			habitat.addObject(new SimApple(main.randomPos(), main.getRandom().nextDouble() * 2 + 0.5));
		}

		if (main.getRandom().nextInt(5000) == 0) {
			habitat.addObject(new SimRepellant(main.randomPos()));
		}

		while (!newObjects.isEmpty()) {
			// System.out.println(newObjects.size());
			habitat.addObject(newObjects.pop());
		}
	}

	public static void newSimObject(ISimObject o) {
		newObjects.add(o);
	}
}
