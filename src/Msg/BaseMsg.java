package Msg;

import NetWork.Client;

import java.io.Serializable;
import java.net.Socket;

public abstract class BaseMsg implements Serializable {
    protected Socket socket;
    protected Client client;
    protected String clientname;
    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public String getClientname() {
        return clientname;
    }

    public void setClientname(String clientname) {
        this.clientname = clientname;
    }

    public abstract void doSeverthing(CloseFlag flag);

    public void doClientthing(){}
}
