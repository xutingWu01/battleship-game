package edu.duke.xw218.battleship;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * This class is used for read the input and display the ships accordingly.
 */
public class TextPlayer extends Player {
  // final Board<Character> theBoard;
  // final BoardTextView view;
  final BufferedReader inputReader;
  final PrintStream out;
  // final AbstractShipFactory<Character> shipFactory;
  // final String name;
  protected ArrayList<String> shipsToPlace;
  private HashMap<String, Function<Placement, Ship<Character>>> shipCreationFns;
  int move_times;
  int scan_times;
  // LinkedHashMap<Coordinate, Character> hitsLocation;

  public TextPlayer(String name, Board<Character> theBoard, BufferedReader input, PrintStream out,
      AbstractShipFactory<Character> shipFactory) {
    this.name = name;
    this.theBoard = theBoard;
    this.view = new BoardTextView(theBoard);
    this.inputReader = input;
    this.out = out;
    this.shipFactory = shipFactory;
    shipsToPlace = new ArrayList<String>();
    setupShipCreationList();
    shipCreationFns = new HashMap<String, Function<Placement, Ship<Character>>>();
    setupShipCreationMap();
    move_times = 2;
    scan_times = 1;
  }
  /*
   * public Board<Character> getBoard() { return theBoard; }
   * 
   * public BoardTextView getView() { return view; }
   */

  /**
   * Map ships name to ship construction functions
   */
  protected void setupShipCreationMap() {
    shipCreationFns.put("Submarine", (p) -> shipFactory.makeSubmarine(p));
    shipCreationFns.put("Destroyer", (p) -> shipFactory.makeDestroyer(p));
    shipCreationFns.put("Battleship", (p) -> shipFactory.makeBattleship(p));
    shipCreationFns.put("Carrier", (p) -> shipFactory.makeCarrier(p));
  }

  /**
   * Sets up ship creation list
   */
  protected void setupShipCreationList() {
    shipsToPlace.addAll(Collections.nCopies(2, "Submarine"));
    shipsToPlace.addAll(Collections.nCopies(3, "Destroyer"));
    shipsToPlace.addAll(Collections.nCopies(3, "Battleship"));
    shipsToPlace.addAll(Collections.nCopies(2, "Carrier"));
  }

  /**
   * Read a placement from user input
   * 
   * @param prompt: to prompt user to enter the placement info
   * @return the new placement according to placement
   */
  public Placement readPlacement(String prompt, String shipName) throws IOException {
    out.println(prompt);
    String s = inputReader.readLine();
    try {
      if (shipName == "Carrier" || (shipName == "Battleship")) {
        Placement p = new Placement(s, false);
        return p;
      }
      Placement p = new Placement(s, true);
      return p;
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("That placement is invalid: it does not have the correct format.");
    }
    // return new Placement(s);
  }

  /**
   * Creates a placement object according to user input
   */
  public void doOnePlacement(String shipName, Function<Placement, Ship<Character>> createFn) throws IOException {
    String prompt = "Player " + name + " where do you want to place a " + shipName + "?";
    Placement p;
    int flag = 0;
    while (flag == 0) {
      try {
        p = readPlacement(prompt, shipName);
        Ship<Character> ship = createFn.apply(p);
        String ans = theBoard.tryAddShip(ship);
        if (ans == null) {
          out.print(view.displayMyOwnBoard());
          flag = 1;
        } else {
          out.println(ans);
        }
      } catch (IllegalArgumentException e) {
        out.println("That placement is invalid: it does not have the correct format.");
        // throw e;}
      }
    }

  }

