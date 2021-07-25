package Dao;

import pojo.Message;

import java.util.List;

public interface MessageDao {
    public List<Message> findMessageByRecieveID(int recieveid);
    public Message findMessageByID(int messageid);
    public void insertMessage(Message message);
    public void updateMessageStateByRecieveID(int id);
    public void updateMessageStateByMessageID(int messageid);
}
