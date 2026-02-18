package com.michaelctchan.colourlines.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Random;

import com.michaelctchan.colourlines.ColourLines;
import com.michaelctchan.colourlines.Colours;
import com.michaelctchan.colourlines.Constants;
import com.michaelctchan.colourlines.Position;

public class PlayingField extends Model implements Constants, Serializable
{
    private static final long serialVersionUID = 1L;
    /**
     * Root Model in the MVC architecture; Maintains a model of the
     * playing field and scores.
     * @author Michael Chan 
     */
    
    
    private Square[][] playingField;  
    private ColourLines mContext;
    private PlayingFieldData mPreviousState;
    private String status   = "";     // current ststus representation.
    private Position start  = null;   // start position for move.
    private Position finish = null;   // finish position for move.
    private LinkedList<Square> ballsToRemove = new LinkedList<Square>();
                                      // balls scheduled to be removed.
    private LinkedList<Square> visitedSquares = new LinkedList<Square>(); 
                                      // used with `isPathAvailable()'.
    private Panel mPanelModel;
    
    public PlayingField(ColourLines context, Panel panelModel) 
    {
    	// Initialise.
        mContext = context;
        mPanelModel = panelModel;
            
    	playingField = new Square[getWidth()][getHeight()];
    	for (int i = 0; i < getWidth();  i++) 
        for (int j = 0; j < getHeight(); j++) 
        {
            playingField[i][j] = new Square(Colours.NONCOLOUR);
        }

    	setNeighbours();
    	prepareNewGame();
    }
    
    public PlayingFieldData getState()
    {
        PlayingFieldData data = new PlayingFieldData();
        for (int i = 0; i < getWidth();  i++) 
        for (int j = 0; j < getHeight(); j++) 
        {
            data.pfSquareColours[j * getWidth() + i] = playingField[i][j].getColour();
        }
        return data;
    }
    
    public void setState(PlayingFieldData data)
    {
        for (int i = 0; i < getWidth();  i++) 
        for (int j = 0; j < getHeight(); j++) 
        {
            playingField[i][j].setInitialColour(data.pfSquareColours[j * getWidth() + i]);
        }        
        if (isFull())
        {
            prepareNewGame();
        }
    }
    
    public int getWidth()
    {
        return Constants.DIMENSION;
    }
    
    public int getHeight()
    {
        if (mContext.isPlayingFieldExtended())
            return Constants.DIMENSION + Constants.EXTRA_DIMENSION;
        else
            return Constants.DIMENSION;
    }    

    public void setStartPosition(Position p) 
    {
    	// Record `p' as start position of the current move.
    	// PRE: !isStartPositionOk()
    	start = p;
    	finish = null;
    }

    public boolean isStartPositionOk() 
    {
    	// Is the start position valid?
    	Position p = start;
    
    	if (p != null) 
    	{
    	    if 
    	    (
                p.getXCoordinate() >= 0 && p.getXCoordinate() < getWidth() 
                &&
                p.getYCoordinate() >= 0 && p.getYCoordinate() < getHeight()
            ) 
    	    {
    	        Square s = getSquareAt(p);
                return s.isOccupied() && s.getColour() != Colours.DUD; 
    	    }
    	}
    	return false;
    }

    public void setFinishPosition(Position p) 
    {
    	// Record `p' as finish position of the current move.
    	// PRE: !isFinishPositionOk()
    	finish = p;
    }

    public boolean isFinishPositionOk()
    {
    	// Is the finish position valid?
    	Position p = finish;
    
    	if (p != null) 
    	{
            if 
            (
                p.getXCoordinate() >= 0 && p.getXCoordinate() < getWidth() 
                &&
                p.getYCoordinate() >= 0 && p.getYCoordinate() < getHeight()
            ) 
            {   
                if (!getSquareAt(p).isOccupied()) 
                {
                    visitedSquares = new LinkedList<Square>();
                    return isPathAvailable(getSquareAt(start), getSquareAt(p));
                }
            }
    	}
    	return false;
    }

