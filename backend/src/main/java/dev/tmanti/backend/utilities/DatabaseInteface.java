package dev.tmanti.backend.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

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

    private final String createUsersTableSQL = "CREATE TABLE IF NOT EXISTS users (" +
            "id INT NOT NULL, " +
            "username VARCHAR(50) NOT NULL, " +
            "passwordhash TEXT NOT NULL, " +
            "privilege INT NOT NULL DEFAULT '0', " +
            "PRIMARY KEY (id)" +
        ")";

    private final String createResourcesTableSQL = "CREATE TABLE IF NOT EXISTS resources (" +
            "id INT NOT NULL, " +
            "name VARCHAR(50) NOT NULL, " +
            "type INT NOT NULL, " +
            "location TEXT, " +
            "event_date DATE, " +
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
            userStatement.execute(createUsersTableSQL);

            Statement resStatement = dbconnection.createStatement();
            resStatement.execute(createResourcesTableSQL);
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
