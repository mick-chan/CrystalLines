package colour;


import java.awt.event.*;
import colour.view.*;
import colour.model.*;

public class ColourLines 
{
    /**
     * Root class of ColourLines program.
     * @author Michael Chan 
     */

    public static void main(String[] args) 
    {
        
        // Setup model of ColourLines.
        PlayingField model = new PlayingField();
        MainView view = new MainView(model);
        
        // Setup view of ColourLines.
        view.addWindowListener 
        (
            new WindowAdapter() 
            {
                public void windowClosing(WindowEvent e)
                {
                	System.exit(0);
                }
            }
        );
    }
}
