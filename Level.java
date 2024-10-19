import java.awt.*;
/**
 * contains data for and draws each level selection in survival mode
 */
public class Level
{
    private String name;
    private int levelNumber;// useless
    private boolean complete;
    private int xSize;
    private int ySize;
    private Color myColor;
    
    public Level(String n, int ln)
    {
        name = n;
        levelNumber = ln;
        complete = false;
        myColor = new Color((int)(Math.random() * 256), 200, 200);
        
        // gets x and y size of the puzzle
        int[][] tempGrid = FileManager.readPuzzle(name);
        xSize = tempGrid.length;
        ySize = tempGrid[0].length;
    }
    
    public String getName()
    {
        return name;
    }
    
    public int getLevelNumber()
    {
        return levelNumber;
    }
    
    public int getSize()
    {
        return xSize * ySize;
    }
    
    public boolean getComplete()
    {
        return complete;
    }
    
    public void setComplete(boolean c)
    {
        if (c)
        {
            complete = true;
        }
    }
    
    public void draw(Graphics g, int y)
    {
        g.setColor(myColor);
        g.fillRect(0, y, LevelSelect.X_DIM, LevelSelect.SELECT_H);
        
        // check box
        g.setColor(Board.TILE_COL);
        if (complete)
        {
            g.setColor(Board.FILL_COL);
        }
        else
        {
            g.setColor(Board.TILE_COL);
        }
        double margin = LevelSelect.SELECT_H / 4.0;
        g.fillRect((int)margin, y + (int)margin, (int)(2 * margin), (int)(2 * margin));
        
        // puzzle title
        g.setFont(new Font("Arial", Font.BOLD, LevelSelect.SELECT_H - (int)(1.7 * margin)));
        if (complete)
        {
            g.drawString(name, (int)(4 * margin), y + LevelSelect.SELECT_H - (int)(1.2 * margin));
        }
        else
        {
            g.drawString("???", (int)(4 * margin), y + LevelSelect.SELECT_H - (int)(1.2 * margin));
        }
        
        // grid size
        g.setFont(new Font("Courier New", Font.BOLD, LevelSelect.SELECT_H - (int)(1.7 * margin)));
        String dimensions = xSize + " x " + ySize;
        if (xSize < 10)
        {
            dimensions = " " + dimensions;
        }
        g.drawString(dimensions, LevelSelect.X_DIM - (int)(11 * margin), y + LevelSelect.SELECT_H - (int)(1.2 * margin));
    }
}