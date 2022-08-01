package edu.duke.xw218.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class PlacementTest {
  @Test
  public void test_invalid_argument() {
    // Placement p1 = new Placement("1");
    // Placement p2 = new Placement("123");
    assertThrows(IllegalArgumentException.class, () -> new Placement("12"));
    assertThrows(IllegalArgumentException.class, () -> new Placement("1234"));
    assertThrows(IllegalArgumentException.class, () -> new Placement("A06"));
    assertThrows(IllegalArgumentException.class, () -> new Placement("A0q"));
    Coordinate c1 = new Coordinate("A0");
    assertThrows(IllegalArgumentException.class, () -> new Placement(c1, 'a'));
    assertThrows(IllegalArgumentException.class, () -> new Placement(c1, 'v', false));
     assertThrows(IllegalArgumentException.class, () -> new Placement("a0e", false));  
    assertThrows(IllegalArgumentException.class, () -> new Placement(c1, '1'));
    assertThrows(IllegalArgumentException.class, () -> new Placement("a0e", true));
    assertThrows(IllegalArgumentException.class, () -> new Placement("a0e1", true));
    assertThrows(IllegalArgumentException.class, () -> new Placement(c1,'c', true));
    
  }

  @Test
  public void test_valid_constructor() {
    Placement p1 = new Placement("A0V");
    Placement p2 = new Placement(new Coordinate("A0"), 'v');
    Placement p3 = new Placement(new Coordinate("A0"), 'V');
    assertEquals(new Coordinate("A0"), p1.getCoordinate());
    assertEquals('V', p1.getOrientation());
    assertEquals(new Coordinate("A0"), p2.getCoordinate());
    assertEquals('V', p2.getOrientation());
    assertEquals(new Coordinate("A0"), p3.getCoordinate());
    assertEquals('V', p3.getOrientation());

    Placement p4 = new Placement(new Coordinate("A0"), 'H');
    Placement p5 = new Placement(new Coordinate("A0"), 'h');
    assertEquals('H', p4.getOrientation());
    assertEquals('H', p5.getOrientation());
    Placement p6 = new Placement("B4h");
    Placement p7 = new Placement("C9H");
    assertEquals('H', p6.getOrientation());
    assertEquals('H', p7.getOrientation());
    assertEquals("((1, 4), H)", p6.toString());

    Placement p8 = new Placement("B4V");
    Placement p9 = new Placement("C9v");
    assertEquals('V', p8.getOrientation());
    assertEquals('V', p9.getOrientation());
    assertEquals(new Coordinate("B4"), p8.getCoordinate());
    // assertEquals("((1, 4), H)", p6.toString());

    Placement p10 = new Placement("a0d", false);
    assertEquals('D', p10.getOrientation());
    Placement p11 = new Placement(new Coordinate("e3"),'u', false);   
    assertEquals('U', p11.getOrientation());
    Placement p12 = new Placement("a0v", true);
    assertEquals('V', p12.getOrientation());
    Coordinate c = new Coordinate("a0");
    Placement p13 = new Placement(c, 'v', true);
    assertEquals('V', p13.getOrientation());
  }

  @Test
  public void test_hashCode() {
    Placement p1 = new Placement("A0V");
    Placement p2 = new Placement("A0v");
    Placement p3 = new Placement("A1H");
    assertEquals(p1.hashCode(), p2.hashCode());
    assertEquals(p1.hashCode(), p1.hashCode());
    assertNotEquals(p2.hashCode(), p3.hashCode());
  }

  @Test
  public void test_equals() {
    Placement p1 = new Placement("A0V");
    Placement p2 = new Placement("A0V");
    Placement p3 = new Placement("A0H");
    Placement p4 = new Placement("A0v");
    Placement p5 = new Placement(new Coordinate("A0"), 'v');
    //Placement p6 = new Placement("a0v");
    assertEquals(p1, p1);
    assertEquals(p1, p4);
    assertEquals(p1, p2);
    assertNotEquals(p3, p2);
    assertNotEquals(p1, p3);
    assertEquals(p1, p5);
    assertEquals(p5, p1);
    assertNotEquals(p1, "asf");
    //assertEquals(p1, p6);
  }
}
