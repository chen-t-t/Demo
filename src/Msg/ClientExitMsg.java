package Msg;

import NetWork.Server;

import java.io.IOException;


public class ClientExitMsg extends BaseMsg {
    @Override
    public void doSeverthing(CloseFlag flag) {
        flag.setFlag(false);
        try {
            socket.shutdownInput();
        } catch (IOException e) {
            e.printStackTrace();
        }
        SeverExitMsg severExitMsg = new SeverExitMsg();
        Server server = Server.getServer();
        server.getList().remove(socket);
        server.SendMsgTo(socket,severExitMsg);
        try {
            socket.shutdownOutput();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
