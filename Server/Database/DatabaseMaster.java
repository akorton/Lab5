package Lab5.Server.Database;

import Lab5.CommonStaff.CollectionStaff.*;
import Lab5.CommonStaff.Others.User;

import java.sql.*;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DatabaseMaster {
    private static Connection con;
    private static Statement statement;
    private static String localUrl = "jdbc:postgresql://localhost:5432/studs";
    private static String heliosUrl = "jdbc:postgresql://pg:5432/studs";
    private static DatabaseMaster databaseMaster = new DatabaseMaster();
    private static final Lock lock = new ReentrantLock();

    private DatabaseMaster(){
    }
    
    public static DatabaseMaster getDatabaseMaster(){
        return databaseMaster;
    }

    public static boolean setUp(){
        try {
            Properties properties = new Properties();
            properties.setProperty("user", "s336667");
            properties.setProperty("password", "jto371");
            con = DriverManager.getConnection(localUrl, properties);
            String createTableUsers = "CREATE TABLE IF not EXISTS public.users"+
                    "(id INTEGER,"+
                    "login TEXT," +
                    "password bytea," +
                    "salt TEXT," +
                    "PRIMARY KEY(id)"+
                    ")";
            Statement st = con.createStatement();
            statement = st;
            st.executeUpdate(createTableUsers);
            String createTableCollection = "CREATE TABLE IF not EXISTS public.collection"+
                    "(id BIGINT,"+
                    "name TEXT," +
                    "x FLOAT," +
                    "y FLOAT," +
                    "creationTime TEXT," +
                    "area FLOAT," +
                    "population BIGINT," +
                    "metersAbove FLOAT," +
                    "agglomeration FLOAT," +
                    "climate TEXT," +
                    "standart TEXT," +
                    "age INTEGER," +
                    "birthday TEXT," +
                    "userId INTEGER," +
                    "PRIMARY KEY(id)"+
                    ")";
            st.executeUpdate(createTableCollection);
            String createUserIdSequence = "CREATE SEQUENCE IF not EXISTS userId START WITH 1;";
            st.executeUpdate(createUserIdSequence);
            String createCollectionIdSequence = "CREATE SEQUENCE IF not EXISTS collectionId START WITH 1;";
            st.executeUpdate(createCollectionIdSequence);
            return true;
        } catch (SQLException e){
            e.printStackTrace();
            System.out.println("Connection with database can not be established.");
            return false;
        }
    }

    public Map<Integer, User> getIdToUsersTable() throws SQLException {
        String getTable = "SELECT * FROM users";
        Map<Integer, User> users = new HashMap<>();
        lock.lock();
        try (ResultSet answer = statement.executeQuery(getTable)){
            while (answer.next()){
                int id = answer.getInt("id");
                String login = answer.getString("login");
                byte[] password = answer.getBytes("password");
                String salt = answer.getString("salt");
                User user = new User(login, password, salt);
                users.put(id, user);
            }
        } finally {
            lock.unlock();
        }
        return users;
    }

    private Map<User, Integer> getUserToIdTable() throws SQLException {
        String getTable = "SELECT * FROM users";
        Map<User, Integer> users = new HashMap<>();
        lock.lock();
        try (ResultSet answer = statement.executeQuery(getTable)) {
            while (answer.next()) {
                int id = answer.getInt("id");
                String login = answer.getString("login");
                byte[] password = answer.getBytes("password");
                String salt = answer.getString("salt");
                User user = new User(login, password, salt);
                users.put(user, id);
            }
        } finally {
            lock.unlock();
        }
        return users;
    }

    public int getIdByUser(User user) throws SQLException{
        int result = getUserToIdTable().getOrDefault(user, -1);
        if (result == -1) throw new SQLException();
        return result;
    }

    public LinkedList<City> getCollectionTable() throws SQLException {
        String getTable = "SELECT * FROM collection";
        LinkedList<City> collection = new LinkedList<>();
        lock.lock();
        try (ResultSet answer = statement.executeQuery(getTable);) {
            while (answer.next()) {
                long id = answer.getLong("id");
                String name = answer.getString("name");
                double x = answer.getDouble("x");
                float y = answer.getFloat("y");
                Coordinates coordinates = new Coordinates(x, y);
                ZonedDateTime creationTime = ZonedDateTime.parse(answer.getString("creationTime"));
                double area = answer.getDouble("area");
                long population = answer.getLong("population");
                float meters = answer.getFloat("metersAbove");
                float agglomeration = answer.getFloat("agglomeration");
                Climate climate = Climate.valueOf(answer.getString("climate"));
                StandartOfLiving standart = StandartOfLiving.valueOf(answer.getString("standart"));
                int age = answer.getInt("age");
                ZonedDateTime birthday = ZonedDateTime.parse(answer.getString("birthday"));
                Human human = new Human(age, birthday);
                City city = new City(id, name, coordinates, creationTime, area, population, meters, agglomeration, climate, standart, human);
                int userId = answer.getInt("userId");
                city.setUserId(userId);
                collection.add(city);
            }
        } finally {
            lock.unlock();
        }
        return collection;
    }

    public static int getNextUserId() throws SQLException{
        String nextId = "SELECT nextval('userid')";
        lock.lock();
        try {
            ResultSet res = statement.executeQuery(nextId);
            res.next();
            int ans = res.getInt(1);
            res.close();
            return ans;
        } finally {
            lock.unlock();
        }
    }


    public static long getNextCollectionId() throws SQLException{
        String nextId = "SELECT nextval('collectionid');";
        lock.lock();
        try {
            ResultSet res = statement.executeQuery(nextId);
            res.next();
            long ans = res.getLong(1);
            res.close();
            return ans;
        } finally {
            lock.unlock();
        }
    }

    public boolean insertUser(User user) throws SQLException {
        String insert = "INSERT INTO users VALUES(?, ?, ?, ?)";
        PreparedStatement statement1 = con.prepareStatement(insert);
        statement1.setInt(1, getNextUserId());
        statement1.setString(2, user.getName());
        statement1.setBytes(3, user.getEncodedPassword());
        statement1.setString(4, user.getSalt());
        lock.lock();
        try {
            int result = statement1.executeUpdate();
            return result != 0;
        } finally {
            statement1.close();
            lock.unlock();
        }
    }

    public boolean insertCity(City city) throws SQLException {
        String insertCity = "INSERT INTO collection VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        city.setId(getNextCollectionId());
        PreparedStatement statement1 = con.prepareStatement(insertCity);
        statement1.setLong(1, city.getId());
        statement1.setString(2, city.getName());
        statement1.setDouble(3, city.getCoordinates().getX());
        statement1.setFloat(4, city.getCoordinates().getY());
        statement1.setString(5, city.getCreationDate().toString());
        statement1.setDouble(6, city.getArea());
        statement1.setLong(7, city.getPopulation());
        statement1.setFloat(8, city.getMetersAboveSeaLevel());
        statement1.setFloat(9, city.getAgglomeration());
        statement1.setString(10, city.getClimate().toString());
        statement1.setString(11, city.getStandartOfLiving().toString());
        statement1.setInt(12, city.getGovernor().getAge());
        statement1.setString(13, city.getGovernor().getBirthday().toString());
        statement1.setInt(14, city.getUserId());
        lock.lock();
        try {
            int result = statement1.executeUpdate();
            return result != 0;
        } finally {
            statement1.close();
            lock.unlock();
        }
    }

    public boolean containsId(long id) throws SQLException{
        LinkedList<City> collection = getCollectionTable();
        for (City city: collection){
            if (city.getId() == id){
                return true;
            }
        }
        return false;
    }

    public City getCityById(long id) throws SQLException{
        LinkedList<City> collection = getCollectionTable();
        for (City city: collection){
            if (city.getId() == id){
                return city;
            }
        }
        return null;
    }

    public boolean removeCityById(long id) throws SQLException{
        String deleteCity = "DELETE from collection WHERE id = ?";
        PreparedStatement st = con.prepareStatement(deleteCity);
        st.setLong(1, id);
        lock.lock();
        try {
            int result = st.executeUpdate();
            return result != 0;
        } finally {
            st.close();
            lock.unlock();
        }
    }

    public boolean removeAll(Collection<City> cities) throws SQLException{
        lock.lock();
        try {
            for (City city : cities) {
                if (!removeCityById(city.getId())) {
                    return false;
                }
            }
            return true;
        } finally {
            lock.unlock();
        }
    }

    public boolean update(Long id, City newCity) throws SQLException{
        String update = "UPDATE collection SET name=?,x=?,y=?,creationTime=?," +
                "area=?,population=?,metersAbove=?,agglomeration=?,climate=?,standart=?," +
                "age=?,birthday=? WHERE id=?";
        PreparedStatement statement1 = con.prepareStatement(update);
        statement1.setString(1, newCity.getName());
        statement1.setDouble(2, newCity.getCoordinates().getX());
        statement1.setFloat(3, newCity.getCoordinates().getY());
        statement1.setString(4, newCity.getCreationDate().toString());
        statement1.setDouble(5, newCity.getArea());
        statement1.setLong(6, newCity.getPopulation());
        statement1.setFloat(7, newCity.getMetersAboveSeaLevel());
        statement1.setFloat(8, newCity.getAgglomeration());
        statement1.setString(9, newCity.getClimate().toString());
        statement1.setString(10,newCity.getStandartOfLiving().toString());
        statement1.setInt(11, newCity.getGovernor().getAge());
        statement1.setString(12,newCity.getGovernor().getBirthday().toString());
        statement1.setLong(13, id);
        lock.lock();
        try {
            int result = statement1.executeUpdate();
            return result != 0;
        } finally {
            statement1.close();
            lock.unlock();
        }
    }
}
