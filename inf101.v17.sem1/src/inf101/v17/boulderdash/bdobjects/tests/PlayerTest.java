package inf101.v17.boulderdash.bdobjects.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import inf101.v17.boulderdash.bdobjects.IBDObject;
import inf101.v17.boulderdash.maps.BDMap;
import inf101.v17.datastructures.IGrid;
import inf101.v17.datastructures.MyGrid;
import javafx.scene.input.KeyCode;

public class PlayerTest {

	@Test
	public void pushRock() {
		IGrid<Character> grid = new MyGrid<>(6, 6, ' ');
		grid.set(5, 0, 'p');
		grid.set(4, 0, 'r');
		grid.set(0, 0, '*');
		BDMap map = new BDMap(grid);

		IBDObject rock = map.get(4, 0);

		for (int i = 0; i < 10; i++) {
			map.getPlayer().keyPressed(KeyCode.LEFT);
			map.step();
		}
		// Rock has been pushed by the player
		assertEquals(map.get(1, 0), rock);
		assertEquals(map.get(2, 0), map.getPlayer());
	}

	@Test
	public void pushRockToFall() {
		IGrid<Character> grid = new MyGrid<>(6, 6, ' ');
		grid.set(5, 3, 'p');
		grid.set(4, 3, 'r');
		grid.set(4, 2, 'r');
		BDMap map = new BDMap(grid);

		IBDObject rock = map.get(4, 3);

		map.getPlayer().keyPressed(KeyCode.LEFT);
		for (int i = 0; i < 15; i++) {
			map.step();
		}

		// Rock has fallen down
		assertEquals(map.get(3, 0), rock);
	}

	@Test
	public void pickUpDiamond() {
		IGrid<Character> grid = new MyGrid<>(4, 3, ' ');
		grid.set(3, 0, 'p');
		grid.set(2, 0, 'd');
		grid.set(1, 0, 'd');
		BDMap map = new BDMap(grid);

		for (int i = 0; i < 3; i++) {
			map.getPlayer().keyPressed(KeyCode.LEFT);
			map.step();
		}

		// Player picked up the diamond
		assertEquals(map.getPlayer().numberOfDiamonds(), 2);
	}

}
