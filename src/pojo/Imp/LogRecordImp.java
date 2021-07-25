package pojo.Imp;

import Dao.Imp.ProjectDaoImp;
import pojo.LogRecord;
import pojo.Project;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LogRecordImp {
    public List<LogRecord> findAllLogRecord() throws IOException {
        List<LogRecord> logList= new ArrayList<>();
        ProjectDaoImp projectDaoImp=new ProjectDaoImp();
        List<Project> projectList=projectDaoImp.findAllPro();
        for(Project project:projectList){
            logList.add(findLogByProID(project.getProid()));
        }
        return logList;
    }
    public LogRecord findLogByProID(int proid) throws IOException {
        ProjectDaoImp projectDaoImp=new ProjectDaoImp();
        Project project=projectDaoImp.findProByID(proid);
        String filename=project.getLogpath()+project.getProname()+".txt";
        FileInputStream fileInputStream = new FileInputStream(filename);
        LogRecord logRecord = new LogRecord();
        logRecord.setPID(proid);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream,"UTF-8"));
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            logRecord.addRecord(line);
        }
        return logRecord;
    }
    void writeLog(int proid,String content) throws IOException {
        ProjectDaoImp projectDaoImp=new ProjectDaoImp();
        Project project=projectDaoImp.findProByID(proid);
        String filePath=project.getLogpath()+project.getProname()+".txt";
        File dir = new File(filePath);
        File checkFile = new File(filePath);
        FileWriter writer = null;
        if (!checkFile.exists()){
            try {
                checkFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        writer = new FileWriter(checkFile, true);
        writer.append(content);
        writer.append("\r\n");
        writer.flush();
        writer.close();
    }
    /*public void writeLog(int proid,String content) throws FileNotFoundException, UnsupportedEncodingException {
        ProjectDaoImp projectDaoImp=new ProjectDaoImp();
        Project project=projectDaoImp.findProByID(proid);
        String filename=project.getLogpath()+project.getProname()+".txt";
        BufferedWriter writer = null;
        File file = new File(filename);
        try {
            if(!file.exists())
                file.createNewFile();
            writer = new BufferedWriter(new FileWriter(file,true));

            writer.write(content);
            writer.newLine();
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }*/
}
