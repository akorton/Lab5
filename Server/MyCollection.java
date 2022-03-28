package Lab5.Server;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * class that encapsulates all the operations upon the collection
 * @param <T>
 */
public class MyCollection<T extends City> {
    private static Long cur_id = 1L;
    private LinkedList<T> myCollection = new LinkedList<>();
    private final String creationTime;

    public MyCollection(){
        creationTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis());
    }

    public MyCollection(Collection<T> initialCollection){
        this();
        myCollection.addAll(initialCollection);
        for (T city: myCollection){
            if (city.getId().compareTo(cur_id) >= 0){
                cur_id = city.getId() + 1;
            }
        }
    }

    public String getCreationTime(){
        return creationTime;
    }

    public Class getType(){return myCollection.getClass();}

    public int getSize(){
        return myCollection.size();
    }

    public City getFirstElement(){
        return getSize() > 0 ? myCollection.getFirst() : null;
    }

    public City getLastElement(){
        return getSize() > 0 ? myCollection.getLast() : null;
    }

    /**
     * clears collection
     */
    public void clearCollection(){
        myCollection.clear();
    }

    /**
     * removes last element of the collection
     * @return true if the element was removed else false
     */
    public boolean removeLast(){
        int size = getSize();
        if (size > 0) myCollection.removeLast();
        return size > 0;
    }

    /**
     * @return the copy of the collection
     */
    public LinkedList<T> getMyCollection(){
        return new LinkedList<T>(myCollection);
    }

    /**
     * adds element to the collection
     * @param city element to add
     */
    public void addLast(T city){
        myCollection.addLast(city);
    }

    /**
     * check if the element with id is in collection
     * @param id id to check
     * @return true if it is in collection else false
     */
    private boolean containsId(Long id){
        for (T city: myCollection){
            if (city.getId().equals(id)){
                return true;
            }
        }
        return false;
    }

    public boolean updateById(Long id, T arg){
        arg.setId(id);
        if (!containsId(id)) return false;
        LinkedList<T> newCollection = new LinkedList<>();
        for (T city: myCollection){
            if (city.getId().equals(id)){
                newCollection.add(arg);
            } else{
                newCollection.add(city);
            }
        }
        myCollection = newCollection;
        return true;
    }

    /**
     * removes element with the given id from collection
     * @param id id of element to remove
     */
    public boolean removeCityById(Long id){
        if (!containsId(id)) return false;
        myCollection.remove(getCityById(id));
        return true;
    }

    private T getCityById(Long id){
        for (T city: myCollection){
            if (city.getId().equals(id)){
                return city;
            }
        }
        return null;
    }

    /**
     * changes the order od the elements in collection
     */
    public void reorder(){
        LinkedList<T> newCollection = new LinkedList<>();
        for (Iterator<T> it = myCollection.descendingIterator();it.hasNext();){
            newCollection.add(it.next());
        }
        myCollection = newCollection;
    }

    /**
     * removes all given elements
     * @param cities elements hto remove
     */
    public void removeAll(Collection<T> cities) {myCollection.removeAll(cities);}

    /**
     * generates id for the next element
     * @return id
     */
    public static Long generateNextId(){
        return cur_id++;
    }

    public String toString(){
        String s = "";
        for (T city: myCollection){
            s += city.toString() + "\n";
        }
        return s;
    }
}
