package com.michaelctchan.colourlines.view;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.michaelctchan.colourlines.ColourLines;
import com.michaelctchan.colourlines.Colours;
import com.michaelctchan.colourlines.Constants;
import com.michaelctchan.colourlines.R;
import com.michaelctchan.colourlines.model.PlayingField;



public class MenuBarView implements Constants 
{
   /**
     * Automatically added by Eclipse.
     */
    private static final long serialVersionUID = 1L;
      
    /**
     * Menu bar.
     * @author Michael Chan
     */

    private PlayingField mModel;
    private MainView mView;
    private ColourLines mContext;

    public MenuBarView(ColourLines context, Menu menu, PlayingField model, MainView view)
    {
        // Setup the menu bar.
        
        super();
        mModel = model;
        mView = view;
        mContext = context; 
        
        MenuInflater inflater = context.getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
    }

    public boolean onMenuItemSelected(int featureId, MenuItem item)
    {
        int id = item.getItemId();
        if (id == R.id.menu_new_game) {
            Colours.resetColours();
            mModel.prepareNewGame();
            mView.update();
            return true;
        } else if (id == R.id.menu_high_scores) {
            mContext.showDialog(Constants.DIALOG_HIGH_SCORES_ID);
            return true;
        } else if (id == R.id.menu_undo) {
            if (mModel.canUndo())
            {
                mModel.undoMove();
                mView.update();
            }
            return true;
        } else if (id == R.id.menu_how_to_play) {
            mContext.showDialog(Constants.DIALOG_HOW_TO_PLAY_ID);
            return true;
        } else if (id == R.id.menu_about) {
            mContext.showDialog(Constants.DIALOG_ABOUT_ID);
            return true;
        } else if (id == R.id.menu_settings) {
            Intent intent = new Intent(mContext, SettingsScreen.class);
            intent.putExtra(Constants.ACCELEROMETER_AVAILABLE, mContext.isAccelerometerAvailable());
            intent.putExtra(Constants.VIBRATOR_AVAILABLE, mContext.isVibratorAvailable());
            intent.putExtra(Constants.IS_FULL_VERSION, mContext.isFullVersion());
            mContext.startActivity(intent);
            return true;
        }
        return false;
    }
}