    //
    // Make the move, update field and score.
    // Return true if the move caused balls to be removed.
    //
    public boolean performMove() 
    {
        // Save the current state before the move.
        mPreviousState = getState();
    
        // Prepare the squares for the move.
        for (int i = 0; i < getWidth();  i++) 
        for (int j = 0; j < getHeight(); j++) 
        {
            getSquareAt(i, j).onPrepareMove();
        }
            
        // Do the move.
    	getSquareAt(finish).changeColour(getSquareAt(start).getColour());
    	getSquareAt(start).changeColour(Colours.NONCOLOUR);
    
    	start = null; finish = null;
    	return removeColourLines();
    }

    public void undoMove()
    {
        if (canUndo())
        {
            mPanelModel.undo();
            setState(mPreviousState);
        }
    }
    
    public boolean canUndo()
    {
        return mPreviousState != null && !getState().sameAs(mPreviousState) && !isFull();
    }
    
    public void abandonMove() 
    {
    	// Reset the start and finish positions.
    	start = null; finish = null;
    }
    
    public void setStatus(String stat) 
    {
    	// Set the status representation to `stat'. 
    	status = stat;
    }

    public String getStatus()
    {
    	// Representation of current game status.
    	if (isFull()) 
    	{
    	    setStatus(GAME_OVER_MESSAGE);
    	}
    	return status;
    }

    public void prepareNewGame() 
    {
        // Initialise for a new game.
        mPreviousState = null;
        resetBoard();
        mPanelModel.generateFutureColours();
        mPanelModel.resetScore();
        generateRandomBoard();
        abandonMove();
        setStatus(START_POSITION_NOT_OK_MESSAGE);
    }

    public boolean isFull() 
    {
        // Are there no blank spaces left?
        for (int i = 0; i < getWidth();  i++) 
        for (int j = 0; j < getHeight(); j++) 
        {
        	Square s = getSquareAt(new Position(i, j));
        	if (!s.isOccupied()) 
        	{
        	    return false;
        	}
        }
        return true;	
    }

    public Position getCoordinatesOf(Square s) 
    {
    	// What's the position of `s'?
        for (int i = 0; i < getWidth();  i++) 
        for (int j = 0; j < getHeight(); j++) 
        {
    		if (s == getSquareAt(new Position(i, j))) 
    		{
    		    return new Position(i, j);
    	    }
    	}
    	return new Position(-1, -1);
    }

    public boolean contains(Square s) 
    {
        // Is `s' on the field?
        for (int i = 0; i < getWidth();  i++) 
        for (int j = 0; j < getHeight(); j++) 
        {
            if (s == getSquareAt(new Position(i, j))) 
            {
                return true;
            }
        }
        return false;
    }

    public Square getSquareAt(int i, int j)
    {
        return playingField[i][j];
    }    
    
    public Square getSquareAt(Position p)
    {
    	// Square at `p'.
    	return getSquareAt(p.getXCoordinate(), p.getYCoordinate());
    }

    // private methods.

    private void setNeighbours() 
    {
    	// Set the neighbouring squares for each in `playingField'.
        for (int i = 0; i < getWidth();  i++) 
        for (int j = 0; j < getHeight(); j++) 
        {
            Square s = playingField[i][j];
            if (i > 0) 
            {
                s.setNeighbourAt(playingField[i - 1][j], NORTH);
                if (j > 0) 
                {
                    s.setNeighbourAt(playingField[i - 1][j - 1], NORTH_WEST);
                    s.setNeighbourAt(playingField[i][j - 1], WEST);
                }
                if (j < getHeight() - 1) 
                {
                    s.setNeighbourAt(playingField[i - 1][j + 1], NORTH_EAST);
                    s.setNeighbourAt(playingField[i][j + 1], EAST);
                }
            }
            if (i < getWidth() - 1)
            {
                s.setNeighbourAt (playingField[i + 1][j], SOUTH);
                if (j > 0) 
                {
                    s.setNeighbourAt(playingField[i + 1][j - 1], SOUTH_WEST);
                    s.setNeighbourAt(playingField[i][j - 1], WEST);
                }
                if (j < getHeight() - 1) 
                {
                    s.setNeighbourAt(playingField[i + 1][j + 1], SOUTH_EAST);
                    s.setNeighbourAt(playingField[i][j + 1], EAST);
                }
            }
    	}
    }

