import java.sql.Date;

/**
 * @Author: WSS
 * @Date:
 * @Description: 让将表中一条记录封装到javabean对象中
 */
public class Emp {  //表结构和类对应
    private Integer id;
    private String empname;
    private Integer age;
    private Double salary;
    private Date birthday;
    private Integer deptId;

    public Emp(String empname, Integer age, Double salary) {
        super();
        this.empname = empname;
        this.age = age;
        this.salary = salary;
    }

    public Emp(Integer id, String empname, Integer age, Double salary, Date birthday, Integer deptId) {
        this.id = id;
        this.empname = empname;
        this.age = age;
        this.salary = salary;
        this.birthday = birthday;
        this.deptId = deptId;
    }

    public Emp(String empname, Integer age, Double salary, Date birthday, Integer deptId) {
        this.empname = empname;
        this.age = age;
        this.salary = salary;
        this.birthday = birthday;
        this.deptId = deptId;
    }

    public Emp() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmpname() {
        return empname;
    }

    public void setEmpname(String empname) {
        this.empname = empname;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Integer getDeptid() {
        return deptId;
    }

    public void setDeptid(Integer deptId) {
        this.deptId = deptId;
    }
}
