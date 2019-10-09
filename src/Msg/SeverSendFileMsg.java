package Msg;

import java.net.DatagramPacket;

public class SeverSendFileMsg extends BaseMsg{
    private byte[] bytes = null;
    private int len;
    public SeverSendFileMsg()
    {
        bytes = new byte[2048];
    }
    public SeverSendFileMsg(int num)
    {
        bytes = new byte[num];
    }
    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }

    @Override
    public void doSeverthing(CloseFlag flag) {

    }
}
