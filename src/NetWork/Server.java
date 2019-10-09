package NetWork;

import Dao.BaseDao;
import Dao.OfflineMsg;
import Dao.UserDao;
import Entity.OfflineMsgEntity;
import Msg.*;

import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

public class Server {
    private UserDao userDao;
    private static Server server;
    private static ServerSocket serverSocket;
    private List<Socket> list = new ArrayList<>();
    private ArrayList<String> loginedlist = new ArrayList<>();
    private Map<String, Socket> userList = new HashMap<>();
    private Map<String,Client> clientHashMap = new HashMap<>();
    private boolean flag;
    private Queue<BaseMsg> MsgQueue = new ArrayBlockingQueue<BaseMsg>(512);
    private int port = 8092;
    private int udpport = 8094;
    private DatagramSocket datagramSocket = null;
    private HashSet<Socket> heartlist = new HashSet<>();

    public Queue<BaseMsg> getMsgQueue() {
        return MsgQueue;
    }

    public void setMsgQueue(Queue<BaseMsg> msgQueue) {
        MsgQueue = msgQueue;
    }

    public Map<String, Client> getClientHashMap() {
        return clientHashMap;
    }

    public void setClientHashMap(Map<String, Client> clientHashMap) {
        this.clientHashMap = clientHashMap;
    }

    public HashSet<Socket> getHeartlist() {
        return heartlist;
    }

    public int getUdpport() {
        return udpport;
    }

    public void setUdpport(int udpport) {
        this.udpport = udpport;
    }

    public void setHeartlist(HashSet<Socket> heartlist) {
        this.heartlist = heartlist;
    }

    public Map<String, Socket> getUserList() {
        return userList;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setUserList(Map<String, Socket> userList) {
        this.userList = userList;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public List<Socket> getList() {
        return list;
    }

    public void setList(List<Socket> list) {
        this.list = list;
    }


    public ArrayList<String> getLoginedlist() {
        return loginedlist;
    }

    public void setLoginedlist(ArrayList<String> loginedlist) {
        this.loginedlist = loginedlist;
    }

    private Server() {
        userDao = new UserDao();
//        ReadConfigure();
       /* Timer t = new Timer();
        t.schedule(new HeartThread(), 1000, 2000);*/
    }

    public static Server getServer() {
        synchronized (Server.class) {
            if (server == null) {
                server = new Server();
            }
        }
        return server;
    }

    private class HeartThread extends TimerTask {
        @Override
        public void run() {
            if (list.size() > 0) {
                SendMsg(new SeverHeartMsg());
            }
        }
    }


    public void ReadConfigure() {
        File filep = new File(getClass().getClassLoader().getResource("").getPath());
        String sp = filep.getParent();
        File filepp = new File(sp + File.separator + "configure.properties");

        if (filepp.exists()) {
            return;
        }

        InputStream in = getClass().getResourceAsStream("/configure/configure.properties");
        Properties properties = new Properties();
        try {
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        port = Integer.valueOf(properties.getProperty("port"));

        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        File files = new File(getClass().getClassLoader().getResource("").getPath() + File.separator + "/configure/configure.properties");
        File filet = new File(getClass().getClassLoader().getResource("").getPath());
        String s = filet.getParent();
        File filed = new File(s + File.separator + "configure.properties");
        if (!filed.exists()) {
            try {
                Files.copy(files.toPath(), filed.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public DatagramSocket getDatagramSocket() {
        return datagramSocket;
    }

    public void setDatagramSocket(DatagramSocket datagramSocket) {
        this.datagramSocket = datagramSocket;
    }

    public void StartListen() {
       /* File filet = new File(getClass().getClassLoader().getResource("").getPath());
        String s = filet.getParent();
        File filed = new File(s+File.separator+"configure.properties");
        Properties properties = new Properties();
        try {
            InputStream in = new FileInputStream(filed);
            try {
                properties.load(in);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if(properties.getProperty("port") == null) {
            port = 8092;
        }
        else{
            port = Integer.valueOf(properties.getProperty("port"));
        }*/
        if (serverSocket == null) {
            try {
                System.out.println(port);
                serverSocket = new ServerSocket(port);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("绑定端口失败");
            }
        }
        new Thread(() -> {
            while (true) {
                Socket socket = null;
                try {
                    socket = serverSocket.accept();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("收到连接");
                Thread socketThread = new SocketThread(socket);
                socketThread.start();
                User user = new User();
                list.add(socket);
                heartlist.add(socket);
            }
        }).start();
    }

    public void SendMsg(BaseMsg baseMsg) {
        System.out.println(list.size());
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                Socket socket = list.get(i);
                try {
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                    objectOutputStream.writeObject(baseMsg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void SendMsgTo(Socket socket, BaseMsg baseMsg) {
        if (socket == null) {
            return;
        }
        ObjectOutputStream objectOutputStream = null;
        try {
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            objectOutputStream.writeObject(baseMsg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void delete(Socket socket) {
        for (Socket c : list) {
            if (c.equals(socket)) {
                list.remove(c);
            }
        }
    }

    class SocketThread extends Thread {
        private Socket clientsocket;

        private SocketThread(Socket socket) {
            clientsocket = socket;
        }

        @Override
        public void run() {
            CloseFlag flag = new CloseFlag();
            while (flag.isFlag()) {
                ObjectInputStream objectInputStream = null;
                try {
                    objectInputStream = new ObjectInputStream(clientsocket.getInputStream());
                    try {
                        BaseMsg msg = (BaseMsg) objectInputStream.readObject();
                        System.out.println("服务器收到" + msg);
                        msg.setSocket(clientsocket);
                        msg.doSeverthing(flag);

                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void close() {
        try {
            serverSocket.close();
            System.out.println("服务器关闭");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean FindUser(User user) {
        if (user == null) {
            return false;
        }
        try {
            User user1 = userDao.FindUser(user.getUsername());
            if (user1 != null && user1.getPassword().equals(user.getPassword())) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public int getOnlinepeople() {
        return this.getLoginedlist().size();
    }

    private SeverMsg getSeverMsg(BaseMsg baseMsg)
    {
        SeverMsg msg = new SeverMsg();
        ClientMsg clientMsg = (ClientMsg) baseMsg;
        msg.setName(clientMsg.getName());
        msg.setDate(clientMsg.getData());
        msg.setSay(clientMsg.getSay());
        return msg;
    }

    public void findUserOnline(String user,BaseMsg baseMsg)
    {
        if(loginedlist != null && loginedlist.size() > 0)
        {
            if(loginedlist.contains(user))
            {
                Client c = getClientHashMap().get(user);
                if(c != null) {
                    SeverMsg msg = getSeverMsg(baseMsg);
                    SendMsgTo(c.getSocket(), msg);
                }
                else
                {
                    System.out.println("用户不在线");
                }
            }
            else{
                //MsgQueue.add(baseMsg);
                ClientMsg clientMsg = (ClientMsg)baseMsg;
                OfflineMsgEntity offlineMsgEntity = new OfflineMsgEntity();
                offlineMsgEntity.setSay(clientMsg.getSay());
                offlineMsgEntity.setSrc_Name(clientMsg.getName());
                offlineMsgEntity.setDst_Name(clientMsg.getDstname());
                OfflineMsg offlineMsg = new OfflineMsg();
                offlineMsg.InsertMsg(offlineMsgEntity);
            }
        }
    }

}

