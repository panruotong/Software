package com.mybatis;

import pojo.Project;

import java.util.Date;
import java.util.List;

public interface ProjectMapper {
    List<Project> findAllPro();
    List<Project> findProByState(String prostate);
    Project findProByID(int proid);
    void updateProStByID(Project project);
    void updateProEtByID(Project project);
    void updateProStateByID(Project project);
    void updateProRateByID(Project project);
    int  findProIDByName(String name);
    void insertProject(Project project);
}
