package Msg;

import NetWork.Server;

public class User extends BaseMsg {
    private String username;
    private String password;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public void doSeverthing(CloseFlag closeFlag) {
        FlagMsg flagMsg = new FlagMsg();
        flagMsg .setName(getUsername());
        if(Server.getServer().FindUser(this)){
            System.out.println("找到"+ getUsername());
            flagMsg.setFlag(true);
            Server server = Server.getServer();
            server.getLoginedlist().add(getUsername());
            server.getClientHashMap().put(getUsername(),socket);
        }
        Server.getServer().SendMsgTo(socket,flagMsg);
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
