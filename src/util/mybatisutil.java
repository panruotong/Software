package util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class mybatisutil {
    private mybatisutil(){}
    private static SqlSessionFactory sqlsessionfactory=null;
    static
    {
        try
        {
            InputStream in= Resources.getResourceAsStream("mybatis_config.xml");
            sqlsessionfactory=new SqlSessionFactoryBuilder().build(in);
        }
        catch(IOException e)
        {
           e.printStackTrace();
        }
    }
    public static SqlSession getsqlsession()
    {
        return sqlsessionfactory.openSession();
    }
}
