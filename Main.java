package ivan;
import java.util.*;
public class Main {

    //1) check if input is valid
    //2) check if N nd M are odd or even numbers(should be even)
    //3) check if N nd M eceed 100 (should be less than 100)
    public static boolean checkInput(int rows, int cols) {
        if (rows > 100 || cols > 100 ||
                cols % 2 != 0 || rows % 2 != 0) {
            System.out.println("-1\nNo solution exists");
            return false;
        }
        return true;
    }


    public static boolean checkProperBricks(Cell[][] matrix, Scanner scanner) {
//the validator Map will garantee us that every number appears only 2 times, no more, no less;
        Map<Integer, Integer> validator = new HashMap<>();
        if (checkInput(matrix[0].length, matrix.length)) {

            for (int i = 0; i < matrix.length; i++) {
                //creating an array for each line of input, split by space
                int[] brickNumbers = Arrays.stream(scanner.nextLine().split(" "))
                        .mapToInt(Integer::parseInt)
                        .toArray();

                //going through the input line in order to record the numbers we have received
                for (int j = 0; j < brickNumbers.length; j++) {
                    if (!validator.containsKey(brickNumbers[j])) {
                        validator.put(brickNumbers[j], 1);
                    } else if (validator.get(brickNumbers[j]) == 2) {
                        System.out.println("-1\nNo solution exists");
                        return true;
                    } else {
                        validator.put(brickNumbers[j], 2);
                    }

                    //the current line of the matrix will equal the Object Cell
                    //Object cell has 2 properties - number(int) and boolean;
                    //the boolean property helps us to check an index only once
                    //the check is to make sure that the second number is eather below (vertcal brick)
                    //or next to the current number (a horizontal brick).
                    matrix[i][j] = new Cell(brickNumbers[j]);
                    //a horzontal check
                    if ((j != brickNumbers.length - 1) &&
                            (brickNumbers[j] == brickNumbers[j + 1])) {

                        matrix[i][j].check = true;
                        matrix[i][j + 1] = new Cell(brickNumbers[j]);
                        matrix[i][j + 1].check = true;
                        validator.put(brickNumbers[j], 2);
                        j++;
                    }
                }
            }
        }

        //a verticl check
        for (int i = 0; i < matrix.length - 1; i++) {
            for (int j = 0; j < matrix[0].length - 1; j++) {
                //System.out.println(matrix[i][j].brickNumber);
                if (!matrix[i][j].check) {
                    if (matrix[i][j].brickNumber == matrix[i + 1][j].brickNumber) {
                        matrix[i][j].check = true;
                        matrix[i + 1][j].check = true;
                    } else {
                        System.out.println("-1\nNo solution exists");
                        return true;
                    }
                }
            }
        }
        return false;
    }


    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        //a varible for the dimensions of the matrix
        int[] dimensions = Arrays.stream(scanner.nextLine()
                .split("\\s++"))
                .mapToInt(Integer::parseInt)
                .toArray();
        //number of rows
        int matrixRows = dimensions[0];
        //number of cols
        int matrixCols = dimensions[1];

        //the matrix we will be working with
        Cell[][] matrix = new Cell[matrixRows][matrixCols];

        if (!checkProperBricks(matrix, scanner)) {
            for (int row = 0; row < matrix.length; row += 2) {
                for (int col = 0; col < matrix[0].length; col += 2) {
                    //Case A:
                    //1 1
                    //3 3
                    if ((matrix[row][col].brickNumber != matrix[row + 1][col].brickNumber &&
                            matrix[row][col + 1].brickNumber != matrix[row + 1][col + 1].brickNumber)) {
                        // we pull the top left number (1)
                        int first = matrix[row][col].brickNumber;
                        // we pull the bottom left number (1)
                        int second = matrix[row + 1][col].brickNumber;
                        //change the brick from horizontal to a second layer of vertical
                        matrix[row + 1][col].brickNumber = first;
                        matrix[row][col + 1].brickNumber = second;
                    //Case B:
                    //2 4
                    //2 4
                    } else if ((matrix[row][col].brickNumber == matrix[row + 1][col].brickNumber) &&
                            (matrix[row][col + 1].brickNumber == matrix[row + 1][col + 1].brickNumber)) {
                        // we pull the top left number (2)
                        int first = matrix[row][col].brickNumber;
                        // we pull the bottom right number (4)
                        int second = matrix[row][col + 1].brickNumber;
                        //we create a second layer of horizontal bricks
                        matrix[row][col + 1].brickNumber = first;
                        matrix[row + 1][col].brickNumber = second;
                    //Case C: when we have a vertical brick, then two horzontal bricks
                    //1 2          //2 1
                    //1 3          //3 1
                    } else if (((matrix[row][col].brickNumber == matrix[row + 1][col].brickNumber) &&
                            matrix[row][col + 1].brickNumber != matrix[row + 1][col + 1].brickNumber) ||
                            (matrix[row][col + 1].brickNumber == matrix[row + 1][col + 1].brickNumber) &&
                                    matrix[row][col].brickNumber != matrix[row + 1][col].brickNumber) {
                        // we pull the top left number (1)
                        int first = matrix[row][col].brickNumber;
                        // we pull the bottom right number (3)
                        int third = matrix[row + 1][col + 1].brickNumber;
                        matrix[row][col + 1].brickNumber = first;
                        matrix[row + 1][col].brickNumber = third;
                    //Case D
                    //2 4
                    //3 5
                    } else {
                        // we pull the top left number (2)
                        int first = matrix[row][col].brickNumber;
                        // we pull the bottom right number (5)
                        int third = matrix[row + 1][col + 1].brickNumber;
                        matrix[row + 1][col].brickNumber = first;
                        matrix[row][col + 1].brickNumber = third;
                    }
                }
            }
        }
        printMatrix(matrix);
    }








//a method to print the matrix
   private static void printMatrix(Cell[][] matrix) {
       for (Cell[] ints : matrix) {
           for (int col = 0; col < matrix[0].length; col++) {
               System.out.print(ints[col].brickNumber + " ");
           }
           System.out.println();
       }
   }
}