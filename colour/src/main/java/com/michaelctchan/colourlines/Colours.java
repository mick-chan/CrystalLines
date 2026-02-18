package com.michaelctchan.colourlines;

import java.util.Random;

///
/// This class encapulates the crystal colours and themes.
///
public class Colours
{
    // Colours.
    public static final int NONCOLOUR = 0;
    public static final int BLUE      = 1;
    public static final int PURPLE    = 2;
    public static final int RED       = 3;
    public static final int ORANGE    = 4;
    public static final int YELLOW    = 5;
    public static final int GREEN     = 6;
    public static final int CYAN      = 7;
    public static final int NUM_STANDARD_COLOURS = CYAN;
    public static final int NUM_TOTAL_COLOURS = CYAN + 1 + Colours.NUM_ARCADE_COLOURS;
    
    // Arcade mode colours.
    public static final int WILDCARD = 8;
    public static final int DUD = 9;
    public static final int NUM_ARCADE_COLOURS = 2;
    public static final double ARCADE_PROBABILITY = 0.10;
    
    
    // Default theme.
    public static final int DEFAULT_THEME = 0;   
    
    //
    // Produces a random colour.
    //
    public static int generateRandomColour(boolean arcadeMode)
    {
        int ARCADE_THRESHOLD = (int)(ARCADE_PROBABILITY * 100);
        
        Random r1 = new Random();
        Random r2 = new Random();

        if (r1.nextInt(100) < ARCADE_THRESHOLD && arcadeMode)
        {
            // Generate an arcade colour.
            return r2.nextInt(NUM_ARCADE_COLOURS) + WILDCARD;
        }
        else
        {
            // Generate a standard colour.
            return r2.nextInt(Colours.NUM_STANDARD_COLOURS) + 1;            
        }
    }
    
    //
    // Obtain the resource IDs for the crystal images for the given theme.
    //
    public static int[] getColours(int theme)
    {
        switch (theme)
        {
        case 0:
            return COLOURS;

        case 1:
            return ACC_COLOURS;
            
        case 2:
            return mahjongColours;
            
        case 3: 
            return FRUIT_COLOURS;
            
        case 4: 
            return SPORT_COLOURS;
            
        case 5:
            return WARN_COLOURS;
            
        case 6:
            return poolColours;
            
        case 7:
            return ALIENS_COLOURS;
            
        case 8:
            return chessColours;
            
        case 9: 
            return INFECTION_COLOURS;
            
        case 10:
            return PEOPLE_COLOURS;
            
        case 11: 
            return MARINE_COLOURS;
            
        case 12:
            return STAR_COLOURS;
            
        default: 
            return COLOURS;
        }
    }
    
    //
    // Obtain the resource IDs for the crystal selected images for the given theme.
    //
    public static int[] getColoursSelected(int theme)
    {
        switch (theme)
        {
        case 0:
            return COLOURS_SELECTED;
        
        case 1:
            return ACC_COLOURS_SELECTED;
            
        case 2:
            return mahjongColoursSelected;
            
        case 3: 
            return FRUIT_COLOURS_SELECTED;
            
        case 4: 
            return SPORT_COLOURS_SELECTED;
            
        case 5: 
            return WARN_COLOURS_SELECTED;
            
        case 6:
            return poolColoursSelected;
            
        case 7: 
            return ALIENS_COLOURS_SELECTED;
            
        case 8:
            return chessColoursSelected;
            
        case 9: 
            return INFECTION_COLOURS_SELECTED;
            
        case 10:
            return PEOPLE_COLOURS_SELECTED;
            
        case 11:
            return MARINE_COLOURS_SELECTED;
            
        case 12:
            return STAR_COLOURS_SELECTED;
            
        default:
            return COLOURS_SELECTED;
            
        }
    }
    
    //
    // Some themes have dynamic colour selection.
    // Reset the colours for these themes.
    //
    public static void resetColours()
    {
        getMahjongTiles();
        getPoolBalls();
        getChessTiles();
    }
    
