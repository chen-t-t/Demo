package Dao;

import Entity.OfflineMsgEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OfflineMsg implements IOfflineMsg {
    private BaseDao baseDao = null;
    private List<OfflineMsgEntity> list = null;

    public OfflineMsg() {
        baseDao = new BaseDao();
    }

    @Override
    public void FindMsg(String name) {

    }

    @Override
    public void InsertMsg(OfflineMsgEntity offlineMsg) {
        if (offlineMsg == null) {
            return;
        }
        String sql = "insert into offlinemsg(srcName,dstName,say) values(?,?,?)";
        String[] paras = {offlineMsg.getSrc_Name(), offlineMsg.getDst_Name(), offlineMsg.getSay()};
        try {
            baseDao.doInsert(sql, paras);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void DeleteMsg() {

    }

    @Override
    public List<OfflineMsgEntity> FindAllMsg(String name) {
        list = new ArrayList<>();
        String sql = "select * from offlinemsg where dstName == ?";
        String[] paras = {name};
        ResultSet rs = BaseDao.doQuery(sql, paras);
        try {
            while (rs.next()) {
                OfflineMsgEntity off = new OfflineMsgEntity();
                off.setSrc_Name(rs.getString("srcName"));
                off.setDst_Name(rs.getString("dstName"));
                off.setSay(rs.getString("say"));
                list.add(off);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
