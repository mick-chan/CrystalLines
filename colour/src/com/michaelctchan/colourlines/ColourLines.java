package com.michaelctchan.colourlines;

import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.res.Configuration;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.widget.Toast;

import com.admob.android.ads.AdManager;
import com.admob.android.ads.AdView;
import com.michaelctchan.colourlines.model.Panel;
import com.michaelctchan.colourlines.model.PanelData;
import com.michaelctchan.colourlines.model.PlayingField;
import com.michaelctchan.colourlines.model.PlayingFieldData;
import com.michaelctchan.colourlines.view.AboutDialog;
import com.michaelctchan.colourlines.view.HowToPlayDialog;
import com.michaelctchan.colourlines.view.MainView;
import com.michaelctchan.colourlines.view.MenuBarView;
import com.michaelctchan.colourlines.view.ViewHighScoresDialog;

public class ColourLines extends Activity implements OnSharedPreferenceChangeListener, SensorEventListener
{
    /** Called when the activity is first created. */

    private Panel mPanelModel;
    private PlayingField mPlayingFieldModel;
    private MainView mView;
    private MenuBarView mMenuBar;
    private boolean mIsDialogOpen = false;
    
    //
    // System service objects.
    //
    private Vibrator mVibrator;
    private SensorManager mSensorManager;
    private boolean mAccelSupported;
    //
    // Preference values.
    //
    private boolean mSoundOn;
    private boolean mHapticsOn;
    private boolean mAnimationOn;
    private int mNumBallsPerLine;
    private boolean mArcadeMode;
    private int mCrystalTheme = Colours.DEFAULT_THEME;
    private boolean mShakeOn;

    //
    // Sounds.
    //
    private SoundPool mSoundPool;
    private int mClick;
    private int mSwish;
    private int mHarp;
    private int mPop;
    
    //
    // Advertisement.
    //
    private boolean mIsFullVersion;
    private AdView mAd;
    
    
    private void saveState()
    {
        //
        // Serialise the model.
        //
        if (mPanelModel == null || mPlayingFieldModel == null || mView == null)
            return;
        try
        {
            ObjectOutput panelOut = new ObjectOutputStream(openFileOutput("panel.ser", Context.MODE_PRIVATE));
            ObjectOutput fieldOut = new ObjectOutputStream(openFileOutput("field.ser", Context.MODE_PRIVATE));
            ObjectOutput dialogStateOut = new ObjectOutputStream(openFileOutput("dialog.ser", Context.MODE_PRIVATE));
            ObjectOutput coloursOut = new ObjectOutputStream(openFileOutput("colours.ser", Context.MODE_PRIVATE));
            
            panelOut.writeObject(mPanelModel.getState());
            fieldOut.writeObject(mPlayingFieldModel.getState());
            dialogStateOut.writeObject(new DialogData(mIsDialogOpen));
            coloursOut.writeObject(Colours.getState());
            panelOut.close();
            fieldOut.close();
            dialogStateOut.close();
            coloursOut.close();
        }
        catch (Exception e) 
        {
            e.getCause();
        }
    }
    
    public boolean isPlayingFieldExtended()
    {
        return isFullVersion();
    }
    
