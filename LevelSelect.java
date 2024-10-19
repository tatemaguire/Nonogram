import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
/**
 * creates the level select window on the left
 * levels are sorted by grid size (width * height)
 */
public class LevelSelect extends JPanel implements MouseWheelListener, MouseListener, WindowListener
{
    public Level[] levels;
    public int currentLevelIndex;
    public int verticalDisplacement;
    public double scroll;
    public static boolean inLevel;// olb is go()ing, frame can still be open if inLevel = false
    public static OneLevelBoard olb;
    public static JFrame frame;
    
    public static final int X_DIM = 380;
    public static final int Y_DIM = 1000;
    public static final int SELECT_H = 40;// height of each level's selection
    
    public LevelSelect()
    {
        this.setLayout(null);
        this.setPreferredSize(new Dimension(X_DIM, Y_DIM));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addMouseWheelListener(this);
        this.addMouseListener(this);
        
        frame = new JFrame("Nonogram");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocation(LevelSelect.X_DIM, 0);
        
        levels = sortBySize(getAllLevels());
        verticalDisplacement = 0;
        scroll = 0;
        inLevel = false;
    }
    
    public void go()
    {
        while (true)
        {
            try
            {
                Thread.sleep(15);
            }
            catch (InterruptedException ex) {}
            
            if (inLevel)
            {
                levels[currentLevelIndex].setComplete(olb.goSolved());
                inLevel = false;
            }
            
            repaint();
        }
    }
    
    /**
     * creates array of levels, removes default
     */
    public Level[] getAllLevels()
    {
        String[] levelNames = Board.getAllPuzzleNames();
        
        Level[] tempLevels = new Level[levelNames.length - 1];
        int levNum = 1;
        
        boolean passedDefault = false;
        for (int i = 0; i < levelNames.length; i++)
        {
            if (levelNames[i].equals("Default"))
            {
                passedDefault = true;
            }
            else
            {
                if (!passedDefault)
                {
                    tempLevels[i] = new Level(levelNames[i], levNum++);
                }
                else
                {
                    tempLevels[i - 1] = new Level(levelNames[i], levNum++);
                }
            }
        }
        
        return tempLevels;
    }
    
    public void runLevel(Level l)
    {
        if (!inLevel)
        {
            olb = new OneLevelBoard(frame, l);
            frame.add(olb);
            frame.setJMenuBar(olb.menuBar);
            frame.pack();
            frame.addWindowListener(this);
            frame.setVisible(true);
            inLevel = true;
        }
    }
    
    public Level[] sortBySize(Level[] unsorted)
    {
        if (unsorted.length == 1)
        {
            return unsorted;
        }
        Level[] sorted = new Level[unsorted.length];
        Level[] a = new Level[unsorted.length / 2];// sometimes shorter than b by 1
        Level[] b = new Level[unsorted.length - unsorted.length / 2];// sometimes longer than a by 1
        
        for (int i = 0; i < unsorted.length; i++)
        {
            if (i < a.length)
            {
                a[i] = unsorted[i];
            }
            else
            {
                b[i - a.length] = unsorted[i];
            }
        }
        
        a = sortBySize(a);
        b = sortBySize(b);
        int ai = 0, bi = 0;
        
        for (int i = 0; i < sorted.length; i++)
        {
            if (ai >= a.length)
            {
                sorted[i] = b[bi];
                bi++;
            }
            else if (bi >= b.length)
            {
                sorted[i] = a[ai];
                ai++;
            }
            else
            {
                if (a[ai].getSize() < b[bi].getSize())
                {
                    sorted[i] = a[ai];
                    ai++;
                }
                else
                {
                    sorted[i] = b[bi];
                    bi++;
                }
            }
        }
        
        return sorted;
    }
    
    public void mouseWheelMoved(MouseWheelEvent e)
    {
        if (!inLevel)
        {
            scroll -= 5 * e.getPreciseWheelRotation();
            verticalDisplacement = (int)scroll;
        }
    }
    
    public void mouseExited(MouseEvent e) {}
    
    public void mouseEntered(MouseEvent e) {}
    
    public void mouseReleased(MouseEvent e) {}
    
    public void mousePressed(MouseEvent e)
    {
        if (e.getY() - verticalDisplacement < 0 || e.getY() - verticalDisplacement > levels.length * SELECT_H)
        {
            return;
        }
        currentLevelIndex = (e.getY() - verticalDisplacement) / SELECT_H;
        if (e.getButton() == MouseEvent.BUTTON1)
        {
            runLevel(levels[currentLevelIndex]);
        }
    }
    
    public void mouseClicked(MouseEvent e) {}
    
    public void windowDeactivated(WindowEvent e) {}
    
    public void windowActivated(WindowEvent e) {}
    
    public void windowDeiconified(WindowEvent e) {}
    
    public void windowIconified(WindowEvent e) {}
    
    public void windowClosed(WindowEvent e)
    {
        olb.stop();
    }
    
    public void windowClosing(WindowEvent e) {}
    
    public void windowOpened(WindowEvent e) {}
    
    public void windowOpening(WindowEvent e) {}
    
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        
        for (int i = 0; i < levels.length; i++)
        {
            if (verticalDisplacement + (i * SELECT_H) >= -SELECT_H && verticalDisplacement + (i * SELECT_H) <= Y_DIM)
            {
                levels[i].draw(g, verticalDisplacement + (i * SELECT_H));
            }
        }
    }
}