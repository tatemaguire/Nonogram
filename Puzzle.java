import java.io.*;
import java.util.ArrayList;
import java.awt.*;
/**
 * obtains file data, transforms into arrays of row numbers and column numbers
 * used to acces numbers of a puzzle's solution
 */
public class Puzzle
{
    //these arraylists describe the solution
    public ArrayList<ArrayList<Integer>> columns = new ArrayList<ArrayList<Integer>>();
    public ArrayList<ArrayList<Integer>> rows = new ArrayList<ArrayList<Integer>>();
    public ArrayList<ArrayList<Integer>> columnNumbers = new ArrayList<ArrayList<Integer>>();
    public ArrayList<ArrayList<Integer>> rowNumbers = new ArrayList<ArrayList<Integer>>();
    
    public Puzzle(String puzzleName)
    {
        int[][] tiles = FileManager.readPuzzle(puzzleName);
        
        //constructs 'columns' with lists of tile states
        for (int i = 0; i < tiles.length; i++) //for each column
        {
            ArrayList<Integer> column = new ArrayList<Integer>();
            for (int j = 0; j < tiles[0].length; j++) //for each row
            {
                column.add(tiles[i][j]);
            }
            columns.add(column);
        }
        
        //constructs 'rows' with lists of tile states
        for (int i = 0; i < tiles[0].length; i++) //for each row
        {
            ArrayList<Integer> row = new ArrayList<Integer>();
            for (int j = 0; j < tiles.length; j++) //for each column
            {
                row.add(tiles[j][i]);
            }
            rows.add(row);
        }
        
        //constructs 'columnNumbers' from 'columns'
        for (int i = 0; i < columns.size(); i++)
        {
            columnNumbers.add(tilesToNumbers(columns.get(i)));
        }
        
        //constructs 'rowNumbers' from 'rows'
        for (int i = 0; i < rows.size(); i++)
        {
            rowNumbers.add(tilesToNumbers(rows.get(i)));
        }
    }
    
    /**
     * takes list of tiles, transforms into list of numbers
     */
    public static ArrayList<Integer> tilesToNumbers(ArrayList<Integer> tiles)
    {
        int fillCount = 0;
        ArrayList<Integer> nums = new ArrayList<Integer>();
        for (int i = 0; i < tiles.size(); i++)
        {
            if (tiles.get(i) == 1)
            {
                fillCount++;
            }
            else if (fillCount != 0)
            {
                nums.add(fillCount);
                fillCount = 0;
            }
            
            if (i == tiles.size()-1 && fillCount != 0) //if at the end and still counting
            {
                nums.add(fillCount);
                fillCount = 0;
            }
        }
        return nums;
    }
}