    ///
    /// Standard crystals.
    ///
    private static final int[] COLOURS = 
    {
        R.drawable.noncolour, 
        R.drawable.blue, 
        R.drawable.purple, 
        R.drawable.red, 
        R.drawable.orange, 
        R.drawable.yellow, 
        R.drawable.green, 
        R.drawable.cyan,
        R.drawable.wildcard,
        R.drawable.dud
    };
    private static final int[] COLOURS_SELECTED = 
    {
        R.drawable.noncolour, 
        R.drawable.blue_pressed, 
        R.drawable.purple_pressed, 
        R.drawable.red_pressed, 
        R.drawable.orange_pressed, 
        R.drawable.yellow_pressed, 
        R.drawable.green_pressed, 
        R.drawable.cyan_pressed,
        R.drawable.wildcard,
        R.drawable.dud
    };      
    
    
    //
    // Accessibility crystals.
    //
    private static final int[] ACC_COLOURS = 
    {
        R.drawable.noncolour, 
        R.drawable.acc_blue, 
        R.drawable.acc_purple, 
        R.drawable.acc_red, 
        R.drawable.acc_orange, 
        R.drawable.acc_yellow, 
        R.drawable.acc_green, 
        R.drawable.acc_cyan,
        R.drawable.wildcard,
        R.drawable.dud
    };
    private static final int[] ACC_COLOURS_SELECTED = 
    {
        R.drawable.noncolour, 
        R.drawable.acc_blue_pressed, 
        R.drawable.acc_purple_pressed, 
        R.drawable.acc_red_pressed, 
        R.drawable.acc_orange_pressed, 
        R.drawable.acc_yellow_pressed, 
        R.drawable.acc_green_pressed, 
        R.drawable.acc_cyan_pressed,
        R.drawable.wildcard,
        R.drawable.dud

    };      
    
    //
    // Crystal stars theme.
    //
    private static final int[] STAR_COLOURS = 
    {
        R.drawable.noncolour, 
        R.drawable.star_blue, 
        R.drawable.star_purple, 
        R.drawable.star_red, 
        R.drawable.star_orange, 
        R.drawable.star_yellow, 
        R.drawable.star_green, 
        R.drawable.star_cyan,
        R.drawable.wildcard,
        R.drawable.dud
    };
    private static final int[] STAR_COLOURS_SELECTED = 
    {
        R.drawable.noncolour, 
        R.drawable.star_blue_pressed, 
        R.drawable.star_purple_pressed, 
        R.drawable.star_red_pressed, 
        R.drawable.star_orange_pressed, 
        R.drawable.star_yellow_pressed, 
        R.drawable.star_green_pressed, 
        R.drawable.star_cyan_pressed,
        R.drawable.wildcard,
        R.drawable.dud
    };      
    
    
    //
    // Fruit theme.
    //
    private static final int[] FRUIT_COLOURS = 
    {
        R.drawable.noncolour, 
        R.drawable.fruit_banana, 
        R.drawable.fruit_strawberry, 
        R.drawable.fruit_grapes, 
        R.drawable.fruit_apple, 
        R.drawable.fruit_watermelon, 
        R.drawable.fruit_cherry, 
        R.drawable.fruit_orange,
        R.drawable.wildcard,
        R.drawable.dud
    };
    private static final int[] FRUIT_COLOURS_SELECTED = 
    {
        R.drawable.noncolour, 
        R.drawable.fruit_banana_pressed, 
        R.drawable.fruit_strawberry_pressed, 
        R.drawable.fruit_grapes_pressed, 
        R.drawable.fruit_apple_pressed, 
        R.drawable.fruit_watermelon_pressed, 
        R.drawable.fruit_cherry_pressed, 
        R.drawable.fruit_orange_pressed,
        R.drawable.wildcard,
        R.drawable.dud
    };              
    
