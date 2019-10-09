package Dao;

import Msg.User;

import java.sql.SQLException;
import java.util.List;

public interface IUserDao {
    boolean InsertUser(User user);
    User FindUser(String name) throws SQLException;
    boolean DeleteUser(Integer id);
    List<User> getAllUser() throws SQLException;
}
