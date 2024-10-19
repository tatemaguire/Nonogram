import java.awt.Graphics;
/**
 * sets up a puzzlemanager with no numbers, just a grid with custom width and height
 */
public class BlankManager extends PuzzleManager
{
    public BlankManager(int w, int h)
    {
        grid = new Grid(w, h, Board.X_DIM, Board.Y_DIM);
    }
    
    public boolean checkGrid()
    {
        return false;
    }
    
    public boolean checkRow(int i)
    {
        return false;
    }
    
    public boolean checkCol(int i)
    {
        return false;
    }
    
    public void draw(Graphics g)
    {
        grid.draw(g);
    }
}