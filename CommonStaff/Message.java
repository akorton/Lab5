package Lab5.CommonStaff;

import java.io.Serializable;

public class Message<T, U> implements Serializable {
    public CommandTypes type;
    public T arg = null;
    public U arg2 = null;


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

    public CommandTypes getType(){
        return type;
    }

    public T getArg() {
        return arg;
    }

    public U getArg2() {
        return arg2;
    }

    public Class<?> getArgClass(){
        return arg.getClass();
    }
    public Class<?> getArg2Class(){
        return arg2.getClass();
    }


    public String toString() {
        return "Message{" +
                "type=" + type +
                ", arg=" + arg +
                ", arg2=" + arg2 +
                '}';
    }
}
