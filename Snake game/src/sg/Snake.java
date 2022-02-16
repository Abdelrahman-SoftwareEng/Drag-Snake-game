package hw3;

import java.util.ArrayList;

import api.Direction;
import api.SnakePiece;

/**
 * A Snake is a sequence of (row, column) pairs in which each (row, column) in
 * the sequence represents a 2d position that is horizontally or vertically
 * adjacent to the previous one. Each (row, column) is represented by an
 * instance of the class SnakePiece. The first piece in the sequence is called
 * the "head", and the last one is called the "tail". In addition to the list of
 * SnakePiece objects, a Snake has an id consisting of a single character.
 * <p>
 * A snake may or may not be <em>valid</em>. More precisely, a snake is defined
 * to be valid if:
 * <ul>
 * <li>It has at least two pieces
 * <li>There are no null elements in its pieces list
 * <li>There are no duplicates in its pieces list
 * <li>Each piece in the list has a row and column position that is horizontally
 * or vertically adjacent to the previous piece in the list
 * </ul>
 * The methods to add pieces to a snake do NOT do any error-checking. Instead,
 * clients can use the method isValid to check whether the snake is valid.
 *
 * @author Abdelrahman Mohamed
 */
public class Snake {

  /**
   * Snake's ID
   */
  private char ID;

  /**
   * The snake pieces
   */

  private ArrayList<SnakePiece> snake;

  /**
   * Constructs a Snake with an empty list of SnakePiece objects and with the
   * given character as its ID.
   *
   * @param givenId ID to use for this Snake
   */
  public Snake(char givenId) {

    ArrayList<SnakePiece> snake = new ArrayList<SnakePiece>();
    this.snake = snake;
    ID = givenId;

  }

  /**
   * Adds a new SnakePiece to the end of this Snake's list of pieces. This method
   * does no error-checking to ensure the given position is actually adjacent to
   * the current tail.
   *
   * @param row row for the new SnakePiece
   * @param col column for the new SnakePiece
   */
  public void addPiece(int row, int col) {
    SnakePiece toAdd = new SnakePiece(row, col);
    snake.add(toAdd);
  }

  /**
   * Sets this Snake's list of pieces at the given index to be a new SnakePiece
   * with the given row and column. If the current list of pieces is shorter than
   * the given index, it is padded with nulls so the given index can be set. This
   * method does no error-checking to ensure the given (row, column) is actually
   * adjacent to its neighbors in the list of pieces.
   *
   * @param index index in the list of pieces where the new SnakePiece will be set
   * @param row   row for the new SnakePiece
   * @param col   column for the new SnakePiece
   */
  public void addPiece(int index, int row, int col) {

    SnakePiece toAdd = new SnakePiece(row, col);

    //Adds null before the given piece if the the given index is bigger than the snake's size
    for (int i = snake.size() - 1; i < index ; i++) {
      snake.add(i + 1, null);

    }



    //Sets the given piece at the given index
    snake.set(index, toAdd);

  }

  /**
   * Returns the ID for this Snake.
   *
   * @return ID for this Snake
   */
  public char getId() {
    return ID;
  }

  /**
   * Returns true if the ID is one of the characters 'g' or 'G'.
   *
   * @return true if ID is 'g' or 'G'
   */
  public boolean isGreen() {

    if (ID == 'g' || ID == 'G') {

      return true;
    }

    return false;
  }

  /**
   * Returns the first piece in this Snake's list of pieces, or null if this snake
   * has no pieces.
   *
   * @return first piece
   */
  public SnakePiece getHead() {

    if (snake.isEmpty()) {

      return null;
    }

    return snake.get(0);

  }

  /**
   * Returns the last piece in this Snake's list of pieces, or null if this snake
   * has no pieces.
   *
   * @return last piece
   */
  public SnakePiece getTail() {
    if (snake.isEmpty()) {

      return null;
    }

    return snake.get(snake.size() - 1);
  }

  /**
   * Returns true if given row and column match the row and column of this Snake's
   * first piece. Returns false if this snake has no pieces.
   *
   * @param row given row
   * @param col given column
   * @return true if the head of this Snake has the same row and column
   */
  public boolean isHead(int row, int col) {

    SnakePiece Head = snake.get(0);
    SnakePiece GivenHead = new SnakePiece(row, col);

    return Head.equals(GivenHead);
  }

  /**
   * Returns true if given row and column match the row and column of this Snake's
   * last piece. Returns false if this snake has no pieces.
   *
   * @param row given row
   * @param col given column
   * @return true if the tail of this Snake has the same row and column
   */
  public boolean isTail(int row, int col) {
    SnakePiece Tail = snake.get(snake.size() - 1);
    SnakePiece GivenTail = new SnakePiece(row, col);

    return Tail.equals(GivenTail);
  }

  /**
   * Returns this Snake's list of pieces. Normally this method is used to render
   * or display the game; clients should not directly modify the snakes through
   * this method.
   *
   * @return list of SnakePiece objects for this Snake
   */
  public ArrayList<SnakePiece> getPieces() {

    return snake;
  }

