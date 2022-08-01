package edu.duke.xw218.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class V1ShipFactoryTest {
  /**
   * Tests whether can create various ships successfully.
   */
  @Test
  public void test_submarine() {
    Placement p = new Placement(new Coordinate(2, 0), 'V');
    V1ShipFactory factory = new V1ShipFactory();

    Ship<Character> ship1= factory.makeSubmarine(p);
    assertEquals("Submarine", ship1.getName());
    Placement p1 = new Placement(new Coordinate(2, 4), 'H');

    Ship<Character> ship2= factory.makeBattleship(p1);
    assertEquals("Battleship", ship2.getName()); 

    Ship<Character> ship3= factory.makeCarrier(p);
    assertEquals("Carrier", ship3.getName());

    Ship<Character> ship4= factory.makeDestroyer(p);    
    assertEquals("Destroyer", ship4.getName());
  }

}
