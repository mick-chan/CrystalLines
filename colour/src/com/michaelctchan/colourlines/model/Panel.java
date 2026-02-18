package com.michaelctchan.colourlines.model;

import java.io.IOException;
import java.util.Random;

import com.michaelctchan.colourlines.ColourLines;
import com.michaelctchan.colourlines.Colours;
import com.michaelctchan.colourlines.Constants;
import com.michaelctchan.colourlines.Position;

public class Panel extends Model 
{
    private Square[] mSquares;
    private int mScore = 0;         // Current score.
    private int mBonus = 0;         // Last bonus scored.
    private HighScores mHighScores;  // record of high scores.
    private PanelData mPreviousState;
    private ColourLines mContext;
    
    public Panel(ColourLines context)
    {
        mContext = context;
        mSquares = new Square[Constants.NUM_NEXT_BALLS];
        for (int i = 0; i < mSquares.length; ++i)
            mSquares[i] = new Square(Colours.NONCOLOUR);
    
        try
        {
            mHighScores = HighScores.getInstance(context);
        }
        catch (IOException e)
        {
            mHighScores = null;
        }
    }
    
    public void setState(PanelData data)
    {
        mScore = data.pdCurrentScore;
        for (int i = 0; i < data.pdNextColours.length; ++i)
        {
            mSquares[i].changeColour(data.pdNextColours[i]);
        }
    }
    
    public PanelData getState()
    {
        PanelData data = new PanelData();
        for (int i = 0; i < data.pdNextColours.length; ++i)
        {
            data.pdNextColours[i] = mSquares[i].getColour();
        }
        data.pdCurrentScore = mScore;
        
        return data;
    }
    
    public void undo()
    {
        setState(mPreviousState);
    }
    
    @Override
    public Square getSquareAt(Position p)
    {
        return mSquares[p.getYCoordinate() * getWidth() + p.getXCoordinate()];
    }

    @Override
    public int getWidth()
    {
        return Constants.NUM_NEXT_BALLS;
    }

    @Override
    public int getHeight()
    {
        return 1;
    }
    
    synchronized public void generateFutureColours() 
    {
        // Save current state as before changing to next balls.
        mPreviousState = getState();
        
        // Replace current next balls with new random ones.
        for (int i = 0; i < mSquares.length; i++) 
        {
            mSquares[i].setInitialColour(Colours.NONCOLOUR);
            mSquares[i].changeColour(Colours.generateRandomColour(mContext.isArcadeMode()));
        }
    }

    synchronized public void addFutureColoursToBoard(PlayingField model)
    {
        // Place current next balls on the field.
        Random rand = new Random();
        for (int i = 0; i < mSquares.length && !model.isFull(); /* nothing */) 
        {   
            int r1 = rand.nextInt(model.getWidth());
            int r2 = rand.nextInt(model.getHeight());
            int col = mSquares[i].getColour();
        
            Square s = model.getSquareAt(new Position(r1, r2));
            if (!s.isOccupied()) 
            {
                s.changeColour(col);
                i++;
            }
        }
    }   
    
    ///
    /// Return null if unavailable.
    ///
    public HighScores getHighScores()
    {
        // `HighScores' object.
        return mHighScores;
    }
    
    public int getScore()
    {
        // Current score.
        return mScore;
    }
    
    public void resetScore()
    {
        mScore = 0;
        mBonus = 0;
    }

    ///
    /// Add score according to number of balls remmoved @param ballsRemoved.
    /// Return the bonus score gained.
    ///
    public void addScore(int ballsRemoved)
    {
        // Update score appropriate to last move.
        mBonus = 0;
        for (int i = 0; i < ballsRemoved; ++i)
        {
            if (i < mContext.getNumBallsPerLine())
                mScore += Constants.POINTS_PER_BALL;
            else
            {
                int incrementalScore = 
                    (i - mContext.getNumBallsPerLine() + 1) * Constants.POINTS_PER_BALL_BONUS;
                mScore += incrementalScore;
                mBonus += incrementalScore;
            }
        }
    }   
    
    public int getLastBonusScored()
    {
        return mBonus;
    }

}
