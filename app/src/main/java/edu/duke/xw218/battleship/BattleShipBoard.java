package edu.duke.xw218.battleship;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.HashMap;

/**
 * A class for the board of the battleship to display ships location and type
 */
public class BattleShipBoard<T> implements Board<T> {

  private final PlacementRuleChecker<T> placementChecker;
  private final int width;
  private final int height;
  final ArrayList<Ship<T>> myShips;
  private HashSet<Coordinate> enemyMisses;
  final T missInfo;
  LinkedHashMap<Coordinate, T> hitsLocation;
  LinkedHashSet<Coordinate> shouldEmptyLocation;

  /**
   * Returns the height of the board
   */
  public int getHeight() {
    return height;
  }

  /**
   * Returns the width of the board
   */
  public int getWidth() {
    return width;
  }

  public void addToHitsLocation(T content, Coordinate c) {
    hitsLocation.put(c, content);
  }

  public void removeFromHitsLocation(Coordinate c) {
    hitsLocation.remove(c);
  }

  /**
   * Construct a battleshipBoard with height and weight
   * 
   * @param w is the width
   * @param h is the height throws IllegalArgumentException if width or height are
   *          less than or equal to zero
   */
  public BattleShipBoard(int w, int h, T missInfo) {
    this(w, h, new InBoundsRuleChecker<>(new NoCollisionRuleChecker<T>(null)), missInfo);
  }

  public BattleShipBoard(int w, int h, PlacementRuleChecker<T> placementChecker, T missInfo) {
    // this(w, h, new InBoundsRuleChecker<T>(null));
    if (w <= 0) {
      throw new IllegalArgumentException("BattleShipBoard's width must be positive but is " + w);
    }
    if (h <= 0) {
      throw new IllegalArgumentException("BattleShipBoard's height must be positive but is " + h);
    }
    this.width = w;
    this.height = h;
    myShips = new ArrayList<Ship<T>>();
    this.placementChecker = placementChecker;
    this.enemyMisses = new HashSet<Coordinate>();
    this.missInfo = missInfo;
    this.hitsLocation = new LinkedHashMap<>();
    this.shouldEmptyLocation = new LinkedHashSet<>();
  }
  public int getHitsLocationSize(){
    return hitsLocation.size();
  }
  /**
   * First searchs for any ship that occupies coordinate c. if found, the ship is
   * hit
   * 
   * @return the hit ship
   */
  public Ship<T> fireAt(Coordinate c) {
    if (hitsLocation.containsKey(c)) {
      hitsLocation.remove(c);
    }
    if (shouldEmptyLocation.contains(c)) {
      shouldEmptyLocation.remove(c);
    }

    for (Ship<T> s : myShips) {
      if (s.occupiesCoordinates(c)) {
        // if(s.wasHitAt(c)==false){
        s.recordHitAt(c);
        return s;
        // }
      }
    }
    enemyMisses.add(c);
    return null;
  }

  /**
   * Add the ship to ship array
   * 
   * @param toAdd: the ship to add
   * @return return null if the ship is added successfully, else return detailed
   *         error message
   */
  public String tryAddShip(Ship<T> toAdd) {
    String ans = placementChecker.checkPlacement(toAdd, this);
    if (ans == "") {
      myShips.add(toAdd);
      return null;
    } else {
      return ans;
    }
  }

  /**
   * Checks which ship occupies the coordinate if found, return whatever
   * displayInfo, if not, return null
   */
  public T whatIsAtForSelf(Coordinate where) {
    return whatIsAt(where, true);
  }

  /**
   * Checks which ship occupies the coordinate if found, return whatever
   * displayInfo, if not, return null
   */
  protected T whatIsAt(Coordinate where, boolean isSelf) {
    if (isSelf == false && hitsLocation.containsKey(where)) {
      return hitsLocation.get(where);
    }
    if (isSelf == false && shouldEmptyLocation.contains(where)) {
      return null;
    }
    for (Ship<T> s : myShips) {
      if (s.occupiesCoordinates(where)) {
        return s.getDisplayInfoAt(where, isSelf);
      }
    }
    if (isSelf == false && enemyMisses.contains(where)) {
      return missInfo;
    }
    return null;

  }

  @Override
  public T whatIsAtForEnemy(Coordinate where) {
    return whatIsAt(where, false);
  }

  /**
   * Decides which play win or nobody wins
   */
  public boolean winOrlose() {
    for (Ship<T> s : myShips) {
      if (s.isSunk() == false) {
        return false;
      }
    }
    return true;
  }

  public Ship<T> checkShipExistence(Coordinate c) {
    for (Ship<T> s : myShips) {
      if (s.occupiesCoordinates(c)) {
        return s;
      }
    }
    return null;
  }

  public boolean removeShip(Ship<T> ship) {
    return myShips.remove(ship);
  }

  /**
   * Cover the square that should be blank after move from enemy view
   */
  public void addToEmptyLocation(Ship<T> ship) {
    LinkedHashMap<Coordinate, Boolean> pieces = ship.getMyPieces();
    for (Map.Entry<Coordinate, Boolean> entry : pieces.entrySet()) {
      shouldEmptyLocation.add(entry.getKey());
      //      System.err.println(entry.getKey());
    }
  }

  public void addShip(Ship<T> ship) {
    myShips.add(ship);
  }

  public HashMap<String, Integer> sonarScan(Coordinate center) {
    int y = 3;
    int start_row = center.getRow();
    int start_column = center.getColumn();
    HashMap<String, Integer> res = new HashMap<>();

    res.put("Carrier", 0);
    res.put("Battleship", 0);
    res.put("Destroyer", 0);
    res.put("Submarine", 0);
    int n = 3;
    for (int i = -n; i <= n; i++) {
      for (int j = -n; j <= n; j++) {
        if (Math.abs(i) + Math.abs(j) <= n) {
          int row = start_row + i;
          int column = start_column + j;
          if (row >= 0 && row < height && column >= 0 && column < width) {
            //System.out.print(new Coordinate(row, column));
            Ship<T> ans = checkShipExistence(new Coordinate(row, column));
            if (ans != null) {
              res.merge(ans.getName(), 1, Integer::sum);
              //System.out.print(new Coordinate(row, column));
            }
          }
        }
      }
    }
    return res;
  }
}
