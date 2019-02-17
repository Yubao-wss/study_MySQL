import java.sql.*;

/**
 * @Author: WSS
 * @Date:
 * @Description: 测试批处理的基本用法
 * 灵活指定SQL语句中的变量
 *  PreparedStatement
 * 批处理
 *  Bath
 * 对于大量的批处理，建议使用Statement,因为PreparedStatement的预编译空间有限，数据特别大时会发生异常
 * 注意：还需设置手动提交
 */
public class Demo5 {
    public static void main(String[] args) {
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            //加载驱动类
            Class.forName("com.mysql.jdbc.Driver");
            //建立连接
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/testjdbc?useUnicode=true&characterEncoding=utf-8&useSSL=false", "root", "password");

            connection.setAutoCommit(false);//设为手动提交

            long start = System.currentTimeMillis();
            stmt = connection.createStatement();
            //批处理
            for (int i = 0;i<2000;i++){
                stmt.addBatch("insert into user (username,pwd,regTime) values ('wss"+i+"',666,now())");
            }
            //执行
            stmt.executeBatch();

            connection.commit();//提交事务
            long end = System.currentTimeMillis();
            System.out.println("插入2000条数据，耗时"+(end-start)+"ms");

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
            //关闭Statement
            if (stmt != null){
                try {
                    stmt.close();
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



