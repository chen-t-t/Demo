package View;

import Msg.ClientExitMsg;
import Msg.ClientGetFrinedsMsg;
import Msg.ClientLoginoff;
import NetWork.Client;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

/**
 * Friend dialog
 * 显示好友列表
 */
public class FriendDialog extends BaseFrame {
    private final static int width = 300;
    private final static int height = 700;
    /** 客户端 */
    private Client client;
    /** 托盘 */
    private TrayIcon trayIcon;
    /** 好友列表 */
    private DefaultListModel<String> defaultListModel = null;
    private JList<String> friendJList = null;
    private JScrollPane jScrollPane = null;

    /** 选择名称 */
    private String selectname = null;


    private JPanel jPanel = new JPanel(){
        @Override
        protected void paintComponent(Graphics g) {
            Image image = new ImageIcon(getClass().getResource("/Img/back.jpg")).getImage();
            g.drawImage(image,0,0,width,height,this);
        }
    };

    public void flushFriendlist(List<String> list, String name) {
        if(name == null) {
            System.out.println(list);
            if (list != null && list.size() > 0) {
                for (String p : list) {
                    if (!defaultListModel.contains(p)) {
                        defaultListModel.addElement(p);
                    }
                }
            }
        }
        else{
            if(defaultListModel.contains(name))
            {
                defaultListModel.removeElement(name);
            }
            else{
                System.out.println("错误的名字");
            }
        }
    }

    private void addEvent()
    {
        friendJList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                selectname = friendJList.getSelectedValue();
            }
        });
        friendJList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 2)
                {
                    if(selectname != null) {
                        SingleFrame singleFrame = new SingleFrame(getClient(),selectname);
                    }
                    else{
                        System.out.println("双击失败");
                    }
                }
            }
        });

    }

    public void getFriendList()
    {
        ClientGetFrinedsMsg clientGetFrinedsMsg = new ClientGetFrinedsMsg();
        getClient().SendMsg(clientGetFrinedsMsg);
    }
    private void InitFrame(){
        /*DefaultMutableTreeNode root = new DefaultMutableTreeNode("我的好友",true);

        DefaultTreeModel defaultTreeModel = new DefaultTreeModel(root);
        jTree = new JTree(defaultTreeModel);*/
        defaultListModel = new DefaultListModel<>();
        getFriendList();
        friendJList = new JList<>(defaultListModel);
        friendJList.setFont(new Font("宋体", Font.PLAIN, 15));
        friendJList.setFixedCellHeight(20);
        friendJList.setBorder(BorderFactory.createTitledBorder("在线的人"));
        jScrollPane = new JScrollPane(friendJList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        /*jScrollPane = new JScrollPane(jTree);*/
        jScrollPane.setBounds(10, 100, 260, 500);
    }
    public FriendDialog(Client client)
    {
        this.client = client;
        InitFrame();
        addEvent();
        jPanel.setLayout(null);
        jPanel.add(jScrollPane);
        this.add(jPanel);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                ClientLoginoff clientLoginoff = new ClientLoginoff();
                clientLoginoff.setClientname(getClient().getName());
                getClient().SendMsg(clientLoginoff);
                ClientExitMsg clientExitMsg = new ClientExitMsg();
                getClient().SendMsg(clientExitMsg);
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }

            @Override
            public void windowIconified(WindowEvent e) {
                setVisible(false);
                setTray();
            }
        });

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((int)((d.width / 2 - width / 2)*1.8), (int)((d.height / 2 - height / 2)*0.5));
        this.setSize(width,height);
        this.setVisible(true);
    }

    private void setTray()
    {
        System.out.println("tuopan");
        /** 是否支持 托盘显示 */
        if(SystemTray.isSupported())
        {
            SystemTray tray = SystemTray.getSystemTray();
            Image image = Toolkit.getDefaultToolkit().createImage("D:\\Java-Project\\Demo01\\Img\\Img\\top.gif");

            String text = "chat";

            PopupMenu popupMenu = new PopupMenu();
            MenuItem menuItem = new MenuItem("open");
            menuItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    tray.remove(trayIcon);
                    setVisible(true);
                }
            });
            popupMenu.add(menuItem);
            trayIcon = new TrayIcon(image,text,popupMenu);
            trayIcon.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if(e.getClickCount() == 2)
                    {
                        tray.remove(trayIcon);
                        setVisible(true);
                    }
                }
            });
            try {
                tray.add(trayIcon);
            } catch (AWTException e) {
                e.printStackTrace();
            }
        }
    }
    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
