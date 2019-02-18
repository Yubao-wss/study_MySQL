import java.io.*;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @Author: WSS
 * @Date:
 * @Description: 测试CLOB大文本对象的使用（将字符串、文件内容插入数据库中的CLOB字段，将CLOB字段取出）
 * CLOB（Character Large Object)
 *  用于存储大量的文本数据
 * Myaql中相关类型
 *  TINYEXT最大长度为255（2^8-1)字符的TEXT列
 *  TEXT最大长度为65535（2^16-1）字符的TEXT列
 *  MEDIUMTEXT最大长度为16777215（2^24-1)字符的TEXT列
 *  LONGTEXT最大长度为4294967295或4GB（2^32-1）字符的TEXT列
 *
 * 大字段有些特殊，不同数据库处理的方式不一样，大字段的操作常常是[以流的方式]来处理的，而非一般的字段，一次即可读出数据
 *
 *
 *
 */
public class Demo1 {
    public static void main(String[] args) throws FileNotFoundException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Reader r = null;
        try {
            //加载驱动类
            Class.forName("com.mysql.jdbc.Driver");
            //建立连接
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/testjdbc?useUnicode=true&characterEncoding=utf-8&useSSL=false", "root", "password");

            //插入大文本数据
           /* ps = connection.prepareStatement("insert into user (username,info) values (?,?)");
            ps.setObject(1,"wss");
            //将文件内容直接输入到数据库中
            //ps.setClob(2,new FileReader("C:\\Users\\Administrator\\Desktop\\java_project\\大文本\\a.txt"));
            //将程序中的字符串输入到数据库的CLOB字段中
            ps.setClob(2,new BufferedReader(new InputStreamReader(new ByteArrayInputStream("hello".getBytes()))));*/

            //读取大文本数据
            ps = connection.prepareStatement("select * from user where id=?");
            ps.setObject(1,1);

            rs = ps.executeQuery();
            while (rs.next()){
                Clob c = rs.getClob("info");
                r = c.getCharacterStream();
                int temp = 0;
                while ((temp=r.read())!=-1){
                    System.out.print((char)temp);
                }
            }//运行结果：Hello mysql��

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            try {
                connection.rollback();//异常情况，回滚
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭Reader
            if (r != null){
                try {
                    r.close();
                } catch (Exception e) {
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
