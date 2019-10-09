package Dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class BaseDao {
    private static String url = "jdbc:mysql://localhost:3306/demo?useUnicode=true&characterEncoding=utf-8&useSSL=false";
    private static String username = "root";
    private static String password = "";
    private static String classname = "com.mysql.jdbc.Driver";

    public void GetResource()
    {
        InputStream in = getClass().getResourceAsStream("/configure/configure.properties");
        Properties properties = new Properties();
        try {
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String username = properties.getProperty("username");
        String password = properties.getProperty("password");
        BaseDao.username = username;
        BaseDao.password = password;
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection()
    {
        Connection connection = null;
        try {
            Class.forName(classname);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(url,username,password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
    /**
     * 查询可动态配置
     * */
    public static ResultSet doQuery(String sql,String[] args)
    {
        Connection  connection = getConnection();
        if(connection == null)
        {
            return null;
        }
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            if(args != null)
            {
                int index = 1;
                for (String arg:args){
                    preparedStatement.setString(index++,arg);
                }
            }
            rs = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }
    /**
     * 查询所有结果集
     * */
    public static ResultSet doQuery(String sql)
    {
        Connection  connection = getConnection();
        if(connection == null)
        {
            return null;
        }
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            rs = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public boolean doInsert(String sql,String args[]) throws SQLException {
        Connection  connection = getConnection();
        if(connection == null)
        {
            return false;
        }
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(args != null)
        {
            int index = 1;
            for (String arg:
                args) {
                try {
                    preparedStatement.setString(index++,arg);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }finally {
            doClose(connection,rs,preparedStatement);
        }
        return true;
    }
    private static void doClose(Connection c, ResultSet rs, PreparedStatement ps) throws SQLException {
        if(ps != null)
        {
            c.close();
        }
        if(rs != null)
        {
            rs.close();
        }
        if(c != null)
        {
            c.close();
        }
    }

    public static int doUpdate(String sql,String[] args) throws SQLException {
        Connection  connection = getConnection();
        PreparedStatement preparedStatement = null;
        int rs = -1;
        try {
            preparedStatement = connection.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(args != null)
        {
            int index = 1;
            for (String arg: args) {
                try {
                    preparedStatement.setString(index++,arg);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        try {
             rs = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            doClose(connection,null,preparedStatement);
        }
        return rs;
    }
}
