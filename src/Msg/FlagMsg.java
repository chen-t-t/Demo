package Msg;

import NetWork.Server;
import View.LoginDialog;

public class FlagMsg extends BaseMsg {
    private boolean flag;
    private String name;
    public FlagMsg()
    {
        flag = false;
    }
    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void doSeverthing(CloseFlag flag) {
        if (this.isFlag())
        {
            System.out.println("成功收到姓名"+ getName());
            client.start02();
        }
        else{
            LoginDialog dialog = client.getLoginDialog();
            dialog.loginfail();
        }
    }

    @Override
    public String toString() {
        return "FlagMsg{" +
                "flag=" + flag +
                '}';
    }
}
