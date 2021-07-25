package Dao.Imp;

import Dao.TaskDao;
import com.mybatis.TaskMapper;
import org.apache.ibatis.session.SqlSession;
import pojo.Task;
import util.mybatisutil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class TaskDaoImp implements TaskDao
{
    @Override
    public List<Task> findTaskByWorkerID(int workerid) {
        List<Task> taskList=null;
        SqlSession sqlSession= mybatisutil.getsqlsession();
        TaskMapper taskMapper =sqlSession.getMapper(TaskMapper.class);
        taskList=taskMapper.findTaskByWorkerID(workerid);
        sqlSession.close();
        return taskList;
    }

    @Override
    public List<Task> findAllTask() {
        List<Task> taskList=null;
        SqlSession sqlSession= mybatisutil.getsqlsession();
        TaskMapper taskMapper =sqlSession.getMapper(TaskMapper.class);
        taskList=taskMapper.findAllTask();
        sqlSession.close();
        return taskList;
    }

    @Override
    public List<Task> findTaskByState(String taskstate) {
        List<Task> taskList=null;
        SqlSession sqlSession= mybatisutil.getsqlsession();
        TaskMapper taskMapper =sqlSession.getMapper(TaskMapper.class);
        taskList=taskMapper.findTaskByState(taskstate);
        sqlSession.close();
        return taskList;
    }

    @Override
    public List<Task> findTaskByProID(int proid) {
        List<Task> taskList=null;
        SqlSession sqlSession= mybatisutil.getsqlsession();
        TaskMapper taskMapper =sqlSession.getMapper(TaskMapper.class);
        taskList=taskMapper.findTaskByProID(proid);
        sqlSession.close();
        return taskList;
    }

    @Override
    public void updateTaskStByID(int taskid, Timestamp taskstarttime) {
        SqlSession sqlSession= mybatisutil.getsqlsession();
        Task task=new Task(null,-1,-1,taskstarttime,null,null,0,-1,-1);
        task.setTaskid(taskid);
        sqlSession.update("updateTaskStByID",task);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public void updateTaskEtByID(int taskid, Timestamp taskendtime) {
        SqlSession sqlSession= mybatisutil.getsqlsession();
        Task task=new Task(null,-1,-1,null,taskendtime,null,0,-1,-1);
        task.setTaskid(taskid);
        sqlSession.update("updateTaskEtByID",task);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public void updateTaskStateByID(int taskid, String taskstate) {
        SqlSession sqlSession= mybatisutil.getsqlsession();
        Task task=new Task(null,-1,-1,null,null,taskstate,0,-1,-1);
        task.setTaskid(taskid);
        sqlSession.update("updateTaskStateByID",task);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public void updateTaskRateByID(int taskid, float taskrate) {
        SqlSession sqlSession= mybatisutil.getsqlsession();
        Task task=new Task(null,-1,-1,null,null,null,taskrate,-1,-1);
        task.setTaskid(taskid);
        sqlSession.update("updateTaskRateByID",task);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public void insertTask(Task task) {
        SqlSession sqlSession= mybatisutil.getsqlsession();
        sqlSession.insert("insertTask",task);
        sqlSession.commit();
        sqlSession.close();
    }
    @Override
    public Task findTaskByID(int taskid)
    {
        Task task=null;
        SqlSession sqlSession= mybatisutil.getsqlsession();
        TaskMapper taskMapper =sqlSession.getMapper(TaskMapper.class);
        task=taskMapper.findTaskByID(taskid);
        sqlSession.close();
        return task;
    }
    @Override
    public void updateEntrustByID(int taskid, int entrust, int postworkerid, Timestamp entrusttime)
    {
        Task task=new Task("",-1,-1,null,null,"",0,0,0);
        task.setTaskid(taskid);
        task.setEntrust(entrust);
        task.setPostworkerid(postworkerid);
        task.setEntrusttime(entrusttime);
        SqlSession sqlSession= mybatisutil.getsqlsession();
        sqlSession.update("updateEntrustByID",task);
        sqlSession.commit();
        sqlSession.close();
    }
    public List<Task> findEntrustTaskByWorkerID(int postworkerid){
        List<Task> taskList=null;
        SqlSession sqlSession= mybatisutil.getsqlsession();
        TaskMapper taskMapper =sqlSession.getMapper(TaskMapper.class);
        taskList=taskMapper.findEntrustTaskByWorkerID(postworkerid);
        sqlSession.close();
        return taskList;
    }
}
