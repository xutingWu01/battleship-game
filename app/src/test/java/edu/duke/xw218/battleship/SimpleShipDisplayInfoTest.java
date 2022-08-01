package edu.duke.xw218.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class SimpleShipDisplayInfoTest {
  @Test
  public void test_constructor() {
    SimpleShipDisplayInfo info = new SimpleShipDisplayInfo('c', 'a');
    assertEquals('a', info.getInfo(new Coordinate(1,2), true));
    SimpleShipDisplayInfo info1 = new SimpleShipDisplayInfo('c', 'a');
    assertEquals('c', info1.getInfo(new Coordinate(1,2), false));
  }

}
