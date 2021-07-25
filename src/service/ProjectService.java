package service;

import Dao.Imp.MessageDaoImp;
import Dao.Imp.ProjectDaoImp;
import Dao.Imp.TaskDaoImp;
import Dao.Imp.WorkerDaoImp;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.mysql.cj.xdevapi.JsonArray;
import pojo.*;
import pojo.Imp.LogRecordImp;
import pojo.Imp.MessageImp;
import pojo.Imp.ProjectImp;
import pojo.Imp.TaskImp;
import util.MyJsonUtil;
import util.MyTimeUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class ProjectService {
    public void createpro(HttpServletRequest request, HttpServletResponse response) throws IOException {
        /*为各项目随机分配合适的人员*/
        String path="A";
        String proname=request.getParameter("proname");
        String order=request.getParameter("order");
        int Astaff=Integer.parseInt(request.getParameter("Astaff"));
        int Bstaff=Integer.parseInt(request.getParameter("Bstaff"));
        int Cstaff=Integer.parseInt(request.getParameter("Cstaff"));
        Project project=new Project(proname, MyTimeUtil.getZeroTime(),MyTimeUtil.getZeroTime(),"waiting",0,path,null);
        int proid=project.getProid();
        WorkerDaoImp workerDaoImp=new WorkerDaoImp();
        List<Worker>workerList=null;
        if(Astaff==-1){
            workerList=workerDaoImp.getWorkerByType("A");
            int index=(int)Math.random()*workerList.size();
            Astaff=workerList.get(index).getWorkerid();
        }
        if(Bstaff==-1){
            workerList=workerDaoImp.getWorkerByType("B");
            int index=(int)Math.random()*workerList.size();
            Bstaff=workerList.get(index).getWorkerid();
        }
        if(Cstaff==-1){
            workerList=workerDaoImp.getWorkerByType("C");
            int index=(int)Math.random()*workerList.size();
            Cstaff=workerList.get(index).getWorkerid();
        }
        Task taskA=new Task("A",proid,0,MyTimeUtil.getZeroTime(),MyTimeUtil.getZeroTime(),"waiting",0,-1,Astaff);
        Task taskB=new Task("B",proid,0,MyTimeUtil.getZeroTime(),MyTimeUtil.getZeroTime(),"waiting",0,-1,Bstaff);
        Task taskC=new Task("C",proid,0,MyTimeUtil.getZeroTime(),MyTimeUtil.getZeroTime(),"waiting",0,-1,Cstaff);
        String message="您好，您被分配项目"+proid+"中的A任务，请您尽快完成";
        Message messageA=new Message(0,Astaff,message);
        message="您好，您被分配项目"+proid+"中的B任务，请您尽快完成";
        Message messageB=new Message(0,Bstaff,message);
        message="您好，您被分配项目"+proid+"中的C任务，请您尽快完成";
        Message messageC=new Message(0,Cstaff,message);
        switch (order){
            case"ABC":
                taskA.setTaskOrder(1);
                taskB.setTaskOrder(2);
                taskC.setTaskOrder(3);
                taskB.setFatherid(taskA.getTaskid());
                taskC.setFatherid(taskB.getTaskid());
                break;
            case"ACB":
                taskA.setTaskOrder(1);
                taskB.setTaskOrder(3);
                taskC.setTaskOrder(2);
                taskC.setFatherid(taskA.getTaskid());
                taskB.setFatherid(taskC.getTaskid());
                break;
            case"BAC":
                taskA.setTaskOrder(2);
                taskB.setTaskOrder(1);
                taskC.setTaskOrder(3);
                taskA.setFatherid(taskB.getTaskid());
                taskC.setFatherid(taskA.getTaskid());
                break;
            case"BCA":
                taskA.setTaskOrder(3);
                taskB.setTaskOrder(1);
                taskC.setTaskOrder(2);
                taskC.setFatherid(taskB.getTaskid());
                taskA.setFatherid(taskC.getTaskid());
                break;
            case"CAB":
                taskA.setTaskOrder(2);
                taskB.setTaskOrder(3);
                taskC.setTaskOrder(1);
                taskA.setFatherid(taskC.getTaskid());
                taskB.setFatherid(taskA.getTaskid());
                break;
            case"CBA":
                taskA.setTaskOrder(3);
                taskB.setTaskOrder(2);
                taskC.setTaskOrder(1);
                taskB.setFatherid(taskC.getTaskid());
                taskA.setFatherid(taskB.getTaskid());
                break;
        }
        ProjectDaoImp projectDaoImp=new ProjectDaoImp();
        projectDaoImp.insertProject(project);
        TaskDaoImp taskDaoImp=new TaskDaoImp();
        taskDaoImp.insertTask(taskA);
        taskDaoImp.insertTask(taskB);
        taskDaoImp.insertTask(taskC);
        MessageDaoImp messageDaoImp=new MessageDaoImp();
        messageDaoImp.insertMessage(messageA);
        messageDaoImp.insertMessage(messageB);
        messageDaoImp.insertMessage(messageC);

    }
    public void newmessage(HttpServletRequest request, HttpServletResponse response)  {
        List<Message> messageList=null;
        MessageDaoImp messageDaoImp=new MessageDaoImp();
        messageList=messageDaoImp.findMessageByRecieveID(Integer.parseInt(request.getParameter("recieveid")));
        int count=0;
        for(Message message:messageList){
            if(message.getState()==0)
                count=count+1;
        }
        System.out.println(count);
        String result="{\"messagenum\":"+count+"}";
        System.out.println(result);
        //String result= "{\"name\":\"张三\",\"password\":\"123456\"}";
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");
        try {
            response.getWriter().write(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void findworker(HttpServletRequest request, HttpServletResponse response){
        String type=request.getParameter("worktype");
        WorkerDaoImp workerDaoImp=new WorkerDaoImp();
        List<Worker> workerList=workerDaoImp.getWorkerByType(type);
        JSONArray jsonArray=new JSONArray(Collections.singletonList(workerList));
        try {
            response.getWriter().write(jsonArray.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void login(HttpServletRequest request, HttpServletResponse response)throws IOException{
        int workerid=Integer.parseInt(request.getParameter("username"));
        System.out.println(workerid);
        WorkerDaoImp workerDaoImp=new WorkerDaoImp();
        Worker worker=workerDaoImp.findWorkerByID(workerid);
        String s=JSON.toJSONString(worker);
        response.getWriter().write(s);
    }
    public void selectallpro(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("start selectallpro");
        ProjectDaoImp projectDaoImp=new ProjectDaoImp();
        List<Project> projectList=projectDaoImp.findAllPro();
        if(projectList!=null){
            JSONArray jsonArray=new JSONArray(Collections.singletonList(projectList));
            String s=jsonArray.toString();
           response.getWriter().write(s);
        }

        System.out.println("end selectallpro");
    }
    public void selectalltask(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("selectalltask start...");
        TaskDaoImp taskDaoImp=new TaskDaoImp();
        List<Task> taskList=taskDaoImp.findAllTask();
        if(taskList!=null){
            /*String path=request.getServletContext().getRealPath("taskadmin.json");
            MyJsonUtil.writeJson(taskList,path);*/
            JSONArray jsonArray=new JSONArray(Collections.singletonList(taskList));
            String s=jsonArray.toString();
            response.getWriter().write(s);
        }

        System.out.println("selectalltask end...");
    }
    public void stoppro(HttpServletRequest request, HttpServletResponse response) throws IOException {
        /*先检查是否waiting或working，否则返回任务已完成
        * 若是，找到子任务，检查状态（若为working或waiting），改为interrupt，开始时间结束时间，向工人发送通知，更改工人状态（查看工人状态，委托）
        * 则更改项目状态为interrupt,写入endtime，rate不变，，写日志*/
        int proid=Integer.parseInt(request.getParameter("proid"));
        String prostate=request.getParameter("prostate");
        ProjectImp projectImp=new ProjectImp();
        String msg=projectImp.canStopProject(proid);
        if(msg.equals("success")){
            projectImp.stopProject(proid);
            response.getWriter().write("The project has been successfully terminated");
        }else{
            response.getWriter().write(msg);
        }
    }
    public void selectstafftask(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("selectstafftask start...");
        int workerid=Integer.parseInt(request.getParameter("workerid"));
        TaskDaoImp taskDaoImp=new TaskDaoImp();
        List<Task> taskList=new ArrayList<>();
        taskList=taskDaoImp.findTaskByWorkerID(workerid);
        List<Task> entrustTaskList=taskDaoImp.findEntrustTaskByWorkerID(workerid);
        for(Task entrustTask:entrustTaskList){
            taskList.add(entrustTask);
        }
        if(taskList!=null){
            JSONArray jsonArray=new JSONArray(Collections.singletonList(taskList));
            String s=jsonArray.toString();
            response.getWriter().write(s);
        }

        System.out.println("selectstafftask end...");
    }
    public void starttask(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("starttask start...");
        int taskid=Integer.parseInt(request.getParameter("taskid"));
        int workerid=Integer.parseInt(request.getParameter("workerid"));
        TaskImp taskImp=new TaskImp();
        String msg=taskImp.canTaskStart(taskid,workerid);
        if(msg.equals("success")){
            taskImp.startTask(taskid,workerid);
            msg="the task is starting";
            response.getWriter().write(msg);
        }else{
                response.getWriter().write(msg);
        }
        System.out.println("starttask end...");
    }
    public void finishtask(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("finishtask start...");
        int taskid=Integer.parseInt(request.getParameter("taskid"));
        int workerid=Integer.parseInt(request.getParameter("workerid"));
        TaskImp taskImp=new TaskImp();
        String msg=taskImp.canTaskComplete(taskid,workerid);
        if(msg.equals("success")){
            taskImp.finishTask(taskid,workerid);
            msg="任务已完成";
            response.getWriter().write(msg);
        }
        else{
            response.getWriter().write(msg);
        }
        System.out.println("finishtask end....");
    }
    public void restartpro(HttpServletRequest request, HttpServletResponse response){
        int proid=Integer.parseInt(request.getParameter("proid"));
        ProjectImp projectImp=new ProjectImp();
        String msg=projectImp.restartProject(proid);
        try {
            response.getWriter().write(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void getmessage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int recieveid=Integer.parseInt(request.getParameter("workerid"));
        MessageImp messageImp=new MessageImp();
        List<Message> messageList=messageImp.readMessage(recieveid);
        JSONArray jsonArray=new JSONArray(Collections.singletonList(messageList));
        response.getWriter().write(jsonArray.toString());
    }
    public void entrust(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int taskid=Integer.parseInt(request.getParameter("taskid"));
        int workerid=Integer.parseInt(request.getParameter("workerid"));
        int entrusttime=Integer.parseInt(request.getParameter("entrusttime"));
        int entruststaff=Integer.parseInt(request.getParameter("entruststaff"));
        TaskImp taskImp=new TaskImp();
        String msg=taskImp.canTaskEntrust(taskid,workerid);
        if(msg.equals("success")){
            msg="entrust successfully";
            taskImp.EntrustTask(taskid,workerid,entruststaff,entrusttime);
        }else{
            response.getWriter().write(msg);
        }
    }
    public void getlog(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("getlog start....");
        LogRecordImp logRecordImp=new LogRecordImp();
        List<LogRecord> logRecordList=logRecordImp.findAllLogRecord();
        String s="";
        if(logRecordList!=null){
            for(LogRecord logRecord:logRecordList){
                List<String> info=logRecord.getRecord();
                List<String> newinfo=new ArrayList<>();
                String temp="";
                for(String log:info){
                    temp="<dd>"+temp+log+"</dd>";
                }
                newinfo.add(temp);
                logRecord.setRecord(newinfo);
            }
            JSONArray jsonArray=new JSONArray(Collections.singletonList(logRecordList));
             s=jsonArray.toString();

            response.getWriter().write(s);
        }

        System.out.println("getlog end....");
    }
    public void getWorkerStructure(HttpServletRequest request, HttpServletResponse response){
        List<StructureInfo> InfoList = new ArrayList<>();
        //公司对象
        StructureInfo company = new StructureInfo();
        company.setAuthorityId(1);
        company.setParentId(-1);
        company.setIsMenu(0);
        company.setAuthorityName("公司系统");
        InfoList.add(company);

        //各部门对象及管理员对象

        StructureInfo ASector = new StructureInfo();
        ASector.setAuthorityName("完成A任务部门");
        ASector.setIsMenu(0);
        ASector.setParentId(1);
        ASector.setAuthorityId(2);
        InfoList.add(ASector);

        StructureInfo BSector = new StructureInfo();
        BSector.setAuthorityName("完成B任务部门");
        BSector.setIsMenu(0);
        BSector.setParentId(1);
        BSector.setAuthorityId(3);
        InfoList.add(BSector);

        StructureInfo CSector = new StructureInfo();
        CSector.setAuthorityName("完成C任务部门");
        CSector.setIsMenu(0);
        CSector.setParentId(1);
        CSector.setAuthorityId(4);
        InfoList.add(CSector);

        StructureInfo AdminSector = new StructureInfo();
        AdminSector.setAuthorityName("管理员");
        AdminSector.setIsMenu(0);
        AdminSector.setParentId(1);
        AdminSector.setAuthorityId(5);
        InfoList.add(AdminSector);

        WorkerDaoImp workerDaoImp=new WorkerDaoImp();
        String[] type = new String[]{"A","B","C"};
        for(int i=1; i<= 3; i++)
        {
            List<Worker> workerList=workerDaoImp.getWorkerByType(type[i]);
            for ( Worker worker: workerList) {
                StructureInfo newInfo = new StructureInfo();
                newInfo.setAuthorityId(5+worker.getWorkerid());
                newInfo.setParentId(i+1);
                newInfo.setIsMenu(1);
                newInfo.setAuthorityName("员工"+String.valueOf(worker.getWorkerid()));
                InfoList.add(newInfo);
            }
        }

        //增加管理员们
        List<Worker> adminList=workerDaoImp.findAdmain();
        for ( Worker admin: adminList) {
            StructureInfo newInfo = new StructureInfo();
            newInfo.setAuthorityId(5+admin.getWorkerid());
            newInfo.setParentId(5);
            newInfo.setIsMenu(1);
            newInfo.setAuthorityName("管理员"+String.valueOf(admin.getWorkerid()));
            InfoList.add(newInfo);
        }

        JSONArray jsonArray=new JSONArray(Collections.singletonList(InfoList));
        s=jsonArray.toString();

        response.getWriter().write(s);
    }
    public void addWorker(HttpServletRequest request, HttpServletResponse response){
        int AuthorityId = Integer.parseInt(request.getParameter("authorityId"));
        WorkerDaoImp workerDaoImp=new WorkerDaoImp();
        //A部门
        if(AuthorityId == 2){
            Worker worker = new Worker("regular","123456","free","A");
            workerDaoImp.insertWorker(worker);
        }
        if(AuthorityId == 3){
            Worker worker = new Worker("regular","123456","free","B");
            workerDaoImp.insertWorker(worker);
        }
        if(AuthorityId == 4){
            Worker worker = new Worker("regular","123456","free","C");
            workerDaoImp.insertWorker(worker);
        }
        //管理员
        if(AuthorityId == 5){
            Worker worker = new Worker("admin","123456","free","");
            workerDaoImp.insertWorker(worker);
        }
    }
    public void delWorker(HttpServletRequest request, HttpServletResponse response){
        int authorityId = Integer.parseInt(request.getParameter("authorityId"));
        WorkerDaoImp workerDaoImp=new WorkerDaoImp();
    }
}
