package edu.duke.xw218.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.Test;

public class BasicShipTest {
  @Test
  public void test_constructor_where() {
    // Iterable<Coordinate> where = new Iterable<Coordinate>();
    /*
     * List<Coordinate> where = new ArrayList<Coordinate>(); Coordinate c1 = new
     * Coordinate(0, 1); Coordinate c2 = new Coordinate(0, 2); Coordinate c3 = new
     * Coordinate(0, 3); where.add(c1); where.add(c2); where.add(c3);
     * HashMap<Coordinate, Boolean> expected = new HashMap<Coordinate, Boolean>();
     * expected.put(c1, false); expected.put(c2, false); expected.put(c3, false); //
     * BasicShip ship = new BasicShip(where); SimpleShipDisplayInfo info = new
     * SimpleShipDisplayInfo('c', 'a'); BasicShip ship = new BasicShip(where, info);
     * assertEquals(expected, ship.getMyPieces());
     */
  }

  @Test
  public void test_coor() {
    Coordinate c = new Coordinate(2, 2);
    // BasicShip ship = new BasicShip(c);
    RectangleShip<Character> ship = new RectangleShip<Character>(c, 's', '*');
    HashMap<Coordinate, Boolean> expected = new HashMap<Coordinate, Boolean>();
    expected.put(c, false);
    assertEquals(expected, ship.getMyPieces());
  }

  @Test
  public void test_occpuies() {
    Coordinate c = new Coordinate(3, 2);
    // BasicShip ship = new BasicShip(c);
    RectangleShip<Character> ship = new RectangleShip<Character>(c, 's', '*');
    assertEquals(true, ship.occupiesCoordinates(c));
    assertEquals(false, ship.occupiesCoordinates(new Coordinate(3, 4)));
  }

  @Test
  void test_get_coor() {
    Coordinate c = new Coordinate(3, 2);
    RectangleShip<Character> ship = new RectangleShip<Character>(c, 's', '*');
    HashSet<Coordinate> expected = new HashSet<>();
    expected.add(c);
    assertEquals(expected, ship.getCoordinates());
  }
}
