package Lab5.Server.Commands;

import Lab5.Server.City;
import Lab5.Server.MyCollection;

public class HelpCommand<T extends City> extends CommandZero<T> {

    public HelpCommand(MyCollection<T> collection) {
        super(collection);
    }

    public String execute() {
        return "info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)\n" +
                "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении\n" +
                "add {element} : добавить новый элемент в коллекцию\n" +
                "update id {element} : обновить значение элемента коллекции, id которого равен заданному\n" +
                "remove_by_id id : удалить элемент из коллекции по его id\n" +
                "clear : очистить коллекцию\n" +
                "save : сохранить коллекцию в файл\n" +
                "execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.\n" +
                "exit : завершить программу (без сохранения в файл)\n" +
                "remove_last : удалить последний элемент из коллекции\n" +
                "remove_greater {element} : удалить из коллекции все элементы, превышающие заданный\n" +
                "reorder : отсортировать коллекцию в порядке, обратном нынешнему\n" +
                "group_counting_by_area : сгруппировать элементы коллекции по значению поля area, вывести количество элементов в каждой группе\n" +
                "filter_greater_than_meters_above_sea_level metersAboveSeaLevel : вывести элементы, значение поля metersAboveSeaLevel которых больше заданного\n" +
                "print_descending : вывести элементы коллекции в порядке убывания";
    }
}
