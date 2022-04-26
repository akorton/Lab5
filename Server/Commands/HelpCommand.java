package Lab5.Server.Commands;

import Lab5.CommonStaff.CollectionStaff.City;
import Lab5.Server.MyCollection;

public class HelpCommand extends CommandZero {

    public HelpCommand(MyCollection collection) {
        super(collection);
    }

    public String execute() {
        return """
                info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)
                show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении
                add {element} : добавить новый элемент в коллекцию
                update id {element} : обновить значение элемента коллекции, id которого равен заданному
                remove_by_id id : удалить элемент из коллекции по его id
                clear : очистить коллекцию
                save : сохранить коллекцию в файл
                execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.
                exit : завершить программу (без сохранения в файл)
                remove_last : удалить последний элемент из коллекции
                remove_greater {element} : удалить из коллекции все элементы, превышающие заданный
                reorder : отсортировать коллекцию в порядке, обратном нынешнему
                group_counting_by_area : сгруппировать элементы коллекции по значению поля area, вывести количество элементов в каждой группе
                filter_greater_than_meters_above_sea_level metersAboveSeaLevel : вывести элементы, значение поля metersAboveSeaLevel которых больше заданного
                print_descending : вывести элементы коллекции в порядке убывания""";
    }
}
