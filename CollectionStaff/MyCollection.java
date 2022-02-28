package Lab5.CollectionStaff;

import Lab5.CollectionStaff.City;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

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

    public void clearCollection(){
        myCollection.clear();
    }

    public boolean removeLast(){
        int size = getSize();
        if (size > 0) myCollection.removeLast();
        return size > 0;
    }

    public LinkedList<T> getMyCollection(){
        return new LinkedList<T>(myCollection);
    }

    public void addLast(T city){
        myCollection.addLast(city);
    }

    public T containsId(Long id){
        for (T city: myCollection){
            if (city.getId().equals(id)){
                return city;
            }
        }
        return null;
    }

    public void removeCity(T city){
        myCollection.remove(city);
    }

    public int indexOf(T city){
        return myCollection.indexOf(city);
    }

    public void set(int index, T city){
        myCollection.set(index, city);
    }

    public void reorder(){
        LinkedList<T> newCollection = new LinkedList<>();
        for (Iterator<T> it = myCollection.descendingIterator();it.hasNext();){
            newCollection.add(it.next());
        }
        myCollection = newCollection;
    }

    public void removeAll(Collection<T> cities) {myCollection.removeAll(cities);}

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
