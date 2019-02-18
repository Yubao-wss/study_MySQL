import java.sql.*;
import java.util.Random;

/**
 * @Author: WSS
 * @Date:
 * @Description: 测试时间处理(java.sql.Date,Time,Timestamp)
 * java.util.Date
 *  子类：java.sql.Date              表示年月日
 *  子类：java.sql.Time              表示时分秒
 *  子类：java.sql.Timestamp         表示年月日时分秒
 *
 * 日期比较处理
 *  插入随机日期
 *  取出指定日期范围的记录
 */
public class Demo1 {
    public static void main(String[] args) {
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            //加载驱动类
            Class.forName("com.mysql.jdbc.Driver");
            //建立连接
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/testjdbc?useUnicode=true&characterEncoding=utf-8&useSSL=false", "root", "password");

            for (int i=0;i<100;i++){
                ps = connection.prepareStatement("insert into user (username,pwd,regTime,lasttime) values (?,?,?,?)");
                ps.setObject(1,"wss");
                ps.setObject(2,"123");

                int rand = new Random().nextInt();//随机数

                Date date = new Date(System.currentTimeMillis()-rand);
                ps.setObject(3,date);

                Timestamp stamp = new Timestamp(System.currentTimeMillis()-rand);//如果需要插入指定日期，可以使用Calendar、DateFormat
                ps.setObject(4,stamp);


                ps.execute();
                System.out.println("插入一个用户"+i+1);
            }





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

