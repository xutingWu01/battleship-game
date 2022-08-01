package edu.duke.xw218.battleship;

import java.util.function.Function;

/**
 * This class display a board textual, supports display in two ways
 */
public class BoardTextView {
  private final Board<Character> toDisplay;

  /**
   * Constructs a BoardView
   * 
   * @param toDisplay is the Board to display
   */
  public BoardTextView(Board<Character> toDisplay) {
    this.toDisplay = toDisplay;
    if (toDisplay.getWidth() > 10 || toDisplay.getHeight() > 26) {
      throw new IllegalArgumentException(
          "Board must be no larger than 10x26, but is " + toDisplay.getWidth() + "x" + toDisplay.getHeight());
    }
  }

  /**
   * Makes the header 0/1/2/3..
   */
  String makeHeader() {
    StringBuilder ans = new StringBuilder("  ");// 2 spaces
    String sep = "";
    for (int i = 0; i < toDisplay.getWidth(); i++) {
      ans.append(sep);
      ans.append(i);
      sep = "|";
    }
    ans.append("\n");
    return ans.toString();
  }

  public String displayAnyBoard(Function<Coordinate, Character> getSquareFn) {
    String header = makeHeader();
    StringBuilder ans = new StringBuilder();
    int width = toDisplay.getWidth();
    StringBuilder middle = new StringBuilder(" ");
    /*
     * for(int i=0; i<(width-1); ++i){ if(toDisplay.whatIsAt(new Coordinat(i, i)))
     * middle.append(" |"); }
     */
    ans.append(header);
    // String sep = "";
    for (int i = 0; i < toDisplay.getHeight(); i++) {
      char c = (char) ('A' + i);
      ans.append(c);
      ans.append(" ");
      // ans.append(middle);
      String sep = "";
      for (int j = 0; j < toDisplay.getWidth(); j++) {
        ans.append(sep);
        Character ship = getSquareFn.apply(new Coordinate(i, j));
        // Character ship = toDisplay.whatIsAtForSelf(new Coordinate(i, j));
        if (ship == null) {
          // find whether need to append hard-code miss X
          ans.append(" ");
        } else {
          ans.append(ship);
        }
        sep = "|";
      }
      ans.append(" ");
      ans.append(c);
      ans.append("\n");
    }
    ans.append(header);
    return ans.toString();
  }

  public String displayMyOwnBoard() {
    return displayAnyBoard((c) -> toDisplay.whatIsAtForSelf(c));
  }

  public String displayEnemyBoard() {
    return displayAnyBoard((c) -> toDisplay.whatIsAtForEnemy(c));
  }

  /**
   * Display two boards side by side
   * 
   * @param enemyView the enemy's board view
   * @param my        Header of the board
   * @param header    of enemy board
   * @return the cancanated string of two boards
   */
  public String displayMyBoardWithEnemyNextToIt(BoardTextView enemyView, String myHeader, String enemyHeader) {
    String my = this.displayMyOwnBoard();
    String enemy = enemyView.displayEnemyBoard();
    String[] my_lines = my.split("\n");
    String[] enemy_lines = enemy.split("\n");
    StringBuilder str = new StringBuilder();
    str.append("     ");// indent for my header
    str.append(myHeader);
    StringBuilder header_blank = new StringBuilder();
    for (int i = 0; i < (2 * toDisplay.getWidth() + 22 - 4 - myHeader.length()); i++) {
      header_blank.append(" ");
    }
    str.append(header_blank);
    str.append(enemyHeader);
    str.append("\n");
    // todo: add board header: 0|1|2
    str.append(my_lines[0]);
    str.append("  ");// 2 blank
    str.append("                ");// 16 blank
    str.append(enemy_lines[0]);
    str.append("\n");
    // todo: display board
    for (int i = 1; i < (toDisplay.getHeight() + 1); i++) {
      str.append(my_lines[i]);
      str.append("                ");
      str.append(enemy_lines[i]);
      str.append("\n");
    }
    // todo: add bottom header
    str.append(my_lines[0]);
    str.append("                  ");// 16 blank
    str.append(enemy_lines[0]);
    return str.toString();
  }
}
