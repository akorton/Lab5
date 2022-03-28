package Lab5.Server.Commands;

import Lab5.Server.City;
import Lab5.Server.FileInputMaster;
import Lab5.Server.MyCollection;
import Lab5.Server.RecursionInFileException;

public class ExecuteScriptCommand<T extends City, U extends String> extends CommandOne<T, U>{

    public ExecuteScriptCommand(MyCollection<T> collection, U arg){
        super(collection, arg);
    }

    public String execute() throws RecursionInFileException {
        FileInputMaster<T> fileInputMaster = new FileInputMaster<T>(collection, arg);
        return fileInputMaster.run();
    }
}
