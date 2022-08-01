package edu.duke.xw218.battleship;

import java.util.HashMap;

/**
 * Interface for battleship, have width and height
 */
public interface Board<T> {
  public int getWidth();

  public int getHeight();

  public String tryAddShip(Ship<T> toAdd);

  public T whatIsAtForSelf(Coordinate where);

  public Ship<T> fireAt(Coordinate c);

  public T whatIsAtForEnemy(Coordinate where);

  public boolean winOrlose();

  public Ship<T> checkShipExistence(Coordinate c);

  public boolean removeShip(Ship<T> ship);

  public void addToHitsLocation(T content, Coordinate c);

  public void addToEmptyLocation(Ship<T> ship);

  public void removeFromHitsLocation(Coordinate c);

  public void addShip(Ship<T> ship);

  public HashMap<String, Integer> sonarScan(Coordinate center);
}
