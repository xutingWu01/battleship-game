package edu.duke.xw218.battleship;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class CombineShip<T> extends BasicShip<T> {
  HashSet<Coordinate> pieces;
  String name;
  
  @Override
  public String getName(){
    return this.name;
  }
  public CombineShip(HashSet<Coordinate> coor1, ShipDisplayInfo<T> info, ShipDisplayInfo<T> enemyDisplayInfo, String name){
    super(coor1, info, enemyDisplayInfo);
    pieces = coor1;
    this.name = name;
  }

}
