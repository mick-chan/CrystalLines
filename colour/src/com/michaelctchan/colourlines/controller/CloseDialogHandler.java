package com.michaelctchan.colourlines.controller;

import android.content.DialogInterface;
import com.michaelctchan.colourlines.view.MainView;

public class CloseDialogHandler implements DialogInterface.OnClickListener 
{
    /**
     * Controller (in MVC architecture) for handling the closure of a
     * window.
     * @author Michael Chan 
     */
    
    public CloseDialogHandler(MainView view) 
    {
        // Initialise.
        super();
    }    

    @Override
    public void onClick(DialogInterface dialog, int which)
    {
        dialog.dismiss();
    }
}
