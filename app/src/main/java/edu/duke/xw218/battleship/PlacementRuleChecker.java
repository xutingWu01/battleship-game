package edu.duke.xw218.battleship;
/**
 * Abstract class to chain error checker rules for add ships
 */
public abstract class PlacementRuleChecker<T> {
  private final PlacementRuleChecker<T> next;
  /**
   * Constructor
   */
  public PlacementRuleChecker(PlacementRuleChecker<T> next) {
    this.next = next;
  }
  
  protected abstract String checkMyRule(Ship<T> theShip, Board<T> theBoard);
  /**
   * Main body to chain rules, if the ship corresponds to the ruler, check the next rule if it has, else return false
   * @param theShip: ship tp add
   * @param theBoard: where to add ship
   * @return true if it can place ship, else return false
   */
  public String checkPlacement (Ship<T> theShip, Board<T> theBoard){
    //if we fail our own rule: stop the placement is not legal
    String ans = checkMyRule(theShip, theBoard);
    if (ans!="") {
      return ans;
    }
    //other wise, ask the rest of the chain.
    if (next!=null) {
      return next.checkPlacement(theShip, theBoard); 
    }
    //if there are no more rules, then the placement is legal
    return "";
  }
}
