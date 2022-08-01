package edu.duke.xw218.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class InBoundsRuleCheckerTest {
  @Test
  public void test_check_my_ruke() {
    Placement p = new Placement(new Coordinate(2, 2), 'V');
    V1ShipFactory factory = new V1ShipFactory();
    Ship<Character> ship1 = factory.makeSubmarine(p);
    InBoundsRuleChecker<Character> checker = new InBoundsRuleChecker<Character>(null);
    BattleShipBoard<Character> board = new BattleShipBoard<>(10, 20, 'X');
    String ans = checker.checkMyRule(ship1, board);
    assertEquals("", ans);

    Placement p1 = new Placement(new Coordinate(2, 110), 'V');
    Ship<Character> ship2 = factory.makeSubmarine(p1);
    String ans_right = checker.checkMyRule(ship2, board);
    assertEquals("That placement is invalid: the ship goes off the right of the board.", ans_right);
    Placement p2 = new Placement(new Coordinate(-1, 1), 'V');
    Ship<Character> ship3 = factory.makeSubmarine(p2);
    String ans_top = checker.checkMyRule(ship3, board);
    assertEquals("That placement is invalid: the ship goes off the top of the board.", ans_top);

    Placement p3 = new Placement(new Coordinate(0, -1), 'V');
    Ship<Character> ship4 = factory.makeSubmarine(p3);
    String ans_left = checker.checkMyRule(ship4, board);
    assertEquals("That placement is invalid: the ship goes off the left of the board.", ans_left);

    Placement p4 = new Placement(new Coordinate(100, 1), 'V');
    Ship<Character> ship5 = factory.makeSubmarine(p4);
    String ans_btm = checker.checkMyRule(ship5, board);
    assertEquals("That placement is invalid: the ship goes off the bottom of the board.", ans_btm);

    Placement p5 = new Placement(new Coordinate(2, 1), 'V');
    Ship<Character> ship6 = factory.makeSubmarine(p5);
    String ans_btm_1 = checker.checkMyRule(ship6, board);
    assertEquals("", ans_btm_1);

  }

}
