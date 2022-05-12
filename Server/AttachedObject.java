package Lab5.Server;

import Lab5.CommonStaff.Others.Message;

import java.net.SocketAddress;

public class AttachedObject {
    private SocketAddress adr;
    private Message<String, ?> answer;

    public AttachedObject(SocketAddress adr, Message<String, ?> answer){
        this.adr = adr;
        this.answer = answer;
    }

    public Message<String, ?> getAnswer() {
        return answer;
    }

    public SocketAddress getAdr() {
        return adr;
    }

    public String toString(){
        return answer.getArg();
    }
}
