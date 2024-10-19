import java.awt.Graphics;
public class Cursor
{
    public static int x = 0; //(0, 0) at top left
    public static int y = 0;
    
    public static void moveTo(int h, int k)
    {
        if (h >= 0 && h < Grid.tiles.size())
        {
            x = h;
        }
        else if (h < 0)
        {
            x = Grid.tiles.size() - 1;
        }
        else
        {
            x = 0;
        }
        if (k >= 0 && k < Grid.tiles.get(0).size())
        {
            y = k;
        }
        else if (k < 0)
        {
            y = Grid.tiles.get(0).size() - 1;
        }
        else
        {
            y = 0;
        }
    }
    
    public static void move(int h, int k)
    {
        moveTo(x + h, y + k);
    }
    
    public static void moveToMousePos(int h, int k)
    {
        int mouseX = h - Grid.gridX;
        int mouseY = k - Grid.gridY;
        if (mouseX < 0)
        {
            x = -1;
        }
        else
        {
            x = mouseX / Grid.tileSpread;
        }
        if (mouseY < 0) 
        {
            y = -1;
        }
        else
        {
            y = mouseY / Grid.tileSpread;
        }
    }
}