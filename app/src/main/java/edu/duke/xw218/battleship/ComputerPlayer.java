package edu.duke.xw218.battleship;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Random;

public class ComputerPlayer extends Player {
  // final BufferedReader inputReader;
  final PrintStream out;
  Random r;
  public ComputerPlayer(String name, Board<Character> theBoard, PrintStream out,
                        AbstractShipFactory<Character> shipFactory, Random random) {
    this.name = name;
    this.theBoard = theBoard;
    this.view = new BoardTextView(theBoard);
    this.out = out;
    this.shipFactory = shipFactory;
    this.r = random;
  }

  public void doPlacementPhase() throws IOException {
    Ship<Character> ship1 = shipFactory.makeSubmarine(new Placement("a0h"));
    Ship<Character> ship2 = shipFactory.makeSubmarine(new Placement("b0h"));
    Ship<Character> ship3 = shipFactory.makeDestroyer(new Placement("c0h"));
    Ship<Character> ship4 = shipFactory.makeDestroyer(new Placement("d0h"));
    Ship<Character> ship5 = shipFactory.makeDestroyer(new Placement("e0h"));
    Ship<Character> ship6 = shipFactory.makeBattleship(new Placement("a4u", false));
    Ship<Character> ship7 = shipFactory.makeBattleship(new Placement("c3r", false));
    Ship<Character> ship8 = shipFactory.makeBattleship(new Placement("a2d", false));
    Ship<Character> ship9 = shipFactory.makeCarrier(new Placement("g0r", false));
    Ship<Character> ship10 = shipFactory.makeCarrier(new Placement("e2l", false));
    theBoard.tryAddShip(ship1);
    theBoard.tryAddShip(ship2);
    theBoard.tryAddShip(ship3);
    theBoard.tryAddShip(ship4);
    theBoard.tryAddShip(ship5);
    theBoard.tryAddShip(ship6);
    theBoard.tryAddShip(ship7);
    theBoard.tryAddShip(ship8);
    theBoard.tryAddShip(ship9);
    theBoard.tryAddShip(ship10);
  }

  public boolean playOneTurn(Board<Character> enemyBoard, BoardTextView enemyView) throws IOException {
    // randomly choose where to shoot at
    //Random r = new Random();
    int row = r.nextInt(theBoard.getHeight() - 1);
    int column = r.nextInt(theBoard.getWidth() - 1);
    Coordinate coordinate = new Coordinate(row, column);
    int row_int = 'A' - 0;
    char row_char = (char) (row_int + coordinate.getRow());
    if (enemyBoard.fireAt(coordinate) == null) {
      out.println("Player " + this.name + " missed at " + row_char + coordinate.getColumn());
    } else {
      Character ship = enemyBoard.whatIsAtForEnemy(coordinate);
      String hit_msg;
      if (ship == 's') {
        hit_msg = "Player " + this.name + " hit your submarine at " + row_char + coordinate.getColumn() + "!";
      } else if (ship == 'd') {
        hit_msg = "Player " + this.name + " hit your destroyer at " + row_char + coordinate.getColumn() + "!";
        // hit_msg = "You hit a Destroyer";
      } else if (ship == 'c') {
        hit_msg = "Player " + this.name + " hit your carrier at " + row_char + coordinate.getColumn() + "!";
      } else {
        hit_msg = "Player " + this.name + " hit your battleship at " + row_char + coordinate.getColumn() + "!";
      }
      out.println(hit_msg);
    }
    boolean result = enemyBoard.winOrlose();
    if (result == true) {
      String msg = "Player" + this.name + "you won!";
      out.println(msg);
      return false;
    }
    return true;
  }
}
