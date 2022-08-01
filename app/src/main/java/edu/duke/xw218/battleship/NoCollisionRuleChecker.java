package edu.duke.xw218.battleship;
/**
 * Used to check that the ship should not collide with anything else on board
 * The blocks used for the ship all need to be empty.
 */
public class NoCollisionRuleChecker<T> extends PlacementRuleChecker<T>{
  /**
   * Constructor, used to chain the rule together
   */
  public NoCollisionRuleChecker(PlacementRuleChecker<T> next){
    super(next);
  }

  /**
   * Check whether the ship going to replace will collide with any existing ships on the board
   * @param theShip: ship to add
   * @param theBoard: the board to place ship
   * @return null if can place the ship, otherwise return any error message
   */
  @Override
  protected String checkMyRule(Ship<T> theShip, Board<T> theBoard){
    Iterable<Coordinate> coors = theShip.getCoordinates();
    for(Coordinate coor:coors){
      if(theBoard.whatIsAtForSelf(coor)!=null){
        return "That placement is invalid: the ship overlaps another ship.";
      }
    }
    return "";
  }
}
