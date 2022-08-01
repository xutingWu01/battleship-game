package edu.duke.xw218.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

/**
 * Test class for testing the functions of BattleShip
 */
public class BattleShipBoardTest {
  @Test
  /**
   * Tests whether the constructor of battleship works correctly
   */
  public void test_width_and_height() {
    Board<Character> b1 = new BattleShipBoard<Character>(10, 20, 'X');
    assertEquals(10, b1.getWidth());
    assertEquals(20, b1.getHeight());
  }

  /**
   * Tests whether the contructor of battleship works correctly if width or height
   * <=0
   */
  @Test
  public void test_invalid_dimensions() {
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(10, 0, 'X'));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(0, 20, 'X'));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(10, -5, 'X'));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(-8, 20, 'X'));
  }

  /**
   * Test whether each element in expected is what we expected by iterating over
   * the 2d array
   */
  private <T> void checkWhatIsAtBoard(BattleShipBoard<T> b, T[][] expected, Boolean ifSelf) {
    // the board starts empty
    int w = b.getWidth();
    int h = b.getHeight();
    assertEquals(expected.length, b.getWidth());
    assertEquals(expected[0].length, b.getHeight());
    for (int i = 0; i < w; i++) {
      for (int j = 0; j < h; j++) {
        Coordinate coor = new Coordinate(i, j);
        T ans;
        if(ifSelf){
        ans = b.whatIsAtForSelf(coor);
        }else{
          ans = b.whatIsAtForEnemy(coor);
        }
        assertEquals(expected[i][j], ans);
      }
    }
  }

  /**
   * Test what is at board
   */
  @Test
  public void test_what_is_at_board() {
    BattleShipBoard<Character> b = new BattleShipBoard<Character>(2, 3, 'X');
    Character[][] expected;
    expected = new Character[][] { { null, null, null }, { null, null, null } };
    checkWhatIsAtBoard(b, expected, true);
    // b.tryAddShip(new BasicShip(new Coordinate(0, 0)));
    // b.tryAddShip(new RectangleShip<Character>(new Coordinate(0, 0), 's', '*'));
    b.tryAddShip(new RectangleShip<Character>(new Coordinate(0, 0), 's', '*'));
    expected[0][0] = 's';
    checkWhatIsAtBoard(b, expected, true);
  }

  /**
   * Test wheter fire at works properly
   */
  @Test
  public void test_fire_at() {
    Board<Character> board = new BattleShipBoard<>(2, 3, 'X');
    Ship<Character> add_ship = new RectangleShip<Character>(new Coordinate(0, 0), 's', '*');
    board.tryAddShip(add_ship);
    Ship<Character> expect_ship = board.fireAt(new Coordinate(0, 0));
    assertTrue(expect_ship.isSunk());
    assertSame(add_ship, expect_ship);
    // hit miss
    assertEquals(null, board.checkShipExistence(new Coordinate(1, 1)));
    expect_ship = board.fireAt(new Coordinate(1, 1));
    assertSame(null, expect_ship);
    expect_ship = board.fireAt(new Coordinate(0, 0));
    assertSame(add_ship, expect_ship);
    assertSame(add_ship, board.checkShipExistence(new Coordinate(0, 0)));
    assertEquals('X', board.whatIsAtForEnemy(new Coordinate(1, 1)));
    assertEquals('s', board.whatIsAtForEnemy(new Coordinate(0, 0)));
    assertEquals(null, board.whatIsAtForEnemy(new Coordinate(1, 3)));
  }

  /**
   * Test whether one plyaer has won
   */
  @Test
  public void test_win_or_lose() {
    Board<Character> board = new BattleShipBoard<>(2, 3, 'X');
    Ship<Character> add_ship = new RectangleShip<Character>(new Coordinate(0, 0), 's', '*');
    board.tryAddShip(add_ship);
    assertEquals(false, board.winOrlose());
    board.fireAt(new Coordinate(0, 0));
    assertEquals(true, board.winOrlose());
  }

  /**
   * Test the function, sonar scan
   */
  @Test
  public void test_sonar_scan() {
    Board<Character> board = new BattleShipBoard<>(3, 2, 'X');
    V2ShipFactory factory = new V2ShipFactory();
    Placement p = new Placement(new Coordinate(0, 0), 'H', true);
    Ship<Character> ship1 = factory.makeSubmarine(p);
    Placement p1 = new Placement(new Coordinate(1, 0), 'H', true);
    Ship<Character> ship2 = factory.makeDestroyer(p1);
    board.tryAddShip(ship1);
    board.tryAddShip(ship2);
    HashMap<String, Integer> expected = new HashMap<>();
    expected.put("Carrier", 0);
    expected.put("Destroyer", 3);
    expected.put("Submarine", 2);
    expected.put("Battleship", 0);
    assertEquals(expected, board.sonarScan(new Coordinate(0, 0)));
  }

  /**
   * Test add and remove ship from battleship
   */
  @Test
  public void test_add_remove() {
    BattleShipBoard<Character> board = new BattleShipBoard<>(3, 2, 'X');
    //  BoardTextView view = new BoardTextView(board);
    V2ShipFactory factory = new V2ShipFactory();
    Placement p = new Placement(new Coordinate(0, 0), 'H', true);
    Ship<Character> ship1 = factory.makeSubmarine(p);
    board.addShip(ship1);
    assertEquals("That placement is invalid: the ship overlaps another ship.", board.tryAddShip(ship1));
    assertEquals(true, board.removeShip(ship1));
    assertEquals(null, board.tryAddShip(ship1));
    //now the board have one ship
    //change the square for blank for enemy
    board.addToEmptyLocation(ship1);
    Character[][] expected = new Character[][] { { null, null }, { null, null }, { null, null } };
    checkWhatIsAtBoard(board, expected, false);
    board.addToHitsLocation('s', new Coordinate(0,0));
    expected[0][0] = 's';
    checkWhatIsAtBoard(board, expected, false); 
  }
  
  @Test
  public void test_fake(){
    BattleShipBoard<Character> board = new BattleShipBoard<>(3, 2, 'X');
    V2ShipFactory factory = new V2ShipFactory();
    board.addToHitsLocation('t', new Coordinate(0,0));
    board.removeFromHitsLocation(new Coordinate(0,0));
    assertEquals(0, board.getHitsLocationSize());
    board.addToHitsLocation('t', new Coordinate(1,0));
    board.fireAt(new Coordinate(1,0));
    assertEquals(0, board.getHitsLocationSize());
    Ship<Character> ship = factory.makeSubmarine(new Placement("a0h"));
    board.addToEmptyLocation(ship);
    board.fireAt(new Coordinate(0,0));
  }
}
