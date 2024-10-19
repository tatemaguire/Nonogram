import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import javax.swing.JCheckBoxMenuItem;
/**
 * sets up the window, menu, and most key parts of the program
 */
public class Board extends JPanel implements MouseMotionListener, MouseListener, KeyListener, ActionListener
{
    public PuzzleManager pm;
    private JFrame frame;
    public JMenuBar menuBar;
    private JMenu fileMenu, editMenu, settingsMenu;
    private JMenuItem newItem, importItem, exportItem, undoItem, clearItem, backgroundColItem, tileSizeItem;
    private JCheckBoxMenuItem completeLineItem;
    
    private boolean filling = false;
    private boolean blocking = false;
    private boolean clearing = false;
    private boolean firstPress = true; //false after first pressed, true after released
    
    public static final int X_DIM = 950;
    public static final int Y_DIM = 950;
    public static final String puzzleDirectory = "Puzzles/";
    
    public static final String[] colorStrings = {"Lavender", "Purple", "Orange", "Blue", "Green", "Yellow", "Pink", "Red", "Magenta", "Cyan"};
    public static final Color[] colorColors = {new Color(175, 145, 210), new Color(100, 0, 150), Color.ORANGE, Color.BLUE, Color.GREEN, Color.YELLOW, Color.PINK, Color.RED, Color.MAGENTA, Color.CYAN};
    
    public static Color BACK_COL = colorColors[0];
    public static final Color NUM_COL = Color.BLACK;
    public static final Color TILE_COL = Color.WHITE;
    public static final Color FILL_COL = Color.GRAY;
    public static final Color BLOCK_COL = Color.RED;
    public static final Color CURSOR_COL = new Color(90, 170, 230, 100); //transparent light blue
    
