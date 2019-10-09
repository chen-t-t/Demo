
import NetWork.Server;

public class Main {
    public static void main(String[] args)
    {
        Server server = Server.getServer();
        System.out.println("服务器启动");
        server.StartListen();
    }
}