    //
    // Sports theme.
    //
    private static final int[] SPORT_COLOURS = 
    {
        R.drawable.noncolour, 
        R.drawable.sport_baseball, 
        R.drawable.sport_tennis, 
        R.drawable.sport_netball, 
        R.drawable.sport_basketball, 
        R.drawable.sport_bowling, 
        R.drawable.sport_rugby, 
        R.drawable.sport_soccer,
        R.drawable.wildcard,
        R.drawable.dud
    };
    private static final int[] SPORT_COLOURS_SELECTED = 
    {
        R.drawable.noncolour, 
        R.drawable.sport_baseball_pressed, 
        R.drawable.sport_tennis_pressed, 
        R.drawable.sport_netball_pressed, 
        R.drawable.sport_basketball_pressed, 
        R.drawable.sport_bowling_pressed, 
        R.drawable.sport_rugby_pressed, 
        R.drawable.sport_soccer_pressed,
        R.drawable.wildcard,
        R.drawable.dud
    }; 
    
    //
    // Biohazard theme.
    //
    private static final int[] WARN_COLOURS = 
    {
        R.drawable.noncolour, 
        R.drawable.warn_poison, 
        R.drawable.warn_slip, 
        R.drawable.warn_voltage, 
        R.drawable.warn_fire, 
        R.drawable.warn_explode, 
        R.drawable.warn_radioactive, 
        R.drawable.warn_biohazard,
        R.drawable.wildcard,
        R.drawable.dud
    };
    private static final int[] WARN_COLOURS_SELECTED = 
    {
        R.drawable.noncolour, 
        R.drawable.warn_poison_pressed, 
        R.drawable.warn_slip_pressed, 
        R.drawable.warn_voltage_pressed, 
        R.drawable.warn_fire_pressed, 
        R.drawable.warn_explode_pressed, 
        R.drawable.warn_radioactive_pressed, 
        R.drawable.warn_biohazard_pressed,
        R.drawable.wildcard,
        R.drawable.dud
    }; 
    
    //
    // Aliens theme.
    //
    private static final int[] ALIENS_COLOURS = 
    {
        R.drawable.noncolour, 
        R.drawable.aliens_01, 
        R.drawable.aliens_02, 
        R.drawable.aliens_03, 
        R.drawable.aliens_04, 
        R.drawable.aliens_05, 
        R.drawable.aliens_06, 
        R.drawable.aliens_07,
        R.drawable.wildcard,
        R.drawable.dud
    };        
    private static final int[] ALIENS_COLOURS_SELECTED = 
    {
        R.drawable.noncolour, 
        R.drawable.aliens_01_pressed, 
        R.drawable.aliens_02_pressed, 
        R.drawable.aliens_03_pressed, 
        R.drawable.aliens_04_pressed, 
        R.drawable.aliens_05_pressed, 
        R.drawable.aliens_06_pressed, 
        R.drawable.aliens_07_pressed,
        R.drawable.wildcard,
        R.drawable.dud
    };   
    

    //
    // Infection theme.
    //
    private static final int[] INFECTION_COLOURS = 
    {
        R.drawable.noncolour, 
        R.drawable.infection_01, 
        R.drawable.infection_02, 
        R.drawable.infection_03, 
        R.drawable.infection_04, 
        R.drawable.infection_05, 
        R.drawable.infection_06, 
        R.drawable.infection_07,
        R.drawable.wildcard,
        R.drawable.dud
    };        
    private static final int[] INFECTION_COLOURS_SELECTED = 
    {
        R.drawable.noncolour, 
        R.drawable.infection_01_pressed, 
        R.drawable.infection_02_pressed, 
        R.drawable.infection_03_pressed, 
        R.drawable.infection_04_pressed, 
        R.drawable.infection_05_pressed, 
        R.drawable.infection_06_pressed, 
        R.drawable.infection_07_pressed,
        R.drawable.wildcard,
        R.drawable.dud
    };   
    
