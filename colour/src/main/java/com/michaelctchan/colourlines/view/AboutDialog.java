package com.michaelctchan.colourlines.view;

import android.app.AlertDialog;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.TextView;

import com.michaelctchan.colourlines.ColourLines;
import com.michaelctchan.colourlines.Constants;
import com.michaelctchan.colourlines.R;
import com.michaelctchan.colourlines.controller.DialogOKButtonHandler;


public class AboutDialog extends AlertDialog implements Constants
{
   /**
     * Automatically added by Eclipse.
     */
    private static final long serialVersionUID = 732058257219031372L;

    /**
     * `About' dialog box.
     * @author Michael Chan
     */
    
    public AboutDialog(ColourLines context, MainView view) 
    {
        // Set up dialog box. 
        super(context);      
               
        setIcon(R.drawable.icon);
        String aboutTitle = context.getString(R.string.app_name);
        setTitle(aboutTitle);
        String aboutContents = ABOUT_CONTENTS;
        if (!context.isFullVersion())
            aboutContents = aboutContents.concat(Constants.ABOUT_CONTENTS_FREE);
        aboutContents = String.format(aboutContents, context.getString(R.string.ver_name));
        
        ScrollView sv = new ScrollView(context);
        TextView t = new TextView(context);  
        //t.setAutoLinkMask(Linkify.ALL);
        t.setText(Html.fromHtml(aboutContents));
        t.setMovementMethod(LinkMovementMethod.getInstance());
        t.setClickable(false);
        t.setFocusable(false);
        t.setCursorVisible(false);
        t.setFocusableInTouchMode(false);
        t.setSelected(false);
        t.setLongClickable(false);
        t.setPadding(24, 24, 24, 24);
        t.setTextSize(Constants.DIALOG_TEXT_SIZE);
        t.setScroller(new Scroller(context));
        t.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_INSET);
        sv.addView(t);
        setView(sv);
        
        setButton(BUTTON_POSITIVE, Constants.ABOUT_BUTTON_MESSAGE, new DialogOKButtonHandler(context));
        show();
    }

}
