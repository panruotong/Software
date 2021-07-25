package util;

import com.alibaba.fastjson.JSONArray;
import pojo.Worker;

import java.io.*;
import java.util.Collections;
import java.util.List;

public class MyJsonUtil {
    public static void writeJson(List workerList,String pathname){
        JSONArray jsonArray=new JSONArray(workerList);
        String s=jsonArray.toString();
        //s=s.substring(1,s.length()-1);
        int length=jsonArray.size();
        BufferedWriter writer;
        File file = new File(pathname);
        try{
            file.createNewFile();
            System.out.println(file.getAbsolutePath());
            writer = new BufferedWriter(new FileWriter(file));
            writer.write("{" );
            writer.newLine();
            writer.write("\"code\":0," );
            writer.newLine();
            writer.write("\"msg\":\"\"," );
            writer.newLine();
            writer.write("\"count\":" +length+"," );
            writer.newLine();
            writer.write("\"data\":");
            writer.write(s);
            writer.newLine();
            writer.write("}" );
            writer.flush();
            writer.close();
        }
        catch(
                FileNotFoundException e)
        {
            System.out.println("File Not Found");
            System.exit( 1 );
        }
        catch(IOException e)
        {
            e.printStackTrace();
            System.exit( 1 );
        }
    }
}
