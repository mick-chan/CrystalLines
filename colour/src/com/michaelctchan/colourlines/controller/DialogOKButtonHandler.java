
package com.michaelctchan.colourlines.controller;

import android.content.DialogInterface;

import com.michaelctchan.colourlines.ColourLines;

public class DialogOKButtonHandler implements DialogInterface.OnClickListener 
{
    /**
     * Controller (in MVC architecture) for closing the window on
     * pressing the 'OK' button on dialog boxes.
     * @author Michael Chan 
     */
    protected ColourLines mContext;
    
    public DialogOKButtonHandler(ColourLines context) 
    {
        // Initialise.
        super();
        mContext = context;
    }    

    @Override
    public void onClick(DialogInterface dialog, int which)
    {
        // Handle dialog "OK" presses.
        mContext.setDialogOpen(false);
        dialog.dismiss();
    }
}
