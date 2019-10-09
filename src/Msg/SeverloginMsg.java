package Msg;

import NetWork.Server;

import java.util.ArrayList;
import java.util.List;

public class SeverloginMsg extends BaseMsg{
    private List<String> list = new ArrayList<>();

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    @Override
    public void doSeverthing(CloseFlag flag) {
        Server server = Server.getServer();
        server.getLoginedlist().add(getClientname());
        getClient().getViewFrame().flushFriendlist(getList(),null);
        getClient().getFriendDialog().flushFriendlist(getList(),null);
    }
}
