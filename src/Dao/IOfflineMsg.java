package Dao;

import Entity.OfflineMsgEntity;

import java.util.List;

public interface IOfflineMsg {
    void FindMsg(String name);
    void InsertMsg(OfflineMsgEntity offlineMsg);
    void DeleteMsg();
    List<OfflineMsgEntity> FindAllMsg(String name);
}
