import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
/**
 * reads and writes files for importing and exporting puzzles
 */
public class FileManager
{
    /**
     * Reads a puzzle file and returns 2D array
     */
    public static int[][] readPuzzle(String name)
    {
        File puzzle = new File(Board.puzzleDirectory + name + ".txt");
        Scanner scPuz, scLine;
        String line = "";
        int lineCount = 0;
        int[][] tiles = new int[1][1];
        try
        {
            scPuz = new Scanner(puzzle); //for counting
            while (scPuz.hasNext())
            {
                lineCount++;
                scPuz.nextLine();
            }
            
            scPuz = new Scanner(puzzle);
            line = scPuz.nextLine();
            tiles = new int[line.length()][lineCount];
            
            scPuz = new Scanner(puzzle);
            for (int i = 0; scPuz.hasNext(); i++) //y
            {
                scLine = new Scanner(scPuz.nextLine());
                scLine.useDelimiter("");
                for (int j = 0; scLine.hasNext(); j++)//x
                {
                    tiles[j][i] = scLine.nextInt();
                }
            }
        }
        catch (FileNotFoundException ex) {System.out.println("File Not Found");}
        
        return tiles;
    }
    
    public static void writePuzzle(ArrayList<ArrayList<Integer>> tiles, String name)
    {
        if (name == null || name.length() < 1)
        {
            return;
        }
        try
        {
            FileWriter writer = new FileWriter("/Users/tatemaguire/Documents/Nonograms/" + name + ".txt");
            
            for (int i = 0; i < tiles.get(0).size(); i++)//for each row
            {
                for (int j = 0; j < tiles.size(); j++)//for each column
                {
                    //write the i-th int from each column
                    if (tiles.get(j).get(i) == 1)//this if accounts for 2s in the tiles
                    {
                        writer.write("1");
                    }
                    else
                    {
                        writer.write("0");
                    }
                }
                writer.write("\n");//next line
            }
            
            writer.close();
        }
        catch (IOException ex)
        {System.out.println("file couldn't be created/written");}
    }
}