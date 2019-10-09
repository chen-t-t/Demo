package Msg;


import NetWork.Server;

import java.io.File;

public class ClientMsg extends BaseMsg {
    private String name;
    private String say;
    private String data;
    private File file;
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSay() {
        return say;
    }

    public void setSay(String say) {
        this.say = say;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    @Override
    public void doSeverthing(CloseFlag flag) {
        SeverMsg severMsg = new SeverMsg();

        severMsg.setName(getName());
        severMsg.setSay(getSay());
        severMsg.setDate(getData());
        System.out.println(file.getName());
        Server server = Server.getServer();
        server.SendMsg(severMsg);
    }

    @Override
    public String toString() {
        return "ClientMsg{" +
                "name='" + name + '\'' +
                ", say='" + say + '\'' +
                '}';
    }
}
