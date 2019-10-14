package View;
import javax.swing.*;

public class BaseFrame extends JFrame {
    public BaseFrame()
    {
        InitFrame();
    }

    private void InitFrame()
    {
        ImageIcon image = new ImageIcon(getClass().getResource("/Img/top.gif"));
        this.setIconImage(image.getImage());
    }
}
