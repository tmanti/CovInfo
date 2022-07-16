package dev.tmanti.backend.utilities;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

public class DatabaseInteface {

    private static DatabaseInteface instance;

    public static DatabaseInteface getInstance() throws RuntimeException {
        if(instance == null){
            throw new RuntimeException("the database has not been initialized!");
        }

        return instance;
    }

    private String url = "jdbc:postgresql://localhost:5432/covinfo";
    private String user = "postgres";
    private String password = "root";

    private Connection dbconnection = null;

    private final String CREATE_USERS_TABLE = "CREATE TABLE IF NOT EXISTS users (" +
            "id TEXT NOT NULL, " +
            "username VARCHAR(50) NOT NULL, " +
            "passwordhash TEXT NOT NULL, " +
            "privilege INT NOT NULL DEFAULT '0', " +
            "PRIMARY KEY (id)" +
        ")";

    private final String CREATE_RESOURCES_TABLE = "CREATE TABLE IF NOT EXISTS resources (" +
            "id TEXT NOT NULL, " +
            "name VARCHAR(50) NOT NULL, " +
            "type INT NOT NULL, " +
            "location TEXT, " +
            "date DATE, " +
            "comment TEXT, " +
            "PRIMARY KEY (id)" +
        ")";

    private void init(){
        try{
            dbconnection = DriverManager.getConnection(url, user, password);

            if(dbconnection != null){
                System.out.println("Connected to PostgreSQL server successfully.");
            } else {
                System.out.println("Failed to connect!");
            }
        } catch (SQLException e) {
            System.out.println("[COVInfo] Error when connection to database: " + e.getMessage());
        }

        try{
            Statement userStatement = dbconnection.createStatement();
            userStatement.execute(CREATE_USERS_TABLE);

            Statement resStatement = dbconnection.createStatement();
            resStatement.execute(CREATE_RESOURCES_TABLE);
        } catch (SQLException e){
            printSQLException(e);
        }
    }

    public DatabaseInteface(){
        instance = this;
        this.init();
    }

    public DatabaseInteface(String url, String user, String password){
        instance = this;

        this.url = url;
        this.user = user;
        this.password = password;
        this.init();
    }

    public void AddResource(Resource res){
        String INSERT_RESOURCE = "INSERT INTO resources "+
                "(id, name, type, location, date, comment) VALUES " +
                "(?, ?, ?, ?, ?, ?);";

        try{
            PreparedStatement statement = dbconnection.prepareStatement(INSERT_RESOURCE);
            statement.setString(1, res.getId().toString());
            statement.setString(2, res.getName());
            statement.setString(3, res.getType().toString());
            statement.setString(4, res.getLocation());
            statement.setDate(5, Date.valueOf(res.getDate()));
            statement.setString(6, res.getComment());

            statement.executeUpdate();
        } catch(SQLException e){
            printSQLException(e);
        }
    }

    public void UpdateResource(Resource res){
        StringBuilder UPDATE_RESOURCE_BUILDER = new StringBuilder();
        UPDATE_RESOURCE_BUILDER.append("UPDATE resources SET ");
        if(res.getName() != null) UPDATE_RESOURCE_BUILDER.append("name = ").append(res.getName()).append(", ");
        if(res.getType() != null) UPDATE_RESOURCE_BUILDER.append("type = ").append(res.getType().toString()).append(", ");
        if(res.getLocation() != null) UPDATE_RESOURCE_BUILDER.append("location = ").append(res.getLocation()).append(", ");
        if(res.getDate() != null) UPDATE_RESOURCE_BUILDER.append("date = ").append(res.getDate().toString()).append(", ");
        if(res.getComment() != null) UPDATE_RESOURCE_BUILDER.append("comment = ").append(res.getComment()).append(", ");
        int l = UPDATE_RESOURCE_BUILDER.length();
        UPDATE_RESOURCE_BUILDER.delete(l-2, l);
        UPDATE_RESOURCE_BUILDER.append(" WHERE id = ").append(res.getId().toString()).append(";");

        String UPDATE_RESOURCE = UPDATE_RESOURCE_BUILDER.toString();
        try{
            Statement statement = dbconnection.createStatement();
            statement.executeUpdate(UPDATE_RESOURCE);
        } catch(SQLException e){
            printSQLException(e);
        }
    }

    public int DeleteResource(UUID id){
        String DELETE_RESOURCE = "DELETE FROM resources where id = ?;";

        int result = 0;

        try{
            PreparedStatement statement = dbconnection.prepareStatement(DELETE_RESOURCE);
            statement.setString(1, id.toString());

            result = statement.executeUpdate();
        } catch (SQLException e){
            printSQLException(e);
            result = -1;
        }

        return result;
    }

    public Resource GetResource(UUID id){
        String GET_RESOURCE = "SELECT id,name,type,location,date,comment FROM resources WHERE id = ?;";
        Resource res = null;

        try{
            PreparedStatement statement = dbconnection.prepareStatement(GET_RESOURCE);
            statement.setString(1, id.toString());

            ResultSet rs = statement.executeQuery();

            if(rs.next()){
                res = new Resource();

                res.setId(UUID.fromString(rs.getString("id")));

                res.setName(rs.getString("name"));
                res.setType(ResourceType.valueOf(rs.getString("type")));
                res.setLocation(rs.getString("location"));

                Date d = rs.getDate("date");
                if(d != null) {
                    res.setDate(d.toLocalDate());
                }

                res.setComment(rs.getString("comment"));
            }
        } catch (SQLException e){
            printSQLException(e);
        }

        return res;
    }

