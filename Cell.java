package ivan;
public class Cell {

//we will create a class that has two properties:
//1) the actual brick number
//2) a boolean to check if the index is checked for a vertical or horzontal brick.
//When true - we don`t check this index when we receive the next number/brick.
    protected int brickNumber;
    protected boolean check = false;


    public Cell (int brickNumber) {
        this.brickNumber = brickNumber;
    }






}
