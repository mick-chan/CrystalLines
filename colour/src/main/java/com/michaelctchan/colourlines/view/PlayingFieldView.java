package com.michaelctchan.colourlines.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.view.Display;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.michaelctchan.colourlines.ColourLines;
import com.michaelctchan.colourlines.Constants;
import com.michaelctchan.colourlines.Position;
import com.michaelctchan.colourlines.R;
import com.michaelctchan.colourlines.controller.MovesHandler;
import com.michaelctchan.colourlines.model.Panel;
import com.michaelctchan.colourlines.model.PlayingField;
import com.michaelctchan.colourlines.model.Square;


public class PlayingFieldView implements Constants, Serializable 
{
    /**
     * Playing field, the interactive area of the game.
     * @author Michael Chan
     */
    
    /**
     * Automatically added by Eclipse.
     */
    private static final long serialVersionUID = 1L;    

    private TableLayout mField;
    private List<SquareView> mSquareViews;
    private PlayingField mPlayingFieldModel;

    private int mSquareDimensions;
    
    public PlayingFieldView(ColourLines context, PlayingField playingFieldModel, Panel panelModel, MainView view) 
    {
        // Set up the playing field.
        super();

        mPlayingFieldModel = playingFieldModel;
        Display display = ((WindowManager)context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        mSquareDimensions = Math.min(display.getWidth(), display.getHeight()) / (mPlayingFieldModel.getWidth() + 1);
        
        mField = (TableLayout)context.findViewById(R.id.playingfield);
        mField.setPadding(1, 1, 0, 0);
        MovesHandler movesHandler = new MovesHandler(context, mPlayingFieldModel, panelModel, view);
        mSquareViews = new ArrayList<SquareView>();
        for (int y = 0; y < mPlayingFieldModel.getHeight(); ++y)
        {
            TableRow row = new TableRow(context);
            for (int x = 0; x < mPlayingFieldModel.getWidth();  ++x)
            {
                Square squareModel = mPlayingFieldModel.getSquareAt(x, y);
                SquareView squareView = new SquareView(context, squareModel, mSquareDimensions);
                EmptySquareView squareBackground = new EmptySquareView(context, mSquareDimensions);
                squareView.setOnClickListener(movesHandler);                
                squareView.setPadding(0, 0, 1, 1);
                squareBackground.setPadding(0, 0, 1, 1);

                RelativeLayout relativeView = new RelativeLayout(context);
                relativeView.addView(squareBackground);
                relativeView.addView(squareView);
                
                mSquareViews.add(squareView);
                row.addView(relativeView);
            }
            mField.addView(row);
        }
    }    


    ///
    /// The absolute dimensions in pixels of a square.
    ///
    public int getSquareDimensions()
    {
        return mSquareDimensions;
    }
    
    public SquareView getSquareAt(Position p)
    {
        return mSquareViews.get(p.getYCoordinate() * mPlayingFieldModel.getWidth() + p.getXCoordinate());
    }
    
    public void animateMove()
    {
        for(Iterator<SquareView> i = mSquareViews.iterator(); i.hasNext();)
            i.next().animateSquare();
    }
    
    synchronized public void update()
    {
        for(Iterator<SquareView> i = mSquareViews.iterator(); i.hasNext();)
            i.next().update();
    }
}
