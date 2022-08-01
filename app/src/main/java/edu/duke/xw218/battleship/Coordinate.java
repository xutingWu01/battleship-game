package edu.duke.xw218.battleship;

public class Coordinate {
  private final int row;
  private final int column;

  public int getRow(){
    return this.row;
  }

  public int getColumn(){
    return this.column;
  }

  /**
   *Contrusctor for setting column and row value
   *@param r represent row number
   *@param c represents column number
   */
  public Coordinate(int r, int c){
    this.row = r;
    this.column = c;
  }
  /**
   *Constructor for taking a string then converting its to right position on the board
   *@param descr represent the coordinate loction, like A2
   */
  public Coordinate(String descr) {
    String upper_descr = descr.toUpperCase();
    if(descr.length()!=2){
      throw new IllegalArgumentException("input must have length of 2\n");
    }
    char letter = upper_descr.charAt(0);
    if(letter>'Z' || letter<'A'){
      throw new IllegalArgumentException("input is invalid\n"); 
    }
    this.row = letter-'A';
    if(upper_descr.charAt(1)<'0' || upper_descr.charAt(1)>'9'){
      throw new IllegalArgumentException("input is invalid 2\n");
    }
    this.column = Character.getNumericValue(upper_descr.charAt(1));
    
  }
  /**
   * Compare whether 2 coordinates are the same
   * if same, return true, else return false
   */
 @Override
  public boolean equals(Object o){
    if (o.getClass().equals(getClass())) {
      Coordinate c = (Coordinate) o;
      return row == c.row && column == c.column;
    }
    return false;
  }

  /**
   *Convert Coordinate class to string
   */
  @Override
  public String toString() {
    return "("+row+", " + column+")";
  }
  /**
   * Hash Coordinate with the help of string hash
   */
  @Override
  public int hashCode() {
    return toString().hashCode(); 
  }
 }
