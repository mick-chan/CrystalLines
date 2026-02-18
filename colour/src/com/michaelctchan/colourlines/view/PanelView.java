package com.michaelctchan.colourlines.view;

import java.io.Serializable;

import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.michaelctchan.colourlines.ColourLines;
import com.michaelctchan.colourlines.Constants;
import com.michaelctchan.colourlines.Position;
import com.michaelctchan.colourlines.R;
import com.michaelctchan.colourlines.model.Panel;
import com.michaelctchan.colourlines.model.Square;

public class PanelView implements Constants, Serializable 
{
    /**
     * Automatically added by Eclipse.
     */
    private static final long serialVersionUID = 1L;    

   /**
     * Top panel, providing information about the game.
     * @author Michael Chan
    */

    private ColourLines mContext;
    private Panel mPanelModel;
    private TableLayout mNextColours;
    private EditText mScoreValue;

    public PanelView(ColourLines context, Panel model, PlayingFieldView view) 
    {
        // Set up the panel.
        super();
        mPanelModel = model;
        mContext = context;
        int squareDimensions = view.getSquareDimensions();
        
        // Initialise.    
        mNextColours = (TableLayout)context.findViewById(R.id.nextcolours);       
        for (int y = 0; y < model.getHeight(); ++y)
        {
            TableRow row = new TableRow(context);
            for (int x = 0; x < model.getWidth(); ++x)
            {
                Square squareModel = model.getSquareAt(new Position(x, y));
                SquareView squareView = new SquareView(context, squareModel, squareDimensions);
                squareView.setEnabled(false);
                squareView.setClickable(false);
                squareView.setFocusable(false);
                squareView.setPadding(0, 0, 1, 1);
                row.addView(squareView);
            }
            mNextColours.addView(row);
        }

        mScoreValue = (EditText)mContext.findViewById(R.id.score_value);
        mScoreValue.setClickable(false);
        mScoreValue.setFocusable(false);
        mScoreValue.setCursorVisible(false);
        mScoreValue.setMinimumHeight(squareDimensions);
        
        LinearLayout panel = (LinearLayout)mContext.findViewById(R.id.panel);
        panel.setMinimumHeight((int)(squareDimensions * 1.5));
        
        update();
    }

    public void update() 
    {
        // Refresh display to the latest information.
        updateScore();
        updateNextBalls();       
    }
    
    // Private methods.

    private void updateNextBalls()
    {
        for (int r = 0; r < mNextColours.getChildCount(); ++r)
        {
            TableRow row = (TableRow)mNextColours.getChildAt(r);
            for (int c = 0; c < row.getChildCount(); ++c)
            {
                SquareView sv = (SquareView)row.getChildAt(c); 
                sv.update();
            }
        }
    }
	
    public void animateMove()
    {
        for (int r = 0; r < mNextColours.getChildCount(); ++r)
        {
            TableRow row = (TableRow)mNextColours.getChildAt(r);
            for (int c = 0; c < row.getChildCount(); ++c)
            {
                SquareView sv = (SquareView)row.getChildAt(c); 
                sv.animate();
            }
        }     
    }
    
    private void updateScore() 
    {
        // Acquire the current score.
        mScoreValue.setText(Integer.toString(mPanelModel.getScore()) + " ");
    }
    
}
