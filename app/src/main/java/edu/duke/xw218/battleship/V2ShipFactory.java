package edu.duke.xw218.battleship;

import java.util.LinkedHashSet;

public class V2ShipFactory extends V1ShipFactory {
  @Override
  public Ship<Character> makeBattleship(Placement where) {
    Coordinate c1, c2;
    int x, y;
    x = where.getCoordinate().getRow();
    y = where.getCoordinate().getColumn();
    int row1, row2, column1, column2;
    if (where.getOrientation() == 'U') {
      row1 = x + 1;
      column1 = y;
      row2 = x;
      column2 = y + 1;
    } else if (where.getOrientation() == 'R') {
      row1 = x;
      column1 = y;
      row2 = x + 1;
      column2 = y + 1;
    } else if (where.getOrientation() == 'D') {
      row1 = x;
      column1 = y;
      row2 = x + 1;
      column2 = y + 1;
    } else {
      row1 = x;
      column1 = y + 1;
      row2 = x + 1;
      column2 = y;
    }
    c1 = new Coordinate(row1, column1);
    c2 = new Coordinate(row2, column2);
    int width1 = 1, height1 = 3, width2 = 1, height2 = 1;
    if (where.getOrientation() == 'U' || where.getOrientation() == 'D') {
      height1 = 1;
      width1 = 3;

    }
    LinkedHashSet<Coordinate> coor1 = RectangleShip.makeCoords(c1, width1, height1);
    LinkedHashSet<Coordinate> coor2 = RectangleShip.makeCoords(c2, width2, height2);
    coor1.addAll(coor2);
    SimpleShipDisplayInfo info = new SimpleShipDisplayInfo('b', '*');
    SimpleShipDisplayInfo e_info = new SimpleShipDisplayInfo(null, 'b');
    CombineShip res = new CombineShip(coor1, info, e_info, "Battleship");
    return res;
  }

  @Override
  public Ship<Character> makeCarrier(Placement where) { // create
    Coordinate c1, c2;
    int x, y;
    x = where.getCoordinate().getRow();
    y = where.getCoordinate().getColumn();
    int row1, row2, column1, column2;
    if (where.getOrientation() == 'U') {
      row1 = x;
      column1 = y;
      row2 = x + 2;
      column2 = y + 1;
    } else if (where.getOrientation() == 'R') {
      row1 = x;
      column1 = y + 1;
      row2 = x + 1;
      column2 = y;
    } else if (where.getOrientation() == 'D') {
      row1 = x + 1;
      column1 = y + 1;
      row2 = x;
      column2 = y;
    } else {
      row1 = x + 1;
      column1 = y;
      row2 = x;
      column2 = y + 2;
    }
    c1 = new Coordinate(row1, column1);
    c2 = new Coordinate(row2, column2);
    int width1 = 1, height1 = 4, width2 = 1, height2 = 3;
    if (where.getOrientation() == 'L' || where.getOrientation() == 'R') {
      height1 = 1;
      width1 = 4;
      height2 = 1;
      width2 = 3;

    }
    LinkedHashSet<Coordinate> coor1 = RectangleShip.makeCoords(c1, width1, height1);
    LinkedHashSet<Coordinate> coor2 = RectangleShip.makeCoords(c2, width2, height2);
    coor1.addAll(coor2);
    SimpleShipDisplayInfo info = new SimpleShipDisplayInfo('c', '*');
    SimpleShipDisplayInfo e_info = new SimpleShipDisplayInfo(null, 'c');
    CombineShip res = new CombineShip(coor1, info, e_info, "Carrier");
    return res;
  }

}
