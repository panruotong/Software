package pojo.Imp;

import Dao.Imp.MessageDaoImp;
import pojo.Message;

import java.util.ArrayList;
import java.util.List;

public class MessageImp {
    //已读信件
    public List<Message> readMessage(int workerid){
        List<Message> messageList=null;
        List<Message> unreadmessage=new ArrayList<>();
        MessageDaoImp messageDaoImp=new MessageDaoImp();
        messageList=messageDaoImp.findMessageByRecieveID(workerid);
        for(Message message:messageList){
            if(message.getState()==0){
                unreadmessage.add(message);
                messageDaoImp.updateMessageStateByMessageID(message.getMessageid());
            }
        }
        return unreadmessage;
    }
}
