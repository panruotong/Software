package pojo;

public class Message {
    int messageid;
    int sendid;
    int recieveid;
    int state; //0代表信件未读，1代表信件已读
    String content;

    public Message( int sendid, int recieveid, String content) {
        this.sendid = sendid;
        this.recieveid = recieveid;
        this.state = 0;
        this.content = content;
    }

    public Message() {
    }

    public int getMessageid() {
        return messageid;
    }

    public void setMessageid(int messageid) {
        this.messageid = messageid;
    }

    public int getSendid() {
        return sendid;
    }

    public void setSendid(int sendid) {
        this.sendid = sendid;
    }

    public int getRecieveid() {
        return recieveid;
    }

    public void setRecieveid(int recieveid) {
        this.recieveid = recieveid;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
