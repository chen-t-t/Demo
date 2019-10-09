package View;

import Msg.ClientExitMsg;
import Msg.User;
import NetWork.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginDialog extends JFrame {
    private static String localpath;
    private final static int width = 430;
    private final static int height = 330;
    private JButton jButton;
    private JButton jsubmit;
    private JComboBox<String> jComboBox;
    private JTextField jnumber;
    private JPasswordField jPasswordField;
    private Client client;

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    private JPanel jPanel = new JPanel(){
        @Override
        protected void paintComponent(Graphics g) {
            Image image = new ImageIcon(getClass().getResource("/Img/back.jpg")).getImage();
            g.drawImage(image,0,0,width,height,this);
        }
    };

    public LoginDialog(Client client) {
        this.client = client;
        /* 设置中间图标 */
        ImageIcon imageIcon = new ImageIcon(getClass().getResource("/Img/top.gif"));
        JLabel jLabelIcon = new JLabel(imageIcon);
        jLabelIcon.setBounds(175, 45, 70, 70);
        jPanel.add(jLabelIcon);
/*      this.getContentPane().setLayout(null);

        ImageIcon imageIcon = new ImageIcon("Img/back.jpg");
        JLabel jLabelpic = new JLabel(imageIcon);
        jLabelpic.setBounds(0,0,430,330);
        this.getLayeredPane().add(jLabelpic,Integer.MAX_VALUE);
        this.getLayeredPane().setLayout(null);
        JPanel j = (JPanel)this.getContentPane();
        j.setOpaque(false);*/

        JLabel jLabel01 = new JLabel("昵称:");
        jLabel01.setBounds(70, 140, 30, 20);
        jLabel01.setFont(new Font("宋体", Font.PLAIN, 12));

        JLabel jLabel02 = new JLabel("密码:");
        jLabel02.setBounds(70, 170, 30, 20);
        jLabel02.setFont(new Font("宋体", Font.PLAIN, 12));

        jPanel.add(jLabel01);
        jPanel.add(jLabel02);

        jsubmit = new JButton("登录");
        jnumber = new JTextField();
        jnumber.setBounds(120, 140, 200, 25);
        jPasswordField = new JPasswordField();
        jPasswordField.setBounds(120, 170, 200, 25);
        jsubmit.setBackground(Color.BLUE);
        jsubmit.setBounds(100, 220, 235, 35);

        jnumber.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == 10)
                {
                    String jname = jnumber.getText();
                    String jpassword = new String(jPasswordField.getPassword());
                    getClient().Connect();
                    User user = new User(jname,jpassword);
                    user.setClientname(jname);

                    getClient().setName(jname);
                    getClient().ReciveMsg();
                    getClient().SendMsg(user);
                }
            }
        });

        jPasswordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == 10)
                {
                    String jname = jnumber.getText();
                    String jpassword = new String(jPasswordField.getPassword());
                    getClient().Connect();
                    User user = new User(jname,jpassword);
                    user.setClientname(jname);
                    getClient().setName(jname);
                    getClient().ReciveMsg();
                    getClient().SendMsg(user);
                }
            }
        });

        jsubmit.addActionListener(e -> {
            if (e.getSource() == jsubmit) {
                String jname = jnumber.getText();
                String jpassword = new String(jPasswordField.getPassword());
                getClient().Connect();
                User user = new User(jname,jpassword);
                user.setClientname(jname);

                getClient().setName(jname);
                getClient().ReciveMsg();
                getClient().SendMsg(user);
            }
        });
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                System.exit(1);
            }
        });

        jButton = new JButton("注册账号");
        jButton.setFocusPainted(false);
        jButton.setContentAreaFilled(false);
        jButton.setBorderPainted(false);
        jButton.setBounds(5, 250, 80, 30);
        jButton.setFont(new Font("宋体", Font.PLAIN, 11));
        jButton.addActionListener(e -> {
            if (e.getSource() == jButton) {
                localpath = "www.baidu.com";
                try {
                    browse2(localpath);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        jPanel.setLayout(null);
        jPanel.add(jnumber);
        jPanel.add(jPasswordField);
        jPanel.add(jsubmit);
        jPanel.add(jButton);

        this.add(jPanel);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ImageIcon image = new ImageIcon(getClass().getResource("/Img/top.gif"));
        this.setIconImage(image.getImage());
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();

        this.setResizable(false);
        this.setLocation(d.width / 2 - width / 2, d.height / 2 - height / 2);
        this.setSize(width, height);
        this.setVisible(true);
    }
    public void loginfail()
    {
        JOptionPane.showMessageDialog(null, "登陆失败", "错误", JOptionPane.ERROR_MESSAGE);
        ClientExitMsg clientExitMsg = new ClientExitMsg();
        getClient().SendMsg(clientExitMsg);
        this.getJnumber().setText("");
        this.getjPasswordField().setText("");
    }

    private static void browse2(String url) throws Exception {
        Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + localpath);
    }

    public JButton getjButton() {
        return jButton;
    }

    public void setjButton(JButton jButton) {
        this.jButton = jButton;
    }

    public JButton getJsubmit() {
        return jsubmit;
    }

    public void setJsubmit(JButton jsubmit) {
        this.jsubmit = jsubmit;
    }

    public JComboBox<String> getjComboBox() {
        return jComboBox;
    }

    public void setjComboBox(JComboBox<String> jComboBox) {
        this.jComboBox = jComboBox;
    }

    public JTextField getJnumber() {
        return jnumber;
    }

    public void setJnumber(JTextField jnumber) {
        this.jnumber = jnumber;
    }

    public JPasswordField getjPasswordField() {
        return jPasswordField;
    }

    public void setjPasswordField(JPasswordField jPasswordField) {
        this.jPasswordField = jPasswordField;
    }

    public void Close()
    {
        this.dispose();
    }
}
