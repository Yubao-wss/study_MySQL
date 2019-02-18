import java.io.*;
import java.sql.*;

/**
 * @Author: WSS
 * @Date:
 * @Description: 测试BLOB大文本对象(二进制大对象）的使用
 *BLOB （ Binary Large Object)
 *  *  用于存储大量的二进制数据
 *  * Myaql中相关类型
 *  *  TINYBLOB最大长度为255（2^8-1)字符的TEXT列
 *  *  BLOB最大长度为65535（2^16-1）字符的TEXT列
 *  *  MEDIUMBLOB最大长度为16777215（2^24-1)字符的TEXT列
 *  *  LONGBLOB最大长度为4294967295或4GB（2^32-1）字符的TEXT列
 */
public class Demo2 {
    public static void main(String[] args) throws FileNotFoundException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        InputStream is = null;
        OutputStream os =null;
        try {
            //加载驱动类
            Class.forName("com.mysql.jdbc.Driver");
            //建立连接
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/testjdbc?useUnicode=true&characterEncoding=utf-8&useSSL=false", "root", "password");

            /*//插入二进制数据（图片）
            ps = connection.prepareStatement("insert into user (username,headimg) values (?,?)");
            ps.setObject(1,"wss");
            ps.setBlob(2,new FileInputStream("C:\\Users\\Administrator\\Desktop\\java_project\\大文本\\test.png"));
            */

            //读取数据
            ps = connection.prepareStatement("select * from user where id=?");
            ps.setObject(1,3);

            rs = ps.executeQuery();
            while (rs.next()){
                Blob b = rs.getBlob("headimg");
                is = b.getBinaryStream();
                os = new FileOutputStream("d:/a.png");//输出文件
                int temp = 0;
                while ((temp=is.read())!=-1){
                    os.write(temp);//写入数据
                }
            }//把图片发送到了d盘位置
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
            //关闭InputStream
            if (is != null){
                try {
                    is.close();
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
