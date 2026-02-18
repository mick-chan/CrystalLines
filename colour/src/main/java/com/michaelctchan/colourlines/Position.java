package com.michaelctchan.colourlines;

public class Position 
{
    /**
     * Represent coordinates on the field.
     * @author Michael Chan
     */
 
    private int xx;
    private int yy;

    public Position(int x, int y) 
    {
        // Initialise.
        xx = x;
        yy = y;
    }
    
    public int getXCoordinate() 
    {
        // x coordinate.
        return xx;
    }

    public int getYCoordinate() 
    {
        // y coordinate.
        return yy;
    } 
}
