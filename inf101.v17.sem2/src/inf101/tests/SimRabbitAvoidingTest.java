package inf101.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import inf101.simulator.Habitat;
import inf101.simulator.Position;
import inf101.simulator.SimMain;
import inf101.simulator.objects.SimRabbit;
import inf101.simulator.objects.SimRepellant;

public class SimRabbitAvoidingTest {
	private SimMain main;

	/**
	 * Test scenario: check that animal turns away from repellant
	 */
	@Test
	public void avoidDangerTest1() {
		Habitat hab = new Habitat(main, 2000, 500);
		SimRabbit sim1 = new SimRabbit(new Position(250, 250), hab);
		SimRepellant rep1 = new SimRepellant(new Position(550, 250));
		hab.addObject(sim1);
		hab.addObject(rep1);

		for (int i = 0; i < 1000; i++) {
			hab.step();
		}
		// we're currently facing away from the repellant
		assertTrue(Math.abs(
				sim1.getPosition().directionTo(rep1.getPosition()).toAngle() - sim1.getDirection().toAngle()) > 90);
		for (int i = 0; i < 1000; i++) {
			hab.step();
		}
	}

	/**
	 * Test scenario: check that animal avoid repellant
	 */
	@Test
	public void avoidDangerTest2() {
		Habitat hab = new Habitat(main, 1000, 1000);
		SimRabbit sim1 = new SimRabbit(new Position(250, 250), hab);
		SimRepellant rep1 = new SimRepellant(new Position(500, 500));
		hab.addObject(sim1);
		hab.addObject(rep1);

		for (int i = 0; i < 50000; i++) {
			hab.step();
			// we should never be touching the repellant
			assertTrue(sim1.getPosition().distanceTo(rep1.getPosition()) > sim1.getRadius() + rep1.getRadius());
		}
	}

	@Before
	public void setup() {
		main = new SimMain();
	}
}
