package backend.database_jdbc.entity;


public class Homework {
    private String cid;         // 课程ID
    private String sid;         // 学生ID
    private double grade1;      // 作业1成绩
    private double grade2;      // 作业2成绩
    private double grade3;      // 作业3成绩

    // 构造函数
    public Homework(String cid, String sid, double grade1, double grade2, double grade3) {
        this.cid = cid;
        this.sid = sid;
        this.grade1 = grade1;
        this.grade2 = grade2;
        this.grade3 = grade3;
    }

    public String getCid() { return cid; }
    public void setCid(String cid) { this.cid = cid; }

    public String getSid() { return sid; }
    public void setSid(String sid) { this.sid = sid; }

    public double getGrade1() { return grade1; }
    public void setGrade1(double grade1) { this.grade1 = grade1; }

    public double getGrade2() { return grade2; }
    public void setGrade2(double grade2) { this.grade2 = grade2; }

    public double getGrade3() { return grade3; }
    public void setGrade3(double grade3) { this.grade3 = grade3; }

    @Override
    public String toString() {
        return "Homework{" +
                "cid='" + cid + '\'' +
                ", sid='" + sid + '\'' +
                ", grade1=" + grade1 +
                ", grade2=" + grade2 +
                ", grade3=" + grade3 +
                '}';
    }
}

