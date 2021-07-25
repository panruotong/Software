package com.mybatis;

import pojo.Task;

import java.util.Date;
import java.util.List;

public interface TaskMapper {
    List<Task> findTaskByWorkerID(int workerid);
    List<Task> findAllTask();
    List<Task> findTaskByState(String taskstate);
    List<Task> findTaskByProID(int proid);
    List<Task> findEntrustTaskByWorkerID(int postworkerid);
    void updateTaskStByID(Task task);
    void updateTaskEtByID(Task task);
    void updateTaskStateByID(Task task);
    void updateTaskRateByID(Task task);
    void updateEntrustByID(Task task);
    void insertTask(Task task);
    Task findTaskByID(int taskid);
}