    public ArrayList<Resource> GetResources(){
        String GET_RESOURCE = "SELECT id,name,type,location,date,comment FROM resources;";
        ArrayList<Resource> resources = new ArrayList<Resource>();

        try{
            PreparedStatement statement = dbconnection.prepareStatement(GET_RESOURCE);

            ResultSet rs = statement.executeQuery();

            while(rs.next()){
                Resource res = new Resource();
                
                res.setId(UUID.fromString(rs.getString("id")));

                res.setName(rs.getString("name"));
                res.setType(ResourceType.valueOf(rs.getString("type")));
                res.setLocation(rs.getString("location"));

                Date d = rs.getDate("date");
                if(d != null) {
                    res.setDate(d.toLocalDate());
                }

                res.setComment(rs.getString("comment"));

                resources.add(res);
            }
        } catch (SQLException e){
            printSQLException(e);
        }

        return resources;
    }

    public ArrayList<Resource> GetResources(ResourceType type){
        String GET_RESOURCE = "SELECT id,name,type,location,date,comment FROM resources WHERE type = ?;";
        ArrayList<Resource> resources = new ArrayList<>();

        try{
            PreparedStatement statement = dbconnection.prepareStatement(GET_RESOURCE);
            statement.setString(1, type.toString());

            ResultSet rs = statement.executeQuery();

            while(rs.next()){
                Resource res = new Resource();

                res.setId(UUID.fromString(rs.getString("id")));

                res.setName(rs.getString("name"));
                res.setType(ResourceType.valueOf(rs.getString("type")));
                res.setLocation(rs.getString("location"));

                Date d = rs.getDate("date");
                if(d != null) {
                    res.setDate(d.toLocalDate());
                }

                res.setComment(rs.getString("comment"));

                resources.add(res);
            }
        } catch (SQLException e){
            printSQLException(e);
        }

        return resources;
    }

    public ArrayList<Resource> GetResources(String name){
        String GET_RESOURCE = "SELECT id,name,type,location,date,comment FROM resources WHERE name = ?;";
        ArrayList<Resource> resources = new ArrayList<>();

        try{
            PreparedStatement statement = dbconnection.prepareStatement(GET_RESOURCE);
            statement.setString(1, name);

            ResultSet rs = statement.executeQuery();

            while(rs.next()){
                Resource res = new Resource();

                res.setId(UUID.fromString(rs.getString("id")));

                res.setName(rs.getString("name"));
                res.setType(ResourceType.valueOf(rs.getString("type")));
                res.setLocation(rs.getString("location"));

                Date d = rs.getDate("date");
                if(d != null) {
                    res.setDate(d.toLocalDate());
                }

                res.setComment(rs.getString("comment"));

                resources.add(res);
            }
        } catch (SQLException e){
            printSQLException(e);
        }

        return resources;
    }

    public User GetUser(UUID id){
        String GET_USER = "SELECT id,username,passwordhash,location,date,comment FROM users WHERE id = ?;";
        User user = null;

        try{
            PreparedStatement statement = dbconnection.prepareStatement(GET_USER);
            statement.setString(1, id.toString());

            ResultSet rs = statement.executeQuery();

            if(rs.next()) {
                user = new User(
                        UUID.fromString(rs.getString("id")),
                        rs.getString("username"),
                        rs.getString("passwordhash"),
                        rs.getInt("privilege")
                );
            }
        } catch (SQLException e){
            printSQLException(e);
        }

        return user;
    }

    public User GetUser(String username){
        String GET_USER = "SELECT id,username,passwordhash,location,date,comment FROM users WHERE username = ?;";
        User user = null;

        try{
            PreparedStatement statement = dbconnection.prepareStatement(GET_USER);
            statement.setString(1, username);

            ResultSet rs = statement.executeQuery();

            if(rs.next()) {
                user = new User(
                        UUID.fromString(rs.getString("id")),
                        rs.getString("username"),
                        rs.getString("passwordhash"),
                        rs.getInt("privilege")
                );
            }
        } catch (SQLException e){
            printSQLException(e);
        }

        return user;
    }

    public void AddUser(User user){
        String INSERT_USER = "INSERT INTO users "+
                "(id, username, passwordhash, privilege) VALUES " +
                "(?, ?, ?, ?);";

        try{
            PreparedStatement statement = dbconnection.prepareStatement(INSERT_USER);
            statement.setString(1, user.getId().toString());
            statement.setString(2, user.getUsername());
            statement.setString(3, user.getPasswordhash());
            statement.setInt(4, user.getPrivilege());

            statement.executeUpdate();
        } catch(SQLException e){
            printSQLException(e);
        }
    }

    public static void printSQLException(SQLException ex) {
        for (Throwable e: ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}
