package com.michaelctchan.colourlines.view;

import android.widget.ImageView;

import com.michaelctchan.colourlines.ColourLines;
import com.michaelctchan.colourlines.Colours;
import com.michaelctchan.colourlines.Constants;



public class EmptySquareView extends ImageView implements Constants
{
    public EmptySquareView(ColourLines context, int dimensions)
    {
        super(context);

        setMinimumWidth(dimensions);
        setMinimumHeight(dimensions);
        setMaxWidth(dimensions);
        setMaxHeight(dimensions);
        setAdjustViewBounds(true);
        setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        setImageResource(Colours.getColours(context.getCrystalTheme())[Colours.NONCOLOUR]);
        setPadding(0, 0, 1, 1);
    }
}
