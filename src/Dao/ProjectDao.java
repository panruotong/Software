package Dao;

import pojo.Project;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public interface ProjectDao {
    List<Project> findAllPro();
    List<Project> findProByState(String prostate);
    Project findProByID(int proid);
    void updateProStByID(int proid, Timestamp prostarttime);
    void updateProEtByID(int proid,Timestamp proendtime);
    void updateProStateByID(int proid,String prostate);
    void updateProRateByID(int proid,float prorate);
    int  findProIDByName(String name);
    void insertProject(Project project);
}
