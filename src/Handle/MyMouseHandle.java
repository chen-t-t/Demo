package Handle;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MyMouseHandle extends MouseAdapter {

    @Override
    public void mouseClicked(MouseEvent e) {
       if(e.getClickCount() == 2)
       {
           Frame f = new Frame();
           f.setVisible(true);
           f.setSize(200,300);

       }
    }
}
