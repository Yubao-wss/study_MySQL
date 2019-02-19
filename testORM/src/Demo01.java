import com.wss.testorm.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.lang.Object;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: WSS
 * @Date:
 * @Description: 测试使用Object[]来封装一条记录,使用ListList<Object[]>存储多条记录
 *
 * ORM(Object Relationship Mapping)的基本思想
 *  【表结构跟类对应；表中字段和属性对应；表中记录和对象对应】
 *  一条记录对应一个对象。将这些查询到的对象放到容器中（List、Set、Map)
 */
public class Demo01 {
    public static void main(String[] args) {
        Connection conn = JDBCUtil.getMysqlConn();
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Object[]> list = new ArrayList<Object[]>();
        try {
            ps = conn.prepareStatement("select empname,salary,age from emp where id>?");
            ps.setObject(1,0);
            rs = ps.executeQuery();

            while (rs.next()){
            //    System.out.println(rs.getObject(1)+"---"+rs.getObject(2)+"---"+rs.getObject(3));
            //运行结果：up1---3000.0---20
                //将数据存入数组中
                Object[] objs = new Object[3];  //一个Object数组封装了一条记录的消息
                objs[0] = rs.getObject(1);
                objs[1] = rs.getObject(2);
                objs[2] = rs.getObject(3);

                list.add(objs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtil.close(rs,ps,conn );
        }
        for (Object[] objs:list){
            System.out.println(""+objs[0]+objs[1]+objs[2]);
            //运行结果：
            //up13000.020
            //up24500.024
            //up33600.019
        }

    }
}
