package Msg;

public class SeverExitMsg extends BaseMsg{
    @Override
    public void doSeverthing(CloseFlag flag) {
        flag.setFlag(false);
        getClient().Close();
    }
}
