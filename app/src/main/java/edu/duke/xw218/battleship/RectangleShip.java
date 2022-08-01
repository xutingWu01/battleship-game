package edu.duke.xw218.battleship;

import java.util.HashSet;
import java.util.LinkedHashSet;

public class RectangleShip<T> extends BasicShip<T> {
  // private final int width;
  // private final int height;
  // private Coordinate upperleft;
  private final String name;

  public String getName() {
    return this.name;
  }

  /**
   * Constructs a rectangleship according to name, the coordinate of upperleft
   * block width and height, and info
   */
  public RectangleShip(String name, Coordinate upperLeft, int width, int height, ShipDisplayInfo<T> info,
      ShipDisplayInfo<T> enemyDisplayInfo) {
    super(makeCoords(upperLeft, width, height), info, enemyDisplayInfo);
    this.name = name;
  }

  public RectangleShip(String name, Coordinate upperLeft, int width, int height, T data, T onHit) {
    this(name, upperLeft, width, height, new SimpleShipDisplayInfo<T>(data, onHit),
        new SimpleShipDisplayInfo<T>(null, data));
  }

  public RectangleShip(Coordinate upperLeft, T data, T onHit) {
    this("testship", upperLeft, 1, 1, data, onHit);
  }

  /**
   * Generates the coordinates for corners of the rectablge
   * 
   * @param the     coordinate for upperleft point of the rectangle
   * @param width,  width of the rectangle
   * @param height, height of the recntangle if upperleft is (1,2), width = 1,
   *                height = 2,
   * @return set {(1,2), (2, 2), (3,2)}
   */
  static LinkedHashSet<Coordinate> makeCoords(Coordinate upperleft, int width, int height) {
    LinkedHashSet<Coordinate> coords = new LinkedHashSet<Coordinate>();
    for (int h = 0; h < height; h++) {
      for (int w = 0; w < width; w++) {

        Coordinate c = new Coordinate(upperleft.getRow() + h, upperleft.getColumn() + w);
        coords.add(c);
      }
    }
    return coords;
  }

}
