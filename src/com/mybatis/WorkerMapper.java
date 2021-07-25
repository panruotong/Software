package com.mybatis;

import pojo.Worker;

import java.util.List;

public interface WorkerMapper {
    Worker findWorkerByID(int workerid);
    List<Worker> findWorkerByStateAndType(String workerstate, String worktype);
    List<Worker> findWorkerByType(String worktype);
    List<Worker> findAdmin(String type);
    void insertWorker(Worker worker);
    void updateWorkerState(Worker worker);}
