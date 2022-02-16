package hw3;

import java.util.ArrayList;
//import static Status;

import api.BasketCell;
import api.Direction;
import api.SnakeLayoutException;
import api.SnakePiece;
import api.GridUtil;

/**
 * This class represents the basic game state for a "Snake Escape" game,
 * including a 2d grid of cells and a list of snakes.
 *
 * @author Abdelrahman Mohamed
 */
public class SnakeBasket {
  /**
   * The 2D array of cells.
   */
  private BasketCell[][] grid;

  /**
   * The list of snakes.
   */
  private ArrayList<Snake> snakes;

  /**
   * number of moves
   */

  private int moveCount;

  /**
   * current snake
   */
  private Snake CURRENTsnake;

  /**
   * True if the current snake is grabbed by head
   */
  private boolean GrabbedbyHead;

  /**
   * True if the game is over and false otherwise
   */
  private boolean isOver;

  /**
   * Constructs an instance of this game from the given string array and list of
   * snakes. <em>Snake information, if any, in the given string array is
   * ignored</em>.
   *
   * @param desc        array of strings representing the initial grid layout
   * @param givenSnakes array of snakes
   */
  public SnakeBasket(String[] desc, ArrayList<Snake> givenSnakes) {
    grid = GridUtil.createGridFromDescriptor(desc);
    snakes = givenSnakes;
    GrabbedbyHead = false;
    isOver = false;
    resetAllSnakes();


  }

  /**
   * Constructs an instance of this game from the given string array.
   *
   * @param desc array of strings representing the initial grid layout, including
   *             ids and placement of snakes
   */
  public SnakeBasket(String[] desc) {
    grid = GridUtil.createGridFromDescriptor(desc);
    snakes = SnakeUtil.findSnakes(desc);
    resetAllSnakes();

  }

  /**
   * Returns the grid cell at the given row and column.
   *
   * @param row given row
   * @param col given column
   * @return grid cell at the given row and column
   */
  public BasketCell getCell(int row, int col) {
    return grid[row][col];
  }

  /**
   * Returns the number of rows in this game.
   *
   * @return number of rows
   */
  public int getRows() {
    return grid.length;
  }

  /**
   * Returns the number of columns in this game.
   *
   * @return number of columns
   */
  public int getCols() {
    return grid[0].length;
  }

  /**
   * Returns the currently grabbed snake, if any. Returns null if there is no
   * current snake.
   *
   * @return current snake, if any
   */
  public Snake currentSnake() {
    return CURRENTsnake;
  }

  /**
   * Returns true if there is a current snake and it was grabbed at the head end,
   * false if it was grabbed by the tail.
   *
   * @return true if current snake was grabbed by the head
   */
  public boolean currentWasGrabbedByHead() {

    if (CURRENTsnake != null) {

      return GrabbedbyHead;
    }

    return false;

  }

  /**
   * Returns this SnakeBasket's list of all snakes. Normally this method is used
   * to easily render or display the game; clients should not modify the list or
   * the snakes.
   *
   * @return list of all snakes
   */
  public ArrayList<Snake> getAllSnakes() {
    return snakes;
  }

  /**
   * Returns true if the green snake is in the exit cell, false otherwise.
   *
   * @return true if green snake is in the exit
   */
  public boolean isOver() {
    return isOver;
  }

  /**
   * Returns the total number of moves that have been made so far in this game.
   *
   * @return number of moves
   */
  public int getMoves() {
    return moveCount;
  }

  /**
   * Attempts to grab a snake by the head or tail at the given position. If there
   * is not already a current snake, and if the given position contains a snake
   * head or tail, that becomes the current snake. If this game already has a
   * current snake selected, this method does nothing.
   *
   * @param row given row at which to grab a snake
   * @param col given column at which to grab a snake
   */
  public void grabSnake(int row, int col) {

    if (CURRENTsnake == null) {

      //Goes through the snakes and checks if the piece is the head or tail of any of the snakes
      for (int i = 0; i < snakes.size(); i++) {

        if (snakes.get(i).isHead(row, col) || snakes.get(i).isTail(row, col)) {

          CURRENTsnake = snakes.get(i);
          GrabbedbyHead = snakes.get(i).isHead(row, col);

        }

      }

    }

  }

