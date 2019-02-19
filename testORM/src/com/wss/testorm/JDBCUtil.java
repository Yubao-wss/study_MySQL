package com.wss.testorm;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/**
 * @Author: WSS
 * @Date:
 * @Description: 使用JDBCUtil工具来简化JDBC开发
 */
public class JDBCUtil {

    static Properties pros = null; //可以帮助读取和处理资源文件中的信息

    static {
        pros = new Properties();
        try {
            pros.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("com/wss/testorm/db.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }//加载JDBCUtil类的时候调用

    /**
     * 功能描述
     * @param: 加载驱动类、建立连接
     * @return: Connection对象
     * @author:
     * @date:
     */
    public static Connection getMysqlConn(){
        try {
            Class.forName(pros.getProperty("mysqlDriver"));
            return DriverManager.getConnection(pros.getProperty("mysqlURL"),pros.getProperty("mysqlUser"),pros.getProperty("mysqlPwd"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 功能描述
     * @param: 先后关闭ResultSet、Statement、Connection接口
     * @return: void
     * @author:
     * @date:
     */
    public static void close(ResultSet rs, Statement ps,Connection conn){
        //关闭ResultSet
        if (rs != null){
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        //关闭PreparedStatement
        if (ps != null){
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        //关闭connection
        if (conn != null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
    //测试
    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        //J建立连接
        conn = JDBCUtil.getMysqlConn();

        //业务内容
        try {
            ps = conn.prepareStatement("select id,username from user where username = ?");
            ps.setObject(1,"wss");
            rs = ps.executeQuery();
            while (rs.next()){
                System.out.println(rs.getObject(1)+"---"+rs.getObject(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        //关闭
        JDBCUtil.close(rs,ps,conn);
    }
}
//运行结果：
//1---wss
//2---wss
//3---wss
//4---wss