  /**
   * Creates a placement object according to user input
   */
  public boolean doOneMovePlacement(String shipName, Function<Placement, Ship<Character>> createFn,
      LinkedHashMap<Coordinate, Boolean> oldmap) throws IOException {
    String prompt = "Player " + name + " where do you want to place a " + shipName + "?";
    Placement p;
    try {
      p = readPlacement(prompt, shipName);
      Ship<Character> ship = createFn.apply(p);
      String ans = theBoard.tryAddShip(ship);
      if (ans == null) {
        ship.setMyPieces(oldmap);
        theBoard.addToEmptyLocation(ship);
        out.print(view.displayMyOwnBoard());
        return true;
      } else {
        out.println(ans);
        return false;
      }
    } catch (IllegalArgumentException e) {
      out.println("That placement is invalid: it does not have the correct format.");
      // throw e;}
    }
    return false;
  }

  /**
   * Displays an empty board
   */
  public void doPlacementPhase() throws IOException {
    // view.displayMyOwnBoard();
    String prompt = "Player " + name + ":"
        + "you are going to place the following ships (which are all rectangular).\nFor each ship, type the coordinate of the upper left side of the ship, followed by either H (for horizontal) or V (for vertical).\nFor example M4H would place a ship horizontally starting at M4 and going to the right. You have\n\n2 \"Submarines\" ships that are 1x2\n3 \"Destroyers\" that are 1x3\n3 \"Battleships\" that are 1x4\n2 \"Carriers\" that are 1x6\n";
    out.print(prompt);
    out.print(view.displayMyOwnBoard());
    // doOnePlacement();
    for (int i = 0; i < shipsToPlace.size(); i++) {
      Function<Placement, Ship<Character>> func = shipCreationFns.get(shipsToPlace.get(i));
      doOnePlacement(shipsToPlace.get(i), func);
    }

  }

  /**
   * Reads action from user: fire, move or scan
   * 
   * @param prompt the prompt string
   * @return string indicating whether success or not
   */
  public String readAction(String prompt) throws IOException {
    out.println(prompt);
    String s = inputReader.readLine().toUpperCase();
    if ((s.equals("F")) || (s.equals("M")) || (s.equals("S"))) {
      return s;
    } else {
      return null;
    }
  }

  /**
   * Read a coordinate from user input
   * 
   * @param prompt: to prompt user to enter the placement info
   * @return the new placement according to placement
   */
  public Coordinate readCoordinate(String prompt) throws IOException {
    out.println(prompt);
    String s = inputReader.readLine();
    try {
      Coordinate p = new Coordinate(s);
      return p;
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("That coornidate is invalid: it does not have the correct format.");
    }
    // return new Placement(s);
  }
  /**
   * Execute fire instructions, display miss or display the hit information
   * 
   */
  public void fireAction(Board<Character> enemyBoard, BoardTextView enemyView) throws IOException {
    String prompt = "Player " + this.name + " Where would you like to fire at?";
    Coordinate c;
    while (true) {
      try {
        c = readCoordinate(prompt);
        break;
      } catch (IllegalArgumentException e) {
        out.println(e.getMessage());
      }
    }

    if (enemyBoard.fireAt(c) == null) {
      out.println("You missed");
    } else {
      Character ship = enemyBoard.whatIsAtForEnemy(c);
      String hit_msg;
      if (ship == 's') {
        hit_msg = "You hit a Submarine";
      } else if (ship == 'd') {
        hit_msg = "You hit a Destroyer";
      } else if (ship == 'c') {
        hit_msg = "You hit a Carrier";
      } else {
        hit_msg = "You hit a Battleship";
      }
      out.println(hit_msg);
    }
  }
  /**
   * Records the hits location to conceal the fact that the ship has been removed
   */
  public void recordHits(String name, LinkedHashMap<Coordinate, Boolean> oldmap) {
    for (Map.Entry<Coordinate, Boolean> entry : oldmap.entrySet()) {
      if (theBoard.whatIsAtForEnemy(entry.getKey()) != null) {
        theBoard.addToHitsLocation(theBoard.whatIsAtForEnemy(entry.getKey()), entry.getKey());
      }
    }
  }
  /**
   * Delete the hits location when move action encounter failure
   */
  public void deleteHits(LinkedHashMap<Coordinate, Boolean> oldmap) {
    for (Map.Entry<Coordinate, Boolean> entry : oldmap.entrySet()) {
      theBoard.removeFromHitsLocation(entry.getKey());
    }
  }
  /**
   * Execute scan operation
   */
  public boolean scanAction() throws IOException {
    String prompt = "Player " + this.name + " Which coordinate do you want to sonar scan?";
    Coordinate c;
    try {
      c = readCoordinate(prompt);
    } catch (IllegalArgumentException e) {
      out.println(e.getMessage());
      return false;
    }

    HashMap<String, Integer> res = theBoard.sonarScan(c);

    // print result
    for (Map.Entry<String, Integer> entry : res.entrySet()) {
      String ans = entry.getKey() + "s occupies " + entry.getValue() + " squares";
      out.println(ans);
    }
    scan_times--;
    return true;
  }
  /**
   * Execute move operation
   */
  public boolean moveAction() throws IOException {
    String move_prompt = "Player " + this.name + " Which ship do you want to move?";
    Coordinate c;
    Ship<Character> shipToMove;
    try {
      c = readCoordinate(move_prompt);
      shipToMove = theBoard.checkShipExistence(c);
      if (shipToMove == null) {
        out.println("This square dose not belong to any ships");
        return false;
      }
      // break;
    } catch (IllegalArgumentException e) {
      out.println(e.getMessage());
      return false;
    }
    // create new ship and copy the orginal content from old to new
    String shipname = shipToMove.getName();
    Function<Placement, Ship<Character>> func = shipCreationFns.get(shipname);
    LinkedHashMap<Coordinate, Boolean> oldMap = new LinkedHashMap<Coordinate, Boolean>(shipToMove.getMyPieces());
    recordHits(shipname, oldMap);
    theBoard.removeShip(shipToMove);
    if (doOneMovePlacement(shipname, func, oldMap) == false) {
      theBoard.addShip(shipToMove);
      deleteHits(oldMap);
      return false;
    }
    /*
     * } catch (IllegalArgumentException e) { theBoard.addShip(shipToMove);
     * deleteHits(oldMap); return false; }
     */
    // recordHits(shipname, oldMap);
    move_times--;
    return true;
  }

