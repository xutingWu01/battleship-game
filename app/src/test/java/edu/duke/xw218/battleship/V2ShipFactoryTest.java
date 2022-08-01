package edu.duke.xw218.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class V2ShipFactoryTest {
  @Test
  public void test_makeSubmarine() {
    Placement p = new Placement(new Coordinate(0, 0), 'H', true);
    V2ShipFactory factory = new V2ShipFactory();
    Ship<Character> ship1 = factory.makeSubmarine(p);
    Board<Character> board = new BattleShipBoard<Character>(3, 5, 'X');
    BoardTextView view = new BoardTextView(board);
    board.tryAddShip(ship1);
    String expectedHeader = "  0|1|2\n";
    String expected = expectedHeader + "A s|s|  A\n" + "B  | |  B\n" + "C  | |  C\n" + "D  | |  D\n" + "E  | |  E\n"
        + expectedHeader;
    assertEquals(expected, view.displayMyOwnBoard());
  }

  @Test
  public void test_makeDestroyer() {
    Placement p = new Placement(new Coordinate(0, 0), 'H', true);
    V2ShipFactory factory = new V2ShipFactory();
    Ship<Character> ship1 = factory.makeDestroyer(p);
    Board<Character> board = new BattleShipBoard<Character>(3, 5, 'X');
    BoardTextView view = new BoardTextView(board);
    board.tryAddShip(ship1);
    String expectedHeader = "  0|1|2\n";
    String expected = expectedHeader + "A d|d|d A\n" + "B  | |  B\n" + "C  | |  C\n" + "D  | |  D\n" + "E  | |  E\n"
        + expectedHeader;
    assertEquals(expected, view.displayMyOwnBoard());
  }

  @Test
  public void test_makeCarrier() {
    Placement p = new Placement(new Coordinate(0, 1), 'U', false);
    V2ShipFactory factory = new V2ShipFactory();
    Ship<Character> ship1 = factory.makeCarrier(p);
    Board<Character> board = new BattleShipBoard<Character>(3, 5, 'X');
    BoardTextView view = new BoardTextView(board);
    board.tryAddShip(ship1);
    String expectedHeader = "  0|1|2\n";
    String expected = expectedHeader + "A  |c|  A\n" + "B  |c|  B\n" + "C  |c|c C\n" + "D  |c|c D\n" + "E  | |c E\n"
        + expectedHeader;
    assertEquals(expected, view.displayMyOwnBoard());
  }

  @Test
  public void test_makeCarrier_right_left() {
    Placement p = new Placement(new Coordinate(0, 0), 'R', false);
    V2ShipFactory factory = new V2ShipFactory();
    Ship<Character> ship1 = factory.makeCarrier(p);
    Board<Character> board = new BattleShipBoard<Character>(5, 5, 'X');
    BoardTextView view = new BoardTextView(board);
    board.tryAddShip(ship1);
    Ship<Character> ship2 = factory.makeCarrier(new Placement("C0L", false));
    board.tryAddShip(ship2);
    String expectedHeader = "  0|1|2|3|4\n";
    String expected = expectedHeader + "A  |c|c|c|c A\n" + "B c|c|c| |  B\n" + "C  | |c|c|c C\n" + "D c|c|c|c|  D\n"
        + "E  | | | |  E\n" + expectedHeader;
    assertEquals(expected, view.displayMyOwnBoard());
  }

  @Test
  public void test_makeCarrier_down() {
    Placement p = new Placement(new Coordinate(0, 0), 'D', false);
    V2ShipFactory factory = new V2ShipFactory();
    Ship<Character> ship1 = factory.makeCarrier(p);
    Board<Character> board = new BattleShipBoard<Character>(5, 5, 'X');
    BoardTextView view = new BoardTextView(board);
    board.tryAddShip(ship1);
    // Ship<Character> ship2 = factory.makeCarrier(new Placement("C1L"));
    // board.tryAddShip(ship2);
    String expectedHeader = "  0|1|2|3|4\n";
    String expected = expectedHeader + "A c| | | |  A\n" + "B c|c| | |  B\n" + "C c|c| | |  C\n" + "D  |c| | |  D\n"
        + "E  |c| | |  E\n" + expectedHeader;
     assertEquals("Carrier", ship1.getName()); 
    assertEquals(expected, view.displayMyOwnBoard());
  }

  @Test
  public void test_makeBattleShip() {
    Placement p = new Placement(new Coordinate(0, 0), 'U', false);
    V2ShipFactory factory = new V2ShipFactory();
    Ship<Character> ship1 = factory.makeBattleship(p);
    Board<Character> board = new BattleShipBoard<Character>(5, 5, 'X');
    BoardTextView view = new BoardTextView(board);
    board.tryAddShip(ship1);
    // Ship<Character> ship2 = factory.makeCarrier(new Placement("C1L"));
    // board.tryAddShip(ship2);
    String expectedHeader = "  0|1|2|3|4\n";
    String expected = expectedHeader + "A  |b| | |  A\n" + "B b|b|b| |  B\n" + "C  | | | |  C\n" + "D  | | | |  D\n"
        + "E  | | | |  E\n" + expectedHeader;
    assertEquals(expected, view.displayMyOwnBoard());
    Ship<Character> ship2 = factory.makeBattleship(new Placement("C0R", false));
    board.tryAddShip(ship2);
    String expected_1 = expectedHeader + "A  |b| | |  A\n" + "B b|b|b| |  B\n" + "C b| | | |  C\n" + "D b|b| | |  D\n"
        + "E b| | | |  E\n" + expectedHeader;
    assertEquals(expected_1, view.displayMyOwnBoard());

    Ship<Character> ship3 = factory.makeBattleship(new Placement("C1D", false));
    board.tryAddShip(ship3);
    String expected_2 = expectedHeader + "A  |b| | |  A\n" + "B b|b|b| |  B\n" + "C b|b|b|b|  C\n" + "D b|b|b| |  D\n"
        + "E b| | | |  E\n" + expectedHeader;
    assertEquals(expected_2, view.displayMyOwnBoard());
    Ship<Character> ship4 = factory.makeBattleship(new Placement("c3L", false));
    board.tryAddShip(ship4);
    
String expected_3 = expectedHeader + "A  |b| | |  A\n" + "B b|b|b| |  B\n" + "C b|b|b|b|b C\n" + "D b|b|b|b|b D\n"
        + "E b| | | |b E\n" + expectedHeader;
    assertEquals(expected_3, view.displayMyOwnBoard());
    assertEquals("Battleship", ship4.getName());
  }

}
