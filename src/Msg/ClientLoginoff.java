package Msg;

import NetWork.Server;

import java.util.List;

public class ClientLoginoff extends BaseMsg {
    @Override
    public void doSeverthing(CloseFlag flag) {
        Server.getServer().getList().remove(getSocket());
        Server.getServer().getLoginedlist().remove(clientname);
        Server.getServer().getClientHashMap().remove(getClientname());
        SeverLoginoffMsg severLoginoffMsg = new SeverLoginoffMsg();
        severLoginoffMsg.setClientname(getClientname());
        Server server = Server.getServer();
        List<String> list = server.getLoginedlist();
        severLoginoffMsg.setList(list);
        Server.getServer().SendMsg(severLoginoffMsg);
    }
}
