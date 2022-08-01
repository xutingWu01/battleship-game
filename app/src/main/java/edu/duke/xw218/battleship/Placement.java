package edu.duke.xw218.battleship;

public class Placement {
  private final Coordinate where;
  private final char orientation;

  Coordinate getCoordinate() {
    return this.where;
  }

  char getOrientation() {
    return this.orientation;
  }

  public Placement(Coordinate c, char o) {
    this.where = c;
    char o_temp = Character.toUpperCase(o);
    if (o_temp != 'V' && o_temp != 'H') {
      throw new IllegalArgumentException("That placement is invalid: it does not have the correct format.");
    }
    this.orientation = o_temp;
  }

  public Placement(Coordinate c, char o, boolean if_rec) {
    this.where = c;
    char o_temp = Character.toUpperCase(o);
    if (if_rec == true) {
      if (o_temp != 'V' && o_temp != 'H') {
        throw new IllegalArgumentException("That placement is invalid: it does not have the correct format.");
      }
    } else {
      if (o_temp != 'U' && o_temp != 'D' && o_temp != 'L' && o_temp != 'R') {
        throw new IllegalArgumentException("That placement is invalid: it does not have the correct format.");
      }
    }
    this.orientation = o_temp;
  }

  public Placement(String str) {
    String temp = str.toUpperCase();
    if (str.length() != 3) {
      throw new IllegalArgumentException("invalid input length\n");
    }
    char o_temp = Character.toUpperCase(str.charAt(2));
    if (o_temp != 'V' && o_temp != 'H') {
      throw new IllegalArgumentException("That placement is invalid: it does not have the correct format.");
    }
    this.orientation = o_temp;
    this.where = new Coordinate(temp.substring(0, 2));

  }

  public Placement(String str, boolean if_rec) {
    String temp = str.toUpperCase();
    if (str.length() != 3) {
      throw new IllegalArgumentException("invalid input length\n");
    }
    char o_temp = Character.toUpperCase(str.charAt(2));
    if (if_rec == true) {
      if (o_temp != 'V' && o_temp != 'H') {
        throw new IllegalArgumentException("That placement is invalid: it does not have the correct format.");
      }
    } else {
      if (o_temp != 'U' && o_temp != 'D' && o_temp != 'L' && o_temp != 'R') {
        throw new IllegalArgumentException("That placement is invalid: it does not have the correct format.");
      }
    }

    this.orientation = o_temp;
    this.where = new Coordinate(temp.substring(0, 2));
  }

  @Override
  public boolean equals(Object o) {
    if (o.getClass().equals(getClass())) {
      Placement p = (Placement) o;
      return where.equals(p.getCoordinate()) && orientation == p.getOrientation();
    }
    return false;
  }

  @Override
  public String toString() {
    return "(" + where + ", " + orientation + ")";
  }

  @Override
  public int hashCode() {
    return toString().hashCode();
  }

}
