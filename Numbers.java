import java.util.ArrayList;
import java.awt.*;
/**
 * contains data for and draws numbers 
 */
public class Numbers
{
    private ArrayList<ArrayList<Integer>> columnNumbers;
    private ArrayList<ArrayList<Integer>> rowNumbers;
    private int numbersX, numbersY;
    private int tileSpread; //from grid
    private int numSpread; //spread between the numbers of a line
    private boolean longestIsRow;
    
    /**
     * receives numbers to be displayed
     * nx and ny are position of top left of grid, measured from top left of window
     */
    public Numbers(ArrayList<ArrayList<Integer>> cnum, ArrayList<ArrayList<Integer>> rnum, int nx, int ny, int tsp)
    {
        columnNumbers = cnum;
        rowNumbers = rnum;
        tileSpread = tsp;
        numbersX = nx + (int)(tsp / 10);
        numbersY = ny - (int)(tsp / 10);
        
        drawPrep();
    }
    
    /**
     * returns the amount of numbers in the longest list of numbers, column or row
     */
    public int longestNumberLine()
    {
        int longest = 1;
        for (int i = 0; i < columnNumbers.size(); i++)
        {
            if (columnNumbers.get(i).size() > longest)
            {
                longest = columnNumbers.get(i).size();
                longestIsRow = false;
            }
        }
        for (int i = 0; i < rowNumbers.size(); i++)
        {
            if (rowNumbers.get(i).size() > longest)
            {
                longest = rowNumbers.get(i).size();
                longestIsRow = true;
            }
        }
        return longest;
    }
    
    public void drawPrep()
    {
        int longest = longestNumberLine();
        if (longestIsRow)
        {
            numSpread = numbersX / longest;
        }
        else
        {
            numSpread = numbersY / longest;
        }
    }
    
    public void draw(Graphics g, PuzzleManager pm)
    {
        g.setColor(Board.NUM_COL);
        Color reducedAlpha = new Color(Board.NUM_COL.getRed(), Board.NUM_COL.getGreen(), Board.NUM_COL.getBlue(), 100);
        g.setFont(new Font("Arial", Font.BOLD, (int)(tileSpread*1.2)));
        
        //columns
        int x = numbersX;
        int y = numbersY;
        for (int i = 0; i < columnNumbers.size(); i++)
        {
            if (pm.checkCol(i))
            {
                g.setColor(reducedAlpha);
            }
            else
            {
                g.setColor(Board.NUM_COL);
            }
            
            //draw column of numbers
            if (columnNumbers.get(i).size() == 0)
            {
                g.drawString("0", x, y);
            }
            else
            {
                for (int j = columnNumbers.get(i).size()-1; j >= 0; j--)
                {
                    if (columnNumbers.get(i).get(j) > 9)
                    {
                        g.setFont(new Font("Arial", Font.BOLD, (int)(tileSpread*0.6)));
                    }
                    else
                    {
                        g.setFont(new Font("Arial", Font.BOLD, (int)(tileSpread*1.2)));
                    }
                    g.drawString(columnNumbers.get(i).get(j).toString(), x, y);
                    y -= numSpread;
                }
            }
            y = numbersY;
            x += tileSpread;
        }
        
        //rows
        x = numbersX;
        y = numbersY;
        for (int i = 0; i < rowNumbers.size(); i++)
        {
            if (pm.checkRow(i))
            {
                g.setColor(reducedAlpha);
            }
            else
            {
                g.setColor(Board.NUM_COL);
            }
            
            //draw row of numbers
            if (rowNumbers.get(i).size() == 0)//if nothing, draw "0"
            {
                g.drawString("0", x-numSpread, y+tileSpread);
            }
            else
            {
                for (int j = rowNumbers.get(i).size()-1; j >= 0; j--)
                {
                    if (rowNumbers.get(i).get(j) > 9) //if double digit, make smaller font
                    {
                        g.setFont(new Font("Arial", Font.BOLD, (int)(tileSpread*0.6)));
                    }
                    else
                    {
                        g.setFont(new Font("Arial", Font.BOLD, (int)(tileSpread*1.2)));
                    }
                    g.drawString(rowNumbers.get(i).get(j).toString(), x-numSpread, y+tileSpread);
                    x -= numSpread;
                }
            }
            x = numbersX;
            y += tileSpread;
        }
    }
}