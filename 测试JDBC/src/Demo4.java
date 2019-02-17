import java.sql.*;

/**
 * @Author: WSS
 * @Date:
 * @Description: 测试ResultSet的基本用法
 */
public class Demo4 {
    public static void main(String[] args) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            //加载驱动类
            Class.forName("com.mysql.jdbc.Driver");
            //建立连接
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/testjdbc?useUnicode=true&characterEncoding=utf-8&useSSL=false", "root", "password");

            String sql = "select id,username,pwd from user where id>?";
            ps = connection.prepareStatement(sql);

            //把id大于2的记录取出来
            ps.setObject(1,2);
            rs = ps.executeQuery();

            while (rs.next()){
                System.out.println(rs.getObject(1)+"---"+rs.getObject(2)+"---"+rs.getObject(3));
            }
            //结果：
            //3---wss---789
            //4---wss---789


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            //ResultSet--->Statement--->Connection（先建后关）,三个try/catch分开写
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



