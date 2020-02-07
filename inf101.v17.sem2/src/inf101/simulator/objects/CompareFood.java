package inf101.simulator.objects;

import java.util.Comparator;

import inf101.simulator.Direction;
import inf101.simulator.Position;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;

public class CompareFood implements Comparator<IEdibleObject> {

	@Override
	public int compare(IEdibleObject a, IEdibleObject b) {
		return Double.compare(b.getNutritionalValue(), a.getNutritionalValue());
	
	}

}
