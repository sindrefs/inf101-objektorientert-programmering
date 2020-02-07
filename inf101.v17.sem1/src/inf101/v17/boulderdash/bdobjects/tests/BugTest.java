package inf101.v17.boulderdash.bdobjects.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import inf101.v17.boulderdash.Direction;
import inf101.v17.boulderdash.IllegalMoveException;
import inf101.v17.boulderdash.Position;
import inf101.v17.boulderdash.bdobjects.AbstractBDFallingObject;
import inf101.v17.boulderdash.bdobjects.BDBug;
import inf101.v17.boulderdash.bdobjects.BDDiamond;
import inf101.v17.boulderdash.bdobjects.BDPlayer;
import inf101.v17.boulderdash.bdobjects.IBDObject;
import inf101.v17.boulderdash.maps.BDMap;
import inf101.v17.datastructures.IGrid;
import inf101.v17.datastructures.MyGrid;

public class BugTest {

	private BDMap map;

	@Before
	public void setup() {
	}

	@Test
	public void bugMoves() {
		IGrid<Character> grid = new MyGrid<>(4, 4, ' ');
		grid.set(2, 2, 'b');
		map = new BDMap(grid);

		// find the bug
		Position bugStart = new Position(2, 2);
		IBDObject bug = map.get(bugStart);
		assertTrue(bug instanceof BDBug);

		for (int i = 0; i < 100; i++) {
			map.step();
			if (map.get(bugStart) != bug) { // bug has moved
				// reported position should be different
				assertNotEquals(bugStart, map.getPosition(bug));
				// bug moved –  we're done
				return;
			}

		}

		fail("Bug should have moved in 100 steps!");
	}

	@Test
	public void lockedInWall() {
		// Generate bug and grid
		IGrid<Character> grid = new MyGrid<>(4, 4, '*');
		grid.set(2, 2, 'b');
		map = new BDMap(grid);
		Position bugStart = new Position(2, 2);
		IBDObject bug = map.get(bugStart);
		assertTrue(bug instanceof BDBug);

		for (int i = 0; i < 100; i++) {
			map.step();
			if (map.get(bugStart) == bug) {
				// reported position should be the same starting position
				assertEquals(bugStart, map.getPosition(bug));
				return;
			}
			fail("Bug should not have moved in 100 steps!");

		}
	}

	@Test
	public void lockedInSand() {
		//Generate bug and grid
		IGrid<Character> grid = new MyGrid<>(4, 4, '#');
		grid.set(2, 2, 'b');
		map = new BDMap(grid);
		Position bugStart = new Position(2, 2);
		IBDObject bug = map.get(bugStart);
		assertTrue(bug instanceof BDBug);
		

		for (int i = 0; i < 100; i++) {
			map.step();
			if (map.get(bugStart) == bug) { 
				// reported position should be the same starting position
				assertEquals(bugStart, map.getPosition(bug));
				return;
			}
			fail("Bug should not have moved in 100 steps!");
		}
	}

	@Test
	public void moveAround() {

		// Generate bug and grid
		IGrid<Character> grid = new MyGrid<>(8, 8, ' ');
		grid.set(4, 4, 'b');
		map = new BDMap(grid);
		Position bugStart = new Position(4, 4);
		IBDObject bug = map.get(bugStart);
		assertTrue(bug instanceof BDBug);

		// Expected pattern of movement
		Position west = bugStart.moveDirection(Direction.WEST);
		Position north = west.moveDirection(Direction.NORTH);
		Position east = north.moveDirection(Direction.EAST);
		Position south = east.moveDirection(Direction.SOUTH);

		// For debugging
		// System.out.println("Starter i: " + map.getPosition(bug) + "\n \n");

		// Move the bug, and checking positions
		for (int i = 0; i < 100; i++) {
			map.step();
			if (map.get(west) == bug)
				assertEquals(west, map.getPosition(bug));
			else if (map.get(east) == bug)
				assertEquals(east, map.getPosition(bug));
			else if (map.get(north) == bug)
				assertEquals(north, map.getPosition(bug));
			else if (map.get(south) == bug)
				assertEquals(south, map.getPosition(bug));
			else {
				fail("The bug isn't present in the excepted positions");
			}

		}

	}

	@Test
	public void killPlayer() {

		try {
			// Generate bug and grid
			IGrid<Character> grid = new MyGrid<>(4, 4, ' ');
			map = new BDMap(grid);
			Position bugStart = new Position(2, 2);
			IBDObject bug = new BDBug(map, bugStart);
			map.set(2, 2, bug);
			assertTrue(bug instanceof BDBug);

			// Move bug from start position
			while (bugStart.equals(map.getPosition(bug))) {
				map.step();
			}

			// Generate player
			IBDObject player = new BDPlayer(map);
			map.set(2, 2, player);
			assertTrue(player instanceof BDPlayer);

			// For debugging
			// System.out.println(player.getPosition());

			for (int i = 0; i < 100; i++) {
				map.step();
				// For debugging
				// System.out.println("P: " + player.getPosition());
				// System.out.println("B: " + bug.getPosition());
			}

			assertFalse((((BDPlayer) player).isAlive()));

		} catch (IllegalMoveException e) {
			e.printStackTrace();
		}
	}
}
