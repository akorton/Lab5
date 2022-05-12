package Lab5.Server;

import Lab5.CommonStaff.CollectionStaff.City;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * class that encapsulates all the operations upon the collection
 */
public class MyCollection {
    private static Long cur_id = 1L;
    private LinkedList<City> myCollection = new LinkedList<>();
    private final String creationTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis());
    private static final MyCollection collection = new MyCollection();
    private static final Lock lock = new ReentrantLock();

    private MyCollection(){}

    public static MyCollection getCollection(){
        return collection;
    }

    public String getCreationTime(){
        return creationTime;
    }

    public Class getType(){return myCollection.getClass();}

    public int getSize(){
        lock.lock();
        try {
            return myCollection.size();
        } finally {
            lock.unlock();
        }
    }

    public City getFirstElement(){
        lock.lock();
        try {
            return getSize() > 0 ? myCollection.getFirst() : null;
        } finally {
            lock.unlock();
        }
    }

    public City getLastElement(){
        lock.lock();
        try {
            return getSize() > 0 ? myCollection.getLast() : null;
        } finally {
            lock.unlock();
        }
    }

    /**
     * removes last element of the collection
     * @return true if the element was removed else false
     */
    public boolean removeLast(){
        lock.lock();
        try {
            int size = getSize();
            if (size > 0) myCollection.removeLast();
            return size > 0;
        } finally {
            lock.unlock();
        }
    }

    /**
     * @return the copy of the collection
     */
    public LinkedList<City> getMyCollection(){
        lock.lock();
        try {
            return new LinkedList<>(myCollection);
        } finally {
            lock.unlock();
        }
    }

    /**
     * adds element to the collection
     * @param city element to add
     */
    public void addLast(City city){
        lock.lock();
        try {
            myCollection.addLast(city);
        } finally {
            lock.unlock();
        }
    }

    public void addAll(Collection<? extends City> cities){
        lock.lock();
        try {
            for (City city : cities) {
                addLast(city);
            }
        } finally {
            lock.unlock();
        }
    }

    public boolean updateById(Long id, City arg){
        LinkedList<City> newCollection = new LinkedList<>();
        lock.lock();
        try {
            for (City city : myCollection) {
                if (city.getId().equals(id)) {
                    newCollection.add(arg);
                } else {
                    newCollection.add(city);
                }
            }
            myCollection = newCollection;
            return true;
        } finally {
            lock.unlock();
        }
    }

    /**
     * removes element with the given id from collection
     * @param id id of element to remove
     */
    public void removeCityById(Long id){
        lock.lock();
        try {
            myCollection.remove(getCityById(id));
        } finally {
            lock.unlock();
        }
    }

    private City getCityById(Long id){
        lock.lock();
        try {
            for (City city : myCollection) {
                if (city.getId().equals(id)) {
                    return city;
                }
            }
            return null;
        } finally {
            lock.unlock();
        }
    }

    /**
     * changes the order of the elements in collection
     */
    public void reorder(){
        LinkedList<City> newCollection = new LinkedList<>();
        lock.lock();
        try {
            for (Iterator<City> it = myCollection.descendingIterator(); it.hasNext(); ) {
                newCollection.add(it.next());
            }
            myCollection = newCollection;
        } finally {
            lock.unlock();
        }
    }

    /**
     * removes all given elements
     * @param cities elements to remove
     */
    public void removeAll(Collection<City> cities) {
        lock.lock();
        try {
            myCollection.removeAll(cities);
        } finally {
            lock.unlock();
        }
    }

    public String toString(){
        StringBuilder s = new StringBuilder();
        for (City city: myCollection){
            s.append(city.toString()).append("\n");
        }
        return s.toString();
    }
}
