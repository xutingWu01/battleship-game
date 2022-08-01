package edu.duke.xw218.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.HashSet;

import org.junit.jupiter.api.Test;

public class RectangleShipTest {
  @Test
  public void test_make_coords() {
    Coordinate c = new Coordinate(2, 3);
    int width = 2;
    int height = 2;
    HashSet<Coordinate> expected = new HashSet<Coordinate>();
    expected.add(new Coordinate(2, 3));
    expected.add(new Coordinate(2, 4));
    expected.add(new Coordinate(3, 3));
    expected.add(new Coordinate(3, 4));
    HashSet<Coordinate> ans = RectangleShip.makeCoords(c, width, height);

    assertEquals(expected, ans);
  }

  @Test
  public void test_super() {
    // BasicShip ship = new RectangleShip(new Coordinate(3, 2), 2, 2);
    SimpleShipDisplayInfo info = new SimpleShipDisplayInfo('c', 'a');
    SimpleShipDisplayInfo e_info = new SimpleShipDisplayInfo(null, 'c');
    RectangleShip<Character> ship = new RectangleShip<Character>("testship", new Coordinate(3, 2), 2, 2, info, e_info);
    HashSet<Coordinate> expected = RectangleShip.makeCoords(new Coordinate(3, 2), 2, 2);
    HashMap<Coordinate, Boolean> ans = ship.getMyPieces();
    assertEquals("testship", ship.getName());
    /*
     * for (int i = 0; i < ans.size(); ++i) { assertEquals(expected[i], ans[i]);
     * assertEquals(false, ans.get(ans[i])); }
     */
    for (Coordinate e : expected) {
      assertEquals(false, ans.get(e));
    }
    // assertEquals(expected, ship.getMyPieces());
  }

  @Test
  public void test_hit() {
    Coordinate c = new Coordinate(1, 2);
    RectangleShip<Character> s = new RectangleShip<Character>("testhit",c, 2, 2,'s', '*');
    s.recordHitAt(c);
    assertEquals(true, s.wasHitAt(c));
    Coordinate c1 = new Coordinate(10, 4);
    //assertEquals(false, s.wasHitAt(c1));
    Coordinate c2 = new Coordinate(2,2);
    assertEquals(false, s.wasHitAt(c2));
    assertEquals('*', s.getDisplayInfoAt(c, true));
    assertEquals('s', s.getDisplayInfoAt(c2, true));
    assertEquals('s', s.getDisplayInfoAt(c, false));
    assertEquals(null, s.getDisplayInfoAt(c2, false));
    assertThrows(IllegalArgumentException.class, ()->s.wasHitAt(c1));
  }

  @Test
  public void test_sunk(){
     Coordinate c = new Coordinate(1, 2);
     RectangleShip<Character> s = new RectangleShip<Character>("a", c, 1, 1,'s','*');
     assertEquals(false, s.isSunk()); 
     s.recordHitAt(c);
     assertEquals(true, s.isSunk());
  }
}
