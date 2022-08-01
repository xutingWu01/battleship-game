package edu.duke.xw218.battleship;
/**
 * This class is used to store the charater representing the hit or onhit situation of a ship
 */
public class SimpleShipDisplayInfo<T> implements ShipDisplayInfo<T> {
  private final T myData;
  private final T onHit;

  /**
   * Initializes data and hit
   */
  public SimpleShipDisplayInfo(T data, T onHit){
    this.myData = data;
    this.onHit = onHit;
  }
  /**
   * Returns hit or unhit letter
   */
  @Override
  public T getInfo(Coordinate where, boolean hit){
    if(hit){
      return onHit;
    }else{
      return myData;
    }
  }
}
