package Lab5.Server.Commands;

import Lab5.CommonStaff.CollectionStaff.City;
import Lab5.CommonStaff.Others.Message;
import Lab5.CommonStaff.Others.User;
import Lab5.Server.FileInputMaster;
import Lab5.Server.MyCollection;
import Lab5.Server.RecursionInFileException;

public class ExecuteScriptCommand extends CommandOne<String>{

    public ExecuteScriptCommand(MyCollection collection, User user, String arg){
        super(collection, user, arg);
    }

    public Message<String, ?> execute() throws RecursionInFileException, UnauthorizedException {
        checkUnauthorized();
        FileInputMaster fileInputMaster = new FileInputMaster(arg, user);
        Message<String, ?> message = new Message<>(fileInputMaster.run());
        message.setResult(true);
        return message;
    }
}
