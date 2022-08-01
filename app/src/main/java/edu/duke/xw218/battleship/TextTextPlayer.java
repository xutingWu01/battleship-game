package edu.duke.xw218.battleship;

import java.io.BufferedReader;
import java.io.PrintStream;
import java.util.Collections;

public class TextTextPlayer extends TextPlayer{

public TextTextPlayer(String name, Board<Character> theBoard, BufferedReader input, PrintStream out,AbstractShipFactory<Character> shipFactory){
    super(name, theBoard, input, out, shipFactory);
  }
  @Override
  protected void setupShipCreationList() {
    shipsToPlace.addAll(Collections.nCopies(1, "Submarine"));
    shipsToPlace.addAll(Collections.nCopies(1, "Destroyer"));
    shipsToPlace.addAll(Collections.nCopies(1, "Battleship"));
    shipsToPlace.addAll(Collections.nCopies(1, "Carrier"));
  }
}
