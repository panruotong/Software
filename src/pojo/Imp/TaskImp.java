package pojo.Imp;

import Dao.Imp.MessageDaoImp;
import Dao.Imp.ProjectDaoImp;
import Dao.Imp.TaskDaoImp;
import Dao.Imp.WorkerDaoImp;
import Dao.MessageDao;
import Dao.TaskDao;
import pojo.Message;
import pojo.Project;
import pojo.Task;
import pojo.Worker;
import util.MyTimeUtil;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class TaskImp {
    /*----测试任务能否开始：--
       父任务的状态，自身任务的状态，是否被委托（检查是否是受委托人），当前工人的状态，当前请求发出者是否有权限
    * */
    public String canTaskStart(int taskid,int workerid){
        String msg="success";
        System.out.println(" canTaskStart start");
        TaskDaoImp taskDaoImp=new TaskDaoImp();
        Task task=taskDaoImp.findTaskByID(taskid);
        if(task.getFatherid()!=-1){   //检查父任务是否完成
            Task fathertask=taskDaoImp.findTaskByID(task.getFatherid());
            if(!(fathertask.getTaskstate().equals("complete"))){
                msg="启动任务失败：当前任务的前驱任务未完成无法启动";
                return msg;
            }
        }
        if(!task.getTaskstate().equals("waiting")){
            if(task.getTaskstate().equals("working")){
                msg="启动任务失败：当前任务已经处于运行状态";
            }
            else if(task.getTaskstate().equals("complete")){
                msg="启动任务失败：当前任务处于完成状态无法执行启动命令";
            }
            else if(task.getTaskstate().equals("interupt")){
                msg="启动任务失败：当前任务所属的项目处于终止状态无法启动";
            }
            return msg;
        }
        /*WorkerDaoImp workerDaoImp=new WorkerDaoImp();
        Worker worker=workerDaoImp.findWorkerByID(workerid);*/
        if(task.getEntrust()==0){
            if(workerid!=task.getWorkerid())
            {
                msg="启动任务失败：您不具备执行任务的权限";
                return msg;
            }
        }
        else if(task.getEntrust()==1){
            if(workerid!=task.getPostworkerid())
            {
                msg="启动任务失败：该任务已被您委托，若您想执行任务请先收回权限";
                return msg;
            }
        }
        System.out.println(" canTaskStart end");
        return msg;
    }
    /*---测试任务能否完成--*/
    /*自身任务的状态，是否该任务已经被委托不是自己在执行（权限）
    * */
    public String canTaskComplete(int taskid,int workerid){
        System.out.println("canTaskComplete start");
        String msg="success";
        TaskDaoImp taskDaoImp=new TaskDaoImp();
        Task task=taskDaoImp.findTaskByID(taskid);
        if(task.getTaskstate().equals("waiting")){
            msg="失败：该任务尚未开始处理无法执行完成动作";
            return msg;
        }
        if(task.getTaskstate().equals("complete")){
            msg="失败：该任务已经处于完成状态";
            return msg;
        }
        if(task.getEntrust()==1&&workerid==task.getWorkerid()){
            msg="您已经将该任务委托若要更改任务状态请先收回委托权限";
            return msg;
        }
        System.out.println("canTaskComplete end");
        return msg;
    }
    /*--测试任务能否被委托--*
    /*任务的状态，是否已经被委托
     */
    public String canTaskEntrust(int taskid,int workerid){
        System.out.println("canTaskEntrust start");
        String msg="success";
        TaskDaoImp taskDaoImp=new TaskDaoImp();
        Task task=taskDaoImp.findTaskByID(taskid);
        if(task.getEntrust()==1){
            msg="失败：任务"+taskid+"已经被委托";
            return msg;
        }
        if(task.getTaskstate().equals("complete")){
            msg="失败:任务"+taskid+"已处于完成状态，无法进行委托";
            return msg;
        }
        System.out.println("canTaskEntrust end...");
        return msg;
    }
    /*--测试委托权限是否可收回*/
    /*是否有委托，执行者是否为sender
    * */
    public String canCancelEntrust(int taskid,int workerid){
        System.out.println("canCancelEntrust start...");
        String msg="success";
        TaskDaoImp taskDaoImp=new TaskDaoImp();
        Task task=taskDaoImp.findTaskByID(taskid);
        if(task.getEntrust()==0){
            msg="失败：未检查到当前任务有存在的委托关系";
            return msg;
        }
        if(!(workerid==task.getWorkerid()))
        {
            msg="失败：您是任务的受委托人，并未交付出权限无法回收";
            return msg;
        }
        System.out.println("canCancelEntrust end");
        return msg;

    }
    /*
     * 1.开始任务
     *   1.1 检测order是否为1，为1调用开始项目函数
     *   1.2 更改taskstaretime
     *   1.3 更改taskstate=working
     *   1.4 任务的工人workerstate为working
     */
    public void startTask(int taskid,int workerid) throws FileNotFoundException, UnsupportedEncodingException {
        System.out.println("startTask start");
        TaskDaoImp taskDaoImp=new TaskDaoImp();
        Task task=taskDaoImp.findTaskByID(taskid);
        if(task.getTaskOrder()==1){
            ProjectImp projectImp=new ProjectImp();
            projectImp.startPro(task.getProid());
        }
        String state="working";
        taskDaoImp.updateTaskStateByID(taskid,state);
        Timestamp time=MyTimeUtil.getCurrentTime();
        taskDaoImp.updateTaskStByID(taskid, time);
        LogRecordImp logRecordImp=new LogRecordImp();
        String s=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.0").format(time)+"       "+"工人"+workerid+"启动"+"任务"+task.getTaskname();
        logRecordImp.writeLog(task.getProid(),s);
        System.out.println("startTask end....");
    }
    /*
    1.常规完成任务
     1.1 写log
     1.2 写入endtime
     1.3 更改taskstate
     1.4 检测任务order，若order为3调用项目完成的函数
     1.4 更改task的rate以及project的rate
     1.5 更改worker的state*/
       /*
    2.委托完成任务
      2.1 写log
      2.2 写入endtime
      2.3 更改taskstate
      2.4 检测任务order，若order为3调用项目完成的函数
      2.5 更改taskrate以及prorate
      2.6 更改委托表的workerstate
      2.6 向原委托人发送信息
      2.6 删除委托信息*/
    public void finishTask(int taskid,int workerid) throws FileNotFoundException, UnsupportedEncodingException {
        //写日志
        System.out.println("finishTask start");
        TaskDaoImp taskDaoImp=new TaskDaoImp();
        Task task=taskDaoImp.findTaskByID(taskid);
        String state="complete";
        taskDaoImp.updateTaskStateByID(taskid,state);
        Timestamp time=MyTimeUtil.getCurrentTime();
        taskDaoImp.updateTaskEtByID(taskid,time);
        taskDaoImp.updateTaskRateByID(taskid,100);
        ProjectDaoImp projectDaoImp=new ProjectDaoImp();
        Project project=projectDaoImp.findProByID(task.getProid());
        float rate=project.getProrate()+33;
        System.out.println("rate"+rate);
        if(rate==99)
            rate=100;
        projectDaoImp.updateProRateByID(project.getProid(),rate);
        if(task.getTaskOrder()==3){
            ProjectImp projectImp=new ProjectImp();
            projectImp.completePro(project.getProid());
        }
        String s=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.0").format(time)+"       "+"工人"+task.getWorkerid()+"完成"+"任务"+task.getTaskname();
        if(task.getEntrust()==1){    //被委托状态
            s=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.0").format(time)+"       "+"工人"+task.getPostworkerid()+"完成"+"任务"+task.getTaskname()+"(委托完成后委托权限回收)";
            String msg="您委托于工人"+task.getPostworkerid()+"的任务其已经完成并提交，现为您收回权限";
            Message message=new Message(0,task.getWorkerid(),msg);
            MessageDaoImp messageDaoImp=new MessageDaoImp();
            messageDaoImp.insertMessage(message);
            taskDaoImp.updateEntrustByID(taskid,0,-1,null);
        }
        LogRecordImp logRecordImp=new LogRecordImp();
        logRecordImp.writeLog(task.getProid(),s);
        System.out.println("finishTask end...");
    }

    /*
     3.手动回收委托任务：
       3.1 写log
       3.2 更改taskstate=waiting
       3.3 更改受委托者的workerstate
       3.4 删除委托信息
      4.定时回收委托任务：
        4.1 写log
        4.2 更改taskstate=waiting
        4.3 更改受委托者的workerstate
        4.4 删除委托信息、
        4.5 向原委托人发送通知
          */
    /*
    * flag=true  定时回收
    * flag=false  手动回收
    * */
    public void cancelEntrust(int taskid,int workerid,boolean flag) throws FileNotFoundException, UnsupportedEncodingException {
        System.out.println("cancelEntrust start");
        //写log
        TaskDaoImp taskDaoImp=new TaskDaoImp();
        Task task=taskDaoImp.findTaskByID(taskid);
        taskDaoImp.updateTaskStateByID(taskid,"waiting");
        if(flag){
            String msg1="您交给工人"+task.getPostworkerid()+"的项目"+task.getProid()+"的"+task.getTaskname()+"的委托时间已到，系统已为您回收权限，请尽快完成";
            String msg2="任务"+taskid+"的委托权限时间已到已进行权限回收您无法继续运行";
            Message message1=new Message(0,task.getWorkerid(),msg1);
            Message message2=new Message(0,task.getPostworkerid(),msg2);
            MessageDaoImp messageDaoImp=new MessageDaoImp();
            messageDaoImp.insertMessage(message1);
            messageDaoImp.insertMessage(message2);
        }
        taskDaoImp.updateEntrustByID(taskid,0,-1,null);
        Timestamp time=MyTimeUtil.getCurrentTime();
        String s=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.0").format(time)+"       "+"任务"+task.getTaskname()+"的权限回收至"+task.getWorkerid();
        LogRecordImp logRecordImp=new LogRecordImp();
        logRecordImp.writeLog(task.getProid(),s);
        System.out.println("cancelEntrust end");
    }


    /* 2 委托任务：(taskid,sendid,recieveid)
    *   2.1 创建message通知reciever
    *   //2.2 委托任务开始函数
    *   2.3若定时 则暂停时间调用定时回收函数
    *   2.4若不定时，则结束
    * */
    public void EntrustTask(int taskid, int workerid, int postworkerid, int entrusttime) throws FileNotFoundException, UnsupportedEncodingException {
        System.out.println("EtrustTask  start...");
        String msg="";
        if(entrusttime!=0){
            msg="您收到了工人"+workerid+"对于任务"+taskid+"的委托,委托的截止时间为"+entrusttime+"请尽快完成";
        }else{
            msg="您收到了工人"+workerid+"对于任务"+taskid+"的委托,请尽快完成";
        }
        Message message=new Message(workerid,postworkerid,msg);
        TaskDaoImp taskDaoImp=new TaskDaoImp();
        Task task=taskDaoImp.findTaskByID(taskid);
        taskDaoImp.updateEntrustByID(taskid,1,postworkerid,null);
        taskDaoImp.updateTaskStateByID(taskid,"waiting");
        MessageDaoImp messageDaoImp=new MessageDaoImp();
        messageDaoImp.insertMessage(message);
        Timestamp time=MyTimeUtil.getCurrentTime();
        String s=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.0").format(time)+"       "+"任务"+task.getTaskname()+"的权限由工人"+workerid+"移交至"+postworkerid;
        LogRecordImp logRecordImp=new LogRecordImp();
        logRecordImp.writeLog(task.getProid(),s);
        if(entrusttime!=0){
            try {
                Thread.sleep(entrusttime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(task.getEntrust()==1){
                cancelEntrust(taskid,workerid,true);
            }
        }
        System.out.println("EtrustTask  end...");
    }
    /*中止任务：
    1.任务是委托：
       1.1：任务处于waiting
           1.1.1 删除委托，发送通知
           1.1.2更改taskendtime，taskstate
       1.2 任务处于working
           1.2.1 更改recieveid的workerstate
           1.2.2 删除委托，发送通知
           1.2.3 更改taskendtime，taskstate
    2.任务不是委托：
       2.1.任务处于waiting
            2.1.1 写入taskendtime
            2.1.2 更改state
       2.2  任务处于working
           2.2.1 更新workerstate
           2.2.2更新taskendtime，taskstate
    * */
    public void stopTask(int taskid){
        System.out.println("stoptask start...");
        TaskDaoImp taskDaoImp=new TaskDaoImp();
        Task task=taskDaoImp.findTaskByID(taskid);
        taskDaoImp.updateTaskStateByID(taskid,"interupt");
        Timestamp time=MyTimeUtil.getCurrentTime();
        taskDaoImp.updateTaskEtByID(taskid,time);
        String s=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.0").format(time)+"       "+"任务"+task.getTaskname()+"被迫中止";
        LogRecordImp logRecordImp=new LogRecordImp();
        try {
            logRecordImp.writeLog(task.getProid(),s);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        MessageDaoImp messageDaoImp=new MessageDaoImp();
        if(task.getEntrust()==1){
            String msg="管理员中止了您负责的项目下的任务"+taskid+",现委托权已收回";
            Message message=new Message(0,task.getPostworkerid(),msg);
            messageDaoImp.insertMessage(message);
            taskDaoImp.updateEntrustByID(taskid,0,-1,null);
        }
        String msg="管理员中止了您负责的项目下的任务"+taskid;
        Message message=new Message(0,task.getWorkerid(),msg);
        messageDaoImp.insertMessage(message);
           System.out.println("stoptask end.....");
    }
    /*
    * 重启任务
    * 若任务是complete状态则不改变
    * 否则，更新taskstarttime，taskendtime，taskstate
    * 发送消息*/
    public void restartTask(int taskid){
        TaskDaoImp taskDaoImp=new TaskDaoImp();
        Task task=taskDaoImp.findTaskByID(taskid);
        Timestamp time=MyTimeUtil.getCurrentTime();
        taskDaoImp.updateTaskStByID(taskid,MyTimeUtil.getZeroTime());
        String s=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.0").format(time)+"       "+"任务"+task.getTaskname()+"重启";
        LogRecordImp logRecordImp=new LogRecordImp();
        try {
            logRecordImp.writeLog(task.getProid(),s);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        taskDaoImp.updateTaskEtByID(taskid,MyTimeUtil.getZeroTime());
        taskDaoImp.updateTaskStateByID(taskid,"waiting");
        String msg="您负责的项目"+task.getProid()+"中的"+task.getTaskname()+"已经被重启，请尽快完成任务";
        Message message=new Message(0,task.getWorkerid(),msg);
        MessageDaoImp messageDaoImp=new MessageDaoImp();
        messageDaoImp.insertMessage(message);

    }
}
