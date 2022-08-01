package edu.duke.xw218.battleship;

import java.io.IOException;

public abstract class Player {
  String name;
  AbstractShipFactory<Character> shipFactory;
  Board<Character> theBoard;
  BoardTextView view;

  public Board<Character> getBoard() {
    return this.theBoard;
  }

  public BoardTextView getView() {
    return this.view;
  }
  public String getName(){
    return this.name;
  }
  public abstract void doPlacementPhase() throws IOException;
  
  public abstract boolean playOneTurn(Board<Character> enemyBoard, BoardTextView enemyView) throws IOException;
  
}
