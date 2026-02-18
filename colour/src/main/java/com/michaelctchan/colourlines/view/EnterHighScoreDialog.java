package com.michaelctchan.colourlines.view;

import java.io.IOException;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import com.michaelctchan.colourlines.ColourLines;
import com.michaelctchan.colourlines.Constants;
import com.michaelctchan.colourlines.model.Panel;

public class EnterHighScoreDialog extends AlertDialog
{
    private EditText mName;
    private final Panel mModel;
    private ColourLines mContext;
     
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public Bundle onSaveInstanceState()
    {     
        Bundle result = super.onSaveInstanceState();
        return result;
    }

    ///
    /// Save the entered name and score to the high scores and 
    /// then show the high scores dialog.
    ///
    protected void onOKButtonClicked()
    {
        //
        // Save the high score name to high scores list.
        //
        dismiss();
        String name = getEnteredName();
        if (name == null || name.trim().length() <= 0) 
        {
            name = Constants.ENTER_HIGH_SCORE_NO_NAME;
        }
        try
        {
            mModel.getHighScores().addEntry(name, mModel.getScore());
        } 
        catch (IOException e) { }       
        mContext.showDialog(Constants.DIALOG_HIGH_SCORES_ID);
    }
    
    
    public EnterHighScoreDialog(ColourLines context, Panel model, MainView view) 
    {
        // Set up `High Scores' dialog box. 
        super(context);
        mModel = model;
        mContext = context;
        
        setTitle(Constants.ENTER_HIGH_SCORE_TITLE);  
        String message = String.format(Constants.ENTER_HIGH_SCORE_MESSAGE, model.getScore());
        setMessage(message);  
           
        // Set an EditText view to get user input   
        mName = new EditText(context);  
        mName.setLines(1);
        InputFilter[] FilterArray = new InputFilter[1];
        FilterArray[0] = new InputFilter.LengthFilter(Constants.ENTER_HIGH_SCORES_NAME_MAX_CHARS);
        mName.setFilters(FilterArray);
        mName.setOnKeyListener
        (
            new View.OnKeyListener()
            {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event)
                {
                    if (keyCode == KeyEvent.KEYCODE_ENTER)
                        onOKButtonClicked();
                    return true;
                }
            }
        );
        setView(mName, 0, 20, 0, 20);
        
        // Set up the OK button.
        setButton
        (
            "OK", 
            new DialogInterface.OnClickListener() 
            {  
                public void onClick(DialogInterface dialog, int whichButton) 
                {
                    onOKButtonClicked();
                }  
            }
        );  
        
        //
        // Play sound on achieving high score.
        //
        mContext.playSoundHighScore();
        
        show();
    }
    
    public String getEnteredName()
    {
        return mName.getText().toString();
    }
}
