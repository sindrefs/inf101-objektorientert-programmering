package inf101.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import inf101.simulator.Habitat;
import inf101.simulator.Position;
import inf101.simulator.SimMain;
import inf101.simulator.objects.SimAnimalKOPI;
import inf101.simulator.objects.examples.SimFeed;

public class SimAnimalBestFoodTest {
	private SimMain main;

	@Before
	public void setup() {
		main = new SimMain();
	}

	/**
	 * Test scenario: food is placed around a sim animal. The animal correctly
	 * identifies the best food.
	 */
	@Test
	public void willFindBestFoodTest() {
		Habitat hab = new Habitat(main, 500, 500);
		SimAnimalKOPI sim1 = new SimAnimalKOPI(new Position(250, 250), hab);
		SimFeed feed1 = new SimFeed(new Position(400, 240), 1.0);
		SimFeed feed2 = new SimFeed(new Position(380, 250), .5);
		SimFeed feed3 = new SimFeed(new Position(350, 260), 2.0);
		SimFeed feed4 = new SimFeed(new Position(340, 240), 1.3);
		hab.addObject(sim1);
		hab.addObject(feed1);
		hab.addObject(feed2);
		hab.addObject(feed3);
		hab.addObject(feed4);
		
		System.out.println(sim1.getBestFood());
		
		assertEquals(feed4, sim1.getClosestFood().get(0));
		assertEquals(feed3, sim1.getBestFood());
	}

}
