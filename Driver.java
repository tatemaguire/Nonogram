import javax.swing.*;
import java.awt.*;
/**
 * used to start the game in creative or survival
 */
public class Driver
{
    /**
     * Choose any level, create your own
     */
    public static void creativeMode()
    {
        // :3
        JFrame frame = new JFrame("Nonogram");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(LevelSelect.X_DIM, 0);
        Board b = new Board(frame, false);
        frame.add(b);
        frame.setJMenuBar(b.menuBar);
        frame.pack();
        frame.setVisible(true);
        b.go();
    }
    
    /**
     * Follow level progression
     */
    public static void survivalMode()
    {
        JFrame LSFrame = new JFrame("Level Select");
        LSFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        LSFrame.setLocation(0, 0);
        LevelSelect ls = new LevelSelect();
        LSFrame.add(ls);
        LSFrame.pack();
        LSFrame.setVisible(true);
        ls.go();
    }
    
    public static void main(String[] args)
    {
        if (args.length == 1 && args[0].equals("-c"))
        {
            creativeMode();
        }
        else if (args.length == 1 && args[0].equals("-s"))
        {
            survivalMode();
        }
        else
        {
            System.out.println("Please choose one of the following arguments:");
            System.out.println("\t-s\tSurvival Mode (Solve pre-made puzzles");
            System.out.println("\t-c\tCreative Mode (Create your own puzzle)");
            return;
        }
    }
}