package Lab5.CommonStaff.Others;

import java.io.Serializable;

public class Message<T, U> implements Serializable {
    private CommandTypes type;
    private boolean result;
    private User user;
    private T arg = null;
    private U arg2 = null;

    public Message(){

    }

    public Message(CommandTypes type){
        this.type = type;
    }

    public Message(CommandTypes type, User user){
        this(type);
        this.user = user;
    }

    public Message(CommandTypes type, T arg){
        this(type);
        this.arg = arg;
    }

    public Message(CommandTypes type, T arg, User user){
        this(type, arg);
        this.user = user;
    }

    public Message(CommandTypes type, T arg, U arg2){
        this(type, arg);
        this.arg2 = arg2;
    }

    public Message(CommandTypes type, T arg, U arg2, User user){
        this(type, arg, arg2);
        this.user = user;
    }

    public Message(T arg){
        this.arg = arg;
    }

    public  Message(T arg, U arg2){
        this(arg);
        this.arg2 = arg2;
    }

    public CommandTypes getType(){
        return type;
    }

    public T getArg() {
        return arg;
    }

    public U getArg2() {
        return arg2;
    }

    public void setArg(T arg){
        this.arg = arg;
    }

    public void serArg2(U arg){
        this.arg2 = arg;
    }

    public boolean isResult(){
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public void setUser(User user){
        this.user = user;
    }

    public User getUser(){
        return user;
    }

    public String toString() {
        return "Message{" +
                "type=" + type +
                ", user=" + user +
                ", arg=" + arg +
                ", arg2=" + arg2 +
                '}';
    }
}
