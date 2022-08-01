package edu.duke.xw218.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.StringReader;

import org.junit.jupiter.api.Test;

public class TextPlayerTest {
  @Test
  public void test_() {
    
  }

  private TextPlayer createTextPlayer(int w, int h, String inputData, OutputStream bytes) {
    BufferedReader input = new BufferedReader(new StringReader(inputData));
    PrintStream output = new PrintStream(bytes, true);
    Board<Character> board = new BattleShipBoard<Character>(w, h, 'X');
    V1ShipFactory shipFactory = new V1ShipFactory();
    return new TextPlayer("A", board, input, output, shipFactory);
  }

  private TextPlayer createTestPlayer(int w, int h, String inputData, OutputStream bytes) {
    BufferedReader input = new BufferedReader(new StringReader(inputData));
    PrintStream output = new PrintStream(bytes, true);
    Board<Character> board = new BattleShipBoard<Character>(w, h, 'X');
    V1ShipFactory shipFactory = new V2ShipFactory();
    return new TextTextPlayer("A", board, input, output, shipFactory);
  }

  private TextPlayer createTestPlayer1(int w, int h, String inputData, OutputStream bytes) {
    BufferedReader input = new BufferedReader(new StringReader(inputData));
    PrintStream output = new PrintStream(bytes, true);
    Board<Character> board = new BattleShipBoard<Character>(w, h, 'X');
    V1ShipFactory shipFactory = new V2ShipFactory();
    return new TextTextPlayer("B", board, input, output, shipFactory);
  }

  @Test
  void test_read_placement() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer p1 = createTextPlayer(10, 20, "B2V\nC8H\nA4v\n123\n", bytes);
    String prompt = "Please enter a location for a ship:";
    Placement[] expected = new Placement[3];
    expected[0] = new Placement(new Coordinate(1, 2), 'V');
    expected[1] = new Placement(new Coordinate(2, 8), 'H');
    expected[2] = new Placement(new Coordinate(0, 4), 'V');

