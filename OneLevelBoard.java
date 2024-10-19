import javax.swing.*;
import java.awt.*;
/**
 * Creates a board that's used for one survival mode puzzle
 */
public class OneLevelBoard extends Board
{
    private Level level;
    private boolean lost;
    
    public OneLevelBoard(JFrame f, Level l)
    {
        super(f, true);
        level = l;
        lost = false;
        
        pm = new PuzzleManager(level.getName());
    }
    
    /**
     * returns true if puzzle solved
     */
    public boolean goSolved()
    {
        while (true)
        {
            try
            {
                Thread.sleep(15);
            }
            catch (InterruptedException ex) {}
            
            autoComplete();
            repaint();
            
            if (pm.checkGrid())
            {
                return true;
            }
            
            if (lost)
            {
                return false;
            }
        }
    }
    
    public void stop()
    {
        lost = true;
    }
    
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        if (pm.checkGrid())
        {
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 100));
            g.drawString("YOU DID IT!", 20, 100);
        }
    }
}