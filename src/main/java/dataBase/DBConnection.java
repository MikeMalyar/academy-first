package dataBase;

import java.sql.*;
import java.util.Properties;

public class DBConnection {

    private static final String URL = "jdbc:postgresql://localhost:5432/company";
    private static final String USER = "postgres";
    private static final String PASSWORD = "05071999";

    private Connection connection;

    public DBConnection() throws SQLException {
        Properties properties = new Properties();
        properties.setProperty("user", USER);
        properties.setProperty("password", PASSWORD);
        properties.setProperty("autoReconnect", "true");

        connection = DriverManager.getConnection(URL, properties);
    }

    public Connection getConnection() {
        return connection;
    }
}
