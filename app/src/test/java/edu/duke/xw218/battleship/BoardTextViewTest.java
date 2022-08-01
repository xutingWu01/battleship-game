package edu.duke.xw218.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class BoardTextViewTest {
  @Test
  public void test_display_empty_2by2() {
    Board<Character> b1 = new BattleShipBoard<Character>(2, 2, 'X');
    BoardTextView view = new BoardTextView(b1);
    String expectedHeader = "  0|1\n";
    assertEquals(expectedHeader, view.makeHeader());
    String expected = expectedHeader + "A  |  A\n" + "B  |  B\n" + expectedHeader;
    assertEquals(expected, view.displayMyOwnBoard());
  }

  @Test
  public void test_display_empty_3by2() {
    Board<Character> b1 = new BattleShipBoard<Character>(3, 2, 'X');
    BoardTextView view = new BoardTextView(b1);
    String expectedHeader = "  0|1|2\n";
    assertEquals(expectedHeader, view.makeHeader());
    String expected = expectedHeader + "A  | |  A\n" + "B  | |  B\n" + expectedHeader;
    assertEquals(expected, view.displayMyOwnBoard());
  }

  @Test
  public void test_display_empty_3by5() {
    Board<Character> b1 = new BattleShipBoard<Character>(3, 5, 'X');
    BoardTextView view = new BoardTextView(b1);
    String expectedHeader = "  0|1|2\n";
    assertEquals(expectedHeader, view.makeHeader());
    String expected = expectedHeader + "A  | |  A\n" + "B  | |  B\n" + "C  | |  C\n" + "D  | |  D\n" + "E  | |  E\n"
        + expectedHeader;
    assertEquals(expected, view.displayMyOwnBoard());
  }

  @Test
  public void test_invalid_board_size() {
    Board<Character> wideBoard = new BattleShipBoard<Character>(11, 20, 'X');
    Board<Character> tallBoard = new BattleShipBoard<Character>(10, 27, 'X');
    assertThrows(IllegalArgumentException.class, () -> new BoardTextView(wideBoard));
    assertThrows(IllegalArgumentException.class, () -> new BoardTextView(tallBoard));
  }

  @Test
  public void test_with_ship() {
    Board<Character> b = new BattleShipBoard<Character>(3, 5, 'X');
    // b.tryAddShip(new BasicShip(new Coordinate(0, 0)));
    b.tryAddShip(new RectangleShip<Character>(new Coordinate(0, 0), 's', '*'));
    BoardTextView view = new BoardTextView(b);
    String expectedHeader = "  0|1|2\n";
    String expected = expectedHeader + "A s| |  A\n" + "B  | |  B\n" + "C  | |  C\n" + "D  | |  D\n" + "E  | |  E\n"
        + expectedHeader;
    assertEquals(expected, view.displayMyOwnBoard());

  }

  @Test
  public void test_with_enemy_board() {
    Board<Character> b = new BattleShipBoard<Character>(3, 5, 'X');
    // b.tryAddShip(new BasicShip(new Coordinate(0, 0)));
    b.tryAddShip(new RectangleShip<Character>(new Coordinate(0, 0), 's', '*'));
    BoardTextView view = new BoardTextView(b);
    String expectedHeader = "  0|1|2\n";
    String expected = expectedHeader + "A  | |  A\n" + "B  | |  B\n" + "C  | |  C\n" + "D  | |  D\n" + "E  | |  E\n"
        + expectedHeader;
    assertEquals(expected, view.displayEnemyBoard());

    b.fireAt(new Coordinate(0, 0));
    String expected_fire = expectedHeader + "A s| |  A\n" + "B  | |  B\n" + "C  | |  C\n" + "D  | |  D\n"
        + "E  | |  E\n" + expectedHeader;
    assertEquals(expected_fire, view.displayEnemyBoard());

    b.fireAt(new Coordinate(0, 1));
    String expected_miss = expectedHeader + "A s|X|  A\n" + "B  | |  B\n" + "C  | |  C\n" + "D  | |  D\n"
        + "E  | |  E\n" + expectedHeader;
    assertEquals(expected_miss, view.displayEnemyBoard());

  }

  @Test
  public void test_2_boards(){
    Board<Character> b = new BattleShipBoard<Character>(3, 5, 'X');
    Board<Character> b1 = new BattleShipBoard<Character>(3, 5, 'X');
    b.tryAddShip(new RectangleShip<Character>(new Coordinate(0, 0), 's', '*'));
    BoardTextView myView = new BoardTextView(b);
    BoardTextView enemyView = new BoardTextView(b1);
    String expectedHeader = "  0|1|2";
    String str_header = "     Your ocean              Player B's ocean\n";
    String myHeader = "Your ocean";
    String enemyHeader = "Player B's ocean";
    assertEquals(str_header + expectedHeader + "                  " + expectedHeader + "\n" + "A s| |  A                A  | |  A\n" + "B  | |  B                B  | |  B\n" + "C  | |  C                C  | |  C\n" + "D  | |  D                D  | |  D\n" + "E  | |  E                E  | |  E\n"  + expectedHeader + "                  " + expectedHeader, myView.displayMyBoardWithEnemyNextToIt(enemyView, myHeader, enemyHeader));
    
    b.fireAt(new Coordinate(0, 0));
    assertEquals(str_header + expectedHeader + "                  " + expectedHeader + "\n" + "A *| |  A                A  | |  A\n" + "B  | |  B                B  | |  B\n" + "C  | |  C                C  | |  C\n" + "D  | |  D                D  | |  D\n" + "E  | |  E                E  | |  E\n"  + expectedHeader + "                  " + expectedHeader, myView.displayMyBoardWithEnemyNextToIt(enemyView, myHeader, enemyHeader));

  }
}