    //
    // People theme.
    //
    private static final int[] PEOPLE_COLOURS = 
    {
        R.drawable.noncolour, 
        R.drawable.people_01, 
        R.drawable.people_02, 
        R.drawable.people_03, 
        R.drawable.people_04, 
        R.drawable.people_05, 
        R.drawable.people_06, 
        R.drawable.people_07,
        R.drawable.wildcard,
        R.drawable.dud
    };            
    private static final int[] PEOPLE_COLOURS_SELECTED = 
    {
        R.drawable.noncolour, 
        R.drawable.people_01_pressed, 
        R.drawable.people_02_pressed, 
        R.drawable.people_03_pressed, 
        R.drawable.people_04_pressed, 
        R.drawable.people_05_pressed, 
        R.drawable.people_06_pressed, 
        R.drawable.people_07_pressed,
        R.drawable.wildcard,
        R.drawable.dud
    };            
       
    //
    // Marine theme.
    //
    private static final int[] MARINE_COLOURS = 
    {
        R.drawable.noncolour, 
        R.drawable.marine_01, 
        R.drawable.marine_02, 
        R.drawable.marine_03, 
        R.drawable.marine_04, 
        R.drawable.marine_05, 
        R.drawable.marine_06, 
        R.drawable.marine_07,
        R.drawable.wildcard,
        R.drawable.dud
    };    
    private static final int[] MARINE_COLOURS_SELECTED = 
    {
        R.drawable.noncolour, 
        R.drawable.marine_01_pressed, 
        R.drawable.marine_02_pressed, 
        R.drawable.marine_03_pressed, 
        R.drawable.marine_04_pressed, 
        R.drawable.marine_05_pressed, 
        R.drawable.marine_06_pressed, 
        R.drawable.marine_07_pressed,
        R.drawable.wildcard,
        R.drawable.dud
    };            
    
    //
    // Mahjong theme tiles.
    //
    private static final int[][] MAHJONG_COLOURS = 
    {
        {
            R.drawable.mahjong_0_0,
            R.drawable.mahjong_0_1,
            R.drawable.mahjong_0_2,
            R.drawable.mahjong_0_3,
            R.drawable.mahjong_0_4,
            R.drawable.mahjong_0_5,
            R.drawable.mahjong_0_6,
            R.drawable.mahjong_0_7,
            R.drawable.mahjong_0_8            
        },
        {
            R.drawable.mahjong_1_0,
            R.drawable.mahjong_1_1,
            R.drawable.mahjong_1_2,
            R.drawable.mahjong_1_3,
            R.drawable.mahjong_1_4,
            R.drawable.mahjong_1_5,
            R.drawable.mahjong_1_6,
            R.drawable.mahjong_1_7,
            R.drawable.mahjong_1_8            
        },
        {
            R.drawable.mahjong_2_0,
            R.drawable.mahjong_2_1,
            R.drawable.mahjong_2_2,
            R.drawable.mahjong_2_3,
            R.drawable.mahjong_2_4,
            R.drawable.mahjong_2_5,
            R.drawable.mahjong_2_6,
            R.drawable.mahjong_2_7,
            R.drawable.mahjong_2_8            
        },
        {
            R.drawable.mahjong_3_0,
            R.drawable.mahjong_3_1,
            R.drawable.mahjong_3_2,
            R.drawable.mahjong_3_3,
            R.drawable.mahjong_3_4,
            R.drawable.mahjong_3_5,
            R.drawable.mahjong_3_6,
            R.drawable.mahjong_3_3,
            R.drawable.mahjong_3_4            
        }
    };
    
