package Dao;

import Msg.User;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDao implements IUserDao {
    private BaseDao baseDao = null;
    private List<User> UserList = null;
    public UserDao()
    {
        baseDao = new BaseDao();
    }
    @Override
    public boolean InsertUser(User user){
        if(user == null)
        {
            return false;
        }
        String sql = "insert into user(name,password) values(?,?)";
        String[] paras = {user.getUsername(),user.getPassword()};
        try {
            baseDao.doInsert(sql,paras);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public User FindUser(String name) throws SQLException {
        String sql = "select * from user where name = ?";
        String[] args = {name};
        ResultSet resultSet = BaseDao.doQuery(sql,args);
        User user = null;
        if(resultSet.next()) {
            user = new User();
            String tname = resultSet.getString("name");
            String tpassword = resultSet.getString("password");
            user.setPassword(tpassword);
            user.setUsername(tname);
        }
        return user;
    }

    @Override
    public boolean DeleteUser(Integer id) {
        return false;
    }

    @Override
    public List<User> getAllUser() throws SQLException {
        String sql = "select * from user";
        ResultSet resultSet = BaseDao.doQuery(sql);
        while (resultSet.next()) {
            User user = new User();
            String tname = resultSet.getString("name");
            String tpassword = resultSet.getString("password");
            user.setPassword(tpassword);
            user.setUsername(tname);
            UserList.add(user);
        }
        return getUserList();
    }

    public List<User> getUserList() {
        return UserList;
    }

    public void setUserList(List<User> userList) {
        UserList = userList;
    }

    @Test
    public void TestConnection()
    {
        Connection connection = BaseDao.getConnection();
        System.out.println(BaseDao.getConnection());
        UserDao userDao = new UserDao();
        User user = new User();
        user.setUsername("李焕");
        user.setPassword("123456");
        userDao.InsertUser(user);
    }
}
