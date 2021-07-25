package pojo;

import java.util.ArrayList;
import java.util.List;

public class LogRecord {
    private int PID;
    private List<String> record;

    public void setRecord(List<String> record) {
        this.record = record;
    }

    public int getPID() {
        return PID;
    }

    public List<String> getRecord() {
        return record;
    }

    public LogRecord(int id){
        PID = id;
        record = new ArrayList<>();
    }
    public LogRecord(){
        record = new ArrayList<>();
    }
    public void setPID(int id){PID = id;}
    public void addRecord(String newRecord){
        record.add(newRecord);
    }
}
