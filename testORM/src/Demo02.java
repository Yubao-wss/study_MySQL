import com.wss.testorm.JDBCUtil;

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
 * @Description: 测试使用Map来封装一条记录,
 *               使用List<Map>，Map<Map>存储多条记录，
 *
 */
public class Demo02{

    //Map存一条
    public static void test01(){
        Connection conn = JDBCUtil.getMysqlConn();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Map<String,Object> row = new HashMap<String, Object>();//使用一个map封装一条记录
        try {
            ps = conn.prepareStatement("select empname,salary,age from emp where id>?");
            ps.setObject(1,0);
            rs = ps.executeQuery();

            while (rs.next()){
                row.put("empname",rs.getObject(1));
                row.put("salary",rs.getObject(2));
                row.put("age",rs.getObject(3));


            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtil.close(rs,ps,conn );
        }
        //遍历Map对象
        for (String key:row.keySet()){
            System.out.print(key+"---"+row.get(key)+"\t");

        }

    }

    //List<Map>存多条
    public static void test02(){
        Connection conn = JDBCUtil.getMysqlConn();
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Map<String,Object>> list = new ArrayList<Map<String, Object>>();
        try {
            ps = conn.prepareStatement("select empname,salary,age from emp where id>?");
            ps.setObject(1,0);
            rs = ps.executeQuery();

            while (rs.next()){
                Map<String,Object> row = new HashMap<String, Object>();//使用一个map封装一条记录
                row.put("empname",rs.getObject(1));
                row.put("salary",rs.getObject(2));
                row.put("age",rs.getObject(3));

                list.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtil.close(rs,ps,conn );
        }

        for (Map<String,Object> row:list){
            for (String key:row.keySet()){
                System.out.print(key+"---"+row.get(key)+"\t");
            }
            System.out.println();
        }

    }

    //Map<Map>存多条
    public static void test03(){
        Connection conn = JDBCUtil.getMysqlConn();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Map<String,Map<String,Object>> maps = new HashMap<String,Map<String,Object>>();
        try {
            ps = conn.prepareStatement("select empname,salary,age from emp where id>?");
            ps.setObject(1,0);
            rs = ps.executeQuery();

            while (rs.next()){
                Map<String,Object> row = new HashMap<String, Object>();//使用一个map封装一条记录
                row.put("empname",rs.getObject(1));
                row.put("salary",rs.getObject(2));
                row.put("age",rs.getObject(3));

                maps.put(rs.getString(1),row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtil.close(rs,ps,conn );
        }

        for (String empname:maps.keySet()){
            Map<String,Object> row = maps.get(empname);
            for (String key:row.keySet()){
                System.out.print(key+"---"+row.get(key)+"\t");
            }
            System.out.println();
        }

    }
    public static void main(String[] args) {
        test01();
        //运行结果：
        //empname---up3	salary---3600.0	age---19

        test02();
        //运行结果：
        //empname---up1	salary---3000.0	age---20
        //empname---up2	salary---4500.0	age---24
        //empname---up3	salary---3600.0	age---19

        test03();
        //运行结果：
        //empname---up2	salary---4500.0	age---24
        //empname---up1	salary---3000.0	age---20
        //empname---up3	salary---3600.0	age---19
    }
}

