import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @Author: WSS
 * @Date:
 * @Description: 测试跟数据库建立连接
 */
public class Demo1 {
    public static void main(String[] args) {
        Connection connection = null;
        try {
            //加载驱动类
            Class.forName("com.mysql.jdbc.Driver");
            long start = System.currentTimeMillis();
            //建立连接（连接对象内部其实包含了Socket对象，是一个远程连接。比较耗时！这是Connection对象管理的一个要点！）
            //真正开发中，为了提高效率，都会使用连接池来管理连接对象！
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/testjdbc", "root", "password");
            long end = System.currentTimeMillis();
            System.out.println(connection);
            System.out.println("建立连接，耗时："+(end-start)+"ms");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }//关闭connection
        finally {
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
//运行结果：com.mysql.jdbc.JDBC4Connection@42e26948
//        建立连接，耗时：1709ms
