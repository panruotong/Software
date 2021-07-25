package pojo;

import Dao.Imp.MessageDaoImp;
import Dao.Imp.ProjectDaoImp;
import Dao.Imp.TaskDaoImp;
import Dao.Imp.WorkerDaoImp;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.session.SqlSession;
import pojo.Imp.LogRecordImp;
import service.ProjectService;
import util.MyTimeUtil;
import util.mybatisutil;


import java.io.*;
import java.sql.Timestamp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class Test {
    public static void main(String[] args) throws ParseException, IOException {
       /* String taskname="A";
        int proid=1;
        int order=1;
        Timestamp taskstarttime=MyTimeUtil.getCurrentTime();
        Timestamp taskendtime=MyTimeUtil.getZeroTime();
        String taskstate="working";
        float taskrate=100;
        int fatherid=-1;
        int workerid=3;
        Task task=new Task(taskname,proid,order,taskstarttime,taskendtime,taskstate,taskrate,fatherid,workerid);
        SqlSession sqlsession= mybatisutil.getsqlsession();
        sqlsession.insert("insertTask",task);
        sqlsession.commit();
        sqlsession.close();*/
        /*Worker worker1=new Worker("admin","password","free","C");
        Worker worker2=new Worker("admin","password","free","C");
        Worker worker3=new Worker("admin","password","free","C");
        Worker worker4=new Worker("admin","password","free","C");
        WorkerDaoImp workerDaoImp=new WorkerDaoImp();
        workerDaoImp.insertWorker(worker1);
        workerDaoImp.insertWorker(worker2);
        workerDaoImp.insertWorker(worker3);
        workerDaoImp.insertWorker(worker4);*/
        /*Timestamp timestamp=null;
        String date = "0000-00-00T00:00"; // <input type="datetime-local"> 输入参数
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        try {
            Date dt = sdf.parse(date);
             timestamp=new Timestamp(dt.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Project project=new Project("bbb",timestamp,null,"waiting",0,"B",null);
        ProjectDaoImp projectDaoImp=new ProjectDaoImp();
        projectDaoImp.insertProject(project);*/
        //long currentTimeMillions=System.currentTimeMillis();
        //timestamp=new Timestamp(currentTimeMillions);
        //定义一个String类型实体str保存你要的时间，格式如下(以2018年5月6号10点30分40秒为例)
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        /*String str = "2018-05-06 18:30:40";
        //用Timestamp的valueOf方法转化为Timestamp实体
        Timestamp time = Timestamp.valueOf(str);
        System.out.println(time);
        String strn = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.0").format(System.currentTimeMillis());
        TaskDaoImp taskDaoImp=new TaskDaoImp();
        taskDaoImp.updateTaskStByID(10,time);*/
        /*String content="工人1在"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.0").format(System.currentTimeMillis())+"完成了任务A";
        String content1="工人1在"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.0").format(System.currentTimeMillis())+"完成了任务A";
        String content2="工人1在"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.0").format(System.currentTimeMillis())+"完成了任务A";
        LogRecordImp logRecordImp=new LogRecordImp();
        logRecordImp.writeLog(5,content);
        logRecordImp.writeLog(5,content1);
        logRecordImp.writeLog(5,content2);
        ProjectService projectService=new ProjectService();
        try {
            projectService.getlog(null,null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String d="aaa\r\nbbb";
        System.out.print(d);*/
        //System.out.println(MyTimeUtil.getZeroTime());
        //createfile();
    }


}