  /**
   * Sets the current snake to null, if any.
   */
  public void releaseSnake() {

    CURRENTsnake = null;

  }

  /**
   * Attempt to move the current snake (head or tail) to an adjacent cell in the
   * given direction. If a move is possible, this method updates the current
   * snake, the move count, and the grid cells (via resetAllSnakes).
   * <p>
   * It is only possible to move in the following cases:
   * <ul>
   * <li>The adjacent cell is empty; then the snake (head or tail) moves into the
   * cell
   * <li>The adjacent cell is the exit and the current snake is the green one;
   * then the snake (head or tail) moves into the exit and the game ends
   * <li>The current snake was grabbed by the head, and the adjacent cell is
   * row/column of the current snake's tail; then the snake (head) moves into the
   * cell
   * <li>The current snake was grabbed by the tail, and the adjacent cell is
   * row/column of the current snake's head; then the snake (tail) moves into the
   * cell
   * <li>The current snake was grabbed by the head and the adjacent cell is an
   * apple; then the apple is removed and the snake (head) moves into the cell,
   * increasing its size by one piece
   * <li>The current snake was grabbed by the head, has at least three pieces, and
   * the adjacent cell is a mushroom; then the mushroom is removed and the snake
   * (head) moves into the cell, reducing its size by one piece
   * </ul>
   * If none of the above conditions is met, this method does nothing. If any of
   * the conditions is met and a move occurs, the move count is increased by 1. If
   * there is no currently grabbed snake, this method does nothing.
   *
   * @param dir Direction in which to attempt to move
   */
  public void move(Direction dir) {
    if (!isOver) {

      if (currentWasGrabbedByHead()) {

        if (dir == Direction.UP) {

          if ((grid[CURRENTsnake.getHead().row() - 1][CURRENTsnake.getHead().col()].isEmpty())
                  || (CURRENTsnake.getHead().row() - 1 == CURRENTsnake.getTail().row()
                  && CURRENTsnake.getHead().col() == CURRENTsnake.getTail().col())) {

            CURRENTsnake.moveHead(dir);

            moveCount++;

          }
          else if (grid[CURRENTsnake.getHead().row() - 1][CURRENTsnake.getHead().col()].isApple()) {

            grid[CURRENTsnake.getHead().row() - 1][CURRENTsnake.getHead().col()].clearFood();

            CURRENTsnake.moveHeadAndGrow(dir);

            moveCount++;

          }
          else if (grid[CURRENTsnake.getHead().row() - 1][CURRENTsnake.getHead().col()].isMushroom()) {

            if (CURRENTsnake.getPieces().size() >= 3) {
              grid[CURRENTsnake.getHead().row() - 1][CURRENTsnake.getHead().col()].clearFood();

              CURRENTsnake.moveHeadAndShrink(dir);

              moveCount++;

            }
          }

          else if (grid[CURRENTsnake.getHead().row() - 1][CURRENTsnake.getHead().col()].isExit()) {

            if (CURRENTsnake.isGreen()) {
              CURRENTsnake.moveHead(dir);
              moveCount++;
              isOver = true;
            }
          }
        }
        if (dir == Direction.DOWN) {

          if ((grid[CURRENTsnake.getHead().row() + 1][CURRENTsnake.getHead().col()].isEmpty())
                  || (CURRENTsnake.getHead().row() + 1 == CURRENTsnake.getTail().row()
                  && CURRENTsnake.getHead().col() == CURRENTsnake.getTail().col())) {

            CURRENTsnake.moveHead(dir);

            moveCount++;

          }
          else if (grid[CURRENTsnake.getHead().row() + 1][CURRENTsnake.getHead().col()].isApple()) {

            grid[CURRENTsnake.getHead().row() + 1][CURRENTsnake.getHead().col()].clearFood();
            CURRENTsnake.moveHeadAndGrow(dir);

            moveCount++;

          }
          else if (grid[CURRENTsnake.getHead().row() + 1][CURRENTsnake.getHead().col()].isMushroom()) {

            if (CURRENTsnake.getPieces().size() >= 3) {
              grid[CURRENTsnake.getHead().row() + 1][CURRENTsnake.getHead().col()].clearFood();

              CURRENTsnake.moveHeadAndShrink(dir);

              moveCount++;

            }

          }

          else if (grid[CURRENTsnake.getHead().row() + 1][CURRENTsnake.getHead().col()].isExit()) {

            if (CURRENTsnake.isGreen()) {
              CURRENTsnake.moveHead(dir);
              moveCount++;
              isOver = true;
            }
          }

        }
        if (dir == Direction.RIGHT) {

          if ((grid[CURRENTsnake.getHead().row()][CURRENTsnake.getHead().col() + 1].isEmpty())
                  || (CURRENTsnake.getHead().row() == CURRENTsnake.getTail().row()
                  && CURRENTsnake.getHead().col() + 1 == CURRENTsnake.getTail().col())) {

            CURRENTsnake.moveHead(dir);

            moveCount++;

          }
          else if (grid[CURRENTsnake.getHead().row()][CURRENTsnake.getHead().col() + 1].isApple()) {

            grid[CURRENTsnake.getHead().row()][CURRENTsnake.getHead().col() + 1].clearFood();
            CURRENTsnake.moveHeadAndGrow(dir);

            moveCount++;

          }
          else if (grid[CURRENTsnake.getHead().row()][CURRENTsnake.getHead().col() + 1].isMushroom()) {

            if (CURRENTsnake.getPieces().size() >= 3) {
              grid[CURRENTsnake.getHead().row()][CURRENTsnake.getHead().col() + 1].clearFood();
              CURRENTsnake.moveHeadAndShrink(dir);

              moveCount++;
            }

          }

          else if (grid[CURRENTsnake.getHead().row()][CURRENTsnake.getHead().col() + 1].isExit()) {

            if (CURRENTsnake.isGreen()) {
              CURRENTsnake.moveHead(dir);
              moveCount++;
              isOver = true;
            }
          }

        }

        if (dir == Direction.LEFT) {

          if ((grid[CURRENTsnake.getHead().row()][CURRENTsnake.getHead().col() - 1].isEmpty())
                  || (CURRENTsnake.getHead().row() == CURRENTsnake.getTail().row()
                  && CURRENTsnake.getHead().col() - 1 == CURRENTsnake.getTail().col())) {

            CURRENTsnake.moveHead(dir);

            moveCount++;

          }
          else if (grid[CURRENTsnake.getHead().row()][CURRENTsnake.getHead().col() - 1].isApple()) {

            grid[CURRENTsnake.getHead().row()][CURRENTsnake.getHead().col() - 1].clearFood();
            CURRENTsnake.moveHeadAndGrow(dir);

            moveCount++;

          }
          else if (grid[CURRENTsnake.getHead().row()][CURRENTsnake.getHead().col() - 1].isMushroom()) {

            if (CURRENTsnake.getPieces().size() >= 3) {
              grid[CURRENTsnake.getHead().row()][CURRENTsnake.getHead().col() - 1].clearFood();
              CURRENTsnake.moveHeadAndShrink(dir);

              moveCount++;

            }
          }

          else if (grid[CURRENTsnake.getHead().row()][CURRENTsnake.getHead().col() - 1].isExit()) {

            if (CURRENTsnake.isGreen()) {
              CURRENTsnake.moveHead(dir);
              moveCount++;
              isOver = true;
            }
          }

        }
      }

      else {

        if (dir == Direction.UP) {

          if ((grid[CURRENTsnake.getTail().row() - 1][CURRENTsnake.getTail().col()].isEmpty())
                  || (CURRENTsnake.getTail().row() - 1 == CURRENTsnake.getHead().row()
                  && CURRENTsnake.getTail().col() == CURRENTsnake.getHead().col())) {

            CURRENTsnake.moveTail(dir);

            moveCount++;

          }
          else if (grid[CURRENTsnake.getTail().row() - 1][CURRENTsnake.getTail().col()].isExit()) {

            if (CURRENTsnake.isGreen()) {
              CURRENTsnake.moveTail(dir);
              moveCount++;
              isOver = true;
            }
          }

        }

        if (dir == Direction.DOWN) {

          if ((grid[CURRENTsnake.getTail().row() + 1][CURRENTsnake.getTail().col()].isEmpty())
                  || (CURRENTsnake.getTail().row() + 1 == CURRENTsnake.getHead().row()
                  && CURRENTsnake.getTail().col() == CURRENTsnake.getHead().col())) {

            CURRENTsnake.moveTail(dir);

            moveCount++;

          }

          else if (grid[CURRENTsnake.getTail().row() + 1][CURRENTsnake.getTail().col()].isExit()) {

            if (CURRENTsnake.isGreen()) {
              CURRENTsnake.moveTail(dir);
              moveCount++;
              isOver = true;
            }
          }

        }
        if (dir == Direction.RIGHT) {

          if ((grid[CURRENTsnake.getTail().row()][CURRENTsnake.getTail().col() + 1].isEmpty())
                  || (CURRENTsnake.getTail().row() == CURRENTsnake.getHead().row()
                  && CURRENTsnake.getTail().col() + 1 == CURRENTsnake.getHead().col())) {

            CURRENTsnake.moveTail(dir);

            moveCount++;

          }
          else if (grid[CURRENTsnake.getTail().row()][CURRENTsnake.getTail().col() + 1].isExit()) {

            if (CURRENTsnake.isGreen()) {
              CURRENTsnake.moveTail(dir);
              moveCount++;
              isOver = true;
            }
          }

        }

        if (dir == Direction.LEFT) {

          if ((grid[CURRENTsnake.getTail().row()][CURRENTsnake.getTail().col() - 1].isEmpty())
                  || (CURRENTsnake.getTail().row() == CURRENTsnake.getHead().row()
                  && CURRENTsnake.getTail().col() - 1 == CURRENTsnake.getHead().col())) {

            CURRENTsnake.moveTail(dir);

            moveCount++;

          }

          else if (grid[CURRENTsnake.getTail().row()][CURRENTsnake.getTail().col() - 1].isExit()) {

            if (CURRENTsnake.isGreen()) {
              CURRENTsnake.moveTail(dir);
              moveCount++;
              isOver = true;
            }
          }
        }

      }
    }
    resetAllSnakes();
  }

  /**
   * Clears all snake information from the grid, if any, and then sets it from the
   * current list of snakes. After executing this method, the information in the
   * grid cells and the information in the snake list should be fully consistent.
   */
  public void resetAllSnakes() {

    //Goes over the grid and clears it
    for (int i = 0; i < grid.length; i++) {

      for (int j = 0; j < grid[i].length; j++) {

        if (grid[i][j].hasSnake()) {

          grid[i][j].clearSnake();

        }

      }

    }
    //Updates the grid from the current list of snakes
    for (int i = 0; i < snakes.size(); i++) {

      ArrayList<SnakePiece> currentSnake = snakes.get(i).getPieces();
      for (int j = 0; j < currentSnake.size(); j++) {

        grid[currentSnake.get(j).row()][currentSnake.get(j).col()].setSnake(snakes.get(i));

      }

    }

  }

}
