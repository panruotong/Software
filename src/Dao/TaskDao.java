package Dao;

import pojo.Task;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public interface TaskDao {
    List<Task> findTaskByWorkerID(int workerid);
    List<Task> findAllTask();
    List<Task> findTaskByState(String taskstate);
    List<Task> findTaskByProID(int proid);
    void updateTaskStByID(int taskid,Timestamp taskstarttime);
    void updateTaskEtByID(int taskid,Timestamp taskendtime);
    void updateTaskStateByID(int taskid,String taskstate);
    void updateTaskRateByID(int taskid,float taskrate);
    void insertTask(Task task);
    Task findTaskByID(int taskid);
    List<Task> findEntrustTaskByWorkerID(int postworkerid);
    void updateEntrustByID(int taskid, int entrust, int postworkerid, Timestamp entrusttime);
}
