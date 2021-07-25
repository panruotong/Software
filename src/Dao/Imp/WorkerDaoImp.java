package Dao.Imp;

import Dao.WorkerDao;

import com.mybatis.WorkerMapper;
import org.apache.ibatis.session.SqlSession;
import pojo.Worker;
import util.mybatisutil;

import java.util.List;

public class WorkerDaoImp implements WorkerDao {
    @Override
    public List<Worker> getWorkerByType(String type) {
        List<Worker> workerList=null;
        SqlSession sqlSession= mybatisutil.getsqlsession();
        WorkerMapper workerMapper =sqlSession.getMapper(WorkerMapper.class);
        workerList=workerMapper.findWorkerByType(type);
        sqlSession.close();
        return workerList;
    }

    @Override
    public void insertWorker(Worker worker) {
        SqlSession sqlSession= mybatisutil.getsqlsession();
        sqlSession.insert("insertWorker",worker);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public List<Worker> findAdmain() {
        List<Worker> workerList=null;
        SqlSession sqlSession= mybatisutil.getsqlsession();
        WorkerMapper workerMapper =sqlSession.getMapper(WorkerMapper.class);
        workerList=workerMapper.findAdmin("admin");
        sqlSession.close();
        return workerList;
    }

    @Override
    public Worker findWorkerByID(int workerid) {
        Worker worker=null;
        SqlSession sqlSession= mybatisutil.getsqlsession();
        WorkerMapper workerMapper =sqlSession.getMapper(WorkerMapper.class);
        worker=workerMapper.findWorkerByID(workerid);
        sqlSession.close();
        return worker;
    }

    @Override
    public List<Worker> findWorkerByStateAndType(String workerstate, String worktype) {
        List<Worker> workerList=null;
        SqlSession sqlSession= mybatisutil.getsqlsession();
        WorkerMapper workerMapper =sqlSession.getMapper(WorkerMapper.class);
        workerList=workerMapper.findWorkerByStateAndType(workerstate,worktype);
        sqlSession.close();
        return workerList;
    }
    @Override
    public void updateWorkerState(Worker worker)
    {
        SqlSession sqlSession= mybatisutil.getsqlsession();
        sqlSession.update("updateWorkerState",worker);
        sqlSession.commit();
        sqlSession.close();
    }
}
