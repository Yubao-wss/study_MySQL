import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @Author: WSS
 * @Date:
 * @Description: 测试Statement的基本用法,执行SQL语句，以及SQL注入问题
 */
public class Demo2 {
    public static void main(String[] args) {
        Connection connection = null;
        Statement statement = null;
        try {
            //加载驱动类
            Class.forName("com.mysql.jdbc.Driver");
            //建立连接
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/testjdbc?useUnicode=true&characterEncoding=utf-8&useSSL=false", "root", "password");
            //SQL注入
            statement = connection.createStatement();
            String name = "sqs";
            String sql = "insert into user (username,pwd,regTime) values ('"+name+"',123,now())";
            String sql3 = "insert into user (username,pwd,regTime) values ('wss',123,now())";
            statement.execute(sql);
            statement.execute(sql3);

            //执行此语句会删除表中所有数据，若String id来源于外部，则可随意修改数据库，故有风险，一般不使用statement
            String id = "5 or 1=1";
            String sql2 = "delete from user where id="+id;
            statement.execute(sql2);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            //关闭statement
            if (statement != null){
                try {
                    statement.close();
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


