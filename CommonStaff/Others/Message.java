package Lab5.CommonStaff.Others;

import java.io.Serializable;

public class Message<T, U> implements Serializable {
    private CommandTypes type;
    private boolean result;
    private T arg = null;
    private U arg2 = null;

    public Message(){

    }

    public Message(CommandTypes type){
        this.type = type;
    }

    public Message(CommandTypes type, T arg){
        this(type);
        this.arg = arg;
    }

    public Message(CommandTypes type, T arg, U arg2){
        this(type, arg);
        this.arg2 = arg2;
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

    public boolean isResult(){
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String toString() {
        return "Message{" +
                "type=" + type +
                ", arg=" + arg +
                ", arg2=" + arg2 +
                '}';
    }
}
