package Msg;

import NetWork.Client;
import NetWork.Server;

import java.util.ArrayList;
import java.util.List;

public class SeverSendFriendsMsg extends BaseMsg{
    private ArrayList<String> list = new ArrayList<>();
    public List<String> getList() {
        return list;
    }

    public void setList(ArrayList<String> list) {
        this.list = list;
    }
    @Override
    public void doSeverthing(CloseFlag flag) {
        getClient().getFriendDialog().flushFriendlist(list,null);
    }
}
