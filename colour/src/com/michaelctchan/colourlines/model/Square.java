package com.michaelctchan.colourlines.model;

import java.io.Serializable;

import com.michaelctchan.colourlines.Colours;
import com.michaelctchan.colourlines.Constants;


public class Square implements Constants, Serializable 
{
    /**
     * Automatically added by Eclipse.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Cell on the playing field.
     * @author Michael Chan 
     */
    private int mColour;
    private int mPreviousColour;
    private boolean mChosen;
    private boolean mIsStart = false;
    private boolean mIsFinish = false;
    private Square[] neighbours = new Square[NUM_DIRECTIONS];

    
    public Square(int colour) 
    {
        setInitialColour(colour); // Initialise with colour, `col'.
    }

    public void setIsStartSquare(boolean isStart)
    {
        mIsStart = isStart;
    }
    
    public void setIsFinishSquare(boolean isFinish)
    {
        mIsFinish = isFinish;
    }
    
    public boolean isStartSquare()
    {
        return mIsStart;
    }
    
    public boolean isFinishSquare()
    {
        return mIsFinish;
    }
    
    public int getColour() 
    {
        // Colour of cell.
        return mColour;
    }

    ///
    /// Change the current state of the square.
    ///
    public void changeColour(int col) 
    {
        // Set the colour of the cell to `col'.
        mPreviousColour = mColour;
        mColour = col;
    }
    
    public int getPreviousColour() 
    {
        // Colour of cell.
        return mPreviousColour;
    }

    ///
    /// Sets the initial state of the square.
    ///
    public void setInitialColour(int col) 
    {
        mColour = col;
        mPreviousColour = mColour;
    }
    
    public void setChosen(boolean chosen)
    {
        mChosen = chosen;
    }
    
    public boolean isChosen()
    {
        return mChosen;
    }
    
    public boolean isOccupied() 
    {
        // Is the cell occupied?
        return mColour != Colours.NONCOLOUR;
    }

    //
    // Called before a move is performed.
    //
    public void onPrepareMove()
    {
        mPreviousColour = mColour;
    }
    
    public Square getNeighbourAt(int direction) 
    {
        // Neighbouring cell in direction, `direction'.
        return neighbours[direction];
    }

    public void setNeighbourAt(Square s, int direction) 
    {
        // Set the neighbour in direction, `direction' to `s'. 
        neighbours[direction] = s;
    }
}
