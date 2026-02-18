package com.michaelctchan.colourlines.view;

import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.michaelctchan.colourlines.ColourLines;
import com.michaelctchan.colourlines.Colours;
import com.michaelctchan.colourlines.Constants;
import com.michaelctchan.colourlines.R;
import com.michaelctchan.colourlines.model.Square;

public class SquareView extends ImageView implements Constants
{
    protected Square mModel;
    protected Animation mFadeIn;
    protected Animation mFadeOut;
    protected boolean mAnimated;
    protected ColourLines mContext;
    
    class SquareAnimationListener implements AnimationListener
    {
        private Square mModel;
        private SquareView mView;
        
        public SquareAnimationListener(Square model, SquareView view)
        {
            mModel = model;
            mView = view;
        }
        
        @Override
        public void onAnimationEnd(Animation animation)
        {
            mView.setImageResource(Colours.getColours(mContext.getCrystalTheme())[mModel.getColour()]);
        }

        @Override
        public void onAnimationRepeat(Animation animation)
        {        
            // Do nothing.
        }

        @Override
        public void onAnimationStart(Animation animation)
        {
            int colours[] = getColours();
            mView.setImageResource(colours[mModel.getPreviousColour()]);
        }
    }
    
    public SquareView(ColourLines context, Square model, int dimensions)
    {
        super(context);
        mContext = context;
        mModel = model;
        mFadeIn = AnimationUtils.loadAnimation(context, R.anim.fade_in);
        mFadeOut = AnimationUtils.loadAnimation(context, R.anim.fade_out);
        if (mFadeOut != null)
            mFadeOut.setAnimationListener(new SquareAnimationListener(model, this));
        
        setMinimumWidth(dimensions);
        setMinimumHeight(dimensions);
        setMaxWidth(dimensions);
        setMaxHeight(dimensions);
        setAdjustViewBounds(true);
        setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        update();  
    }
    
    public Square getModel()
    {
        return mModel;
    }
    
    private void fadeOut()
    {
        if (mFadeOut != null) 
            startAnimation(mFadeOut);
    }
    
    private void fadeIn()
    {
        if (mFadeIn != null) 
            startAnimation(mFadeIn);
    }
    
    public void animateSquare()
    {
        if (!mContext.isAnimationOn())
            return;
        
        if (mModel.getPreviousColour() == Colours.NONCOLOUR && mModel.getColour() != Colours.NONCOLOUR)
        {
            fadeIn();
        }
        else if (mModel.isStartSquare())
        {
            mModel.setInitialColour(Colours.NONCOLOUR);
        }
        else if (mModel.getPreviousColour() != Colours.NONCOLOUR && mModel.getColour() == Colours.NONCOLOUR)
        {
            fadeOut();    
        }
    }
    
    public void update()
    {
        int colours[] = getColours();
        if (!mContext.isAnimationOn() || !(mModel.getPreviousColour() != Colours.NONCOLOUR && mModel.getColour() == Colours.NONCOLOUR))
        {
            //
            // This square is not a fade out square.
            //
            setImageResource(colours[mModel.getColour()]);
            postInvalidate();
        }
    }

    private int[] getColours()
    {
        if (!mModel.isChosen())
            return Colours.getColours(mContext.getCrystalTheme());
        else
            return Colours.getColoursSelected(mContext.getCrystalTheme());
    }
    
}
