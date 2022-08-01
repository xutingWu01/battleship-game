package edu.duke.xw218.battleship;

/**
 * This class is used to check that the ship will be placed in the validate area
 * of the board. Specifically, the coordinate's row and column needs to be >=0
 * and < board's row and column.
 */
public class InBoundsRuleChecker<T> extends PlacementRuleChecker<T> {

  public InBoundsRuleChecker(PlacementRuleChecker<T> next) {
    super(next);
  }

  /**
   * iterate over all the coordinates in theShip and check that they are in bounds
   * on theBoard
   */
  @Override
  protected String checkMyRule(Ship<T> theShip, Board<T> theBoard) {
    // TODO Auto-generated method stub
    Iterable<Coordinate> coors = theShip.getCoordinates();
    int width = theBoard.getWidth();
    int height = theBoard.getHeight();
    for (Coordinate c : coors) {
      if ((0 <= c.getColumn()) && (c.getColumn() < width) && (0 <= c.getRow()) && (c.getRow() < height)) {
        continue;
      } else {
        // return false;
        if (0 > c.getColumn()) {
          return "That placement is invalid: the ship goes off the left of the board.";
        } else if (c.getColumn() >= width) {
          return "That placement is invalid: the ship goes off the right of the board.";
        } else if (0 > c.getRow()) {
          return "That placement is invalid: the ship goes off the top of the board.";
        }
        // if(c.getRow() >= height){
        else {
          return "That placement is invalid: the ship goes off the bottom of the board.";
        }
      }
    }
    return "";//return null
  }

}
