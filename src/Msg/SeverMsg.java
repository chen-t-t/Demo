package Msg;


public class SeverMsg extends BaseMsg {
    private String name;
    private String say;
    private String date;
    private String dstname;

    public String getDstname() {
        return dstname;
    }

    public void setDstname(String dstname) {
        this.dstname = dstname;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    @Override
    public void doSeverthing(CloseFlag flag) {
        System.out.println("Client"+client);
        if(dstname != null)
        {
            client.getSingleFrame().setVisible(true);
            client.getSingleFrame().getjTextArea01().append(this.getName()+" "+ this.getDate()+"\n");
            client.getSingleFrame().getjTextArea01().append("    "+this.getSay()+"\n");
        }
        else {
            client.getViewFrame().getjTextArea01().append(this.getName() + " " + this.getDate() + "\n");
            client.getViewFrame().getjTextArea01().append("    " + this.getSay() + "\n");
        }
    }

    @Override
    public String toString() {
        return "SeverMsg{" +
                "name='" + name + '\'' +
                ", say='" + say + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
