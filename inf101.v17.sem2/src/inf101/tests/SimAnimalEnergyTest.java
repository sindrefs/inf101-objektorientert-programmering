package inf101.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import inf101.simulator.Habitat;
import inf101.simulator.Position;
import inf101.simulator.SimMain;
import inf101.simulator.objects.SimFox;
import inf101.simulator.objects.SimRabbit;

public class SimAnimalEnergyTest {
	private SimMain main;

	@Test
	public void SimRabbitEnergy() {
		Habitat hab = new Habitat(main, 500, 500);
		SimRabbit sim1 = new SimRabbit(new Position(250, 250), hab);
		hab.addObject(sim1);

		double startEnergy = sim1.getEnergy();
		double startLife = sim1.getLifetime();
		for (int i = 0; i < 100; i++) {
			hab.step();
		}

		// Energy has been used
		assertTrue(sim1.getEnergy() < startEnergy);

		// Life has gone past
		assertTrue(startLife > sim1.getLifetime());

	}

	@Test
	public void SimFoxEnergy() {
		Habitat hab = new Habitat(main, 500, 500);
		SimFox sim1 = new SimFox(new Position(250, 250), hab);
		hab.addObject(sim1);

		double startEnergy = sim1.getEnergy();
		double startLife = sim1.getLifetime();
		for (int i = 0; i < 100; i++) {
			hab.step();
		}

		// Energy has been used
		assertTrue(sim1.getEnergy() < startEnergy);

		// Life has gone past
		assertTrue(startLife > sim1.getLifetime());

	}

	@Before
	public void setup() {
		main = new SimMain();
	}

}
