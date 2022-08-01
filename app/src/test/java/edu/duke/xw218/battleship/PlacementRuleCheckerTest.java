package edu.duke.xw218.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class PlacementRuleCheckerTest {
  /**
   * Chain the add ship checker rules together 
   */
  @Test
  public void test_check_placement() {
    //create checker
    //PlacementRuleChecker<Character> bounds_checker = new InBoundsRuleChecker<Character>(null);
    //PlacementRuleChecker<Character> coll_checker = new NoCollisionRuleChecker<>(bounds_checker);
    Board<Character> board = new BattleShipBoard<Character>(10,20, 'X');
    Placement p = new Placement("A1H");
    V1ShipFactory shipFactory = new V1ShipFactory();
    Ship<Character> ship1 = shipFactory.makeDestroyer(p);
    String ans = board.tryAddShip(ship1);
    assertEquals(null, ans);

    String ans1 = board.tryAddShip(ship1);
    assertEquals("That placement is invalid: the ship overlaps another ship.", ans1);
    //Ship<T> ship = new RectangleShip<T>(null, null, null)
    Placement p1 = new Placement("A2H");
    Ship<Character> ship2 = shipFactory.makeDestroyer(p1);
    String ans2 = board.tryAddShip(ship2);
    assertEquals("That placement is invalid: the ship overlaps another ship.", ans2);
    Placement p3 = new Placement("E5V");
    Ship<Character> ship3 = shipFactory.makeSubmarine(p3);
    String ans3 = board.tryAddShip(ship3);
    assertEquals(null, ans);
  }

}
