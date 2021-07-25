package pojo;

public class Worker {
    int workerid;
    String type;
    String password;
    String workerstate;
    String worktype;

    public int getWorkerid() {
        return workerid;
    }

    public void setWorkerid(int workerid) {
        this.workerid = workerid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getWorkerstate() {
        return workerstate;
    }

    public void setWorkerstate(String workerstate) {
        this.workerstate = workerstate;
    }

    public String getWorktype() {
        return worktype;
    }

    public void setWorktype(String worktype) {
        this.worktype = worktype;
    }

    public Worker() {
    }

    public Worker( String type, String password, String workerstate, String worktype) {
        this.type = type;
        this.password = password;
        this.workerstate = workerstate;
        this.worktype = worktype;
    }
}
