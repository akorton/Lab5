package Lab5.Server.Commands;

import Lab5.CommonStaff.CollectionStaff.City;
import Lab5.CommonStaff.Others.Message;
import Lab5.CommonStaff.Others.User;
import Lab5.Server.MyCollection;

public class HelpCommand extends CommandZero {

    public HelpCommand(MyCollection collection, User user) {
        super(collection, user);
    }

    public Message<String, ?> execute() throws UnauthorizedException {
        checkUnauthorized();
        Message<String, ?> message = new Message<>(
                "info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)"+
                "\nshow : вывести в стандартный поток вывода все элементы коллекции в строковом представлении"+
                "\nadd {element} : добавить новый элемент в коллекцию"+
                "\nupdate id {element} : обновить значение элемента коллекции, id которого равен заданному"+
                "\nremove_by_id id : удалить элемент из коллекции по его id"+
                "\nclear : очистить коллекцию"+
                "\nexecute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме."+
                "\nexit : завершить программу (без сохранения в файл)"+
                "\nremove_last : удалить последний элемент из коллекции"+
                "\nremove_greater {element} : удалить из коллекции все элементы, превышающие заданный"+
                "\nreorder : отсортировать коллекцию в порядке, обратном нынешнему"+
                "\ngroup_counting_by_area : сгруппировать элементы коллекции по значению поля area, вывести количество элементов в каждой группе"+
                "\nfilter_greater_than_meters_above_sea_level metersAboveSeaLevel : вывести элементы, значение поля metersAboveSeaLevel которых больше заданного"+
                "\nprint_descending : вывести элементы коллекции в порядке убывания");
        message.setResult(true);
        return message;
    }
}
