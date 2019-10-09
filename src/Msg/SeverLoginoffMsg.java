package Msg;



import java.util.List;

public class SeverLoginoffMsg extends BaseMsg {
    private List<String> list;

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }
    @Override
    public void doSeverthing(CloseFlag flag) {
        getClient().getViewFrame().flushFriendlist(getList(),getClientname());
        getClient().getFriendDialog().flushFriendlist(getList(),null);
    }
}
