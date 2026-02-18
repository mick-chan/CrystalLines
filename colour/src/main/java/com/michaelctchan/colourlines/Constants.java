package com.michaelctchan.colourlines;

public interface Constants 
{
    /**
     * Stores all constants related to ColourLines.
     * @author Michael Chan
     */

    // Android SDK.
    public static final int MIN_SDK = 4;
    
    // Game parameters.
    public static final int DIMENSION        = 9; // dimension of playing field
    public static final int EXTRA_DIMENSION  = 2; // extended playing field height.
    public static final int NUM_NEXT_BALLS   = 3; // no. of next balls
    public static final int NUM_START_BALLS  = 5; // no. of balls to start with
    public static final int NUM_COLOUR_LINES_DEFAULT = 5; 
    public static final int POINTS_PER_BALL  = 2;
    public static final int POINTS_PER_BALL_BONUS = 5;  

    // Directions.
    public static final int NORTH          = 0;
    public static final int NORTH_EAST     = 1;
    public static final int EAST           = 2;
    public static final int SOUTH_EAST     = 3;
    public static final int SOUTH          = 4;
    public static final int SOUTH_WEST     = 5;
    public static final int WEST           = 6;
    public static final int NORTH_WEST     = 7;
    public static final int NUM_DIRECTIONS = 8;

    // General text constants.
    public static final String GAME_TITLE         = "CrystalLines";
    public static final String START_POSITION_OK_MESSAGE = 
	"Please select position to move this ball";
    public static final String START_POSITION_NOT_OK_MESSAGE = 
	"Please select ball to move";
    public static final String FINISH_POSITION_NOT_OK_MESSAGE = 
	"Can't move there!";
    public static final String GAME_OVER_MESSAGE = "Game over.";

    // Dialog constants.
    public static final int DIALOG_ABOUT_ID        = 0x1;
    public static final int DIALOG_HOW_TO_PLAY_ID  = 0x2;
    public static final int DIALOG_HIGH_SCORES_ID  = 0x3;

    public static final int DIALOG_BORDER_WIDTH = 20;
    public static final float DIALOG_TEXT_SIZE = 16.0f;

    // About dialog constants.
    public static final String ABOUT_BUTTON_MESSAGE = "OK";
    public static final String ABOUT_CONTENTS = 
    	"\u00a9 2011 Michael C. T. Chan<br/>Version %s<br/><br/>" + 
    	"Game concept based on GLines for Linux. Images designed in Blender, " +
    	"rendered in LuxRender and post-processed in GIMP. Additional images " +
    	"provided by VectorStock. Sound effects provided by freeSFX.<br/>";
    public static final String ABOUT_CONTENTS_FREE = 
        "<br/>Download the ad-free " + 
        "<a href=\"market://details?id=com.michaelctchan.crystallinespro\">Pro version</a> " +
        "with bonus features and themes!<br/>";
    // How to play dialog constants.  
    public static final String DIALOG_CLOSE = "Close";
    public static final String HOW_TO_PLAY_DIALOG_TITLE =
	"Instructions";
    public static final String HOW_TO_PLAY_TITLE =
	"Instructions";
    public static final String HOW_TO_PLAY_BUTTON_MESSAGE = "OK";
    public static final String HOW_TO_PLAY_CONTENTS = 
    	"The goal of <i>" + GAME_TITLE + "</i> is to beat the high scores! " + 
    	"Move any crystal to a free space so long as the path is not " + 
    	"blocked. Place five crystals of the same color in a row, " + 
    	"column or diagonal to remove them from the board, adding two " + 
    	"points per crystal to your score. Longer lines score bonus points. " +
    	"Otherwise, the next crystals are added to the board as a penalty. " +
    	"The game is over when the board is full.<br/><br/>" +
    
    	"<i><b>Arcade mode</b></i> brings another dimension of excitement to " +
    	"the game by introducing wildcard crystals (dotted outline) that can " +
    	"match any other crystal, whilst dud crystals (red crosses) cannot " +
    	"be moved! Go to <i>Settings</i> to enable it.<br/>"
    	;

    // View high scores dialog constants.
    public static final String VIEW_HIGH_SCORES_DIALOG_TITLE   = "High Scores";
    public static final String VIEW_HIGH_SCORES_TITLE          = "High Scores";
    public static final String VIEW_HIGH_SCORES_BUTTON_MESSAGE = "OK";
    public static final String VIEW_HIGH_SCORES_NAME_COLUMN    = "Name"; 
    public static final String VIEW_HIGH_SCORES_SCORE_COLUMN   = "Score";
    public static final String VIEW_HIGH_SCORES_NO_SCORES      = "No high scores yet.";
    public static final String ENTER_HIGH_SCORE_TITLE          = "Congratulations!";
    public static final String ENTER_HIGH_SCORE_MESSAGE        = "You've achieved a high score of %d! Please enter your name:\n"; 
    public static final String ENTER_HIGH_SCORE_NO_NAME        = "<Untitled>";
    public static final int ENTER_HIGH_SCORES_NAME_MAX_CHARS   = 25;
    public static final String BONUS_POINTS_MESSAGE            = "Bonus %d points!";
    public static final String CANT_MOVE_MESSAGE               = "Can't move there. Path is blocked!";
    
    // Miscellaneous.
    public static final String HIGH_SCORES_FILE_NAME = "scores.dat";
    public static final int VIRATION_DURATION_SHORT = 50; // in ms.
    public static final int VIRATION_DURATION_LONG = 125; // in ms.
    public static final String ACCELEROMETER_AVAILABLE = "accel_available";
    public static final String VIBRATOR_AVAILABLE = "vibrator_available";
    public static final String IS_FULL_VERSION = "is_full_version";
    public static final int SHAKE_THRESHOLD = 1600; // units/s

    
}   
