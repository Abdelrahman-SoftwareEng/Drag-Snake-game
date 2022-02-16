package hw3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import api.GridUtil;
import api.SnakeLayoutException;

/**
 * Utility methods for working with Snakes.
 *
 * @author Abdelrahman Mohameds
 */
public class SnakeUtil {
    /**
     * Private constructor disables instantiation.
     */
    private SnakeUtil() {
    }

    /**
     * Given a descriptor array, return a list of all the snakes it contains. Snake
     * pieces are represented in the descriptor array by id strings consisting of a
     * single character followed by a number. All other text elements in the
     * descriptor are single characters only and are ignored by this method. The
     * first character of the id string becomes the "id" for the Snake. The number
     * following the id becomes the actual list index for the corresponding piece of
     * the snake. For example, if row 3 and column 5 of the descriptor is the string
     * "g7", then the snake with id 'g' should have, at index 7 of its pieces list,
     * a SnakePiece with row 3, column 5 .
     * <p>
     * This method first calls GridUtil.validateDescriptor, which may throw a
     * SnakeLayoutException. In addition, after constructing the list of snakes,
     * this method checks isValid() on each snake and throws SnakeLayoutException if
     * any snake is found to be invalid.
     * <p>
     * The order of snakes within the returned list is unspecified.
     *
     * @param desc descriptor array
     * @return list of snakes in the descriptor
     * @throws SnakeLayoutException
     * if the descriptor fails to pass GridUtil.validateDescriptor
     * or if any of the resulting snakes is invalid
     */
    public static ArrayList<Snake> findSnakes(String[] desc) {

        GridUtil.validateDescriptor(desc);

        ArrayList<Snake> SnakesFound = new ArrayList<>();
        //Goes over the descriptor array searching for snakes pieces
        for (int i = 0; i < desc.length; i++) {

            Scanner scnr = new Scanner(desc[i]);

            int j = 0;
            while (scnr.hasNext()) {

                String snakepiece = scnr.next();

                if (snakepiece.length() > 1) {

                    char id = snakepiece.charAt(0);
                    String index = snakepiece.substring(1, snakepiece.length());

                    if (SnakesFound.isEmpty()) {

                        Snake snake = new Snake(id);
                        snake.addPiece(Integer.parseInt(index), i, j);
                        SnakesFound.add(snake);
                    } else {

                        boolean toggle = false;
                        for (int k = 0; k < SnakesFound.size(); k++) {

                            if (SnakesFound.get(k).getId() == id) {

                                SnakesFound.get(k).addPiece(Integer.parseInt(index), i, j);
                                toggle = true;
                                break;
                            }

                        }
                        if (!toggle) {

                            Snake anotherSnake = new Snake(id);

                            anotherSnake.addPiece(Integer.parseInt(index), i, j);
                            SnakesFound.add(anotherSnake);

                        }

                    }

                }

                j++;

            }

        }
        for (Snake snake : SnakesFound) {
            if (!snake.isValid()) {
                throw new SnakeLayoutException("Invalid snake '" + snake.getId() + "'.");
            }
        }

        return SnakesFound;
    }

    /**
     * Reads a the given file and creates a string array from each block of text
     * separated by whitespace. The string arrays are returned in an ArrayList.
     * Despite the name of this method, it does NO checking for whether a given
     * block of text actually represents a descriptor for the game. The file is
     * assumed to end with a blank line.
     *
     * @param filename name of the file to read
     * @return ArrayList of string arrays, one for each block of text
     * @throws FileNotFoundException if the named file cannot be opened
     */
    public static ArrayList<String[]> createDescriptorsFromFile(String filename) throws FileNotFoundException {

        ArrayList<String[]> blocks = new ArrayList<>();

        File file = new File(filename);

        Scanner scnr = new Scanner(file);
        ArrayList<String> block = new ArrayList<>();

        while (scnr.hasNextLine()) {
            String line = scnr.nextLine();
            if (line.length() > 0) {

                block.add(line);

            } else {

                if (block.size() != 0) {

                    String[] blockStringArray = new String[block.size()];
                    for (int i = 0; i < block.size(); i++) {

                        blockStringArray[i] = block.get(i);

                    }
                    blocks.add(blockStringArray);
                    //clears the string I have to refill it again
                    block.clear();
                }

                else {
                    continue;
                }
            }
        }

        return blocks;
    }
}