    for (int i = 0; i < expected.length; i++) {
      Placement p = p1.readPlacement(prompt, "testship");
      assertEquals(p, expected[i]); // did we get the right Placement back
      assertEquals(prompt + "\n", bytes.toString()); // should have printed prompt and newline
      bytes.reset(); // clear out bytes for next time around
    }
    assertThrows(IllegalArgumentException.class, () -> p1.readPlacement(prompt, "testship"));
  }

  @Test
  void test_read_action() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer p1 = createTextPlayer(10, 20, "a\n1\nF\nM\ns\n", bytes);
    String prompt = "Choose your action";
    String ans1 = p1.readAction(prompt);
    assertEquals(null, ans1);
    String ans2 = p1.readAction(prompt);
    assertEquals(null, ans2);
    String ans3 = p1.readAction(prompt);
    assertEquals("F", ans3);
    String ans4 = p1.readAction(prompt);
    assertEquals("M", ans4);
    String ans5 = p1.readAction(prompt);
    assertEquals("S", ans5);
  }

  @Test
  void test_doOnePlacement_v1() throws IOException {
    // StringReader sr = new StringReader("B2V\n");
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer p1 = createTextPlayer(3, 5, "B2V\nA1C\nA0V\n", bytes);
    String prompt = "Player A where do you want to place a Destroyer?";
    V1ShipFactory shipFactory = new V1ShipFactory();
    // Placement p = app.readPlacement(prompt);
    p1.doOnePlacement("Destroyer", (p) -> shipFactory.makeDestroyer(p));
    assertEquals(prompt + "\n" + "  0|1|2\n" + "A  | |  A\n" + "B  | |d B\n" + "C  | |d C\n" + "D  | |d D\n"
        + "E  | |  E\n" + "  0|1|2\n", bytes.toString());
    // assertThrows(IllegalArgumentException.class,
    // () -> p1.doOnePlacement("Destroyer", (p) -> shipFactory.makeDestroyer(p)));
    bytes.reset();
    p1.doOnePlacement("Destroyer", (p) -> shipFactory.makeDestroyer(p));

    /*
     * assertEquals( prompt + "\n" + "  0|1|2\n" + "A  | |  A\n" + "B  | |d B\n" +
     * "C  | |d C\n" + "D  | |d D\n" + "E  | |  E\n" + "  0|1|2\n" + prompt + "\n" +
     * "That placement is invalid: it does not have the correct format.\n" + prompt
     * + "\n" + " 0|1|2\n" + "A d| | A\n" + "B d| |d B\n" + "C d| |d C\n" +
     * "D | |d D\n" + "E | | E\n", bytes.toString());
     */
    /*
     * assertEquals(prompt + "\n" + "  0|1|2\n" + "A  | |  A\n" + "B  | |d B\n" +
     * "C  | |d C\n" + "D  | |d D\n" + "E  | |  E\n" + "  0|1|2\n" + prompt + "\n" +
     * "That placement is invalid: it does not have the correct format.\n"+ prompt +
     * "\n"+"That placement is invalid: the ship goes off the bottom of the board."
     * +"\n", bytes.toString());
     */
  }

  @Test
  public void test_do_one_placement() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer p1 = createTestPlayer(5, 5, "a1v\nc2v\nb2d\na0u\na1\na1\nd3v\nb2\n", bytes);
  }

  /**
   * Tests move action
   */
  @Test
  void test_move_action() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer p1 = createTestPlayer(5, 5, "z1v\na1v\nc2v\nb2d\na0u\na1\na1\nd3v\nb2\n", bytes);
    String expectedHeader = "  0|1|2|3|4\n";

    String expected_4 = expectedHeader + "A  | | | |  A\n" + "B  | | | |  B\n" + "C  | | | |  C\n" + "D  | | | |  D\n"
        + "E  | | | |  E\n" + expectedHeader;
    String expected_3 = expectedHeader + "A  |s| | |  A\n" + "B  |s| | |  B\n" + "C  | | | |  C\n" + "D  | | | |  D\n"
        + "E  | | | |  E\n" + expectedHeader;
    String expected_2 = expectedHeader + "A  |s| | |  A\n" + "B  |s| | |  B\n" + "C  | |d| |  C\n" + "D  | |d| |  D\n"
        + "E  | |d| |  E\n" + expectedHeader;
    String expected_1 = expectedHeader + "A  |s| | |  A\n" + "B  |s|b|b|b B\n" + "C  | |d|b|  C\n" + "D  | |d| |  D\n"
        + "E  | |d| |  E\n" + expectedHeader;
    String expected = expectedHeader + "A c|s| | |  A\n" + "B c|s|b|b|b B\n" + "C c|c|d|b|  C\n" + "D c|c|d| |  D\n"
        + "E  |c|d| |  E\n" + expectedHeader;
    String a = "Player A where do you want to place a Submarine?\n";
    String b = "Player A where do you want to place a Destroyer?\n";
    String c = "Player A where do you want to place a Battleship?\n";
    String d = "Player A where do you want to place a Carrier?\n";
    String prompt = "Player " + "A" + ":"
        + "you are going to place the following ships (which are all rectangular).\nFor each ship, type the coordinate of the upper left side of the ship, followed by either H (for horizontal) or V (for vertical).\nFor example M4H would place a ship horizontally starting at M4 and going to the right. You have\n\n2 \"Submarines\" ships that are 1x2\n3 \"Destroyers\" that are 1x3\n3 \"Battleships\" that are 1x4\n2 \"Carriers\" that are 1x6\n";
    p1.doPlacementPhase();
    assertEquals(prompt + expected_4 + a + "That placement is invalid: the ship goes off the bottom of the board.\n" + a
        + expected_3 + b + expected_2 + c + expected_1 + d + expected, bytes.toString());

    TextPlayer p2 = createTestPlayer(5, 5, "a1v\nc2v\nb2d\na0u\na1\nd3v\nd3\na1h\nb2\nc3r", bytes);
    p2.doPlacementPhase();
    bytes.reset();
    p1.fireAction(p2.getBoard(), p2.getView());
    String expected_fire = expectedHeader + "A c|*| | |  A\n" + "B c|s|b|b|b B\n" + "C c|c|d|b|  C\n"
        + "D c|c|d| |  D\n" + "E  |c|d| |  E\n" + expectedHeader;
    assertEquals(expected_fire, p2.getView().displayMyOwnBoard());
    // start play one turn
    p1.moveAction();
    String move_prompt = "Player A Which ship do you want to move?\n";
    String error_1 = "This square dose not belong to any ships\n";
    String expect_move1 = expectedHeader + "A c| | | |  A\n" + "B c| |b|b|b B\n" + "C c|c|d|b|  C\n" + "D c|c|d|s|  D\n"
        + "E  |c|d|s|  E\n" + expectedHeader;
    assertEquals(expect_move1, p1.getView().displayMyOwnBoard());
    // String expected_enemy_move1 = expectedHeader + "A c| | | | A\n" + "B c|
    // |b|b|b B\n" + "C c|c|d|b| C\n" + "D c|c|d|s| D\n"
    // + "E |c|d|s| E\n" + expectedHeader;
    assertEquals(expected_4, p1.getView().displayEnemyBoard());// should be an empty board
    p2.moveAction();
    String expect_move2 = expectedHeader + "A c| | | |  A\n" + "B c| |b|b|b B\n" + "C c|c|d|b|  C\n" + "D c|c|d|*|  D\n"
        + "E  |c|d|s|  E\n" + expectedHeader;
    String expected_move5 = expectedHeader + "A  |s| | |  A\n" + "B  | | | |  B\n" + "C  | | | |  C\n"
        + "D  | | | |  D\n" + "E  | | | |  E\n" + expectedHeader;

    assertEquals(expect_move2, p2.getView().displayMyOwnBoard());
    assertEquals(expected_move5, p2.getView().displayEnemyBoard());
    p2.moveAction();
    String expected_move3 = expectedHeader + "A c|*|s| |  A\n" + "B c| |b|b|b B\n" + "C c|c|d|b|  C\n"
        + "D c|c|d| |  D\n" + "E  |c|d| |  E\n" + expectedHeader;
    String expected_move6 = expectedHeader + "A  |*| | |  A\n" + "B  | | | |  B\n" + "C  | | | |  C\n"
        + "D  | | | |  D\n" + "E  | | | |  E\n" + expectedHeader;
    assertEquals(expected_move3, p2.getView().displayMyOwnBoard());
    p1.fireAction(p2.getBoard(), p2.getView());
    p2.moveAction();
    String expected_move4 = expectedHeader + "A c|*|s| |  A\n" + "B c| | | |  B\n" + "C c|c|d|*|  C\n"
        + "D c|c|d|b|b D\n" + "E  |c|d|b|  E\n" + expectedHeader;
    assertEquals(expected_move4, p2.getView().displayMyOwnBoard());
  }

  @Test
  void test_move_action_formal() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer p1 = createTestPlayer(5, 5, "a1v\nc2v\nb2d\na0u\na0\na1\nb3\nc2\n", bytes);
    String expectedHeader = "  0|1|2|3|4\n";

    String expected_empty = expectedHeader + "A  | | | |  A\n" + "B  | | | |  B\n" + "C  | | | |  C\n"
        + "D  | | | |  D\n" + "E  | | | |  E\n" + expectedHeader;

    TextPlayer p2 = createTestPlayer(5, 5, "a1v\nc2v\nb2d\na0u\nc3\na2d\n", bytes);

    p1.doPlacementPhase();
    p2.doPlacementPhase();
    String expected_fillin = expectedHeader + "A c|s| | |  A\n" + "B c|s|b|b|b B\n" + "C c|c|d|b|  C\n"
        + "D c|c|d| |  D\n" + "E  |c|d| |  E\n" + expectedHeader;

    // test empty for itself and enemy
    assertEquals(expected_fillin, p1.getView().displayMyOwnBoard());
    assertEquals(expected_fillin, p2.getView().displayMyOwnBoard());
    assertEquals(expected_empty, p2.getView().displayEnemyBoard());
    assertEquals(expected_empty, p1.getView().displayEnemyBoard());

    p1.fireAction(p2.getBoard(), p2.getView());
    p1.fireAction(p2.getBoard(), p2.getView());
    p1.fireAction(p2.getBoard(), p2.getView());
    p1.fireAction(p2.getBoard(), p2.getView());
    String expected_fired = expectedHeader + "A *|*| | |  A\n" + "B c|s|b|*|b B\n" + "C c|c|*|b|  C\n"
        + "D c|c|d| |  D\n" + "E  |c|d| |  E\n" + expectedHeader;
    assertEquals(expected_fired, p2.getView().displayMyOwnBoard());
    String expected_fired_empty = expectedHeader + "A c|s| | |  A\n" + "B  | | |b|  B\n" + "C  | |d| |  C\n"
        + "D  | | | |  D\n" + "E  | | | |  E\n" + expectedHeader;
    assertEquals(expected_fired_empty, p2.getView().displayEnemyBoard());

    // player2 start moves battleship to a2
    p2.moveAction();
    String expected_move_own = expectedHeader + "A *|*|b|*|b A\n" + "B c|s| |b|  B\n" + "C c|c|*| |  C\n"
        + "D c|c|d| |  D\n" + "E  |c|d| |  E\n" + expectedHeader;
    assertEquals(expected_move_own, p2.getView().displayMyOwnBoard());
    assertEquals(expected_fired_empty, p2.getView().displayEnemyBoard());

  }

  /**
   * Test reading coordinate both valid and invalid
   */
  @Test
  public void test_read_coordinate() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer p1 = createTextPlayer(10, 20, "a1\n11\n", bytes);
    String prompt = "Enter coordinates";
    Coordinate expected = new Coordinate(0, 1);
    assertEquals(expected, p1.readCoordinate(prompt));
    assertEquals(prompt + "\n", bytes.toString());
    assertThrows(IllegalArgumentException.class, () -> p1.readCoordinate(prompt));

  }

  /**
   * Tests the function of fire function
   */
  @Test
  public void test_fire_action() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer p1 = createTextPlayer(5, 5, "c1\n11\na0\n", bytes);
    TextPlayer p2 = createTextPlayer(5, 5, "a1\n11\n", bytes);
    String expectedHeader = "  0|1|2|3|4\n";
    V1ShipFactory factory = new V1ShipFactory();
    Ship<Character> ship1 = factory.makeSubmarine(new Placement("a0h"));
    p2.getBoard().tryAddShip(ship1);
    p1.fireAction(p2.getBoard(), p2.getView());
    assertEquals("Player A Where would you like to fire at?\n" + "You missed\n", bytes.toString());
    bytes.reset();
    p1.fireAction(p2.getBoard(), p2.getView());
    assertEquals(
        "Player A Where would you like to fire at?\nThat coornidate is invalid: it does not have the correct format.\nPlayer A Where would you like to fire at?\nYou hit a Submarine\n",
        bytes.toString());
  }

  /**
   * Test the function of scan function
   */
  @Test
  public void test_scan() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer p1 = createTestPlayer(5, 5, "a1v\nc2v\nb2d\na0u\n123\na1\n", bytes);
    p1.doPlacementPhase();
    bytes.reset();
    p1.scanAction();
    assertEquals(
        "Player A Which coordinate do you want to sonar scan?\nThat coornidate is invalid: it does not have the correct format.\n",
        bytes.toString());
    assertEquals(true, p1.scanAction());

  }

  @Test
  public void test_move_error() throws IOException {
    // trying to move a ship but with failure
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer p1 = createTestPlayer(5, 5, "a1v\nc2v\nb2d\na0u\ne0\n123\na1\na1\na1\nc2h\n", bytes);
    // first make it enter no exist coordinate
    p1.doPlacementPhase();
    bytes.reset();
    assertEquals(false, p1.moveAction());
    assertEquals("Player A Which ship do you want to move?\nThis square dose not belong to any ships\n",
        bytes.toString());
    bytes.reset();
    assertEquals(false, p1.moveAction());
    assertEquals(
        "Player A Which ship do you want to move?\nThat coornidate is invalid: it does not have the correct format.\n",
        bytes.toString());
    // coordinate is valid but placement is invalid
    bytes.reset();
    assertEquals(false, p1.moveAction());
    assertEquals(
        "Player A Which ship do you want to move?\nPlayer A where do you want to place a Submarine?\nThat placement is invalid: it does not have the correct format.\n",
        bytes.toString());
    bytes.reset();
    // coordinate is valid but the placement is outof bounds
    assertEquals(false, p1.moveAction());
    assertEquals(
        "Player A Which ship do you want to move?\nPlayer A where do you want to place a Submarine?\nThat placement is invalid: the ship overlaps another ship.\n",
        bytes.toString());
  }

  @Test
  public void test_play_one_turn() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer p1 = createTestPlayer(5, 5, "a1v\nc2v\nb2d\na0u\na\ns\n123\ns\na0\ns\nf\nc1\n", bytes);
    TextPlayer p2 = createTestPlayer1(5, 5,
        "a1v\nc2v\nb2d\na0u\nm\n123\nm\na1\ne3h\nm\ne3\na1v\nm\nf\na1\ns\nz1\ns\n123\nf\na1\n", bytes);
    p1.doPlacementPhase();
    p2.doPlacementPhase();
    bytes.reset();
    // scan
    p1.playOneTurn(p2.getBoard(), p2.getView());
    // fire once
    p1.playOneTurn(p2.getBoard(), p2.getView());
    p2.playOneTurn(p1.getBoard(), p1.getView());
    p2.playOneTurn(p1.getBoard(), p1.getView());
    p2.playOneTurn(p1.getBoard(), p1.getView());
    // player want to scan but input invalie
    p2.playOneTurn(p1.getBoard(), p1.getView());
    p2.playOneTurn(p1.getBoard(), p1.getView());
  }

  @Test
  public void test_result() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    StringBuilder input = new StringBuilder();
    input.append("a1v\nc2v\nb2d\na0u\n");
    for (int i = 0; i < 5; i++) {
      for (int j = 0; j < 5; j++) {
        input.append("f\n");
        int a = 'a' + i;
        char c = (char) a;
        input.append(String.valueOf(c));
        input.append(j);
        input.append("\n");
      }
    }
    TextPlayer p1 = createTestPlayer(5, 5, input.toString(), bytes);
    TextPlayer p2 = createTestPlayer(5, 5, input.toString(), bytes);
    for (int i = 0; i < 25; i++) {
      p1.playOneTurn(p2.getBoard(), p2.getView());
    }
  }
}
