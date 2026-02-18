package com.michaelctchan.colourlines.model;

import java.io.Serializable;

import com.michaelctchan.colourlines.Constants;


public class PlayingFieldData implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    public int pfSquareColours[] = new int[Constants.DIMENSION * (Constants.DIMENSION + Constants.EXTRA_DIMENSION)];
    public boolean sameAs(PlayingFieldData other)
    {
        if (other == null)
            return false;
        for (int i = 0; i < pfSquareColours.length; ++i)
        {
            if (pfSquareColours[i] != other.pfSquareColours[i])
                return false;
        }
        return true;
    }
}
