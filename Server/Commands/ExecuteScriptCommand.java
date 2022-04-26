package Lab5.Server.Commands;

import Lab5.CommonStaff.CollectionStaff.City;
import Lab5.Server.FileInputMaster;
import Lab5.Server.MyCollection;
import Lab5.Server.RecursionInFileException;

public class ExecuteScriptCommand extends CommandOne<String>{

    public ExecuteScriptCommand(MyCollection collection, String arg){
        super(collection, arg);
    }

    public String execute() throws RecursionInFileException {
        FileInputMaster fileInputMaster = new FileInputMaster(arg);
        return fileInputMaster.run();
    }
}
