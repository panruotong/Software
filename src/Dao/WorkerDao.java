package Dao;

import pojo.Worker;

import java.util.List;

public interface WorkerDao {
    public List<Worker> getWorkerByType(String type);
    public void insertWorker(Worker worker);
    public List<Worker> findAdmain();
    Worker findWorkerByID(int workerid);
    List<Worker> findWorkerByStateAndType(String workerstate, String worktype);
    void updateWorkerState(Worker worker);
}
