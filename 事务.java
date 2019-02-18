import java.sql.*;

/**
 * @Author: WSS
 * @Date:
 * @Description: 测试事务的基本用法
 */
public class Demo6 {
    public static void main(String[] args) {
        Connection connection = null;
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        try {
            //加载驱动类
            Class.forName("com.mysql.jdbc.Driver");
            //建立连接
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/testjdbc?useUnicode=true&characterEncoding=utf-8&useSSL=false", "root", "password");

            connection.setAutoCommit(false);//JDBC中默认是true，自动提交事务

            ps1 = connection.prepareStatement("insert into user (username,pwd) values (?,?)");
            ps1.setObject(1,"wss");
            ps1.setObject(2,"123");
            ps1.execute();
            System.out.println("插入一个用户1");


            try {
                Thread.sleep(6000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            ps2 = connection.prepareStatement("insert into user (username,pwd) values (?,?,?)");//此处写三个？，致使出错，以检验事务的特性
            ps2.setObject(1,"pyy");
            ps2.setObject(2,"123");
            ps2.execute();
            System.out.println("插入一个用户2");

            connection.commit();//最后手动提交
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            try {
                connection.rollback();//异常情况，回滚
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {

            //关闭PreparedStatement
            if (ps1 != null){
                try {
                    ps1.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (ps2 != null){
                try {
                    ps2.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            //关闭connection
            if (connection != null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
//程序输出结果：插入一个用户1
//           后面报错
//数据库表中没有增加任何内容，验证了事务的特性


