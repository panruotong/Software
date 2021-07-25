package Dao.Imp;

import Dao.MessageDao;
import com.mybatis.MessageMapper;
import org.apache.ibatis.session.SqlSession;
import pojo.Message;
import util.mybatisutil;

import java.util.List;

public class MessageDaoImp implements MessageDao {
    public List<Message> findMessageByRecieveID(int recieveid)
    {
        List<Message> messageList=null;
        SqlSession sqlSession= mybatisutil.getsqlsession();
        MessageMapper messageMapper=sqlSession.getMapper(MessageMapper.class);
        messageList=messageMapper.findMessageByRecieveID(recieveid);
        sqlSession.close();
        return messageList;
    }
    public Message findMessageByID(int messageid)
    {
        Message message=null;
        SqlSession sqlSession= mybatisutil.getsqlsession();
        MessageMapper messageMapper=sqlSession.getMapper(MessageMapper.class);
        message=messageMapper.findMessageByID(messageid);
        sqlSession.close();
        return message;
    }
    public void insertMessage(Message message)
    {
        SqlSession sqlSession= mybatisutil.getsqlsession();
        sqlSession.insert("insertMessage",message);
        sqlSession.commit();
        sqlSession.close();
    }
    public void updateMessageStateByRecieveID(int id)
    {
        SqlSession sqlSession=mybatisutil.getsqlsession();
        sqlSession.update("updateMessageStateByRecieveID",id);
        sqlSession.commit();
        sqlSession.close();
    }
    public void updateMessageStateByMessageID(int messageid){
        SqlSession sqlSession=mybatisutil.getsqlsession();
        sqlSession.update("updateMessageStateByMessageID",messageid);
        sqlSession.commit();
        sqlSession.close();
    }
}
