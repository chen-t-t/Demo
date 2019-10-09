package Msg;

import NetWork.Server;

import java.util.ArrayList;

public class ClientGetFrinedsMsg extends BaseMsg{

    @Override
    public void doSeverthing(CloseFlag flag) {
        SeverSendFriendsMsg severSendFriendsMsg = new SeverSendFriendsMsg();
        ArrayList<String> list = Server.getServer().getLoginedlist();
        System.out.println("服务端收到Frineds"+list);
        severSendFriendsMsg.setList(list);
        Server server = Server.getServer();
        server.SendMsg(severSendFriendsMsg);
    }
}
