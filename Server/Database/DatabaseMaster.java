package Lab5.Server.Database;

import Lab5.CommonStaff.CollectionStaff.*;
import Lab5.CommonStaff.Others.User;

import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Properties;

public class DatabaseMaster {
    private static Connection con;
    private static Statement statement;
    private static String localUrl = "jdbc:postgresql://localhost:5432/studs";
    private static String heliosUrl = "jdbc:postgresql://pg:5432/studs";
    private static DatabaseMaster databaseMaster = new DatabaseMaster();

    static {
        setUp();
    }

    private DatabaseMaster(){

    }
    
    public static DatabaseMaster getDatabaseMaster(){
        return databaseMaster;
    }

    private static boolean setUp(){
        try {
            Properties properties = new Properties();
            properties.setProperty("user", "s336667");
            properties.setProperty("password", "jto371");
            con = DriverManager.getConnection(localUrl, properties);
            String createTableUsers = "CREATE TABLE IF not EXISTS public.users"+
                    "(id INTEGER,"+
                    "login TEXT," +
                    "password TEXT," +
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
            System.out.println("Connection with database can not be established.");
            return false;
        }
    }

    public Map<Integer, User> getUsersTable(){
        String getTable = "SELECT * FROM users";
        Map<Integer, User> users = new HashMap<>();
        try (ResultSet answer = statement.executeQuery(getTable);){
            while (answer.next()){
                int id = answer.getInt("id");
                String login = answer.getString("login");
                String password = answer.getString("password");
                User user = new User(login, password.getBytes(StandardCharsets.UTF_8));
                users.put(id, user);
            }
        } catch (SQLException e){
            System.out.println("Something wrong with database.");
            return null;
        }
        return users;
    }

    public LinkedList<City> getCollectionTable(){
        String getTable = "SELECT * FROM collection";
        LinkedList<City> collection = new LinkedList<>();
        try (ResultSet answer = statement.executeQuery(getTable);){
            while (answer.next()){
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
        } catch (SQLException e){
            System.out.println("Something wrong with database.");
            return null;
        }
        return collection;
    }

    public static int getNextUserId() throws SQLException{
        String nextId = "SELECT nextval('userid')";
        ResultSet res = statement.executeQuery(nextId);
        res.next();
        int ans = res.getInt(1);
        res.close();
        return ans;
    }


    public static long getNextCollectionId() throws SQLException{
        String nextId = "SELECT nextval('collectionid');";
        ResultSet res = statement.executeQuery(nextId);
        res.next();
        long ans = res.getLong(1);
        res.close();
        return ans;
    }

    public boolean insertUser(User user){
        String insert = "INSERT INTO users VALUES(?, ?, ?)";
        try {
            PreparedStatement statement1 = con.prepareStatement(insert);
            statement1.setInt(1, getNextUserId());
            statement1.setString(2, user.getName());
            statement1.setString(3, new String(user.getEncodedPassword()));
            int result = statement1.executeUpdate();
            statement1.close();
            return result != 0;
        } catch (SQLException e) {
            System.out.println("No connection with db.");
            return false;
        }
    }
}
