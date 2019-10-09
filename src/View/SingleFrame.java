package View;

import Handle.MyKeyHandle;
import Msg.ClientExitMsg;
import Msg.ClientLoginoff;
import Msg.ClientMsg;
import NetWork.Client;
import Util.FileUtil;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.text.DateFormat;
import java.util.Date;

public class SingleFrame extends JFrame {
    private JPanel jPanel = null;
    private Dimension dimension = null;
    private int width = 600;
    private int height = 510;
    private JButton jButtonSend = null;
    private JButton jButtonClose = null;
    private JTextArea jTextArea01 = null;
    private JTextArea jTextArea02 = null;
    private LayoutManager layoutManager = null;
    private JScrollPane jScrollPane = null;
    private JMenuBar jMenuBartop = null;
    private JMenuBar jMenuBarBottom = null;
    private DateFormat dateFormat = null;
    private Client client = null;
    private File file;

    private JPanel jPanelback = new JPanel(){
        @Override
        protected void paintComponent(Graphics g) {
            Image image = new ImageIcon(getClass().getResource("/Img/back.jpg")).getImage();
            g.drawImage(image,0,0,width,height,this);
        }
    };

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public SingleFrame(Client client) {
        super();
        this.client = client;
        jPanelback.setLayout(null);
        dateFormat = DateFormat.getDateTimeInstance();

        jTextArea01 = new JTextArea();
        jTextArea01.setEditable(false);
        jTextArea01.addKeyListener(new MyKeyHandle());
        JScrollPane jScrollPane1 = new JScrollPane(jTextArea01,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setBounds(0, 30, 450, 300);

        jPanelback.add(jScrollPane1);
        jTextArea02 = new JTextArea();
        jButtonSend = new JButton("发送");
        jButtonClose = new JButton("关闭");
        jMenuBarBottom = new JMenuBar();
        JMenu jMenupic = new JMenu("图片");
        JMenu jMenujie = new JMenu("截屏");
        jMenuBarBottom.add(jMenujie);
        jMenujie.addMenuListener(new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
                if (e.getSource() == jMenupic) {
                    System.out.println("get picture");
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setDialogTitle("选择图片");
                    fileChooser.setApproveButtonToolTipText("确定");
                    int f = fileChooser.showOpenDialog(null);
                    if(f == JFileChooser.APPROVE_OPTION)
                    {
                        File file = fileChooser.getSelectedFile();
                        jTextArea02.append(file.getAbsolutePath());
                    }
                }
            }
            @Override
            public void menuDeselected(MenuEvent e) {

            }

            @Override
            public void menuCanceled(MenuEvent e) {

            }
        });

        jMenupic.addMenuListener(new MenuListener(){
            @Override
            public void menuSelected(MenuEvent e) {
                if (e.getSource() == jMenupic) {
                    System.out.println("get picture");
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setDialogTitle("选择图片");
                    fileChooser.setApproveButtonToolTipText("确定");
                    int f = fileChooser.showOpenDialog(null);
                    if(f == JFileChooser.APPROVE_OPTION)
                    {
                        file = fileChooser.getSelectedFile();
                        jTextArea02.append(file.getAbsolutePath());
                    }
                }
            }
            @Override
            public void menuDeselected(MenuEvent e) {
                System.out.println("gg");
            }

            @Override
            public void menuCanceled(MenuEvent e) {

            }
        });

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                /*ClientLoginoff clientLoginoff = new ClientLoginoff();
                clientLoginoff.setClientname(getClient().getName());
                getClient().SendMsg(clientLoginoff);
                ClientExitMsg clientExitMsg = new ClientExitMsg();
                getClient().SendMsg(clientExitMsg);*/
 /*             setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);*/
                setVisible(false);
            }
        });

        jButtonSend.addActionListener((ActionEvent e) -> {
            if (e.getSource() == jButtonSend) {
                String str = jTextArea02.getText();
                if(FileUtil.IsFile(file,str))
                {

                }
                else{
                    ClientMsg b = new ClientMsg();
                    b.setName(getClient().getName());
                    b.setData(dateFormat.format(new Date()));
                    b.setSay(str);
                    getClient().SendMsg(b);
                }
            }
        });

        jMenuBarBottom.add(jMenupic);
        jMenuBarBottom.setBounds(0, 330, 450, 30);
        jTextArea02.setBounds(0, 360, 450, 100);
        jButtonSend.setBounds(480, 380, 80, 30);
        jButtonClose.setBounds(480, 420, 80, 30);
        jTextArea02.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == 10) {
                    String str = jTextArea02.getText();
                    ClientMsg b = new ClientMsg();
                    b.setName(getClient().getName());
                    b.setData(dateFormat.format(new Date()));
                    b.setSay(str);
                    getClient().SendMsg(b);
                    jTextArea02.setText("");
                    jTextArea02.setCaretPosition(0);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == 10) {
                    jTextArea02.setText("");
                }
            }
        });

        jPanelback.add(jMenuBarBottom);
        jPanelback.add(jTextArea02);
        jPanelback.add(jButtonSend);
        jPanelback.add(jButtonClose);
        this.add(jPanelback);
        this.setResizable(false);
        dimension = Toolkit.getDefaultToolkit().getScreenSize();

        setSize(width, height);
        setTitle("聊天");
        setLocation(dimension.width / 2 - width / 2, dimension.height / 2 - height / 2);
        setVisible(true);
    }

    public JPanel getjPanel() {
        return jPanel;
    }

    public void setjPanel(JPanel jPanel) {
        this.jPanel = jPanel;
    }

    public Dimension getDimension() {
        return dimension;
    }

    public void setDimension(Dimension dimension) {
        this.dimension = dimension;
    }

    @Override
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public JButton getjButtonSend() {
        return jButtonSend;
    }

    public void setjButtonSend(JButton jButtonSend) {
        this.jButtonSend = jButtonSend;
    }

    public JButton getjButtonClose() {
        return jButtonClose;
    }

    public void setjButtonClose(JButton jButtonClose) {
        this.jButtonClose = jButtonClose;
    }

    public JTextArea getjTextArea01() {
        return jTextArea01;
    }

    public void setjTextArea01(JTextArea jTextArea01) {
        this.jTextArea01 = jTextArea01;
    }

    public JTextArea getjTextArea02() {
        return jTextArea02;
    }

    public void setjTextArea02(JTextArea jTextArea02) {
        this.jTextArea02 = jTextArea02;
    }

    public LayoutManager getLayoutManager() {
        return layoutManager;
    }

    public void setLayoutManager(LayoutManager layoutManager) {
        this.layoutManager = layoutManager;
    }

    public JScrollPane getjScrollPane() {
        return jScrollPane;
    }

    public void setjScrollPane(JScrollPane jScrollPane) {
        this.jScrollPane = jScrollPane;
    }

    public JMenuBar getjMenuBartop() {
        return jMenuBartop;
    }

    public void setjMenuBartop(JMenuBar jMenuBartop) {
        this.jMenuBartop = jMenuBartop;
    }

    public JMenuBar getjMenuBarBottom() {
        return jMenuBarBottom;
    }

    public void setjMenuBarBottom(JMenuBar jMenuBarBottom) {
        this.jMenuBarBottom = jMenuBarBottom;
    }

}
