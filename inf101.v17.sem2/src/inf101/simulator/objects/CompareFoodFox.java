package inf101.simulator.objects;

import java.util.Comparator;

import inf101.simulator.Direction;
import inf101.simulator.Position;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;

public class CompareFoodFox implements Comparator<IEdibleObject> {

	@Override
	public int compare(IEdibleObject a, IEdibleObject b) {
		if(a instanceof SimRabbit && b instanceof SimRabbit)
			return returnMostNutrient(a, b);
		else if (a instanceof SimRabbit) return -1;
		else if (b instanceof SimRabbit) return 1;
		else return returnMostNutrient(a, b);

	}

	private int returnMostNutrient(IEdibleObject a, IEdibleObject b) {
		return Double.compare(b.getNutritionalValue(), a.getNutritionalValue());
	}

}