    public Board(JFrame f, boolean oneLevel)
    {
        this.setLayout(null);
        this.setPreferredSize(new Dimension(X_DIM, Y_DIM)); //don't change
        this.setBackground(BACK_COL);
        this.addMouseMotionListener(this);
        this.addMouseListener(this);
        this.addKeyListener(this);
        this.setFocusable(true);
        frame = f;
        
        //make a menu bar
        menuBar = new JMenuBar();
        
        //making settingsMenu
        settingsMenu = new JMenu("Settings");
        
        completeLineItem = new JCheckBoxMenuItem("Auto-Complete Line", true);
        settingsMenu.add(completeLineItem);
        
        backgroundColItem = new JMenuItem("Change Background Color");
        backgroundColItem.addActionListener(this);
        settingsMenu.add(backgroundColItem);
        
        tileSizeItem = new JMenuItem("Change Tile Size");
        tileSizeItem.addActionListener(this);
        settingsMenu.add(tileSizeItem);
        
        //making fileMenu
        fileMenu = new JMenu("File");
        
        newItem = new JMenuItem("New");
        newItem.addActionListener(this);
        newItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.META_MASK));
        fileMenu.add(newItem);
        
        importItem = new JMenuItem("Import");
        importItem.addActionListener(this);
        importItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.META_MASK));
        fileMenu.add(importItem);
        
        exportItem = new JMenuItem("Export");
        exportItem.addActionListener(this);
        exportItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.META_MASK));
        fileMenu.add(exportItem);
        
        //making editMenu
        editMenu = new JMenu("Edit");
        
        undoItem = new JMenuItem("Undo");
        undoItem.addActionListener(this);
        undoItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.META_MASK));
        editMenu.add(undoItem);
        
        clearItem = new JMenuItem("Clear");
        clearItem.addActionListener(this);
        clearItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.META_MASK));
        editMenu.add(clearItem);
        
        menuBar.add(settingsMenu);
        if (!oneLevel)
        {
            menuBar.add(fileMenu);
        }
        menuBar.add(editMenu);
        //menuBar is added to frame in driver
        
        pm = new BlankManager(10, 10);
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
            
            autoComplete();
            repaint();
        }
    }
    
    public void autoComplete()
    {
        if (!(pm instanceof BlankManager) && completeLineItem.getState())
            {
                for (int c = 0; c < Grid.tiles.size(); c++)
                {
                    if (pm.checkCol(c))
                    {
                        Grid.completeCol(c);
                    }
                }
                for (int r = 0; r < Grid.tiles.get(0).size(); r++)
                {
                    if (pm.checkRow(r))
                    {
                        Grid.completeRow(r);
                    }
                }
            }
    }
    
    //action listener:
    public void actionPerformed(ActionEvent event)
    {
        String action = event.getActionCommand();//returns string of item
        
        if (action == "New")
        {
            try
            {
                int blankW = Integer.parseInt(JOptionPane.showInputDialog(frame, "Enter Grid Width"));
                if (blankW <= 0)
                {
                    JOptionPane.showMessageDialog(frame, "That's too small, enter an integer > 0");
                    return;
                }
                int blankH = Integer.parseInt(JOptionPane.showInputDialog(frame, "Enter Grid Height"));
                if (blankH <= 0)
                {
                    JOptionPane.showMessageDialog(frame, "That's too small, enter an integer > 0");
                    return;
                }
                pm = new BlankManager(blankW, blankH);
            }
            catch (NumberFormatException ex)
            {
                JOptionPane.showMessageDialog(frame, "Enter an integer > 0");
            }
        }
        
        if (action == "Import")
        {
            pm = new PuzzleManager((String)JOptionPane.showInputDialog(frame, "Choose Puzzle", "Import", JOptionPane.PLAIN_MESSAGE, null, getAllPuzzleNames(), "Default"));
        }
        
        if (action == "Export")
        {
            FileManager.writePuzzle(Grid.tiles, JOptionPane.showInputDialog(frame, "Enter File Name"));
        }
        
        if (action == "Undo")
        {
            Grid.tiles = Undo.arrayToList2D(Undo.getPrevGrid());
        }
        
        if (action == "Clear")
        {
            Grid.clearAll();
        }
        
        if (action == "Change Background Color")
        {
            String str = (String)JOptionPane.showInputDialog(frame, null, "Change Background Color", JOptionPane.QUESTION_MESSAGE, null, colorStrings, colorStrings[0]);
            for (int i = 0; i < colorStrings.length; i++)
            {
                if (colorStrings[i].equals(str))
                {
                    BACK_COL = colorColors[i];
                    i = colorStrings.length;
                }
            }
            this.setBackground(BACK_COL);
        }
        
        if (action == "Change Tile Size")
        {
            try
            {
                double newSize = Double.parseDouble(JOptionPane.showInputDialog(frame, "Enter a double between 0 and 1"));
                if (newSize <= 0 || newSize > 1)
                {
                    JOptionPane.showMessageDialog(frame, "Just enter a fucking double between 0 and 1 it's not that hard...");
                    return;
                }
                Grid.tileProportion = newSize;
                Grid.drawPrep();
            }
            catch (NumberFormatException ex)
            {
                JOptionPane.showMessageDialog(frame, "Just enter a fucking double between 0 and 1 it's not that hard...");
            }
        }
    }
    
    public static String[] getAllPuzzleNames()
    {
        String[] allNames = new File(puzzleDirectory).list();
        for (int i = 0; i < allNames.length; i++)
        {
            if (allNames[i].equals(".DS_Store"))
            {
                //remove .DS_Store from list:
                String[] tempNames = new String[allNames.length-1];
                boolean beforeDS = true;
                for (int j = 0; j < allNames.length; j++)
                {
                    if (j != i)
                    {
                        if (beforeDS)
                        {
                            tempNames[j] = allNames[j];
                        }
                        else
                        {
                            tempNames[j-1] = allNames[j];
                        }
                    }
                    else
                    {
                        beforeDS = false;
                    }
                }
                allNames = tempNames;
                
                i--;//ITS A FLRP!
            }
            else
            {
                allNames[i] = allNames[i].substring(0, allNames[i].length()-4);
            }
        }
        return allNames;
    }
    
    //mouse motion listener:
    public void mouseMoved(MouseEvent event) //not clicking, but moving
    {
        Cursor.moveToMousePos(event.getX(), event.getY());
    }
    
    public void mouseDragged(MouseEvent event) //held down and moving
    {
        Cursor.moveToMousePos(event.getX(), event.getY());
        
        if (event.getButton() == MouseEvent.BUTTON1)
        {
            filling = true;
            updateTile();
        }
        
        if (event.getButton() == MouseEvent.BUTTON3)
        {
            blocking = true;
            updateTile();
        }
    }
    
    //mouse listener:
    public void mousePressed(MouseEvent event)
    {
        // if cursor isn't on grid, don't go further
        if(Cursor.x < 0 || Cursor.y < 0)
        {
            return;
        }
        
        // left click
        if (event.getButton() == MouseEvent.BUTTON1)
        {
            Undo.recordGrid(Undo.listToArray2D(Grid.tiles));
            filling = true;
            if (Grid.tiles.get(Cursor.x).get(Cursor.y) == 1 && firstPress)
            {
                clearing = true;
            }
            firstPress = false;
            updateTile();
        }
        
        // right click
        if (event.getButton() == MouseEvent.BUTTON3)
        {
            Undo.recordGrid(Undo.listToArray2D(Grid.tiles));
            blocking = true;
            if (Grid.tiles.get(Cursor.x).get(Cursor.y) == 2 && firstPress)
            {
                clearing = true;
            }
            firstPress = false;
            updateTile();
        }
    }
    
    public void mouseReleased(MouseEvent event)
    {
        if (event.getButton() == MouseEvent.BUTTON1)
        {
            filling = false;
            clearing = false;
            firstPress = true; //resets for next first press
        }
        
        if (event.getButton() == MouseEvent.BUTTON3)
        {
            blocking = false;
            clearing = false;
            firstPress = true;
        }
    }
    
    public void mouseClicked(MouseEvent event) {}
    
    public void mouseEntered(MouseEvent event) {}
    
    public void mouseExited(MouseEvent event) {}
    
    //key listener:
    public void keyPressed(KeyEvent event)
    {
        if (event.getKeyCode() == KeyEvent.VK_RIGHT)
        {
            Cursor.move(1, 0);
            updateTile();
        }
        
        if (event.getKeyCode() == KeyEvent.VK_LEFT)
        {
            Cursor.move(-1, 0);
            updateTile();
        }
        
        if (event.getKeyCode() == KeyEvent.VK_DOWN)
        {
            Cursor.move(0, 1);
            updateTile();
        }
        
        if (event.getKeyCode() == KeyEvent.VK_UP)
        {
            Cursor.move(0, -1);
            updateTile();
        }
        
        if (event.getKeyCode() == KeyEvent.VK_Z && event.getKeyCode() == KeyEvent.VK_META)
        {
            Undo.recordGrid(Undo.listToArray2D(Grid.tiles));
            filling = true;
            if (Grid.tiles.get(Cursor.x).get(Cursor.y) == 1 && firstPress)
            {
                clearing = true;
            }
            firstPress = false;
            updateTile();
        }
        
        if (event.getKeyCode() == KeyEvent.VK_X)
        {
            Undo.recordGrid(Undo.listToArray2D(Grid.tiles));
            blocking = true;
            if (Grid.tiles.get(Cursor.x).get(Cursor.y) == 2 && firstPress)
            {
                clearing = true;
            }
            firstPress = false;
            updateTile();
        }
    }
    
    public void keyReleased(KeyEvent event)
    {
        // event.getModifiers() != KeyEvent.META_MASK
        if (event.getKeyCode() == KeyEvent.VK_Z && event.getKeyCode() == KeyEvent.VK_META)
        {
            filling = false;
            clearing = false;
            firstPress = true; //resets for next first press
        }
        
        if (event.getKeyCode() == KeyEvent.VK_X)
        {
            blocking = false;
            clearing = false;
            firstPress = true;
        }
    }
    
    public void updateTile() //updates tile to be what they keys tell it to
    {
        if (clearing == true)
        {
            Grid.clear();
        }
        else if (filling == true)
        {
            Grid.fill();
        }
        else if (blocking == true)
        {
            Grid.block();
        }
    }
    
    public void keyTyped(KeyEvent event) {} //not used
    
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        if (pm.checkGrid())
        {
            g.setColor(new Color(250, 250, 250, 100));//transparent grey
            g.fillRect(0, 0, X_DIM, Y_DIM);
        }
        pm.draw(g);
    }
}