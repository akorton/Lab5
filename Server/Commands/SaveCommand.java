package Lab5.Server.Commands;

import Lab5.CommonStaff.JsonStaff.GsonMaster;
import Lab5.CommonStaff.CollectionStaff.City;
import Lab5.Server.MyCollection;

import java.io.*;
import java.util.LinkedList;

public class SaveCommand extends CommandOne<String>{

    public SaveCommand(MyCollection myCollection, String path){
        super(myCollection, path);
    }

    public String execute(){
        try{
            File file = new File(arg);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(file));
            String data = new GsonMaster<LinkedList<City>>().serialize(collection.getMyCollection());
            outputStreamWriter.write(data);
            outputStreamWriter.close();
            return "Successfully saved.";
        } catch (FileNotFoundException e){
            return "Can not create files in the directory.";
        } catch (IOException e){
            return "Can not write in this file.";
        }
    }
}
