//package colour.view;
//
//import javax.swing.*;
//import javax.swing.border.*;
//import colour.*;
//import colour.model.*;
//
//public class StatusBarView extends JLabel implements Constants 
//{
//   /**
//     * Informative game status bar.
//     * @author Michael Chan
//     */
//    
//    /**
//     * Automatically added by Eclipse.
//     */
//    private static final long serialVersionUID = 1L;    
//    
//    private PlayingField m;
//
//    public StatusBarView(PlayingField model) 
//    {
//        // Set up status bar.
//        super();
//        m = model;
//        setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
//        update();
//   }
//
//    public void update() 
//    {
//        // Acquire the latest status.
//        setText (m.getStatus());
//    }
//    
//}
