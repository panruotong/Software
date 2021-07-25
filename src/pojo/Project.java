package pojo;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class Project {
    int proid;
    String proname;
    Timestamp prostarttime;
    Timestamp proendtime;
    String prostate;
    float prorate;
    String path;
    List<Task> tasklist;
    static int projectnum=(int)Math.random()*100+5;

    public int getProid() {
        return proid;
    }

    public void setProid(int proid) {
        this.proid = proid;
    }

    public String getProname() {
        return proname;
    }

    public void setProname(String proname) {
        this.proname = proname;
    }

    public Timestamp getProstarttime() {
        return prostarttime;
    }

    public void setProstarttime(Timestamp prostarttime) {
        this.prostarttime = prostarttime;
    }

    public Timestamp getProendtime() {
        return proendtime;
    }

    public void setProendtime(Timestamp proendtime) {
        this.proendtime = proendtime;
    }

    public String getProstate() {
        return prostate;
    }

    public void setProstate(String prostate) {
        this.prostate = prostate;
    }

    public float getProrate() {
        return prorate;
    }

    public void setProrate(float prorate) {
        this.prorate = prorate;
    }

    public String getLogpath() {
        return path;
    }

    public void setLogpath(String logpath) {
        this.path = logpath;
    }

    public List<Task> getTasklist() {
        return tasklist;
    }

    public void setTasklist(List<Task> tasklist) {
        this.tasklist = tasklist;
    }

    public Project() {
    }

    public Project( String proname, Timestamp prostarttime, Timestamp proendtime, String prostate, float prorate, String logpath, List<Task> tasklist) {
        this.proid=projectnum;
        projectnum=projectnum+1;
        this.proname = proname;
        this.prostarttime = prostarttime;
        this.proendtime = proendtime;
        this.prostate = prostate;
        this.prorate = prorate;
        this.path = logpath;
        this.tasklist = tasklist;
    }
}
