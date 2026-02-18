package com.michaelctchan.colourlines.controller;

import android.view.View;
import android.widget.Toast;

import com.michaelctchan.colourlines.R;
import com.michaelctchan.colourlines.ColourLines;
import com.michaelctchan.colourlines.Constants;
import com.michaelctchan.colourlines.model.HighScores;
import com.michaelctchan.colourlines.model.Panel;
import com.michaelctchan.colourlines.model.PlayingField;
import com.michaelctchan.colourlines.model.Square;
import com.michaelctchan.colourlines.view.EnterHighScoreDialog;
import com.michaelctchan.colourlines.view.MainView;
import com.michaelctchan.colourlines.view.SquareView;

public class MovesHandler implements View.OnClickListener, Constants 
{
    /**
     * Controller (in MVC architecture) responsible for responding to
     * the selection of cells on the playing field.
     * @author Michael Chan 
     */

    private ColourLines mContext;
    private PlayingField mPlayingFieldModel;
    private Panel mPanelModel;
    private MainView mView;
    private HighScores mHighScores;
    private Square mStart;
    
    public MovesHandler(ColourLines context, PlayingField playingFieldModel, Panel panelModel, MainView view) 
    {
        // Initialise.
        mContext = context;
        mPlayingFieldModel = playingFieldModel;
        mPanelModel = panelModel;
        mView = view;
        mHighScores = mPanelModel.getHighScores();
    }

    private void onSelected()
    {
        mContext.vibrateShort();
        mContext.playSoundSelected();
    }
    
    private void onScored()
    {
        mContext.playSoundLineCleared();
        mContext.vibrateLong();
        mContext.updateAd();
    }
    
    @Override
    synchronized public void onClick(View v)
    {
        if (mPlayingFieldModel.isFull())
            return;
        
        // Respond to cell selection.
        SquareView sv = (SquareView)v;
        Square s = sv.getModel();

        if (!mPlayingFieldModel.isStartPositionOk()) 
        {
            // Start position not set yet. Set it.
            mStart = s;
            mPlayingFieldModel.setStartPosition(mPlayingFieldModel.getCoordinatesOf(s));
            if (!mPlayingFieldModel.isStartPositionOk()) 
            {
            	mPlayingFieldModel.setStatus(START_POSITION_NOT_OK_MESSAGE);
            }
            else 
            {
                mStart.setChosen(true);
                onSelected();
            	mPlayingFieldModel.setStatus(START_POSITION_OK_MESSAGE);
            }
        }
        else 
        {
            // start position already set. Attempt setting finish position.
            mPlayingFieldModel.setFinishPosition(mPlayingFieldModel.getCoordinatesOf(s));
            if (mPlayingFieldModel.isFinishPositionOk()) 
            {                                
                // make the move (set start square state before animating).
                mStart.setIsStartSquare(true);
                boolean scored = mPlayingFieldModel.performMove();
                if (scored)
                    mView.animatePlayingField();
                mStart.setIsStartSquare(false);
                if (scored)
                {
                    onScored();
                    int bonusPoints = mPanelModel.getLastBonusScored();
                    if (bonusPoints > 0)
                    {
                        String msg = String.format(Constants.BONUS_POINTS_MESSAGE, bonusPoints);
                        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    mView.animatePlayingField();
                    mView.animatePanel();
                    onSelected();
                }                   
                mStart.setChosen(false);                
                mPlayingFieldModel.setStatus(START_POSITION_NOT_OK_MESSAGE);
                if (mPlayingFieldModel.isFull()) 
                {
                    //
                    // Player achieved a high score so enter his/her name to the high scores list.
                    //
                    if (mPanelModel.getScore() > mHighScores.getLowest()) 
                    {
                        new EnterHighScoreDialog(mContext, mPanelModel, mView);
            	    }
                    else
                    {
                        Toast.makeText(mContext, R.string.game_over_message, Toast.LENGTH_LONG).show();
                    }
            	}
            }
            else if (s.isOccupied()) 
            {
            	mPlayingFieldModel.setStartPosition(mPlayingFieldModel.getCoordinatesOf(s));
            	if (!mPlayingFieldModel.isStartPositionOk()) 
            	{
            	    mPlayingFieldModel.setStatus(START_POSITION_NOT_OK_MESSAGE);
            	}
            	else 
            	{
            	    onSelected();
                    mStart.setChosen(false);
                    s.setChosen(true);
                    mStart = s;
            	    mPlayingFieldModel.setStatus(START_POSITION_OK_MESSAGE);
            	}
            }
            else 
            {
            	mPlayingFieldModel.setStatus(FINISH_POSITION_NOT_OK_MESSAGE);
            	Toast.makeText(mContext, Constants.CANT_MOVE_MESSAGE, Toast.LENGTH_SHORT).show();
            }
    	}
        mView.update();
    }
    
}
