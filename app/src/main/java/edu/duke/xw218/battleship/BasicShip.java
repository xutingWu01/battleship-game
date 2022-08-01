package edu.duke.xw218.battleship;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

public abstract class BasicShip<T> implements Ship<T> {
  // private final Coordinate myLocation;
  protected LinkedHashMap<Coordinate, Boolean> myPieces;
  protected ShipDisplayInfo<T> myDisplayInfo;
  protected ShipDisplayInfo<T> enemyDisplayInfo;
  
  /**
   * Get my pieces
   */
  public LinkedHashMap<Coordinate, Boolean> getMyPieces() {
    return myPieces;
  }

  public void setMyPieces(LinkedHashMap<Coordinate, Boolean> oldMap) {
    Iterator<Entry<Coordinate, Boolean>> iterator1 = oldMap.entrySet().iterator();
    Iterator<Entry<Coordinate, Boolean>> iterator2 = myPieces.entrySet().iterator();
    while (iterator1.hasNext()) {
      Entry<Coordinate, Boolean> entry1 = iterator1.next();
      Entry<Coordinate, Boolean> entry2 = iterator2.next();
      entry2.setValue(entry1.getValue());
    }
  }

  /**
   * Returns the coordinates of the ship
   */
  @Override
  public Iterable<Coordinate> getCoordinates() {
    return myPieces.keySet();
  }

  /**
   * Check whether the coordinate is a part of myPieces throws
   * IllegalArgumentException if not
   */
  protected void checkCoordinateInThisShip(Coordinate c) throws IllegalArgumentException {
    if (!myPieces.containsKey(c)) {
      throw new IllegalArgumentException("The coordinate is not a part of the ship\n");
    }
  }
  /*
   * public Coordinate getMyLocation() { return myLocation; }
   */

  /*
   * public BasicShip(Coordinate c) { // this.myLocation = c; myPieces = new
   * HashMap<Coordinate, Boolean>(); myPieces.put(c, false); }
   */

  /**
   * Initializes myPieces to have each Coordinate in where mapped to false
   */
  public BasicShip(Iterable<Coordinate> where, ShipDisplayInfo<T> myDisplayInfo, ShipDisplayInfo<T> enemyDisplayInfo) {
    myPieces = new LinkedHashMap<Coordinate, Boolean>();
    for (Coordinate c : where) {
      myPieces.put(c, false);
    }
    this.myDisplayInfo = myDisplayInfo;
    this.enemyDisplayInfo = enemyDisplayInfo;
  }

  /**
   * Checks wheter the coordinate is a part of the ship
   */
  @Override
  public boolean occupiesCoordinates(Coordinate where) {
    return myPieces.containsKey(where);
  }

  /**
   * Returns true if every part of the ship has been hit
   */
  @Override
  public boolean isSunk() {
    for (HashMap.Entry<Coordinate, Boolean> piece : myPieces.entrySet()) {
      if (piece.getValue() == true) {
        continue;
      } else {
        return false;
      }
    }
    return true;
  }

  /**
   * Check if this ship occupies the given coordinate.
   * 
   * @param where is the Coordinate to check if this Ship occupies
   * @return true if where is inside this ship, false if not.
   */
  @Override
  public void recordHitAt(Coordinate where) throws IllegalArgumentException {
    // try {
    checkCoordinateInThisShip(where);
    // } catch (IllegalArgumentException e) {
    // return;
    // }
    myPieces.put(where, true);
  }

  /**
   * Check if this ship was hit at the specified coordinates. The coordinates must
   * be part of this Ship.
   * 
   * @param where is the coordinates to check.
   * @return true if this ship as hit at the indicated coordinates, and false
   *         otherwise.
   * @throws IllegalArgumentException if the coordinates are not part of this
   *                                  ship.
   */
  @Override
  public boolean wasHitAt(Coordinate where) throws IllegalArgumentException {
    // try {
    checkCoordinateInThisShip(where);
    // } catch (IllegalArgumentException e) {
    // return false;
    // }
    return myPieces.get(where);
  }

  /**
   * Return the view-specific information at the given coordinate. This coordinate
   * must be part of the ship.
   * 
   * @param where is the coordinate to return information for
   * @throws IllegalArgumentException if where is not part of the Ship
   * @return The view-specific information at that coordinate.
   */
  @Override
  public T getDisplayInfoAt(Coordinate where, boolean myShip) throws IllegalArgumentException {
    checkCoordinateInThisShip(where);
    if (myShip == true) {
      return myDisplayInfo.getInfo(where, wasHitAt(where));
    } else {
      return enemyDisplayInfo.getInfo(where, wasHitAt(where));
    }
  }

}
