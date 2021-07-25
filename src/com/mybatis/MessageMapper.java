package com.mybatis;

import pojo.Message;

import java.util.List;

public interface MessageMapper {
    List<Message> findMessageByRecieveID(int recieveid);
    Message findMessageByID(int messageid);
    void insertMessage(Message message);
    void updateMessageStateByRecieveID(int id);
    void updateMessageStateByMessageID(int messageid);
}
