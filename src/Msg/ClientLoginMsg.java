package Msg;

import NetWork.Server;

public class ClientLoginMsg extends BaseMsg{
    @Override
    public void doSeverthing(CloseFlag flag) {
        SeverloginMsg severloginMsg = new SeverloginMsg();
        severloginMsg.setList(Server.getServer().getLoginedlist());
        severloginMsg.setClientname(getClientname());
        Server.getServer().SendMsg(severloginMsg);
    }
}
