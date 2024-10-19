import java.util.ArrayList;
import java.awt.*;
/**
 * sets up the grid and numbers
 * kinda the main controller
 */
public class PuzzleManager
{
    //these arraylists describe the input
    private ArrayList<ArrayList<Integer>> columns;
    private ArrayList<ArrayList<Integer>> rows;
    
    private Puzzle puz;
    public Grid grid;
    private Numbers numbers;
    
    public PuzzleManager()
    {
        getPuzzle("Default");
    }
    
    public PuzzleManager(String s)
    {
        getPuzzle(s);
    }
    
    private void getPuzzle(String s)
    {
        if (s == null)
        {
            s = "Default";
        }
        else if (s.equals(""))
        {
            s = "Default";
        }
        puz = new Puzzle(s);
        
        //grid size and num size should always add up to window dimension
        grid = new Grid(puz.columns.size(), puz.rows.size(), 650, 650);
        numbers = new Numbers(puz.columnNumbers, puz.rowNumbers, 300, 300, grid.tileSpread);
        
        updateRowsAndCols();
    }
    
    public void updateRowsAndCols()
    {
        columns = Grid.tiles;
        
        //make rows from columns
        rows = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i < columns.get(0).size(); i++)
        {
            ArrayList<Integer> row = new ArrayList<Integer>();
            for (int j = 0; j < columns.size(); j++)
            {
                row.add(columns.get(j).get(i));
            }
            rows.add(row);
        }
    }
    
    //check if grid numbers match puzzle numbers, called every frame
    public boolean checkGrid()
    {
        updateRowsAndCols();
        
        for (int i = 0; i < rows.size(); i++)//if any row doesn't match, return false
        {
            if (!checkRow(i))
            {
                return false;
            }
        }
        
        for (int i = 0; i < columns.size(); i++)//if any column doesn't match, return false
        {
            if (!checkCol(i))
            {
                return false;
            }
        }
        
        return true;
    }
    
    //checks if row or column matches puzzle row or column numbers
    public boolean checkRow(int i)
    {
        try
        {
            return Puzzle.tilesToNumbers(rows.get(i)).equals(puz.rowNumbers.get(i));
        }
        catch (IndexOutOfBoundsException ex)
        {
            return false;
        }
    }
    
    public boolean checkCol(int i)
    {
        try
        {
            return Puzzle.tilesToNumbers(columns.get(i)).equals(puz.columnNumbers.get(i));
        }
        catch (IndexOutOfBoundsException ex)
        {
            return false;
        }
    }
    
    public void draw(Graphics g)
    {
        grid.draw(g);
        numbers.draw(g, this);
    }
}