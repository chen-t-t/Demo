package Entity;

import java.util.Date;

public class OfflineMsgEntity {
    private String src_Name;
    private String dst_Name;
    private String say;
    private Date date;

    public String getSrc_Name() {
        return src_Name;
    }

    public void setSrc_Name(String src_Name) {
        this.src_Name = src_Name;
    }

    public String getDst_Name() {
        return dst_Name;
    }

    public void setDst_Name(String dst_Name) {
        this.dst_Name = dst_Name;
    }

    public String getSay() {
        return say;
    }

    public void setSay(String say) {
        this.say = say;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
