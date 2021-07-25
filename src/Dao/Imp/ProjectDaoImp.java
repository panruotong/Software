package Dao.Imp;

import Dao.ProjectDao;
import com.mybatis.ProjectMapper;
import org.apache.ibatis.session.SqlSession;
import pojo.Project;
import util.mybatisutil;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ProjectDaoImp implements ProjectDao {
    @Override
    public List<Project> findAllPro() {
        List<Project> projectList=null;
        SqlSession sqlSession= mybatisutil.getsqlsession();
        ProjectMapper projectMapper=sqlSession.getMapper(ProjectMapper.class);
        projectList=projectMapper.findAllPro();
        sqlSession.close();
        return projectList;
    }

    @Override
    public List<Project> findProByState(String prostate) {
        List<Project> projectList=null;
        SqlSession sqlSession= mybatisutil.getsqlsession();
        ProjectMapper projectMapper=sqlSession.getMapper(ProjectMapper.class);
        projectList=projectMapper.findProByState(prostate);
        sqlSession.close();
        return projectList;
    }

    @Override
    public Project findProByID(int proid) {
        Project project=null;
        SqlSession sqlSession= mybatisutil.getsqlsession();
        ProjectMapper projectMapper=sqlSession.getMapper(ProjectMapper.class);
        project=projectMapper.findProByID(proid);
        sqlSession.close();
        return project;
    }

    @Override
    public void updateProStByID(int proid,Timestamp prostarttime) {
        Project project=new Project(null,prostarttime,null,null,0,null,null);
        SqlSession sqlSession= mybatisutil.getsqlsession();
        project.setProid(proid);
        sqlSession.update("updateProStByID",project);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public void updateProEtByID(int proid, Timestamp proendtime) {
        Project project=new Project(null,null,proendtime,null,0,null,null);
        SqlSession sqlSession= mybatisutil.getsqlsession();
        project.setProid(proid);
        sqlSession.update("updateProEtByID",project);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public void updateProStateByID(int proid, String prostate) {
        Project project=new Project(null,null,null,prostate,0,null,null);
        SqlSession sqlSession= mybatisutil.getsqlsession();
        project.setProid(proid);
        sqlSession.update("updateProStateByID",project);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public void updateProRateByID(int proid, float prorate) {
        Project project=new Project(null,null,null,null,prorate,null,null);
        SqlSession sqlSession= mybatisutil.getsqlsession();
        project.setProid(proid);
        sqlSession.update("updateProRateByID",project);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public int findProIDByName(String name) {
        SqlSession sqlSession= mybatisutil.getsqlsession();
        ProjectMapper projectMapper=sqlSession.getMapper(ProjectMapper.class);
        int id=projectMapper.findProIDByName(name);
        sqlSession.close();
        return id;
    }

    @Override
    public void insertProject(Project project) {
        SqlSession sqlSession= mybatisutil.getsqlsession();
        sqlSession.insert("insertProject",project);
        sqlSession.commit();
        sqlSession.close();
    }
}
