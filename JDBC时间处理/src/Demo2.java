import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @Author: WSS
 * @Date:
 * @Description: 取出指定时间段的数据
 *
 */
public class Demo2 {
    /***
     * 将字符串代表的日期转为long数字（格式 yyyy-MM-dd hh：mm：ss）
     * @param dateStr
     * @return
     */
    public static long strToDate(String dateStr){
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            return format.parse(dateStr).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static void main(String[] args) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            //加载驱动类
            Class.forName("com.mysql.jdbc.Driver");
            //建立连接
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/testjdbc?useUnicode=true&characterEncoding=utf-8&useSSL=false", "root", "password");

            ps = connection.prepareStatement("select * from user where regTime>? and regTime<?");
            java.sql.Date start = new java.sql.Date(strToDate("2019-2-10 00:00:00"));
            java.sql.Date end = new java.sql.Date(strToDate("2019-2-20 00:00:00"));
            ps.setObject(1,start);
            ps.setObject(2,end);
            rs = ps.executeQuery();
            while (rs.next()){
                System.out.println(rs.getObject("id")+"---"+rs.getObject("username")+"---"+rs.getObject("regTime"));
            }

            ps = connection.prepareStatement("select * from user where lasttime>? and lasttime<? order by lasttime");//order by lasttime：按lasttime排序
            Timestamp start2 = new Timestamp(strToDate("2019-2-10 00:00:00"));
            Timestamp end2 = new Timestamp(strToDate("2019-2-20 00:00:00"));
            ps.setObject(1,start2);
            ps.setObject(2,end2);
            rs = ps.executeQuery();
            while (rs.next()){
                System.out.println(rs.getObject("id")+"---"+rs.getObject("username")+"---"+rs.getObject("lasttime"));
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
            //关闭ResultSet
            if (rs != null){
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
//程序运行结果：
//1---wss---2019-02-18
//2---wss---2019-02-17
//13---wss---2019-02-13
//14---wss---2019-02-11
//18---wss---2019-02-17
//19---wss---2019-02-16
//23---wss---2019-02-15
//30---wss---2019-02-11
//33---wss---2019-02-19
//35---wss---2019-02-15
//41---wss---2019-02-12
//47---wss---2019-02-11
//48---wss---2019-02-16
//49---wss---2019-02-19
//56---wss---2019-02-17
//57---wss---2019-02-11
//58---wss---2019-02-12
//68---wss---2019-02-11
//69---wss---2019-02-13
//71---wss---2019-02-17
//73---wss---2019-02-11
//75---wss---2019-02-13
//78---wss---2019-02-16
//80---wss---2019-02-15
//86---wss---2019-02-11
//89---wss---2019-02-16
//99---wss---2019-02-13
//101---wss---2019-02-10 08:24:33.0
//32---wss---2019-02-10 22:12:09.0
//73---wss---2019-02-11 02:37:47.0
//30---wss---2019-02-11 08:41:21.0
//47---wss---2019-02-11 14:17:12.0
//86---wss---2019-02-11 20:39:31.0
//68---wss---2019-02-11 20:58:22.0
//14---wss---2019-02-11 22:42:33.0
//57---wss---2019-02-11 22:51:29.0
//58---wss---2019-02-12 10:14:09.0
//41---wss---2019-02-12 16:30:55.0
//69---wss---2019-02-13 04:57:13.0
//13---wss---2019-02-13 14:19:19.0
//75---wss---2019-02-13 19:39:43.0
//99---wss---2019-02-13 21:44:35.0
//23---wss---2019-02-15 02:10:06.0
//80---wss---2019-02-15 15:27:06.0
//35---wss---2019-02-15 17:19:18.0
//19---wss---2019-02-16 01:36:03.0
//89---wss---2019-02-16 16:33:12.0
//48---wss---2019-02-16 17:55:43.0
//78---wss---2019-02-16 22:58:44.0
//71---wss---2019-02-17 05:41:48.0
//56---wss---2019-02-17 09:12:40.0
//18---wss---2019-02-17 09:48:15.0
//2---wss---2019-02-17 11:21:51.0
//1---wss---2019-02-18 17:39:00.0
//33---wss---2019-02-19 03:00:16.0
//49---wss---2019-02-19 23:48:36.0
