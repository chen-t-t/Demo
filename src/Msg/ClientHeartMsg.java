package Msg;

import NetWork.Server;

public class ClientHeartMsg extends BaseMsg {
    @Override
    public void doSeverthing(CloseFlag flag) {
        Server.getServer().getHeartlist().remove(this.socket);
    }
}
