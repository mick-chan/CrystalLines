package com.michaelctchan.colourlines.model; 

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.LinkedList;

import android.app.Activity;
import android.content.Context;

import com.michaelctchan.colourlines.Constants;

public class HighScores implements Constants, Serializable
{
    private static final long serialVersionUID = 1L;
    /**
     * Manages high scores.
     * @author Michael Chan
     */
    
    // High scores table structure constants.
    public static final int NUM_HIGH_SCORE_ENTRIES = 10;
    public static final int NUM_COLS               =  2;
    public static final int NAME_COL               =  0;
    public static final int SCORE_COL              =  1;
    
    // Variables.
    private Activity mContext;
    private BufferedReader in;
    private PrintWriter out;
    private LinkedList<String[]> highScores = new LinkedList<String[]>();
    private static HighScores mInstance = null;
    
    static public HighScores getInstance(Activity context) throws IOException
    {
        if (mInstance == null)
        {
            mInstance = new HighScores(context);            
        }
        return mInstance;
    }
    
    private HighScores(Activity context) throws IOException 
    {        
        FileInputStream highScoresFileStream = null; 
        
        // Initialise.
        String[] entry = new String[NUM_COLS];
        int i = 0;
        mContext = context;

        try 
        {
            highScoresFileStream = context.openFileInput(Constants.HIGH_SCORES_FILE_NAME);
        }
        catch (FileNotFoundException e)
        {
            //
            // Create an empty file.
            //
            FileOutputStream f = context.openFileOutput(Constants.HIGH_SCORES_FILE_NAME, Context.MODE_PRIVATE);
            f.close();
        }
        finally
        {
            highScoresFileStream = context.openFileInput(Constants.HIGH_SCORES_FILE_NAME);
        }
        
        // prepare to read `highScoresFile' 
        try 
        {
            in = new BufferedReader(new InputStreamReader(highScoresFileStream));
        } 
        catch (Exception e) 
        {
            entry = new String[0];
        }
     
    	// read `highScoresFile'
        while (in.ready()) 
        {
        	entry = new String[NUM_COLS];
        	for (i = 0; i < NUM_COLS; i++) 
        	{
        	    if (in.ready()) 
        	    {
        	        entry[i] = in.readLine();
        	    }
        	}
        	highScores.add(entry);
        }
        in.close();
        highScoresFileStream.close();
   }
    
    public int getCount()
    {
        return highScores.size();
    }
    
    public int getLowest() 
    {
        // Lowest score recorded.
        
        int i = 0, t = 0, c = 0;;
        String[] current, tentative;
        if (highScores.size() < NUM_HIGH_SCORE_ENTRIES) 
        {
            // there's space available. 
            t = 0; 
        }
        else
        {
            // find lowest.	
            try 
            {
                tentative = (String[]) highScores.get(0);
                t = Integer.parseInt (tentative[SCORE_COL]);
                for (i = 0; i < highScores.size(); i++) 
                {
                    current = (String[]) highScores.get(i);
                    c = Integer.parseInt (current[SCORE_COL]);
                    if (c < t) 
                    {
                        t = c;
                    }
                }
            } 
            catch (Exception e) {}   
        }
        return t;
    }
    
    public void addEntry(String name, int score) throws IOException 
    {
        // Add high score entry of `name' and `score'.
        // PRE: score > getLowest()
        
        int t = 0, n = 0, index = 0;
        String[] entry, next, tentative;
        int i = 0;
        
        if (highScores.size() == NUM_HIGH_SCORE_ENTRIES) 
        {
            // remove the smallest entry.
            try 
            {
            	tentative = (String[])highScores.get(i);
            	t = Integer.parseInt (tentative[SCORE_COL]);	
            	for (i = 0; i < highScores.size(); i++) 
            	{
            	    next = (String[])highScores.get(i);
            	    n = Integer.parseInt (next[SCORE_COL]);	
            	    if (n < t) 
            	    {
                		t = n;
                		index = i;
            	    }
            	}
            } 
            catch (Exception e) {}
            highScores.remove(index);
        }
        
        // add this entry
        entry = new String[NUM_COLS];
        entry[NAME_COL] = name;
        entry[SCORE_COL] = Integer.toString(score);
        highScores.add(entry);
        
        // sort and save the high scores
        sort();
        save();
    }
    
    // Iterator for high scores.
    private int cursor = -1;
    
    public void resetCursor() 
    {
        // reset the cursor.
        cursor = -1;
    }
    
    public void incrementCursor() 
    {
        // Increment cursor.
        cursor++;
    }
    
    public boolean isCursorValid() 
    {
        // Is cursor at a valid index? 
        boolean result = false;
        if (cursor >= 0 && cursor < NUM_HIGH_SCORE_ENTRIES && cursor < highScores.size()) 
        {
            result = true;
        }
        return result;
    }
    
    public String getName() 
    {
        // Name of entry at cursor.
        // PRE: isCursorValid()
        String[] entry = (String[]) highScores.get(cursor);
        return entry[NAME_COL];
    }
    
    public int getScore() 
    {
        // Score of entry at cursor.
        // PRE: isCursorValid()
        int score = -1;
        String[] entry = (String[]) highScores.get(cursor);
        try 
        {
            score = Integer.parseInt(entry[SCORE_COL]);
        }
        catch (Exception e) {}
        return score;
    }
    
    // Private methods
    
    private void save() throws IOException 
    {
        // Save high scores to `highScoresFile'.
     
        FileOutputStream highScoresFileStream = null;
        
        try
        {
            highScoresFileStream = mContext.openFileOutput(Constants.HIGH_SCORES_FILE_NAME, Context.MODE_PRIVATE);
        }
        catch (FileNotFoundException e)
        {
            return;
        }
        
        int i = 0, j = 0;
        String[] entry;
        
        out = new PrintWriter(new OutputStreamWriter(highScoresFileStream));
        for (i = 0; i < highScores.size(); i++) 
        {
            entry = (String[])highScores.get(i);
            for (j = 0; j < NUM_COLS; j++) 
            {
                out.println(entry[j]);
            }
        }
        out.flush();
        out.close();
        highScoresFileStream.close();
    }
    
    private void sort() 
    {
        // Sort entries in ascending order based on score value.
        
        String[] tentative, current;
        int t = 0, c = 0;
        int i = 0, j = 0;
        
        try 
        {
            for (i = 0; i < highScores.size(); i++) 
            {
            	current = (String[]) highScores.get(i);
            	c = Integer.parseInt(current[SCORE_COL]);
            	for (j = i + 1; j < highScores.size(); j++) 
            	{
            	    tentative = (String[]) highScores.get(j);
            	    t = Integer.parseInt(tentative[SCORE_COL]);
            	    if (c < t) 
            	    {
                		highScores.remove(i);
                		highScores.add(i, tentative);
                		highScores.remove(j);
                		highScores.add(j, current);
            	    }
            	}
            }
        } 
        catch (Exception e) {}
    }
}
