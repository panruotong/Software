package pojo.Imp;

import Dao.Imp.ProjectDaoImp;
import Dao.Imp.TaskDaoImp;
import Dao.Imp.WorkerDaoImp;
import pojo.Project;
import pojo.Task;
import pojo.Worker;
import util.MyTimeUtil;

import java.sql.Timestamp;
import java.util.List;

public class ProjectImp {
    /*项目开始：
    1 记录prostarttime
    2 更改prostate
    * */
    public void startPro(int proid){
        ProjectDaoImp projectDaoImp=new ProjectDaoImp();
        Timestamp time= MyTimeUtil.getCurrentTime();
        String state="working";
        projectDaoImp.updateProStateByID(proid,state);
        projectDaoImp.updateProStByID(proid,time);
        //写日志
    }
    /*项目结束：
    1.记录proendtime
    2.记录prostate
    * */
    public void completePro(int proid){
        ProjectDaoImp projectDaoImp=new ProjectDaoImp();
        Timestamp time= MyTimeUtil.getCurrentTime();
        String state="complete";
        projectDaoImp.updateProStateByID(proid,state);
        projectDaoImp.updateProEtByID(proid,time);
        //写日志
    }
    /*中止项目：

      2.1 记录项目proendtime
      2.2 更改prostate
      2.3 更新任务
      2.4日志
    * */

    public String  canStopProject(int proid){
        String msg="success";
        ProjectDaoImp projectDaoImp=new ProjectDaoImp();
        Project project=projectDaoImp.findProByID(proid);
        if(project.getProstate().equals("complete")){
            msg="失败：该任务已处于完成状态无法执行中止命令";
            return msg;
        }
        if(project.getProstate().equals("interupt")){
            msg="失败：该任务已经处于终止状态不能执行中止命令";
        }
        return msg;
    }
    public void  stopProject(int proid) {
        System.out.println("stopproject start...");
        ProjectDaoImp projectDaoImp=new ProjectDaoImp();
        Project project=projectDaoImp.findProByID(proid);
        projectDaoImp.updateProStateByID(proid,"interupt");
        projectDaoImp.updateProEtByID(proid,MyTimeUtil.getCurrentTime());
        TaskDaoImp taskDaoImp=new TaskDaoImp();
        List<Task> taskList=taskDaoImp.findTaskByProID(proid);
        for(Task task:taskList){
            TaskImp taskImp=new TaskImp();
            taskImp.stopTask(task.getTaskid());
        }
        System.out.println("stopproject end....");
    }
    /*重启项目
       1.日志
       2.更新prostarttime,proendtime,prostate
       2.将任务列表中非complete的prostarttime,proendtime,state.
    * */
    public String restartProject(int proid){
        ProjectDaoImp projectDaoImp=new ProjectDaoImp();
        Project project=projectDaoImp.findProByID(proid);
        String msg="restart the   " +project.getProname() +"  successfully!";
        if(!project.getProstate().equals("interupt")){
            stopProject(proid);
            msg="项目"+project.getProname() +"并未处于终止状态，系统已为您中止该项目成功重启";
        }
        TaskDaoImp taskDaoImp=new TaskDaoImp();
        List<Task> taskList=taskDaoImp.findTaskByProID(proid);
        projectDaoImp.updateProStByID(proid,MyTimeUtil.getZeroTime());
        projectDaoImp.updateProEtByID(proid,MyTimeUtil.getZeroTime());
        projectDaoImp.updateProStateByID(proid,"waiting");
        TaskImp taskImp=new TaskImp();
        for(Task task:taskList){
            taskImp.restartTask(task.getTaskid());
        }
        //重启日志
        return msg;
    }
}
