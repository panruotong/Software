package pojo;

import java.sql.Timestamp;
import java.util.Date;

public class Task {
    int taskid;
    String taskname;
    int proid;
    int taskorder;
    Date  taskstarttime;
    Date taskendtime;
    String taskstate;
    float taskrate;
    int fatherid;
    int workerid;
    int entrust;
    int postworkerid;
    Timestamp entrusttime;
    static int tasknum=(int)Math.random()*100+10;

    public int getTaskid() {
        return taskid;
    }

    public void setTaskid(int taskid) {
        this.taskid = taskid;
    }

    public String getTaskname() {
        return taskname;
    }

    public void setTaskname(String taskname) {
        this.taskname = taskname;
    }

    public int getProid() {
        return proid;
    }

    public void setProid(int proid) {
        this.proid = proid;
    }

    public int getTaskOrder() {
        return taskorder;
    }

    public void setTaskOrder(int taskorder) {
        this.taskorder = taskorder;
    }

    public Date getTaskstarttime() {
        return taskstarttime;
    }

    public void setTaskstarttime(Date taskstarttime) {
        this.taskstarttime = taskstarttime;
    }

    public Date getTaskendtime() {
        return taskendtime;
    }

    public void setTaskendtime(Date taskendtime) {
        this.taskendtime = taskendtime;
    }

    public String getTaskstate() {
        return taskstate;
    }

    public void setTaskstate(String taskstate) {
        this.taskstate = taskstate;
    }

    public float getTaskrate() {
        return taskrate;
    }

    public void setTaskrate(float taskrate) {
        this.taskrate = taskrate;
    }

    public int getFatherid() {
        return fatherid;
    }

    public void setFatherid(int fatherid) {
        this.fatherid = fatherid;
    }

    public int getWorkerid() {
        return workerid;
    }

    public void setWorkerid(int workerid) {
        this.workerid = workerid;
    }

    public Task() {
    }

    public int getEntrust() {
        return entrust;
    }

    public void setEntrust(int entrust) {
        this.entrust = entrust;
    }

    public int getPostworkerid() {
        return postworkerid;
    }

    public void setPostworkerid(int postworkerid) {
        this.postworkerid = postworkerid;
    }

    public Timestamp getEntrusttime() {
        return entrusttime;
    }

    public void setEntrusttime(Timestamp entrusttime) {
        this.entrusttime = entrusttime;
    }

    public Task(String taskname, int proid, int taskorder, Timestamp taskstarttime, Timestamp taskendtime, String taskstate, float taskrate, int fatherid, int workerid) {
        this.taskid=tasknum;
        tasknum=tasknum+1;
        this.taskname = taskname;
        this.proid = proid;
        this.taskorder = taskorder;
        this.taskstarttime = taskstarttime;
        this.taskendtime = taskendtime;
        this.taskstate = taskstate;
        this.taskrate = taskrate;
        this.fatherid = fatherid;
        this.workerid = workerid;
        this.entrust=0;
        this.postworkerid=-1;
        this.entrusttime=null;
    }


}