    private boolean removeColourLines() 
    {
        // Remove colour lines, add future colours and add score.
        int count = 1;
        int numRuns = 0;
        
        ballsToRemove = new LinkedList<Square>();
        while (numRuns <= 2)
        {
            // ran a second time?
            for (int i = 0; i < getWidth();  i++) 
            for (int j = 0; j < getHeight(); j++) 
        	{
        	    Square s = playingField[i][j];
        	    if (s.isOccupied()) 
        	    {
            		for (int k = EAST; k <= SOUTH_WEST; k++) 
            		{
            		    findColourLines(s, s.getColour(), k, count);
            		}
        	    }
            }
            if (ballsToRemove.size() == 0 && numRuns == 0) 
            {
                // if balls aren't to be removed then add future colours.
                mPanelModel.addFutureColoursToBoard(this); 
                mPanelModel.generateFutureColours();
            }
            numRuns++;
        }
        removeColours();
        mPanelModel.addScore(ballsToRemove.size());
        return ballsToRemove.size() > 0;
    }

    private boolean findColourLines(Square s, int colourOfLine, int direction, int count) 
    {
    	// Record colours to be removed.
    	boolean remove = false;
    	Square next = s.getNeighbourAt(direction);
    
    	if (count >= mContext.getNumBallsPerLine()) 
    	{
    	    // record balls to remove since line has sufficient length. 
    	    remove = true;
            ballsToRemove.add(s);
    	}
    	else 
    	{
    	    // remove only if sufficient lines found in future.
    	    if (next != null) 
    	    {
    	        if (next.getColour() == Colours.DUD || next.getColour() == Colours.NONCOLOUR)
    	            return false;
    	        else if (colourOfLine == Colours.WILDCARD)
    	        {
    	            //
    	            // Case where starting colour is a wildcard.
    	            //
    	            remove = findColourLines(next, next.getColour(), direction, count + 1);
                    if (remove)
                    {
                        ballsToRemove.add(s);
                    }
                }
    	        else if (next.getColour() == colourOfLine || next.getColour() == Colours.WILDCARD) 
        		{
        		    remove = findColourLines(next, colourOfLine, direction, count + 1);
        		    if (remove)
        		    {
        		        ballsToRemove.add(s);
        		    }	
        		}
    	    }
    	}
    	return remove;
    }
  
    private void removeColours() 
    {
        // Remove balls that were recorded to be removed.
        int i = 0;
        LinkedList<Square> uniqueBallsToRemove = new LinkedList<Square>();
        
        // remove duplicate balls.
        for (i = 0; i < ballsToRemove.size(); i++) 
        {
            Square s = ballsToRemove.get(i);
            if (!uniqueBallsToRemove.contains(s)) 
            {
                uniqueBallsToRemove.add(s);
            }
        }	
        // remove unique balls from `playingField'
        ballsToRemove = uniqueBallsToRemove;
        for (i = 0; i < ballsToRemove.size(); i++) 
        {
            Square s = ballsToRemove.get(i);
            s.changeColour(Colours.NONCOLOUR);
        }
    }


    private boolean isPathAvailable(Square s, Square f) 
    {
        // Can I move ball from `s' to `f'?
        boolean result = false;
        int direction = 0;
        visitedSquares.add(s);
        if (s == f) 
        {
            result = true;
        }
        else 
        {
            for (direction = NORTH; direction <= WEST; /* Nothing */)
            {
                Square next = s.getNeighbourAt(direction);
                if (next != null) 
                {
                    if (!next.isOccupied() && !visitedSquares.contains(next)) 
                    {
                        result = result || isPathAvailable(next, f);
                    }
                }
                direction += 2; // N, S, E, W only.
            }
        }	
        return result;
    }

    private void resetBoard() 
    {
        // Remove all the balls from the field.
        for (int i = 0; i < getWidth();  i++) 
        for (int j = 0; j < getHeight(); j++) 
        {
            playingField[i][j].setInitialColour(Colours.NONCOLOUR);
        }
    }


    private void generateRandomBoard() 
    {
        // Place random balls on the field.
        Random rand = new Random();
        resetBoard();
        for (int i = 0; i < NUM_START_BALLS && !isFull(); /* nothing */) 
        {	
            int r1 = rand.nextInt(getWidth());
            int r2 = rand.nextInt(getHeight());
            int col = Colours.generateRandomColour(mContext.isArcadeMode());
        
            Square s = getSquareAt(new Position(r1, r2));
            if (!s.isOccupied()) 
            {
                s.changeColour(col);
                i++;
            }
        }
    }
    
}
