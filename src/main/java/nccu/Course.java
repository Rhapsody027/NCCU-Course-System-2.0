package nccu;

public class Course {
    int sweet, cool;
    String name, pro, time, att;

    public Course(int sweet, int cool, String name, String pro, String time, String att) {
        this.sweet = sweet;
        this.cool = cool;
        this.name = name;
        this.pro = pro;
        this.time = time;
        this.att = att;
    }

    public int getSweet() {
        return sweet;
    }

    public void setSweet(int sweet) {
        this.sweet = sweet;
    }

    public int getCool() {
        return cool;
    }

    public void setCool(int cool) {
        this.cool = cool;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPro() {
        return pro;
    }

    public void setPro(String pro) {
        this.pro = pro;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAtt() {
        return att;
    }

    public void setAtt(String att) {
        this.att = att;
    }
}