  /**
   * Moves the head of this Snake in the given direction without changing its
   * length. Does nothing if the snake has fewer than two pieces.
   *
   * @param dir which direction
   */
  public void moveHead(Direction dir) {


    if (snake.size() >= 2) {

      SnakePiece NewHead = null;

      if (dir==Direction.UP){
        NewHead = new SnakePiece(snake.get(0).row() - 1, snake.get(0).col());
      }
      if (dir==Direction.DOWN){
        NewHead = new SnakePiece(snake.get(0).row() + 1, snake.get(0).col());
      }
      if (dir==Direction.RIGHT){
        NewHead = new SnakePiece(snake.get(0).row(), snake.get(0).col() + 1);
      }
      if (dir==Direction.LEFT){
        NewHead = new SnakePiece(snake.get(0).row(), snake.get(0).col() - 1);
      }

      snake.add(0, NewHead);
      snake.remove(snake.size() - 1);
    }
  }


  /**
   * Moves the tail of this Snake in the given direction without changing its
   * length. Does nothing if the snake has fewer than two pieces.
   *
   * @param dir which direction
   */
  public void moveTail(Direction dir) {



    if (snake.size() >= 2) {
      SnakePiece NewTail = null;

      if (dir==Direction.RIGHT){
        NewTail = new SnakePiece(snake.get(snake.size() - 1).row(), snake.get(snake.size() - 1).col() + 1);
      }

      if (dir==Direction.LEFT) {
        NewTail = new SnakePiece(snake.get(snake.size() - 1).row(), snake.get(snake.size() - 1).col() - 1);
      }

      if (dir==Direction.UP) {
        NewTail = new SnakePiece(snake.get(snake.size() - 1).row() - 1, snake.get(snake.size() - 1).col());
      }
      if (dir==Direction.DOWN){
        NewTail = new SnakePiece(snake.get(snake.size() - 1).row() + 1, snake.get(snake.size() - 1).col());
      }
      snake.add(snake.size(), NewTail);
      snake.remove(0);
    }


  }

  /**
   * Moves the head of this Snake in the given direction, increasing its length by
   * 1. Does nothing if the snake has fewer than two pieces.
   *
   * @param dir which direction
   */
  public void moveHeadAndGrow(Direction dir) {


    if (snake.size() >= 2) {

      SnakePiece NewHead = null;

      if (dir==Direction.UP) {
        NewHead = new SnakePiece(snake.get(0).row() - 1, snake.get(0).col());
      }
      if (dir==Direction.DOWN) {
        NewHead = new SnakePiece(snake.get(0).row() + 1, snake.get(0).col());
      }
      if (dir==Direction.RIGHT) {
        NewHead = new SnakePiece(snake.get(0).row(), snake.get(0).col() + 1);
      }
      if (dir==Direction.LEFT) {
        NewHead = new SnakePiece(snake.get(0).row(), snake.get(0).col() - 1);
      }

      snake.add(0, NewHead);

    }
  }



  /**
   * Moves the head of this Snake in the given direction, decreasing its length by
   * 1. Does nothing if this Snake fewer than three pieces.
   *
   * @param dir which direction
   */
  public void moveHeadAndShrink(Direction dir) {


    if (snake.size() >= 3) {

      SnakePiece NewHead = null;

      if (dir==Direction.UP) {
        NewHead = new SnakePiece(snake.get(0).row() - 1, snake.get(0).col());
      }
      if (dir==Direction.DOWN) {
        NewHead = new SnakePiece(snake.get(0).row() + 1, snake.get(0).col());
      }
      if (dir==Direction.RIGHT) {
        NewHead = new SnakePiece(snake.get(0).row(), snake.get(0).col() + 1);
      }
      if (dir==Direction.LEFT) {
        NewHead = new SnakePiece(snake.get(0).row(), snake.get(0).col() - 1);
      }

      snake.add(0, NewHead);
      snake.remove(snake.size() - 1);
      snake.remove(snake.size() - 1);
    }

  }

  /**
   * Determines whether this snake is valid. A snake is <em>valid</em> if
   * <ul>
   * <li>It has at least two pieces
   * <li>There are no null elements in its pieces list
   * <li>There are no duplicates in its pieces list
   * <li>Each piece in the list has a row and column position that is horizontally
   * or vertically adjacent to the previous piece in the list
   * </ul>
   *
   * @return true if this snake is valid, false otherwise
   */
  public boolean isValid() {


    //Checks if it has 2 or more pieces and no null elements in its pieces list
    if (snake.size() >= 2 && !snake.contains(null)) {


      //Checking if each piece in the list has a row and column position
      //that is horizontally or vertically adjacent to the previous piece in the list
      for(int i=0; i<snake.size()-1; ++i){

        if(snake.get(i).row()==snake.get(i+1).row()) {

          if(snake.get(i).col()!=snake.get(i+1).col()+1 && snake.get(i).col()!=snake.get(i+1).col()-1) {

            return false;

          }

        }
        if(snake.get(i).row()!=snake.get(i+1).row()) {

          if(snake.get(i).row()!= snake.get(i+1).row()+1 && snake.get(i).row()!= snake.get(i+1).row()-1) {

            return false;
          }

          if(snake.get(i).col()!=snake.get(i+1).col()) {

            return false;

          }

        }



      }


      //checking if there are no duplicates in snake pieces list
      for (int i = 0; i < snake.size() - 1; i++) {
        for(int k=i+1; k<snake.size();k++) {

          if(snake.get(i).row()==snake.get(k).row()){
            if(snake.get(i).col()==snake.get(k).col()) {

              return false;
            }
          }

        }

      }

      return true;
    }

    return false;
  }

}
