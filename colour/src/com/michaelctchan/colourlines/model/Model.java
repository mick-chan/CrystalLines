package com.michaelctchan.colourlines.model;

import com.michaelctchan.colourlines.Position;


///
/// Represents a model containing an array of Squares.
///
public abstract class Model
{
    ///
    /// Square model at position @param p.
    ///
    abstract public Square getSquareAt(Position p);
    
    ///
    /// Number of Squares in the horizontal direction.
    ///    
    abstract public int getWidth();
    
    ///
    /// Number of Squares in the vertical direction.
    ///
    abstract public int getHeight();
}
