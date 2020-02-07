package inf101.simulator.objects;

import inf101.simulator.Habitat;
import inf101.simulator.Position;

public class SimAnimalFactoryFox implements ISimObjectFactory {

	@Override
	public ISimObject create(Position pos, Habitat hab) {
			return new SimFox(pos, hab);
	}

}