    ///
    /// Notify activity whether a dialog is open.
    ///
    public void setDialogOpen(boolean open)
    {
        mIsDialogOpen = open;
        enableSensor(!open);
    }
    
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
        restoreState();
    }

    private void restoreState()
    {
        try
        {
            ObjectInputStream panelIn = new ObjectInputStream(openFileInput("panel.ser"));
            ObjectInputStream fieldIn = new ObjectInputStream(openFileInput("field.ser"));
            ObjectInputStream dialogDataIn = new ObjectInputStream(openFileInput("dialog.ser"));           
            ObjectInputStream coloursIn = new ObjectInputStream(openFileInput("colours.ser"));           
            PanelData panelData = (PanelData)panelIn.readObject();
            PlayingFieldData playingFieldData = (PlayingFieldData)fieldIn.readObject();
            DialogData ds = (DialogData)dialogDataIn.readObject();
            ColoursData coloursData = (ColoursData)coloursIn.readObject();
            mIsDialogOpen = ds.isDialogOpen();
            mPanelModel.setState(panelData);
            mPlayingFieldModel.setState(playingFieldData);
            Colours.setState(coloursData);
            mView.update();
        }
        catch (Exception e)
        { 
            e.getCause();
        }
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        //
        // Check version.
        //
        String appName = getString(R.string.app_name);
        String freeKey = getString(R.string.free_key);
        mIsFullVersion = !(appName.toLowerCase().contains(freeKey.toLowerCase()));
        
        if (mPanelModel == null || mPlayingFieldModel == null || mView == null)
        {        
            // Initialise application from scratch.
            Colours.resetColours();
            mPanelModel = new Panel(this);
            mPlayingFieldModel = new PlayingField(this, mPanelModel);
            mView = new MainView(this, mPlayingFieldModel, mPanelModel);
        }
        restoreState();  
        
        //
        // Read initial preferences and set up the listener for handling future preference changes.
        //
        SharedPreferences sharedPreferences  = PreferenceManager.getDefaultSharedPreferences(this);
        onSharedPreferenceChanged(sharedPreferences, null);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
        
        //
        // Set up sounds.
        //
        mSoundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);//MediaPlayer.create(this, R.raw.button_click);
        mClick = mSoundPool.load(this, R.raw.button_click, 1);
        mSwish = mSoundPool.load(this, R.raw.cartoon_comical_swoosh_or_swipe_sound_version_1_, 1);        
        mHarp = mSoundPool.load(this, R.raw.magic_bell_2, 1);
        mPop = mSoundPool.load(this, R.raw.remove_large_suction_cup_from_floor, 1);
        
        //
        // Set up the haptics system.
        //
        mVibrator = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
        
        //
        // Set up the motion sensor.
        //
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        
        //
        // Set up Admob.
        //
        AdManager.setTestDevices(new String[] { AdManager.TEST_EMULATOR, "1000d2bc4743" });
        mAd = (AdView)findViewById(R.id.ad);
        if (!mIsFullVersion)
        {
            updateAd();
        }
        else
        {
            mAd.setVisibility(View.INVISIBLE);
        }
    }
    
    public int getCrystalTheme()
    {
        return mCrystalTheme;
    }
    
    public boolean isArcadeMode()
    {
        return mArcadeMode;
    }
    
    public boolean isFullVersion()
    {
        return mIsFullVersion;
    }
   
    public void updateAd()
    {
        if (!mIsFullVersion)
        {
            // Grab new ad and fade the ad in over 4/10 of a second.
            mAd.setVisibility(View.VISIBLE);
            mAd.requestFreshAd();
            AlphaAnimation animation = new AlphaAnimation( 0.0f, 1.0f );
            animation.setDuration(600);
            animation.setFillAfter(true);
            animation.setInterpolator(new AccelerateInterpolator());
            mAd.startAnimation(animation);
        }
    }
   
    public boolean isAccelerometerAvailable()
    {
        return mSensorManager != null && mAccelSupported;
    }
    
    public boolean isVibratorAvailable()
    {
        return mVibrator != null;
    }
    
    ///
    /// Handle change of preferences.
    /// @param key is not used.
    ///
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key)
    {        
        mSoundOn = sharedPreferences.getBoolean("android:@+id/pref_sound", true);
        mHapticsOn = sharedPreferences.getBoolean("android:@+id/pref_vibrate", true);
        mAnimationOn = sharedPreferences.getBoolean("android:@+id/pref_animate", true);
        String numBallsPerLineStr = 
            sharedPreferences.getString("android:@+id/pref_balls_per_line", Integer.toString(Constants.NUM_COLOUR_LINES_DEFAULT));
        mNumBallsPerLine = Integer.parseInt(numBallsPerLineStr);
        String crystalTheme = 
            sharedPreferences.getString("android:@+id/pref_theme", Integer.toString(Colours.DEFAULT_THEME));
        mCrystalTheme = Integer.parseInt(crystalTheme);
        mArcadeMode = sharedPreferences.getBoolean("android:@+id/pref_arcade", false);
        mShakeOn = sharedPreferences.getBoolean("android:@+id/pref_shake", true);
    }
    
    
    @Override
    protected void onPause()
    {
        enableSensor(false);
        saveState();
        super.onPause();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        restoreState(); 
        enableSensor(!mIsDialogOpen);
    }

    protected void enableSensor(boolean enable)
    {
        if (enable)
        {
            //
            // Set up the motion sensor listener.
            //
            Sensor sensor = null;
            List<Sensor> sensors = mSensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);
            if (sensors.size() > 0) 
                sensor = sensors.get(0);
            
            mAccelSupported = mSensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME);
                     
            if (!mAccelSupported)
            {
                // No accelerometer on this device.
                mSensorManager.unregisterListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER));     
            }                        
        }
        else
        {
            if (mSensorManager != null) 
            {
                mSensorManager.unregisterListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER));
            }                       
        }        
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState)
    {    
        saveState();
    }
    
    @Override
    protected void onStart()
    {
        super.onStart();
        restoreState();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        saveState();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        saveState();
        finish();        
    }
    
    @Override
    public void onConfigurationChanged(Configuration newConfig) 
    {
      super.onConfigurationChanged(newConfig);
      //
      // Maintain screen orientation. Do nothing. 
      //
    }

    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
        super.onCreateOptionsMenu(menu);
        mMenuBar = new MenuBarView(this, menu, mPlayingFieldModel, mView);
        return true;
    }
    
    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) 
    {
        return mMenuBar.onMenuItemSelected(featureId, item);
    }
    
    
    @Override
    public boolean onPrepareOptionsMenu(Menu menu)
    {
        MenuItem item = menu.findItem(R.id.menu_undo);
        item.setEnabled(mPlayingFieldModel.canUndo());
        return super.onPrepareOptionsMenu(menu);        
    }

    @Override
    protected Dialog onCreateDialog(int id)
    {
        Dialog dialog;
        
        setDialogOpen(true);
        switch (id)
        {
        case Constants.DIALOG_ABOUT_ID:        
            dialog = new AboutDialog(this, mView);
            break;
            
        case Constants.DIALOG_HOW_TO_PLAY_ID:
            dialog = new HowToPlayDialog(this, mView);
            break;
            
        case Constants.DIALOG_HIGH_SCORES_ID:
            dialog = new ViewHighScoresDialog(this, mPanelModel, mView);
            break;
            
        default:
            dialog = super.onCreateDialog(id);
            break;
        }
        return dialog;
    }

    @Override
    protected void onPrepareDialog(int id, Dialog dialog)
    {
        super.onPrepareDialog(id, dialog);
        dialog.dismiss();
        setDialogOpen(true);
    }
    
    
    ///
    /// Play sound on selection of ball.
    ///
    public void playSoundSelected()
    {
        if (!mSoundOn || mSoundPool == null)
            return;
        
        // Play sound.
        mSoundPool.play(mClick, 1.0f, 1.0f, 1, 0, 1.0f);
    }
    
    ///
    /// Play sound on selection of ball.
    ///
    public void playSoundLineCleared()
    {
        if (!mSoundOn || mSoundPool == null)
            return;
        
        // Play sound.
        mSoundPool.play(mSwish, 1.0f, 1.0f, 1, 0, 1.0f);
    }
    
    ///
    /// Play sound on change of crystals.
    ///
    public void playSoundNextCrystals()
    {
        if (!mSoundOn || mSoundPool == null)
            return;
        
        // Play sound.
        mSoundPool.play(mPop, 1.0f, 1.0f, 1, 0, 1.0f);
    }
    
    public void playSoundHighScore()
    {
        if (!mSoundOn || mSoundPool == null)
            return;
        
        // Play sound.
        mSoundPool.play(mHarp, 1.0f, 1.0f, 1, 0, 1.0f);
    }
    
    
    ///
    /// Vibrate for a short interval.
    ///    
    public void vibrateShort()
    {
        if (!mHapticsOn || mVibrator == null)
            return;
            
        // Provide haptic feedback.        
        mVibrator.vibrate(Constants.VIRATION_DURATION_SHORT);
    }
    
    ///
    /// Vibrate for a long interval.
    ///    
    public void vibrateLong()
    {
        if (!mHapticsOn || mVibrator == null)
            return;
        
        // Provide haptic feedback.
        mVibrator.vibrate(Constants.VIRATION_DURATION_LONG);
    }

    public boolean isAnimationOn()
    {
        return mAnimationOn && Build.VERSION.SDK_INT > Constants.MIN_SDK;
    }
    
    ///
    /// The number of balls per line set in the preferences.
    ///    
    public int getNumBallsPerLine()
    {
        return mNumBallsPerLine;
    }    


    @Override
    public void onAccuracyChanged(Sensor arg0, int arg1)
    {
        // Do nothing.
    }

    private long mLastUpdate = 0;
    private long mLastShake = 0;
    private float mLastX;
    private float mLastY;
    private float mLastZ;

    
    ///
    /// Handle sensor motion and if greater than threshold, 
    /// change the next crystals.
    ///
    @Override
    public void onSensorChanged(SensorEvent event)
    {
        synchronized(this) 
        {
            Sensor sensor = event.sensor;

            if (sensor.getType() == Sensor.TYPE_ACCELEROMETER) 
            {
                long currentTime = System.currentTimeMillis();
                
                // Only allow one update every 100ms for performance reasons.
                if ((currentTime - mLastUpdate) > 100) 
                {
                    long diffTime = currentTime - mLastUpdate;
                    mLastUpdate = currentTime;
                   
                    float x = event.values[0];
                    float y = event.values[1];
                    float z = event.values[2];
                    
                    float speed = Math.abs(x + y + z - mLastX - mLastY - mLastZ) / diffTime * 10000;
                    if (speed > Constants.SHAKE_THRESHOLD) 
                    {
                        // Ensure at least a 1s gap between shakes.
                        if (mShakeOn && currentTime - mLastShake > 1000)
                        {
                            // Yes, this is a shake action! Do something about it!
                            mPanelModel.generateFutureColours();
                            mView.animatePanel();
                            mView.update();
                            playSoundNextCrystals();
                            Toast.makeText(this, R.string.shaken_message, Toast.LENGTH_SHORT).show();
                            mLastShake = currentTime;
                        }
                    }
                    mLastX = x;
                    mLastY = y;
                    mLastZ = z;
                }
            }            
        }
    }
}