    private static final int[][] MAHJONG_COLOURS_SELECTED = 
    {
        {
            R.drawable.mahjong_selected_0_0,
            R.drawable.mahjong_selected_0_1,
            R.drawable.mahjong_selected_0_2,
            R.drawable.mahjong_selected_0_3,
            R.drawable.mahjong_selected_0_4,
            R.drawable.mahjong_selected_0_5,
            R.drawable.mahjong_selected_0_6,
            R.drawable.mahjong_selected_0_7,
            R.drawable.mahjong_selected_0_8            
        },
        {
            R.drawable.mahjong_selected_1_0,
            R.drawable.mahjong_selected_1_1,
            R.drawable.mahjong_selected_1_2,
            R.drawable.mahjong_selected_1_3,
            R.drawable.mahjong_selected_1_4,
            R.drawable.mahjong_selected_1_5,
            R.drawable.mahjong_selected_1_6,
            R.drawable.mahjong_selected_1_7,
            R.drawable.mahjong_selected_1_8            
        },
        {
            R.drawable.mahjong_selected_2_0,
            R.drawable.mahjong_selected_2_1,
            R.drawable.mahjong_selected_2_2,
            R.drawable.mahjong_selected_2_3,
            R.drawable.mahjong_selected_2_4,
            R.drawable.mahjong_selected_2_5,
            R.drawable.mahjong_selected_2_6,
            R.drawable.mahjong_selected_2_7,
            R.drawable.mahjong_selected_2_8            
        },
        {
            R.drawable.mahjong_selected_3_0,
            R.drawable.mahjong_selected_3_1,
            R.drawable.mahjong_selected_3_2,
            R.drawable.mahjong_selected_3_3,
            R.drawable.mahjong_selected_3_4,
            R.drawable.mahjong_selected_3_5,
            R.drawable.mahjong_selected_3_6,
            R.drawable.mahjong_selected_3_3,
            R.drawable.mahjong_selected_3_4            
        }
    };
    
    private static int[] mahjongColours;
    private static int[] mahjongColoursSelected;
    
    //
    // Reset the mahjong tiles with new randomly selected tiles.
    //
    private static void getMahjongTiles()
    {
        final int MAX_PER_ROW = 2;
        
        mahjongColours = new int[NUM_TOTAL_COLOURS];
        mahjongColoursSelected = new int[NUM_TOTAL_COLOURS];
        int[] rowTally = new int[MAHJONG_COLOURS.length];
        
        for (int i = 0; i < mahjongColours.length; ++i)
            mahjongColours[i] = R.drawable.noncolour;
        for (int i = 0; i < rowTally.length; ++i)
            rowTally[i] = 0;
        
        Random r = new Random();
        
        for (int i = 1; i <= NUM_STANDARD_COLOURS; ++i)
        {
            while (true)
            {
                int x = r.nextInt(MAHJONG_COLOURS[0].length);
                int y = r.nextInt(MAHJONG_COLOURS.length);
                
                // Ensure no more than two from each row.
                if (rowTally[y] >= MAX_PER_ROW)
                    continue;
                
                // Check if candidate already chosen.
                int candidate = MAHJONG_COLOURS[y][x];
                boolean exists = false;
                for (int j = 1; j < mahjongColours.length; ++j)
                    if (mahjongColours[j] == candidate)
                    {
                        exists = true;
                        break;
                    }
                if (exists)
                    continue;
                
                //
                // Add candidate to colours.
                //
                ++rowTally[y];
                mahjongColours[i] = candidate;
                mahjongColoursSelected[i] = MAHJONG_COLOURS_SELECTED[y][x];
                break;
            }                        
        } 
        
        mahjongColours[Colours.WILDCARD] = R.drawable.wildcard;
        mahjongColoursSelected[Colours.WILDCARD] = R.drawable.wildcard;
        mahjongColours[Colours.DUD] = R.drawable.dud;
        mahjongColoursSelected[Colours.DUD] = R.drawable.dud;        
    }
    
