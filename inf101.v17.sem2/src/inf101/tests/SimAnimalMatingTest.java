package inf101.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import inf101.simulator.Habitat;
import inf101.simulator.Position;
import inf101.simulator.SimMain;
import inf101.simulator.objects.SimFox;
import inf101.simulator.objects.SimRabbit;

public class SimAnimalMatingTest {
	private SimMain main;

	@Before
	public void setup() {
		main = new SimMain();
	}

	@Test
	public void rabbitMating() {
		Habitat hab = new Habitat(main, 100, 100);
		SimRabbit sim1 = new SimRabbit(new Position(25, 25), hab);
		SimRabbit sim2 = new SimRabbit(new Position(30, 30), hab);
		
		sim1.setReadyToMate();
		sim2.setReadyToMate();
		
		hab.addObject(sim1);
		hab.addObject(sim2);
		
		
		
		for (int i = 0; i < 10000; i++) {
			sim1.setReadyToMate();
			sim2.setReadyToMate();
			//sim1.mating();
			//sim2.mating();
			hab.step();
			
			System.out.println(hab.allObjects().size());
		}

		//assertTrue(hab.allObjects().size() > 2);

	}
}
