package NetWork;

import Msg.BaseMsg;
import Msg.ClientLoginMsg;
import Msg.CloseFlag;
import View.FriendDialog;
import View.LoginDialog;
import View.SingleFrame;
import View.ViewFrame;

import javax.swing.*;
import java.io.*;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.SocketException;


public class Client {
    private Socket socket = null;
    private FriendDialog friendDialog = null;
    private SingleFrame singleFrame = null;
    private ViewFrame viewFrame = null;
    private boolean flag = true;
    private LoginDialog loginDialog = null;
    private String name = null;
    private DatagramSocket datagramSocket = null;
    private int port;
    public Client() {
        loginDialog = new LoginDialog(this);
    }

    public DatagramSocket getDatagramSocket() {
        return datagramSocket;
    }

    public void setDatagramSocket(DatagramSocket datagramSocket) {
        this.datagramSocket = datagramSocket;
    }

    public Socket Connect()
    {
        try {
            socket = new Socket("localhost",8092);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "网络错误", "错误", JOptionPane.ERROR_MESSAGE);
            System.out.println("客户端连接失败");
            setFlag(false);
        }
        this.setFlag(true);
        return getSocket();
    }

    public LoginDialog getLoginDialog() {
        return loginDialog;
    }

    public void setLoginDialog(LoginDialog loginDialog) {
        this.loginDialog = loginDialog;
    }

    public ViewFrame getViewFrame() {
        return viewFrame;
    }

    public void setViewFrame(ViewFrame viewFrame) {
        this.viewFrame = viewFrame;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SingleFrame getSingleFrame() {
        return singleFrame;
    }

    public void setSingleFrame(SingleFrame singleFrame) {
        this.singleFrame = singleFrame;
    }

    public FriendDialog getFriendDialog() {
        return friendDialog;
    }

    public void setFriendDialog(FriendDialog friendDialog) {
        this.friendDialog = friendDialog;
    }

    /**
     * 向服务端发送消息
     * */
    public void SendMsg(BaseMsg b) {
        if(!this.isFlag())
        {
            return;
        }
        System.out.println(b);
        ObjectOutputStream objectOutputStream = null;
        try {
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(b);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 接受服务端消息
     * */
    class ReciveMsgThread extends Thread {
        Client client;
        public ReciveMsgThread(Client c)
        {
            client = c;
        }
        @Override
        public void run() {
            if(!client.isFlag())
            {
                return;
            }
            CloseFlag flag = new CloseFlag();
            while (flag.isFlag()) {
                try {
                    ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                    try {
                        BaseMsg baseMsg = (BaseMsg) objectInputStream.readObject();
                        baseMsg.setSocket(socket);
                        baseMsg.setClient(client);
                        baseMsg.doSeverthing(flag);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println();
            }
        }
    }

    public void ReciveMsg() {
        Thread c = new ReciveMsgThread(this);
        c.start();
    }

    public void start01()
    {
        loginDialog.Close();
        viewFrame = new ViewFrame(this);
        ClientLoginMsg clientLoginMsg = new ClientLoginMsg();
        clientLoginMsg.setClientname(getName());
        SendMsg(clientLoginMsg);
    }
    public void start02()
    {
        loginDialog.Close();
        friendDialog = new FriendDialog(this);
        /*singleFrame = new SingleFrame(this);
        ClientLoginMsg clientLoginMsg = new ClientLoginMsg();
        clientLoginMsg.setClientname(getName());
        SendMsg(clientLoginMsg);*/
    }

    public void Close()
    {
        try {
            socket.shutdownInput();
            socket.shutdownOutput();
            socket.close();
        } catch (IOException e) {
            System.out.println("关闭socket");
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        Client client = new Client();
        Client client1 = new Client();
    }
}
