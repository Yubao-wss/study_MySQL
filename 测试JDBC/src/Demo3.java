import java.sql.*;

/**
 * @Author: WSS
 * @Date:
 * @Description: 测试PreparedStatement的基本用法
 */
public class Demo3 {
    public static void main(String[] args) {
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            //加载驱动类
            Class.forName("com.mysql.jdbc.Driver");
            //建立连接
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/testjdbc?useUnicode=true&characterEncoding=utf-8&useSSL=false", "root", "password");

            String sql = "insert into user (username ,pwd) values (?,?)";//?是占位符，可以防止sql注入
            ps = connection.prepareStatement(sql);
            ps.setString(1,"wss");
            ps.setObject(2,"789");//传入数据

            ps.execute();//执行
            System.out.println("插入一行记录");
            int count = ps.executeUpdate();
            System.out.println(count);//结果是1

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            //关闭PreparedStatement
            if (ps != null){
                try {
                    ps.close();
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


