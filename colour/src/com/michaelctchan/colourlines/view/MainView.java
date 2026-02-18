package com.michaelctchan.colourlines.view;

import java.io.Serializable;

import com.michaelctchan.colourlines.ColourLines;
import com.michaelctchan.colourlines.Constants;
import com.michaelctchan.colourlines.model.Panel;
import com.michaelctchan.colourlines.model.PlayingField;

public class MainView implements Constants, Serializable
{
   /**
     * Automatically added by Eclipse.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Root View in the MVC architecture; Main window.
     * @author Michael Chan
     */

    private PlayingFieldView mPlayingFieldView;
    private PanelView mPanelView;

    public MainView(ColourLines context, PlayingField playingFieldModel, Panel panelModel) 
    {                 
        // setup the playing field.
        mPlayingFieldView = new PlayingFieldView(context, playingFieldModel, panelModel, this);
        mPanelView = new PanelView(context, panelModel, mPlayingFieldView);
    }
    
    public void animatePanel()
    {
        mPanelView.animateMove();
    }
    
    public void animatePlayingField()
    {
        mPlayingFieldView.animateMove();
    }
        
    public void update() 
    {
        mPlayingFieldView.update();
        mPanelView.update();
    }
    
}
