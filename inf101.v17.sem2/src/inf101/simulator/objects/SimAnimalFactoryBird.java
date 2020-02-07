package inf101.simulator.objects;

import inf101.simulator.Habitat;
import inf101.simulator.Position;

public class SimAnimalFactoryBird extends SimAnimalFactoryFox {
	
	@Override
	public ISimObject create(Position pos, Habitat hab) {
			return new SimBird(pos, hab);
	}
}
