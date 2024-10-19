import java.util.ArrayList;
/**
 * contains 50 instances of the history of the grid
 * methods to get grid and write grid
 */
public class Undo
{
    //each history of 2d grids
    public static ArrayList<int[][]> history = new ArrayList<int[][]>();
    
    public static int historyLength = 50;
    
    /**
     * stores the entire grid
     */
    public static void recordGrid(int[][] inputGrid)
    {
        history.add(0, inputGrid);
        if (history.size() > historyLength)
        {
            history.remove(historyLength);
        }
    }
    
    /**
     * returns the previous state
     */
    public static int[][] getPrevGrid()
    {
        try
        {
            if (history.size() > 1)
            {
                return history.remove(0);
            }
            else if (history.size() == 1)
            {
                return history.get(0);
            }
        }
        catch (ArrayIndexOutOfBoundsException|NullPointerException ex) {}
        return null;
    }
    
    public static void clearHistory()
    {
        history = new ArrayList<int[][]>();
    }
    
    public static int[][] listToArray2D(ArrayList<ArrayList<Integer>> inList)
    {
        int[][] outArray = new int[inList.size()][inList.get(0).size()];
        for (int c = 0; c < inList.size(); c++)
        {
            for (int r = 0; r < inList.get(0).size(); r++)
            {
                outArray[c][r] = inList.get(c).get(r);
            }
        }
        return outArray;
    }
    
    /**
     * r and c are different than what the grid's r and c are, in this method only
     */
    public static ArrayList<ArrayList<Integer>> arrayToList2D(int[][] inArray)
    {
        ArrayList<ArrayList<Integer>> outList = new ArrayList<ArrayList<Integer>>();
        for (int c = 0; c < inArray.length; c++)
        {
            outList.add(new ArrayList<Integer>());
            for (int r = 0; r < inArray[0].length; r++)
            {
                outList.get(c).add(inArray[c][r]);
            }
        }
        return outList;
    }
}