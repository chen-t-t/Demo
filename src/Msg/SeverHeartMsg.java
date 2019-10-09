package Msg;


public class SeverHeartMsg extends BaseMsg {
    @Override
    public void doSeverthing(CloseFlag flag) {
        ClientHeartMsg clientHeartMsg = new ClientHeartMsg();
        getClient().SendMsg(clientHeartMsg);
    }
}
