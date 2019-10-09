package Util;

import Msg.ClientFileSendMsg;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;

public class FileUtil {

    public static void ReadFile(DatagramSocket datagramSocket,File file) throws IOException {
        byte[] bytes = new byte[1024];

        InputStream inputStream = new FileInputStream(file);
        inputStream.read(bytes,0,bytes.length);
        while(inputStream.read(bytes) != -1)
        {
            DatagramPacket d = new DatagramPacket(bytes,bytes.length, InetAddress.getByName("localhost"),8094);
            datagramSocket.send(d);
        }
        datagramSocket.close();
        //datagramSocket.close();
    }

    public static boolean IsFile(File f, String filePath)
    {
        if(f == null)
        {
            return false;
        }
        if(filePath.equals(f.getAbsolutePath()))
        {
            return true;
        }
        return false;
    }

    public boolean IsFile(ArrayList<String> filelist)
    {
        return false;
    }
    public static int ReadFile(String filePath)
    {
        return 1;
    }
}
