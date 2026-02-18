package com.michaelctchan.colourlines.view;


import android.app.AlertDialog;
import android.text.Html;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.michaelctchan.colourlines.ColourLines;
import com.michaelctchan.colourlines.Constants;
import com.michaelctchan.colourlines.R;
import com.michaelctchan.colourlines.controller.DialogOKButtonHandler;

public class HowToPlayDialog extends AlertDialog implements Constants 
{
   /**
     * Automatically added by Eclipse. 
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * `How To Play' dialog box.
     * @author Michael Chan
     */
    
    public HowToPlayDialog(ColourLines context, MainView view) 
    {
        // Set up `How To Play' dialog box.
        
        super(context);
        
        setIcon(R.drawable.icon);
        setTitle(Constants.HOW_TO_PLAY_DIALOG_TITLE);
        String contents = Constants.HOW_TO_PLAY_CONTENTS;
        
        ScrollView sv = new ScrollView(context);
        TextView t = new TextView(context);  
        t.setText(Html.fromHtml(contents));
        t.setClickable(false);
        t.setFocusable(false);
        t.setCursorVisible(false);
        t.setFocusableInTouchMode(false);
        t.setSelected(false);
        t.setLongClickable(false);
        t.setPadding(24, 24, 24, 24);
        t.setTextSize(Constants.DIALOG_TEXT_SIZE);
        t.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_INSET);
        sv.addView(t);
        setView(sv);
        
        setButton(BUTTON_POSITIVE, Constants.HOW_TO_PLAY_BUTTON_MESSAGE, new DialogOKButtonHandler(context));
        show();
    }
}
