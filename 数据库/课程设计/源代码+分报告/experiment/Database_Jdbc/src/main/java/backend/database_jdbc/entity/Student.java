package backend.database_jdbc.entity;

import java.util.Date;

public class Student {
    private String sid;           // 学号
    private String name;          // 姓名
    private String gender;        // 性别
    private String majorClass;    // 专业班级
    private Date birth;           // 出生日期
    private String phone;         // 电话号码

    public Student() {}

    public Student(String sid, String name, String gender, String majorClass, String birth, String phone) {
        this.sid = sid;
        this.name = name;
        this.gender = gender;
        this.majorClass = majorClass;
        this.birth = java.sql.Date.valueOf(birth);
        this.phone = phone;
    }

    // Getter和Setter方法
    public String getSid() { return sid; }
    public void setSid(String sid) { this.sid = sid; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getMajorClass() { return majorClass; }
    public void setMajorClass(String majorClass) { this.majorClass = majorClass; }

    public Date getBirth() { return birth; }
    public void setBirth(Date birth) { this.birth = birth; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    @Override
    public String toString() {
        return "Student{" +
                "sid='" + sid + '\'' +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", majorClass='" + majorClass + '\'' +
                ", birth=" + birth +
                ", phone='" + phone + '\'' +
                '}';
    }
}