    //
    // Pool balls.
    //
    private static final int[][] POOL_COLOURS = 
    {
        {
            R.drawable.pool_01,
            R.drawable.pool_02,
            R.drawable.pool_03,
            R.drawable.pool_04,
            R.drawable.pool_05,
            R.drawable.pool_06,
            R.drawable.pool_07,
            R.drawable.pool_08            
        },
        {
            R.drawable.pool_09,
            R.drawable.pool_10,
            R.drawable.pool_11,
            R.drawable.pool_12,
            R.drawable.pool_13,
            R.drawable.pool_14,
            R.drawable.pool_15,
            R.drawable.pool_16            
        }
    };  
    private static final int[][] POOL_COLOURS_SELECTED = 
    {
        {
            R.drawable.pool_01_pressed,
            R.drawable.pool_02_pressed,
            R.drawable.pool_03_pressed,
            R.drawable.pool_04_pressed,
            R.drawable.pool_05_pressed,
            R.drawable.pool_06_pressed,
            R.drawable.pool_07_pressed,
            R.drawable.pool_08_pressed            
        },
        {
            R.drawable.pool_09_pressed,
            R.drawable.pool_10_pressed,
            R.drawable.pool_11_pressed,
            R.drawable.pool_12_pressed,
            R.drawable.pool_13_pressed,
            R.drawable.pool_14_pressed,
            R.drawable.pool_15_pressed,
            R.drawable.pool_16_pressed            
        }
    };
    
    private static int[] poolColours;
    private static int[] poolColoursSelected;
    
    //
    // Reset the pool balls with new randomly selected tiles.
    //
    private static void getPoolBalls()
    {
        final int MAX_PER_COL = 1;
        final int MAX_PER_ROW = 4;        
        
        poolColours = new int[NUM_TOTAL_COLOURS];
        poolColoursSelected = new int[NUM_TOTAL_COLOURS];
        int[] colTally = new int[POOL_COLOURS[0].length];
        int[] rowTally = new int[POOL_COLOURS.length];
        
        for (int i = 0; i < poolColours.length; ++i)
            poolColours[i] = R.drawable.noncolour;
        for (int i = 0; i < colTally.length; ++i)
            colTally[i] = 0;
        for (int i = 0; i < rowTally.length; ++i)
            rowTally[i] = 0;
        
        Random r = new Random();
        
        for (int i = 1; i <= NUM_STANDARD_COLOURS; ++i)
        {
            while (true)
            {
                int x = r.nextInt(POOL_COLOURS[0].length);
                int y = r.nextInt(POOL_COLOURS.length);
                
                // Ensure no more than one from each column.
                if (colTally[x] >= MAX_PER_COL)
                    continue;
                // Ensure no more than four from each row.
                if (rowTally[y] >= MAX_PER_ROW)
                    continue;
                
                
                // Check if candidate already chosen.
                int candidate = POOL_COLOURS[y][x];
                boolean exists = false;
                for (int j = 1; j < poolColours.length; ++j)
                    if (poolColours[j] == candidate)
                    {
                        exists = true;
                        break;
                    }
                if (exists)
                    continue;
                
                //
                // Add candidate to colours.
                //
                ++colTally[x];
                ++rowTally[y];
                poolColours[i] = candidate;
                poolColoursSelected[i] = POOL_COLOURS_SELECTED[y][x];
                break;
            }                        
        }
        
        poolColours[Colours.WILDCARD] = R.drawable.wildcard;
        poolColoursSelected[Colours.WILDCARD] = R.drawable.wildcard;
        poolColours[Colours.DUD] = R.drawable.dud;
        poolColoursSelected[Colours.DUD] = R.drawable.dud;                
    }

    //
    // Chess theme.
    //
    private static final int[][] CHESS_COLOURS = 
    {
        {
            R.drawable.chess_black_bishop,
            R.drawable.chess_black_castle,
            R.drawable.chess_black_horse,
            R.drawable.chess_black_king,
            R.drawable.chess_black_pawn,
            R.drawable.chess_black_queen           
        },
        {
            R.drawable.chess_white_bishop,
            R.drawable.chess_white_castle,
            R.drawable.chess_white_horse,
            R.drawable.chess_white_king,
            R.drawable.chess_white_pawn,
            R.drawable.chess_white_queen           
        }
    };  
    private static final int[][] CHESS_COLOURS_SELECTED = 
    {
        {
            R.drawable.chess_black_bishop_pressed,
            R.drawable.chess_black_castle_pressed,
            R.drawable.chess_black_horse_pressed,
            R.drawable.chess_black_king_pressed,
            R.drawable.chess_black_pawn_pressed,
            R.drawable.chess_black_queen_pressed           
        },
        {
            R.drawable.chess_white_bishop_pressed,
            R.drawable.chess_white_castle_pressed,
            R.drawable.chess_white_horse_pressed,
            R.drawable.chess_white_king_pressed,
            R.drawable.chess_white_pawn_pressed,
            R.drawable.chess_white_queen_pressed           
        }
    };  
    
