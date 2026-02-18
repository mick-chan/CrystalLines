package com.michaelctchan.colourlines;

import java.io.Serializable;

public class ColoursData implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    public int cdMahjongColours[] = new int[Colours.NUM_TOTAL_COLOURS];
    public int cdMahjongColoursSelected[] = new int[Colours.NUM_TOTAL_COLOURS];

    public int cdPoolColours[] = new int[Colours.NUM_TOTAL_COLOURS];
    public int cdPoolColoursSelected[] = new int[Colours.NUM_TOTAL_COLOURS];

    public int cdChessColours[] = new int[Colours.NUM_TOTAL_COLOURS];
    public int cdChessColoursSelected[] = new int[Colours.NUM_TOTAL_COLOURS];
}
