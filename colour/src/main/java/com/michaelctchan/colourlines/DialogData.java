package com.michaelctchan.colourlines;

import java.io.Serializable;

class DialogData implements Serializable
{
    private static final long serialVersionUID = 1L;

    protected boolean mIsDialogOpen;

    public DialogData(boolean open)
    {
        mIsDialogOpen = open;
    }

    public boolean isDialogOpen()
    {
        return mIsDialogOpen;
    }
}