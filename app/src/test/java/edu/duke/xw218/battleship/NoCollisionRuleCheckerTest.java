package edu.duke.xw218.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
/**
 * Test with whether adding shipp will collide with other ship
 */
public class NoCollisionRuleCheckerTest {
  @Test
  public void test_no_collision_checker() {
    SimpleShipDisplayInfo info = new SimpleShipDisplayInfo('s', '*'); 
    V1ShipFactory shipFactory = new V1ShipFactory();
    Placement p = new Placement("B3V");
    Ship<Character> ship1 = shipFactory.makeDestroyer(p);
    NoCollisionRuleChecker<Character> checker = new NoCollisionRuleChecker<Character>(null);
    //create board
    Board<Character> b = new BattleShipBoard<>(10, 20, checker, 'X');
    String ans = checker.checkMyRule(ship1, b);
    assertEquals("", ans);
    b.tryAddShip(ship1);
    String ans1 = checker.checkMyRule(ship1, b);
    assertEquals("That placement is invalid: the ship overlaps another ship.",ans1);
  }

}
