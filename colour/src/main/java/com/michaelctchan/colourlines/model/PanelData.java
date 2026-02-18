package com.michaelctchan.colourlines.model;

import java.io.Serializable;

import com.michaelctchan.colourlines.Constants;

public class PanelData implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    public int pdNextColours[] = new int[Constants.NUM_NEXT_BALLS];
    public int pdCurrentScore;
}
