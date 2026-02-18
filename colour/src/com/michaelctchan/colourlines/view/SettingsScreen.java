package com.michaelctchan.colourlines.view;

import android.os.Build;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.michaelctchan.colourlines.Constants;
import com.michaelctchan.colourlines.R;

public class SettingsScreen extends PreferenceActivity implements Preference.OnPreferenceClickListener
{    
    protected CheckBoxPreference mShakePreference;
    protected CheckBoxPreference mAnimationPreference;    
    protected boolean mIsFullVersion;
    protected boolean mSDKVersionOK;
    
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
        PreferenceManager.setDefaultValues(this, R.xml.settings, false);
        
        //
        // Disable features that are not available.
        //
        Bundle extras = getIntent().getExtras();
        mShakePreference = (CheckBoxPreference)findPreference("android:@+id/pref_shake");
        mShakePreference.setEnabled(extras.getBoolean(Constants.ACCELEROMETER_AVAILABLE));
        Preference hapticsPreference = findPreference("android:@+id/pref_vibrate");
        hapticsPreference.setEnabled(extras.getBoolean(Constants.VIBRATOR_AVAILABLE));
        mSDKVersionOK = Build.VERSION.SDK_INT > Constants.MIN_SDK;
        mAnimationPreference = (CheckBoxPreference)findPreference("android:@+id/pref_animate");
        if (!mSDKVersionOK)
        {
            mAnimationPreference.setEnabled(false);
            mAnimationPreference.setChecked(false);
        }
        mIsFullVersion = extras.getBoolean(Constants.IS_FULL_VERSION);
        mShakePreference.setChecked(mIsFullVersion);
        mShakePreference.setOnPreferenceClickListener(this);
    }

    @Override
    public boolean onPreferenceClick(Preference preference)
    {
        if (preference == mShakePreference && !mIsFullVersion)
        {
            String proOnly = getString(R.string.pro_only);
            Toast.makeText(this, proOnly, Toast.LENGTH_LONG).show();
            mShakePreference.setChecked(mIsFullVersion);
        }
        return true;
    }
}
