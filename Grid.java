import java.awt.*;
import java.util.ArrayList;
/**
 * contains 2d arraylist of tiles
 * draws tiles on board
 * 
 * weirdly static except for drawing and construction
 * the data and its modification is static
 */
public class Grid
{
    public static ArrayList<ArrayList<Integer>> tiles;//technically an array list of columns
    public static int gridX, gridY; //location of top left of grid
    public static int tileSpread;
    private static int tileSize;
    
    public static double tileProportion = 0.93; //what percent of the tileSpread is the tileSize? (tileSize = this*tileSpread)
    
    /**
     * 0 is empty
     * 1 is full
     * 2 is blocked
     * 
     * w and h are grid size
     * gx and gy are pixels from bottom right
     */
    public Grid(int w, int h, int gx, int gy)
    {
        if (w < 0 || h < 0)
        { System.out.println("Grid constructed with negative size"); }
        
        tiles = new ArrayList<ArrayList<Integer>>();
        //fills all spots with 0
        for (int i = 0; i < w; i++)
        {
            tiles.add(i, new ArrayList<Integer>());
            for (int j = 0; j < h; j++)
            {
                tiles.get(i).add(j, 0);
            }
        }
        
        gridX = Board.X_DIM - gx;
        gridY = Board.Y_DIM - gy;
        
        drawPrep();
    }
    
    public static void clearAll()
    {
        for (int i = 0; i < tiles.size(); i++)
        {
            for (int j = 0; j < tiles.get(0).size(); j++)
            {
                tiles.get(i).set(j, 0);
            }
        }
    }
    
    public static void clear()
    {
        if (isValid(Cursor.x, Cursor.y))
        {
            tiles.get(Cursor.x).set(Cursor.y, 0);
        }
    }
    
    public static void fill()
    {
        if (isValid(Cursor.x, Cursor.y))
        {
            tiles.get(Cursor.x).set(Cursor.y, 1);
        }
    }
    
    public static void block()
    {
        if (isValid(Cursor.x, Cursor.y))
        {
            tiles.get(Cursor.x).set(Cursor.y, 2);
        }
    }
    
    //fills empty squares with x's
    public static void completeRow(int r)
    {
        for (int c = 0; c < tiles.size(); c++)
        {
            if (tiles.get(c).get(r) == 0)
            {
                tiles.get(c).set(r, 2);
            }
        }
    }
    
    public static void completeCol(int c)
    {
        for (int r = 0; r < tiles.get(0).size();  r++)
        {
            if (tiles.get(c).get(r) == 0)
            {
                tiles.get(c).set(r, 2);
            }
        }
    }
    
    public static boolean isValid(int x, int y)
    {
        return y >= 0 && y <= tiles.get(0).size()-1 && x >= 0 && x <= tiles.size()-1;
    }
    
    public static void drawPrep()
    {
        float gridRatio = (float) (Board.X_DIM - gridX) / (Board.Y_DIM - gridY);
        float tilesRatio = (float) tiles.size() / tiles.get(0).size();
        
        if (tilesRatio > gridRatio) //more width in the tiles than in the space allocated for them
        {
            tileSpread = (int) ((Board.X_DIM - gridX) / tiles.size());
        }
        else
        {
            tileSpread = (int) ((Board.Y_DIM - gridY) / tiles.get(0).size());
        }
        tileSize = (int) (tileSpread * tileProportion);
    }
    
    public void draw(Graphics g)
    {
        //draw each tile
        for (int i = 0; i < tiles.size(); i++)
        {
            for (int j = 0; j < tiles.get(0).size(); j++)
            {
                if (tiles.get(i).get(j) == 0)
                {
                    g.setColor(Board.TILE_COL);
                }
                else if (tiles.get(i).get(j) == 1)
                {
                    g.setColor(Board.FILL_COL);
                }
                else if (tiles.get(i).get(j) == 2)
                {
                    g.setColor(Board.BLOCK_COL);
                }
                g.fillRect(i * tileSpread + gridX + ((tileSpread - tileSize) / 2), j * tileSpread + gridY + ((tileSpread - tileSize) / 2), tileSize, tileSize);
                
                //draw line every five tiles
                if (j % 5 == 0 && j != 0) //when j is multiple of 5 (6th index, 11th, 16th, etc.)
                {
                    g.setColor(Board.NUM_COL);
                    g.fillRect(gridX, gridY + (j-1) * tileSpread + ((tileSpread - tileSize) / 2) + tileSize, tiles.size() * tileSpread, tileSpread - tileSize);
                }
            }
            if (i % 5 == 0 && i != 0) //when j is multiple of 5 (6th index, 11th, 16th, etc.)
            {
                g.setColor(Board.NUM_COL);
                g.fillRect(gridX + (i-1) * tileSpread + ((tileSpread - tileSize) / 2) + tileSize, gridY, tileSpread - tileSize, tiles.get(0).size() * tileSpread);
            }
        }
        
        //draw cursor
        g.setColor(Board.CURSOR_COL);
        if (isValid(Cursor.x, Cursor.y))
        {
            g.fillRect(Cursor.x * tileSpread + gridX + ((tileSpread - tileSize) / 2), Cursor.y * 
                tileSpread + gridY + ((tileSpread - tileSize) / 2), tileSize, tileSize);
        }
    }
}