  /**
   * One stage of the game, which includes one player chooose one coordinate to
   * hit at
   * 
   * @param enemyBoard: enemy's board
   * @param enemyView:  enemy's view
   */
  public boolean playOneTurn(Board<Character> enemyBoard, BoardTextView enemyView) throws IOException {
    String myHeader = "Your ocean";
    String enemyHeader;
    if (this.name == "A") {
      enemyHeader = "Player B's ocean";
    } else {
      enemyHeader = "Player A's ocean";
    }
    out.println(view.displayMyBoardWithEnemyNextToIt(enemyView, myHeader, enemyHeader));
    if (move_times == 0 && scan_times == 0) {
      fireAction(enemyBoard, enemyView);
    }
    // TODO: check the times of move and scan operations, and display each action
    // accordingly
    else {
      String ans = null;
      while (true) {
        String prompt = "Possbile actions for Player " + this.name
            + ":\n\nF: Fire at a square.\nM: Move a ship to another square (" + this.move_times
            + " remaining)\nS: Sonar scan(" + this.scan_times + " remaining)\n" + "\nPlayer " + this.name
            + ", what would you like to do?";
        // read input for(fms);
        ans = readAction(prompt);
        if (ans == null) {
          out.println("Please enter f,m,s");
          continue;
        }
        // fire stage
        if (ans.equals("F")) {
          fireAction(enemyBoard, enemyView);
          break;
        }
        if (ans.equals("M") && move_times == 0) {
          out.println("You have no chance to move your ship");
          continue;
        }
        if (ans.equals("M") && move_times > 0) {
          if (moveAction() == true) {
            break;
          }
        }

        if (ans.equals("S") && scan_times == 0) {
          out.println("You have no chance to sonar scan");
          continue;
        }
        if (ans.equals("S") && scan_times > 0) {
          if (scanAction() == true) {
            break;
          }
        }

      }
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
