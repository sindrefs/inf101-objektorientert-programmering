package inf101.v17.boulderdash.bdobjects.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import inf101.v17.boulderdash.Direction;
import inf101.v17.boulderdash.IllegalMoveException;
import inf101.v17.boulderdash.Position;
import inf101.v17.boulderdash.bdobjects.AbstractBDFallingObject;
import inf101.v17.boulderdash.bdobjects.BDDiamond;
import inf101.v17.boulderdash.bdobjects.IBDObject;
import inf101.v17.boulderdash.gui.BoulderDashGUI;
import inf101.v17.boulderdash.maps.BDMap;
import inf101.v17.datastructures.IGrid;
import inf101.v17.datastructures.MyGrid;
import javafx.scene.input.KeyCode;

public class FallingTest {

	private BDMap mapD;
	private BDMap mapR;

	@Before
	public void setup() {
		// Grid with diamond
		IGrid<Character> gridD = new MyGrid<>(2, 5, ' ');
		gridD.set(0, 4, 'd');
		gridD.set(0, 0, '*');
		mapD = new BDMap(gridD);

		// Grid with rock
		IGrid<Character> gridR = new MyGrid<>(2, 5, ' ');
		gridR.set(0, 4, 'r');
		gridR.set(0, 0, '*');
		mapR = new BDMap(gridR);
	}

	@Test
	public void fallingTest2() {
		checkFallD(new Position(0, 4));
	}

	@Test
	public void fallingTest2R() {
		checkFallR(new Position(0, 4));
	}

	@Test
	public void fallingKills1() {
		// diamond two tiles above kills player
		IGrid<Character> grid = new MyGrid<>(2, 5, ' ');
		grid.set(0, 4, 'd');
		grid.set(0, 2, 'p');
		grid.set(0, 0, '*');
		mapD = new BDMap(grid);

		checkFallD(new Position(0, 4));
		checkFallD(new Position(0, 3));
		checkFallD(new Position(0, 2));
		assertFalse(mapD.getPlayer().isAlive());
	}

	@Test
	public void fallingKills1R() {
		// rock two tiles above kills player
		IGrid<Character> grid = new MyGrid<>(2, 5, ' ');
		grid.set(0, 4, 'r');
		grid.set(0, 2, 'p');
		grid.set(0, 0, '*');
		mapR = new BDMap(grid);

		checkFallR(new Position(0, 4));
		checkFallR(new Position(0, 3));
		checkFallR(new Position(0, 2));
		assertFalse(mapR.getPlayer().isAlive());
	}

	@Test
	public void restingDoesntKill1() {
		// diamond on top of player doesn't kill player
		IGrid<Character> grid = new MyGrid<>(2, 5, ' ');
		grid.set(0, 3, 'd');
		grid.set(0, 2, 'p');
		grid.set(0, 0, '*');
		mapD = new BDMap(grid);

		for (int i = 0; i < 100; i++)
			mapD.step();

		assertTrue(mapD.getPlayer().isAlive());
	}

	@Test
	public void restingDoesntKill1R() {
		// rock on top of player doesn't kill player
		IGrid<Character> grid = new MyGrid<>(2, 5, ' ');
		grid.set(0, 3, 'r');
		grid.set(0, 2, 'p');
		grid.set(0, 0, '*');
		mapD = new BDMap(grid);

		for (int i = 0; i < 100; i++)
			mapD.step();

		assertTrue(mapD.getPlayer().isAlive());
	}

	@Test
	public void fallingTest1() {
		IBDObject obj = mapD.get(0, 4);
		assertTrue(obj instanceof BDDiamond);

		// four steps later, we've fallen down one step
		mapD.step();
		mapD.step();
		mapD.step();
		mapD.step();
		assertEquals(obj, mapD.get(0, 3));

		mapD.step();
		mapD.step();
		mapD.step();
		mapD.step();
		assertEquals(obj, mapD.get(0, 2));

		mapD.step();
		mapD.step();
		mapD.step();
		mapD.step();

		assertEquals(obj, mapD.get(0, 1));

		// wall reached, no more falling
		for (int i = 0; i < 10; i++)
			mapD.step();

		assertEquals(obj, mapD.get(1, 0));
	}

	@Test
	public void fallingTest1R() {
		IBDObject obj = mapD.get(0, 4);
		assertTrue(obj instanceof BDDiamond);

		// four steps later, we've fallen down one step
		mapD.step();
		mapD.step();
		mapD.step();
		mapD.step();
		assertEquals(obj, mapD.get(0, 3));

		mapD.step();
		mapD.step();
		mapD.step();
		mapD.step();
		assertEquals(obj, mapD.get(0, 2));

		mapD.step();
		mapD.step();
		mapD.step();
		mapD.step();

		assertEquals(obj, mapD.get(0, 1));

		// wall reached, no more falling
		for (int i = 0; i < 10; i++)
			mapD.step();

		assertEquals(obj, mapD.get(1, 0));
	}

	@Test
	public void pushTest() {
		IGrid<Character> grid = new MyGrid<>(5, 3, ' ');
		grid.set(3, 0, 'r');
		grid.set(4, 0, 'p');
		grid.set(0, 0, '*');

		mapR = new BDMap(grid);
		IBDObject rock = mapR.get(3, 0);

		for (int i = 0; i < 10; i++) {
			mapR.getPlayer().keyPressed(KeyCode.LEFT);
			mapR.step();
		}
		assertEquals(mapR.get(1, 0), rock);

	}

	protected Position checkFallD(Position pos) {
		IBDObject obj = mapD.get(pos);
		if (obj instanceof AbstractBDFallingObject) {
			Position next = pos.moveDirection(Direction.SOUTH);
			if (mapD.canGo(next)) {
				IBDObject target = mapD.get(next);
				if (target.isEmpty() || target.isKillable()) {
				} else {
					next = pos;
				}
			} else {
				next = pos;
			}

			// map.step(); System.out.println(map.getPosition(object));
			mapD.step();
			mapD.step();
			mapD.step();
			mapD.step();
			assertEquals(obj, mapD.get(next));
			return next;
		} else
			return pos;
	}

	protected Position checkFallR(Position pos) {
		IBDObject obj = mapR.get(pos);
		if (obj instanceof AbstractBDFallingObject) {
			Position next = pos.moveDirection(Direction.SOUTH);
			if (mapR.canGo(next)) {
				IBDObject target = mapR.get(next);
				if (target.isEmpty() || target.isKillable()) {
				} else {
					next = pos;
				}
			} else {
				next = pos;
			}

			// map.step(); System.out.println(map.getPosition(object));
			mapR.step();
			mapR.step();
			mapR.step();
			mapR.step();
			assertEquals(obj, mapR.get(next));
			return next;
		} else
			return pos;
	}

}
