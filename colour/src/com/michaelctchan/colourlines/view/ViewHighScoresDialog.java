package com.michaelctchan.colourlines.view;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.michaelctchan.colourlines.ColourLines;
import com.michaelctchan.colourlines.Constants;
import com.michaelctchan.colourlines.R;
import com.michaelctchan.colourlines.controller.DialogOKButtonHandler;
import com.michaelctchan.colourlines.model.HighScores;
import com.michaelctchan.colourlines.model.Panel;

public class ViewHighScoresDialog extends AlertDialog implements Constants 
{
   /**
     * `High Scores' dialog box.
     * @author Michael Chan
     */
    
    /**
     * Automatically added by Eclipse.
     */
    private static final long serialVersionUID = 1L;    
    
    private ColourLines mContext;
    private HighScores mHighScores;
    private View mContents;

    public ViewHighScoresDialog(ColourLines context, Panel model, MainView view) 
    {
        // Set up `High Scores' dialog box. 
        super(context);
        
        LayoutInflater factory = LayoutInflater.from(context);
        mContents = factory.inflate(R.layout.high_scores, null);
        if (mContents != null)
            this.setView(mContents, 0, 0, 0, 0);
        
        mContext = context;
        mHighScores = model.getHighScores();
        populateHighScoresTable(); 
        setIcon(R.drawable.icon);
        ImageView icon = (ImageView)mContents.findViewById(R.id.high_scores_image);
        icon.setImageResource(R.drawable.high_scores);
        setTitle(Constants.VIEW_HIGH_SCORES_TITLE);
        setButton
        (
            AlertDialog.BUTTON_NEUTRAL,
            Constants.DIALOG_CLOSE,
            new DialogOKButtonHandler(context)
        );  
        show();
    }

    // Private methods

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
        populateHighScoresTable();
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        populateHighScoresTable();
    }

    private TableRow createRow(String name, String score)
    {
        TableRow row = new TableRow(mContext);
        row.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
        
        TextView nameText = new TextView(mContext);
        TextView scoreText = new TextView(mContext);
        nameText.setTextSize(Constants.DIALOG_TEXT_SIZE);
        scoreText.setTextSize(Constants.DIALOG_TEXT_SIZE);        
        nameText.setText(name);
        scoreText.setText(score);
        scoreText.setGravity(Gravity.RIGHT);
     
        row.addView(nameText, 0);
        row.addView(scoreText, 1);
        
        return row;
    }
    
    
    private TableLayout populateHighScoresTable() 
    {   
        TableLayout table = (TableLayout)mContents.findViewById(R.id.high_scores_table);

        table.setColumnShrinkable(0, true);
        table.removeAllViews();
        
        if (mHighScores.getCount() == 0)
        {
            TextView emptyMessage = new TextView(mContext);
            emptyMessage.setTextSize(Constants.DIALOG_TEXT_SIZE);
            emptyMessage.setText(Constants.VIEW_HIGH_SCORES_NO_SCORES);
            table.addView(emptyMessage);
            return table;
        }       
        
        // Setup the column names
        TableRow header = 
            createRow(new String(VIEW_HIGH_SCORES_NAME_COLUMN), new String(VIEW_HIGH_SCORES_SCORE_COLUMN));
        table.addView(header);
        
        
        // Add a separator
        TextView spaceTop = new TextView(mContext);
        TextView spaceBottom = new TextView(mContext);
        spaceTop.setMaxHeight(5);
        spaceBottom.setMaxHeight(10);
        TextView separator = new TextView(mContext);
        separator.setBackgroundColor(separator.getCurrentTextColor());        
        separator.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, 1));
        table.addView(spaceTop);
        table.addView(separator);
        table.addView(spaceBottom);
        
        // Get high scores data
        mHighScores.resetCursor();
        mHighScores.incrementCursor();
        while (mHighScores.isCursorValid()) 
        {
            table.addView(createRow(mHighScores.getName(), new String(Integer.toString(mHighScores.getScore()))));
            mHighScores.incrementCursor();
        }
 
        return table;
    }
    
}
