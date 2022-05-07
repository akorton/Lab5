package Lab5.Server.Commands;

import Lab5.CommonStaff.CollectionStaff.City;
import Lab5.CommonStaff.Others.Message;
import Lab5.Server.FileInputMaster;
import Lab5.Server.MyCollection;
import Lab5.Server.RecursionInFileException;

public class ExecuteScriptCommand extends CommandOne<String>{

    public ExecuteScriptCommand(MyCollection collection, String arg){
        super(collection, arg);
    }

    public Message<String, ?> execute() throws RecursionInFileException {
        FileInputMaster fileInputMaster = new FileInputMaster(arg);
        Message<String, ?> message = new Message<>(fileInputMaster.run());
        message.setResult(true);
        return message;
    }
}