    private static int[] chessColours;
    private static int[] chessColoursSelected;
    
    //
    // Reset the chess tiles with new randomly selected tiles.
    //
    private static void getChessTiles()
    {
        final int MAX_PER_ROW = 4;
        
        chessColours = new int[NUM_TOTAL_COLOURS];
        chessColoursSelected = new int[NUM_TOTAL_COLOURS];
        int[] rowTally = new int[CHESS_COLOURS.length];
        
        for (int i = 0; i < chessColours.length; ++i)
            chessColours[i] = R.drawable.noncolour;
        for (int i = 0; i < rowTally.length; ++i)
            rowTally[i] = 0;
        
        Random r = new Random();
        
        for (int i = 1; i <= NUM_STANDARD_COLOURS; ++i)
        {
            while (true)
            {
                int x = r.nextInt(CHESS_COLOURS[0].length);
                int y = r.nextInt(CHESS_COLOURS.length);
                
                // Ensure no more than two from each row.
                if (rowTally[y] >= MAX_PER_ROW)
                    continue;
                
                // Check if candidate already chosen.
                int candidate = CHESS_COLOURS[y][x];
                boolean exists = false;
                for (int j = 1; j < chessColours.length; ++j)
                    if (chessColours[j] == candidate)
                    {
                        exists = true;
                        break;
                    }
                if (exists)
                    continue;
                
                //
                // Add candidate to colours.
                //
                ++rowTally[y];
                chessColours[i] = candidate;
                chessColoursSelected[i] = CHESS_COLOURS_SELECTED[y][x];
                break;
            }                        
        }  
        
        chessColours[Colours.WILDCARD] = R.drawable.wildcard;
        chessColoursSelected[Colours.WILDCARD] = R.drawable.wildcard;
        chessColours[Colours.DUD] = R.drawable.dud;
        chessColoursSelected[Colours.DUD] = R.drawable.dud;            
    }
    
    //
    // Save/load state functions.
    //
    
    
    public static void setState(ColoursData data)
    {
        for (int i = 0; i < mahjongColours.length; ++i)
        {
            mahjongColours[i] = data.cdMahjongColours[i];
            mahjongColoursSelected[i] = data.cdMahjongColoursSelected[i];            
        }
        for (int i = 0; i < poolColours.length; ++i)
        {
            poolColours[i] = data.cdPoolColours[i];
            poolColoursSelected[i] = data.cdPoolColoursSelected[i];            
        }
        for (int i = 0; i < chessColours.length; ++i)
        {
            chessColours[i] = data.cdChessColours[i];
            chessColoursSelected[i] = data.cdChessColoursSelected[i];            
        }
    }
    
    public static ColoursData getState()
    {
        ColoursData data = new ColoursData();

        for (int i = 0; i < mahjongColours.length; ++i)
        {
            data.cdMahjongColours[i] = mahjongColours[i];
            data.cdMahjongColoursSelected[i] = mahjongColoursSelected[i];            
        }
        for (int i = 0; i < poolColours.length; ++i)
        {
            data.cdPoolColours[i] = poolColours[i];
            data.cdPoolColoursSelected[i] = poolColoursSelected[i];            
        }
        for (int i = 0; i < chessColours.length; ++i)
        {
            data.cdChessColours[i] = chessColours[i];
            data.cdChessColoursSelected[i] = chessColoursSelected[i];            
        }        
        return data;
    }    
    
}

