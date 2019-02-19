import com.wss.testorm.JDBCUtil;

import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: WSS
 * @Date:
 * @Description: 测试使用Javabean来封装一条记录,
 *               使用List<Javabean>存储多条记录，
 *
 */
public class Demo03 {

    //用Javabean来封装一条记录
    public static void test1(){
        Connection conn = JDBCUtil.getMysqlConn();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Emp emp = null;
        try {
            ps = conn.prepareStatement("select empname,age,salary from emp where id=?");
            ps.setObject(1,1);
            rs = ps.executeQuery();

            while (rs.next()){
                emp = new Emp(rs.getString(1),rs.getInt(2),rs.getDouble(3));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtil.close(rs,ps,conn );
        }
        System.out.println(emp.getEmpname()+"---"+emp.getSalary()+"---"+emp.getAge());

    }

    //用List<Javabean>来封装多条记录
    public static void test2(){
        Connection conn = JDBCUtil.getMysqlConn();
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Emp> list = new ArrayList<Emp>();
        try {
            ps = conn.prepareStatement("select empname,age,salary from emp where id>?");
            ps.setObject(1,0);
            rs = ps.executeQuery();

            while (rs.next()){
                Emp emp = new Emp(rs.getString(1),rs.getInt(2),rs.getDouble(3));
                list.add(emp);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtil.close(rs,ps,conn );
        }
        for (Emp emp:list){
            System.out.println(emp.getEmpname()+"---"+emp.getSalary()+"---"+emp.getAge());
        }
    }
    public static void main(String[] args) {
        test1();
        //结果：
        //up1---3000.0---20

        test2();
        //结果:
        //up1---3000.0---20
        //up2---4500.0---24
        //up3---3600.0---19
    }
}









